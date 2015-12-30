package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Spencer Colton
 */
public class EnchantCmd extends TASPCommand {

    @Getter
    private final String syntax = "/enchant <enchantment> [level]";

    @Getter
    public static final String name = "enchant";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.enchant";

    @Override
    public void execute(CommandSender sender, String... argsg) {
        assert sender instanceof Player;

        List<String> args = Command.processQuotedArguments(argsg);

        if(args.size() < 1 || args.size() > 2) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player)sender;
        ItemStack i = p.getItemInHand();
        Integer level = null;
        Enchantment e = null;
        switch(args.size()) {
            case 2: {
                try {
                    level = Integer.parseInt(args.get(1));
                    if(level > 10 || level <= 0) {
                        // TODO Alert player that the level must be between 1 and 10
                        return;
                    }
                } catch (NumberFormatException g) {
                    Command.sendSyntaxError(sender, this);
                }
            }
            case 1: {
                e = Enchantment.getByName(args.get(0).toUpperCase().replace(' ', '_'));
                if(e == null) {
                    // TODO Alert player that the enchantment doesn't exist
                    Bukkit.broadcastMessage("NO ENCHANTMENT");
                    return;
                }

                if(level == null)
                    level = e.getStartLevel();

                i.addUnsafeEnchantment(e, level);
                p.setItemInHand(i);
                // TODO Add message to this command
            }
            default: {
                Command.sendSyntaxError(sender, this);
            }
        }
    }

}
