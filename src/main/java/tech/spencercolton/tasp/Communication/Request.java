package tech.spencercolton.tasp.Communication;

import lombok.Getter;

import java.util.Collections;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class Request {

    @Getter
    private final UUID uid;
    @Getter
    private RequestStatus status;

    @Getter
    private RequestType type;

    @Getter
    private Message cause;

    private Client source;

    public Request(Message cause, Client c) {
        this.uid = UUID.fromString(cause.getPartsAsList().get(1));
        this.status = RequestStatus.WAITING;
        this.type = RequestType.valueOf(cause.getPartsAsList().get(2));
        this.source = c;
    }

    public void respond(Message m) {
        this.source.queueMessage(m);
    }

    public void na() {
        this.source.queueMessage(new Message(MessageResponseType.NA, this.getUid(), Collections.emptyList()));
    }

}
