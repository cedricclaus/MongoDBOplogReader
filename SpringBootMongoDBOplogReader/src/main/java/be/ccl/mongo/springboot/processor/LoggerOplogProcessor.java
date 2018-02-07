package be.ccl.mongo.springboot.processor;

import be.ccl.mongo.oplog.domain.OplogItem;
import be.ccl.mongo.oplog.domain.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerOplogProcessor implements Processor {

    private final Logger log = LoggerFactory.getLogger(LoggerOplogProcessor.class);

    @Override
    public boolean process(OplogItem nextOp) {
        log.info("Processing" + nextOp.toString());
        return true;
    }
}
