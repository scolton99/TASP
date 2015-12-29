package tech.spencercolton.tasp.Commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.ColorChat;

import java.util.List;
import java.util.Set;

/**
 * @author Spencer Colton
 */
public class SignCmd extends TASPCommand {

    @Getter
    private final String syntax = "/sign \"<line 1>\" \"<line 2>\" \"<line 3>\" \"<line 4>\"";

    @Getter
    public static final String name = "sign";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.signedit";

    @Override
    public void execute(CommandSender sender, String[] argsg) {
        assert sender instanceof Player;

        List<String> args = Command.processQuotedArguments(argsg);

        if(args.size() < 1 || args.size() > 4) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Block b = ((Player)sender).getTargetBlock((Set<Material>) null, 10000);
        if(b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {
            Sign s = (Sign)b.getState();

            for(int i = 0; i < args.size(); i++) {
                s.setLine(i, ColorChat.color(args.get(i)));
            }
        } else {
            // TODO Add not sign message here
        }
    }

}
