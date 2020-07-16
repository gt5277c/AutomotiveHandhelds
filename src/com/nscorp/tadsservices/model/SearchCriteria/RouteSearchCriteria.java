package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Enums.eRouteObjAction;

public class RouteSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eRouteObjAction SearchType = eRouteObjAction.eRouteFindByMfgRoute;
	private String Manufacturer;
	private String Route;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public eRouteObjAction getSearchType() {
		return SearchType;
	}

	public void setSearchType(eRouteObjAction searchType) {
		SearchType = searchType;
	}

	public String getManufacturer() {
		return Manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}

	public String getRoute() {
		return Route;
	}

	public void setRoute(String route) {
		Route = route;
	}	
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	public Object[] getParameters() {
		Object[] params = null;
				
				switch(getSearchType()) {
				case eRouteFindByMfg:
					params = new Object[] {getManufacturer()};
					break;
				case eRouteFindByMfgRoute:
				case eRouteFindRailcarAssoc:
					params = new Object[] {getManufacturer(),getRoute()};
					break;
				case eRouteFindRampRoute:
					//No parameters
					break;
				case eRouteFindAll:
					//No parameters
					break;
				default:
					break;
				}
		
		return params;
	}
}
