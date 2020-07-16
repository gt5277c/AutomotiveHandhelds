package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Enums.eUpfitter;

public class UpfitterSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eUpfitter SearchType;
	private String Manufacturer;
	private String Code;
	private String VIN;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public eUpfitter getSearchType() {
		return SearchType;
	}

	public void setSearchType(eUpfitter searchType) {
		SearchType = searchType;
	}

	public String getManufacturer() {
		return Manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	public Object[] getParameters() {
		Object[] params = null;
			
				switch(getSearchType()) {
				case eFindUpfitter:
					params = new Object[] {this.getManufacturer(),this.getCode()};
					break;
				case eFindUpfitterAll:
					break;
				case eFindUpfitterManf:
					params = new Object[] {this.getManufacturer()};
					break;
				case eFindUpfitterVIN:
					params = new Object[] {this.getVIN(),this.getCode()};
					break;
				default:
					break;
				}		
		return params;
	}

}
