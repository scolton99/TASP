package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Message;

public class UnblockCmd extends TASPCommand {

    public static final String name = "unblock";

    @Getter
    private static final String syntax = "/unblock <player>";

    @Getter
    private static final String consoleSyntax = null;

    @Getter
    private static final String permission = "tasp.block";

    @Override
    public void execute(CommandSender sender, String... args) {
        assert !(sender instanceof ConsoleCommandSender);

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

        if(pa.isPlayerBlocked(ps))
            pa.unblockPlayer(ps);
        else {
            Message.Unblock.Error.sendNotBlockedMessage(sender, ps);
            return;
        }

        Message.Unblock.sendUnblockedMessage(sender, ps);
    }

}
