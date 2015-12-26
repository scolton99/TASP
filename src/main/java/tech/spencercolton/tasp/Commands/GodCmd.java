package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Message;

public class GodCmd extends TASPCommand {

    public static final String name = "god";

    @Getter
    private static final String syntax = "/god [player]";

    @Getter
    private static final String consoleSyntax = "/god <player>";

    @Getter
    private static final String permission = "tasp.god";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender && args.length != 1) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Person p = null;
        switch(args.length) {
            case 1: {
                p = Person.get(Bukkit.getPlayer(args[0]));
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if (p == null)
                    p = Person.get((Player) sender);
                p.setGod(!p.isGod());
                Message.God.sendGodMessage(sender, p.isGod(), p.getPlayer());
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... s) {
        return s.length > 0 && Bukkit.getPlayer(s[0]) != null && !Bukkit.getPlayer(s[0]).equals(sender) ? permission + ".others" : permission;
    }

}
