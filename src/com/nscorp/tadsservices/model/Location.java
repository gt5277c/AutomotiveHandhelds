package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.LocationDAO;

@XmlRootElement (name="Location")
@Entity
public class Location extends TADSLocationObject {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	//{{
	//Location 
	private String Lot;
	private String Zone;
	private String Row;
	private int Spot;
	
	//Vehicle Characteristics
	private String Manufacturer;
	private String VehicleType;
	private String Route;
	private String Dealer;
	private String VIN;
	
	//Bay Capacity
	private double FillFactor;
	private double UsedSpots;
	private double TotalSpots;
	
	//Status
	private boolean Active;
	private boolean EndBay;
	private boolean AllowOverflow;
	
	//Trucker
	private boolean TransmitToTrucker;
	private String TransmitSCAC;
	
	//Sort
	private int RowOrder;
	private int SpotOrder;
	
	//User
	private String ChngWho;
	private Date ChngDate;
	
	private LocationDAO lDAO;
	// }}
	
	//Constructor
	public Location() {};
	
	public Location(Database database) {
		this.setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		lDAO = new LocationDAO(database);
	}
	
	@XmlElement (name="Lot")
	public String getLot() { return Lot; }
	public void setLot(String lot) { Lot = lot; }

	@XmlElement (name="Zone")
	public String getZone() { return Zone; }
	public void setZone(String zone) { Zone = zone; }

	@XmlElement (name="Row")
	public String getRow() { return Row; }
	public void setRow(String row) { Row = row; }

	@XmlElement (name="Spot")
	public int getSpot() { return Spot; }
	public void setSpot(int spot) { Spot = spot; }

	@XmlElement (name="Manufacturer")
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String manufacturer) { Manufacturer = manufacturer;	}

	@XmlElement (name="VehicleType")
	public String getVehicleType() {return VehicleType; }
	public void setVehicleType(String vehicleType) {VehicleType = vehicleType; }

	@XmlElement (name="Route")
	public String getRoute() { return Route; }
	public void setRoute(String route) { Route = route; }

	@XmlElement (name="Dealer")
	public String getDealer() { return Dealer; }
	public void setDealer(String dealer) { Dealer = dealer;	}

	@XmlElement (name="Dealer")
	public double getFillFactor() { return FillFactor; }
	public void setFillFactor(double fillFactor) { FillFactor = fillFactor;	}

	@XmlElement (name="UsedSpots")
	public double getUsedSpots() { return UsedSpots; }
	public void setUsedSpots(double usedSpots) { UsedSpots = usedSpots; }

	@XmlElement (name="TotalSpots")
	public double getTotalSpots() { return TotalSpots; }
	public void setTotalSpots(double totalSpots) { TotalSpots = totalSpots;	}

	@XmlElement (name="Active")
	public boolean isActive() { return Active; }
	public void setActive(boolean active) { Active = active; }

	@XmlElement (name="EndBay")
	public boolean isEndBay() { return EndBay; }
	public void setEndBay(boolean endBay) { EndBay = endBay; }

	@XmlElement (name="TransmitToTrucker")
	public boolean isTransmitToTrucker() { return TransmitToTrucker; }
	public void setTransmitToTrucker(boolean transmitToTrucker) { TransmitToTrucker = transmitToTrucker; }

	@XmlElement (name="TransmitSCAC")
	public String getTransmitSCAC() { return TransmitSCAC; }
	public void setTransmitSCAC(String transmitSCAC) { TransmitSCAC = transmitSCAC;	}

	@XmlElement (name="RowOrder")
	public int getRowOrder() { return RowOrder; }
	public void setRowOrder(int rowOrder) { RowOrder = rowOrder; }

	@XmlElement (name="SpotOrder")
	public int getSpotOrder() { return SpotOrder; }
	public void setSpotOrder(int spotOrder) { SpotOrder = spotOrder; }

	@XmlElement (name="ChngWho")
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho; }

	@XmlElement (name="ChngDate")
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date chngDate) { ChngDate = chngDate; }
	
	@XmlElement (name="AllowOverflow")
	public boolean isAllowOverflow() { return AllowOverflow; }
	public void setAllowOverflow(boolean allowOverflow) { AllowOverflow = allowOverflow; }

	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Location getLocation(String sqlName) {
		return lDAO.getLocation(sqlName,this);
	}
	
	public List<Location> getLastLocationUpdated() {
		return lDAO.getLastLocationUpdated();
	}
	
	public List<Location> getLocationList(String sqlName) throws SQLException {
		return lDAO.getLocationList(sqlName,this);
	}
	
	public void setLocation(String sqlName) {
		lDAO.set(sqlName,this);
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}
}
