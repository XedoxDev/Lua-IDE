package xedox.luaide.editor.view.parser;

public class Token {
    public int color, start, end;

    public Token(int color, int start, int end) {
        this.color = color;
        this.start = start;
        this.end = end;
    }
}
