package com.nscorp.tadsservices.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.User;
import com.nscorp.tadsservices.model.Vehicle;

@XmlRootElement (name="VehicleRequest")
@Entity
public class VehicleRequest {

	public VehicleRequest() {}

	private User user;
	private Vehicle vehicle;

	@XmlElement (name="User")
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
	
	@XmlElement (name="Vehicle")
	public Vehicle getVehicle() { return vehicle; }
	public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}
