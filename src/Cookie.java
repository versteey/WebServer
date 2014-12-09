//import java.util.UUID;

/**
 * Created by eversteeg on 3-12-2014.
 */
public class Cookie implements CookieInterface {
    private String name;
    private String value;
    //private UUID uniqueKey;

    public Cookie(String name, String value) {
        setName(name);
        setValue(value);
    }

    public Cookie(String value) {
        String[] makeCookie = value.split("=");

        this.name = makeCookie[0];
        this.value = makeCookie[1];
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        //uniqueKey = UUID.randomUUID();
        //this.name = name+uniqueKey.toString();
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
