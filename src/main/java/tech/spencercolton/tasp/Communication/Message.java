package tech.spencercolton.tasp.Communication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Spencer Colton
 */
public class Message {

    private final List<String> parts;

    public Message(MessageResponseType m, UUID u, List<String> components) {
        this.parts = new ArrayList<>();
        List<String> prefixes = new ArrayList<>();

        prefixes.add("RES");

        switch (m) {
            case SUCCESS: {
                prefixes.add(u.toString());
            }
            case FAILURE: {
                prefixes.add(u.toString());
                prefixes.add("ERR");
            }
            case NA: {
                prefixes.add(u.toString());
                prefixes.add("RNA");
            }
        }

        prefixes.add(u.toString());

        this.parts.addAll(prefixes);
        this.parts.addAll(components);
        this.parts.add("TERM");
    }

    public Message(List<String> components) {
        this.parts = components;
    }

    public Stream<String> getParts() {
        return parts.stream();
    }

    public List<String> getPartsAsList() {
        return parts;
    }

}
