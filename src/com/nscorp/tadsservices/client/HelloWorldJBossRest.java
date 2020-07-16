package com.nscorp.tadsservices.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class HelloWorldJBossRest {

	public HelloWorldJBossRest() {
		super();

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/WebService/");
		String responseMsg = target.path("helloworld").request().get(String.class);
		
		System.out.println(responseMsg);
	}
	
	public static void main(String[] args) {
		System.out.println("Starting JBoss client");
		
		System.out.println("Finished JBoss client");
		
	}
}
