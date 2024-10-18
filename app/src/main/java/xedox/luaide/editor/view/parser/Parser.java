package xedox.luaide.editor.view.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import xedox.luaide.editor.view.TextField;
import xedox.luaide.editor.view.parser.Token;

public class Parser {
    public static List<Token> parse(TextField editor) {
        List<Token> tokens = new ArrayList<>();
        Matcher m;
        for(Object[] args : editor.getScheme()){
            m = Pattern.compile((String)args[0]).matcher(editor.getVisibleText());
            while(m.find()) {
                tokens.add(new Token((int)args[1], m.start(), m.end()));
            }
        }
        return tokens;
    }
}
