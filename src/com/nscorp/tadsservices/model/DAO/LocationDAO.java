package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Location;

public class LocationDAO extends TADSDAO<Location> {

	public LocationDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	Location get(String id) {
		return null;
	}

	@Override
	int set(Location obj) {
		return 0;
	}
	
	public int set(String sqlName, Location obj) {
		Object[] params = getParams(sqlName,obj);
		String sql = getSQL(sqlName);
		
		//Determine if need to call a stored procedure or use a query
		if(sql.startsWith("sp")) {
			return database.executeStoredProcedureUpdate(sql, params);
		} else {
			return database.executeQueryUpdate(sql, params);
		}
	}

	@Override
	int delete(Location obj) {
		return 0;
	}

	public Location getLocation(String sqlName, Location obj) {
		Object[] params = getParams(sqlName,obj);
		String sql = getSQL(sqlName);
		
		//Determine if need to call a stored procedure or use a query
		if(sql.startsWith("sp")) {
			return database.executeStoredProcedure(Location.class, fieldsMap, sql, params);
		} else {
			return database.executeQuery(Location.class, fieldsMap, sql, params);	
		}
	}
	
	public List<Location> getLastLocationUpdated() {
		String sql = "Select top 1 'Convoy' as Lot, tblBaySpot_ZoneID, tblBaySpot_RowID, tblBaySpot_Spot, tblBaySpot_ActiveInd, tblBaySpot_ChngWho, tblBaySpot_ChngDate from tblBaySpot " + 
				"where tblbayspot_chngdate = (select max(tblbayspot_chngdate) from tblbayspot) " + 
				"union " + 
				"Select top 1 'Loadline' as Lot, * from tblLLSpot " + 
				"where tblLLSpot_ChngDate = (select max(tblLLSpot_ChngDate) from tblllspot) " + 
				"union " + 
				"Select top 1 'Storage' as Lot, * from tblStoreSpot " + 
				"where tblStoreSpot_ChngDate = (select max(tblStoreSpot_ChngDate) from tblStoreSpot) " + 
				"union " + 
				"Select top 1 'Track' as Lot, * from tblTrkSpot " + 
				"where tblTrkSpot_ChngDate = (select max(tblTrkSpot_ChngDate) from tblTrkSpot) " + 
				"union " + 
				"Select top 1 'SupportTrack' as Lot, * FROM tblSupportTrackSpot " + 
				"where tblSupportTrackSpot_ChngDate = (select max(tblSupportTrackSpot_ChngDate) from tblSupportTrackSpot)";
		return database.executeQueryList(Location.class, fieldsMap, sql);
	}
	
	public List<Location> getLocationList(String sqlName, Location obj) throws SQLException{
		Object[] params = getParams(sqlName,obj);
		String sql = getSQL(sqlName);

		//Determine if need to call a stored procedure or use a query
		if(sql.startsWith("sp")) {
			return database.executeStoredProcedureList(Location.class, fieldsMap, sql, params);
		} else {
			return database.executeQueryList(Location.class, fieldsMap, sql, params);	
		}		
	}
	
