package application.client;

import application.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
    User user;
    Socket client;
    DataOutputStream out;

    public Client(String serverName,int port, User user) throws IOException {
        this.user = user;
        client = new Socket(serverName, port);
        out = new DataOutputStream(client.getOutputStream());
    }

    public void requestCreateLobby(){
        try {
            out.writeUTF("Create Lobby");
        }
        catch (Exception e) {

        }
    }
}
