package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Warp;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class WarpsCmd extends TASPCommand {

    @Getter
    private final String syntax = "/warps [page]";

    @Getter
    public static final String name = "warps";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.warp.list";

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<Map<String,String>> warps = Warp.getAllWarps();

        int start = 0;
        if(args.length == 1) {
            try {
                start = Integer.parseInt(args[0]);
                start--;
            } catch(NumberFormatException e) {
                Command.sendSyntaxError(sender, this);
                return;
            }
        }

        for(int i = start * 5; i < Math.min((5 + 5 * start), warps.size()); i++) {
            Map<String, String> mp = warps.get(i);
            sender.sendMessage(Config.c1() + " * " + Config.c2() + mp.get("name") + Config.c1() + " - " + Config.c2() + Bukkit.getWorld(UUID.fromString(mp.get("world"))).getName() + Config.c1() + " * ");
        }
    }

}
