package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.*;
import static tech.spencercolton.tasp.Util.Message.Mute.*;

public class MuteCmd extends TASPCommand {

    @Getter
    private final String permission = "tasp.mute";

    @Getter
    private final String syntax = "/mute <player>";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private static final String name = "mute";

    @Override
    public CommandResponse execute(CommandSender sender, String... args) {
        switch (args.length) {
            case 1: {
                Player p = getPlayer(args[0]);

                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return CommandResponse.PLAYER;
                }

                Person b = get(p);
                b.setMuted(!b.isMuted());

                sendMutedMessage(sender, b.isMuted(), b.getPlayer());
                return CommandResponse.SUCCESS;
            }
            default: {
                sendSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

}


