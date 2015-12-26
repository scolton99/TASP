package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 * @author Spencer Colton
 */
public class ItemCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/i <item>";

    public static final String name = "i";

    @Getter
    private static final String consoleSyntax = null;

    @Getter
    private static final String permission = "tasp.give";

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);
    }

}
