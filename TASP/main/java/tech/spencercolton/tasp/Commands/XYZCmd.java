package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

public class XYZCmd extends TASPCommand {

    public static final String syntax = "/xyz";
    public static final String name = "xyz";
    public static final String consoleSyntax = null;
    public static final String permission = "tasp.xyz";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender);
            return;
        }

        Player p = (Player)sender;
        sendPosMessage(sender, p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
    }

    private void sendPosMessage(CommandSender sender, int x, int y, int z) {
        sender.sendMessage(M.m("command-message-text.xyz", Integer.toString(x), Integer.toString(y), Integer.toString(z)));
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getName() {
        return name;
    }

}
