package com.nscorp.tadsservices.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

//Specifies the path to the RESTful service
@Path("/helloworld")
public class helloWorld {

	helloWorld(){
		
	}
	
	// Specifies that the method processes HTTP GET requests 
	@GET
	@Produces("text/plain")
	public String sayHello() {
		return "Hello World!";
	}
	
	@GET
	@Path("/now")
	@Produces("text/plain")
	public String sayHelloNow() {
		return "Hello NOW!";
	}
}