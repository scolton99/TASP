package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingCmd extends TASPCommand {

    public static final String name = "ping";
    public static final String syntax = "/ping";
    public static final String consoleSyntax = null;
    public static final String permission = "tasp.ping";

    @Override
    public void execute(CommandSender sender, String[] args) {
        String x = System.getProperty("os.name");
        String ip = ((Player) sender).getAddress().getHostString();
        long s = System.currentTimeMillis();
        if(x.contains("Windows")) {
            try {
                Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 " + ip);
                p1.waitFor();
                long time = System.currentTimeMillis() - s - 7;
                if(time < 0)
                    time = 0;
                sendPingMessage(sender, time);
            } catch(IOException|InterruptedException e) {
                sender.sendMessage(Config.err() + "An error occured while checking your ping.");
            }
        } else if(x.contains("Linux")) {
            boolean res = true;
            try {
                if (!InetAddress.getByName(ip).isReachable(2000))
                    res = false;
                if (res) {
                    long time = System.currentTimeMillis() - s;
                    sendPingMessage(sender, time);
                } else {
                    sender.sendMessage(Config.err() + "An error occured while checking your ping.");
                }
            } catch (IOException e) {
                sender.sendMessage(Config.err() + "An error occured while checking your ping.");
            }
        } else if(x.contains("Mac OS X")) {

        } else {
            sender.sendMessage(Config.err() + "An error occured while checking your ping.");
        }
    }

    private void sendPingMessage(CommandSender sender, long ping) {
        sender.sendMessage(M.m("command-message-text.ping", Long.toString(ping)));
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean predictOthers(CommandSender sender, String[] s) {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
