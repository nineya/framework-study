package com.nineya.jdk;

import java.util.concurrent.*;

public class ThreadPoolMain {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        /**
         * 用给定的初始参数创建一个新的线程池
         * corePoolSize 核心线程数
         * maximumPoolSize 最大线程数
         * keepAliveTime 空闲线程存活时间
         * unit 空闲线程存活时间单位
         * workQueue 工作队列，新任务先提交到工作队列
         * threadFactory 创建一个新线程时使用的线程工厂
         * handler 由于达到线程边界和队列容量而阻塞执行时使用的拒绝策略
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        executor.submit(() -> System.out.println(Thread.currentThread().getName()));
        executor.submit(() -> System.out.println(Thread.currentThread().getName()));
        executor.submit(() -> System.out.println(Thread.currentThread().getName()));
        executor.submit(() -> System.out.println(Thread.currentThread().getName()));
        executor.submit(() -> System.out.println(Thread.currentThread().getName()));
    }
}