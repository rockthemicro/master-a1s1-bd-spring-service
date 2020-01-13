package com.service.application;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.Duration;
import java.util.Collections;

public class ConsumeLocationsJob implements Job {
    static private KafkaClients clients = new KafkaClients("localhost:9092");
    static private KafkaConsumer<String, String> msgConsumer = clients.createConsumer("client",
            StringDeserializer.class, StringDeserializer.class);

    {
        msgConsumer.subscribe(Collections.singletonList("device_positions"));
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ConsumerRecords<String, String> records = msgConsumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.key() + " " + record.value());
        }
    }
}
