package be.ccl.mongo.oplog.service;

import be.ccl.mongo.oplog.domain.OplogTimestamp;

public interface PersistentService<E extends OplogTimestamp> {

     E retrieve(String name);

     E save(E opLogTimestamp);


}
