package com.ssp.clubhouse.jetlang;

import org.jetlang.channels.Publisher;
import org.jetlang.core.RunnableExecutorImpl;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.ThreadFiber;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 10:41 PM
 */
public class FiberGroup {
    private List<Fiber> fibers = newArrayList();
    private Publisher<Throwable> errors;
    private Fiber starterFiber = null;

    public FiberGroup(Publisher<Throwable> errors) {
        this.errors = errors;
    }

    public Fiber createStarterFiber(String name) {
        if (starterFiber == null) {
            starterFiber = new ThreadFiber(new RunnableExecutorImpl(new ErrorCatchingBatchExecutor(errors)), name, false);
        }
        return starterFiber;
    }

    public Fiber create(String name) {
        Fiber fiber = new ThreadFiber(new RunnableExecutorImpl(new ErrorCatchingBatchExecutor(errors)), name, false);
        fibers.add(fiber);
        return fiber;
    }

    public void start() {
        for (Fiber fiber : fibers) {
            fiber.start();
        }
        if (starterFiber != null) {
            starterFiber.start();
        }
    }


}
