package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.TADSJournalMessage;
import com.nscorp.tadsservices.model.Vehicle;

@XmlType(propOrder= {"prodStat","header","site","customer"})
public class VehicleBase extends TADSJournalMessage {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String prodStat;
	private Header header;
	private Site site;
	private Customer customer;
	protected Vehicle vehicle;
	protected SystemSettings systemsettings;
	// }}
	
	//Constructor
	public VehicleBase() {
		header = new Header();
		site = new Site();
		customer = new Customer();
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public String getProdStat() {
		return prodStat;
	}

	@XmlAttribute(name="PRODSTAT")
	public void setProdStat(String prodStat) {
		this.prodStat = prodStat;
	}

	public Header getHeader() {
		return header;
	}

	@XmlElement(name="HEADER")
	public void setHeader(Header header) {
		this.header = header;
	}

	public Site getSite() {
		return site;
	}

	@XmlElement(name="SITE")
	public void setSite(Site site) {
		this.site = site;
	}

	public Customer getCustomer() {
		return customer;
	}

	@XmlElement(name="CUST")
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	@Override
	public String getXML() {
		
		//Get the Vehicle and System Settings
		if(systemsettings != null && vehicle != null) {
			
			if(vehicle.getProdStatus() != null) {
				this.setProdStat(vehicle.getProdStatus());
			} else {
				this.setProdStat("P");
			}
			
			//Header
			SystemID orig = new SystemID();
			orig.setId("TADS");
			if(systemsettings.getRampCode() != null) orig.setNode(systemsettings.getRampCode());
			header.setOriginSystem(orig);
			
			SystemID dest = new SystemID();
			dest.setId("");
			dest.setNode("");
			header.setDestinationSystem(dest);
			
			Confirmation confirmation = new Confirmation();
			confirmation.setCode("1");
			if(systemsettings.getConfirmMessage() != null) confirmation.setValue(systemsettings.getConfirmMessage());
			header.setConfirmation(confirmation);
			if(vehicle.getChngWho() != null) header.setRacf(vehicle.getChngWho());
			
			//Customer
			customer.setType("TADS");
			if(vehicle.getManufacturer() != null) customer.setValue(vehicle.getManufacturer());
						
			//Site
			site = new Site();
			site.setType("MC");
			if(systemsettings.getRampCode() != null) site.setValue(systemsettings.getRampCode());	
		}
		
		return VehicleBase.convertObjectToXML(this);
	}
	
	public void setVehicle(Vehicle vehicle) {
		if(vehicle != null) {
			this.vehicle = vehicle;
		}
	}
	
	public void setSystemSettings(SystemSettings systemsettings) {
		if(systemsettings != null) {
			this.systemsettings = systemsettings;		
		}
	}
}
