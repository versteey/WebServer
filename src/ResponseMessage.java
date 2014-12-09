import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by eversteeg on 1-12-2014.
 */
public class ResponseMessage implements Response {
    private Calendar calendar = new GregorianCalendar(Locale.ROOT);
    private HTTPStatusCode httpStatusCode;
    private Cookie cookie;
    private String content;

    @Override
    public HTTPStatusCode getStatus() {
        return httpStatusCode;
    }

    @Override
    public void setStatus(HTTPStatusCode status) {
        httpStatusCode = status;
    }

    @Override
    public Calendar getDate() {
        return calendar;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void addCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public String writeHeader(RequestMessage request) {
        String result = request.getHTTP_Type() + " " + this.getStatus().getDescription();
        result += "\nDate: " + this.getDate().getTime().toString();
        result += "\nCache-Control: no-cache";
        result += "\nContent-Length: " + this.getContent().getBytes().length;
        for ( String[] s : request.parameters) {
            result += "\n" + s[0] + ": " + s[1];
        }
        if(cookie != null) result += "\nSet-Cookie: "+ cookie.getName() + "=" + cookie.getValue();
        result += "\n";

        System.err.println("Header");
        System.err.println(result);
        return result;
    }
}
