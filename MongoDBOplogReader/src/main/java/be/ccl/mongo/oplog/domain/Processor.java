package be.ccl.mongo.oplog.domain;




public interface Processor {


    boolean process(OplogItem nextOp);
}
