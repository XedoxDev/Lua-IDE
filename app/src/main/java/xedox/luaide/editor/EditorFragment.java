package xedox.luaide.editor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.HorizontalScrollView;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileReader;
import xedox.luaide.App;
import xedox.luaide.editor.view.TextField;

import java.io.File;
import java.io.FileWriter;

public class EditorFragment extends Fragment {

    private TextField editor;
    private File file;

    public EditorFragment(File file) {
        this.file = file;
    }

    public void saveChanges() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(editor.getText().toString());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle) {
        TextField editor = new TextField(getContext());
        this.editor = editor;
        editor.setText(readFile(file));
        editor.startParserTask();
        
        return editor;
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return file.getName();
    }
    
    private static String readFile(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }
}
