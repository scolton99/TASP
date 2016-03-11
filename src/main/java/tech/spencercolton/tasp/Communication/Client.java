package tech.spencercolton.tasp.Communication;

import tech.spencercolton.tasp.Util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author Spencer Colton
 */
public class Client extends Thread {

    private Socket s;

    private Logger l;

    private PrintWriter out;
    private BufferedReader in;

    String input;

    private boolean actionEnabled = false, waiting = true;

    private List<tech.spencercolton.tasp.Communication.Message> queue = new ArrayList<>();

    public Client(Logger l) {
        this.l = l;

        try (Socket z = new Socket(Config.getString("router-host"), Config.getInt("router-port"));
             PrintWriter x = new PrintWriter(s.getOutputStream(), true);
             BufferedReader y = new BufferedReader(new InputStreamReader(s.getInputStream()))
        ) {
            this.s = z;
            this.out = x;
            this.in = y;
            this.start();
        } catch (IOException e) {
            l.severe("Unable to open connection to TASPRouter at " + Config.getString("router-host") + ":" + Config.getInt("router-port"));
        }
    }

    @Override
    public void run() {
        try {
            while ((input = in.readLine()) != null) {
                waiting = false;

                switch (input) {
                    case "PWD": {
                        send_pwd();
                        break;
                    }
                    case "PMM": {
                        l.warning("Password mismatch.  Retrying.");
                        send_pwd();
                        break;
                    }
                    case "NIC": {
                        send_nic();
                        break;
                    }
                    case "PWA": {
                        this.actionEnabled = true;
                        break;
                    }
                    case "REQ": {
                        rcv_req();
                        break;
                    }
                    case "RES": {
                        rcv_res();
                        break;
                    }
                }

                for(tech.spencercolton.tasp.Communication.Message m : queue) {
                    m.getParts().forEach(out::println);
                    queue.remove(m);
                }

                waiting = true;
            }
        } catch (IOException e) {
            l.warning("Error reading from socket.");
        }
    }

    private void send_pwd() {
        if (!input.equals("PWD") && !input.equals("PMM"))
            return;

        out.println(Config.getString("router-password"));
    }

    private void rcv_req() {
        try {
            if (!input.equals("REQ"))
                return;

            List<String> msg = new ArrayList<>();
            msg.add("REQ");

            UUID u = UUID.fromString(in.readLine());
            msg.add(u.toString());

            String temp;
            while (!(temp = in.readLine()).equals("TERM"))
                msg.add(temp);

            msg.add("TERM");

            Request r = new Request(new Message(msg), this);
            RequestHandler.handle(r);
        } catch (IOException e) {

        }
    }

    private void rcv_res() {
        try {
            if(!input.equals("RES"))
                return;

            List<String> msg = new ArrayList<>();
            msg.add("RES");

            UUID u = UUID.fromString(in.readLine());
            msg.add(u.toString());

            String temp;
            while (!(temp = in.readLine()).equals("TERM"))
                msg.add(temp);

            msg.add("TERM");

            InternalRequest ir = InternalRequest.getByUid(u);

            if(ir == null)
                return;

            ir.resolve(new Message(msg));
        } catch (IOException e) {

        }
    }

    private void send_nic() {
        if(!input.equals("NIC"))
            return;

        String nick = Config.getString("router-client-name");

        out.println(nick);

        return;
    }

    public void queueMessage(tech.spencercolton.tasp.Communication.Message m) {
        if (this.waiting)
            m.getParts().forEach(g -> this.out.println(g));
        else
            this.queue.add(m);
    }

}
