package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.TADSJournalMessage;
import com.nscorp.tadsservices.model.Vehicle;
import com.nscorp.tadsservices.model.XML.Confirmation;
import com.nscorp.tadsservices.model.XML.Customer;
import com.nscorp.tadsservices.model.XML.Header;
import com.nscorp.tadsservices.model.XML.InventoryLocation;
import com.nscorp.tadsservices.model.XML.Manufacturer;
import com.nscorp.tadsservices.model.XML.PieceIdentification;
import com.nscorp.tadsservices.model.XML.Site;
import com.nscorp.tadsservices.model.XML.SystemID;

@XmlRootElement(name="INVLOAD")
@XmlType(propOrder= {"prodStat","header","site","customer","eventTime","railCar","pcid"})
public class RcdispInvl extends TADSJournalMessage {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	
	//data
	protected Vehicle vehicle;
	protected SystemSettings systemsettings;
	protected Railspot railspot;
	
	//attributes
	private String prodStat;
	private String action;
	
	//elements
	private Header header;
	private Site site;
	private Customer customer;
	private String eventTime;
	private RailCarInvl railCar;
	private PieceIdentification pcid;
	// }}
	
	//Constructor
	public RcdispInvl() {
		header = new Header();
		site = new Site();
		customer = new Customer();
		railCar = new RailCarInvl();
		pcid = new PieceIdentification();
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public String getAction() {
		return action;
	}

	@XmlAttribute(name="ACTION")
	public void setAction(String action) {
		this.action = action;
	}

	public Customer getCustomer() {
		return customer;
	}

	@XmlElement(name="CUST")
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

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

	public String getEventTime() {
		return eventTime;
	}

	@XmlElement(name="EVENTTIME")
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public RailCarInvl getRailCar() {
		return railCar;
	}

	@XmlElement(name="RAILCAR")
	public void setRailCar(RailCarInvl railCar) {
		this.railCar = railCar;
	}

	public PieceIdentification getPcid() {
		return pcid;
	}

	@XmlElement(name="PCID")
	public void setPcid(PieceIdentification pcid) {
		this.pcid = pcid;
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
	
	public void setRailspot(Railspot railspot) {
		if(railspot != null) {
			this.railspot = railspot;
		}
	}
	// }}

	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return "RcdispInvl [action=" + action + ", customer=" + customer + ", prodStat=" + prodStat + ", header="
				+ header + ", site=" + site + ", eventTime=" + eventTime + ", railCar=" + railCar + ", pcid=" + pcid
				+ "]";
	}

	@Override
	public String getXML() {
		//Get the System Settings, Vehicle, and Railcar
		if(systemsettings != null && vehicle != null && railspot != null) {
			
			//ProdStat
			this.setProdStat("P");
			
			//Action - already set by user
			
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

			//Event Time
			if(vehicle.getChngDate() != null) {
				eventTime = Common.getDateTime(vehicle.getChngDate());
			}
			
			//Railcar
			if(railspot.getRailcarNumber() != null) {
				String railcar = railspot.getRailcarNumber();
				
				//Set the init
				railCar.setInit(Common.getRailcarInit(railcar));
				
				//Set the number
				railCar.setNum(Common.getRailcarNum(railcar));
				
				//Set the SCAC
				if(railspot.getOutboundSCAC() != null) {
					railCar.setScac(railspot.getOutboundSCAC());
				}
				
				//Set the Dest
				if(railspot.getOutboundFSAC() != null) {
					LocationType lt = new LocationType();
					lt.setType("FSAC");
					lt.setValue(railspot.getOutboundFSAC());
				}
			}
			
			//PCID
			if(vehicle.getVIN() != null) pcid.setValue(vehicle.getVIN());	
			pcid.setType("VIN");
			
			//Manufacturer
			Manufacturer manf = new Manufacturer();
			manf.setType("TADS");
			if(vehicle.getManufacturer() != null) manf.setValue(vehicle.getManufacturer());		
			pcid.setManufacturer(manf); 
			
			//INVLOC
			InventoryLocation invlocation = new InventoryLocation();
			if(vehicle.getSpot() != null) {
				invlocation.setSpot(vehicle.getSpot().toString().trim());	
			}
			if(vehicle.getRow() != null) invlocation.setRow(vehicle.getRow().trim());
			if(vehicle.getZone() != null) invlocation.setZone(vehicle.getZone().trim());
			if(vehicle.getLotCode() != null) invlocation.setLot(vehicle.getLotCode().trim());
			pcid.setInventoryLocation(invlocation);
		}
		
		return RcdispInvl.convertObjectToXML(this);
	}
	
}
