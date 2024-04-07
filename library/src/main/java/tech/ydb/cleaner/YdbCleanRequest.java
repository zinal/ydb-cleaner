package tech.ydb.cleaner;

import java.util.ArrayList;
import java.util.List;
import tech.ydb.table.description.KeyRange;

/**
 *
 * @author zinal
 */
public class YdbCleanRequest {

    private final String tablePath;
    private final List<KeyRange> ranges;
    private final String filter;

    private YdbCleanRequest(Builder builder) {
        this.tablePath = builder.tablePath;
        this.ranges = builder.ranges;
        this.filter = builder.filter;
    }

    public String getTablePath() {
        return tablePath;
    }

    public List<KeyRange> getRanges() {
        return ranges;
    }

    public String getFilter() {
        return filter;
    }

    public Builder builder(String tablePath) {
        return new Builder(tablePath);
    }

    public final static class Builder {
        private final String tablePath;
        private final List<KeyRange> ranges;
        private String filter;

        private Builder(String tablePath) {
            this.tablePath = tablePath;
            this.ranges = new ArrayList<>();
        }

        public Builder withRange(KeyRange kr) {
            ranges.add(kr);
            return this;
        }

        public Builder withFilter(String filter) {
            this.filter = filter;
            return this;
        }

        public YdbCleanRequest build() {
            return new YdbCleanRequest(this);
        }
    }

}
