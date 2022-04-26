package com.ieee2030.scheduling.scheduler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author andrii.borovyk 04/26/2022
 */
@Configuration
@EnableAutoConfiguration
@RequiredArgsConstructor
public class SchedulerConfig {

  private final DataSource dataSource;

  private final ApplicationContext applicationContext;

  private final QuartzProperties quartzProperties;

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() {

    SchedulerJobFactory jobFactory = new SchedulerJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    DBConnectionManager.getInstance().addConnectionProvider("quartzDataSource",
        new ConnectionProvider() {
          @Override
          public Connection getConnection() throws SQLException {
            return dataSource.getConnection();
          }

          @Override
          public void shutdown() throws SQLException {

          }

          @Override
          public void initialize() throws SQLException {

          }
        });


    Properties properties = new Properties();
    properties.putAll(quartzProperties.getProperties());

    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setOverwriteExistingJobs(false);
    factory.setDataSource(dataSource);
    factory.setQuartzProperties(properties);
    factory.setJobFactory(jobFactory);
    return factory;
  }
}

