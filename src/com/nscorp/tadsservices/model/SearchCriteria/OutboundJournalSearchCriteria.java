package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Enums.eMessageObjAction;

public class OutboundJournalSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eMessageObjAction SearchType;
	private Integer key;
	private Integer days;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public eMessageObjAction getSearchType() {
		return SearchType;
	}

	public void setSearchType(eMessageObjAction searchType) {
		SearchType = searchType;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	public Object[] getParameters() {
		Object[] params = null;
				
				switch(getSearchType()) {
				case eMessageFindRec:
					params = new Object[] {getKey()};
					break;
				case eMessageFindQueued:
					//No parameters
					break;
				case eMessageFindAll:
					params = new Object[] {getDays()};
					break;
				case eMessageFindUnSent:
					//No parameters
					break;
				case eMessageFindTypes:
					//No parameters
					break;
				default:
					break;
				}
		
		return params;
	}


}
