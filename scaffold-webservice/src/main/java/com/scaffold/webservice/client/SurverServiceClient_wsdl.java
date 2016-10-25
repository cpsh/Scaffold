package com.scaffold.webservice.client;

import javax.xml.ws.Response;

import com.scaffold.webservice.generateSource.ISurveyService;
import com.scaffold.webservice.generateSource.ISurveyServiceService;
import com.scaffold.webservice.generateSource.VoteResponse;


public class SurverServiceClient_wsdl {
	public static void main(String[] args) {
		ISurveyServiceService service = new ISurveyServiceService();
		ISurveyService port = service.getISurveyServicePort();
		
		
		//Response<VoteResponse> response = port.vote("cpsh", 100);
		String response = port.vote("cpsh", 1);
		System.out.println(response);
	}
}
