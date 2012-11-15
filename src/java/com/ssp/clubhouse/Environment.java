package com.ssp.clubhouse;

import com.ssp.clubhouse.config.Config;
import com.ssp.clubhouse.util.DateProvider;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 9:40 PM
 */
public class Environment {

    public final Config config;
    private DateProvider dateProvider;

    public Environment(File propertiesFile, DateProvider dateProvider) throws IOException {
        this.dateProvider = dateProvider;
        Properties properties = new Properties();
        properties.load(new BufferedReader(new FileReader(propertiesFile)));
        config = new Config(properties);
    }

    public File debugDir(){
        return new File(config.get("debug.dir") + "/"+ new SimpleDateFormat("yyyy_MM_dd").format(dateProvider.getDate()));
    }
}
