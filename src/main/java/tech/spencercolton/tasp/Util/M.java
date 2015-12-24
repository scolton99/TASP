package tech.spencercolton.tasp.Util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is a message class, but for ease of coding, it was
 * named "M."
 */
public class M {

    public static String u(String s) {
        return Config.getString(s);
    }

    public static String m(String... s) {
        if (s.length == 0) {
            return null;
        }

        String str = s[0];
        String finStr = Config.getString(str);

        List<String> newStrs = new ArrayList<>();

        newStrs.addAll(Arrays.asList(s));

        if(newStrs.contains(str)) {
            newStrs.remove(str);
        }
        newStrs.add(0, Config.c2().toString());
        newStrs.add(0, Config.c1().toString());
        return MessageFormat.format(finStr, newStrs.toArray(new String[newStrs.size()]));
    }

}
