package xedox.luaide;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import xedox.luaide.editor.EditorFragment;
import xedox.luaide.editor.EditorPagerAdapter;
import xedox.luaide.project.Project;
import xedox.luaide.project.ProjectType;
import xedox.luaide.runCode.OutputActivity;

import java.io.File;

public class EditorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager2 editorPager;
    private EditorPagerAdapter adapter;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        toolbar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        editorPager = findViewById(R.id.editorPager);

        setSupportActionBar(toolbar);

        String name = getIntent().getStringExtra("name");
        project = new Project(ProjectType.CONSOLE, name);

        updateTabs();
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getTitle() + ": " + name);
    }

    public void updateTabs() {
        adapter = new EditorPagerAdapter(this);

        for (File file : project.getFiles()) {
            if (file != null) adapter.newEditor(file);
        }

        editorPager.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator =
                new TabLayoutMediator(
                        tabs,
                        editorPager,
                        (tab, position) ->
                                tab.setText(adapter.getEditors().get(position).getName()));
        tabLayoutMediator.attach();

        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            View tabView = tab.view;
            registerForContextMenu(tabView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
        }
        if (item.getItemId() == R.id.save) {
            for (EditorFragment ef : adapter.getEditors()) {
                ef.saveChanges();
            }
        }
        if (item.getItemId() == R.id.run) {
            for (EditorFragment ef : adapter.getEditors()) {
                ef.saveChanges();
            }
            Intent i = new Intent(EditorActivity.this, OutputActivity.class);
            i.putExtra("name", project.getName());
            startActivity(i);
            finish();
        }
        if (item.getItemId() == R.id.make_file) {
            Dialogs.showMakeFileDialog(this, project);
        }

        return true;
    }

    private void back() {
        startActivity(new Intent(EditorActivity.this, ProjectsActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        getMenuInflater().inflate(R.menu.tab_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == getString(R.string.remove_file)) {
            int tabPosition = tabs.getSelectedTabPosition();
            File file = adapter.getEditors().get(tabPosition).getFile();
            if (file.delete()) {
                project.removeFile(file.getName());
                tabs.removeTab(tabs.getTabAt(tabPosition));
                adapter.removeEditor(tabPosition);
                updateTabs();
            }
        }

        return super.onContextItemSelected(item);
    }
}
