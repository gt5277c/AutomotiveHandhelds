package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Enums.eDealerObjAction;

public class DealerSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eDealerObjAction SearchType = eDealerObjAction.eDealerFindByMfgDealer;
	private String Manufacturer;
	private String Dealer;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	//{{
	public eDealerObjAction getSearchType() { return SearchType; }
	public void setSearchType(eDealerObjAction searchType) { SearchType = searchType; }

	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String manufacturer) { Manufacturer = manufacturer; }

	public String getDealer() { return Dealer; }
	public void setDealer(String dealer) { Dealer = dealer;	}
	//}}
	
	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	public Object[] getParameters() {
		Object[] params = null;
				
				switch(getSearchType()) {
				case eDealerFindAll:
					//No parameters
					break;
				case eDealerFindByMfg:
					params = new Object[] {getManufacturer()};
					break;
				case eDealerFindByMfgDealer:
					params = new Object[] {getManufacturer(),getDealer()};
					break;
				default:
					params = new Object[] {getManufacturer()};
					break;
				}
		
		return params;
	}


}
