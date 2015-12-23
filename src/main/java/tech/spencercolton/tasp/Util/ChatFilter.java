package tech.spencercolton.tasp.Util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Spencer Colton
 */
public class ChatFilter {

    private static List<String> filteredWords = Config.getListString("bad-words");

    public static String filter(String s) {
        for(String a : filteredWords) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < s.length(); i++)
                sb.append(Config.getFilterChar());
            String n = sb.toString();
            s = s.replaceAll("(?i)" + Pattern.quote(a), n);
        }

        return s;
    }



}
