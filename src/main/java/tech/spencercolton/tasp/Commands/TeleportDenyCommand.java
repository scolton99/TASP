package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportDenyCommand extends TASPCommand {

    private static final String syntax = "/tpd";
    public static final String name = "tpd";
    private static final String permission = "tasp.teleport.request.respond";
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        Person p = Person.get((Player)sender);
        if(p.getLastTeleportRequester() == null) {
            p.getPlayer().sendMessage(Config.err() + "You have no existing teleport requests.");
            return;
        }

        Person pa = p.getLastTeleportRequester();
        p.clearTeleportRequests();

        sendTeleportDenyMessage(pa.getPlayer(), p.getPlayer());
    }

    private void sendTeleportDenyMessage(Player requester, Player requestee) {
        requester.sendMessage(Config.c1() + requestee.getDisplayName() + Config.c1() + " denied your teleport request.");
        requestee.sendMessage(Config.c1() + "You denied " + requester.getDisplayName() + Config.c1() + "'s teleport request.");
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
    public List<String> getAliases() {
        return Collections.singletonList("tpdeny");
    }

}
