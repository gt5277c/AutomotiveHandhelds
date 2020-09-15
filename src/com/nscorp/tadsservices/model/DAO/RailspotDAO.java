package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.Enums.eRailObjAction;
import com.nscorp.tadsservices.model.Railcar;
import com.nscorp.tadsservices.model.SearchCriteria.RailspotSearchCriteria;

public class RailspotDAO extends TADSDAO<Railspot>{

	public RailspotDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public Railspot get(String id) {
		RailspotSearchCriteria rsc = new RailspotSearchCriteria();
		rsc.setSearchType(eRailObjAction.eRailFindRailcar);
		rsc.setRailcarNumber(id);
		return get(rsc);
	}
	
	public Railspot get(RailspotSearchCriteria rsc) {
		Railspot railspot = null;
		railspot = database.executeStoredProcedure(Railspot.class, fieldsMap, getSQL(rsc.getSearchType()),rsc.getParameters());
		return railspot;
	}

	public List<Railspot> getList(RailspotSearchCriteria rsc) throws SQLException{
		String sql = null;
		List<Railspot> railspots = null;
		
		sql = getSQL(rsc.getSearchType());
		Object[] params = rsc.getParameters();
		
		if(sql.startsWith("sp")) {
			railspots = database.executeStoredProcedureList(Railspot.class, fieldsMap, sql, params);
		} else {
			railspots = database.executeQueryList(Railspot.class, fieldsMap, sql, params);
		}
		return railspots;
	}
	
	public Railcar getRailcar(String railcarnum) {
		Railcar railcar = new Railcar(database);
		
		return railcar.getRailcar(railcarnum);
	}
	
	/**
	 * Will find the next position in the railar
	 * @param railcar
	 * @param deck
	 * @return
	 */
	public int GetFilledPosInRailcar(String railcar, String deck) {
		List<Railspot> railspots;
		Railspot railspot;
		int spot = 0;
		Object[] params = new Object[] {railcar,deck};
		String sql = "SELECT qryVIN_Tracks.tblVIN_OutboundDeckLevel, qryVIN_Tracks.tblVIN_OutboundPos " + 
				"FROM qryVIN_Tracks " + 
				"WHERE qryVIN_Tracks.tblVIN_OutboundRailcarNumber = ? " + 
				"AND qryVIN_Tracks.tblVIN_OutboundDeckLevel = ? " + 
				"ORDER BY qryVIN_Tracks.tblVIN_OutboundPos";
		
		railspots = database.executeQueryList(Railspot.class, fieldsMap, sql, params);
		
		if(railspots != null && railspots.size() > 0) {
			railspot = railspots.get(railspots.size() -1);
			spot = railspot.getSpot();
		}
		
		return spot;
	}
	
	@Override
	public int set(Railspot obj) {
		int result = 0;
		Object[] params = new Object[] {obj.getRailcarNumber(),
				obj.getLotCode(),
				obj.getZoneID(),
				obj.getTrackNbr(),
				obj.getSpot(),
				obj.getGroup(),
				obj.getMfg(),
				obj.getRoute(),
				obj.getStatusCode(),
				obj.getActionCode(),
				obj.getBadOrderCode(),
				obj.getHeadLight(),
				(obj.getLTD() ? "Y" : "N"),
				obj.getProcessedAsLTD() ? "Y" : "N",
				obj.getLoadEmptyStatus(),
				obj.getSwitchInstructions(),
				obj.getBlockToInstrucions(),
				obj.getInboundSCAC(),
				obj.getInboundFSAC(),
				obj.getInboundLoadedWeight(),
				obj.getInboundTrainID(),
				obj.getInboundTrainEvent(),
				obj.getInboundWayBill(),
				obj.getInboundWayBillDate(),
				obj.getInboundWayBillSN(),
				obj.getInboundBOL(),
				obj.getInboundBOLDate(),
				obj.getInboundCity(),
				obj.getInboundState(),
				obj.getInboundDestination(),
				obj.getInboundSTCC(),
				obj.getInboundEmbargo(),
				obj.getInboundPermit(),
				obj.getDepartureDate(),
				obj.getOutboundSCAC() ,
				obj.getOutboundFSAC(),
				obj.getOutboundLoadedWeight(),
				obj.getOutboundTrainID() ,
				obj.getOutboundTrainEvent(),
				obj.getOutboundWayBill(),
				obj.getOutboundWayBillDate(),
				obj.getOutboundWayBillSN(),
				obj.getOutboundBOL(),
				obj.getOutboundBOLDate(),
				obj.getOutboundEmbargo(),
				obj.getOutboundPermit(),
				obj.getNotes(),
				obj.getConsignee(),
				obj.getShipper(),
				obj.getArrivalDate(),
				obj.getRailShipApproveDate(),
				obj.getChngWho() == null ? "" : obj.getChngWho(),
				obj.getChngDate()
				
		};
		result = database.executeStoredProcedureUpdate("spInsertRailcar", params);
		
		return result;
	}
	