	private Object[] getParams(String sqlName, Location obj) {
		Object[] params = null;
		
		switch(sqlName) {
		case "GetLoadLineSpots":
		case "GetLoadLineSpotsAvail":
		case "GetLoadLineSpotsUnAvail":
		case "GetLLineActive":
		case "GET_CONVOY_AVAIL":
		case "GET_LL_AVAIL":
		case "spGetEmptyStorageArea":
		case "spGetEmptySpotsLLArea":
			params = new Object[] {obj.getZone(),obj.getRow()};
			break;
		case "UPDATE_BAYROW_DEALER":
			params = new Object[] {obj.getManufacturer(),obj.getDealer(),obj.getZone(),obj.getRow()};
			break;
		case "spUpdLLineSpot":
			params = new Object[] {obj.getZone(),obj.getRow(),obj.getSpot(),(obj.isActive() ? "Y" : "N"),obj.getChngWho(),obj.getChngDate()};
			break;
		case "UpdLLine":
			params = new Object[] {obj.getManufacturer(),obj.getRoute(),(obj.isActive() ? "Y" : "N"),obj.getChngWho(),obj.getChngDate(),obj.getZone(),obj.getRow()};
			break;
		case "spGetConvoySpotByDealer":
			params = new Object[] {obj.getZone(),obj.getManufacturer(),obj.getDealer(),obj.getRowOrder(),obj.getSpotOrder()};
			break;
		case "spGetEmptyStorageZone":
		case "spGetEmptyLineNoRoute":
		case "spGetEmptyLineAnyRoute":
		case "spGetEmptyUnassignedLLSpot":
			params = new Object[] {obj.getZone()};
			break;
		case "spGetEmptySpotsConvoyArea2":
			params = new Object[] {obj.getZone(),obj.getRow(),obj.getSpotOrder(),(obj.isEndBay() == true ? "Y" : "N")};
			break;
		case "spGetEmptySpotsConvoy2":
			params = new Object[] {obj.getZone(),obj.getRowOrder(),obj.getSpotOrder(),(obj.isEndBay() == true) ? "Y" : "N"};
			break;
		case "spGetEmptyConvoyRowsNoDealer":
			params = new Object[] {obj.getZone(),obj.getRowOrder(),obj.getSpotOrder()};
			break;
		case "spGetEmptyLLRouteZone":
			params = new Object[] {obj.getZone(),obj.getManufacturer(),obj.getRoute()};
			break;
		case "spGetUnassignedLLSpotByVehTypeRouteZone":
		case "spGetEmptyLLByZoneVType":
			params = new Object[] {obj.getZone(),obj.getVehicleType(),obj.getManufacturer(),obj.getRoute()};
			break;
		default:
			break;	
		}
		
		return params;
	}
	
	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
		case "GetAllConvoySpots":
			sql = "Select 'Convoy' as Lot, * from tblBaySpot order by tblBaySpot_ZoneID, tblBaySpot_RowID, tblBaySpot_Spot";
			break;
		case "GetAllLoadlineSpots":
			sql = "Select 'Loadline' as Lot, * from tblLLSpot order by tblLLSpot_ZoneID, tblLLSpot_Line, tblLLSpot_Spot";
			break;
		case "GetAllStorageSpots":
			sql = "Select 'Storage' as Lot, * from tblStoreSpot order by tblStoreSpot_ZoneID, tblStoreSpot_AreaID, tblStoreSpot_Spot";
			break;
		case "GetAllTrackSpots":
			sql = "Select 'Track' as Lot, * from tblTrkSpot order by tblTrkSpot_ZoneID, tblTrkSpot_TrackNbr, tblTrkSpot_Spot";
			break;
		case "GetAllSupportTrackSpots":
			sql = "Select 'SupportTrack' as Lot, * FROM tblSupportTrackSpot order by tblSupportTrackSpot_ZoneID, tblSupportTrackSpot_TrackNbr, tblSupportTrackSpot_Spot";
			break;
		case "GetLLineActive":
			sql = "SELECT * FROM tblLLine WHERE tblLLine_ZoneID = ? AND tblLLine_LLineNbr = ? AND tblLLine_ActiveInd = 'Y'";
			break;
		case "GET_CONVOY_AVAIL":
			sql = "SELECT * FROM qryRowAvailability WHERE Zone = ? AND Row = ?";
			break;
		case "GET_LL_AVAIL":
			sql = "SELECT * FROM qryLLAvailability WHERE Zone = ? AND Line = ?";
			break;
		case "UPDATE_BAYROW_DEALER":
			sql = "UPDATE tblBayRow " + 
					"SET tblBayRow_Manufacturer = ? ," + 
					"    tblBayRow_Dealer = ? " + 
					"WHERE tblBayRow_ZoneID = ? " + 
					"AND tblBayRow_RowID = ?";
			break;
		case "UpdLLine":
			sql = "UPDATE tblLLine " + 
					"SET tblLLine_Mfg = ?, " + 
					"    tblLLine_Route = ?,  " + 
					"    tblLLine_ActiveInd = ?,   " + 
					"    tblLLine_ChngWho = ?,  " + 
					"    tblLLine_ChngDate = ? " + 
					"WHERE tblLLine_ZoneID = ?  " + 
					"AND tblLLine_LLineNbr = ?";
			break;
		default:
			sql = sqlName;
			break;
		}
		
		return sql;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		return null;
	}
	
	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				//Format is put("database column name", "class field name");
				
				//Location
				put("Lot", "Lot");
				put("Zone", "Zone");
				put("tblBaySpot_ZoneID","Zone");
				put("tblLLine_ZoneID", "Zone");
				put("tblLLSpot_ZoneID", "Zone");
				put("tblStoreSpot_ZoneID","Zone");
				put("tblTrkSpot_ZoneID","Zone");
				put("Row", "Row");
				put("Line", "Row");
				put("LoadLine","Row");
				put("tblBaySpot_RowID","Row");
				put("tblLLine_LLineNbr", "Row");
				put("tblLLSpot_Line","Row");
				put("tblStoreSpot_AreaID","Row");
				put("tblTrkSpot_TrackNbr","Row");
				put("tblBaySpot_Spot","Spot");
				put("tblLLSpot_Spot","Spot");
				put("tblStoreSpot_Spot","Spot");
				put("tblTrkSpot_Spot","Spot");
				put("Spot","Spot");
				
				//Vehicle Characteristics
				put("tblVIN_Manufacturer","Manufacturer");
				put("tblLLine_Mfg", "Manufacturer");
				put("tblLLine_TypeCode","VehicleType");
				put("tblLLine_Route", "Route");
				put("tblVIN_VIN","VIN");
				
				//Bay Capacity
				put("UsedSpots", "UsedSpots");
				put("TotalSpots", "TotalSpots");
				
				//Status 
				put("tblLLine_ActiveInd", "Active");
				put("tblLLSpot_ActiveInd", "Active");
				
				//Trucker 
				put("tblLLine_TransmitToTrucker","TransmitToTrucker");
				put("tblLLine_TransmitSCAC","TransmitSCAC");
				
				//Sort
				put("tblSort_RowOrder", "RowOrder");
				put("tblSort_SpotOrder", "SpotOrder");
				
				//User
				put("tblBaySpot_ChngWho", "ChngWho");
				put("tblBaySpot_ChngDate", "ChngDate");
				put("tblLLine_ChngWho", "ChngWho");
				put("tblLLine_ChngDate", "ChngDate");
				
				put("tblStoreSpot_ChngWho", "ChngWho");
				put("tblStoreSpot_ChngDate", "ChngDate");
				put("tblTrkSpot_ChngWho", "ChngWho");
				put("tblTrkSpot_ChngDate", "ChngDate");
				put("tblSupportTrackSpot_ChngWho", "ChngWho");
				put("tblSupportTrackSpot_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}

}
