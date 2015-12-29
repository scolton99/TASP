package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static java.lang.Integer.*;
import static java.util.Collections.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.XP.*;

/**
 * @author Spencer Colton
 */
public class XPCmd extends TASPCommand {

    @Getter
    private final String syntax = "/xp <give|take|check> <player> [amount] OR /xp <player>";

    @Getter
    private static final String name = "xp";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.xp";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 3 || args.length < 1) {
            sendGenericSyntaxError(sender, this);
        }

        Integer amount = null;
        Player p = null;
        switch (args.length) {
            case 3: {
                try {
                    amount = parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 2: {
                p = getPlayer(args[1]);
                if (p == null) {
                    sendPlayerMessage(sender, args[1]);
                    return;
                }
            }
            case 1: {
                switch (args[0].toLowerCase()) {
                    case "give": {
                        if (p == null || amount == null) {
                            sendGenericSyntaxError(sender, this);
                            return;
                        }
                        p.giveExp(amount);
                        sendIncreasedMessage(sender, amount, p.getTotalExperience(), p);
                        return;
                    }
                    case "take": {
                        if (p == null || amount == null) {
                            sendGenericSyntaxError(sender, this);
                            return;
                        }
                        p.setTotalExperience(p.getTotalExperience() - amount);
                        sendDecreasedMessage(sender, amount, p.getTotalExperience(), p);
                        return;
                    }
                    case "check": {
                        if (p == null) {
                            sendGenericSyntaxError(sender, this);
                            return;
                        }
                        float amt = p.getTotalExperience();
                        sendXPMessage(sender, amt, p);
                        break;
                    }
                    default: {
                        p = getPlayer(args[0]);
                        if (p == null) {
                            sendPlayerMessage(sender, args[0]);
                            return;
                        }
                        sendXPMessage(sender, p.getTotalExperience(), p);
                    }
                }
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return singletonList("exp");
    }

}
