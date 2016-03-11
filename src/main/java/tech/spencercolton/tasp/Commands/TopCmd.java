package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import static org.bukkit.Bukkit.*;
import static org.bukkit.Material.*;
import static tech.spencercolton.tasp.Entity.Person.*;
import static tech.spencercolton.tasp.TASP.*;

public class TopCmd extends TASPCommand {

    @Getter
    private static final String name = "top";

    @Getter
    private final String syntax = "/top";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.top";

    @Override
    public CommandResponse execute(CommandSender sender, String... args) {
        assert !(sender instanceof ConsoleCommandSender);

        Player p = (Player) sender;
        Location l = p.getLocation();
        int x = l.getBlockX();
        int z = l.getBlockZ();
        World w = p.getWorld();
        for (int y = WORLD_HEIGHT; y >= 0; y--) {
            Block b = w.getBlockAt(x, y, z);
            if (b.getType() != AIR) {
                getPluginManager().callEvent(new PersonTeleportEvent(get(p), new Location(w, l.getX(), b.getY() + 1, l.getZ(), l.getYaw(), l.getPitch())));
                break;
            }
        }
        return CommandResponse.SUCCESS;
    }

}