	public int saveHistory(Railspot obj) {
		int result = 0;
		Object[] params = new Object[] {obj.getRailcarNumber(),
				obj.getLotCode(),
				obj.getZoneID(),
				obj.getTrackNbr(),
				obj.getSpot(),
				obj.getGroup(),
				obj.getMfg(),
				obj.getRoute(),
				obj.getStatusCode(),
				obj.getActionCode(),
				obj.getBadOrderCode(),
				obj.getHeadLight(),
				(obj.getLTD() ? "Y" : "N"),
				(obj.getProcessedAsLTD() ? "Y" : "N"),
				obj.getLoadEmptyStatus(),obj.getSwitchInstructions(),
				obj.getBlockToInstrucions(),obj.getInboundSCAC(),obj.getInboundFSAC(),obj.getInboundLoadedWeight(),obj.getInboundTrainID(),obj.getInboundTrainEvent(),
				obj.getInboundWayBill(),obj.getInboundWayBillDate(),obj.getInboundWayBillSN(),obj.getInboundBOL(),obj.getInboundBOLDate(),obj.getInboundCity(),obj.getInboundState(),
				obj.getInboundDestination(),obj.getInboundSTCC(),obj.getInboundEmbargo(),obj.getInboundPermit(),obj.getDepartureDate(),obj.getOutboundSCAC(),obj.getOutboundFSAC(),
				obj.getOutboundLoadedWeight(),obj.getOutboundTrainID(),obj.getOutboundTrainEvent(),obj.getOutboundWayBill(),obj.getOutboundWayBillDate(),obj.getOutboundWayBillSN(),
				obj.getOutboundBOL(),obj.getOutboundBOLDate(),obj.getOutboundEmbargo(),obj.getOutboundPermit(),obj.getNotes(),obj.getConsignee(),obj.getShipper(),obj.getArrivalDate(),
				obj.getRailShipApproveDate(),obj.getChngWho(),obj.getChngDate()
				
		};
		result = database.executeStoredProcedureUpdate("spInsertRailcarHistory", params);
		
		return result;
	}

	@Override
	public int delete(Railspot obj) {
		int result = 0;

		Object[] params = new Object[] {obj.getRailcarNumber(),obj.getLotCode(),obj.getZoneID(),obj.getTrackNbr(),obj.getSpot(),obj.getGroup(),
				obj.getMfg(),obj.getRoute(),Constants.STAT_RAIL_DELETED,obj.getActionCode(),obj.getBadOrderCode(),obj.getHeadLight(),
				obj.getLTD() == true ? "Y" : "N", obj.getProcessedAsLTD() == true ? "Y" : "N",
				obj.getLoadEmptyStatus(),obj.getSwitchInstructions(),obj.getBlockToInstrucions(),obj.getInboundSCAC(),obj.getInboundFSAC(),
				obj.getInboundLoadedWeight(),obj.getInboundTrainID(),obj.getInboundTrainEvent(),obj.getInboundWayBill(),obj.getInboundWayBillDate(),
				obj.getInboundWayBillSN(),obj.getInboundBOL(),obj.getInboundBOLDate(),obj.getInboundCity(),obj.getInboundState(),
				obj.getInboundDestination(),obj.getInboundSTCC(),obj.getInboundEmbargo(),obj.getInboundPermit(),obj.getDepartureDate(),
				obj.getOutboundSCAC(),obj.getOutboundFSAC(),obj.getOutboundLoadedWeight(),obj.getOutboundTrainID(),obj.getOutboundTrainEvent(),
				obj.getOutboundWayBill(),obj.getOutboundWayBillDate(),obj.getOutboundWayBillSN(),obj.getOutboundBOL(),obj.getOutboundBOLDate(),
				obj.getOutboundEmbargo(),obj.getOutboundPermit(),obj.getNotes(),obj.getConsignee(),obj.getShipper(),
				obj.getArrivalDate(),obj.getRailShipApproveDate(),obj.getChngWho(),obj.getChngDate()
				
		};
		
		result = database.executeStoredProcedureUpdate("spDeleteRailcar", params);

		return result;
	}
	
	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  		
				//Format is put("database column name", "class field name");
				put("tblRailCarHist_Number","key");
				put("tblRailCar_Number", "RailcarNumber");	
				put("tblRailCarHist_Number", "RailcarNumber");
				put("tblRailCar_LotCode", "LotCode");
				put("tblRailCarHist_LotCode", "LotCode");
				put("tblRailCar_ZoneID", "ZoneID");
				put("tblRailCarHist_ZoneID", "ZoneID");
				
				put("tblTrkSpot_ZoneID", "ZoneID");
				put("tblRailCar_TrackNbr", "TrackNbr");
				put("tblTrkSpot_TrackNbr","TrackNbr");
				put("tblRailCarHist_TrackNbr","TrackNbr");
				
				put("tblRailCar_Spot", "Spot");
				put("tblTrkSpot_Spot", "Spot");
				put("tblRailCarHist_Spot", "Spot");
				
				put("LoadGroup","LoadGroup");
				put("tblRailCar_Group","LoadGroup");
				put("tblRailCarHist_Group","LoadGroup");
				
				put("tblRailCar_Mfg", "Mfg");
				put("Manufacturer", "Mfg");
				put("tblRailCarHist_Mfg", "Mfg");
				
