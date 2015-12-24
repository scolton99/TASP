package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.ChatFilter;
import tech.spencercolton.tasp.Util.ColorChat;
import tech.spencercolton.tasp.Util.Mail;

/**
 * @author Spencer Colton
 */
public class MailCmd extends TASPCommand {

    public static final String syntax = "/mail <option> [options]";
    public static final String name = "mail";
    public static final String permission = "tasp.mail";
    public static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        switch(args[0]) {
            case "send":
                if(args.length < 3) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }

                if(Person.personExists(args[1]) != null) {
                    String msg = "";
                    for(int i = 2; i < args.length; i++) {
                        msg += args[i];
                        if(!((i + 1) >= args.length))
                            msg += " ";
                    }
                    msg = ColorChat.color(msg);
                    msg = ChatFilter.filter(msg);
                    Mail.send(Person.get((Player)sender), Person.personExists(args[1]), msg);
                    return;
                } else {
                    Command.sendPlayerMessage(sender, args[1]);
                    return;
                }
            case "check":

            default:
                Command.sendSyntaxError(sender, this);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
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
    public String predictRequiredPermission(CommandSender sender, String... args) {
        if(args.length >= 1) {
            switch(args[0]) {
                case "send":
                    return permission + ".send";
                case "check":
                    return permission + ".read";
                case "read":
                    return permission + ".read";
                case "delete":
                    return permission + ".delete";
                default:
                    return permission;
            }
        } else {
            return permission;
        }
    }

}
