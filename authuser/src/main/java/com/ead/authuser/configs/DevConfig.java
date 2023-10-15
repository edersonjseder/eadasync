package com.ead.authuser.configs;

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
@EntityScan(basePackages = "com.ead.authuser")
@ComponentScan(basePackages = {"com.ead.authuser.*"})
@EnableJpaRepositories(basePackages = {"com.ead.authuser.repositories"})
@EnableAutoConfiguration(exclude = { JpaRepositoriesAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@Profile("dev")
@PropertySource(value = "file:///${user.home}/ead/authuser/application_v2-dev.properties", ignoreResourceNotFound = true)
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
