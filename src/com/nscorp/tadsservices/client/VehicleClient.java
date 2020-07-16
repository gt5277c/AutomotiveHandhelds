package com.nscorp.tadsservices.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.nscorp.tadsservices.model.Vehicle;

public class VehicleClient {

	public VehicleClient() {
		super();
	}

	public static void main(String[] args) {
		System.out.println("Starting Vehicle Service client");
				
		VehicleClient vc = new VehicleClient();
		
		//sayHelloVehicle
		//System.out.println(vc.sayHelloVehicle());
		
		//findVehicle
		System.out.println(vc.findVehicle());
		
		System.out.println("Finished Vehicle Service client");
	}
	
	public String sayHelloVehicle() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		//ResteasyWebTarget target = client.target("http://localhost:8080/WebService/");
		ResteasyWebTarget target = client.target("http://10.5.130.89:8080/WebService/");		
		//ResteasyWebTarget target = client.target("http://192.168.1.7:8080/WebService/");
		String response = target.path("Vehicle/sayHelloVehicle/").request().get(String.class);

		return response;
	}
	
	public String findVehicle() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		//ResteasyWebTarget target = client.target("http://localhost:8080/WebService/");
		ResteasyWebTarget target = client.target("http://10.5.130.89:8080/WebService/");
		Vehicle v = target.path("Vehicle/findVehicle/1C4PJLCB1KD326556").request().get(Vehicle.class);

		return v.getVIN();		
	}
}
