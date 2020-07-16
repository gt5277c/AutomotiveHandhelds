package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.ProdStatDAO;

public class ProdStat extends TADSLocationObject {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Code;
	private String Description;
	private String Lot;
	private String Zone;
	private String Row;
	private String HoldManufacturer;
	private String HoldReasonCode;
	private String ChngWho;
	private Date ChngDate;

	private ProdStatDAO pDAO;
	// }}
	
	//Constructor
	public ProdStat() {}
	
	public ProdStat(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		pDAO = new ProdStatDAO(database);
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

	public String getHoldManufacturer() {
		return HoldManufacturer;
	}

	public void setHoldManufacturer(String holdManufacturer) {
		HoldManufacturer = holdManufacturer;
	}

	public String getHoldReasonCode() {
		return HoldReasonCode;
	}

	public void setHoldReasonCode(String holdReasonCode) {
		HoldReasonCode = holdReasonCode;
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
	public ProdStat get(String code) {
		return pDAO.get(code);
	}
	
	public List<ProdStat> getList(){
		return pDAO.getList();
	}
	
	public void Save() throws Exception {
		//validate
		this.validate();
		
		pDAO.set(this);
	}
	
	public void Delete() {
		pDAO.delete(this);
	}
	
	private boolean validate() throws Exception {
		boolean result = true;
		
		if(Code.isEmpty()) {
			throw(new Exception("INVALID_PROD_STATUS"));
		}
		
		if(Zone.isEmpty() || Lot.isEmpty()) {
			// - means need a baying preference for prodstat
			throw(new Exception("PREF_REQ_FOR_PROD_STAT"));
		}
		
		return result;
	}

}
