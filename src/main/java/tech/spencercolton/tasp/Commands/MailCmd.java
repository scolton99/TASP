package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Mail;
import tech.spencercolton.tasp.Util.MailObj;
import tech.spencercolton.tasp.Util.Message;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.*;
import static java.lang.Math.min;
import static java.util.Calendar.*;
import static org.bukkit.ChatColor.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Configuration.Config.*;
import static tech.spencercolton.tasp.Entity.Person.*;
import static tech.spencercolton.tasp.Util.ChatFilter.*;
import static tech.spencercolton.tasp.Util.ColorChat.*;

/**
 * @author Spencer Colton
 */
public class MailCmd extends TASPCommand {

    @Getter
    private final String syntax = "/mail <option> [options]";

    @Getter
    private static final String name = "mail";

    @Getter
    private final String permission = "tasp.mail";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        switch (args[0]) {
            case "send": {
                if (args.length < 3) {
                    sendSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }

                if (personExists(args[1]) != null) {
                    String msg = "";
                    for (int i = 2; i < args.length; i++) {
                        msg += args[i];
                        if (!((i + 1) >= args.length))
                            msg += " ";
                    }
                    msg = color(msg);
                    msg = filter(msg);
                    if (Mail.send(get((Player) sender), personExists(args[1]), msg)) {
                        Message.Mail.sendSentMessage(sender, args[1]);
                        return CommandResponse.SUCCESS;
                    } else {
                        Command.sendStorageMessage(sender);
                        return CommandResponse.FAILURE;
                    }
                } else {
                    sendPlayerMessage(sender, args[1]);
                    return CommandResponse.PLAYER;
                }
            }
            case "check": {
                List<MailObj> mailz = Mail.fetch(get((Player) sender));
                int multi = 0;
                if (args.length == 2) {
                    multi = parseInt(args[1]) - 1;
                }
                sender.sendMessage(c1() + " * Mail for " + ((Player) sender).getDisplayName() + c1() + " * ");
                if (mailz.isEmpty()) {
                    sender.sendMessage(c3() + " * You have no mail.");
                    return CommandResponse.SUCCESS;
                }
                for (int i = 0; i < min(5, mailz.size() - multi * 5); i++) {
                    MailObj z = mailz.get(i + multi * 5);

                    String mostRecentName = getMostRecentName(z.getFrom());

                    String from = mostRecentName == null ? z.getCurrname() : mostRecentName;
                    String add = z.getMessage().length() > 20 ? "..." : "";
                    String truncMessage = z.getMessage().substring(0, min(20, z.getMessage().length())) + add;
                    Date date = z.getSent();
                    boolean b = z.isRead();
                    Calendar c = getInstance();
                    c.setTime(date);
                    String day = Integer.toString(c.get(DATE));
                    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                    String month = months[c.get(MONTH)];
                    String hour = Integer.toString(c.get(HOUR_OF_DAY));
                    String minute = c.get(MINUTE) < 10 ? "0" + Integer.toString(c.get(MINUTE)) : Integer.toString(c.get(MINUTE));
                    sender.sendMessage(c2() + " " + Integer.toString(1 + i + multi * 5) + " " + c3() + month + " " + day + " " + hour + ":" + minute + " " + c1() + from + " " + c4() + (b ? "" : BOLD) + truncMessage);
                }
                return CommandResponse.SUCCESS;
            }
            case "read": {
                if (args.length != 2) {
                    sendSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }

                int g;
                try {
                    g = parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sendSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }
                g--;

                List<MailObj> mails = Mail.fetch(get((Player) sender));
                if (g < 0 || g >= mails.size()) {
                    Message.Mail.Error.sendMailNotFoundMessage(sender);
                    return CommandResponse.FAILURE;
                }
                MailObj mel = mails.get(g);
                Date a = mel.getSent();
                Calendar c = getInstance();
                c.setTime(a);
                String day = Integer.toString(c.get(DATE));
                String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                String month = months[c.get(MONTH)];
                String hour = Integer.toString(c.get(HOUR_OF_DAY));
                String minute = c.get(MINUTE) < 10 ? "0" + Integer.toString(c.get(MINUTE)) : Integer.toString(c.get(MINUTE));
                String year = Integer.toString(c.get(YEAR));
                String mostRecentName = getMostRecentName(mel.getId()) == null ? mel.getCurrname() : getMostRecentName(mel.getFrom());
                sender.sendMessage(c1() + " * ----------------------------- * ");
                sender.sendMessage(c1() + " * Number: " + c2() + Integer.toString(g + 1));
                sender.sendMessage(c1() + " * From: " + c2() + mostRecentName);
                sender.sendMessage(c1() + " * Sent: " + c2() + day + " " + month + " " + year + " " + hour + ":" + minute);
                sender.sendMessage(c1() + " * Message: " + c3() + mel.getMessage());
                sender.sendMessage(c1() + " * ----------------------------- * ");
                return Mail.setRead(mel.getId()) ? CommandResponse.SUCCESS : CommandResponse.FAILURE;
            }
            case "delete": {
                if (args.length != 2) {
                    sendSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }

                int g2;
                try {
                    g2 = parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sendSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }
                g2--;
                List<MailObj> mails2 = Mail.fetch(get((Player) sender));
                if (g2 < 0 || g2 >= mails2.size()) {
                    Message.Mail.Error.sendMailNotFoundMessage(sender);
                    return CommandResponse.FAILURE;
                }
                MailObj m1 = mails2.get(g2);
                if (Mail.delete(m1.getId())) {
                    Message.Mail.sendDeletedMessage(sender);
                    return CommandResponse.SUCCESS;
                } else {
                    Command.sendStorageMessage(sender);
                    return CommandResponse.FAILURE;
                }
            }
            default: {
                sendSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        if (args.length >= 1) {
            switch (args[0]) {
                case "send":
                    return permission + ".send";
                case "check":
                    return permission + ".check";
                case "read":
                    return permission + ".read";
                case "delete":
                    return permission + ".delete";
                default:
                    return permission;
            }
        } else {
            return permission;
        }
    }

}
