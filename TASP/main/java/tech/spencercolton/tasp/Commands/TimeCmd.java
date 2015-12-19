package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCmd extends TASPCommand {

    public static final String name = "time";
    public static final String permission = "tasp.time";
    public static final String syntax = "/time [hh:mm] OR /time [hh:mm am|pm]";
    public static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch(args.length) {
            case 0:
                long g = Bukkit.getWorlds().get(0).getTime();
                long x = (g/1000);
                x += 6;
                if(x >= 24) {
                    x -= 24;
                }
                long m = g % 1000;
                long min = g/1000 * 60;
                sendTimeMessage(sender, x, min);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean predictOthers(CommandSender sender, String[] args) {
        return false;
    }

    private void sendTimeMessage(CommandSender sender, long h, long m) {
        sender.sendMessage(M.m("command-message-text.time", Long.toString(h), Long.toString(m)));
    }

}
