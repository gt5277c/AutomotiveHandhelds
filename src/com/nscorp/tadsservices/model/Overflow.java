package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.OverflowItemDAO;

public class Overflow extends TADSLocationObject{
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private String ChngWho;
	private Date ChngDate;
	private OverflowItemDAO ofiDAO;
	private Database database;
	
	//Constructor
	public Overflow() {}
	
	public Overflow(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		ofiDAO = new OverflowItemDAO(database);
	}
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho; }
	
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date chngDate) { ChngDate = chngDate; }
	
	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////
	public void AddOverflowSeqItem(String Lot, String Zone, String OverflowLot, String OverflowZone, int SeqNbr) {
		OverflowItem ofi  = new OverflowItem(database);
		ofi.setStartLot(Lot);
		ofi.setStartZone(Zone);
		ofi.setSeqNbr(SeqNbr);
		ofi.setLot(OverflowLot);
		ofi.setZone(OverflowZone);
		
		AddOverflowSeqItem(ofi);
	}
	
	public void AddOverflowSeqItem(OverflowItem ofi) {
		ofiDAO.set(ofi);
	}
	
	public void DeleteOverflowItem(String StartLot, String StartZone) {
		OverflowItem ofi  = new OverflowItem(database);
		ofi.setStartLot(StartLot);
		ofi.setStartZone(StartZone);
		
		DeleteOverflowItem(ofi);
	}
	
	public void DeleteOverflowItem(OverflowItem ofi) {
		ofiDAO.delete(ofi);
	}
	
	public List<OverflowItem> GetOverflowSeq(String StartLot, String StartZone){
		OverflowItem ofi  = new OverflowItem(database);
		ofi.setStartLot(StartLot);
		ofi.setStartZone(StartZone);

		return ofiDAO.getList(ofi);
	}	
}
