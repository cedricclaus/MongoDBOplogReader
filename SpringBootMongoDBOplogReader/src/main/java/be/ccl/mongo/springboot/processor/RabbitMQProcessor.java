package be.ccl.mongo.springboot.processor;

import be.ccl.mongo.oplog.domain.OplogItem;
import be.ccl.mongo.oplog.domain.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMQProcessor implements Processor {

    private final Logger log = LoggerFactory.getLogger(RabbitMQProcessor.class);

    private final RabbitTemplate rabbitTemplate;

    private final TopicExchange topic;

    public RabbitMQProcessor(RabbitTemplate rabbitTemplate, TopicExchange topic) {
        this.rabbitTemplate = rabbitTemplate;
        this.topic = topic;
    }


    @Override
    public boolean process(OplogItem nextOp) {
        String routingKey = nextOp.getNs();
        log.info("send message to " + topic.getName() + ":" + routingKey + ":("+nextOp+")");
        rabbitTemplate.convertAndSend(topic.getName(),routingKey,nextOp);
        return true;
    }
}
