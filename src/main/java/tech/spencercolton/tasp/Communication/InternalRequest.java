package tech.spencercolton.tasp.Communication;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class InternalRequest {

    @Getter
    private final UUID uid;

    @Getter
    private static List<InternalRequest> requests = new ArrayList<>();

    @Getter
    private String dest;

    @Getter
    private Message message;

    @Getter
    private CommandSender invoker;

    public InternalRequest(Message m, String destination) {
        this.uid = UUID.randomUUID();
        this.dest = destination;
        this.message = m;
    }

    public InternalRequest(Message m, String destination, CommandSender sender) {
        this.uid = UUID.randomUUID();
        this.dest = destination;
        this.message = m;
        this.invoker = sender;
    }

    public static InternalRequest getByUid(UUID u) {
        for(InternalRequest r : requests)
            if (r.getUid().equals(u))
                return r;
        return null;
    }

    public void resolve(Message m) {
        if (invoker == null)
            return;

        List<String> parts = new ArrayList<>(m.getPartsAsList());

        if(parts.get(2).equals("ERR")) {
            invoker.sendMessage(Config.err() + "Sorry, the network encountered an error while processing your command.");
            invoker.sendMessage(Config.err() + "Most likely, if your command involved a player, that player isn't on the network.");
            requests.remove(this);
            return;
        } else if (parts.get(2).equals("RNA")) {
            invoker.sendMessage(Config.err() + "Unable to find that player.");
            requests.remove(this);
            return;
        }

        parts.remove(parts.size() - 1);
        parts.remove(0);
        parts.remove(1);

        invoker.sendMessage(parts.toArray(new String[parts.size()]));
    }

}
