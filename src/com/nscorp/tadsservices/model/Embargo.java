package com.nscorp.tadsservices.model;

import java.util.Date;

import com.nscorp.tadsservices.model.DAO.EmbargoDAO;

public class Embargo extends TADSLocationObject{

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Action;
	private String Railcar;
	private String Embargo;
	private String Permit;
	private String ChngWho;
	private Date ChngDate;
	
	private EmbargoDAO eDAO;
	// }}
	
	public Embargo() {}

	public Embargo(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		eDAO = new EmbargoDAO(database);
	}
	
	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
	}

	public String getRailcar() {
		return Railcar;
	}

	public void setRailcar(String railcar) {
		Railcar = railcar;
	}

	public String getEmbargo() {
		return Embargo;
	}

	public void setEmbargo(String embargo) {
		Embargo = embargo;
	}

	public String getPermit() {
		return Permit;
	}

	public void setPermit(String permit) {
		Permit = permit;
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
	public Embargo get(String railcar) {
		return eDAO.get(railcar);
	}
	
	public void Delete() {
		eDAO.delete(this);
	}
}
