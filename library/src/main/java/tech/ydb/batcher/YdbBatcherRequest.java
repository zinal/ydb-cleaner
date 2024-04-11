package tech.ydb.batcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tech.ydb.table.description.KeyRange;

/**
 *
 * @author zinal
 */
public class YdbBatcherRequest {

    private final String tablePath;
    private final String indexName;
    private final List<KeyRange> ranges;
    private final String operation;
    private final boolean allColumns;
    private final List<String> specificColumns;

    private YdbBatcherRequest(Builder builder) {
        this.tablePath = builder.tablePath;
        this.indexName = builder.indexName;
        this.ranges = builder.ranges;
        this.operation = builder.operation;
        this.allColumns = builder.allColumns;
        this.specificColumns = builder.specificColumns;
    }

    /**
     * @return Path to the table (can be relative to database root).
     */
    public String getTablePath() {
        return tablePath;
    }

    /**
     * @return Index name to be scanned, or null if scan should run on primary key.
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @return List of ranges to scan.
     */
    public List<KeyRange> getRanges() {
        return ranges;
    }

    /**
     * @return Should all table columns be included in ${records}
     */
    public boolean isAllColumns() {
        return allColumns;
    }

    /**
     * @return The specific columns to be included in ${records}.
     * If empty, and allColumns, then all should be included.
     * If empty, and not allColumns, then only key columns should be included.
     */
    public List<String> getSpecificColumns() {
        return specificColumns;
    }

    /**
     * @return YQL operation template to perform on the records:
     * 
     * `DELETE FROM ... ON SELECT ...` or
     * `UPSERT INTO ... SELECT ... FROM ...`, using
     * ${table} for table path and
     * ${records} for the current records.
     */
    public String getOperation() {
        return operation;
    }


    public Builder builder(String tablePath) {
        return new Builder(tablePath);
    }

    public Builder builder(String tablePath, String indexName) {
        return new Builder(tablePath, indexName);
    }

    public final static class Builder {
        private final String tablePath;
        private final String indexName;
        private final List<KeyRange> ranges;
        private String operation;
        private boolean allColumns;
        private final List<String> specificColumns;

        private Builder(String tablePath, String indexName) {
            this.tablePath = tablePath;
            this.indexName = (indexName==null) ? null :
                    ( (indexName.length() > 0) ? indexName : null );
            this.ranges = new ArrayList<>();
            this.specificColumns = new ArrayList<>();
        }

        private Builder(String tablePath) {
            this(tablePath, null);
        }

        public Builder withRange(KeyRange kr) {
            ranges.add(kr);
            return this;
        }

        public Builder withOperation(String operation) {
            this.operation = operation;
            return this;
        }

        public Builder withAllColumns() {
            this.specificColumns.clear();
            this.allColumns = true;
            return this;
        }

        public Builder withColumn(String name) {
            this.specificColumns.add(name);
            this.allColumns = false;
            return this;
        }

        public Builder withColumns(Collection<String> names) {
            this.specificColumns.addAll(names);
            this.allColumns = false;
            return this;
        }

        public YdbBatcherRequest build() {
            return new YdbBatcherRequest(this);
        }
    }

}
