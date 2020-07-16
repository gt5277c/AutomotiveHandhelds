package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Upfitter;
import com.nscorp.tadsservices.model.Enums.eUpfitter;
import com.nscorp.tadsservices.model.SearchCriteria.UpfitterSearchCriteria;

public class UpfitterDAO extends TADSDAO<Upfitter> {

	public UpfitterDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	Upfitter get(String id) {
		// Not used in TADS
		return null;
	}
	
	public Upfitter get(UpfitterSearchCriteria usc) {
		return database.executeStoredProcedure(Upfitter.class, fieldsMap, getSQL(usc.getSearchType()), usc.getParameters());
	}
	
	public List<Upfitter> getList(UpfitterSearchCriteria usc) throws SQLException{
		String sql = getSQL(usc.getSearchType());
		
		if(sql.startsWith("sp")) {
			return database.executeStoredProcedureList(Upfitter.class, fieldsMap, sql, usc.getParameters());	
		} else {
			return database.executeQueryList(Upfitter.class, fieldsMap, sql, usc.getParameters());
		}		
	}
	
	public Upfitter getLastUpfitterUpdated() {
		String sql = "select top 1 * from tblupfitter where tblUpfitter_ChngDate = (select max(tblUpfitter_ChngDate) from tblupfitter)";
		return database.executeQuery(Upfitter.class, fieldsMap, sql);
	}

	@Override
	public
	int set(Upfitter obj) {
		int result = 0;
		
		Object[] params = new Object[] {obj.getManufacturer(),obj.getCode(),obj.getName(),
				obj.getAddress(),obj.getCity(),obj.getState(),obj.getLot(),obj.getZone(),obj.getRow(),
				obj.getChngWho(),obj.getChngDate()};	
		result = database.executeStoredProcedureUpdate("spInsertUpfitter", params);
		
		return result;
	}

	@Override
	public
	int delete(Upfitter obj) {
		int result = 0;
		
		Object[] params = new Object[] {obj.getManufacturer(),obj.getCode()};
		result = database.executeStoredProcedureUpdate("spRemoveUpfitter", params);
		
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
		eUpfitter eFind;
		
		eFind = (eUpfitter)Find;
		
		switch(eFind) {
		case eFindUpfitter:
			sql = "spGetUpfitter";
			break;
		case eFindUpfitterAll:
			sql = "Select * from tblUpfitter";
			break;
		case eFindUpfitterManf:
			sql = "spGetAllUpfitterCodes";
			break;
		case eFindUpfitterVIN:
			sql = "spGetUpfitterVIN";
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
				put("tblUpfitter_Manufacturer", "Manufacturer");
				put("tblUpfitter_Code", "Code");
				put("tblUpfitter_Name", "Name");
				put("tblUpfitter_Address", "Address");
				put("tblUpfitter_City", "City");
				put("tblUpfitter_State", "State");
				put("tblUpfitter_LotCode", "Lot");
				put("tblUpfitter_ZoneID", "Zone");
				put("tblUpfitter_Row", "Row");
				put("tblUpfitter_ChngWho", "ChngWho");
				put("tblUpfitter_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
