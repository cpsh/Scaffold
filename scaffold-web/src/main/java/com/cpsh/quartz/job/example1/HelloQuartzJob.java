package com.cpsh.quartz.job.example1;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 为了调度此任务执行，需要先得到一个Schedule实例，然后创建一个包含任务信息的JobDetail，最后创建一个Trigger管理任务的执行。
 * @author Administrator
 *
 */
public class HelloQuartzJob implements Job {

//    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hello, Quartz! - executing its JOB at "+ 
                new Date() + " by " + context.getTrigger().getStartTime());
    }
    
}
