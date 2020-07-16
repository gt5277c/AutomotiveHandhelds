package com.nscorp.tadsservices.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Enums")
public class Enums {
	
	public enum eStatusCode {
	    eStatusNone, eStatusTruckIn, eStatusPlantIn;
	}

	public enum eVINSave {
		eSystem, eOverride, eNoRecord, eNoVINCheck;
	}
	
	public enum eLotCode {
		eTruck,
	    eRail,
	    eLoadLine,
	    eStorage,
	    eTrack,
	    eSupportTrack,
	    eAdvanceConsist,
	    eDrop
	}

	public enum eBayArrival {
		eBay_Rail,
	    eBay_Plant,
	    eBay_VINAdd,
	    eBay_ReAssign,
	    eBay_DriveIn,
	    eBay_TruckIn,
	    eBay_Drop
	}
	
	@XmlType(name = "eVINObjAction")
	@XmlEnum
	public enum eVINObjAction {
	    eVINFindVIN,
	    eVINFindVINLike,
	    eVINFindRailCar,
	    eVINFindRailCarLike,
	    eVINFindRoute,
	    eVINFindDealer,
	    eVINFindLot,
	    eVINFindZone,
	    eVINFindArea,
	    eVINFindSpot,
	    eVINFindRailCarASN,
	    eVINFindInboundRailCar,
	    eVINFindOutboundRailCar,
	    eVINFindRailcarLTDs,
	    eVINFindAction
	}

	public enum eHoldObjAction {
	    eHoldFindMfg,
	    eHoldFindMfgCode,
	    eHoldFindAll,
	    eHoldFindVIN
	}
	
	public enum eHoldCriteriaObjAction {
		eHoldCriteriaFindCode,
		eHoldCriteriaFindMfgCodeVIN,
		eHoldCriteriaFindVIN,
		eHoldCriteriaFindAll
	}

	public enum eRailObjAction{
		eRailFindRailcar,
		eRailFindZone,
		eRailFindTrack,
		eRailFindTrackGroup,
		eRailFindSpot,
		eRailFindGroup,
		eRailFindRailcarByLot,
		eRailFindByLotTrack,
		eRailFindSpots,
		eRailFindSpotsAfter,
		eRailFindRailspotByLot,
		eRailFindRailspotByLotZone,
		eRailFindRailspotByLotZoneTrack,
		eRailFindRailspotByLotZoneTrackSpot,
		eRailFindRailspotReportEvents,
		eRailFindRailspotReportEventsAll
	}
	
	public enum eOptionType{
		eOptionTypeString(1), 
		eOptionTypeDate(2),
		eOptionTypeNumber(3),
		eOptionTypeBoolean(4);
		
		private final int value;
		
		eOptionType(final int newValue){ value = newValue; }
		
		public int getValue() { return value; }
	}
		
	public enum eVType{
		eVType
	}
	
	public enum eVTypeAssocObjAction{
    	eFindVTypeAssoc
	}

	public enum eDealerObjAction{
		eDealerFindByMfg,
		eDealerFindByMfgDealer,
		eDealerFindAll
	}

	public enum eZoneSortOrder{
		eZSAscending,
		eZSDescending
	}

	public enum eRowSortOrder{
		eRAscending,
		eRDescending
	}
	
	public enum eSpotSortOrder{
		eSAscending,
		eSDescending
	}

	public enum eRouteObjAction{
		eRouteFindByMfg,
		eRouteFindByMfgRoute,
		eRouteFindRampRoute,
		eRouteFindRailcarAssoc,
		eRouteFindAll
	}

	public enum eQuota{
		eFindQuota,
		eFindQuotaArea
	}

	public enum eUpfitter{
		eFindUpfitter,
		eFindUpfitterAll,
		eFindUpfitterManf,
		eFindUpfitterVIN
	}

	public enum eMessageStatus{
	    eMessageStatusQueued,
	    eMessageStatusSent,
	    eMessageStatusError,
	    S,
	    Q,
	    E
	}

	public enum eMessageObjAction{
		eMessageFindRec,
		eMessageFindQueued,
		eMessageFindAll,
		eMessageFindUnSent,
		eMessageFindSQL,
		eMessageFindTypes
	} 

	public enum eRSAaudit{
    	eFindRsaVIN,
    	eFindRsaRailcar,
    	eFindRsaAll,
    	eFindRsaBoth,
    	eFindRsaSuccess
	}
}
