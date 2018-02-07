package be.ccl.mongo.oplog.service;

import be.ccl.mongo.oplog.domain.OplogItem;
import be.ccl.mongo.oplog.domain.OplogTimestamp;
import be.ccl.mongo.oplog.domain.OplogTimestampFactory;
import be.ccl.mongo.oplog.domain.Processor;
import com.mongodb.BasicDBObject;
import com.mongodb.CursorType;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.BsonTimestamp;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OplogReaderService {

    final String name;

    final MongoTemplate mongoTemplate;

    final MongoDbFactory mongoDbFactory;

    final Processor processor;

    final OplogTimestampFactory oplogTimestampFactory;

    @Autowired
    Optional<PersistentService>  persistentServiceOptional;

    @Autowired
    Optional<OplogFilter> oplogFilterOptional;
    private long sleepTime = 2000;


    public OplogReaderService(String name, MongoTemplate mongoTemplate, MongoDbFactory mongoDbFactory, Processor processor, OplogTimestampFactory oplogTimestampFactory) {
        this.name = name;
        this.mongoTemplate = mongoTemplate;
        this.mongoDbFactory = mongoDbFactory;
        this.processor = processor;
        this.oplogTimestampFactory = oplogTimestampFactory;
    }

    public OplogReaderService(String name, MongoTemplate mongoTemplate, MongoDbFactory mongoDbFactory, Processor processor, OplogTimestampFactory oplogTimestampFactory, long sleepTime) {
        this.name = name;
        this.mongoTemplate = mongoTemplate;
        this.mongoDbFactory = mongoDbFactory;
        this.processor = processor;
        this.oplogTimestampFactory = oplogTimestampFactory;
        this.sleepTime = sleepTime;
    }

    public void execute() {
        OplogTimestamp opLogTimestamp = null;

        if(persistentServiceOptional.isPresent()){
            opLogTimestamp =  persistentServiceOptional.get().retrieve(this.name);


        }

        if(opLogTimestamp == null){
            opLogTimestamp = this.oplogTimestampFactory.newInstance();
            opLogTimestamp.setTs(new BsonTimestamp());
            opLogTimestamp.setName(this.name);
            if(persistentServiceOptional.isPresent()){
                persistentServiceOptional.get().save(opLogTimestamp);
            }

        }


        Bson timeQuery = createTimeQuery(opLogTimestamp.getTs());
        MongoCursor<Document> opCursor = createCursor(timeQuery);

        BsonTimestamp lastTimeStamp = opLogTimestamp.getTs();
        while (true) {
            try {
                if (!opCursor.hasNext()) {
                    continue;
                } else {
                    final OplogItem nextOp = createOplogItem(opCursor.next());
                    if(oplogFilterOptional.isPresent()){
                        if(oplogFilterOptional.get().isValid(nextOp)){
                            lastTimeStamp = process(opLogTimestamp, lastTimeStamp, nextOp);
                        }else{
                            lastTimeStamp = nextOp.getTs();
                        }
                    }else{
                        lastTimeStamp = process(opLogTimestamp, lastTimeStamp, nextOp);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(this.sleepTime);
                    opCursor.close();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                opCursor = createCursor(createTimeQuery(lastTimeStamp));
            }
        }

    }

    private BsonTimestamp process(OplogTimestamp opLogTimestamp, BsonTimestamp lastTimeStamp, OplogItem nextOp) {
        if (processor.process(nextOp)){
            lastTimeStamp = nextOp.getTs();
            if(persistentServiceOptional.isPresent()) {
                opLogTimestamp.setTs(lastTimeStamp);
                persistentServiceOptional.get().save(opLogTimestamp);
            }
        }
        return lastTimeStamp;
    }

    private OplogItem createOplogItem(Document next) {
        return new OplogItem(next.getLong("h"),
                next.getInteger("v"),
                next.getString("op"),
                ((BsonTimestamp) next.get("ts")),
                next.getString("ns"),
                (Document)next.get("o"),
                (Document)next.get("o2"));
    }


    private static Bson createTimeQuery(BsonTimestamp initialTimeStamp) {
        Bson timeQuery = initialTimeStamp == null ? new Document() : Filters.gt("ts",initialTimeStamp);
        return timeQuery;
    }

    private MongoCursor<Document> createCursor(Bson timeQuery) {
        MongoCollection<Document> fromCollection = new MongoClient(this.mongoDbFactory.getDb().getMongo().getAllAddress()).getDatabase(this.mongoDbFactory.getDb("local").getName()).getCollection("oplog.rs");
        return fromCollection.find(timeQuery).projection(new Document())
                .sort(new BasicDBObject("$natural", 1)).cursorType(CursorType.TailableAwait).noCursorTimeout(true).iterator();

    }

}
