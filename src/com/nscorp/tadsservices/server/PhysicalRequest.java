package com.nscorp.tadsservices.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.PhysicalDetail;
import com.nscorp.tadsservices.model.User;

@XmlRootElement (name="PhysicalRequest")
@Entity
public class PhysicalRequest {

	public PhysicalRequest() {}
	
	private PhysicalDetail physicaldetail;
	private User user;
	private boolean MovePhysicalVIN = false;

	@XmlElement (name="MovePhysicalVIN")
	public boolean getMovePhysicalVIN() { return MovePhysicalVIN; }
	public void setMovePhysicalVIN(boolean value) { MovePhysicalVIN = value; }
	
	@XmlElement (name="PhysicalDetail")
	public PhysicalDetail getPhysicalDetail() { return physicaldetail; }
	public void setPhysicalDetail(PhysicalDetail physicaldetail) { this.physicaldetail = physicaldetail; }
	
	@XmlElement (name="User")
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; 	}

}
