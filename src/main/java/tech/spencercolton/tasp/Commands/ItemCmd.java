package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.spencercolton.tasp.Enums.IDs;

import java.util.List;

/**
 * @author Spencer Colton
 */
public class ItemCmd extends TASPCommand {

    @Getter
    private final String syntax = "/i <item>";

    @Getter
    private static final String name = "i";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.give";

    @Override
    public void execute(CommandSender sender, String[] argsg) {
        assert !(sender instanceof ConsoleCommandSender);

        List<String> args = Command.processQuotedArguments(argsg);

        Player p = (Player)sender;
        Integer amount = 64;
        Short damage;
        switch(args.size()) {
            case 2: {
                try {
                    amount = Integer.parseInt(args.get(1));
                } catch (NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            }
            case 1: {
                IDs a = IDs.getByName(args.get(0));
                if(a == null) {
                    a = IDs.getByIdDamage(args.get(0));
                    if(a == null) {
                        Command.sendItemNotFoundMessage(sender);
                        return;
                    }
                }
                damage = a.getDamage();
                ItemStack b = new ItemStack(a.getMaterial(), amount, damage);
                p.getInventory().addItem(b);
            }
            default: {
                Command.sendSyntaxError(sender, this);
            }
        }
    }

}
