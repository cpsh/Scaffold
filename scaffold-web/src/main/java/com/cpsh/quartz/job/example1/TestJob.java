package com.cpsh.quartz.job.example1;

import java.util.Date;

public class TestJob {
    public void testQuartz(){
        try {
            Thread.sleep(7L * 1000L);
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("Hello, Quartz! - executing its JOB at "+ new Date() );
    }
}
