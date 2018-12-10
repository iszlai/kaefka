package com.noboxz.kaefka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.lang.management.ManagementFactory;
import java.util.Properties;

public class KaefkaConfigurationBuilder {

    private final String keyStoreName;
    private final String topic;

    private String applicationId = "kaefka-client-application-";
    private String bootstrapServers = "localhost:9092";
    private Class defaultKeySerdeClass = Serdes.Long().getClass();
    private Class defaultValueSerdeClass = Serdes.String().getClass();
    private int numberOfThreads = 1;
    private int maxPollIntervalMS = 200;
    private int maxPollRecords = 100;
    private String stateDir = "/tmp/kaefkaStore";

    public KaefkaConfigurationBuilder(String topic, String keyStoreName) {
        this.topic = topic;
        this.keyStoreName = keyStoreName;
    }

    public KaefkaConfigurationBuilder ApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public KaefkaConfigurationBuilder BootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
        return this;
    }

    public KaefkaConfigurationBuilder NumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        return this;
    }

    public KaefkaConfigurationBuilder MaxPollIntervalMS(int maxPollIntervalMS) {
        this.maxPollIntervalMS = maxPollIntervalMS;
        return this;
    }

    public KaefkaConfigurationBuilder MaxPollRecords(int maxPollRecords) {
        this.maxPollRecords = maxPollRecords;
        return this;
    }

    public KaefkaConfigurationBuilder StateDir(String stateDir) {
        this.stateDir = stateDir;
        return this;
    }

    public KaefkaConfigurationBuilder defaultSerde(Class keyClass, Class valueClass) {
        this.defaultKeySerdeClass = keyClass;
        this.defaultValueSerdeClass = valueClass;
        return this;
    }

    private Properties toProperties() {
        String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        Properties props = new Properties();
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMS);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId + jvmName);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, defaultKeySerdeClass);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, defaultValueSerdeClass);
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, numberOfThreads);
        props.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
        return props;
    }

    KaefkaConfiguration build() {
        return new KaefkaConfiguration(toProperties(), topic, keyStoreName);
    }
}
