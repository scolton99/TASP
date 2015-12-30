package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static tech.spencercolton.tasp.Util.Message.XYZ.*;

public class XYZCmd extends TASPCommand {

    @Getter
    private final String syntax = "/xyz";

    @Getter
    private static final String name = "xyz";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.xyz";

    @Override
    public void execute(CommandSender sender, String... args) {
        assert !(sender instanceof ConsoleCommandSender);

        Player p = (Player) sender;
        sendPosMessage(sender, p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
    }

}
