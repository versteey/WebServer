import java.io.*;
import java.net.Socket;

/**
 * Created by eversteeg on 2-12-2014.
 */
public class WebApi implements WebApplication {
    private Socket socket;
    private String regex = "_";

    public WebApi(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void process(RequestMessage request, ResponseMessage response) {
        if (request.getResourcePath().endsWith("style.css")) {
            getStyleSheet(response);
        }
        else if (request.getResourcePath().equals("index.html") ||
                request.getResourcePath().equals("") && request.getHeaderParameterValue("isbn").equals("")) {
            createHTMLPage(response, readFile(new File("html/index.html")));
        }
        else {
            String isbn = request.getHeaderParameterValue("isbn");
            loadISBN(request, response, isbn);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(response.writeHeader(request));
            writer.write("\n" + response.getContent());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadISBN(RequestMessage request, ResponseMessage response, String isbn) {
        try {
            String result = readFile(new File("html/" + isbn + ".html"));
            if(result == null) throw new Exception();
            result += createFooter(request, response, isbn);
            createHTMLPage(response, result);
        }
        catch (Exception e) {
            loadFailToProvideISBN(response);
        }
    }

    public void loadFailToProvideISBN(ResponseMessage response) {
        String result = readFile(new File("html/fail.html"));
        if(result != null){
            response.setStatus(HTTPStatusCode.NotFound);
            response.setContent(result);
        }
        else response.setStatus(HTTPStatusCode.ServerError);
    }

    private String createFooter(RequestMessage request, ResponseMessage response, String isbn) {
        StringBuilder result = new StringBuilder();
        result.append("<div id=footer><ul>");
        Cookie cookie = request.getCookie();
        if(cookie == null) {
            response.addCookie(new Cookie("isbn", isbn));
            result.append("<li><a href=\"/?isbn=" + isbn + "\"> Book" + isbn + "</a></li>");
        }
        else {
            StringBuilder newCookieValue = new StringBuilder();
            for (String s : cookie.getValue().split(regex)) {
                if(!s.equals(isbn)) newCookieValue.append(regex + s);
            }
            String cookieValue = newCookieValue.toString()+ regex + isbn;
            if (cookieValue.startsWith(regex)) {
                cookieValue = cookieValue.substring(regex.length());
            }
            response.addCookie(new Cookie("isbn", cookieValue));

            String[] s = cookieValue.split(regex);
            for (int i = s.length - 1; i>=0 && i > s.length - 6; i--) {
                if (!s[i].equals("")) result.append("<li><a href=\"/?isbn=" + s[i] + "\"> Book" + s[i] + "</a></li>");
            }
        }
        result.append("</ul></div>");
        return result.toString();
    }

    private void createHTMLPage(ResponseMessage response, String content) {
        String result = readFile(new File("html/TopPage.html"));
        result += content;
        result += readFile(new File("html/BottomPage.html"));
        response.setStatus(HTTPStatusCode.OK);
        response.setContent(result);
    }

    private void getStyleSheet(ResponseMessage response) {
        String result = readFile(new File("style.css"));
        response.setStatus(HTTPStatusCode.OK);
        response.setContent(result);
    }

    private String readFile(File toRead) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(toRead));
            StringBuilder result = new StringBuilder();
            String add = "";
            while (add != null) {
                result.append(add);
                add = br.readLine();
            }
            return result.toString();
        }
        catch (Exception e) {
            return null;
        }
    }
}
