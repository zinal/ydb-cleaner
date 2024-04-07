package tech.ydb.cleaner;

import java.util.concurrent.ExecutorService;
import tech.ydb.table.SessionRetryContext;
import tech.ydb.table.description.TableDescription;
import tech.ydb.table.settings.DescribeTableSettings;

/**
 *
 * @author zinal
 */
public class YdbCleaner {

    private final YdbCleanRequest request;
    private final SessionRetryContext ctx;

    public YdbCleaner(YdbCleanRequest request, SessionRetryContext ctx) {
        this.request = request;
        this.ctx = ctx;
    }

    public void start(ExecutorService es) {
        final DescribeTableSettings dts = new DescribeTableSettings();
        dts.setIncludeShardKeyBounds(true);
        final TableDescription td = ctx.supplyResult(session -> session.describeTable(
                request.getTablePath(), dts)).join().getValue();
        td.getKeyRanges();
    }

    public void join() {
        
    }

    public void cancel() {
        
    }

}
