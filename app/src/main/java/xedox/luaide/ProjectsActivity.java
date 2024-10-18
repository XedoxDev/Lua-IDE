package xedox.luaide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xedox.luaide.project.Project;
import xedox.luaide.project.ProjectType;
import xedox.luaide.project.ProjectsAdapter;

public class ProjectsActivity extends AppCompatActivity {

    private ListView projectsList;
    private ProjectsAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        projectsList = findViewById(R.id.projects_list);
        toolbar = findViewById(R.id.toolbar);
        
        setSupportActionBar(toolbar);
        updateProjects();
    }

    public void updateProjects() {
        try {
            File[] files =
                    new File(Environment.getExternalStorageDirectory(), "L-IDE/projects")
                            .listFiles();
            List<String> projects = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    projects.add(file.getName());
                }
            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this, R.layout.project_item, R.id.project_name, projects);
            projectsList.setAdapter(adapter);
            projectsList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> av, View view, int pos, long id) {
                            Intent i = new Intent(ProjectsActivity.this, EditorActivity.class);
                            i.putExtra("name", (String) av.getItemAtPosition(pos));
                            startActivity(i);
                            finish();
                        }
                    });
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.projects_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create_project) {
            Dialogs.showCreateProjectDialog(this);
        }
        return true;
    }
}
