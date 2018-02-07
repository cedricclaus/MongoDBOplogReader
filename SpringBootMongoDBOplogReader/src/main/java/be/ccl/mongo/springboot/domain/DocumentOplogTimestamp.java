package be.ccl.mongo.springboot.domain;

import be.ccl.mongo.oplog.domain.OplogTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static be.ccl.mongo.springboot.domain.DocumentOplogTimestamp.OP_TIMESTAMP_COLLECTION;


@Document(collection = OP_TIMESTAMP_COLLECTION)
public class DocumentOplogTimestamp extends OplogTimestamp {

    public static final String OP_TIMESTAMP_COLLECTION = "oplog_timestamp";
    
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
