import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eversteeg on 1-12-2014.
 */
public class RequestMessage implements Request {
    private HTTPMethod hTTPMethod;
    private String resourcePath;
    private String HTTP_Type;

    public ArrayList<String[]> parameters =  new ArrayList<>();
    public ArrayList<String[]> extraParameters =  new ArrayList<>();

    public RequestMessage(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            this.processLineOne(line);
            System.out.println();

            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
                this.processing(line);
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void processLineOne(String toProcess) {
        String[] process = toProcess.split(" ");

        if (process[0].startsWith("GET")) hTTPMethod = HTTPMethod.GET;
        else hTTPMethod = HTTPMethod.POST;

        String[] param = process[1].split("\\?");
        resourcePath = param[0].substring(1);
        if(param.length > 1) {
            String[] input = param[1].split("&");
            for (String s : input) {
                extraProcessing(s);
            }
        }

        HTTP_Type = process[2];
    }

    public void processing(String toProcess) {
        String[] result;
        result = toProcess.split("(: )");
        if(result.length > 1) parameters.add(result);
    }

    public void extraProcessing(String toProcess) {
        String[] result;
        result = toProcess.split("(=)");
        if(result.length > 1) extraParameters.add(result);
    }

    @Override
    public HTTPMethod getHTTPMethod() {
        return hTTPMethod;
    }

    @Override
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public List<String> getHeaderParameterNames() {
        List<String> result = new ArrayList<>();
        for( String[] s : extraParameters){
            result.add(s[0]);
        }
        return result;
    }

    @Override
    public String getHeaderParameterValue(String name) {
        String result = "";
        for( String[] s : extraParameters){
            if(s[0].equals(name)) {
                result = s[1];
                break;
            }
        }
        return result;
    }

    @Override
    public List<Cookie> getCookies() {
        List<Cookie> cookies = new ArrayList<>();
        String[] toProcess = this.parameters.get(parameters.size()-1)[1].split("(; )");
        for(String s : toProcess) {
            if(s.contains("=") && !s.endsWith("=")){
                cookies.add(new Cookie(s));
            }
        }
        return cookies;
    }

    public Cookie getCookie() {
        String[] toProcess = this.parameters.get(parameters.size()-1)[1].split("(; )");
        for(String s : toProcess) {
            if(s.startsWith("isbn")){
                return new Cookie(s);
            }
        }
        return null;
    }

    public String getHTTP_Type() {
        return HTTP_Type;
    }
}
