package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

public class BlockCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "block";


    /**
     * String containing the command's syntax.
     */
    @Getter
    private static final String syntax = "/block <user>";


    /**
     * String containing the command's console syntax.
     */
    @Getter
    private static final String consoleSyntax = "/block <user>";

    @Getter
    private static final String permission = "tasp.block";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError(sender);
            return;
        }

        if(args.length != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        Person ps = Person.get(p);
        Person pa = Person.get((Player)sender);

        if(!pa.isPlayerBlocked(ps))
            pa.blockPlayer(ps);
        else {
            Message.Block.Error.sendAlreadyBlockedMessage(sender, ps.getPlayer());
            return;
        }

        Message.Block.sendBlockedMessage(sender, ps.getPlayer());
    }

}
