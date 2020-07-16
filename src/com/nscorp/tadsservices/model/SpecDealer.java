package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.SpecDealerDAO;

public class SpecDealer extends TADSLocationObject {
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

	private SpecDealerDAO sDAO;
	private Database database;
	// }}
	
	//Constructor
	public SpecDealer() {}
	
	public SpecDealer(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		sDAO = new SpecDealerDAO(database);
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
	public SpecDealer get(String Manufacturer, String Dealer) {
		return sDAO.get(Manufacturer, Dealer);
	}
	
	public List<SpecDealer> getList(){
		return sDAO.getList();
	}
	
	public void Save() throws Exception {
		if(Validate()) sDAO.set(this);
	}
	
	public void Delete() {
		sDAO.delete(this);
	}
	
	private boolean Validate() throws Exception {
		boolean result = true;
		
		if(this.getManufacturer().isEmpty() || this.getCode().isEmpty()) {
			throw(new Exception("INVALID_SPEC_DEALER"));
		}
		
		//Check to make sure that a Dealer exists to match Special Dealer
		Dealer dealer = new Dealer(database);
		dealer = dealer.getDealer(this.getManufacturer(), this.getCode());
		
		if(dealer != null) {
			//Make sure a baying preference is specified
			if(this.getZone().isEmpty() || this.getLot().isEmpty()) {
				throw(new Exception("PREF_REQ_FOR_SPEC_DEALER"));
			}
		}
		
		return result;
	}
}
