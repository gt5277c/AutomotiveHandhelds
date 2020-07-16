package com.nscorp.tadsservices.model.SearchCriteria;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Enums.eRailObjAction;

public class RailspotSearchCriteria {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private eRailObjAction SearchType = eRailObjAction.eRailFindRailcar;
	private String RailcarNumber;
	private String LotCode;
	private String ZoneID;
	private String TrackNbr;
	private String Group;
	private String Spot;
	private String ThruSpot;
	private String InboundTrainID;
	private String InboundTrainEvent;
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public eRailObjAction getSearchType() { return SearchType; }
	public void setSearchType(eRailObjAction SearchType) { this.SearchType = SearchType; }
	
	public String getRailcarNumber() { return RailcarNumber; }
	public void setRailcarNumber(String RailcarNumber)  { this.RailcarNumber = RailcarNumber; }
	
	public String getLotCode() { return LotCode; }
	public void setLotCode(String LotCode)  { this.LotCode = LotCode; }

	public String getZoneID() { return ZoneID; }
	public void setZoneID(String ZoneID)  { this.ZoneID = ZoneID; }

	public String getTrackNbr() { return TrackNbr; }
	public void setTrackNbr(String TrackNbr)  { this.TrackNbr = TrackNbr; }

	public String getGroup() { return Group; }
	public void setGroup(String Group)  { this.Group = Group; }

	public String getSpot() { return Spot; }
	public void setSpot(String Spot)  { this.Spot = Spot; }

	public String getThruSpot() { return ThruSpot; }
	public void setThruSpot(String ThruSpot)  { this.ThruSpot = ThruSpot; }
	
	public String getInboundTrainID() {
		return InboundTrainID;
	}
	public void setInboundTrainID(String inboundTrainID) {
		InboundTrainID = inboundTrainID;
	}
	public String getInboundTrainEvent() {
		return InboundTrainEvent;
	}
	public void setInboundTrainEvent(String inboundTrainEvent) {
		InboundTrainEvent = inboundTrainEvent;
	}
	// }}

	////////////////////////////////////////////////////////////
	//Methods 
	////////////////////////////////////////////////////////////	
	public Object[] getParameters() {
		Object[] params = null;
				
				switch(getSearchType()) {
				case eRailFindRailcar:
					params = new Object[] {getRailcarNumber()};
					break;
				case eRailFindRailcarByLot:
					params = new Object[] {getRailcarNumber(),getLotCode()};
					break;
				case eRailFindZone:
					params = new Object[] {getRailcarNumber(),getLotCode(),getZoneID()};
					break;
				case eRailFindTrack:
					if(Group.length() > 0) {
						params = new Object[] {getLotCode(),getZoneID(),Common.PadString(getTrackNbr(), Constants.AREA_LEN),getGroup()};	
					} else {
						params = new Object[] {getLotCode(),getZoneID(),Common.PadString(getTrackNbr(), Constants.AREA_LEN)};
					}					
					break;
				case eRailFindSpot:
					params = new Object[] {getLotCode(),getZoneID(),Common.PadString(getTrackNbr(), Constants.AREA_LEN),getSpot()};
					break;
				case eRailFindGroup:
					params = new Object[] {getLotCode(),getZoneID(),Common.PadString(getTrackNbr(), Constants.AREA_LEN),getSpot(),getGroup()};
					break;
				case eRailFindByLotTrack:
					params = new Object[] {getLotCode(),getTrackNbr()};
					break;
				case eRailFindSpots:
					params = new Object[] {getLotCode(),getZoneID(),getTrackNbr(),getSpot(),getThruSpot()};
					break;
				case eRailFindSpotsAfter:
					params = new Object[] {getLotCode(),getZoneID(),getTrackNbr(),getSpot()};
					break;
				case eRailFindRailspotByLot:
					break;
				case eRailFindRailspotByLotZone:
					params = new Object[] {getZoneID()};
					break;
				case eRailFindRailspotByLotZoneTrack:
					params = new Object[] {getZoneID(),getTrackNbr()};
					break;
				case eRailFindRailspotByLotZoneTrackSpot:
					params = new Object[] {getZoneID(),getTrackNbr(),getSpot()};
					break;
				case eRailFindRailspotReportEvents:
				case eRailFindRailspotReportEventsAll:
					params = new Object[] {getRailcarNumber(),getInboundTrainID(),getInboundTrainEvent()};
					break;
				default:
					break;
				}
		
		return params;
	}
}
