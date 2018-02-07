package be.ccl.mongo.springboot.domain;

import be.ccl.mongo.oplog.domain.OplogTimestamp;
import be.ccl.mongo.oplog.domain.OplogTimestampFactory;


public class DocumentOplogTimestampFactory implements OplogTimestampFactory {

    @Override
    public OplogTimestamp newInstance() {
        return new DocumentOplogTimestamp();
    }
}
