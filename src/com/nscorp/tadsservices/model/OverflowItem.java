package com.nscorp.tadsservices.model;

import java.util.Date;

import com.nscorp.tadsservices.model.DAO.OverflowItemDAO;

public class OverflowItem extends TADSLocationObject {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String StartLot;
	private String StartZone;
	private Integer SeqNbr;
	private String Lot;
	private String Zone;
	private String ChngWho;
	private Date ChngDate;
	
	OverflowItemDAO ofiDAO;
	// }}
	
	//Constructor
	public OverflowItem() {}
	
	public OverflowItem(Database database) {
		setLocationDatabase(database);
	};
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		ofiDAO = new OverflowItemDAO(database);
	}
	
	public String getStartLot() { return StartLot; }
	public void setStartLot(String StartLot) { this.StartLot = StartLot; }

	public String getStartZone() { return StartZone; }
	public void setStartZone(String StartZone) { this.StartZone = StartZone; }

	public Integer getSeqNbr() { return SeqNbr; }
	public void setSeqNbr(Integer seqNbr) { SeqNbr = seqNbr; }

	public String getLot() { return Lot; }
	public void setLot(String Lot) { this.Lot = Lot; }

	public String getZone() { return Zone; }
	public void setZone(String Zone) { this.Zone = Zone; }

	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho;	}

	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date chngDate) { ChngDate = chngDate; }
	// }}

}
