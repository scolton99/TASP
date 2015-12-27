package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import java.util.List;

import static java.util.Collections.singletonList;
import static tech.spencercolton.tasp.Commands.Command.combineArgs;
import static tech.spencercolton.tasp.Commands.Command.sendConsoleError;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Util.Message.Powertool.sendPowertoolEnabledMessage;
import static tech.spencercolton.tasp.Util.Message.Powertool.sendRemovedPowertoolsMessage;

/**
 * @author Spencer Colton
 */
public class PowertoolCmd extends TASPCommand {

    @Getter
    private final String syntax = "/powertool [command line]";

    @Getter
    private static final String name = "powertool";

    @Getter
    private final String permission = "tasp.powertool";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String... args) {
        if (sender instanceof ConsoleCommandSender) {
            sendConsoleError(sender);
            return;
        }

        Person p = get((Player) sender);
        Material m = p.getPlayer().getItemInHand().getType();

        switch (args.length) {
            case 0: {
                p.clearPowertool(m);
                sendRemovedPowertoolsMessage(sender, m);
                break;
            }
            default: {
                String cmdLine = combineArgs(args);
                p.setPowertool(m, cmdLine);
                sendPowertoolEnabledMessage(sender, m, cmdLine);
                break;
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return singletonList("pt");
    }

}
