package com.cpsh.concurrent.forkjoin;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask {
    private static final long serialVersionUID = 4414956794547719603L;

    private static final int THRESHOLD = 3;// 阈值
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 如果任务足够小就计算任务
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阀值，就分裂成两个子任务计算
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            // 等待子任务执行完，并得到其结果
            int leftResult = (Integer) leftTask.join();
            int rightResult = (Integer) rightTask.join();
            System.out.println("left Result : " + leftResult
                    + "  right Result : " + rightResult);

            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 生成一个计算任务，负责计算1+2+3+4
        CountTask task = new CountTask(1, 100);
        
        // 执行一个任务
        Future result = forkJoinPool.submit(task);
        try {
            if (task.isCompletedAbnormally()) {
                System.out.println(task.getException());
            }else {
                System.out.println("Result : " + result.get());    
            }
        }catch(CancellationException e){
            System.out.println("task is canceled...");
        }
        catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }

}
