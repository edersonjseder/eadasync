package com.ead.course.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableAsync
@EnableScheduling
@EntityScan(basePackages = "com.ead.course")
@ComponentScan(basePackages = {"com.ead.course.*"})
@EnableJpaRepositories(basePackages = {"com.ead.course.repositories"})
@EnableAutoConfiguration(exclude = { JpaRepositoriesAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@Profile("dev")
@PropertySource(value = "file:///${user.home}/ead/course/application-dev.properties", ignoreResourceNotFound = true)
public class DevConfig {
    @Value("${spring.jpa.datasource.class-name}")
    private String driverClassName;
    @Value("${spring.jpa.datasource.url}")
    private String url;
    @Value("${spring.jpa.datasource.username}")
    private String username;
    @Value("${spring.jpa.datasource.password}")
    private String password;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
