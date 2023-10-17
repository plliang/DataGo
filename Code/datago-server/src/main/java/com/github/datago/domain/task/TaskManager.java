package com.github.datago.domain.task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务管理
 */
public class TaskManager {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
            Runtime.getRuntime().availableProcessors() * 2,
            1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(50));

    public void submit() {

    }

    public void shutdown() {
        executor.shutdown();
    }

    public void waitTerminal() {
        while (true) {
            try {
                if (executor.awaitTermination(1, TimeUnit.MINUTES)) {
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
