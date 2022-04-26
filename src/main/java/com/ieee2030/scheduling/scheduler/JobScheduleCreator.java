/*
 * Copyright 2022 General Electric Technology GmbH.  All Rights Reserved.
 * This document is the confidential and proprietary information of General Electric Technology GmbH and may not be reproduced, transmitted, stored,
 * or copied in whole or in part, or used to furnish information to others, without the prior written permission of
 * General Electric Technology GmbH or Grid Solutions.
 */
package com.ieee2030.scheduling.scheduler;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author andrii.borovyk 04/26/2022
 */
@Component
public class JobScheduleCreator {
  
  public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable,
      ApplicationContext context, String jobName, String jobGroup, JobDataMap jobDataMap) {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(jobClass);
    factoryBean.setDurability(isDurable);
    factoryBean.setApplicationContext(context);
    factoryBean.setName(jobName);
    factoryBean.setGroup(jobGroup);

    // Set job data map
    factoryBean.setJobDataMap(jobDataMap);
    factoryBean.afterPropertiesSet();
    return factoryBean.getObject();
  }

}
