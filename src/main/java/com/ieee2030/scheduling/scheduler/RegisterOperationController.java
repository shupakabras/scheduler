package com.ieee2030.scheduling.scheduler;

import com.ieee2030.scheduling.scheduler.job.ControlOperation;
import com.ieee2030.scheduling.scheduler.job.SchedulerJobService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrii.borovyk 04/27/2022
 */
@RestController
@RequiredArgsConstructor
public class RegisterOperationController {

  private final SchedulerJobService schedulerJobService;

  @GetMapping(value = "/register/{controlId}/{operation}/{time}")
  public void addNewSchedule(@PathVariable String controlId, @PathVariable ControlOperation operation,
      @PathVariable int time) {
    Instant nowTime = Instant.now();
    schedulerJobService.scheduleNewControlOperation(controlId, operation, nowTime.plus(time, ChronoUnit.SECONDS));

  }

  @GetMapping(value = "/register/generate/{count}/{time}")
  public void addNewSchedule(@PathVariable int count,
      @PathVariable int time) {
    Instant nowTime = Instant.now();
    for (int i=0; i< count; i++) {
      schedulerJobService.scheduleNewControlOperation("AABBCC" + i, ControlOperation.STARTED,
          nowTime.plus(time, ChronoUnit.SECONDS));
    }

  }
}
