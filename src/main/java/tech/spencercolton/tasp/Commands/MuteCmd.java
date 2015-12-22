package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.M;

public class MuteCmd extends TASPCommand {

    private static final String permission = "tasp.mute";
    private static final String syntax = "/mute <player>";
    private static final String consoleSyntax = syntax;
    public static final String name = "mute";

    @Override
    public void execute(CommandSender sender, String... args) {
        switch(args.length) {
            case 1:
                Player p = Bukkit.getPlayer(args[0]);

                if(p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }


                Person b = Person.get(p);
                b.setMuted(!b.isMuted());

                this.sendMutedMessage(sender, b.isMuted(), b);

                break;
            default:
                Command.sendSyntaxError(sender, this);
                break;
        }
    }

    private void sendMutedMessage(CommandSender sender, boolean muted, Person p) {
        if(Command.messageEnabled("muted"))
            sender.sendMessage(M.m("command-message-text.muted-s", p.getPlayer().getDisplayName(), muted ? "muted" : "unmuted"));
        if(Command.messageEnabled("muted-r"))
            sender.sendMessage(M.m("command-message-text.muted-r", sender.getName(), muted ? "muted" : "unmuted"));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax()  {
        return consoleSyntax;
    }

}


