package tech.spencercolton.tasp.Util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Spencer Colton
 */
public class ChatFilter {

    private static final List<String> filteredWords = Config.getListString("bad-words");

    public static String filter(String s) {
        if(!Config.filterChat())
            return s;
        for(String a : filteredWords) {
            StringBuilder sb = new StringBuilder();
            if(Config.obfuscate())
                sb.append(ChatColor.MAGIC);
            for(int i = 0; i < a.length(); i++)
                sb.append(Config.getFilterChar());
            if(Config.obfuscate())
                sb.append(ChatColor.RESET);
            String n = sb.toString();
            s = s.replaceAll("(?i)" + Pattern.quote(a), n);
        }

        return s;
    }

}
