package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonPunishEvent;
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

        Punishment punishment = PunishManager.getDefault(args[1]);

        if (punishment == null) {
            Command.sendGenericSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Bukkit.getPluginManager().callEvent(new PersonPunishEvent(sender, pa, punishment));

        return CommandResponse.SUCCESS;
    }

}
