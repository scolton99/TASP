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
        String now = new Date().toString();
        List m = (ArrayList)mails.get("mails");
        Map mail = new HashMap<>();
        mail.put("sent", now);
        mail.put("from", from.getUid().toString());
        mail.put("to", to.toString());
        mail.put("message", msg);
        m.add(mail);
        mails.put("mails", m);
        writeMails();
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
