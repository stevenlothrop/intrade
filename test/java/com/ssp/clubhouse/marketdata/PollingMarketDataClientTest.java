package com.ssp.clubhouse.marketdata;

import com.ssp.clubhouse.jetlang.PublisherStub;
import com.ssp.clubhouse.refdata.activecontractlisting.ContractId;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

/**
 * User: stevenlothrop
 * Date: 11/17/12
 * Time: 3:10 PM
 */
public class PollingMarketDataClientTest {
    @Test
    public void tryIt() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
        String marketDataUrl =   "http://api.intrade.com/jsp/XML/MarketData/ContractBookXML.jsp";
        PublisherStub<Book> bookPublisher = new PublisherStub<Book>();
        PollingMarketDataClient pollingMarketDataClient = new PollingMarketDataClient(marketDataUrl, bookPublisher);
        List<ContractId> contractIds = newArrayList();
        contractIds.add(new ContractId("721154"));
        int depth = 15;
        URL url = pollingMarketDataClient.buildUrl(contractIds, depth);
        pollingMarketDataClient.run(url);
        assertEquals(1, bookPublisher.messages.size());
        System.out.println("bookPublisher = " + bookPublisher.messages.get(0));
    }
}
