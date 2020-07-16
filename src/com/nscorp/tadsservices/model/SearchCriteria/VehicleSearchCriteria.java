package com.nscorp.tadsservices.model.SearchCriteria;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.Enums.eVINObjAction;
import com.nscorp.tadsservices.model.Vehicle;

@XmlRootElement(name = "VehicleSearchCriteria")
@Entity
public class VehicleSearchCriteria {
		
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private eVINObjAction SearchType = eVINObjAction.eVINFindVIN;
	private String VIN; 
	private String Manufacturer;
	
	//Destination
	private String Route;
	private String Dealer;
	
	//State
	private String StatusCode;
	private String ActionCode;
	
	//Location
	private String LotCode;
	private String Zone;
	private String Row;
	private Integer Spot;
	
	//Railcar
	private String RailcarNumber;
	private String InboundRailcarNumber;
	private String OutboundRailcarNumber;
	// }}

	//Constructor
	public VehicleSearchCriteria() { }
	
	public VehicleSearchCriteria(eVINObjAction SearchType, Vehicle vehicle) {
		this.SearchType = SearchType;
		this.VIN = vehicle.getVIN();
		this.Manufacturer = vehicle.getManufacturer();
		this.Route = vehicle.getRoute();
		this.Dealer = vehicle.getDealer();
		this.StatusCode = vehicle.getStatusCode();
		this.ActionCode = vehicle.getActionCode();
		this.LotCode = vehicle.getLotCode();
		this.Zone = vehicle.getZone();
		this.Row = vehicle.getRow();
		this.Spot = vehicle.getSpot();
		this.InboundRailcarNumber = vehicle.getInboundRailcarNumber();
		this.OutboundRailcarNumber = vehicle.getOutboundRailcarNumber();
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{	
	@XmlElement (name="VIN")
	public String getVIN() { return VIN; }
	public void setVIN(String VIN) { this.VIN = VIN; }
	
	@XmlElement (name="Manufacturer")
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String Manufacturer)  { this.Manufacturer = Manufacturer; }
	
	@XmlElement (name="Route")
	public String getRoute() { return Route; }
	public void setRoute(String Route)  { this.Route = Route; }

	@XmlElement (name="Dealer")
	public String getDealer() { return Dealer; }
	public void setDealer(String Dealer)  { this.Dealer = Dealer; }
	
	@XmlElement (name="StatusCode")
	public String getStatus() { return StatusCode; }
	public void setStatus(String Status) { this.StatusCode = Status; }

	@XmlElement (name="ActionCode")
	public String getActionCode() { return ActionCode; }
	public void setActionCode(String ActionCode) { this.ActionCode = ActionCode; }

	@XmlElement (name="LotCode")
	public String getLotCode() { return LotCode; }
	public void setLotCode(String LotCode)  { this.LotCode = LotCode; }

	@XmlElement (name="Zone")
	public String getZone() { return Zone; }
	public void setZone(String Zone) { this.Zone = Zone; }

	@XmlElement (name="Row")
	public String getRow() { return Row; }
	public void setRow(String Row)  { this.Row = Row; }

	@XmlElement (name="Spot")
	public Integer getSpot() { return Spot; }
	public void setSpot(Integer Spot) { this.Spot = Spot; }

	@XmlElement (name="RailcarNumber")
	public String getRailcarNumber() { return RailcarNumber; }
	public void setRailcarNumber(String RailcarNumber) { 
		//Default to the InboundRailcarNumber being set also
		this.RailcarNumber = RailcarNumber; 
		this.InboundRailcarNumber = RailcarNumber;
	}

	@XmlElement (name="InboundRailcarNumber")
	public String getInboundRailcarNumber() { return InboundRailcarNumber; }
	public void setInboundRailcarNumber(String InboundRailcarNumber) { this.InboundRailcarNumber = InboundRailcarNumber; }

	@XmlElement (name="OutboundRailcarNumber")
	public String getOutboundRailcarNumber() { return OutboundRailcarNumber; }
	public void setOutboundRailcarNumber(String OutboundRailcarNumber) { this.OutboundRailcarNumber = OutboundRailcarNumber; }

	@XmlElement (name="SearchType")
	public eVINObjAction getSearchType() { return SearchType; }
	public void setSearchType(eVINObjAction SearchType) { this.SearchType = SearchType; }
	//}}
	
	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	// {{
	public Object[] getParameters() {
		Object[] params = null;
		
		switch(getSearchType()) {
		case eVINFindVIN :
			params = new Object[] {getVIN()};
			break;
		case eVINFindVINLike:
			//Need to handle the case where only last 8 are sent
			String vin = getVIN();
			if(vin != null && vin.length() <= 8 ) vin = "%" + vin;
			params = new Object[] {vin};
			break;
		case eVINFindDealer:
			params = new Object[] {getDealer()};
			break;
		case eVINFindRoute:
			params = new Object[] {getRoute()};
			break;
		case eVINFindLot:
			params = new Object[] {getLotCode()};
			break;
		case eVINFindZone:
			params = new Object[] {getLotCode(),getZone()};
			break;
		case eVINFindArea:
			params = new Object[] {getLotCode(),getZone(),getRow()};
			break;
		case eVINFindSpot:
			params = new Object[] {getLotCode(),getZone(),getRow(),getSpot()};
			break;			
		case eVINFindRailCar:
		case eVINFindInboundRailCar:
		case eVINFindOutboundRailCar:
		case eVINFindRailCarLike:
		case eVINFindRailcarLTDs:
			params = new Object[] {getInboundRailcarNumber(),getOutboundRailcarNumber()};
			break;
		case eVINFindRailCarASN:
			params = new Object[] {getInboundRailcarNumber() == null ? getRailcarNumber() : getInboundRailcarNumber()};
		default:
			break;
		}

		return params;
	}
	// }}
}
