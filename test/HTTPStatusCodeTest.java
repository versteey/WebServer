import junit.framework.TestCase;

public class HTTPStatusCodeTest extends TestCase {
    public void test_Status_Not_Found() {
        HTTPStatusCode status = HTTPStatusCode.NotFound;
        assertEquals(404, status.getCode());
        assertEquals("404, Not Found" , status.getDescription());
    }

    public void test_Status_Ok() {
        HTTPStatusCode status = HTTPStatusCode.OK;
        assertEquals(200, status.getCode());
        assertEquals("200, OK" , status.getDescription());
    }

    public void test_Status_Server_Error() {
        HTTPStatusCode status = HTTPStatusCode.ServerError;
        assertEquals(500, status.getCode());
        assertEquals("500, Server Error" , status.getDescription());
    }

}