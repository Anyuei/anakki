package com.anakki.data.utils.common;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author:lijinhao
 * @date:2022/8/31 21:38
 * @useful:
 * @modify
 * ===================================================
 *  modifier            modifytime                description
 * ===================================================
 */

public class TaskDisposeUtils {
    public static final int POOL_SIZE;

    static {
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
    }

    /**
     * @author:lijinhao
     * @date:2022/8/31 22:19
     * @method:dispose: 并行处理，并等待结束
     * @param taskList 任务列表
     * @param consumer 消费者
     * @class:com.javacode2022.util.TaskDisposeUtils
     * @return:void
     */
    public static <T> void dispose(List<T> taskList, Consumer<T> consumer) throws InterruptedException {
        dispose(true, POOL_SIZE, taskList, consumer);
    }

    /**
     * @author:lijinhao
     * @date:2022/8/31 22:05
     * @method:dispose: 并行处理，并等待结束
     * @param moreThread 是否多线程执行
     * @param poolSize  线程池大小
     * @param taskList 任务列表
     * @param consumer 消费者
     * @class:com.javacode2022.util.TaskDisposeUtils
     * @return:void
     */
    public static <T> void dispose(boolean moreThread, int poolSize, List<T> taskList, Consumer<T> consumer) throws InterruptedException {

        if (CollectionUtils.isEmpty(taskList)) {
            return;
        }
        if (moreThread && poolSize > 1) {
            poolSize = Math.min(poolSize, taskList.size());
            ExecutorService executorService = null;
            try {
                executorService = Executors.newFixedThreadPool(poolSize);

                CountDownLatch countDownLatch = new CountDownLatch(taskList.size());
                for (T item : taskList
                ) {
                    executorService.execute(() -> {
                        try {
                            consumer.accept(item);
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }
                countDownLatch.await();
            } finally {
                if (executorService != null) {
                    executorService.shutdown();
                }
            }
        } else {
            for (T item : taskList
            ) {
                consumer.accept(item);
            }
        }
    }
}