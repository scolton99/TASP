package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

public class XYZCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/xyz";

    public static final String name = "xyz";

    @Getter
    private static final String consoleSyntax = null;

    @Getter
    private static final String permission = "tasp.xyz";

    @Override
    public void execute(CommandSender sender, String... args) {
        assert !(sender instanceof ConsoleCommandSender);

        Player p = (Player)sender;
        Message.XYZ.sendPosMessage(sender, p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
    }

}
