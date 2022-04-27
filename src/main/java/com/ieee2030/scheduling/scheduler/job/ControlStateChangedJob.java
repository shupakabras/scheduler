package com.ieee2030.scheduling.scheduler.job;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author andrii.borovyk 04/26/2022
 */
@Slf4j
public class ControlStateChangedJob extends QuartzJobBean {

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    log.info("Executing job: " + context.getJobDetail().getJobDataMap().getWrappedMap());
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new JobExecutionException(e);
    }
  }
}
