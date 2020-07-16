package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.ManfVINCode;
import com.nscorp.tadsservices.model.Manufacturer;

public class ManufacturerDAO extends TADSDAO<Manufacturer> {

	public ManufacturerDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public Manufacturer get(String id) {
		Object[] params = new Object[] {id};
		return database.executeQuery(Manufacturer.class, fieldsMap, getSQL("GET_MANF_REC"), params);
	}
	
	public Manufacturer getLastManufacturerUpdated() {
		String sql = "select top 1 * from tblmanf where tblManf_ChngDate = ( select max(tblManf_ChngDate) from tblmanf)";
		return database.executeQuery(Manufacturer.class, fieldsMap, sql);
	}
	
	public List<Manufacturer> getAllManufacturer(){
		return database.executeQueryList(Manufacturer.class, fieldsMap, getSQL("GET_ALL_MANFS"));
	}

	@Override
	public int set(Manufacturer obj) {
		Object[] params = new Object[] {obj.getCode(),obj.getName(),obj.getAddress(),
				obj.getCity(),obj.getState(),obj.getZIP(),obj.getMfgAbrrevName(),
				obj.getVINCheck(),obj.getChngWho(),obj.getChngDate()};
		
		return database.executeStoredProcedureUpdate("spInsertManf", params);
	}

	@Override
	public int delete(Manufacturer obj) {
		Object[] params = new Object[] {obj.getCode()};
		return database.executeStoredProcedureUpdate("spDeleteManufacturer", params);
	}
	
	public List<ManfVINCode> getManfVINCodes(){
		String sql = "SELECT tblManf_Manufacturer, tblManf_VINCheck, tblManfCodes_VINCode " + 
				"FROM tblManf, tblManfCodes " + 
				"where tblManf_Manufacturer = tblManfCodes_Manufacturer " + 
				"order by tblManf_Manufacturer, tblManfCodes_VINCode";
		
		return database.executeQueryList(ManfVINCode.class, ManfVINCode.columnsToFieldsMap, sql);
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
			case "GET_MANF_REC":
				sql = "SELECT * FROM tblManf WHERE tblManf_Manufacturer = ?";
				break;
			case "GET_ALL_MANFS":
				sql = "SELECT * FROM tblManf";
				break;
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
				put("tblManf_Manufacturer", "code");
				put("tblManf_Name", "name");
				put("tblManf_Address", "address");
				put("tblManf_City", "city");
				put("tblManf_State", "state");
				put("tblManf_ZIP", "ZIP");
				put("tblManf_MfgAbrrevName", "mfgAbrrevName");
				put("tblManf_VINCheck", "VINCheck");
				put("tblManf_ChngWho", "chngWho");
				put("tblManf_ChngDate", "chngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
