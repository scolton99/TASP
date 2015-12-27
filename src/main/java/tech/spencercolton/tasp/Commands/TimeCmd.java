package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;

import static org.bukkit.Bukkit.getWorld;
import static org.bukkit.Bukkit.getWorlds;
import static tech.spencercolton.tasp.Commands.Command.sendGenericSyntaxError;
import static tech.spencercolton.tasp.Util.Message.Time.Error.sendInvalidFormatMessage;
import static tech.spencercolton.tasp.Util.Message.Time.sendTimeMessage;
import static tech.spencercolton.tasp.Util.Message.Time.sendTimeSetMessage;
import static tech.spencercolton.tasp.Util.Time.niceFormatTime;
import static tech.spencercolton.tasp.Util.Time.timeToBukkitTime;

public class TimeCmd extends TASPCommand {

    @Getter
    private static final String name = "time";

    @Getter
    private final String permission = "tasp.time";

    @Getter
    private final String syntax = "/time [time | world [time]]";

    @Getter
    private final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if (args.length > 2) {
            sendGenericSyntaxError(sender, this);
            return;
        }

        World w = null;
        Long spigotTime = null;
        switch (args.length) {
            case 2: {
                try {
                    spigotTime = timeToBukkitTime(args[1]);
                    if (spigotTime == null) {
                        sendInvalidFormatMessage(sender);
                        return;
                    }
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 1: {
                w = getWorld(args[0]);
                if (w == null) {
                    if (sender instanceof ConsoleCommandSender) {
                        w = getWorlds().get(0);
                        assert w != null;
                    } else {
                        assert sender instanceof Entity;
                        w = ((Entity) sender).getWorld();
                    }
                    try {
                        spigotTime = timeToBukkitTime(args[0]);
                        if (spigotTime == null) {
                            sendInvalidFormatMessage(sender);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        sendGenericSyntaxError(sender, this);
                        return;
                    }
                }
            }
            case 0: {
                assert w != null;
                if (spigotTime == null) {
                    long bTime = w.getTime();
                    sendTimeMessage(sender, niceFormatTime(bTime), Long.toString(bTime), w.getName());
                    return;
                } else {
                    w.setTime(spigotTime);
                    sendTimeSetMessage(sender, niceFormatTime(spigotTime), Long.toString(spigotTime), w.getName());
                    return;
                }
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

}
