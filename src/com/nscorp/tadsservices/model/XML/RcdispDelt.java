package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.TADSJournalMessage;
import com.nscorp.tadsservices.model.XML.Confirmation;
import com.nscorp.tadsservices.model.XML.Header;
import com.nscorp.tadsservices.model.XML.LocationType;
import com.nscorp.tadsservices.model.XML.Site;
import com.nscorp.tadsservices.model.XML.SystemID;

@XmlRootElement(name="RCCHANGE")
@XmlType(propOrder= {"prodStat","action","header","site",
		"event","eventTime","railCar","originalRailcar"})
public class RcdispDelt extends TADSJournalMessage{
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	//data
	protected SystemSettings systemsettings;
	protected Railspot railspot;
	
	//attributes
	private String prodStat;
	private String action;
	
	//elements
	private Header header;
	private Site site;
	private Event event;
	private String eventTime;
	private RailCarDelt railCar;
	private RailcarBase originalRailcar;
	// }}
	
	public RcdispDelt() {
		header = new Header();
		site = new Site();
		event = new Event();
		railCar = new RailCarDelt();
		originalRailcar = new RailcarBase();
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

	public String getAction() {
		return action;
	}

	@XmlAttribute(name="ACTION")
	public void setAction(String action) {
		this.action = action;
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

	public Event getEvent() {
		return event;
	}

	@XmlElement(name="EVENT")
	public void setEvent(Event event) {
		this.event = event;
	}

	public String getEventTime() {
		return eventTime;
	}

	@XmlElement(name="EVENTTIME")
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public RailCarDelt getRailCar() {
		return railCar;
	}

	@XmlElement(name="RAILCAR")
	public void setRailCar(RailCarDelt railCar) {
		this.railCar = railCar;
	}

	public RailcarBase getOriginalRailcar() {
		return originalRailcar;
	}

	@XmlElement(name="ORIGINALRC")
	public void setOriginalRailcar(RailcarBase originalRailcar) {
		this.originalRailcar = originalRailcar;
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
		return "RcdispDelt [prodStat=" + prodStat 
				+ ", header=" + header + ", site=" 
				+ site + ", event=" + event
				+ ", eventTime=" + eventTime 
				+ ", railCar=" + railCar 
				+ ", originalRailcar=" 
				+ originalRailcar + "]";
	}

	@Override
	public String getXML() {
		//Get the System Settings, Vehicle, and Railcar
		if(systemsettings != null && railspot != null) {
	
			//ProdStat
			this.setProdStat("P");
						
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
			if(railspot.getChngWho() != null) header.setRacf(railspot.getChngWho());
	
			//Site
			site.setType("MC");
			if(systemsettings.getRampCode() != null) site.setValue(systemsettings.getRampCode());	
			if(systemsettings.getRampSPLC() != null) site.setSplc(systemsettings.getRampSPLC());
			if(systemsettings.getRampFSAC() != null) site.setFsac(systemsettings.getRampFSAC());
			
			//Event - set by caller
						
			//Event Time
			eventTime = Common.getCurrentDateTimeAsString();

			//Railcar
			if(railspot.getRailcarNumber() != null) {
				String railcar = railspot.getRailcarNumber();
				
				//Set the init
				railCar.setInit(Common.getRailcarInit(railcar));
				
				//Set the number
				railCar.setNum(Common.getRailcarNum(railcar));
				
				//Load / Empty
				if(railspot.getLoadEmptyStatus() != null) {
					railCar.setLe(railspot.getLoadEmptyStatus());	
				}
				
				//RCLoc
				if(railspot.getTrackNbr() != null) {
					Location rcLocation = new Location();
					rcLocation.setType("T");
					rcLocation.setZone(railspot.getZoneID().trim());
					rcLocation.setTrack(railspot.getTrackNbr().trim());
					rcLocation.setPosition(Integer.toString(railspot.getSpot()));
					railCar.setRcLocation(rcLocation);
				}
				
				//Set OriginalRailcar
				if(railspot.getRailcarNumber() != null) {
					String origrailcar = railspot.getRailcarNumber();
					originalRailcar.setInit(Common.getRailcarInit(origrailcar));
					originalRailcar.setNum(Common.getRailcarNum(origrailcar));
				}
			}

		}
		return RcdispDelt.convertObjectToXML(this);
	}
}
