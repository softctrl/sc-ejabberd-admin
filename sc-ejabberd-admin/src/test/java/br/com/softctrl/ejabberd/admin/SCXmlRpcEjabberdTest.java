package br.com.softctrl.ejabberd.admin;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;

import com.google.gson.annotations.Expose;

import br.com.softctrl.ejabberd.admin.xmlrpc.SCXmlRpcEjabberd;
import br.com.softctrl.utils.json.GsonUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SCXmlRpcEjabberdTest extends TestCase {

    public static class Result {

        @Expose public int res;
        
        public boolean isOk(){
            return res == 0;
        }

    }

    private SCXmlRpcEjabberd client;

    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public SCXmlRpcEjabberdTest(String testName) {
        super(testName);
        // TODO: configure here for you ejabberd:
//        try {
// Use a real user and password.
//            client = new SCXmlRpcEjabberd("user01", "localhost", "123456",
//                    new URL("http://localhost:4560"));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(SCXmlRpcEjabberdTest.class);
    }

    public void testAddRosterItem() throws XmlRpcException {
        // TODO: uncomment here.
        //assertTrue(GsonUtils.fromJson(client.checkAccount("user2", "localhost"), Result.class).isOk());
        assertTrue(true);
    }
}
