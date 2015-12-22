package tech.spencercolton.tasp.Commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;

public class TopCmd extends TASPCommand {

    public static final String name = "top";
    private static final String syntax = "/top";
    private static final String consoleSyntax = null;
    private static final String permission = "tasp.top";
    private static final int WORLD_HEIGHT = 256;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender);
            return;
        }

        Player p = (Player)sender;
        Location l = p.getLocation();
        int x = l.getBlockX();
        int z = l.getBlockZ();
        World w = p.getWorld();
        for(int y = WORLD_HEIGHT; y >= 0; y--) {
            Block b = w.getBlockAt(x, y, z);
            if(b.getType() != Material.AIR) {
                p.teleport(new Location(w, l.getX(), b.getY() + 1, l.getZ(), l.getYaw(), l.getPitch()));
                this.sendTopMessage(sender, b.getY() + 1);
                return;
            }
        }
    }

    private void sendTopMessage(CommandSender sender, int y) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.top", Integer.toString(y)));
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

}
