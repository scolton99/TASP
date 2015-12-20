package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Util.Config;

import java.util.Arrays;
import java.util.List;

public class MeCmd extends TASPCommand {

    public static final String syntax = "/me <action>";
    public static final String consoleSyntax = syntax;
    public static final String permission = "tasp.me";
    public static final String name = "me";

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<String> arg = Arrays.asList(args);
        String msg = "";

        for(int i = 0; i < arg.size(); i++) {
            msg += arg.get(i);
            if(!((i + 1) >= arg.size()))
                msg += " ";
        }

        Bukkit.broadcastMessage(Config.c1() + " * " + Config.c2() + sender.getName() + Config.c1() + " " + msg);
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
    public boolean predictOthers(CommandSender sender, String[] s) {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
