package com.ssp.clubhouse;

import com.ssp.clubhouse.config.Config;
import com.ssp.clubhouse.refdata.ActiveContractListingProvider;
import com.ssp.clubhouse.jetlang.FiberGroup;
import com.ssp.clubhouse.logging.Logger;
import com.ssp.clubhouse.util.DateProvider;
import org.jetlang.fibers.Fiber;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 8:33 PM
 */
public class Main {
    public static void main(String[] args) {
        try {
            run();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void run() throws InterruptedException, IOException {
        Environment environment = new Environment(new File(new File("etc"), "local.properties"), DateProvider.SYSTEM);
        final Config config = environment.config;
        File debugDir = environment.debugDir();
        debugDir.mkdirs();

        FiberGroup fiberGroup = new FiberGroup(Channels.errors);
        Fiber starterFiber = fiberGroup.createStarterFiber(FiberNames.STARTER);

        //Logging
        {
            Fiber fiber = fiberGroup.create(FiberNames.LOGGING);
            Logger activeContractListingLogger = new Logger(DateProvider.SYSTEM, new File(debugDir, "active-contract-listings.json"), true);
            Channels.LOGGING.activeContractListing.subscribe(fiber, activeContractListingLogger.log());
        }

        // 1) Active Contract Listing
        {
            final Fiber activeContractListingFiber = fiberGroup.create(FiberNames.ACTIVE_CONTRACT_LISTING);
            starterFiber.execute(new Runnable() {
                @Override
                public void run() {
                    activeContractListingFiber.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ActiveContractListingProvider activeContractListingProvider = ActiveContractListingProvider.load(Channels.LOGGING.activeContractListing, new URL(config.get("intrade.activecontractlisting.url")));
                                Channels.REFDATA.activeContractListing.publish(activeContractListingProvider.activeContractListing);
                            } catch (Throwable e) {
                                Channels.errors.publish(e);
                            }
                        }
                    });
                }
            });
        }

        // Startup
        {
            fiberGroup.start();
        }


        new CountDownLatch(1).await();
    }
}
