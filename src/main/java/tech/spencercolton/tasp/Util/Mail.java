package tech.spencercolton.tasp.Util;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Mail {

    private static JSONObject mails;

    @SuppressWarnings("unchecked")
    public static void initMail() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + File.separator + "mails.json");
        if(!f.exists()) {
            mails = new JSONObject();
            mails.put("mails", new ArrayList<>());
            writeMails();
        } else {
            try {
                FileReader a = new FileReader(f);
                mails = (JSONObject)new JSONParser().parse(a);
            } catch(IOException|ParseException e) {
                mails = new JSONObject();
                mails.put("mails", new ArrayList<>());
                writeMails();
                Bukkit.getLogger().warning("Couldn't load mails.");
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void send(Person from, UUID to, String msg) {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        List m = (ArrayList)mails.get("mails");
        Map mail = new HashMap<>();
        mails.put("id", UUID.randomUUID().toString());
        mail.put("sent", df.format(Calendar.getInstance().getTime()));
        mail.put("currname", from.getPlayer().getName());
        mail.put("from", from.getUid().toString());
        mail.put("to", to.toString());
        mail.put("message", msg);
        mail.put("read", "false");
        m.add(mail);
        mails.put("mails", m);
        writeMails();
    }

    @SuppressWarnings("unchecked")
    public static void setRead(Map<String,String> mail) {
        List<Map<String,String>> mz = (ArrayList)mails.get("mails");
        mz.remove(mail);
        mail.put("read", "true");
        mz.add(mail);
        mails.put("mails", mz);
        writeMails();
    }

    @SuppressWarnings("unchecked")
    public static void delete(Map<String,String> mail) {
        List<Map<String,String>> mz = (ArrayList)mails.get("mails");
        mz.remove(mail);
        mails.put("mails", mz);
        writeMails();
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String,String>> fetch(Person to) {
        String uid = to.getUid().toString();
        List<Map<String,String>> m = (ArrayList)mails.get("mails");
        List<Map<String,String>> mailz = new ArrayList<>();
        for(Map g : m) {
            if(g.get("to").equals(uid)) {
                mailz.add(g);
            }
        }
        Collections.sort(mailz, new Comparator<Map<String,String>>() {
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            @Override
            public int compare(Map<String,String> o1, Map<String,String> o2) {
                try {
                    Date a = df.parse(o1.get("sent"));
                    Date b = df.parse(o2.get("sent"));
                    return (int)(b.getTime() - a.getTime());
                } catch(java.text.ParseException e) {
                    return 0;
                }
            }
        });
        return mailz;
    }

    private static void writeMails() {
        try {
            FileWriter f = new FileWriter(new File(TASP.dataFolder().getAbsolutePath() + File.separator + "mails.json"));
            f.write(mails.toString());
            f.close();
        } catch(IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning(Config.err() + "Couldn't write mails.");
        }
    }

}
