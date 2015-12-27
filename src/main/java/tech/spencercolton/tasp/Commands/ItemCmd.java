package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

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
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);
    }

}
