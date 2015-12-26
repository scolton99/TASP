package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class HoldingCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/holding";

    public static final String name = "holding";

    @Getter
    private static final String permission = "tasp.holding";

    @Getter
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);
        ItemStack i = ((Player)sender).getItemInHand();
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("itemdb");
    }

}
