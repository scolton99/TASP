package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.PlayerInventory;
import tech.spencercolton.tasp.TASP;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class InvspyCmd extends TASPCommand {

    @Getter
    private final String syntax = "/invspy <player>";

    @Getter
    public static final String name = "invspy";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.invspy";

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert sender instanceof Player;

        if (args.length != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player) sender;
        Player o = Bukkit.getPlayer(args[0]);

        if (o == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        PlayerInventory i = o.getInventory();

        p.openInventory(i);

        if(!p.hasPermission(permission + ".edit")) {
            TASP.getOpenImmutableInventories().add(i);
        }
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("invsee");
    }

}
