import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RequestMessageTest extends TestCase {
    public void test_processLineOne() {
        RequestMessage rm = new RequestMessage(null);
        rm.processLineOne("GET /home HTTP/1.1");
        assertEquals(HTTPMethod.GET, rm.getHTTPMethod());
        assertEquals("home", rm.getResourcePath());
        assertEquals("HTTP/1.1", rm.getHTTP_Type());
    }

    public void test_prosessing() {
        RequestMessage rm = new RequestMessage(null);
        rm.processing("Host: localhost:9090");
        assertEquals("Host", rm.parameters.get(0)[0] );
        assertEquals("localhost:9090", rm.parameters.get(0)[1] );
    }

    public void test_extra_prosessing() {
        RequestMessage rm = new RequestMessage(null);
        rm.extraProcessing("Host=localhost:9090");
        assertEquals("Host", rm.extraParameters.get(0)[0] );
        assertEquals("localhost:9090", rm.extraParameters.get(0)[1] );
    }

    public void test_get_Parameter_Value() {
        RequestMessage rm = new RequestMessage(null);
        rm.extraProcessing("Host=localhost:9090");
        rm.extraProcessing("BlaBla=9090");
        assertEquals("9090", rm.getHeaderParameterValue("BlaBla") );
    }

    public void test_get_Parameter_Names() {
        RequestMessage rm = new RequestMessage(null);
        rm.extraProcessing("Host=localhost:9090");
        rm.extraProcessing("BlaBla=9090");
        List<String> result = new ArrayList<>();
        result.add("Host");
        result.add("BlaBla");
        assertEquals(result, rm.getHeaderParameterNames());
    }
}