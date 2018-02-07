package be.ccl.mongo.oplog.domain;

public interface OplogTimestampFactory {


    public abstract OplogTimestamp newInstance();
}
