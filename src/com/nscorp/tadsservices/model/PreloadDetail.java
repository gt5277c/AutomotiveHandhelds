package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.PreloadDetailDAO;

public class PreloadDetail extends TADSLocationObject{

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Code;
	private String VIN;
	private String StatusCode;
	private String ActionCode;
	private String LoadedDate;
	private String Chngwho;
	private Date Chngdate;
	
	private PreloadDetailDAO predetailDAO;
	// }}
	
	//Constructor
	public PreloadDetail() {}

	public PreloadDetail(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		predetailDAO = new PreloadDetailDAO(database);
	}
	
	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}

	public String getActionCode() {
		return ActionCode;
	}

	public void setActionCode(String actionCode) {
		ActionCode = actionCode;
	}

	public String getLoadedDate() {
		return LoadedDate;
	}

	public void setLoadedDate(String loadedDate) {
		LoadedDate = loadedDate;
	}

	public String getChngwho() {
		return Chngwho;
	}

	public void setChngwho(String chngwho) {
		Chngwho = chngwho;
	}

	public Date getChngdate() {
		return Chngdate;
	}

	public void setChngdate(Date chngdate) {
		Chngdate = chngdate;
	};
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////

	public PreloadDetail getPreloadVehicle(String VIN) {
		return predetailDAO.get(VIN);
	}
	
	public PreloadDetail getPreloadVehicle(String code, String VIN){
		return predetailDAO.get(code,VIN);
	}
	
	public PreloadDetail getPreloadActiveVehicle(String VIN) {
		return predetailDAO.getActive(VIN);
	}
		
	public void updateLoaded() {
		predetailDAO.updateLoaded(this);
	}

	public void udpateUnLoaded() {
		predetailDAO.updateUnLoaded(this);
	}

	public List<PreloadDetail> getDataAllByCodeStatus(String code, String status) throws SQLException {
		return predetailDAO.getDataAllByCodeStatus(code, status);
	}
	
	public List<PreloadDetail> getDataAllByCode(String code) throws SQLException {
		return predetailDAO.getDataAllByCode(code);
	}

	public void Save() {
		predetailDAO.set(this);
	}

	public void Delete() {
		predetailDAO.delete(this);
	}
}
