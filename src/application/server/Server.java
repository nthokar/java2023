package application.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    private ServerSocket serverSocket;
    ExecutorService executeIt = Executors.newFixedThreadPool(2);
    private List<ServerThread> clients;
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            port = 8000;
        }
        try {
            Server server = new Server(port);
            server.run();
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
    public void run(){
        try {
            while (!serverSocket.isClosed()) {

                Socket client = serverSocket.accept();

                executeIt.execute(new ServerThread(client));
            }
            //accepts all clients
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
