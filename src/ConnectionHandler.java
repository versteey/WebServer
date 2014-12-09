import java.io.*;
import java.net.Socket;

/**
 * Created by eversteeg on 1-12-2014.
 */
public class ConnectionHandler implements Runnable {

    private Socket socket;

    public ConnectionHandler(Socket toHandle) {
        this.socket = toHandle;
    }

    public void run() {
        try {
            RequestMessage request = new RequestMessage(socket);
            ResponseMessage response = new ResponseMessage();

            if (request.getResourcePath().endsWith(".png")) {
                new ImageFileHandler(socket).process(request, response);
            }
            else {
                new WebApi(socket).process(request, response);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
