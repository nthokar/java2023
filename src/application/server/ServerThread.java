package application.server;

import java.io.*;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class ServerThread implements Runnable {
    private final Socket client;
    private Date lastMessage;
    private Instant lastResponse;
    private final int deadlineSeconds = 10;
    public ServerThread(Socket client){
        this.client = client;
        var temp = new Date(System.currentTimeMillis());
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

//            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
//            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            lastResponse = Instant.now();


            while(!client.isClosed() && !isOutOfDeadline()) {
                var response = in.readLine();
                if (Objects.isNull(response)){
                    continue;
                }
                lastResponse = Instant.now();

                if (response.equalsIgnoreCase("close")) {
                    out.write("bye bye...\n");
                    break;
                }
                if (response.equalsIgnoreCase("create lobby")){
                    out.write("lobby almost created\n");
                }
                out.flush();
            }
            in.close();
            out.close();

            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isOutOfDeadline(){
        return Instant.now().isAfter(lastResponse.plusSeconds(deadlineSeconds));
    }
}
