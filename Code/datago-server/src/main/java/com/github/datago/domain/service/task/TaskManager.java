package com.github.datago.domain.service.task;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
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
    public void submit(List<Runnable> runnables) {
        if (!CollectionUtils.isEmpty(runnables)) {
            for (Runnable runnable : runnables) {
                executor.submit(runnable);
            }
        }
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
