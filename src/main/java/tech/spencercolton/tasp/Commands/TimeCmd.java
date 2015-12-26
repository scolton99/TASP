package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import tech.spencercolton.tasp.Util.Message;
import tech.spencercolton.tasp.Util.Time;

public class TimeCmd extends TASPCommand {

    public static final String name = "time";

    @Getter
    private static final String permission = "tasp.time";

    @Getter
    private static final String syntax = "/time [time | world [time]]";

    @Getter
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(args.length > 2) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        World w = null;
        Long spigotTime = null;
        switch(args.length) {
            case 2: {
                try {
                    spigotTime = Time.timeToBukkitTime(args[1]);
                    if (spigotTime == null) {
                        Message.Time.Error.sendInvalidFormatMessage(sender);
                        return;
                    }
                } catch (NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 1: {
                w = Bukkit.getWorld(args[0]);
                if (w == null) {
                    if (sender instanceof ConsoleCommandSender) {
                        w = Bukkit.getWorlds().get(0);
                        assert w != null;
                    } else {
                        assert sender instanceof Entity;
                        w = ((Entity) sender).getWorld();
                    }
                    try {
                        spigotTime = Time.timeToBukkitTime(args[0]);
                        if (spigotTime == null) {
                            Message.Time.Error.sendInvalidFormatMessage(sender);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        Command.sendGenericSyntaxError(sender, this);
                        return;
                    }
                }
            }
            case 0: {
                assert w != null;
                if (spigotTime == null) {
                    long bTime = w.getTime();
                    Message.Time.sendTimeMessage(sender, Time.niceFormatTime(bTime), Long.toString(bTime), w.getName());
                    return;
                } else {
                    w.setTime(spigotTime);
                    Message.Time.sendTimeSetMessage(sender, Time.niceFormatTime(spigotTime), Long.toString(spigotTime), w.getName());
                    return;
                }
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

}
