package com.cpsh.service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cpsh.model.Person;
import com.cpsh.service.IService.IServPerson;

@Component
public class PersonService implements IServPerson{
    
    public List<Person> getPersonList() {
        List<Person> persons = new ArrayList<Person>();
        Person p1 = new Person();
        p1.setId(12345);
        p1.setName("Paul");
        p1.setAge(27);
        p1.setAddress("Dalaguete, Cebu");

        Person p2 = new Person();
        p2.setId(54321);
        p2.setName("Sydney");
        p2.setAge(25);
        p2.setAddress("Cebu City");

        persons.add(p1);
        persons.add(p2);
        return persons;
    }
    
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次  
    public void scheduleTest(){
        System.out.println("每5秒钟执行一次。。。。。");
    }
    
    public void start() {
        System.out.println("Server启动时，Personservice 初始化--------------------------------------");
    }
    
    
     
}
