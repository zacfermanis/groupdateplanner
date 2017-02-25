/*
 * Copyright (C) 2017, Liberty Mutual Group
 *
 * Created on Feb 28th, 2017
 */

package com.groupdateplanner.planner.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by n0198863 on 2/24/17.
 */
@Configuration
public class AWSConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(AWSConfiguration.class);

    @Autowired
    private Environment environment;

    @Bean
    public AmazonSimpleEmailServiceClient amazonSimpleEmailService() {
        LOGGER.info("Creating AmazonSimpleEmailServiceClient Client...");
        String accessKey = environment.getProperty("cloud.aws.credentials.accessKey");
        String secretKey = environment.getProperty("cloud.aws.credentials.secretKey");
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AmazonSimpleEmailServiceClient(basicAWSCredentials,clientConfiguration());
    }

    /**
     * Creates a standard AWS ClientConfiguration object that can be passed to most AWS SDK Services..
     * Initial configuration is to inject the proxy (local development and the AWS Squid Proxy)
     *
     * @return {@link ClientConfiguration}
     */
    @Bean
    public ClientConfiguration clientConfiguration() {
        LOGGER.info("Building ClientConfiguration() for AWS SDK Clients...");

        ClientConfiguration theClientConfig = new ClientConfiguration();
        return theClientConfig;
    }
}
