package test.java;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {
    @Test
    public void client(){
        try {
            Socket clientSocket = new Socket("localhost", 4444);
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Kiss my shiny metal ass!");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket sc = new ServerSocket(4444);
            while (true){
                Socket socket = sc.accept();
                InputStream inputStream = socket.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String message = in.readLine();
                String reverseMessage = new StringBuilder(message).reverse().toString();

                //отправляем ответ
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream, true);
                out.println(reverseMessage);
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
