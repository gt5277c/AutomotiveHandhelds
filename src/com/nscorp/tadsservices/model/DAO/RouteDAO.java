package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Route;
import com.nscorp.tadsservices.model.Enums.eRouteObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.RouteSearchCriteria;

public class RouteDAO extends TADSDAO<Route>{

	public RouteDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	public Route get(String id) {
		return null;
	}
	
	public Route getLastRouteUpdated() {
		String sql = "select top 1 * from tblroute where tblRoute_ChngDate = (Select max(tblRoute_ChngDate) from tblroute)";
		return database.executeQuery(Route.class, fieldsMap, sql);
	}
	
	public Route get(RouteSearchCriteria rsc) {
		String sql = getSQL(rsc.getSearchType());
		Object [] params = rsc.getParameters();
		
		if(sql.startsWith("sp")) {
			//Run the stored procedure
			return database.executeStoredProcedure(Route.class, fieldsMap, sql, params);	
		} else {
			//Run the query
			return database.executeQuery(Route.class, fieldsMap, sql, params);
		}
	}

	public List<Route> getList(RouteSearchCriteria rsc) throws SQLException{
		Object[] params = rsc.getParameters();
		String sql = getSQL(rsc.getSearchType());
		
		if(sql.startsWith("sp")) {
			return database.executeStoredProcedureList(Route.class, fieldsMap, sql, params);
		} else {
			return database.executeQueryList(Route.class, fieldsMap, sql, params);
		}
		
	}
	
	@Override
	public
	int set(Route obj) {
		int result = 0;
		
		//Check to see if Route exists
		RouteSearchCriteria rsc = new RouteSearchCriteria();
		rsc.setSearchType(eRouteObjAction.eRouteFindByMfgRoute);
		rsc.setManufacturer(obj.getManufacturer());
		rsc.setRoute(obj.getRoute());
		
		Route r = get(rsc);
		if( r != null ) {
			Object[] params = new Object[] {obj.getManufacturer(), obj.getRoute(),
					obj.getStationName(), obj.getCity(), obj.getState(),obj.getTrainNbr(),
					obj.getZone(),obj.getBillingRoute(), (obj.isRailRoute()) ? "Y" : "N",
					obj.getSCAC(), obj.getFSAC(), obj.getSTCC(), obj.getRevenueRWC(),
					obj.getNonRevenueRWC(),obj.getReceivingRailroad(),obj.getRailJunction(),
					obj.getDeliveryRailRoad(),(obj.isMixRoute()) ? "Y" : "N",
					(obj.isRestrictedRoute()) ? "Y" : "N",
					obj.getChngWho(), obj.getChngDate()};
			result = database.executeStoredProcedureUpdate("spUpdateRouteRec", params);
		} else {
			Object[] params = new Object[] {obj.getManufacturer(), obj.getRoute(),
					obj.getStationName(), obj.getCity(), obj.getState(),obj.getTrainNbr(),
					obj.getZone(),obj.getBillingRoute(), (obj.isRailRoute()) ? "Y" : "N",
					obj.getSCAC(), obj.getFSAC(), obj.getSTCC(), obj.getRevenueRWC(),
					obj.getNonRevenueRWC(),obj.getReceivingRailroad(),obj.getRailJunction(),
					obj.getDeliveryRailRoad(),(obj.isMixRoute()) ? "Y" : "N",
					(obj.isRestrictedRoute()) ? "Y" : "N",
					obj.getChngWho(), obj.getChngDate()};
			result = database.executeStoredProcedureUpdate("spInsertRouteRec", params);
		}
		
		//'Changed By : Greg Macklem 'Changed On : 07/26/2000
		//Added check of Rail Route flag and the insert or delete of route assocations.
		//This is because a rail route cannot be on the right hand side of a route assocation.		
		if(obj.isRailRoute()) {
			Object[] params = new Object[] {obj.getManufacturer(),obj.getRoute()};
			result = database.executeStoredProcedureUpdate("spDeleteRouteAssociations", params);
		} else {
			Object[] params = new Object[] {obj.getManufacturer(),obj.getRoute(),
					obj.getChngWho(),obj.getChngDate(),obj.getManufacturer(),obj.getRoute()};
			result = database.executeStoredProcedureUpdate("spCreateRouteAssociations", params);			
		}
		
		return result;
	}

