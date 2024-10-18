package xedox.luaide.runCode;

import android.widget.EditText;
import java.io.PrintStream;
import xedox.luaide.editor.view.TextField;

public class LuaPrintStream extends PrintStream {
    
    private TextField codeEditor;

    public LuaPrintStream(TextField codeEditor) {
        super(System.out);
        this.codeEditor = codeEditor;
    }

    @Override
    public void print(boolean b) {
        codeEditor.print(b);
    }

    @Override
    public void print(char c) {
        codeEditor.print(c);
    }

    @Override
    public void print(int i) {
        codeEditor.print(i);
    }

    @Override
    public void print(long l) {
        codeEditor.print(l);
    }

    @Override
    public void print(float f) {
        codeEditor.print(f);
    }

    @Override
    public void print(double d) {
        codeEditor.print(d);
    }

    @Override
    public void print(char[] s) {
        codeEditor.print(s);
    }

    @Override
    public void print(String s) {
        codeEditor.print(s);
    }
    
}