package tech.spencercolton.tasp.Commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.M;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class PowertoolCmd extends TASPCommand {

    private static final String syntax = "/powertool [command line]";
    public static final String name = "powertool";
    private static final String permission = "tasp.powertool";
    private static final String consoleSyntax = null;

    private static boolean powertoolsEnabled = true;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender);
            return;
        }

        Player p = (Player)sender;
        Material m = p.getItemInHand().getType();

        switch(args.length) {
            case 0:
                Person.get(p).clearPowertool(m);
                this.sendRemovedPowertoolsMessage(sender, m);
                break;
            default:
                String cmdLine = "";
                List<String> strs = Arrays.asList(args);
                for(int i = 0; i < strs.size(); i++) {
                    cmdLine += strs.get(i);
                    if(!(i + 1 >= strs.size()))
                        cmdLine += " ";
                }
                Person.get(p).setPowertool(m, cmdLine);
                this.sendPowertoolEnabledMessage(sender, m, cmdLine);
                break;
        }
    }

    private void sendRemovedPowertoolsMessage(CommandSender sender, Material m) {
        if(Command.messageEnabled("powertool-remove"))
            sender.sendMessage(M.m("command-message-text.powertool-remove", m.toString().toLowerCase().replace("_", " ")));

    }

    private void sendPowertoolEnabledMessage(CommandSender sender, Material m, String cmdLine) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.powertool", m.toString().toLowerCase().replace("_", " "), cmdLine));
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
        return Collections.singletonList("pt");
    }

    public static boolean powertoolsEnabled() {
        return powertoolsEnabled;
    }

    public static boolean togglePowertools() {
        powertoolsEnabled = !powertoolsEnabled;
        return powertoolsEnabled;
    }

}
