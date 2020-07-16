package com.nscorp.tadsservices.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.User;

@XmlRootElement (name="OptionRequest")
@Entity
public class OptionRequest {

	public OptionRequest() {}

	private User user;
	private SystemSettings systemsettings;
	
	@XmlElement (name="User")
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
	
	@XmlElement (name="SystemSettings")
	public SystemSettings getSystemSettings() { return systemsettings; }
	public void setSystemSettings(SystemSettings systemsettings) {
		this.systemsettings = systemsettings;
	}
}
