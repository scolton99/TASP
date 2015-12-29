package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.spencercolton.tasp.Enums.IDs;
import tech.spencercolton.tasp.Util.Message;

import java.util.List;

import static java.util.Collections.*;

/**
 * @author Spencer Colton
 */
public class HoldingCmd extends TASPCommand {

    @Getter
    private final String syntax = "/holding";

    @Getter
    private static final String name = "holding";

    @Getter
    private final String permission = "tasp.holding";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);
        ItemStack i = ((Player) sender).getItemInHand();
        Material m = i.getType();

        IDs item = IDs.getByMaterial(m);

        assert item != null;

        String id = Integer.toString(item.getId());
        String damage = Integer.toString(item.getDamage());
        String names = Command.listToCSV(item.getNames());

        Message.Holding.sendHoldingMessage(sender, id, damage, names);
    }

    @Override
    public List<String> getAliases() {
        return singletonList("itemdb");
    }

}
