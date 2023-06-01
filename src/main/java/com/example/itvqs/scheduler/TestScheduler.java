package com.example.itvqs.scheduler;

import java.time.LocalDateTime;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("testScheduler")
public class TestScheduler {

  @SneakyThrows
  @Scheduled(fixedDelay = 1000)
  //It is used to run the scheduled job sequentially with the given n milliseconds delay time between turns
  public void fixedDelayScheduleTask() {
    Thread.sleep(4000);
    log.info("fixedDelayScheduleTask - {}", LocalDateTime.now());
  }

  @Scheduled(initialDelay = 2000, fixedDelay = 1000) //initialDelayString, fixedDelayString
  public void fixedDelayWithInitialDelayScheduleTask() {
    log.info("fixedDelayWithInitialDelayScheduleTask - {}", LocalDateTime.now());
  }

  @SneakyThrows
  @Scheduled(fixedRate = 1000)
  //It is used to run the scheduled jobs in every n milliseconds. It does not matter whether the job has already finished its previous turn or not.
  public void fixedRateScheduleTask() {
    Thread.sleep(4000);
    log.info("fixedRateScheduleTask - {}", LocalDateTime.now());
  }

  @Scheduled(cron = "0 */2 * * * *")
  public void cronScheduleTask() {
    log.info("cronScheduleTask - {}", LocalDateTime.now());
  }
}
