package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Config;

import java.io.File;

public class TASPCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "tasp";


    /**
     * String containing the command's syntax.
     */
    @Getter
    private static final String syntax = "/tasp [option]";


    /**
     * String containing the command's console syntax.
     */
    @Getter
    private static final String consoleSyntax = syntax;

    @Getter
    private static final String permission = "tasp.tasp";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String... args) {
        if(args.length == 0){
            Command.sendSyntaxError(sender, this);
            return;
        }

        switch(args[0]) {
            case "reload":
                TASP.reload();
                sender.sendMessage(Config.c3() + "" + ChatColor.ITALIC + "TASP" + ChatColor.RESET + "" + Config.c4() + " reloaded.");
                break;
            case "config":
                sender.sendMessage(Config.err() + "Sorry, this feature is not yet implemented.");
                break;
            case "deleteconfig":
                File f = new File(TASP.dataFolder().getAbsolutePath() + File.separator + "config.yml");
                boolean deleted = f.delete();
                if(deleted) {
                    TASP.reloadTASPConfig();
                    sender.sendMessage(Config.c3() + "Main configuration file deleted and reset to factory defaults.  TASP configuration was reloaded.");
                } else {
                    sender.sendMessage(Config.err() + "Main configuration file could not be deleted.  No changes were made.");
                }
                return;
            case "resetplayer":
                if(args.length != 2) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }

                Player p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    Command.sendPlayerMessage(sender, args[1]);
                    return;
                }

                Person pa = Person.get(p);
                assert pa != null;
                if(pa.resetData()) {
                    sender.sendMessage(Config.c3() + "Player " + Config.c4() + p.getDisplayName() + Config.c3() + "'s data was reset.");
                }
                return;
            case "deletemail":
                sender.sendMessage(Config.err() + "Sorry, this feature is not yet implemented.");
                break;
            default:
                Command.sendSyntaxError(sender, this);
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        if(args.length == 0)
            return permission;

        switch(args[0]) {
            case "reload":
                return permission + ".reload";
            case "version":
                return permission + ".version";
            case "config":
                if(args[1].equals("set"))
                    return permission + ".config.set";
                if(args[1].equals("get"))
                    return permission + ".config.get";
                return permission + ".config";
            default:
                return permission;
        }
    }

}
