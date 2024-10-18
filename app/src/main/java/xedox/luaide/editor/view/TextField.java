package xedox.luaide.editor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import androidx.appcompat.widget.AppCompatEditText;
import java.util.List;
import xedox.luaide.editor.view.parser.Parser;
import xedox.luaide.editor.view.parser.Token;

public class TextField extends AppCompatEditText {

    public static class Colors {
        public static int background = Color.WHITE;
        public static int text = Color.BLACK;
    }

    public TextField(Context context) {
        super(context);
        init();
    }

    public TextField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextField(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);
        init();
    }

    private void init() {
        setGravity(Gravity.START);
        setBackgroundColor(Colors.background);
        setTextColor(Colors.text);
        addTextChangedListener(watcher);
        setHorizontalScrollBarEnabled(true);
        setTextSize(24);
    }

    public int getTopVisibleLine() {
        if (getLineHeight() == 0) {
            return 0;
        }
        int line = getScrollY() / getLineHeight();
        if (line < 0) {
            return 0;
        }
        return line >= getLineCount() ? getLineCount() - 1 : line;
    }

    public int getBottomVisibleLine() {
        if (getLineHeight() == 0) {
            return 0;
        }
        int line = getTopVisibleLine() + getHeight() / getLineHeight() + 1;
        if (line < 0) {
            return 0;
        }
        return line >= getLineCount() ? getLineCount() : line;
    }

    private TextWatcher watcher =
            new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

                @Override
                public void afterTextChanged(Editable s) {
                    startParserTask();
                }
            };

    public void setSpan(int color, int start, int end) {
        getText()
                .setSpan(
                        new ForegroundColorSpan(color),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void highlight(List<Token> tokens) {
        for (Token t : tokens) {
            setSpan(t.color, t.start, t.end);
        }
    }

    private boolean syntaxHighlight = true;

    public Object[][] getScheme() {
        if (syntaxHighlight) {
            return new Object[][] {
                {"^.*$", Colors.text},
                {"[a-zA-Z\\d]+(?=\\()", Color.parseColor("#00aabb")},
                {
                    "\\b(if|function|else|elseif|for|while|break|in|false|true|nil)",
                    Color.parseColor("#ffaa00")
                },
                {"(\".*\")|(\'.*\')", Color.parseColor("#00bb50")} ,
                {"(--.*?$)|(--\\[.*\\])", Color.parseColor("#aaaaaa")}
            };
        } else {
            return new Object[][] {};
        }
    }

    @Override
    protected void onSizeChanged(int oldWidth, int oldHeight, int width, int height) {
        super.onSizeChanged(oldWidth, oldHeight, width, height);
        startParserTask();
    }

    public String getVisibleText() {
        try {
            String[] lines = getText().toString().split("\n");
            StringBuilder buffer = new StringBuilder();

            if (getText().length() > 0 && lines.length > 0) {
                for (int i = getTopVisibleLine(); i < getBottomVisibleLine(); i++) {
                    buffer.append(lines[i]);
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public void startParserTask() {
        if (syntaxHighlight) {
            highlight(Parser.parse(this));
        }
    }

    public boolean getSyntaxHighlight() {
        return this.syntaxHighlight;
    }

    public void setSyntaxHighlight(boolean syntaxHighlight) {
        this.syntaxHighlight = syntaxHighlight;
    }

    public void print(Object txt) {
        setText(getText() + txt.toString());
    }

    public void println(Object txt) {
        setText(getText() + txt.toString() + "\n");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPadding(padding, padding, padding, padding);
    }

    private int padding = 10;

    public int getPadding() {
        return this.padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}
