package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Enums.eHoldCriteriaObjAction;

public class HoldCriteriaSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eHoldCriteriaObjAction SearchType = eHoldCriteriaObjAction.eHoldCriteriaFindVIN;
	private Integer Key;
	private String Manufacturer;
	private String HoldCode;
	private String VIN;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	public Integer getKey() { return Key; }
	public void setKey(Integer Key) { this.Key = Key; }
	
	public eHoldCriteriaObjAction getSearchType() { return SearchType; }
	public void setSearchType(eHoldCriteriaObjAction SearchType) { this.SearchType = SearchType; }
	
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String Manufacturer)  { this.Manufacturer = Manufacturer; }
	
	public String getHoldCode() { return HoldCode; }
	public void setHoldCode(String HoldCode)  { this.HoldCode = HoldCode; }

	public String getVIN() { return VIN; }
	public void setVIN(String VIN)  { this.VIN = VIN; }

	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	public Object[] getParameters() {

		Object[] params = null;

		switch(getSearchType()) {
		case eHoldCriteriaFindCode:
			params = new Object[] {getKey()};
			break;
		case eHoldCriteriaFindMfgCodeVIN:
			params = new Object[] {getManufacturer(),getHoldCode(),getVIN()};
			break;
		case eHoldCriteriaFindVIN:
			params = new Object[] {getVIN()};
			break;
		case eHoldCriteriaFindAll:
			break;
		default:
			break;
		}
		
		return params;
	}
}
