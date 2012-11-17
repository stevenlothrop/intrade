package com.ssp.clubhouse.marketdata;

import com.ssp.clubhouse.refdata.activecontractlisting.ContractId;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * User: stevenlothrop
 * Date: 11/17/12
 * Time: 3:03 PM
 */
public class PollingMarketDataClient {
    private final String intradeMarketDataUrl;
    private final Publisher<Book> bookPublisher;

    public PollingMarketDataClient(String intradeMarketDataUrl, Publisher<Book> bookPublisher) {
        this.intradeMarketDataUrl = intradeMarketDataUrl;
        this.bookPublisher = bookPublisher;
    }


    public URL buildUrl(List<ContractId> contractIds) throws MalformedURLException {
        return buildUrl(contractIds, 0, 5);
    }

    public URL buildUrl(List<ContractId> contractIds, int depth) throws MalformedURLException {

        return buildUrl(contractIds, 0, depth);
    }

    public URL buildUrl(List<ContractId> contractIds, long timestamp, int depth) throws MalformedURLException {
        if (depth > 15 || depth < 1) {
            throw new IllegalArgumentException("Depth must be between 1 and 15. Got: " + depth);
        }
        StringBuilder stringBuilder = new StringBuilder(intradeMarketDataUrl);
        stringBuilder.append("?");
        for (ContractId contractId : contractIds) {
            stringBuilder.append("id=");
            stringBuilder.append(contractId.id);
            stringBuilder.append("&");
        }
        stringBuilder.append("timestamp=");
        stringBuilder.append(timestamp);
        if (depth != 5) {
            stringBuilder.append("&depth=");
            stringBuilder.append(depth);
        }
        return new URL(stringBuilder.toString());
    }

    public void run(URL url) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            stringBuffer.append(line);
            System.out.println("line = " + line);
        }
        rd.close();
        buildBooksAndPublish(stringBuffer.toString());
    }

    private void buildBooksAndPublish(String bookInfoXml) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        List<Book> value = newArrayList();
        XPath xPath = XPathFactoryImpl.newInstance().newXPath();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new ByteArrayInputStream(bookInfoXml.getBytes()));
        XPathExpression expr = xPath.compile("ContractBookInfo");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList contractBookInfoNodes = (NodeList) result;
        for (int i = 0; i < contractBookInfoNodes.getLength(); i++) {
            Node contractBookInfoNode = contractBookInfoNodes.item(i);
            ContractId contractId = null;
            long lastUpdateTime = Long.parseLong(contractBookInfoNode.getAttributes().getNamedItem("lastUpdateTime").getTextContent());
            List<Book.BookEntry> bids = newArrayList();
            List<Book.BookEntry> offers = newArrayList();
            NodeList contractInfoNodesChildNodes = contractBookInfoNode.getChildNodes();
            for (int j = 0; j < contractInfoNodesChildNodes.getLength(); j++) {
                Node contractInfoChildNode = contractInfoNodesChildNodes.item(j);
                if (contractInfoChildNode.hasAttributes()) {
                    contractId = new ContractId(contractInfoChildNode.getAttributes().getNamedItem("conID").getTextContent());
                }
                NodeList contractInfoChildNodes = contractInfoChildNode.getChildNodes();
                for (int z = 0; z < contractInfoChildNodes.getLength(); z++) {
                    Node contractInfoNode = contractInfoChildNodes.item(z);


                    if (contractInfoNode.getNodeName().equals("symbol")) {
                        //No-op
                    } else if (contractInfoNode.getNodeName().equals("orderBook")) {
                        NodeList orderBookNodeList = contractInfoNode.getChildNodes();
                        for (int k = 0; k < orderBookNodeList.getLength(); k++) {
                            Node orderBookNode = orderBookNodeList.item(k);
                            if (orderBookNode.getNodeName().equals("bids")) {
                                NodeList bidNodeList = orderBookNode.getChildNodes();
                                for (int m = 0; m < bidNodeList.getLength(); m++) {
                                    Node bidNode = bidNodeList.item(m);
                                    if (bidNode.hasAttributes()) {
                                        Book.BookEntry bookEntry = new Book.BookEntry(
                                                bidNode.getAttributes().getNamedItem("price").getTextContent(),
                                                Integer.parseInt(bidNode.getAttributes().getNamedItem("quantity").getTextContent()));
                                        bids.add(bookEntry);
                                    }
                                }
                            } else if (orderBookNode.getNodeName().equals("offers")) {
                                NodeList offerNodeList = orderBookNode.getChildNodes();
                                for (int n = 0; n < offerNodeList.getLength(); n++) {
                                    Node offerNode = offerNodeList.item(n);
                                    if (offerNode.hasAttributes()) {
                                        Book.BookEntry bookEntry = new Book.BookEntry(
                                                offerNode.getAttributes().getNamedItem("price").getTextContent(),
                                                Integer.parseInt(offerNode.getAttributes().getNamedItem("quantity").getTextContent()));
                                        offers.add(bookEntry);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Book book = new Book(contractId, lastUpdateTime, bids, offers);
            value.add(book);
        }
        for (Book book : value) {
            bookPublisher.publish(book);
        }
    }
}
