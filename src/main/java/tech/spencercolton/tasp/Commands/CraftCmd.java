package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class CraftCmd extends TASPCommand {

    @Getter
    private final String syntax = "/craft";

    @Getter
    public static final String name = "craft";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.craft";

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert sender instanceof Player;

        Player p = (Player) sender;
        p.openWorkbench(null, true);
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("workbench", "crafting");
    }

}
