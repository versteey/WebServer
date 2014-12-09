import java.util.Calendar;

/**
 * Created by eversteeg on 1-12-2014.
 */
public interface Response {
    HTTPStatusCode getStatus();
    void setStatus(HTTPStatusCode status);
    Calendar getDate();
    String getContent();
    void setContent(String content);
    String toString();
    void addCookie(Cookie cookie);
}
