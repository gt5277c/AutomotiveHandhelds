package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Seal;

public class SealDAO extends TADSDAO<Seal> {

	public SealDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		return null;
	}
	
	@Override
	Seal get(String id) {
		return null;
	}

	public Seal getOldest(Seal obj) {
		Object[] params = new Object[] {obj.getRailcar(),obj.getWaybill()};
		String sql = "spFindSealNumAscByRCWB";
		
		return database.executeStoredProcedure(Seal.class, fieldsMap, sql, params);
	}
	
	public Seal getNewest(Seal obj) {
		Object[] params = new Object[] {obj.getRailcar(),obj.getWaybill()};
		String sql = "spFindSealNumDescByRCWB";

		return database.executeStoredProcedure(Seal.class, fieldsMap, sql, params);
	}
	
	public List<Seal> getAll() throws SQLException{
		String sql = "spGetAllSealNum";
		
		return database.executeStoredProcedureList(Seal.class, fieldsMap, sql);
	}
	
	@Override
	public int set(Seal obj) {
		Object[] params = new Object[] {obj.getRailcar(),obj.getWaybill(),
				obj.getSeal1(),obj.getSeal2(),obj.getSeal3(),obj.getSeal4(),
				obj.getComment(),obj.getVerified(),
				obj.getChangeWho(),obj.getChangeDate()};
		String sql = "spInsertSealNum";
		
		return database.executeStoredProcedureUpdate(sql, params);
	}

	@Override
	public int delete(Seal obj) {
		Object[] params = new Object[] {obj.getRailcar(),obj.getWaybill()};
		String sql = "spDeleteSealNum";
		
		return database.executeStoredProcedureUpdate(sql, params);
	}

	@Override
	public String getSQL(String sqlName) {
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
				put("tblSeal_Key", "key");
				put("tblSeal_Railcar", "railcar");
				put("tblSeal_WB", "waybill");
				put("tblSeal_Num1", "seal1");
				put("tblSeal_Num2", "seal2");
				put("tblSeal_Num3", "seal3");
				put("tblSeal_Num4", "seal4");
				put("tblSeal_Comment","comment");
				put("tblSeal_Verified","verified");
				put("tblSeal_ChangeWho", "ChangeWho");
				put("tblSeal_ChangeDate", "ChangeDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
