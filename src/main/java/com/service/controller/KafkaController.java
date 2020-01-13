package com.service.controller;


import com.service.ServiceApplication;
import com.service.application.KafkaClients;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("Duplicates")
@CrossOrigin
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private KafkaClients clients = new KafkaClients("localhost:9092");

    @GetMapping("/setupSectorTopics")
    public String setupSectorTopics() {
        Admin kafkaAdminClient = clients.createAdmin();
        List<NewTopic> newTopicList = new ArrayList<>();

        for (String fullSectorName : ServiceApplication.stadiumState.getFullSectorNames()) {
            NewTopic newTopic = new NewTopic(fullSectorName, 1, (short) 1);
            newTopicList.add(newTopic);
        }
        newTopicList.add(new NewTopic("device_positions", 1, (short) 1));

        CreateTopicsResult createTopicsResult = kafkaAdminClient.createTopics(newTopicList);
        try {
            createTopicsResult.all().get(); // await for all the topics to be created
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        kafkaAdminClient.close();
        return "OK";
    }

    @GetMapping("/removeSectorTopics")
    public String removeSectorTopics() {
        Admin kafkaAdminClient = clients.createAdmin();

        List<String> deleteTopicList = new ArrayList<String>(ServiceApplication.stadiumState.getFullSectorNames());
        deleteTopicList.add("device_positions");

        DeleteTopicsResult deleteTopicsResult = kafkaAdminClient.deleteTopics(deleteTopicList);
        try {
            deleteTopicsResult.all().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        kafkaAdminClient.close();
        return "OK";
    }
}
