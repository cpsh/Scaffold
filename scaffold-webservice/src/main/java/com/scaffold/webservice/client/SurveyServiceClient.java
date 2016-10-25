package com.scaffold.webservice.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.scaffold.webservice.server.inf.ISurveyService;

public class SurveyServiceClient {
	public static void main(String[] args) {
		// 加载客户端的配置定义
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"webservice_client.xml");
		// 获取定义的 Web Service Bean
		ISurveyService surveyService = (ISurveyService) context
				.getBean("surveyServiceClient");
		// 1、定义调查投票的变量与内容，用来发送给服务
		String username = "Test";
		int point = 88;
		// 调用方法进行服务消费
		String result = surveyService.vote(username, point);
		System.out.println("Result:" + result);
		// 2、传递不一样的调查投票内容
		username = "Michael";
		point = 100;
		// 再次调用方法进行服务消费，得到不一样的结果
		result = surveyService.vote(username, point);
		System.out.println("Result:" + result);
		// 3、第三次传递与调用
		username = "Jordan";
		point = 9;
		result = surveyService.vote(username, point);
		System.out.println("Result:" + result);
	}
}
