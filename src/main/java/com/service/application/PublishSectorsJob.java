package com.service.application;

import com.service.ServiceApplication;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class PublishSectorsJob implements Job {
    static private KafkaClients clients = new KafkaClients("localhost:9092");
    static private KafkaProducer<String, String> msgProducer = clients.createProducer(StringSerializer.class, StringSerializer.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for (String fullSectorName : ServiceApplication.stadiumState.getFullSectorNames()) {
            String sectorSeats = new JSONObject(ServiceApplication.stadiumState.getSectorToSeats().get(fullSectorName)).toString();

            /*
            JSONObject jso = new JSONObject(jsonStr);
            Map<String, Object> map = jso.toMap();
            System.out.println(map);
             */
            msgProducer.send(new ProducerRecord<>(fullSectorName, "key", sectorSeats));
        }
        msgProducer.send(new ProducerRecord<>("device_positions", "key", "value"));
    }
}
