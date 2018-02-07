package be.ccl.mongo.springboot.service;

import be.ccl.mongo.oplog.domain.OplogTimestamp;
import be.ccl.mongo.oplog.service.PersistentService;
import be.ccl.mongo.springboot.domain.DocumentOplogTimestamp;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;


public class PersistentServiceImpl implements PersistentService<DocumentOplogTimestamp> {


    private final MongoTemplate mongoTemplate;

    public PersistentServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public DocumentOplogTimestamp retrieve(String name) {
        return mongoTemplate.findOne(Query.query(where("name").is(name)), DocumentOplogTimestamp.class);
    }

    @Override
    public DocumentOplogTimestamp save(DocumentOplogTimestamp opLogTimestamp) {
        return mongoTemplate.findAndModify(Query.query(where("name").is(opLogTimestamp.getName())), update("ts",opLogTimestamp.getTs()), new FindAndModifyOptions().returnNew(true).upsert(true),DocumentOplogTimestamp.class);
    }
}
