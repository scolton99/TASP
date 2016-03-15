package tech.spencercolton.tasp.Util;

import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Storage.DatabaseMailStorage;
import tech.spencercolton.tasp.Storage.JSONMailStorage;
import tech.spencercolton.tasp.Storage.MailStorageProvider;

import java.util.List;
import java.util.UUID;

public class Mail {

    private static MailStorageProvider mails;

    @SuppressWarnings("unchecked")
    public static void initMail() {
        if (Config.configDatabase())
            mails = new DatabaseMailStorage();
        else
            mails = new JSONMailStorage();
    }

    public static boolean send(Person from, UUID to, String msg) {
        return mails.send(from, to, msg);
    }

    public static boolean setRead(UUID uid) {
        return mails.setRead(uid);
    }

    public static boolean delete(UUID uid) {
        return mails.delete(uid);
    }

    @SuppressWarnings("unchecked")
    public static List<MailObj> fetch(Person to) {
        return mails.fetch(to);
    }



}
