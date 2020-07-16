package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.Vehicle;

@XmlRootElement(name="TRUCKIN")
@XmlType(propOrder= {"eventTime","scac","plantIn","probill",
		"originShipment","pcid"})
public class TruckInMessage extends VehicleBase  {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String eventTime;
	private String scac;
	private String plantIn;
	private String probill = "";
	private Shipment originShipment = new Shipment();
	private PieceIdentification pcid;
	// }}
	
	//Constructor
	public TruckInMessage() {
		super();
		
		pcid = new PieceIdentification();
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public String getEventTime() {
		return eventTime;
	}

	@XmlElement(name="EVENTTIME")
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getScac() {
		return scac;
	}

	@XmlElement(name="SCAC")
	public void setScac(String scac) {
		this.scac = scac;
	}

	public String getProbill() {
		return probill;
	}

	@XmlElement(name="PROBILL")
	public void setProbill(String probill) {
		this.probill = probill;
	}

	public Shipment getOriginShipment() {
		return originShipment;
	}

	@XmlElement(name="ORIG")
	public void setOriginShipment(Shipment originShipment) {
		this.originShipment = originShipment;
	}

	public PieceIdentification getPcid() {
		return pcid;
	}

	@XmlElement(name="PCID")
	public void setPcid(PieceIdentification pcid) {
		this.pcid = pcid;
	}

	public String getPlantIn() {
		return plantIn;
	}

	@XmlElement(name="PLANTIN")
	public void setPlantIn(String plantIn) {
		this.plantIn = plantIn;
	}	
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
			
			//Event Time
			if(vehicle.getChngDate() != null) {
				eventTime = Common.getDateTime(vehicle.getChngDate());
			}
			
			//SCAC
			if(vehicle.getInboundSCAC() != null) scac = vehicle.getInboundSCAC();
			
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
			if(vehicle.getColorCode() != null) {
				color.setCode(vehicle.getColorCode());
			} else {
				color.setCode("");
			}
			if(vehicle.getManufacturer() != null) color.setManufacturerName(vehicle.getManufacturer());
			color.setColor("");
			pcid.setColor(color);			
		}
		
		return TruckInMessage.convertObjectToXML(this);
	}
}
