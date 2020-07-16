package com.nscorp.tadsservices.client;

import javax.ws.rs.client.ClientBuilder;

public class helloWorldClient {
	public helloWorldClient() {
		super();
		
		//Client c = ClientBuilder.newClient();
		//target = c.target("http://localhost:8080/TADSServices/");
		//String responseMsg = target.path("helloworld").request().get(String.class);	

		//One absurdly long chained together statement
		String responseMsg = ClientBuilder.newClient().target("http://10.5.130.89:8080/TADSServices/").path("helloworld").request().get(String.class);
		System.out.println(responseMsg);
	}
	
	public static void main(String[] args) {
		System.out.println("Starting");
		
		new helloWorldClient();
	
		System.out.println("Finished");
		
	}

}
