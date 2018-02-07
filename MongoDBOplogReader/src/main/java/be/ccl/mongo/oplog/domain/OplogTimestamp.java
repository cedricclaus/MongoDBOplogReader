package be.ccl.mongo.oplog.domain;

import org.bson.BsonTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


public abstract class OplogTimestamp {

    public String name;

    private BsonTimestamp ts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BsonTimestamp getTs() {
        return ts;
    }

    public void setTs(BsonTimestamp ts) {
        this.ts = ts;
    }
}
