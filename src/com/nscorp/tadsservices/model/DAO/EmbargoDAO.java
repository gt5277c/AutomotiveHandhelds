package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Embargo;

public class EmbargoDAO extends TADSDAO<Embargo>{

	public EmbargoDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public Embargo get(String railcar) {
		Object[] params = new Object[] {railcar};
		
		return database.executeStoredProcedure(Embargo.class, fieldsMap, "spGetEmbargoRailcar", params);
	}

	@Override
	int set(Embargo obj) {
		return 0;
	}

	@Override
	public int delete(Embargo obj) {
		Object[] params = new Object[] {obj.getRailcar()};
		String sql = "spDeleteEmbargo";
		
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
				put("tblEmbargo_Action", "Action");
				put("tblEmbargo_Railcar", "Railcar");
				put("tblEmbargo_Embargo", "Embargo");
				put("tblEmbargo_Permit", "Permit");
				put("tblEmbargo_ChangeWho", "ChngWho");
				put("tblEmbargo_ChangeDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
