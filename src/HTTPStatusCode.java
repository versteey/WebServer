/**
 * Created by eversteeg on 1-12-2014.
 */
public enum HTTPStatusCode {
    OK(200, "OK"),
    NotFound(404, "Not Found"),
    ServerError(500, "Server Error");

    private int code;
    private String description;

    private HTTPStatusCode(int code, String description) {
        this.code = code;
        this.description = code + ", " + description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
