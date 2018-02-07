package be.ccl.mongo.springboot.service;

import be.ccl.mongo.oplog.domain.OplogItem;
import be.ccl.mongo.oplog.domain.OplogTimestamp;
import be.ccl.mongo.oplog.service.OplogFilter;
import be.ccl.mongo.springboot.domain.DocumentOplogTimestamp;
import org.springframework.stereotype.Component;


public class OplogFilterImpl implements OplogFilter {
    @Override
    public boolean isValid(OplogItem item) {
        return !item.getNs().contains(DocumentOplogTimestamp.OP_TIMESTAMP_COLLECTION);

    }
}
