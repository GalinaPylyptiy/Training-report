package com.epam.trainingReport.config;

import com.epam.trainingReport.dto.TrainingReportRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ErrorHandler;

import java.util.HashMap;
import java.util.Map;

@EnableJms
@Configuration
@EnableTransactionManagement
public class JMSConfig {

    private final ConnectionFactory jmsConnectionFactory;
    private final Logger LOG = LoggerFactory.getLogger(JMSConfig.class);

    public JMSConfig(ConnectionFactory jmsConnectionFactory) {
        this.jmsConnectionFactory = jmsConnectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper());
        converter.setTypeIdPropertyName("_trainingReport");
        converter.setTypeIdMappings(getTypeIdMappings());
        return converter;
    }

    private Map<String, Class<?>> getTypeIdMappings() {
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("TrainingReportRequest", TrainingReportRequest.class);
        return typeIdMappings;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return JsonMapper.builder()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .addModule(new JavaTimeModule())
                .build();
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
       DefaultJmsListenerContainerFactory jmsListenerContainerFactory =  new DefaultJmsListenerContainerFactory();
       jmsListenerContainerFactory.setConnectionFactory(jmsConnectionFactory);
       jmsListenerContainerFactory.setMessageConverter(messageConverter());
       jmsListenerContainerFactory.setTransactionManager(jmsTransactionManager());
       jmsListenerContainerFactory.setErrorHandler(jmsErrorHandler());
       return jmsListenerContainerFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(jmsConnectionFactory);
        jmsTemplate.setMessageConverter(messageConverter());
        return jmsTemplate;
    }

    @Bean
    public PlatformTransactionManager jmsTransactionManager(){
        return new JmsTransactionManager(jmsConnectionFactory);
    }

    @Bean
    public ErrorHandler jmsErrorHandler(){
      return t -> LOG.error("Error occurred during message processing: {}", t.getCause().getMessage());
    }

}
