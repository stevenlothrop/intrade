package com.ssp.clubhouse.dataretreival;

import com.ssp.clubhouse.jetlang.NullPublisher;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 8:46 PM
 */
public class ActiveContractListingProviderTest {
    @Test
    @Ignore
    public void test() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        ActiveContractListingProvider activeContractListingProvider = ActiveContractListingProvider.load(new NullPublisher<String>(), new URL("http://api.intrade.com/jsp/XML/MarketData/xml.jsp"));
        assertNotNull(activeContractListingProvider.activeContractListing);
    }

}
