import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicServer {

    public static void main(String []args) {
        try {
            ServerSocket ss = new ServerSocket(9999);//9999

            while(true) {

                System.out.println("Aguardando conexão...");
                Socket cli = ss.accept();
                BasicServerConnection conn = new BasicServerConnection(cli);
                conn.start();
                System.out.println("Cliente conectado...");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
