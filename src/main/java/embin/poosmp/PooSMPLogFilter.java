package embin.poosmp;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class PooSMPLogFilter extends AbstractFilter implements Filter {

    @Override
    public Result filter(LogEvent event) {
        // in smosh voice SHUT UP
        if (event.getMessage().getFormattedMessage().contains("Creating a MIN function between two non-overlapping inputs:")) return Result.DENY;
        return super.filter(event);
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return !record.getMessage().contains("Creating a MIN function between two non-overlapping inputs:");
    }
}
