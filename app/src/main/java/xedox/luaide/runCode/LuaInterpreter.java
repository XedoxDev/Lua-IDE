package xedox.luaide.runCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        try {
            for (File file : project.getFiles()) {
                _G.load(readFile(file));
            }

            Thread thread =
                    new Thread() {
                        @Override
                        public void run() {
                            LuaValue result = _G.call();
                            if (!result.isnil()) {
                                String out = result.toString();
                                editor.println(
                                        out.substring(0, out.length() < 3 ? 0 : out.length() - 3));
                            }
                        }
                    };
        } catch (LuaError err) {
            editor.println(err.toString());
        }
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
        return "null";
    }
}
