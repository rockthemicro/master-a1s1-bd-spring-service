package com.service.controller;


import com.service.application.ConsumeLocationsJob;
import com.service.application.PublishSectorsJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@SuppressWarnings("Duplicates")
@CrossOrigin
@RestController
@RequestMapping("/api/quartz")
public class QuartzController {

    private StdSchedulerFactory schedulerFactory;
    private Scheduler scheduler;

    {
        try {
            Properties props = new Properties();
            props.setProperty("org.quartz.scheduler.instanceName", "DefaultQuartzScheduler");
            props.setProperty("org.quartz.scheduler.rmi.export", "false");
            props.setProperty("org.quartz.scheduler.rmi.proxy", "false");

            props.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
            props.setProperty("org.quartz.threadPool.threadCount", "10");
            props.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");

            props.setProperty("org.quartz.jobStore.misfireThreshold", "500");

            schedulerFactory = new StdSchedulerFactory();
            schedulerFactory.initialize(props);

            scheduler = schedulerFactory.getScheduler();

            /* start the producer job */
            JobDetail job = JobBuilder.newJob(PublishSectorsJob.class)
                    .withIdentity("publishSectorsJob", "kafkaJobs")
                    .storeDurably(true)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("kafkaPublishSectorsJobTrigger", "kafkaTriggers")
                    .forJob(job)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)
                            .repeatForever()
                            .withMisfireHandlingInstructionNextWithRemainingCount())
                    .build();

            scheduler.scheduleJob(job, trigger);

            /* start the consumer job */
            job = JobBuilder.newJob(ConsumeLocationsJob.class)
                    .withIdentity("consumeLocationsJob", "kafkaJobs")
                    .storeDurably(true)
                    .build();

            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("kafkaConsumeLocationsJobTrigger", "kafkaTriggers")
                    .forJob(job)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(3)
                            .repeatForever()
                            .withMisfireHandlingInstructionNextWithRemainingCount())
                    .build();

            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    static boolean firstTime = true;
    @GetMapping("/setupQuartz")
    public String setupQuartz() {
        try {
            if (firstTime) {
                scheduler.start();
                firstTime = false;
            } else {
                scheduler.resumeAll();
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return "OK";
    }

    @GetMapping("/removeQuartz")
    public String removeQuartz() {
        try {
            //scheduler.shutdown(true);
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return "OK";
    }
}
