import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by eversteeg on 1-12-2014.
 */
public class Main {

    public static void main(String[] args){
        ServerSocket socket;
        try {
            socket = new ServerSocket(9090);

            while (true) {
                Socket newConnection = socket.accept();
                Thread thread = new Thread(new ConnectionHandler(newConnection));
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
