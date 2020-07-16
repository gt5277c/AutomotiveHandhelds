package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Enums.eHoldObjAction;


public class HoldSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eHoldObjAction SearchType = eHoldObjAction.eHoldFindMfg;
	private String Manufacturer;
	private String HoldCode;
	private String VIN;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	public eHoldObjAction getSearchType() { return SearchType; }
	public void setSearchType(eHoldObjAction SearchType) { this.SearchType = SearchType; }
	
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String Manufacturer)  { this.Manufacturer = Manufacturer; }
	
	public String getHoldCode() { return HoldCode; }
	public void setHoldCode(String HoldCode)  { this.HoldCode = HoldCode; }
	
	public String getVIN() { return VIN; }
	public void setVIN(String VIN) { this.VIN = VIN; }
	
	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	public Object[] getParameters() {
		Object[] params = null;
				
				switch(getSearchType()) {
				case eHoldFindMfg:
					params = new Object[] {getManufacturer()};
					break;
				case eHoldFindMfgCode:
					params = new Object[] {getManufacturer(),getHoldCode()};
					break;
				case eHoldFindAll:
					break;
				case eHoldFindVIN:
					params = new Object[] {getVIN()};
				default:
					break;
				}
		
		return params;
	}
}
