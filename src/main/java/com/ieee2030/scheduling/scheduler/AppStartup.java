/*
 * Copyright 2022 General Electric Technology GmbH.  All Rights Reserved.
 * This document is the confidential and proprietary information of General Electric Technology GmbH and may not be reproduced, transmitted, stored,
 * or copied in whole or in part, or used to furnish information to others, without the prior written permission of
 * General Electric Technology GmbH or Grid Solutions.
 */
package com.ieee2030.scheduling.scheduler;

import com.ieee2030.scheduling.scheduler.job.ControlOperation;
import com.ieee2030.scheduling.scheduler.job.SchedulerJobService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author andrii.borovyk 04/26/2022
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {

  private final SchedulerJobService schedulerJobService;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    Instant nowTime = Instant.now();
    schedulerJobService.scheduleNewControlOperation("AABBCCDD", ControlOperation.STARTED, nowTime.plus(5, ChronoUnit.SECONDS));
    schedulerJobService.scheduleNewControlOperation("AABBCCDD", ControlOperation.STARTED, nowTime.plus(7, ChronoUnit.SECONDS));
    schedulerJobService.scheduleNewControlOperation("AABBCCDD", ControlOperation.FINISHED, nowTime.plus(15, ChronoUnit.SECONDS));
    schedulerJobService.scheduleNewControlOperation("AABBCCFF", ControlOperation.STARTED, nowTime.plus(10, ChronoUnit.SECONDS));
    schedulerJobService.scheduleNewControlOperation("AABBCCFF", ControlOperation.FINISHED, nowTime.plus(15, ChronoUnit.SECONDS));
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      log.error(e.getLocalizedMessage());
    }
    schedulerJobService.cancelControlOperation("AABBCCFF", ControlOperation.STARTED);

  }
}
