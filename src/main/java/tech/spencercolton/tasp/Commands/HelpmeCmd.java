package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonHelpmeEvent;
import tech.spencercolton.tasp.TASP;

/**
 * @author Spencer Colton
 */
public class HelpmeCmd extends TASPCommand {

    @Getter
    private final String syntax = "/helpme <message>";

    @Getter
    public static final String name = "helpme";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.helpme";

    public HelpmeCmd() {
        TASP.setHelpMeRcvPrivilege(this.permission + ".receive");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert sender instanceof ConsoleCommandSender;

        String msg = Command.combineArgs(args);
        Bukkit.getPluginManager().callEvent(new PersonHelpmeEvent(Person.get((Player)sender), msg));
    }

}
