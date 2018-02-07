package be.ccl.mongo.springboot.config;

import be.ccl.mongo.oplog.domain.Processor;
import be.ccl.mongo.oplog.service.OplogReaderService;
import be.ccl.mongo.springboot.processor.LoggerOplogProcessor;
import be.ccl.mongo.springboot.processor.RabbitMQProcessor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

/**
 * Created by cedric.claus on 24-04-17.
 */
@Configuration
@ConditionalOnProperty("oplog.rabbit.enabled")
public class RabbitMQConfiguration extends be.ccl.mongo.springboot.config.Configuration{



    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    public RabbitMQConfiguration(MongoDBOplogreaderApplicationProperties applicationProperties) {
        super(applicationProperties);
    }


    @Bean
    RabbitTemplate rabbitTemplate() throws IOException {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }



    @Bean
    public TopicExchange topic() {
        return new TopicExchange(applicationProperties.getRabbit().getTopic());
    }

    @Bean
    Processor processor() throws IOException {
        return new RabbitMQProcessor(rabbitTemplate(),topic());
    }

    @Bean
    OplogReaderService oplogReaderService(MongoTemplate mongoTemplate, MongoDbFactory mongoDbFactory) throws IOException {
        return new OplogReaderService(applicationProperties.getName(),mongoTemplate,mongoDbFactory,processor(),oplogTimestampFactory(),applicationProperties.getSleepTime());
    }




}
