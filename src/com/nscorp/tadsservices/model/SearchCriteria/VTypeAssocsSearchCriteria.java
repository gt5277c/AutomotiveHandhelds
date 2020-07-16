package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Enums.eVTypeAssocObjAction;

public class VTypeAssocsSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eVTypeAssocObjAction SearchType = eVTypeAssocObjAction.eFindVTypeAssoc;
	private String VTypeCode;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	public eVTypeAssocObjAction getSearchType() { return SearchType; }
	public void setSearchType(eVTypeAssocObjAction searchType) { SearchType = searchType; }
	
	public String getVTypeCode() { return VTypeCode; }
	public void setVTypeCode(String vTypeCode) { VTypeCode = vTypeCode; }

	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	public Object[] getParameters() {
		Object[] params = null;
				
				switch(getSearchType()) {
				case eFindVTypeAssoc:
					params = new Object[] {getVTypeCode()};
					break;
				default:
					break;
				}
		
		return params;
	}

}
