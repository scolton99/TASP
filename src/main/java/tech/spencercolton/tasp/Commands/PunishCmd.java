package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.PunishManager;
import tech.spencercolton.tasp.Util.Punishment;

/**
 * @author Spencer Colton
 */
public class PunishCmd extends TASPCommand {

    @Getter
    private final String syntax = "/punish <player> <reason>";

    @Getter
    public static final String name = "punish";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.punish";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            Command.sendGenericSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player pa = Bukkit.getPlayer(args[0]);
        if (pa == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return CommandResponse.PLAYER;
        }

        Person p = Person.get(pa);
        assert p != null;

        Punishment punishment = PunishManager.getDefault(args[1]);

        if (punishment == null) {

        }

        return CommandResponse.SUCCESS;
    }

}
