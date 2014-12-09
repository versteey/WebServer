import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by eversteeg on 4-12-2014.
 */
public class ImageFileHandler implements WebApplication {
    private Socket socket;

    public ImageFileHandler(Socket sock){
        socket = sock;
    }

    @Override
    public void process(RequestMessage request, ResponseMessage response) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedImage img = ImageIO.read(new File(request.getResourcePath()));
            ImageIO.write(img, "PNG", dos);
            dos.flush();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
