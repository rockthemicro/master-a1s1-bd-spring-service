package com.service.controller;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("Duplicates")
@CrossOrigin
@RestController
@RequestMapping("/api/test")
public class TestController {

    private KafkaClients clients = new KafkaClients("localhost:9092");
    private KafkaProducer<String, String> msgProducer = clients.createProducer(StringSerializer.class, StringSerializer.class);

    @GetMapping("/hello")
    public String hello() {
        return "HELLO";
    }

    @GetMapping("/kafka/produce")
    public String kafkaProduce() {
        msgProducer.send(new ProducerRecord<>("TestTopic", "key", "value"));

        return "HELLO";
    }
}
