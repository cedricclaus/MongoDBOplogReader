package be.ccl.mongo.springboot.config;

import be.ccl.mongo.oplog.domain.OplogTimestampFactory;
import be.ccl.mongo.oplog.domain.Processor;
import be.ccl.mongo.oplog.service.OplogFilter;
import be.ccl.mongo.oplog.service.OplogReaderService;
import be.ccl.mongo.oplog.service.PersistentService;
import be.ccl.mongo.springboot.domain.DocumentOplogTimestampFactory;
import be.ccl.mongo.springboot.processor.LoggerOplogProcessor;
import be.ccl.mongo.springboot.service.OplogFilterImpl;
import be.ccl.mongo.springboot.service.PersistentServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@org.springframework.context.annotation.Configuration
@Import(value = MongoAutoConfiguration.class)
public class Configuration {

    protected final MongoDBOplogreaderApplicationProperties applicationProperties;

    public Configuration(MongoDBOplogreaderApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    OplogTimestampFactory oplogTimestampFactory(){
          return new DocumentOplogTimestampFactory();
    }

    @Bean
    PersistentService persistentService(MongoTemplate mongoTemplate){
        return new PersistentServiceImpl(mongoTemplate);
    }

    @Bean
    OplogFilter oplogFilter(){
        return new OplogFilterImpl();
    }








}
