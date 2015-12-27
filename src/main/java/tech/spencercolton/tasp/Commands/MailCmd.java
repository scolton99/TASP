package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Math.min;
import static java.util.Calendar.*;
import static java.util.UUID.fromString;
import static org.bukkit.ChatColor.BOLD;
import static tech.spencercolton.tasp.Commands.Command.sendPlayerMessage;
import static tech.spencercolton.tasp.Commands.Command.sendSyntaxError;
import static tech.spencercolton.tasp.Entity.Person.*;
import static tech.spencercolton.tasp.Util.ChatFilter.filter;
import static tech.spencercolton.tasp.Util.ColorChat.color;
import static tech.spencercolton.tasp.Util.Config.*;

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
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sendSyntaxError(sender, this);
            return;
        }

        switch (args[0]) {
            case "send":
                if (args.length < 3) {
                    sendSyntaxError(sender, this);
                    return;
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
                    Mail.send(get((Player) sender), personExists(args[1]), msg);
                    Message.Mail.sendSentMessage(sender, args[1]);
                    return;
                } else {
                    sendPlayerMessage(sender, args[1]);
                    return;
                }
            case "check":
                List<Map<String, String>> mailz = Mail.fetch(get((Player) sender));
                int multi = 0;
                if (args.length == 2) {
                    multi = parseInt(args[1]) - 1;
                }
                sender.sendMessage(c1() + " * Mail for " + ((Player) sender).getDisplayName() + c1() + " * ");
                if (mailz.isEmpty()) {
                    sender.sendMessage(c3() + " * You have no mail.");
                    return;
                }
                for (int i = 0; i < min(5, mailz.size() - multi * 5); i++) {
                    Map<String, String> z = mailz.get(i + multi * 5);
                    String from = getMostRecentName(fromString(z.get("from"))) == null ? z.get("currname") : getMostRecentName(fromString(z.get("from")));
                    String add = z.get("message").length() > 20 ? "..." : "";
                    String truncMessage = z.get("message").substring(0, min(20, z.get("message").length())) + add;
                    String date = z.get("sent");
                    boolean b = parseBoolean(z.get("read"));
                    try {
                        DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        Date a = df.parse(date);
                        Calendar c = getInstance();
                        c.setTime(a);
                        String day = Integer.toString(c.get(DATE));
                        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        String month = months[c.get(MONTH)];
                        String hour = Integer.toString(c.get(HOUR_OF_DAY));
                        String minute = c.get(MINUTE) < 10 ? "0" + Integer.toString(c.get(MINUTE)) : Integer.toString(c.get(MINUTE));
                        sender.sendMessage(c2() + " " + Integer.toString(1 + i + multi * 5) + " " + c3() + month + " " + day + " " + hour + ":" + minute + " " + c1() + from + " " + c4() + (b ? "" : BOLD) + truncMessage);
                    } catch (ParseException ignored) {

                    }
                }
                return;
            case "read":
                if (args.length != 2) {
                    sendSyntaxError(sender, this);
                    return;
                }

                int g;
                try {
                    g = parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sendSyntaxError(sender, this);
                    return;
                }
                g--;
                List<Map<String, String>> mails = Mail.fetch(get((Player) sender));
                if (g < 0 || g >= mails.size()) {
                    Message.Mail.Error.sendMailNotFoundMessage(sender);
                    return;
                }
                Map<String, String> mel = mails.get(g);
                try {
                    DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Date a = df.parse(mel.get("sent"));
                    Calendar c = getInstance();
                    c.setTime(a);
                    String day = Integer.toString(c.get(DATE));
                    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                    String month = months[c.get(MONTH)];
                    String hour = Integer.toString(c.get(HOUR_OF_DAY));
                    String minute = c.get(MINUTE) < 10 ? "0" + Integer.toString(c.get(MINUTE)) : Integer.toString(c.get(MINUTE));
                    String year = Integer.toString(c.get(YEAR));
                    String mostRecentName = getMostRecentName(fromString(mel.get("from"))) == null ? mel.get("currname") : getMostRecentName(fromString(mel.get("from")));
                    sender.sendMessage(c1() + " * ----------------------------- * ");
                    sender.sendMessage(c1() + " * Number: " + c2() + Integer.toString(g + 1));
                    sender.sendMessage(c1() + " * From: " + c2() + mostRecentName);
                    sender.sendMessage(c1() + " * Sent: " + c2() + day + " " + month + " " + year + " " + hour + ":" + minute);
                    sender.sendMessage(c1() + " * Message: " + c3() + mel.get("message"));
                    sender.sendMessage(c1() + " * ----------------------------- * ");
                    Mail.setRead(mel);
                } catch (ParseException e) {
                    sender.sendMessage(err() + "There was a problem fetching your mail.  Try again later.");
                    return;
                }
                return;
            case "delete":
                if (args.length != 2) {
                    sendSyntaxError(sender, this);
                    return;
                }

                int g2;
                try {
                    g2 = parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sendSyntaxError(sender, this);
                    return;
                }
                g2--;
                List<Map<String, String>> mails2 = Mail.fetch(get((Player) sender));
                if (g2 < 0 || g2 >= mails2.size()) {
                    Message.Mail.Error.sendMailNotFoundMessage(sender);
                    return;
                }
                Map<String, String> m1 = mails2.get(g2);
                Mail.delete(m1);
                Message.Mail.sendDeletedMessage(sender);
                break;
            default:
                sendSyntaxError(sender, this);
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        if (args.length >= 1) {
            switch (args[0]) {
                case "send":
                    return permission + ".send";
                case "check":
                    return permission + ".read";
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
