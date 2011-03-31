package resttwit;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Command line instance of RESTful client.  Includes some sleeping, since 
 * Twitter blocks more than 150 requests an hour.
 * @author bm3719@gmail.com
 */
public class RESTClient {
    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(
                "http://twitter.com/statuses/public_timeline.xml");
        // Prevent cookie rejection scroll.
        // http://hc.apache.org/httpclient-3.x/cookies.html
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

        String responseBody;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        LinkedList<Long> IDList = new LinkedList<Long>();

        while (true) {
            try {
                int statusCode = client.executeMethod(method);
                if (statusCode == HttpStatus.SC_OK) {
                    responseBody = method.getResponseBodyAsString();

                    // Create XML document object from string.
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource(new StringReader(
                            responseBody));
                    Document doc = db.parse(is);
                    doc.getDocumentElement().normalize();
                    
                    // Print out all messages.
                    NodeList nodeLst = doc.getElementsByTagName("status");

                    // This REST URI should always return 20 messages.  If not,
                    // sleep 24 seconds and try again.
                    if (nodeLst.getLength() < 20) {
                        try {
                            System.out.println("Error retrieving tweets.");
                            Thread.sleep(24000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    for (int i = 0; i < nodeLst.getLength(); i++) {
                        Node firstStatusNode = nodeLst.item(i);
                        if (firstStatusNode.getNodeType() == Node.ELEMENT_NODE) {
                            // Convert status Node to Element.
                            Element statusElement = (Element) firstStatusNode;

                            // Get status/text.
                            NodeList textList = statusElement
                                    .getElementsByTagName("text");
                            Element textElement = (Element) textList.item(0);
                            NodeList textElementList = textElement
                                    .getChildNodes();
                            String text = ((Node) textElementList.item(0))
                                    .getNodeValue().trim();

                            // Get status/user/name
                            NodeList userList = statusElement
                                    .getElementsByTagName("user");
                            Element userElement = (Element) userList.item(0);
                            NodeList nameList = userElement
                                    .getElementsByTagName("name");
                            Element nameElement = (Element) nameList.item(0);
                            NodeList nameElementList = nameElement
                                    .getChildNodes();
                            String name = ((Node) nameElementList.item(0))
                                    .getNodeValue().trim();

                            // Get status/id.
                            NodeList idList = statusElement
                                    .getElementsByTagName("id");
                            Element idElement = (Element) idList.item(0);
                            NodeList idElementList = idElement.getChildNodes();
                            Long id = Long.parseLong(((Node) idElementList.item(0))
                                    .getNodeValue().trim());

                            if (!IDList.contains(id)) {
                                // Caches IDs to prevent duplicate messages 
                                // from being displayed.
                                while (IDList.size() >= 20) {
                                    IDList.removeFirst();
                                }
                                IDList.add(id);

                                // Print message.
                                System.out.println("[" + name + "]: " + text);
                            }
                            // Sleep 1.2 seconds between messages, for a total of 
                            // 24 seconds per request.
                            try {
                                Thread.sleep(1200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } finally {
                method.releaseConnection();
            }
        }
    }
}
