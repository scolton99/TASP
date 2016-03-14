package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import java.io.File;

import static java.io.File.*;
import static org.bukkit.Bukkit.*;
import static org.bukkit.ChatColor.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.*;
import static tech.spencercolton.tasp.TASP.*;
import static tech.spencercolton.tasp.TASP.reload;
import static tech.spencercolton.tasp.Configuration.Config.*;

public class TASPCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    @Getter
    private static final String name = "tasp";


    /**
     * String containing the command's syntax.
     */
    @Getter
    private final String syntax = "/tasp [option]";


    /**
     * String containing the command's console syntax.
     */
    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.tasp";

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse execute(CommandSender sender, String... args) {
        if (args.length == 0) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        switch (args[0]) {
            case "reload": {
                reload();
                sender.sendMessage(c3() + "" + ITALIC + "TASP" + RESET + "" + c4() + " reloaded.");
                break;
            }
            case "config": {
                sender.sendMessage(err() + "Sorry, this feature is not yet implemented.");
                break;
            }
            case "deleteconfig": {
                File f = new File(dataFolder().getAbsolutePath() + separator + "config.yml");
                boolean deleted = f.delete();
                if (deleted) {
                    reloadTASPConfig();
                    sender.sendMessage(c3() + "Main configuration file deleted and reset to factory defaults.  TASP configuration was reloaded.");
                } else {
                    sender.sendMessage(err() + "Main configuration file could not be deleted.  No changes were made.");
                }
                return CommandResponse.SUCCESS;
            }
            case "resetplayer": {
                if (args.length != 2) {
                    sendGenericSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }

                Player p = getPlayer(args[1]);
                if (p == null) {
                    sendPlayerMessage(sender, args[1]);
                    return CommandResponse.PLAYER;
                }

                Person pa = get(p);
                assert pa != null;
                if (pa.resetData()) {
                    sender.sendMessage(c3() + "Player " + c4() + p.getDisplayName() + c3() + "'s data was reset.");
                }
                return CommandResponse.SUCCESS;
            }
            case "deletemail": {
                sender.sendMessage(err() + "Sorry, this feature is not yet implemented.");
                break;
            }
            default: {
                sendSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
        return CommandResponse.SUCCESS;
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        if (args.length == 0)
            return permission;

        switch (args[0]) {
            case "reload":
                return permission + ".reload";
            case "version":
                return permission + ".version";
            case "config":
                if (args[1].equals("set"))
                    return permission + ".config.set";
                if (args[1].equals("get"))
                    return permission + ".config.get";
                return permission + ".config";
            default:
                return permission;
        }
    }

}
