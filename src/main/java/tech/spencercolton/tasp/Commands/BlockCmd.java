package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.*;
import static tech.spencercolton.tasp.Util.Message.Block.Error.*;
import static tech.spencercolton.tasp.Util.Message.Block.*;

public class BlockCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    @Getter
    private static final String name = "block";


    /**
     * String containing the command's syntax.
     */
    @Getter
    private final String syntax = "/block <user>";


    /**
     * String containing the command's console syntax.
     */
    @Getter
    private final String consoleSyntax = "/block <user>";

    @Getter
    private final String permission = "tasp.block";

    @Override
    public CommandResponse execute(CommandSender sender, String... args) {
        if (sender instanceof ConsoleCommandSender) {
            sendConsoleError(sender);
            return CommandResponse.FAILURE;
        }

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

        if (ps.equals(pa)) {
            sendSelfMessage(sender);
            return CommandResponse.FAILURE;
        }

        if (!pa.isPlayerBlocked(ps))
            pa.blockPlayer(ps);
        else {
            sendAlreadyBlockedMessage(sender, ps.getPlayer());
            return CommandResponse.FAILURE;
        }

        sendBlockedMessage(sender, ps.getPlayer());
        return CommandResponse.SUCCESS;
    }

}
