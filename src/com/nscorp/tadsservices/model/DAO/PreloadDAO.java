package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Preload;

public class PreloadDAO extends TADSDAO<Preload> {

	public PreloadDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public Preload get(String id) {
		Object[] params = new Object[] {id};
		String sql = "select tblPreLoad_Code as Code, " + 
				"	tblPreLoad_Action as ActionCode, " + 
				"	tblPreLoad_Submitted as Submitted, " + 
				"	tblPreLoad_Expiration as Expiration, " + 
				"	tblPreLoad_Extended as Extended, " + 
				"	tblPreLoad_DeleteOnCancel as DeleteOnCancel, " + 
				"	tblPreLoad_ChangeWho as [Change Who], " + 
				"	tblPreLoad_ChangeDate as [Change Date] " + 
				"from tblpreload " + 
				"where tblPreload_Code = ?";
		
		return database.executeQuery(Preload.class, fieldsMap, sql, params);
	}

	public List<Preload> getAllPreloads(){
		String sql = "select tblPreLoad_Code as Code, " + 
				"	tblPreLoad_Action as ActionCode, " + 
				"	tblPreLoad_Submitted as Submitted, " + 
				"	tblPreLoad_Expiration as Expiration, " + 
				"	tblPreLoad_Extended as Extended, " + 
				"	tblPreLoad_DeleteOnCancel as DeleteOnCancel, " + 
				"	tblPreLoad_ChangeWho as [Change Who], " + 
				"	tblPreLoad_ChangeDate as [Change Date] " + 
				"from tblpreload";
		
		return database.executeQueryList(Preload.class, fieldsMap, sql);
	}
	
	@Override
	public int set(Preload obj) {
		Object[] params = new Object[] {obj.getCode(),obj.getActionCode(),
				obj.getSubmit(), obj.getExpiration(),obj.getExtended(),
				obj.getDeleteOnCancel(),obj.getChngWho(),obj.getChngdate()
		};
		
		String sql = "spInsertPreLoad";
		
		return database.executeStoredProcedureUpdate(sql, params);
	}

	@Override
	public int delete(Preload obj) {
		Object[] params = new Object[] {obj.getCode(), obj.getActionCode(),obj.getChngWho(),obj.getChngdate()};
		String sql = "spDeletePreLoad";
		
		return database.executeStoredProcedureUpdate(sql, params);
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
				put("ActionCode", "ActionCode");
				put("Submitted", "Submit");
				put("Expiration", "Expiration");
				put("Extended", "Extended");
				put("DeleteOnCancel", "DeleteOnCancel");
				put("Change Who", "ChngWho");
				put("Change Date", "Chngdate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
