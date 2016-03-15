package tech.spencercolton.tasp.Storage;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.MailObj;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Spencer Colton
 */
public class JSONMailStorage implements MailStorageProvider {

    private static JSONObject mails;

    @SuppressWarnings("unchecked")
    public JSONMailStorage() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + File.separator + "mails.json");
        if (!f.exists()) {
            mails = new JSONObject();
            mails.put("mails", new ArrayList<>());
            writeMails();
        } else {
            try {
                FileReader a = new FileReader(f);
                mails = (JSONObject) new JSONParser().parse(a);
            } catch (IOException | ParseException e) {
                mails = new JSONObject();
                mails.put("mails", new ArrayList<>());
                writeMails();
                Bukkit.getLogger().warning("Couldn't load mails.");
                e.printStackTrace();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setRead(UUID uid) {
        List<Map<String, String>> mz = (ArrayList) mails.get("mails");

        for (Map<String, String> m : mz) {
            MailObj mo = MailObj.fromMap(m);

            if (mo == null)
                continue;

            if (mo.getId().equals(uid)) {
                mo.setRead(true);
                mz.remove(m);
                mz.add(mo.toMap());
                break;
            }
        }

        mails.put("mails", mz);
        return writeMails();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean delete(UUID uid) {
        List<Map<String, String>> mz = (ArrayList) mails.get("mails");

        for (Map<String, String> m : mz) {
            MailObj mo = MailObj.fromMap(m);

            if (mo == null)
                continue;

            if (mo.getId().equals(uid)) {
                mz.remove(m);
                break;
            }
        }

        mails.put("mails", mz);
        return writeMails();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean send(Person from, UUID to, String msg) {
        MailObj mo = new MailObj(from, to, msg);

        List m = (ArrayList) mails.get("mails");

        m.add(mo.toMap());
        mails.put("mails", m);
        return writeMails();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MailObj> fetch(Person to) {
        List<Map<String, String>> m = (ArrayList) mails.get("mails");

        List<MailObj> temp = new ArrayList<>();
        for(Map<String, String> x : m)
                temp.add(MailObj.fromMap(x));

        List<MailObj> mailz = temp.stream().filter(tz -> tz.getTo().equals(to.getUid())).collect(Collectors.toList());
        Collections.sort(mailz, (o1, o2) -> o1.getSent().compareTo(o2.getSent()));
        return mailz;
    }

    private static boolean writeMails() {
        try {
            FileWriter f = new FileWriter(new File(TASP.dataFolder().getAbsolutePath() + File.separator + "mails.json"));
            f.write(mails.toString());
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning(Config.err() + "Couldn't write mails.");
            return false;
        }
    }

}
