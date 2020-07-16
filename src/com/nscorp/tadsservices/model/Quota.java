package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.QuotaDAO;
import com.nscorp.tadsservices.model.Enums.eQuota;
import com.nscorp.tadsservices.model.SearchCriteria.QuotaSearchCriteria;

public class Quota extends TADSLocationObject {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	//{{
	private String FromZone;
	private String ToZone;
	private String FromLL;
	private String ToLL;
	private int Quota;
	private int Count;
	private String vType;
	private String ChngWho;
	private Date ChngDate;
	
	private QuotaDAO qDAO;
	
	// }}
	
	//Constructor
	public Quota() {}
	
	public Quota(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		qDAO = new QuotaDAO(database);
	}
	
	public String getFromZone() {
		return FromZone;
	}
	public void setFromZone(String fromZone) {
		FromZone = fromZone;
	}
	public String getToZone() {
		return ToZone;
	}
	public void setToZone(String toZone) {
		ToZone = toZone;
	}
	public String getFromLL() {
		return FromLL;
	}
	public void setFromLL(String fromLL) {
		FromLL = fromLL;
	}
	public String getToLL() {
		return ToLL;
	}
	public void setToLL(String toLL) {
		ToLL = toLL;
	}
	public String getvType() {
		return vType;
	}
	public void setvType(String vType) {
		this.vType = vType;
	}
	public int getQuota() {
		return Quota;
	}
	
	public void setQuota(int quota) {
		Quota = quota;
	}
	
	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
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
	public Quota getQuota(QuotaSearchCriteria qsc) {
		return qDAO.get(qsc);
	}
	
	public List<Quota> getQuotaList(QuotaSearchCriteria qsc) throws SQLException{
		return qDAO.getList(qsc);
	}
	
	public boolean checkQuota(QuotaSearchCriteria qsc) {
		Quota quota = qDAO.checkQuota(qsc);
		
		if(quota != null) return (quota.getCount() + 1 < quota.getQuota());
		
		return true;
	}

	public int Save() throws SQLException {
		int result = 0;
		
		//Look for an overlapping Quota
		QuotaSearchCriteria qsc = new QuotaSearchCriteria();
		qsc.setSearchType(eQuota.eFindQuotaArea);
		qsc.setFromZone(this.getFromZone());
		qsc.setToZone(this.getToZone());
		qsc.setFromLL(this.getFromLL());
		qsc.setToLL(this.getToLL());
		qsc.setvType(this.getvType());
		List<Quota> quotas = qDAO.getList(qsc);
		
		//No overlapping Quotas found
		if(quotas == null) {
			result = qDAO.set(this);			
		}
		
		return result;
	}

	public int Delete() {
		return qDAO.delete(this);
	}
}
