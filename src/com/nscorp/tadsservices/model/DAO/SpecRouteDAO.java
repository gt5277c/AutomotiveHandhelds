package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.SpecRoute;

public class SpecRouteDAO extends TADSDAO<SpecRoute> {

	public SpecRouteDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	SpecRoute get(String id) {
		//Need a manufacturer and a route
		return null;
	}
	
	public SpecRoute get(String Manufacturer, String Route) {
		Object[] params = new Object[] {Manufacturer, Route};
		return database.executeQuery(SpecRoute.class, fieldsMap, getSQL("GET_SPEC_ROUTE"),params);
	}

	public List<SpecRoute> getList(){
		return database.executeQueryList(SpecRoute.class, fieldsMap, getSQL("GET_ALL_SPEC_ROUTES"));
	}
	
	@Override
	public
	int set(SpecRoute obj) {
		int result = 0;
		
		//Check to see if exists
		SpecRoute specroute = this.get(obj.getManufacturer(),obj.getCode());
		
		if(specroute != null) {
			Object[] params = new Object[] {obj.getDescription(),obj.getLot(),
					obj.getZone(),obj.getRow(),obj.getChngWho(),obj.getChngDate(),
					obj.getManufacturer(),obj.getCode()
			};
			result = database.executeQueryUpdate(getSQL("UPDATE_SPEC_ROUTE"), params);
		} else {
			Object[] params = new Object[] {obj.getManufacturer(),obj.getCode(),
					obj.getDescription(),obj.getLot(),obj.getZone(),obj.getRow(),
					obj.getChngWho(),obj.getChngDate()
			};
			result = database.executeQueryUpdate(getSQL("INSERT_SPEC_ROUTE"), params);
		}
		
		return result;
	}

	@Override
	public
	int delete(SpecRoute obj) {
		Object[] params = new Object[] {obj.getManufacturer(),obj.getCode()};
		return database.executeQueryUpdate(getSQL("DELETE_SPEC_ROUTE"), params);
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
			case "GET_SPEC_ROUTE":
				sql = "SELECT * " + 
						"FROM tblSpecRoute " + 
						"WHERE tblSpecRoute_Mfg = ? " + 
						"AND tblSpecRoute_Route = ?";
				break;
			case "GET_ALL_SPEC_ROUTES":
				sql = "SELECT * FROM tblSpecRoute";
				break;
			case "INSERT_SPEC_ROUTE":
				sql = "INSERT INTO tblSpecRoute ( tblSpecRoute_Mfg, tblSpecRoute_Route, tblSpecRoute_Desc, tblSpecRoute_LotCode, " + 
						"                           tblSpecRoute_ZoneID, tblSpecRoute_Area, tblSpecRoute_ChngWho, tblSpecRoute_ChngDate ) " + 
						"VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
				break;
			case "UPDATE_SPEC_ROUTE":
				sql = "UPDATE tblSpecRoute " + 
						"SET  	tblSpecRoute_Desc = ?, " + 
						"	tblSpecRoute_LotCode = ?, " + 
						"	tblSpecRoute_ZoneID = ?, " + 
						"	tblSpecRoute_Area = ?, " + 
						"	tblSpecRoute_ChngWho = ?, " + 
						"	tblSpecRoute_ChngDate = ? " + 
						"WHERE tblSpecRoute_Mfg = ? " + 
						"AND tblSpecRoute_Route = ?";
				break;
			case "DELETE_SPEC_ROUTE":
				sql = "DELETE FROM tblSpecRoute " + 
						"WHERE tblSpecRoute_Mfg = ? " + 
						"AND tblSpecRoute_Route = ?";
			default:
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
				put("tblSpecRoute_Mfg", "Manufacturer");
				put("tblSpecRoute_Route", "Code");
				put("tblSpecRoute_Desc", "Description");
				put("tblSpecRoute_LotCode", "Lot");
				put("tblSpecRoute_ZoneID", "Zone");
				put("tblSpecRoute_Area", "Row");
				put("tblSpecRoute_ChngWho", "ChngWho");
				put("tblSpecRoute_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
