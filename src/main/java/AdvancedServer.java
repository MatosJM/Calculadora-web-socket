import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AdvancedServer {

    public static void main(String []args) {
        try {
            ServerSocket ss = new ServerSocket(9997);//9999

            while(true) {

                System.out.println("Aguardando conexão...");
                Socket cli = ss.accept();
                AdvancedServerConnection conn = new AdvancedServerConnection(cli);
                conn.start();
                System.out.println("Cliente conectado...");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
