package com.ssp.clubhouse;

import com.ssp.clubhouse.refdata.ActiveContractListingProvider;
import org.jetlang.channels.MemoryChannel;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 10:08 PM
 */
public class Channels {
    public static final MemoryChannel<Throwable> errors = new MemoryChannel<Throwable>();

    public static class LOGGING{

        public static final MemoryChannel<String> activeContractListing  = new MemoryChannel<String>();
    }

    public static class REFDATA {
        public static final MemoryChannel<ActiveContractListingProvider.ActiveContractListing> activeContractListing  = new MemoryChannel<ActiveContractListingProvider.ActiveContractListing>();

    }
}
