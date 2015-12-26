package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Message;

public class MuteCmd extends TASPCommand {

    @Getter
    private static final String permission = "tasp.mute";

    @Getter
    private static final String syntax = "/mute <player>";

    @Getter
    private static final String consoleSyntax = syntax;

    public static final String name = "mute";

    @Override
    public void execute(CommandSender sender, String... args) {
        switch(args.length) {
            case 1: {
                Player p = Bukkit.getPlayer(args[0]);

                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }

                Person b = Person.get(p);
                b.setMuted(!b.isMuted());

                Message.Mute.sendMutedMessage(sender, b.isMuted(), b.getPlayer());
                return;
            }
            default: {
                Command.sendSyntaxError(sender, this);
            }
        }
    }

}


