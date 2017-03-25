package xxe;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SimpsonXmlReaderTest {

    private SimpsonXmlReader unsecuredXmlFileReader;
    private SimpsonXmlReader securedXmlFileReader;

    @Before
    public void init() {
        unsecuredXmlFileReader = new SimpsonXmlReader(false);
        securedXmlFileReader = new SimpsonXmlReader(true);
    }

    /**
     * Cases of unsecured xml parser. Used by default in frameworks and applications.
     */

    /**
     * Load normal staff.xml from class path.
     * Display nodes in xml for every staff.
     */
    @Test
    public void testNormalXmlLoad() {
        unsecuredXmlFileReader.loadXml("staff.xml");
    }

    /**
     * Scans file system (for ex /etc folder).
     */
    @Test
    public void testScanFileSystem() {
        unsecuredXmlFileReader.loadXml("staff_scan.xml");
    }

    /**
     * Load content of /etc/passwd and shows us.
     */
    @Test
    public void testReadFileContent() {
        unsecuredXmlFileReader.loadXml("staff_password.xml");
    }

    /**
     * Call localhost web server running on 8889
     * http://127.0.0.1:8889
     *
     * GET / HTTP/1.1
     * User-Agent: Java/1.8.0_92
     * Host: 127.0.0.1:8889
     * Accept: text/html, image/gif, image/jpeg, *; q=.2, **; q=.2
     * Connection: keep-alive
     */
    @Test
    public void testHttpGetCallLocalHost() {
        unsecuredXmlFileReader.loadXml("staff_localhttp.xml");
    }

    /**
     * Call to remote site. Get lohika.txt file with Java champions 2016 list
     * and inject its content in response.
     */
    @Test
    public void testHttpGetCallFileOnRemoteServer() {
        unsecuredXmlFileReader.loadXml("staff_site.xml");
    }

    /**
     * Loaded xml file with content < 150Kbyte in memory has more than 3.5Gb.
     */
    @Test (expected = OutOfMemoryError.class)
    @Ignore
    public void testXmlBomb() {
//        System.setProperty("jdk.xml.entityExpansionLimit", "0");
//        System.out.printf(System.getProperty("jdk.xml.entityExpansionLimit"));
        unsecuredXmlFileReader.loadXml("staff_xml_bomb.xml");
    }

    /**
     * Hijack the file content and send it to remote server in http GET REQUEST parameter.
     *
     * GET /load=QWERTY123 HTTP/1.1
     * User-Agent: Java/1.8.0_92
     * Host: 127.0.0.1:8888
     * Accept: text/html, image/gif, image/jpeg, *; q=.2, **; q=.2
     * Connection: keep-alive
     */
    @Test
    public void testDtdFileSendToRemoteServer() {
        unsecuredXmlFileReader.loadXml("staff_dtdcall.xml");
    }

    /**
     * Emulate the evil server in our local network. To a call to remote server.
     * netcat -l 0.0.0.0 8888
     */
    @Test
    public void testHttpGetToNetworkHost() {
        unsecuredXmlFileReader.loadXml("staff_network_dtd.xml");
    }

    /**
     * Show the cases with secured XML parser. org.xml.sax.SAXParseException will be thrown.
     */

    @Test
    public void testSecuredLoadLocalFile() {
        securedXmlFileReader.loadXml("staff.xml");
    }

    @Test
    public void testSecuredReadFileContent() {
        securedXmlFileReader.loadXml("staff_password.xml");
    }

    @Test
    public void testSecuredHttpGetCallLocalHost() {
        securedXmlFileReader.loadXml("staff_localhttp.xml");
    }

    @Test
    public void testSecuredHttpGetCallFileOnRemoteServer() {
        securedXmlFileReader.loadXml("staff_site.xml");
    }

    @Test
    public void testSecuredXmlBomb() {
        securedXmlFileReader.loadXml("staff_xml_bomb.xml");
    }

}