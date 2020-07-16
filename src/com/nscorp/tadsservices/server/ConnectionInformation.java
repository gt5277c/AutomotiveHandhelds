package com.nscorp.tadsservices.server;

import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.User;

@XmlRootElement (name="ConnectionInformation")
@Entity
public class ConnectionInformation {

	public ConnectionInformation() {}
	
	private User user;
	private String ramp;
	private String environment;
	private Date OptionLoadedDate;
	private Date TruckCompanyLoadedDate;
    private Date RouteLoadedDate;
    private Date DealerLoadedDate;
    private Date UpfitterLoadedDate;
    private Date ManufacturerLoadedDate;
    private Date ManfVINCodeLoadedDate;
    private Date LocationLoadedDate;
    
    @XmlElement (name="User")
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	@XmlElement (name="ramp")
	public String getRamp() { return ramp; }
	public void setRamp(String ramp) { this.ramp = ramp; }

	@XmlElement (name="environment")
	public String getEnvironment() { return environment; }
	public void setEnvironment(String environment) { this.environment = environment; }

	@XmlElement (name="OptionLoadedDate")
	public Date getOptionLoadedDate() { return OptionLoadedDate; }
	public void setOptionLoadedDate(Date optionLoadedDate) { OptionLoadedDate = optionLoadedDate; }

	@XmlElement (name="TruckCompanyLoadedDate")
	public Date getTruckCompanyLoadedDate() { return TruckCompanyLoadedDate; }
	public void setTruckCompanyLoadedDate(Date truckCompanyLoadedDate) { TruckCompanyLoadedDate = truckCompanyLoadedDate; }

	@XmlElement (name="RouteLoadedDate")
	public Date getRouteLoadedDate() { return RouteLoadedDate; }
	public void setRouteLoadedDate(Date routeLoadedDate) { RouteLoadedDate = routeLoadedDate; }

	@XmlElement (name="DealerLoadedDate")
	public Date getDealerLoadedDate() { return DealerLoadedDate; }
	public void setDealerLoadedDate(Date dealerLoadedDate) { DealerLoadedDate = dealerLoadedDate; }

	@XmlElement (name="UpfitterLoadedDate")
	public Date getUpfitterLoadedDate() { return UpfitterLoadedDate; }
	public void setUpfitterLoadedDate(Date upfitterLoadedDate) { UpfitterLoadedDate = upfitterLoadedDate; }

	@XmlElement (name="ManufacturerLoadedDate")
	public Date getManufacturerLoadedDate() { return ManufacturerLoadedDate; }
	public void setManufacturerLoadedDate(Date manufacturerLoadedDate) { ManufacturerLoadedDate = manufacturerLoadedDate; }

	@XmlElement (name="ManfVINCodeLoadedDate")
	public Date getManfVINCodeLoadedDate() { return ManfVINCodeLoadedDate; }
	public void setManfVINCodeLoadedDate(Date manfVINCodeLoadedDate) { ManfVINCodeLoadedDate = manfVINCodeLoadedDate; }

	@XmlElement (name="LocationLoadedDate")
	public Date getLocationLoadedDate() { return LocationLoadedDate; }
	public void setLocationLoadedDate(Date locationLoadedDate) { LocationLoadedDate = locationLoadedDate; }
}
