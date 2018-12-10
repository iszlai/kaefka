package com.noboxz.kaefka;

import java.util.Properties;

public class KaefkaConfiguration {
    private final Properties properties;
    private final String topic;
    private final String keyStoreName;

    public KaefkaConfiguration(Properties properties, String topic, String keyStoreName) {
        this.properties = properties;
        this.topic = topic;
        this.keyStoreName = keyStoreName;
    }
}
