package tech.spencercolton.tasp.Communication;

import tech.spencercolton.tasp.Commands.CommandResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Spencer Colton
 */
public abstract class RequestHandler {

    public static void handle(Request r) {
        TASPCommandSender sender = new TASPCommandSender();

        List<String> args = new ArrayList<>(r.getCause().getPartsAsList());

        args.remove(args.size() - 1);
        args.remove(0);
        args.remove(1);
        args.remove(2);

        CommandResponse res = r.getType().getLogic().execute(sender, args.toArray(new String[args.size()]));

        switch (res) {
            case SUCCESS: {
                r.respond(new Message(MessageResponseType.SUCCESS, r.getUid(), sender.getOutput()));
                break;
            }
            case WORLD:
            case FAILURE: {
                r.respond(new Message(MessageResponseType.FAILURE, r.getUid(), sender.getOutput()));
            }
            case PLAYER: {
                r.na();
                break;
            }
        }
    }

}
