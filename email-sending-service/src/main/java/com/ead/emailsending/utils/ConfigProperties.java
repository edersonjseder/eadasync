package com.ead.emailsending.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ConfigProperties {
    @Value("${mail.smtp.ssl.trust}")
    private String trust;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.starttls}")
    private String tls;
    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.smtp.port}")
    private String port;
    @Value("${mail.smtp.socketFactory.port}")
    private String sfPort;
    @Value("${mail.smtp.socketFactory.class}")
    private String sfClass;
    public Properties getProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.ssl.trust", trust);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", tls);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.port", sfPort);
        props.put("mail.smtp.socketFactory.class", sfClass);

        return props;
    }
}
