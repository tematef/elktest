package com.elk.core.util;

import java.util.concurrent.TimeUnit;

import com.elk.core.logger.Logger;
import org.awaitility.core.ConditionFactory;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public final class WaitUtil {

    private static final long POLL_INTERVAL = 5L;
    private static final String LOG_MESSAGE = "{} (elapsed time {}s, remaining time {}s)\n";

    private WaitUtil() { }

    public static ConditionFactory doWait(long timeout) {
        return doWait(POLL_INTERVAL, SECONDS, timeout, SECONDS);
    }

    private static ConditionFactory doWait(long interval,
                                           TimeUnit timeUnitInterval,
                                           long timeout,
                                           TimeUnit timeUnitTimeout) {
        return await().with().pollInSameThread()
                .conditionEvaluationListener(condition -> Logger.out.trace(LOG_MESSAGE, condition.getDescription(),
                        condition.getElapsedTimeInMS(), condition.getRemainingTimeInMS()))
                .await().atMost(timeout, timeUnitTimeout).with().pollInterval(interval, timeUnitInterval);
    }
}
