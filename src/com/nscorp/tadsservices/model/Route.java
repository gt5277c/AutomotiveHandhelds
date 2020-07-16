package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.RouteDAO;
import com.nscorp.tadsservices.model.Enums.eRouteObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.RouteSearchCriteria;

@XmlRootElement (name="Route")
@Entity
public class Route extends TADSLocationObject {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Manufacturer;
	private String ManufacturerName;
	private String ManufacturerAbbrev;
	private String Route;
	private String StationName;
	private String Address;
	private String City;
	private String State;
	private String ZIP;
	private String TrainNbr;
	private String Zone;
	private String BillingRoute;
	private boolean RailRoute;
	private String SCAC;
	private String FSAC;
	private String STCC;
	private String RevenueRWC;
	private String NonRevenueRWC;
	private String ReceivingRailroad;
	private String RailJunction;
	private String DeliveryRailRoad;
	private boolean MixRoute;
	private boolean RestrictedRoute;
	private boolean VINCheck;
	private String ChngWho;
	private Date ChngDate;
	
	private RouteDAO rDAO;
	// }}
	
	//Constructor
	public Route() {}
	
	public Route(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		rDAO = new RouteDAO(database);
	}
	
	@XmlElement (name="Manufacturer")
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String manufacturer) { Manufacturer = manufacturer; }

	@XmlElement (name="ManufacturerName")
	public String getManufacturerName() { return ManufacturerName; }
	public void setManufacturerName(String manufacturerName) { ManufacturerName = manufacturerName; }
	
	@XmlElement (name="Route")
	public String getRoute() { return Route; }
	public void setRoute(String route) { Route = route; }

	@XmlElement (name="StationName")
	public String getStationName() { return StationName; }
	public void setStationName(String stationName) { StationName = stationName; }

	@XmlElement (name="City")
	public String getCity() { return City; }
	public void setCity(String city) { City = city;	}

	@XmlElement (name="State")
	public String getState() { return State; }
	public void setState(String state) { State = state; }

	@XmlElement (name="TrainNbr")
	public String getTrainNbr() { return TrainNbr; }
	public void setTrainNbr(String trainNbr) { TrainNbr = trainNbr; }

	@XmlElement (name="Zone")
	public String getZone() { return Zone; }
	public void setZone(String zone) { Zone = zone; }

	@XmlElement (name="BillingRoute")
	public String getBillingRoute() { return BillingRoute; }
	public void setBillingRoute(String billingRoute) { BillingRoute = billingRoute; }

	@XmlElement (name="RailRoute")
	public boolean isRailRoute() { return RailRoute; }
	public void setRailRoute(boolean railRoute) { RailRoute = railRoute; }

	@XmlElement (name="SCAC")
	public String getSCAC() { return SCAC; }
	public void setSCAC(String sCAC) { SCAC = sCAC; }

	@XmlElement (name="FSAC")
	public String getFSAC() { return FSAC; }
	public void setFSAC(String fSAC) { FSAC = fSAC; }

	@XmlElement (name="STCC")
	public String getSTCC() { return STCC; }
	public void setSTCC(String sTCC) { STCC = sTCC; }
	
	@XmlElement (name="RevenueRWC")
	public String getRevenueRWC() {	return RevenueRWC; }
	public void setRevenueRWC(String revenueRWC) { RevenueRWC = revenueRWC; }

	@XmlElement (name="NonRevenueRWC")
	public String getNonRevenueRWC() { return NonRevenueRWC; }
	public void setNonRevenueRWC(String nonRevenueRWC) { NonRevenueRWC = nonRevenueRWC;	}

	@XmlElement (name="ReceivingRailroad")
	public String getReceivingRailroad() { return ReceivingRailroad; }
	public void setReceivingRailroad(String receivingRailroad) { ReceivingRailroad = receivingRailroad; }

	@XmlElement (name="RailJunction")
	public String getRailJunction() { return RailJunction; }
	public void setRailJunction(String railJunction) { RailJunction = railJunction; }

	@XmlElement (name="DeliveryRailRoad")
	public String getDeliveryRailRoad() { return DeliveryRailRoad; }
	public void setDeliveryRailRoad(String deliveryRailRoad) { DeliveryRailRoad = deliveryRailRoad; }

	@XmlElement (name="MixRoute")
	public boolean isMixRoute() { return MixRoute; }
	public void setMixRoute(boolean mixRoute) { MixRoute = mixRoute; }

	@XmlElement (name="RestrictedRoute")
	public boolean isRestrictedRoute() { return RestrictedRoute; }
	public void setRestrictedRoute(boolean restrictedRoute) { RestrictedRoute = restrictedRoute; }

	@XmlElement (name="ChngWho")
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho; }

	@XmlElement (name="ChngDate")
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date chngDate) { ChngDate = chngDate; }
	
	@XmlElement (name="ManufacturerAbbrev")
	public String getManufacturerAbbrev() { return ManufacturerAbbrev; }
	public void setManufacturerAbbrev(String manufacturerAbbrev) { ManufacturerAbbrev = manufacturerAbbrev; }

	@XmlElement (name="Address")
	public String getAddress() { return Address; }
	public void setAddress(String address) { Address = address; }

	@XmlElement (name="ZIP")
	public String getZIP() { return ZIP; }
	public void setZIP(String zIP) { ZIP = zIP; }

	@XmlElement (name="VINCheck")
	public boolean isVINCheck() { return VINCheck; }
	public void setVINCheck(boolean vINCheck) { VINCheck = vINCheck; }

	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Route getRoute(String Manufacturer, String code) {
		RouteSearchCriteria rsc = new RouteSearchCriteria();
		rsc.setSearchType(eRouteObjAction.eRouteFindByMfgRoute);
		rsc.setManufacturer(Manufacturer);
		rsc.setRoute(code);
		return rDAO.get(rsc);
	}
	
	public Route getRoute(RouteSearchCriteria rsc) {
		return rDAO.get(rsc);
	}
	
	public List<Route> getRouteList(RouteSearchCriteria rsc) throws SQLException{
		return rDAO.getList(rsc);
	}
	
	public List<Route> getRouteManufacturer(String Manufacturer) throws SQLException{
		RouteSearchCriteria rsc = new RouteSearchCriteria();
		rsc.setSearchType(eRouteObjAction.eRouteFindByMfg);
		rsc.setManufacturer(Manufacturer);
		return rDAO.getList(rsc);
	}
	
	public List<Route> getRouteManufacturer(RouteSearchCriteria rsc) throws SQLException{
		return rDAO.getList(rsc);
	}
	
	public List<Route> getAllRoutes() throws SQLException{
		RouteSearchCriteria rsc = new RouteSearchCriteria();
		rsc.setSearchType(eRouteObjAction.eRouteFindAll);
		return rDAO.getList(rsc);
	}
	
	public Route getLastRouteUpdated() {
		return rDAO.getLastRouteUpdated();
	}
	
	public void Save() {
		rDAO.set(this);
	}
	
	public void Delete() {
		rDAO.delete(this);
	}

}
