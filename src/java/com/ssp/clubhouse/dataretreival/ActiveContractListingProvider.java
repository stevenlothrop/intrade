package com.ssp.clubhouse.dataretreival;

import com.ssp.clubhouse.dataretreival.activecontractlisting.EventClass;
import com.ssp.clubhouse.dataretreival.activecontractlisting.EventClassId;
import com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl;
import org.jetlang.channels.Publisher;
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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 8:36 PM
 */
public class ActiveContractListingProvider {
    public ActiveContractListing activeContractListing;

    public static ActiveContractListingProvider load(Publisher<String> logChannel, URL url) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        ActiveContractListingProvider activeContractListingProvider = new ActiveContractListingProvider();

        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            stringBuffer.append(line);
            logChannel.publish(line);
        }
        rd.close();
        ActiveContractListing activeContractListing = convert(stringBuffer.toString());
        activeContractListingProvider.activeContractListing = activeContractListing;
        return activeContractListingProvider;
    }

    public static ActiveContractListing convert(String theXMLFile) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        Map<EventClassId, EventClass> value = newLinkedHashMap();
        Long timestamp = null;
        XPath xPath = XPathFactoryImpl.newInstance().newXPath();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new ByteArrayInputStream(theXMLFile.getBytes()));
        XPathExpression expr = xPath.compile("MarketData");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList rootNode = (NodeList) result;
        for (int i = 0; i < rootNode.getLength(); i++) {
            Node marketDataNode = rootNode.item(i);
            timestamp = Long.parseLong(marketDataNode.getAttributes().getNamedItem("intrade.timestamp").getTextContent());
            NodeList marketDataNodeChildNodes = marketDataNode.getChildNodes();
            for (int j = 0; j < marketDataNodeChildNodes.getLength(); j++) {
                Node eventClassNode = marketDataNodeChildNodes.item(j);
                if (eventClassNode.hasAttributes()) {
                    EventClassId eventClassId = EventClassId.decode(eventClassNode);
                    EventClass eventClass = EventClass.decode(eventClassNode);
                    value.put(eventClassId, eventClass);
                }
            }
        }
        return new ActiveContractListing(timestamp, value);
    }

    public static class ActiveContractListing {
        public final Long timestamp;
        public final Map<EventClassId, EventClass> eventClassByEventClassId;

        public ActiveContractListing(long timestamp, Map<EventClassId, EventClass> eventClassByEventClassId) {
            this.timestamp = timestamp;
            this.eventClassByEventClassId = eventClassByEventClassId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ActiveContractListing that = (ActiveContractListing) o;

            if (eventClassByEventClassId != null ? !eventClassByEventClassId.equals(that.eventClassByEventClassId) : that.eventClassByEventClassId != null)
                return false;
            if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = timestamp != null ? timestamp.hashCode() : 0;
            result = 31 * result + (eventClassByEventClassId != null ? eventClassByEventClassId.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ActiveContractListing{" +
                    "timestamp=" + timestamp +
                    ", eventClassByEventClassId=" + eventClassByEventClassId +
                    '}';
        }
    }
}
