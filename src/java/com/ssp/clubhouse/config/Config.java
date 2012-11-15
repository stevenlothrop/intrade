package com.ssp.clubhouse.config;

import java.util.Properties;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 9:53 PM
 */
public class Config {
    private Properties properties;

    public Config(Properties properties) {
        this.properties = properties;
    }

    public String get(String key){
        return properties.get(key).toString();
    }
}
