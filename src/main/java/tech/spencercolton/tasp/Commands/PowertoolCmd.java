package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Message;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class PowertoolCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/powertool [command line]";

    public static final String name = "powertool";

    @Getter
    private static final String permission = "tasp.powertool";

    @Getter
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError(sender);
            return;
        }

        Person p = Person.get((Player)sender);
        Material m = p.getPlayer().getItemInHand().getType();

        switch(args.length) {
            case 0: {
                p.clearPowertool(m);
                Message.Powertool.sendRemovedPowertoolsMessage(sender, m);
                break;
            }
            default: {
                String cmdLine = Command.combineArgs(args);
                p.setPowertool(m, cmdLine);
                Message.Powertool.sendPowertoolEnabledMessage(sender, m, cmdLine);
                break;
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("pt");
    }

}
