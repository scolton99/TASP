package tech.spencercolton.tasp.Util;

import lombok.Getter;
import lombok.Setter;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class MailObj {

    @Getter
    private final UUID id;

    @Getter
    private final Timestamp sent;

    @Getter
    private final String currname;

    @Getter
    private final UUID from;

    @Getter
    private final UUID to;

    @Getter
    private String message;

    @Getter
    @Setter
    private boolean read;

    public MailObj(Person from, UUID to, String msg) {
        this.id = UUID.randomUUID();
        this.sent = new Timestamp(System.currentTimeMillis());
        this.currname = from.getPlayer().getName();
        this.from = from.getUid();
        this.to = to;
        this.message = msg;
        this.setRead(false);
    }

    public MailObj(UUID id, Timestamp sent, String currname, UUID from, UUID to, String message, boolean read) {
        this.id = id;
        this.sent = sent;
        this.currname = currname;
        this.to = to;
        this.from = from;
        this.message = message;
        this.setRead(read);
    }

    public static MailObj fromMap(Map<String, String> map) {
        return new MailObj(UUID.fromString(map.get("id")), TASP.parseDate(map.get("sent")), map.get("curname"), UUID.fromString(map.get("from")), UUID.fromString(map.get("to")), map.get("message"), Boolean.parseBoolean(map.get("read")));
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();

        map.put("id", this.getId().toString());
        map.put("sent", TASP.formatDate(this.getSent()));
        map.put("currname", this.getCurrname());
        map.put("from", this.getFrom().toString());
        map.put("to", this.getTo().toString());
        map.put("msg", this.getMessage());
        map.put("read", Boolean.toString(this.isRead()));

        return map;
    }

    public static MailObj fromRS(ResultSet rs) {
        try {
            return new MailObj(UUID.fromString(rs.getString("uid")), rs.getTimestamp("sent"), rs.getString("currname"), UUID.fromString(rs.getString("from")), UUID.fromString(rs.getString("to")), rs.getString("message"), rs.getBoolean("read"));
        } catch (SQLException e) {
            return null;
        }
    }

}
