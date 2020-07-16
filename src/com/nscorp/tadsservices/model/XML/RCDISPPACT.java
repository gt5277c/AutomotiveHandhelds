package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Railcar;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.XML.Confirmation;
import com.nscorp.tadsservices.model.XML.Header;
import com.nscorp.tadsservices.model.XML.Site;
import com.nscorp.tadsservices.model.XML.SystemID;


@XmlRootElement(name="RCDISP")
@XmlType(propOrder= {"railCar"})
public class RCDISPPACT extends RCDISPBase {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private RailcarPACT railCar;
	
	//Data
	private Railspot railspot;
	private SystemSettings systemsettings;
	// }}
	
	//Constructor
	public RCDISPPACT() {
		super();
		railCar = new RailcarPACT();
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public RailcarPACT getRailCar() {
		return railCar;
	}

	@XmlElement(name="RAILCAR")
	public void setRailCar(RailcarPACT railCar) {
		this.railCar = railCar;
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

	@Override
	public String getXML() {	
		//Get the Railspot and System Settings
		if(systemsettings != null && railspot != null) {

			//Setup RCDISPBase
			
			//PRODSTAT
			this.setProdStat("P");
			
			//Header
			Header h = this.getHeader();
			SystemID orig = new SystemID();
			orig.setId("TADS");
			if(systemsettings.getRampCode() != null) orig.setNode(systemsettings.getRampCode());
			h.setOriginSystem(orig);
			
			SystemID dest = new SystemID();
			dest.setId("");
			dest.setNode("");
			h.setDestinationSystem(dest);
			
			Confirmation confirmation = new Confirmation();
			confirmation.setCode("1");
			if(systemsettings.getConfirmMessage() != null) confirmation.setValue(systemsettings.getConfirmMessage());
			h.setConfirmation(confirmation);
			if(railspot.getChngWho() != null) h.setRacf(railspot.getChngWho());
			this.setHeader(h);
			
			//Site
			Site s = this.getSite();
			s.setType("MC");
			if(systemsettings.getRampCode() != null) s.setValue(systemsettings.getRampCode());	
			if(systemsettings.getRampSPLC() != null) s.setSplc(systemsettings.getRampSPLC());
			if(systemsettings.getRampFSAC() != null) s.setFsac(systemsettings.getRampFSAC());
			this.setSite(s);
			
			//groupSiteOrigin
			if(systemsettings.getRampCode() != null) this.setGroupSiteOrigin(systemsettings.getRampCode());
			
			//Event - Will be set in the calling function
			//Example PACT, RMTY, MOVE, LOAD, RLOD, PFPS, PARK
			//		Set a String for eventname as convienence
			String eventname = null;
			if(this.getEvent() != null && this.getEvent().getValue() != null) {
				Event e = this.getEvent();
				eventname = e.getValue();
				e.block = "";
				e.classValue = "";
				e.reason = "";
				this.setEvent(e);
			}
			
			//Event Time
			if(railspot.getChngDate() != null) {
				this.setEventTime(Common.getDateTime(railspot.getChngDate()));
			} else {
				this.setEventTime(Common.getCurrentDateTimeAsString());
			}
			
			//RailcarBase
			String[] railcarinitnum = railspot.getRailcarNumber().split("(?<=\\D)(?=\\d)");
			railCar.setInit(railcarinitnum[0]);
			railCar.setNum(railcarinitnum[1]);
			
			//Railcar
			//	Get a Railcar from the Railspot
			Railcar tadsrailcar = railspot.GetRailcar(railspot.getRailcarNumber());
			
			// aarType
			if(tadsrailcar.getType() != null) railCar.setAarType(tadsrailcar.getType());
			
			// type (Deck Type)
			if(tadsrailcar.getDeckType() != null) railCar.setType(tadsrailcar.getDeckType());
			
			// lights
			if(railspot.getHeadLight() != null) {
				railCar.setLights(railspot.getHeadLight());
			}else{
				railCar.setLights("");
			}
			
			// embargo;
			if(railspot.getOutboundEmbargo() != null) {
				Embargo e = railCar.getEmbargo();
				e.setValue(railspot.getOutboundEmbargo());
				e.setPermit(railspot.getOutboundPermit());
				railCar.setEmbargo(e);
			}
			
			// le
			if(railspot.getLoadEmptyStatus() != null) {
				railCar.setLe(railspot.getLoadEmptyStatus());
			}
			
			// rcLocation
			if(railspot.getLotCode() != null) {
				Location l = railCar.getRcLocation();
				if(railspot.getLotCode() != null) l.setType(railspot.getLotCode().trim());
				if(railspot.getZoneID() != null) l.setZone(railspot.getZoneID().trim());
				if(railspot.getTrackNbr() != null) l.setTrack(railspot.getTrackNbr().trim());
				if(railspot.getSpot() > 0) l.setPosition(Integer.toString(railspot.getSpot()));
				railCar.setRcLocation(l);
			}
			
			// Set waybill information based on railcar status
			if(railspot.getStatusCode() != null) {
				String statuscode = railspot.getStatusCode();
				
				if(statuscode.equals(Constants.STAT_RAIL_ARRIVEDRAIL) || 
						statuscode.equals(Constants.STAT_RAIL_INSUPPORTYARD) ||
						statuscode.equals(Constants.STAT_RAIL_ASSIGNED)) {
					
					//Inbound
					// wbsn;
					if(railspot.getInboundWayBillSN() != null) railCar.setWbsn(railspot.getInboundWayBillSN());
					
					// waybill;
					if(railspot.getInboundWayBill() != null) railCar.setWaybill(railspot.getInboundWayBill());
					
					// bol;
					if(railspot.getInboundBOL() != null) railCar.setBol(railspot.getInboundBOL());
					
					// scac;
					if(railspot.getInboundSCAC() != null) railCar.setBol(railspot.getInboundSCAC());
					
				}else if(statuscode.equals(Constants.STAT_RAIL_RAILSHIPPED) ||
						(eventname != null && eventname.equals(Constants.eXMLRailcarEventCode_LOAD)) ||
						(eventname != null && eventname.equals(Constants.eXMLRailcarEventCode_BOL))){
					
					//Outbound
					// wbsn;
					if(railspot.getOutboundWayBillSN() != null) railCar.setWbsn(railspot.getOutboundWayBillSN());
					
					// waybill;
					if(railspot.getOutboundWayBill() != null) railCar.setWaybill(railspot.getOutboundWayBill());
					
					// bol;
					if(railspot.getOutboundBOL() != null) railCar.setBol(railspot.getOutboundBOL());
					
					// scac;
					if(railspot.getOutboundSCAC() != null) railCar.setBol(railspot.getOutboundSCAC());
				}
			}
		
			//RailcarPACT
			// previousLocation based on Event
			if(eventname != null) {
				if(eventname.equals(Constants.eXMLRailcarEventCode_PACT) ||
						eventname.equals(Constants.eXMLRailcarEventCode_MOVE) ||
						eventname.equals(Constants.eXMLRailcarEventCode_PFPS)) {
					
					Location l = railCar.getPreviousLocation();
					if(railspot.getPrevLotCode() != null) l.setType(railspot.getPrevLotCode());
					if(railspot.getPrevZoneID() != null) l.setZone(railspot.getPrevZoneID());
					if(railspot.getPrevTrackNbr() != null) l.setTrack(railspot.getPrevTrackNbr());
					if(railspot.getPrevSpot() > 0) l.setPosition(Integer.toString(railspot.getPrevSpot()));
					railCar.setPreviousLocation(l);
				}
			}
		}
		return RCDISPPACT.convertObjectToXML(this);
	}
}
