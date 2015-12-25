package tech.spencercolton.tasp.Commands;

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

    private static final String syntax = "/holding";
    public static final String name = "holding";
    private static final String permission = "tasp.holding";
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);
        ItemStack i = ((Player)sender).getItemInHand();

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
        return Collections.singletonList("itemdb");
    }

}
