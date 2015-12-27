package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static java.util.Collections.singletonList;

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
        @SuppressWarnings("UnusedAssignment") ItemStack i = ((Player) sender).getItemInHand();
    }

    @Override
    public List<String> getAliases() {
        return singletonList("itemdb");
    }

}
