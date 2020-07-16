package com.nscorp.tadsservices.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.LotDAO;
import com.nscorp.tadsservices.model.Enums.eLotCode;

@XmlRootElement (name="Lot")
@Entity
public class Lot extends TADSLocationObject{
	private String TransmitSCAC = null;
	private String ActiveInd;
	static LotDAO lDAO;
	
	public Lot() {};
	
	public Lot(Database database) {
		setLocationDatabase(database);
	}
	
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		lDAO = new LotDAO(database);
	}
	public String getTransmitSCAC() { return TransmitSCAC; }
	public void setTransmitSCAC(String TransmitSCAC) { this.TransmitSCAC = TransmitSCAC; }
	public String getActiveInd() { return ActiveInd; }
	public void setActiveInd(String ActiveInd) { this.ActiveInd = ActiveInd; }

	public boolean IsLocation(eLotCode eLot, String Zone, String Row, Integer Spot) {
		boolean result = false;
		
		result = lDAO.IsLocation(eLot, Zone, Row, Spot);
		
		return result;
	}
}