				put("tblRailCar_Route", "Route");
				put("tblRailCarHist_Route", "Route");
				put("tblRailCar_StatusCode", "StatusCode");
				put("Status", "StatusCode");
				put("tblRailCarHist_StatusCode", "StatusCode");
				put("tblRailcar_ActionCode", "ActionCode");
				put("tblRailCarHist_ActionCode", "ActionCode");
				put("tblRailCar_BadOrderCode", "BadOrderCode");
				put("tblRailCarHist_BadOrderCode", "BadOrderCode");
				put("TotalVINsLoaded", "TotalVINsLoaded");
				put("tblRailCar_HeadlightOrient", "HeadLight");
				put("tblRailCarHist_HeadLightOrient", "HeadLight");
				put("Headlight", "HeadLight");
				put("tblRailCar_LTD", "LTD");
				put("tblRailCarHist_LTD", "LTD");
				put("tblRailCar_ProcessedAsLTD", "ProcessedAsLTD");
				put("tblRailCarHist_ProcessedAsLTD", "ProcessedAsLTD");
				put("tblRailCar_LoadEmptyStatus", "LoadEmptyStatus");	
				put("LoadEmpty", "LoadEmptyStatus");	
				put("tblRailCarHist_LoadEmptyStatus", "LoadEmptyStatus");
				put("tblRailCar_SwitchInstructions", "SwitchInstructions");
				put("tblRailCarHist_SwitchInstructions", "SwitchInstructions");
				put("tblRailCar_BlockToInstrucions", "BlockToInstrucions");
				put("tblRailCarHist_BlockToInstrucions", "BlockToInstrucions");
				put("tblRailCar_InboundSCAC", "InboundSCAC");
				put("tblRailCarHist_InboundSCAC", "InboundSCAC");
				put("tblRailCar_InboundFSAC", "InboundFSAC");
				put("tblRailcarHist_InboundFSAC", "InboundFSAC");
				put("tblRailCar_InboundLoadedWeight", "InboundLoadedWeight");
				put("tblRailcarHist_InboundLoadedWeight", "InboundLoadedWeight");
				put("tblRailCar_InboundTrainID", "InboundTrainID");
				put("tblRailCarHist_InboundTrainID", "InboundTrainID");
				put("tblRailCar_InboundTrainEvent", "InboundTrainEvent");
				put("tblRailCarHist_InboundTrainEvent", "InboundTrainEvent");
				put("tblRailCar_InboundWayBill", "InboundWayBill");
				put("tblRailCarHist_InboundWayBill", "InboundWayBill");
				put("tblRailCar_InboundWayBillDate", "InboundWayBillDate");
				put("tblRailCarHist_InboundWayBillDate", "InboundWayBillDate");
				put("tblRailCar_InboundWayBillSN", "InboundWayBillSN");
				put("tblRailCarHist_InboundWayBillSN", "InboundWayBillSN");
				put("tblRailCar_InboundBOL", "InboundBOL");
				put("tblRailCarHist_InboundBOL", "InboundBOL");
				put("tblRailCar_InboundBOLDate", "InboundBOLDate");
				put("tblRailCarHist_InboundBOLDate", "InboundBOLDate");
				put("tblRailCar_InboundCity", "InboundCity");
				put("tblRoute_City", "InboundCity");
				put("tblRailcarHist_InboundCity", "InboundCity");
				put("tblRoute_State", "InboundState");
				put("tblRailCar_InboundState", "InboundState");	
				put("tblRailcarHist_InboundState", "InboundState");	
				put("tblRailCar_InboundDestination", "InboundDestination");
				put("tblRailcarHist_InboundDestination", "InboundDestination");
				put("tblRailCar_InboundSTCC", "InboundSTCC");
				put("tblRailcarHist_InboundSTCC", "InboundSTCC");
				put("tblRailcar_InboundEmbargo", "InboundEmbargo");
				put("tblRailCarHist_InboundEmbargo", "InboundEmbargo");
				put("tblRailcar_InboundPermit", "InboundPermit");
				put("tblRailCarHist_InboundPermit", "InboundPermit");
				put("tblRailCar_DepartureDate", "DepartureDate");
				put("tblRailCarHist_DepartureDate", "DepartureDate");
				put("tblRailCar_OutboundSCAC", "OutboundSCAC");
				put("tblRailCarHist_OutboundSCAC", "OutboundSCAC");
				put("tblRailCar_OutboundFSAC", "OutboundFSAC");
				put("tblRailcarHist_OutboundFSAC", "OutboundFSAC");
				put("tblRailCar_OutboundLoadedWeight", "OutboundLoadedWeight");
				put("tblRailcarHist_OutboundLoadedWeight", "OutboundLoadedWeight");
				put("Weight", "OutboundLoadedWeight");
				put("DeckHeight", "DeckHeight");
				put("tblRailcarDeckType_Desc","DeckType");
				put("tblRailcarTieDown_Desc","TieDown");
				put("tblRailcars_Articulated","Articulated");
				put("tblRailCar_OutboundTrainID", "OutboundTrainID");
				put("tblRailCarHist_OutboundTrainID", "OutboundTrainID");
				put("tblRailCar_OutboundTrainEvent", "OutboundTrainEvent");
				put("tblRailCarHist_OutboundTrainEvent", "OutboundTrainEvent");
				put("tblRailCar_OutboundWayBill", "OutboundWayBill");
				put("Waybill", "OutboundWayBill");
				put("tblRailCarHist_OutboundWayBill", "OutboundWayBill");
				put("tblRailCar_OutboundWayBillDate", "OutboundWayBillDate");
				put("tblRailCarHist_OutboundWayBillDate", "OutboundWayBillDate");
				put("tblRailCar_OutboundWayBillSN", "OutboundWayBillSN");
				put("tblRailCarHist_OutboundWayBillSN", "OutboundWayBillSN");
				put("tblRailCar_OutboundBOL", "OutboundBOL");		
				put("tblRailCarHist_OutboundBOL", "OutboundBOL");
				put("tblRailCar_OutboundBOLDate", "OutboundBOLDate");
				put("tblRailCarHist_OutboundBOLDate", "OutboundBOLDate");
				put("tblRailCar_OutboundEmbargo", "OutboundEmbargo");
				put("tblRailCarHist_OutboundEmbargo", "OutboundEmbargo");
				put("tblRailCar_OutboundPermit", "OutboundPermit");
				put("tblRailCarHist_OutboundPermit", "OutboundPermit");
				put("tblRailCar_Notes", "Notes");
				put("Notes", "Notes");
				put("tblRailCarHist_Notes", "Notes");
				put("tblRailCar_Consignee", "Consignee");
				put("tblRailCar_Shipper", "Shipper");
				put("tblRailCar_ArrivalDate", "ArrivalDate");
				put("tblRailCar_RailShipApproveDate", "RailShipApproveDate");
				put("tblRailCar_ChngWho", "ChngWho");
				put("tblRailCar_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
	
	private String getSQL(eRailObjAction eFind) {
		String sql = null;
		
		switch(eFind) {
		case eRailFindRailcar:
			sql = "spGetRailObjByRailcar";
			break;
		case eRailFindRailcarByLot:
			sql = "spGetRailObjByRailCarAndLot";
			break;
		case eRailFindZone:
			sql = "spGetRailObjByZone";
			break;
		case eRailFindTrack:
			sql = "spGetRailObjByTrack";
			break;
		case eRailFindTrackGroup:
			sql = "spGetRailObjByTrackGroup";
			break;
		case eRailFindSpot:
			sql = "spGetRailObjBySpot";
			break;
		case eRailFindGroup:
			sql = "spGetRailObjByGroup";
			break;
		case eRailFindByLotTrack:
			sql = "spGetRailObjByLotTrack";
			break;
		case eRailFindSpots:
			sql = "spGetRailObjBySpots";
			break;
		case eRailFindSpotsAfter:
			sql = "spGetRailObjSpotsAfter";
			break;
		case eRailFindRailspotByLot:
			sql = GetAllRailspot;
			break;
		case eRailFindRailspotByLotZone:
			sql = GetAllRailspotbyZone;
			break;
		case eRailFindRailspotByLotZoneTrack:
			sql = GetAllRailspotbyZoneTrack;
			break;
		case eRailFindRailspotByLotZoneTrackSpot:
			sql = GetAllRailspotbyZoneTrackSpot;
			break;
		case eRailFindRailspotReportEvents:
			sql = "SELECT * " + 
					"FROM tblRailcarHist " + 
					"WHERE tblRailcarHist_Number = ? " + 
					"AND tblRailCarHist_InboundTrainID = ? " + 
					"AND tblRailCarHist_InboundTrainEvent = ? " + 
					"AND tblRailcarHist_StatusCode IN ( 'F', 'G', 'K', 'H' ) " + 
					"ORDER BY tblRailcarHist_Key ASC";
			break;
		case eRailFindRailspotReportEventsAll:
			sql = "SELECT * " + 
					"FROM tblRailcarHist " + 
					"WHERE tblRailcarHist_Key IN (SELECT MAX(tblRailcarHist_Key) " + 
					"                             FROM tblRailcarHist " + 
					"                             WHERE tblRailcarHist_Number = ? " + 
					"                             AND tblRailCarHist_InboundTrainID = ? " + 
					"                             AND tblRailCarHist_InboundTrainEvent = ?) " + 
					"ORDER BY tblRailcarHist_Key ASC";
			break;
		default:
			break;
		}
		
		return sql;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eRailObjAction eFind;
		
		eFind = (eRailObjAction)Find;
		
		switch(eFind) {
		case eRailFindRailcar:
			sql = "spGetRailObjByRailcar";
			break;
		case eRailFindRailcarByLot:
			sql = "spGetRailObjByRailCarAndLot";
			break;
		case eRailFindZone:
			sql = "spGetRailObjByZone";
			break;
		case eRailFindTrack:
			sql = "spGetRailObjByTrack";
			break;
		case eRailFindTrackGroup:
			sql = "spGetRailObjByTrackGroup";
			break;
		case eRailFindSpot:
			sql = "spGetRailObjBySpot";
			break;
		case eRailFindGroup:
			sql = "spGetRailObjByGroup";
			break;
		case eRailFindByLotTrack:
			sql = "spGetRailObjByLotTrack";
			break;
		case eRailFindSpots:
			sql = "spGetRailObjBySpots";
			break;
		case eRailFindSpotsAfter:
			sql = "spGetRailObjSpotsAfter";
			break;
		case eRailFindRailspotByLot:
			sql = GetAllRailspot;
			break;
		case eRailFindRailspotByLotZone:
			sql = GetAllRailspotbyZone;
			break;
		case eRailFindRailspotByLotZoneTrack:
			sql = GetAllRailspotbyZoneTrack;
			break;
		case eRailFindRailspotByLotZoneTrackSpot:
			sql = GetAllRailspotbyZoneTrackSpot;
			break;
		default:
			break;
		}
		
		return sql;
	}
	
	private String GetAllRailspot = "SELECT 'T' as tblRailCar_LotCode, tblTrkSpot.tblTrkSpot_ZoneID, tblTrkSpot.tblTrkSpot_TrackNbr, tblTrkSpot.tblTrkSpot_Spot,  " + 
			"       tblRailCar.tblRailCar_Group as LoadGroup, tblRailCar.tblRailCar_Notes AS Notes,  " + 
			"       tblRailCar.tblRailCar_Number, tblRailCar_LoadEmptyStatus AS LoadEmpty, tblRailcarStatus.tblRailcarStatus_Desc AS Status, tblRailCar.tblRailCar_Route,  " + 
			"       qryCountVINsInRailcar.TotalVINsLoaded, tblRailCar.tblRailcar_HeadlightOrient AS Headlight, " + 
			"       tblRailcar.tblRailCar_LTD, tblRailcar.tblRailCar_ProcessedAsLTD, tblRailcar.tblRailcar_SwitchInstructions, tblRailcar.tblRailcar_BlockToInstrucions,  " + 
			"       'Manufacturer' = CASE  " + 
			"            WHEN tblManf.tblManf_Manufacturer IS NULL THEN NULL " + 
			"            ELSE tblManf.tblManf_Manufacturer " + 
			"       END, " + 
			"       'Weight' = CASE " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NULL AND tblRailcar.tblRailCar_InboundLoadedWeight IS NULL THEN NULL " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NULL AND tblRailcar.tblRailCar_InboundLoadedWeight IS NOT NULL THEN tblRailcar.tblRailCar_InboundLoadedWeight " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NOT NULL THEN tblRailcar.tblRailCar_OutboundLoadedWeight " + 
			"            ELSE NULL " + 
			"       END, " + 
			"       'DeckHeight' = CASE " + 
			"	    when tblrailcars.tblRailcars_BiTriIndicator = 'B' then tblRailcars.tblRailcars_DeckA " + 
			"	    when tblrailcars.tblRailcars_BiTriIndicator = 'T' then tblRailcars.tblRailcars_DeckC " + 
			"            ELSE tblRailcars.tblRailcars_DeckA " + 
			"       END, " + 
			"       'Waybill' = CASE " + 
			"            WHEN tblRailCar_StatusCode IN ( 'R' ) OR tblRailCar_OutboundWayBill IS NOT NULL THEN tblRailCar_OutboundWayBill " + 
			"            WHEN tblRailCar_StatusCode IN ( 'A', 'I', 'J', 'X' ) THEN tblRailCar_InboundWayBill " + 
			"            ELSE Null " + 
			"       END, " + 
			"       tblRailcarDeckType.tblRailcarDeckType_Desc,  " + 
			"       tblRailcarTieDown.tblRailcarTieDown_Desc, tblRailcars.tblRailcars_Articulated, tblRoute.tblRoute_City,  " + 
			"       tblRoute.tblRoute_State, tblRailCar.tblRailCar_ChngWho, tblRailCar.tblRailCar_ChngDate " + 
			"FROM tblTrkSpot LEFT OUTER JOIN tblRailCar ON " + 
			"     tblTrkSpot.tblTrkSpot_ZoneID = tblRailCar.tblRailCar_ZoneID   " + 
			"     AND tblTrkSpot.tblTrkSpot_TrackNbr = tblRailCar.tblRailCar_TrackNbr  " + 
			"     AND tblTrkSpot.tblTrkSpot_Spot = tblRailCar.tblRailCar_Spot  " + 
			"     AND tblRailcar_LotCode = 'T' LEFT OUTER JOIN tblRailcarStatus ON " + 
			"     tblRailCar.tblRailCar_StatusCode = tblRailcarStatus.tblRailcarStatus_Code LEFT OUTER JOIN tblRailcars ON  " + 
			"     tblRailCar.tblRailCar_Number = tblRailcars.tblRailcars_Nbr LEFT OUTER JOIN tblRailcarTieDown ON  " + 
			"     tblRailcars.tblRailcars_TieDown = tblRailcarTieDown.tblRailcarTieDown_Code LEFT OUTER JOIN tblRailcarDeckType ON  " + 
			"     tblRailcars.tblRailcars_DeckType = tblRailcarDeckType.tblRailcarDeckType_Code LEFT OUTER JOIN tblManf ON " + 
			"     tblRailcar.tblRailCar_Mfg = tblManf.tblManf_Manufacturer LEFT OUTER JOIN tblRoute ON  " + 
			"     tblRailcar.tblRailCar_Mfg = tblRoute.tblRoute_Manufacturer " + 
			"     AND tblRailCar.tblRailCar_Route = tblRoute.tblRoute_Route LEFT OUTER JOIN qryCountVINsInRailcar ON  " + 
			"     tblRailCar.tblRailCar_Number = qryCountVINsInRailcar.Railcar " + 
			"WHERE tblTrkSpot.tblTrkSpot_ActiveInd = 'Y' " + 
			"ORDER BY tblTrkSpot.tblTrkSpot_ZoneID, tblTrkSpot.tblTrkSpot_TrackNbr, tblTrkSpot.tblTrkSpot_Spot";
	
	private String GetAllRailspotbyZone = "SELECT 'T' as tblRailCar_LotCode, tblTrkSpot.tblTrkSpot_ZoneID, tblTrkSpot.tblTrkSpot_TrackNbr, tblTrkSpot.tblTrkSpot_Spot,  " + 
			"       tblRailCar.tblRailCar_Group as LoadGroup, tblRailCar.tblRailCar_Notes AS Notes,  " + 
			"       tblRailCar.tblRailCar_Number, tblRailCar_LoadEmptyStatus AS LoadEmpty, tblRailcarStatus.tblRailcarStatus_Desc AS Status, tblRailCar.tblRailCar_Route,  " + 
			"       qryCountVINsInRailcar.TotalVINsLoaded, tblRailCar.tblRailcar_HeadlightOrient AS Headlight, " + 
			"       tblRailcar.tblRailCar_LTD, tblRailcar.tblRailCar_ProcessedAsLTD, tblRailcar.tblRailcar_SwitchInstructions, tblRailcar.tblRailcar_BlockToInstrucions,  " + 
			"       'Manufacturer' = CASE  " + 
			"            WHEN tblManf.tblManf_Manufacturer IS NULL THEN NULL " + 
			"            ELSE tblManf.tblManf_Manufacturer " + 
			"       END, " + 
			"       'Weight' = CASE " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NULL AND tblRailcar.tblRailCar_InboundLoadedWeight IS NULL THEN NULL " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NULL AND tblRailcar.tblRailCar_InboundLoadedWeight IS NOT NULL THEN tblRailcar.tblRailCar_InboundLoadedWeight " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NOT NULL THEN tblRailcar.tblRailCar_OutboundLoadedWeight " + 
			"            ELSE NULL " + 
			"       END, " + 
			"       'DeckHeight' = CASE " + 
			"	    when tblrailcars.tblRailcars_BiTriIndicator = 'B' then tblRailcars.tblRailcars_DeckA " + 
			"	    when tblrailcars.tblRailcars_BiTriIndicator = 'T' then tblRailcars.tblRailcars_DeckC " + 
			"            ELSE tblRailcars.tblRailcars_DeckA " + 
			"       END, " + 
			"       'Waybill' = CASE " + 
			"            WHEN tblRailCar_StatusCode IN ( 'R' ) OR tblRailCar_OutboundWayBill IS NOT NULL THEN tblRailCar_OutboundWayBill " + 
			"            WHEN tblRailCar_StatusCode IN ( 'A', 'I', 'J', 'X' ) THEN tblRailCar_InboundWayBill " + 
			"            ELSE Null " + 
			"       END, " + 
			"       tblRailcarDeckType.tblRailcarDeckType_Desc,  " + 
			"       tblRailcarTieDown.tblRailcarTieDown_Desc, tblRailcars.tblRailcars_Articulated, tblRoute.tblRoute_City,  " + 
			"       tblRoute.tblRoute_State, tblRailCar.tblRailCar_ChngWho, tblRailCar.tblRailCar_ChngDate " + 
			"FROM tblTrkSpot LEFT OUTER JOIN tblRailCar ON " + 
			"     tblTrkSpot.tblTrkSpot_ZoneID = tblRailCar.tblRailCar_ZoneID   " + 
			"     AND tblTrkSpot.tblTrkSpot_TrackNbr = tblRailCar.tblRailCar_TrackNbr  " + 
			"     AND tblTrkSpot.tblTrkSpot_Spot = tblRailCar.tblRailCar_Spot  " + 
			"     AND tblRailcar_LotCode = 'T' LEFT OUTER JOIN tblRailcarStatus ON " + 
			"     tblRailCar.tblRailCar_StatusCode = tblRailcarStatus.tblRailcarStatus_Code LEFT OUTER JOIN tblRailcars ON  " + 
			"     tblRailCar.tblRailCar_Number = tblRailcars.tblRailcars_Nbr LEFT OUTER JOIN tblRailcarTieDown ON  " + 
			"     tblRailcars.tblRailcars_TieDown = tblRailcarTieDown.tblRailcarTieDown_Code LEFT OUTER JOIN tblRailcarDeckType ON  " + 
			"     tblRailcars.tblRailcars_DeckType = tblRailcarDeckType.tblRailcarDeckType_Code LEFT OUTER JOIN tblManf ON " + 
			"     tblRailcar.tblRailCar_Mfg = tblManf.tblManf_Manufacturer LEFT OUTER JOIN tblRoute ON  " + 
			"     tblRailcar.tblRailCar_Mfg = tblRoute.tblRoute_Manufacturer " + 
			"     AND tblRailCar.tblRailCar_Route = tblRoute.tblRoute_Route LEFT OUTER JOIN qryCountVINsInRailcar ON  " + 
			"     tblRailCar.tblRailCar_Number = qryCountVINsInRailcar.Railcar " + 
			"WHERE tblTrkSpot.tblTrkSpot_ActiveInd = 'Y' " + 
			" " + 
			"AND tblTrkSpot.tblTrkSpot_ZoneID = ? " +
			" " + 
			"ORDER BY tblTrkSpot.tblTrkSpot_ZoneID, tblTrkSpot.tblTrkSpot_TrackNbr, tblTrkSpot.tblTrkSpot_Spot";
	
	private String GetAllRailspotbyZoneTrack = "SELECT 'T' as tblRailCar_LotCode, tblTrkSpot.tblTrkSpot_ZoneID, tblTrkSpot.tblTrkSpot_TrackNbr, tblTrkSpot.tblTrkSpot_Spot,  " + 
			"       tblRailCar.tblRailCar_Group as LoadGroup, tblRailCar.tblRailCar_Notes AS Notes,  " + 
			"       tblRailCar.tblRailCar_Number, tblRailCar_LoadEmptyStatus AS LoadEmpty, tblRailcarStatus.tblRailcarStatus_Desc AS Status, tblRailCar.tblRailCar_Route,  " + 
			"       qryCountVINsInRailcar.TotalVINsLoaded, tblRailCar.tblRailcar_HeadlightOrient AS Headlight, " + 
			"       tblRailcar.tblRailCar_LTD, tblRailcar.tblRailCar_ProcessedAsLTD, tblRailcar.tblRailcar_SwitchInstructions, tblRailcar.tblRailcar_BlockToInstrucions,  " + 
			"       'Manufacturer' = CASE  " + 
			"            WHEN tblManf.tblManf_Manufacturer IS NULL THEN NULL " + 
			"            ELSE tblManf.tblManf_Manufacturer " + 
			"       END, " + 
			"       'Weight' = CASE " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NULL AND tblRailcar.tblRailCar_InboundLoadedWeight IS NULL THEN NULL " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NULL AND tblRailcar.tblRailCar_InboundLoadedWeight IS NOT NULL THEN tblRailcar.tblRailCar_InboundLoadedWeight " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NOT NULL THEN tblRailcar.tblRailCar_OutboundLoadedWeight " + 
			"            ELSE NULL " + 
			"       END, " + 
			"       'DeckHeight' = CASE " + 
			"	    when tblrailcars.tblRailcars_BiTriIndicator = 'B' then tblRailcars.tblRailcars_DeckA " + 
			"	    when tblrailcars.tblRailcars_BiTriIndicator = 'T' then tblRailcars.tblRailcars_DeckC " + 
			"            ELSE tblRailcars.tblRailcars_DeckA " + 
			"       END, " + 
			"       'Waybill' = CASE " + 
			"            WHEN tblRailCar_StatusCode IN ( 'R' ) OR tblRailCar_OutboundWayBill IS NOT NULL THEN tblRailCar_OutboundWayBill " + 
			"            WHEN tblRailCar_StatusCode IN ( 'A', 'I', 'J', 'X' ) THEN tblRailCar_InboundWayBill " + 
			"            ELSE Null " + 
			"       END, " + 
			"       tblRailcarDeckType.tblRailcarDeckType_Desc,  " + 
			"       tblRailcarTieDown.tblRailcarTieDown_Desc, tblRailcars.tblRailcars_Articulated, tblRoute.tblRoute_City,  " + 
			"       tblRoute.tblRoute_State, tblRailCar.tblRailCar_ChngWho, tblRailCar.tblRailCar_ChngDate " + 
			"FROM tblTrkSpot LEFT OUTER JOIN tblRailCar ON " + 
			"     tblTrkSpot.tblTrkSpot_ZoneID = tblRailCar.tblRailCar_ZoneID   " + 
			"     AND tblTrkSpot.tblTrkSpot_TrackNbr = tblRailCar.tblRailCar_TrackNbr  " + 
			"     AND tblTrkSpot.tblTrkSpot_Spot = tblRailCar.tblRailCar_Spot  " + 
			"     AND tblRailcar_LotCode = 'T' LEFT OUTER JOIN tblRailcarStatus ON " + 
			"     tblRailCar.tblRailCar_StatusCode = tblRailcarStatus.tblRailcarStatus_Code LEFT OUTER JOIN tblRailcars ON  " + 
			"     tblRailCar.tblRailCar_Number = tblRailcars.tblRailcars_Nbr LEFT OUTER JOIN tblRailcarTieDown ON  " + 
			"     tblRailcars.tblRailcars_TieDown = tblRailcarTieDown.tblRailcarTieDown_Code LEFT OUTER JOIN tblRailcarDeckType ON  " + 
			"     tblRailcars.tblRailcars_DeckType = tblRailcarDeckType.tblRailcarDeckType_Code LEFT OUTER JOIN tblManf ON " + 
			"     tblRailcar.tblRailCar_Mfg = tblManf.tblManf_Manufacturer LEFT OUTER JOIN tblRoute ON  " + 
			"     tblRailcar.tblRailCar_Mfg = tblRoute.tblRoute_Manufacturer " + 
			"     AND tblRailCar.tblRailCar_Route = tblRoute.tblRoute_Route LEFT OUTER JOIN qryCountVINsInRailcar ON  " + 
			"     tblRailCar.tblRailCar_Number = qryCountVINsInRailcar.Railcar " + 
			"WHERE tblTrkSpot.tblTrkSpot_ActiveInd = 'Y' " + 
			" " + 
			"AND tblTrkSpot.tblTrkSpot_ZoneID = ? " + 
			"and tblTrkSpot_TrackNbr = ? " + 
			" " + 
			"ORDER BY tblTrkSpot.tblTrkSpot_ZoneID, tblTrkSpot.tblTrkSpot_TrackNbr, tblTrkSpot.tblTrkSpot_Spot";
	
	private String GetAllRailspotbyZoneTrackSpot = "SELECT 'T' as tblRailCar_LotCode, tblTrkSpot.tblTrkSpot_ZoneID, tblTrkSpot.tblTrkSpot_TrackNbr, tblTrkSpot.tblTrkSpot_Spot,  " + 
			"       tblRailCar.tblRailCar_Group as LoadGroup, tblRailCar.tblRailCar_Notes AS Notes,  " + 
			"       tblRailCar.tblRailCar_Number, tblRailCar_LoadEmptyStatus AS LoadEmpty, tblRailcarStatus.tblRailcarStatus_Desc AS Status, tblRailCar.tblRailCar_Route,  " + 
			"       qryCountVINsInRailcar.TotalVINsLoaded, tblRailCar.tblRailcar_HeadlightOrient AS Headlight, " + 
			"       tblRailcar.tblRailCar_LTD, tblRailcar.tblRailCar_ProcessedAsLTD, tblRailcar.tblRailcar_SwitchInstructions, tblRailcar.tblRailcar_BlockToInstrucions,  " + 
			"       'Manufacturer' = CASE  " + 
			"            WHEN tblManf.tblManf_Manufacturer IS NULL THEN NULL " + 
			"            ELSE tblManf.tblManf_Manufacturer " + 
			"       END, " + 
			"       'Weight' = CASE " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NULL AND tblRailcar.tblRailCar_InboundLoadedWeight IS NULL THEN NULL " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NULL AND tblRailcar.tblRailCar_InboundLoadedWeight IS NOT NULL THEN tblRailcar.tblRailCar_InboundLoadedWeight " + 
			"            WHEN tblRailcar.tblRailCar_OutboundLoadedWeight IS NOT NULL THEN tblRailcar.tblRailCar_OutboundLoadedWeight " + 
			"            ELSE NULL " + 
			"       END, " + 
			"       'DeckHeight' = CASE " + 
			"	    when tblrailcars.tblRailcars_BiTriIndicator = 'B' then tblRailcars.tblRailcars_DeckA " + 
			"	    when tblrailcars.tblRailcars_BiTriIndicator = 'T' then tblRailcars.tblRailcars_DeckC " + 
			"            ELSE tblRailcars.tblRailcars_DeckA " + 
			"       END, " + 
			"       'Waybill' = CASE " + 
			"            WHEN tblRailCar_StatusCode IN ( 'R' ) OR tblRailCar_OutboundWayBill IS NOT NULL THEN tblRailCar_OutboundWayBill " + 
			"            WHEN tblRailCar_StatusCode IN ( 'A', 'I', 'J', 'X' ) THEN tblRailCar_InboundWayBill " + 
			"            ELSE Null " + 
			"       END, " + 
			"       tblRailcarDeckType.tblRailcarDeckType_Desc,  " + 
			"       tblRailcarTieDown.tblRailcarTieDown_Desc, tblRailcars.tblRailcars_Articulated, tblRoute.tblRoute_City,  " + 
			"       tblRoute.tblRoute_State, tblRailCar.tblRailCar_ChngWho, tblRailCar.tblRailCar_ChngDate " + 
			"FROM tblTrkSpot LEFT OUTER JOIN tblRailCar ON " + 
			"     tblTrkSpot.tblTrkSpot_ZoneID = tblRailCar.tblRailCar_ZoneID   " + 
			"     AND tblTrkSpot.tblTrkSpot_TrackNbr = tblRailCar.tblRailCar_TrackNbr  " + 
			"     AND tblTrkSpot.tblTrkSpot_Spot = tblRailCar.tblRailCar_Spot  " + 
			"     AND tblRailcar_LotCode = 'T' LEFT OUTER JOIN tblRailcarStatus ON " + 
			"     tblRailCar.tblRailCar_StatusCode = tblRailcarStatus.tblRailcarStatus_Code LEFT OUTER JOIN tblRailcars ON  " + 
			"     tblRailCar.tblRailCar_Number = tblRailcars.tblRailcars_Nbr LEFT OUTER JOIN tblRailcarTieDown ON  " + 
			"     tblRailcars.tblRailcars_TieDown = tblRailcarTieDown.tblRailcarTieDown_Code LEFT OUTER JOIN tblRailcarDeckType ON  " + 
			"     tblRailcars.tblRailcars_DeckType = tblRailcarDeckType.tblRailcarDeckType_Code LEFT OUTER JOIN tblManf ON " + 
			"     tblRailcar.tblRailCar_Mfg = tblManf.tblManf_Manufacturer LEFT OUTER JOIN tblRoute ON  " + 
			"     tblRailcar.tblRailCar_Mfg = tblRoute.tblRoute_Manufacturer " + 
			"     AND tblRailCar.tblRailCar_Route = tblRoute.tblRoute_Route LEFT OUTER JOIN qryCountVINsInRailcar ON  " + 
			"     tblRailCar.tblRailCar_Number = qryCountVINsInRailcar.Railcar " + 
			"WHERE tblTrkSpot.tblTrkSpot_ActiveInd = 'Y' " + 
			" " + 
			"AND tblTrkSpot.tblTrkSpot_ZoneID = ? " + 
			"and tblTrkSpot_TrackNbr = ? " + 
			"and tblTrkSpot_Spot = ? " + 
			" " + 
			"ORDER BY tblTrkSpot.tblTrkSpot_ZoneID, tblTrkSpot.tblTrkSpot_TrackNbr, tblTrkSpot.tblTrkSpot_Spot";

	@Override
	public String getSQL(String sqlName) {
		return null;
	}
}
