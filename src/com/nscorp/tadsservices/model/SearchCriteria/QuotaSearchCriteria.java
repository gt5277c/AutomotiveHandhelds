package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Enums.eQuota;

public class QuotaSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eQuota SearchType = eQuota.eFindQuota;
	private String FromZone;
	private String ToZone;
	private String FromLL;
	private String ToLL;
	private Integer Quota;
	private String vType;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public eQuota getSearchType() {
		return SearchType;
	}

	public void setSearchType(eQuota searchType) {
		SearchType = searchType;
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

	public Integer getQuota() {
		return Quota;
	}

	public void setQuota(Integer quota) {
		Quota = quota;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}	
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////
	public Object[] getParameters(String sqlName) {
		Object[] params = null;
		
		switch(sqlName) {
			case "spCheckQuota":
				params = new Object[] {getFromZone(),getFromLL(),getvType()};
				break;
			default:
				break;
		}
		
		return params;
	}
	
	public Object[] getParameters() {
		Object[] params = null;
		
		switch(getSearchType()) {
			case eFindQuota:
				params = new Object[] {getFromZone(),getFromLL(),getvType()};
				break;
			case eFindQuotaArea:
				params = new Object[] {getFromZone(),getToZone(),getFromLL(),getToLL()};
				break;
		}
		return params;
	}


}
