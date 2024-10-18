package xedox.luaide.runCode;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import xedox.luaide.EditorActivity;
import xedox.luaide.editor.view.TextField;
import xedox.luaide.project.Project;
import android.view.MenuItem;
import xedox.luaide.project.ProjectType;
import xedox.luaide.R;

public class OutputActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextField output;
    private Project project;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        toolbar = findViewById(R.id.toolbar);
        output = findViewById(R.id.output);
        project = new Project(ProjectType.CONSOLE, getIntent().getStringExtra("name"));
        code = getIntent().getStringExtra("code");
        output.setSyntaxHighlight(false);
        run();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(project.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        Intent i = new Intent(this, EditorActivity.class);
        i.putExtra("name", project.getName());

        startActivity(i);
        finish();
    }

    private void run() {
        LuaInterpreter interpeter = new LuaInterpreter(output, project);
        interpeter.run();
    }
}
