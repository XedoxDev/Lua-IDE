package xedox.luaide.runCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import xedox.luaide.editor.view.TextField;
import xedox.luaide.project.Project;

public class LuaInterpreter {

    private Globals _G;
    private LuaPrintStream ps;
    private TextField editor;
    private Project project;

    public LuaInterpreter(TextField editor, Project project) {
        _G = JsePlatform.standardGlobals();
        ps = new LuaPrintStream(editor);
        _G.STDOUT = ps;
        _G.STDERR = ps;
        this.editor = editor;
        this.project = project;
    }

    public void run() {
        List<String> codeList = new ArrayList<>();
        for (File file : project.getFiles()) {
            if (file.getName().endsWith(".lua")) codeList.add(readFile(file));
        }
        try {
            CodeTask.execute(
                    () -> {
                        for (String code : codeList) {
                            try {
                                _G.load(code);
                                editor.println(_G.call().toString());
                            } catch (LuaError err) {
                                editor.println(err.toString() + "\nCode:\n" + code);
                            }
                        }
                        _G.load(readFile(project.getMain()));
                        editor.println(_G.call().toString());
                    });
        } catch (Exception e) {
            e.printStackTrace();
            editor.println(e.toString());
        }
    }

    private static String readFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "print('Error read code-file')";
        }
    }
}
