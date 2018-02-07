package be.ccl.mongo.springboot.config;

import be.ccl.mongo.oplog.domain.Processor;
import be.ccl.mongo.oplog.service.OplogReaderService;
import be.ccl.mongo.springboot.processor.LoggerOplogProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConditionalOnProperty(name = "oplog.rabbit.enabled", havingValue = "false")
public class SimpleLoggingConfiguration extends be.ccl.mongo.springboot.config.Configuration{

    public SimpleLoggingConfiguration(MongoDBOplogreaderApplicationProperties applicationProperties) {
        super(applicationProperties);
    }

    Processor processor(){
        return new LoggerOplogProcessor();
    }


    @Bean
    OplogReaderService oplogReaderService(MongoTemplate mongoTemplate, MongoDbFactory mongoDbFactory){
        return new OplogReaderService(applicationProperties.getName(),mongoTemplate,mongoDbFactory,processor(),oplogTimestampFactory(),applicationProperties.getSleepTime());
    }
}
