package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;
import tech.spencercolton.tasp.TASP;

public class TopCmd extends TASPCommand {

    public static final String name = "top";

    @Getter
    private static final String syntax = "/top";

    @Getter
    private static final String consoleSyntax = null;

    @Getter
    private static final String permission = "tasp.top";

    @Override
    public void execute(CommandSender sender, String... args) {
        assert !(sender instanceof ConsoleCommandSender);

        Player p = (Player)sender;
        Location l = p.getLocation();
        int x = l.getBlockX();
        int z = l.getBlockZ();
        World w = p.getWorld();
        for(int y = TASP.WORLD_HEIGHT; y >= 0; y--) {
            Block b = w.getBlockAt(x, y, z);
            if(b.getType() != Material.AIR) {
                Bukkit.getPluginManager().callEvent(new PersonTeleportEvent(Person.get(p), new Location(w, l.getX(), b.getY() + 1, l.getZ(), l.getYaw(), l.getPitch())));
                return;
            }
        }
    }

}
