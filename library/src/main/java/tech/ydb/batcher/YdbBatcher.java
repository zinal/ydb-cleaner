package tech.ydb.batcher;

import java.util.concurrent.ExecutorService;
import tech.ydb.table.SessionRetryContext;
import tech.ydb.table.description.TableDescription;
import tech.ydb.table.settings.DescribeTableSettings;

/**
 *
 * @author zinal
 */
public class YdbBatcher {

    private final YdbBatcherRequest request;
    private final SessionRetryContext ctx;

    public YdbBatcher(YdbBatcherRequest request, SessionRetryContext ctx) {
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
