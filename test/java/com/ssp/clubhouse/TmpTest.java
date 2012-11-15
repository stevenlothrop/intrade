package com.ssp.clubhouse;

import com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 11:05 PM
 */
public class TmpTest {
    @Test
    public void tryIt() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        XPath xPath = XPathFactoryImpl.newInstance().newXPath();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        String s = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><intrade id=\"2\"><contract id=\"7\"></contract><contract id=\"8\"></contract><contract id=\"9\"></contract></intrade>";
        Document doc = documentBuilder.parse(new ByteArrayInputStream(s.getBytes()));
        XPathExpression expr = xPath.compile("intrade");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node item = nodes.item(i);
            System.out.println(item);
        }
        System.out.println("done");
    }
}
