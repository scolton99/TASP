package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;
import tech.spencercolton.tasp.Util.Config;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportAcceptCmd extends TASPCommand {

    private static final String syntax = "/tpa";
    public static final String name = "tpa";
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

        PersonTeleportEvent e = (p.isLastTeleportRequestHere() ? new PersonTeleportEvent(p.getPlayer(), pa.getPlayer(), true, false) : new PersonTeleportEvent(pa.getPlayer(), p.getPlayer(), false, false));
        Bukkit.getServer().getPluginManager().callEvent(e);
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
        return Collections.singletonList("tpaccept");
    }

}
