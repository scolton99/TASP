package tech.spencercolton.tasp.Storage;

import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.MailObj;

import java.util.List;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public interface MailStorageProvider {

    boolean delete(UUID uid);
    boolean setRead(UUID uid);
    boolean send(Person from, UUID to, String msg);
    List<MailObj> fetch(Person to);

}
