package com.nscorp.tadsservices.model;

import java.util.Date;

import com.nscorp.tadsservices.model.DAO.OutboundJournalDAO;
import com.nscorp.tadsservices.model.SearchCriteria.OutboundJournalSearchCriteria;

public class OutboundJournal extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private Integer key;
	private String type;
	private String data;
	private String status;
	private String lastStatus;
	private String errormessage;
	private String chngWho;
	private Date chngDate;
	private OutboundJournalDAO outDAO;
	// }}
	
	public OutboundJournal() {}
	
	public OutboundJournal(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		outDAO = new OutboundJournalDAO(database);
	}	

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String Status) {
				this.status = Status;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String LastStatus) {
		this.lastStatus = LastStatus;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public String getChngWho() {
		return chngWho;
	}

	public void setChngWho(String chngWho) {
		this.chngWho = chngWho;
	}

	public Date getChngDate() {
		return chngDate;
	}

	public void setChngDate(Date chngDate) {
		this.chngDate = chngDate;
	}
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public OutboundJournal get(Integer recnum) {
		return outDAO.get(recnum);
	}
	
	public OutboundJournal get(OutboundJournalSearchCriteria osc) {
		return outDAO.get(osc);
	}
	
	public void save() {
		outDAO.set(this);
	}
	
	public void save(OutboundJournal obj) {
		outDAO.set(obj);
	}
	
	public void delete() {
		outDAO.delete(this);
	}
	


}
