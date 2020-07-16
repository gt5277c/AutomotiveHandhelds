package com.nscorp.tadsservices.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.User;
import com.nscorp.tadsservices.model.Vehicle;

@XmlRootElement (name="RailcarRequest")
@Entity
public class RailcarRequest {

	public RailcarRequest() {}

	private User user;
	private Railspot railspot;
	private Vehicle vehicle;
	
	@XmlElement (name="User")
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; 	}

	@XmlElement (name="Railspot")
	public Railspot getRailspot() { return railspot; }
	public void setRailspot(Railspot railspot) { this.railspot = railspot; }

	@XmlElement (name="Vehicle")
	public Vehicle getVehicle() { return vehicle; }
	public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}
