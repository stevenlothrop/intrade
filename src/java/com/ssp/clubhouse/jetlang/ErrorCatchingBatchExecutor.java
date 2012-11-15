package com.ssp.clubhouse.jetlang;

import org.jetlang.channels.Publisher;
import org.jetlang.core.BatchExecutor;
import org.jetlang.core.EventReader;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 10:46 PM
 */
public class ErrorCatchingBatchExecutor implements BatchExecutor  {
    private Publisher<Throwable> errors;

    public ErrorCatchingBatchExecutor(Publisher<Throwable> errors) {
        this.errors = errors;
    }

    @Override
    public void execute(EventReader toExecute) {
        try {
            for (int i = 0; i < toExecute.size(); i++) {
                toExecute.get(i).run();
            }
        } catch (Throwable t) {
            errors.publish(t);
        }
    }
}
