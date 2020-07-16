package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.Vehicle;

@XmlRootElement(name="INVDISP")
@XmlType(propOrder= {"event","pcid"})
public class INVDispMessage extends VehicleBase {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String event;
	private PieceIdentificationInvdisp pcid;
	// }}
	
	//Constructor
	public INVDispMessage() {
		super();
		
		pcid = new PieceIdentificationInvdisp();
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{

	@XmlElement(name="EVENTTYPE")
	public void setEvent(String event) { this.event = event; }
	public String getEvent() { return event; }
	
	@XmlElement(name="PCID")
	public void setPcid(PieceIdentificationInvdisp pcid) { this.pcid = pcid; }	
	public PieceIdentificationInvdisp getPcid() { return pcid; }

	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	@Override
	public void setVehicle(Vehicle vehicle) {
		super.setVehicle(vehicle);
	}
	
	@Override
	public void setSystemSettings(SystemSettings systemsettings) {
		super.setSystemSettings(systemsettings);
	}
	
	@Override
	public String getXML() {
		super.getXML();
		
		//Get the Vehicle and System Settings
		if(systemsettings != null && vehicle != null) {
			//Header
			if(vehicle.getChngWho() != null) {
				this.getHeader().setRacf(vehicle.getChngWho());
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
			
			//TRANSMITTOTRUCKER
			if(systemsettings.getUseTransmitToTrucker() != null && systemsettings.getUseTransmitToTrucker().equals("Y")) {
				if(vehicle.TransmitToTrucker()) {
					pcid.setTransmitToTrucker("Y");
					if(vehicle.getOutboundSCAC() != null) pcid.setTransmitToTruckerSCAC(vehicle.getOutboundSCAC());
				} else {
					pcid.setTransmitToTrucker("N");
				}
			}
			
			//Route
			if(vehicle.getRoute() != null) pcid.setRoute(vehicle.getRoute());
			
			//Diversion
			if(vehicle.getDiversion() != null) pcid.setDiversion(vehicle.getDiversion());
			
			//Controller Code - Dealer
			Controller controller = new Controller();
			controller.setType("DLR");
			if(vehicle.getDealer() != null) controller.setValue(vehicle.getDealer());
			pcid.setController(controller);
			
			//Color
			Color color = new Color();
			if(vehicle.getColorCode() != null) color.setCode(vehicle.getColorCode());
			if(vehicle.getManufacturer() != null) color.setManufacturerName(vehicle.getManufacturer());
			color.setColor("");
			pcid.setColor(color);				
			
		}
		
		return INVDispMessage.convertObjectToXML(this);
	}
}
