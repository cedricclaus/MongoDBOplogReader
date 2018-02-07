package be.ccl.mongo.oplog.service;

import be.ccl.mongo.oplog.domain.OplogItem;

@FunctionalInterface
public interface OplogFilter{

    boolean isValid(OplogItem item);





}
