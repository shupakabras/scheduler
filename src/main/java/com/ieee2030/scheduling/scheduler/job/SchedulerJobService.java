package com.ieee2030.scheduling.scheduler.job;

import com.ieee2030.scheduling.scheduler.JobScheduleCreator;
import java.time.Instant;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * @author andrii.borovyk 04/26/2022
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SchedulerJobService {

  private final Scheduler scheduler;

  private final SchedulerFactoryBean schedulerFactoryBean;

  private final ApplicationContext context;

  private final JobScheduleCreator scheduleCreator;

  public void cancelControlOperation(String controlId, ControlOperation controlOperation) {
    try {
      String jobIdentity = controlId + "-" + controlOperation;
      JobDetail jobDetail = JobBuilder
          .newJob(ControlStateChangedJob.class)
          .withIdentity(jobIdentity, "ControlGroup").build();
      if (scheduler.checkExists(jobDetail.getKey())) {
        scheduler.deleteJob(jobDetail.getKey());
        log.info(">>>>> jobName = [" + jobIdentity + "]" + " was deleted. Should not be executed. ");
      } else {
        log.info(">>>>> jobName = [" + jobIdentity + "]" + " does not exists. Nothings to do");
      }
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }

  public void scheduleNewControlOperation(String controlId, ControlOperation controlOperation, Instant time) {
    try {
      Scheduler scheduler = schedulerFactoryBean.getScheduler();
      String jobIdentity = controlId + "-" + controlOperation;
      JobDetail jobDetail = JobBuilder
          .newJob(ControlStateChangedJob.class)
          .withIdentity(jobIdentity, "ControlGroup").build();
      if (!scheduler.checkExists(jobDetail.getKey())) {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("ControlId", controlId);
        jobDataMap.put("operation", controlOperation.name());
        jobDetail = scheduleCreator.createJob(
            ControlStateChangedJob.class, false, context,
            jobIdentity, "ControlGroup", jobDataMap);


        var trigger = TriggerBuilder.newTrigger()
            .withIdentity(jobIdentity, "ControlGroup")
            .startAt(Date.from(time))
            .build();
        
        scheduler.scheduleJob(jobDetail, trigger);

        log.info(">>>>> jobName = [" + jobIdentity + "]" + " scheduled at " + time);
      } else {
        log.info(">>>>> jobName = [" + jobIdentity + "]" + " already exists. Nothings to do");
      }
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }
}
