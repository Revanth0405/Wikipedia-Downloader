package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskManager {
    private int threadCount;
    private ExecutorService es;

    public TaskManager(int threadCount) {
        this.threadCount = threadCount;
        this.es = Executors.newFixedThreadPool(threadCount);
    }

    public void waitTillQueueIsFreeAndAddTask(Runnable runnable) {
        while(getQueueSize() >= threadCount) {
            try {
                System.out.println("Sleeping");
                Thread.currentThread();
                Thread.sleep(1000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        addTask(runnable);
    }
    public void addTask(Runnable runnable) {
        this.es.submit(runnable);
    }

    private int getQueueSize() {
        ThreadPoolExecutor ex = (ThreadPoolExecutor) (es);
        return ex.getQueue().size();
    }
}

