package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.PreloadDetail;

public class PreloadDetailDAO extends TADSDAO<PreloadDetail> {

	public PreloadDetailDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public PreloadDetail get(String id) {
		Object[] params = new Object[] {id};
		String sql = "SELECT tblPreLoadDetail_Code as Code, " + 
				"	tblPreLoadDetail_VIN as VIN, " + 
				"	tblPreLoadDetail_StatusCode as StatusCode, " + 
				"	tblPreLoadDetail_ActionCode as ActionCode, " + 
				"	tblPreLoadDetail_LoadedDate as LoadedDate, " + 
				"	tblPreLoadDetail_ChangeWho as [Change Who], " + 
				"	tblPreLoadDetail_ChangeDate as [Change Date] " + 
				"FROM tblPreLoadDetail " + 
				"WHERE tblPreLoadDetail_VIN = ?";
		
		return database.executeQuery(PreloadDetail.class, fieldsMap, sql, params);
	}

	public PreloadDetail get(String code, String id) {
		Object[] params = new Object[] {code, id};
		String sql = "SELECT tblPreLoadDetail_Code as Code, " + 
				"	tblPreLoadDetail_VIN as VIN, " + 
				"	tblPreLoadDetail_StatusCode as StatusCode, " + 
				"	tblPreLoadDetail_ActionCode as ActionCode, " + 
				"	tblPreLoadDetail_LoadedDate as LoadedDate, " + 
				"	tblPreLoadDetail_ChangeWho as [Change Who], " + 
				"	tblPreLoadDetail_ChangeDate as [Change Date] " + 
				"FROM tblPreLoadDetail " + 
				"WHERE tblPreLoadDetail_Code = ? and tblPreLoadDetail_VIN = ?";
		
		return database.executeQuery(PreloadDetail.class, fieldsMap, sql, params);
	}
	
	public PreloadDetail getActive(String VIN) {
		Object[] params = new Object[] {VIN};
		String sql = "select tblPreLoadDetail_Code as Code, " + 
				"	tblPreLoadDetail_VIN as VIN, " + 
				"	tblPreLoadDetail_StatusCode as StatusCode, " + 
				"	tblPreLoadDetail_ActionCode as ActionCode, " + 
				"	tblPreLoadDetail_LoadedDate as LoadedDate, " + 
				"	tblPreLoadDetail_ChangeWho as [Change Who], " + 
				"	tblPreLoadDetail_ChangeDate as [Change Date], " + 
				"	tblPreLoad_Submitted as Submitted, " + 
				"	tblPreLoad_Expiration as Expiration " + 
				"From tblPreloadDetail, tblPreload  " + 
				"Where tblPreLoadDetail_Code = tblPreload_Code  " + 
				"and tblPreLoadDetail_VIN = ? " + 
				"and datediff(mi,getdate(), tblPreload_Expiration) > 0";
		
		return database.executeQuery(PreloadDetail.class, fieldsMap, sql, params);
	}
		
	public List<PreloadDetail> getDataAllByCodeStatus(String code, String status) throws SQLException {
		Object[] params = new Object[] {code,status};
		String sql = "spGetPreloadDetailStatus";
		
		return database.executeStoredProcedureList(PreloadDetail.class, fieldsMap, sql, params);
	}
	
	public List<PreloadDetail> getDataAllByCode(String code) throws SQLException {
		Object[] params = new Object[] {code};
		String sql = "spGetPreloadDetailbyCode";
		
		return database.executeStoredProcedureList(PreloadDetail.class, fieldsMap, sql, params);
	}
	
	@Override
	public int set(PreloadDetail obj) {
		Object[] params = new Object[] {obj.getCode(),obj.getVIN(),
				obj.getStatusCode(),obj.getActionCode(),obj.getLoadedDate(),
				obj.getChngwho(),obj.getChngdate()
		};
		String sql = "spInsertPreLoadDetail";
		
		return database.executeStoredProcedureUpdate(sql, params);
	}

	@Override
	public int delete(PreloadDetail obj) {
		Object[] params = new Object[] {obj.getCode(),obj.getVIN(),
				obj.getChngwho(),obj.getChngdate()
		};
		String sql = "spDeletePreLoadDetailVIN";
		
		return database.executeStoredProcedureUpdate(sql, params);
	}
	
	public void updateLoaded(PreloadDetail obj) {
		Object[] params = new Object[] {obj.getVIN(),obj.getStatusCode(),
				obj.getActionCode(), obj.getLoadedDate(),
				obj.getChngwho(), obj.getChngdate()};
		String sql = "spUpdatePreLoadLoaded";	
		
		database.executeStoredProcedureUpdate(sql, params);
	}
	
	public void updateUnLoaded(PreloadDetail obj) {
		Object[] params = new Object[] {obj.getVIN(),obj.getStatusCode(),
				obj.getActionCode(), obj.getChngwho(), obj.getChngdate()};
		String sql = "spUpdatePreLoadUnLoaded";	
		
		database.executeStoredProcedureUpdate(sql, params);
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
				put("VIN", "VIN");
				put("StatusCode", "StatusCode");
				put("ActionCode", "ActionCode");
				put("LoadedDate", "LoadedDate");
				put("Change Who", "ChngWho");
				put("Change Date", "Chngdate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
