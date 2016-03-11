package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.spencercolton.tasp.Util.Message;

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
    public CommandResponse execute(CommandSender sender, String... argsg) {
        assert sender instanceof Player;

        List<String> args = Command.processQuotedArguments(argsg);

        if(args.size() < 1 || args.size() > 2) {
            Command.sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = (Player)sender;
        ItemStack i = p.getItemInHand();
        Integer level = null;
        Enchantment e;
        switch(args.size()) {
            case 2: {
                try {
                    level = Integer.parseInt(args.get(1));
                    if(level > 10 || level <= 0) {
                        Message.Enchant.Error.sendLevelOOBMessage(sender);
                        return CommandResponse.FAILURE;
                    }
                } catch (NumberFormatException g) {
                    Command.sendSyntaxError(sender, this);
                }
            }
            case 1: {
                e = Enchantment.getByName(args.get(0).toUpperCase().replace(' ', '_'));
                if(e == null) {
                    Message.Enchant.Error.sendEnchantmentNotFoundMessage(sender, args.get(0));
                    return CommandResponse.FAILURE;
                }

                if(level == null)
                    level = e.getStartLevel();

                i.addUnsafeEnchantment(e, level);
                p.setItemInHand(i);

                Message.Enchant.sendEnchantedMessage(sender, e.getName().toLowerCase().replace('_', ' '), level, i.getType().toString().toLowerCase());
                return CommandResponse.SUCCESS;
            }
            default: {
                Command.sendSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

}
