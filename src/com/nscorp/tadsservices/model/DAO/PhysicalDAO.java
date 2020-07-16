package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Physical;

public class PhysicalDAO extends TADSDAO<Physical>{

	public PhysicalDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public Physical get(String id) {
		Object[] params = new Object[] {id};
		String sql = "spGet_Physical_Inv_Code";
		return database.executeStoredProcedure(Physical.class, fieldsMap, sql, params);
	}
	
	public List<Physical> GetAllPhysicals() throws SQLException{
		String sql = "spGet_Physical_Inv_Codes";
		return database.executeStoredProcedureList(Physical.class, fieldsMap, sql);
	}
	
	public List<Physical> GetAllActivePhysicals() throws SQLException{
		String sql = "spGet_Physical_Inv_Codes_Active";
		return database.executeStoredProcedureList(Physical.class, fieldsMap, sql);
	}

	@Override
	public int set(Physical obj) {
		Object[] params = new Object[] {obj.getCode(),obj.getStart(), obj.getEnd(),
				obj.getLot(),obj.getZone(),obj.getRow(),obj.getManufacturer(),
				obj.isUseConvoySpots() ? "Y" : "N",
				obj.getChngWho(),obj.getChngDate()};
		String sql = "spInsertPhysical";
		
		return database.executeStoredProcedureUpdate(sql, params);
	}

	@Override
	public int delete(Physical obj) {
		Object[] params = new Object[] {obj.getCode()};
		String sql = "spDeletePhysical";
		return database.executeStoredProcedureUpdate(sql, params);
	}

	public void CleanLoadLineSpots() {
		String sql = "spDeletePhysicalLoadLineSpots";
		database.executeStoredProcedureUpdate(sql, null);
	}
	
	public void CleanConvoySpots() {
		String sql = "spDeletePhysicalConvoySpots";
		database.executeStoredProcedureUpdate(sql, null);
	}
	
	@Override
	public String getSQL(String sqlName) {
		return null;
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
				put("Code", "Code");
				put("Start", "Start");
				put("Ended", "End");
				put("Lot", "Lot");
				put("Zone", "Zone");
				put("Area", "Row");
				put("Manufacturer","Manufacturer");
				put("UseConvoySpots","UseConvoySpots");
				put("ChngWho","Change Who");
				put("ChngDate","Change Date");
			}
		};
		
		return columnsToFieldsMap;
	}
}
