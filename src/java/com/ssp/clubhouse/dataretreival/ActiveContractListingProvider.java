package com.ssp.clubhouse.dataretreival;

import com.ssp.clubhouse.dataretreival.activecontractlisting.EventClass;
import com.ssp.clubhouse.dataretreival.activecontractlisting.EventClassId;
import org.jetlang.channels.Publisher;

import java.io.BufferedReader;
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
    public Map<EventClassId, EventClass> eventClassByEventClassId = newLinkedHashMap();

    public static ActiveContractListingProvider load(Publisher<String> logChannel, URL url) throws IOException {
        ActiveContractListingProvider activeContractListingProvider = new ActiveContractListingProvider();

        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = rd.readLine()) != null) {
            logChannel.publish(line);
            System.out.println("line = " + line);
        }
        rd.close();
        return activeContractListingProvider;
    }
}
