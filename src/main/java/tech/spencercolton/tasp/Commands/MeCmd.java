package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Util.Config;

import java.util.Arrays;
import java.util.List;

public class MeCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/me <action>";

    @Getter
    private static final String consoleSyntax = syntax;

    @Getter
    private static final String permission = "tasp.me";

    public static final String name = "me";

    @Override
    public void execute(CommandSender sender, String... args) {
        String msg = Command.combineArgs(args);

        Bukkit.broadcastMessage(Config.c1() + " * " + Config.c2() + Command.getDisplayName(sender) + Config.c1() + " " + msg);
    }

}