	@Override
	public
	int delete(Route obj) {
		int result = 0;
		
		Object[] params = new Object[] {obj.getManufacturer(),obj.getRoute()};
		result = database.executeStoredProcedureUpdate("spDeleteRouteRec", params);
		
		return result;
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		return sql;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eRouteObjAction eFind;
		
		eFind = (eRouteObjAction)Find;
		
		switch(eFind) {
		case eRouteFindByMfg:
			sql = "spGetRouteRecsByMfg";
			break;
		case eRouteFindByMfgRoute:
			sql = "spGetRouteRec";
			break;
		case eRouteFindRampRoute:
			sql = "SELECT tblRampRoute_Key, tblRampRoute_Mfg, tblManf_Name, tblRampRoute_Route  " + 
					"FROM tblRampRoute INNER JOIN tblManf ON " + 
					"     tblRampRoute_Mfg = tblManf_Manufacturer";
			break;
		case eRouteFindRailcarAssoc:
			sql = "SELECT tblRailcarRouteAssoc.tblRailcarRouteAssoc_AssocMfg AS [MfgCode], tblManf.tblManf_Name AS [MfgName], " +
					"		tblRailcarRouteAssoc.tblRailcarRouteAssoc_AssocRoute AS [Route] " + 
					"FROM tblRailcarRouteAssoc INNER JOIN tblManf ON " + 
					"     tblRailcarRouteAssoc.tblRailcarRouteAssoc_AssocMfg = tblManf.tblManf_Manufacturer " + 
					"WHERE tblRailcarRouteAssoc.tblRailcarRouteAssoc_Mfg = ? " + 
					"AND tblRailcarRouteAssoc.tblRailcarRouteAssoc_Route = ?";
			break;
		case eRouteFindAll:
			sql = "spGetRoutes";
			break;
		default:
			break;		
		}
		
		
		return sql;
	}

	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				//Format is put("database column name", "class field name");
				//tblRoute
				put("tblRoute_Manufacturer", "Manufacturer");
				put("tblRoute_Route", "Route");
				put("tblRoute_StationName", "StationName");
				put("tblRoute_City", "City");
				put("tblRoute_State", "State");
				put("tblRoute_TrainNbr", "TrainNbr");
				put("tblRoute_ZoneID", "Zone");
				put("tblRoute_BillingRoute", "BillingRoute");
				put("tblRoute_RailRoute", "RailRoute");
				put("tblRoute_SCAC", "SCAC");
				put("tblRoute_FSAC", "FSAC");
				put("tblRoute_STCC", "STCC");
				put("tblRoute_RevenueRWC", "RevenueRWC");
				put("tblRoute_NonRevenueRWC", "NonRevenueRWC");
				put("tblRoute_ReceivingRRoad", "ReceivingRailroad");
				put("tblRoute_RailJunction", "RailJunction");
				put("tblRoute_DeliveryRRoad", "DeliveryRailRoad");
				put("tblRoute_MixRoute", "MixRoute");
				put("tblRoute_RailcarRestricted", "RestrictedRoute");
				put("tblRoute_ChngWho", "ChngWho");
				put("tblRoute_ChngDate", "ChngDate");
				
				//tblRampRoute
				put("tblRampRoute_Mfg", "Manufacturer");
				put("tblManf_Name","ManufacturerName");
				put("tblRampRoute_Route","Route");				
							
				//tblManf
				put("tblManf_Manufacturer", "Manufacturer");
				put("tblManf_Name", "ManufacturerName");
				put("tblManf_Address", "Address");
				put("tblManf_City", "City");
				put("tblManf_State", "State");
				put("tblManf_ZIP", "ZIP");
				put("tblManf_MfgAbrrevName", "ManufacturerAbbrev");
				put("tblManf_VINCheck", "VINCheck");
				put("tblManf_ChngWho", "ChngWho");
				put("tblManf_ChngDate", "ChngDate");
				
				//tblRailcarRouteAssoc
				put("MfgCode", "Manufacturer");
				put("MfgName", "ManufacturerName");
				put("Route", "Route");
			}
		};
		
		return columnsToFieldsMap;
	}
}
