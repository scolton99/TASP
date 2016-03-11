package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Time.Error.*;
import static tech.spencercolton.tasp.Util.Message.Time.*;
import static tech.spencercolton.tasp.Util.Time.*;

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
    public CommandResponse execute(CommandSender sender, String... args) {
        if (args.length > 2) {
            sendGenericSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        World w = null;
        Long spigotTime = null;
        String pDate = null;
        switch (args.length) {
            case 2: {
                try {
                    spigotTime = timeToBukkitTime(args[1]);
                    pDate = args[1];
                    if (spigotTime == null) {
                        sendInvalidFormatMessage(sender);
                        return CommandResponse.FAILURE;
                    }
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
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
                        pDate = args[0];
                        if (spigotTime == null) {
                            sendInvalidFormatMessage(sender);
                            return CommandResponse.FAILURE;
                        }
                    } catch (NumberFormatException e) {
                        sendGenericSyntaxError(sender, this);
                        return CommandResponse.SYNTAX;
                    }
                }
            }
            case 0: {
                assert w != null;
                if (spigotTime == null) {
                    long bTime = w.getTime();
                    sendTimeMessage(sender, niceFormatTime(bTime), Long.toString(bTime), w.getName());
                    return CommandResponse.SUCCESS;
                } else {
                    w.setTime(spigotTime);
                    sendTimeSetMessage(sender, prettyPlayerDate(pDate), Long.toString(spigotTime), w.getName());
                    return CommandResponse.SUCCESS;
                }
            }
            default: {
                sendGenericSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length >= 1 && Bukkit.getWorld(args[0]) == null) ? permission + ".set" : permission;
    }

}
