package com.ssp.clubhouse;

import org.jetlang.channels.MemoryChannel;
import org.jetlang.channels.Publisher;

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
}
