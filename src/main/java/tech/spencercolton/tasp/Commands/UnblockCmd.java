package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.*;
import static tech.spencercolton.tasp.Util.Message.Unblock.Error.*;
import static tech.spencercolton.tasp.Util.Message.Unblock.*;

public class UnblockCmd extends TASPCommand {

    @Getter
    private static final String name = "unblock";

    @Getter
    private final String syntax = "/unblock <player>";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.block";

    @Override
    public CommandResponse execute(CommandSender sender, String... args) {
        assert !(sender instanceof ConsoleCommandSender);

        if (args.length != 1) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = getPlayer(args[0]);
        if (p == null) {
            sendPlayerMessage(sender, args[0]);
            return CommandResponse.PLAYER;
        }

        Person ps = get(p);
        Person pa = get((Player) sender);

        if (pa.isPlayerBlocked(ps))
            pa.unblockPlayer(ps);
        else {
            sendNotBlockedMessage(sender, ps);
            return CommandResponse.FAILURE;
        }

        sendUnblockedMessage(sender, ps);
        return CommandResponse.SUCCESS;
    }

}
