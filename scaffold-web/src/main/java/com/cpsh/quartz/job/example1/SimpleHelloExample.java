package com.cpsh.quartz.job.example1;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * 为了调度此任务执行，需要先得到一个Schedule实例，然后创建一个包含任务信息的JobDetail，最后创建一个Trigger管理任务的执行。
 * @author Administrator
 *
 */
public class SimpleHelloExample {

    public static void main(String[] args) throws Exception {
        SimpleHelloExample example = new SimpleHelloExample();
        example.run();

    }
    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(SimpleHelloExample.class);

        log.info("------- Initializing ----------------------");

        // 首先获取一个scheduler(调度器)的引用对象
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");
        
        //计算出这一轮待执行任务的时间点
        Date runTime = evenMinuteDate(new Date());
        
        log.info("------- Scheduling Job  -------------------");

        // 定义一个job,并且将它与需要执行的任务进行绑定
        JobDetail job = newJob(HelloQuartzJob.class).withIdentity("job1", "group1").build();
        // 指定触发器来触发待执行的job
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
        //告诉quartz通过触发器来调度执行job
        sched.scheduleJob(job, trigger);
        
        
       
        
        log.info(job.getKey() + " will run at: " + runTime);

        // Start up the scheduler (nothing can actually run until the
        // scheduler has been started)
        sched.start();

        log.info("------- Started Scheduler -----------------");

        // wait long enough so that the scheduler as an opportunity to
        // run the job!
        log.info("------- Waiting 5 seconds... -------------");
        try {
          // wait 65 seconds to show job
          Thread.sleep(5L * 1000L);
          // executing...
        } catch (Exception e) {
          //
        }

        // shut down the scheduler
        log.info("------- Shutting Down ---------------------");
        sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");
      }
}
