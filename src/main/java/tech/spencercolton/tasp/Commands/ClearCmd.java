package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.spencercolton.tasp.Util.Message;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class ClearCmd extends TASPCommand {

    @Getter
    private final String syntax = "/clear [player]";

    @Getter
    public static final String name = "clear";

    @Getter
    private final String consoleSyntax = "/clear <player>";

    @Getter
    private final String permission = "tasp.clear";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (args.length != 1 && sender instanceof ConsoleCommandSender) {
            Command.sendConsoleSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = null;
        switch (args.length) {
            case 1: {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return CommandResponse.PLAYER;
                }
            }
            case 0: {
                if (p == null) {
                    assert sender instanceof Player;
                    p = (Player) sender;
                }
                ItemStack[] items = p.getInventory().getContents();
                int count = 0;
                for (ItemStack i : items) {
                    if(i != null)
                        count += i.getAmount();
                }
                p.getInventory().clear();
                Message.Clear.sendClearMessage(sender, count, p);
                return CommandResponse.SUCCESS;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("ci");
    }

}
