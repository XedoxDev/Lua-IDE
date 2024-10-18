package xedox.luaide;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;

import java.io.File;
import xedox.luaide.project.Project;
import xedox.luaide.project.ProjectType;

public class Dialogs {

    public static void showCreateProjectDialog(ProjectsActivity act) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        dialog.setTitle(act.getString(R.string.makeProject));

        LayoutInflater inflater = act.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.create_project_dialog, null);
        dialog.setView(dialogView);

        EditText nameInput = dialogView.findViewById(R.id.project_name);
        nameInput.setHint(act.getString(R.string.project_name));
        dialog.setView(nameInput);
        dialog.setPositiveButton(
                R.string.make,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String projectName = nameInput.getText().toString();
                        try {
                            Project project = new Project(ProjectType.CONSOLE, projectName);
                            act.updateProjects();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        AlertDialog d = dialog.create();
        d.show();
    }

    public static void showMakeFileDialog(EditorActivity act, Project project) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        dialog.setTitle(act.getString(R.string.make_file));

        EditText nameInput = new EditText(act);
        nameInput.setHint(act.getString(R.string.file_name));
        
        dialog.setView(nameInput);
        
        dialog.setPositiveButton(
                R.string.make,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String projectName = nameInput.getText().toString();
                        try {
                            File file = new File(App.projectsDir, project.getName() + "/" +  nameInput.getText().toString());
                            project.addFile(file);
                            act.updateTabs();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        AlertDialog d = dialog.create();
        d.show();
    }
}
