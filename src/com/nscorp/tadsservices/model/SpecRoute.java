package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.SpecRouteDAO;
import com.nscorp.tadsservices.model.Enums.eRouteObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.RouteSearchCriteria;

public class SpecRoute extends TADSLocationObject{
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Manufacturer;
	private String Code;
	private String Description;
	private String Lot;
	private String Zone;
	private String Row;
	private String ChngWho;
	private Date ChngDate;
	
	private SpecRouteDAO pDAO;
	private Database database;
	// }}
	
	//Constructor
	public SpecRoute() {}
	
	public SpecRoute(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		pDAO = new SpecRouteDAO(database);
	}
	public String getManufacturer() {
		return Manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getLot() {
		return Lot;
	}

	public void setLot(String lot) {
		Lot = lot;
	}

	public String getZone() {
		return Zone;
	}

	public void setZone(String zone) {
		Zone = zone;
	}

	public String getRow() {
		return Row;
	}

	public void setRow(String row) {
		Row = row;
	}

	public String getChngWho() {
		return ChngWho;
	}

	public void setChngWho(String chngWho) {
		ChngWho = chngWho;
	}

	public Date getChngDate() {
		return ChngDate;
	}

	public void setChngDate(Date chngDate) {
		ChngDate = chngDate;
	}	
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public SpecRoute get(String Manufacturer, String Route) {
		return pDAO.get(Manufacturer, Route);
	}
	
	public List<SpecRoute> getList(){
		return pDAO.getList();
	}
	
	public void Save() throws Exception {
		if(Validate()) pDAO.set(this);
	}
	
	public void Delete() {
		pDAO.delete(this);
	}
	
	private boolean Validate() throws Exception {
		boolean result = true;
		
		if(this.getManufacturer().isEmpty() || this.getCode().isEmpty()) {
			throw(new Exception("INVALID_SPEC_ROUTE"));
		}
		
		//Check to make sure that a Route exists to match the Special Route
		Route route = new Route(database);
		RouteSearchCriteria rsc = new RouteSearchCriteria();
		rsc.setSearchType(eRouteObjAction.eRouteFindByMfgRoute);
		rsc.setManufacturer(this.getManufacturer());
		rsc.setRoute(this.getCode());
		route = route.getRoute(rsc);
		
		if(route != null) {
			//Make sure a baying preference is specified
			if(this.getZone().isEmpty() || this.getLot().isEmpty()) {
				throw(new Exception("PREF_REQ_FOR_SPEC_ROUTE"));
			}
		}
		
		return result;
	}
	
}
