package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.SealDAO;

public class Seal extends TADSLocationObject{

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private double key;
	private String railcar;
	private String waybill;
	private String seal1;
	private String seal2;
	private String seal3;
	private String seal4;
	private String comment;
	private String verified;
	private String ChangeWho;
	private Date ChangeDate;
	
	private SealDAO sealDAO;
	// }}
	
	//Constructor
	public Seal() {}

	public Seal(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		sealDAO = new SealDAO(database);
	}
	
	public double getKey() {
		return key;
	}
	
	public void setKey(double key) {
		this.key = key;
	}
	
	public String getRailcar() {
		return railcar;
	}

	public void setRailcar(String railcar) {
		this.railcar = railcar;
	}

	public String getWaybill() {
		return waybill;
	}

	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}

	public String getSeal1() {
		return seal1;
	}

	public void setSeal1(String seal1) {
		this.seal1 = seal1;
	}

	public String getSeal2() {
		return seal2;
	}

	public void setSeal2(String seal2) {
		this.seal2 = seal2;
	}

	public String getSeal3() {
		return seal3;
	}

	public void setSeal3(String seal3) {
		this.seal3 = seal3;
	}

	public String getSeal4() {
		return seal4;
	}

	public void setSeal4(String seal4) {
		this.seal4 = seal4;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getChangeWho() {
		return ChangeWho;
	}

	public void setChangeWho(String changeWho) {
		ChangeWho = changeWho;
	}

	public Date getChangeDate() {
		return ChangeDate;
	}

	public void setChangeDate(Date changeDate) {
		ChangeDate = changeDate;
	}	
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	// {{
	
	public void Save() {
		if(ValidSeal()) {
			sealDAO.set(this);
		}
	}
		
	public void Delete() {
		if(ValidSeal()) {
        	sealDAO.delete(this);
		}
	}
	
	//Find Oldest
	//Set rs = ExecuteSP2("spFindSealNumAscByRCWB", sRC, sWB)
	public Seal findOldest() {
		return sealDAO.getOldest(this);
	}
	
	//Find Newest
	//Set rs = ExecuteSP2("spFindSealNumDescByRCWB", sRC, sWB)
	public Seal findNewest() {
		return sealDAO.getNewest(this);
	}
	
	//GetAllSeals
	//Set rs = ExecuteSP2("spGetAllSealNum")
	public List<Seal> getAll() throws SQLException{
		return sealDAO.getAll();
	}
	
	//'ensure that the mandatory fields are populated.
	private boolean ValidSeal() {
		boolean result = true;
		
		if(railcar == null || railcar.length() > 1) {
			//Err.Raise INVALID_RAILCAR_NUM
		}
		
		if(waybill == null || waybill.length() < 1) {
			//Err.Raise INVALID_PARAMETER
		}
		
		return result;
	}
	// }}
}
