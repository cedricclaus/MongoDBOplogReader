package be.ccl.mongo.oplog.domain;

import org.bson.BsonTimestamp;
import org.bson.Document;

import java.util.Objects;

public class OplogItem {

    private final Long id;
    private final String operation;
    private final Integer version;

    private final BsonTimestamp ts;
    private final String ns;
    private final Document data;
    private final Document data2;

    public OplogItem(Long id, Integer version, String operation, BsonTimestamp ts, String ns, Document o, Document o2) {
        this.id = id;
        this.version = version;
        this.operation = operation;
        this.ts = ts;
        this.ns = ns;
        this.data = o;
        this.data2 = o2;
    }

    public String getOperation() {
        return operation;
    }

    public BsonTimestamp getTs() {
        return ts;
    }

    public String getNs() {
        return ns;
    }

    public Document getData() {
        return data;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public Document getData2() {
        return data2;
    }

    @Override
    public String toString() {
        return "OplogItem{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", version=" + version +
                ", ts=" + ts +
                ", ns='" + ns + '\'' +
                ", data=" + data +
                ", data2=" + data2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OplogItem)) return false;
        OplogItem oplogItem = (OplogItem) o;
        return Objects.equals(id, oplogItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
