package com.ssp.clubhouse.dataretreival;

import com.ssp.clubhouse.jetlang.NullPublisher;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 8:46 PM
 */
public class ActiveContractListingProviderTest {
    @Test
    @Ignore
    public void test() throws IOException {
            ActiveContractListingProvider activeContractListingProvider = ActiveContractListingProvider.load(new NullPublisher<String>(), new URL("http://api.intrade.com/jsp/XML/MarketData/xml.jsp"));
    }

}
