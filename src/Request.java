import java.util.List;

/**
 * Created by eversteeg on 1-12-2014.
 */
public interface Request {
    HTTPMethod getHTTPMethod();
    String getResourcePath();
    List<String> getHeaderParameterNames();
    String getHeaderParameterValue(String name);
    List<Cookie> getCookies();
}
