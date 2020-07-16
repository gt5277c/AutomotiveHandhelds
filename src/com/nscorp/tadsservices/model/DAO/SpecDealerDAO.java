package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.SpecDealer;

public class SpecDealerDAO extends TADSDAO<SpecDealer> {

	public SpecDealerDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	SpecDealer get(String id) {
		return null;
	}
	
	public SpecDealer get(String Manufacturer, String Dealer) {
		Object[] params = new Object[] {Manufacturer, Dealer};
		return database.executeQuery(SpecDealer.class, fieldsMap, getSQL("GET_SPEC_DEALER"), params);
	}
	
	public List<SpecDealer> getList(){
		return database.executeQueryList(SpecDealer.class, fieldsMap, getSQL("GET_ALL_SPEC_DEALERS"));
	}

	@Override
	public
	int set(SpecDealer obj) {
		int result = 0;
		
		//Check to see if exists
		SpecDealer specdealer = this.get(obj.getManufacturer(),obj.getCode());
		
		if(specdealer != null) {
			Object[] params = new Object[] {obj.getDescription(),obj.getManufacturer(),obj.getZone(),obj.getRow(),
					obj.getChngWho(),obj.getChngDate()
			};
			result = database.executeQueryUpdate("UPDATE_SPEC_DEALER", params);
		} else {
			Object[] params = new Object[] {obj.getManufacturer(),obj.getCode(),obj.getDescription(),
					obj.getLot(),obj.getZone(),obj.getRow(),obj.getChngWho(),obj.getChngDate()
			};
			result = database.executeQueryUpdate("INSERT_SPEC_DEALER", params);
		}
		
		return result;
	}

	@Override
	public
	int delete(SpecDealer obj) {
		Object[] params = new Object[] {obj.getManufacturer(),obj.getCode()};
		return database.executeQueryUpdate("DELETE_SPEC_DEALER", params);
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
			case "GET_SPEC_DEALER":
				sql = "SELECT * " + 
						"FROM tblSpecDealer " + 
						"WHERE tblSpecDealer_Manufacturer = ? " + 
						"AND tblSpecDealer_Dealer = ?";
				break;
			case "GET_ALL_SPEC_DEALERS":
				sql = "SELECT * FROM tblSpecDealer";
				break;
			case "INSERT_SPEC_DEALER":
				sql = "INSERT INTO tblSpecDealer ( tblSpecDealer_Manufacturer, tblSpecDealer_Dealer, tblSpecDealer_Desc, tblSpecDealer_LotCode, tblSpecDealer_ZoneID, " + 
						"				tblSpecDealer_Area, tblSpecDealer_ChngWho, tblSpecDealer_ChngDate ) " + 
						"VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
				break;
			case "UPDATE_SPEC_DEALER":
				sql = "UPDATE tblSpecDealer\r\n" + 
						"SET  	tblSpecDealer_Desc = ?, " + 
						"	tblSpecDealer_LotCode = ?, " + 
						"	tblSpecDealer_ZoneID = ?, " + 
						"	tblSpecDealer_Area = ?, " + 
						"	tblSpecDealer_ChngWho = ?, " + 
						"	tblSpecDealer_ChngDate = ? " + 
						"WHERE tblSpecDealer_Manufacturer = ? " + 
						"AND tblSpecDealer_Dealer = ?";
				break;
			case "DELETE_SPEC_DEALER":
				sql = "DELETE FROM tblSpecDealer " + 
						"WHERE tblSpecDealer_Manufacturer = ? " + 
						"AND tblSpecDealer_Dealer = ?";
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
				put("tblSpecDealer_Manufacturer", "Manufacturer");
				put("tblSpecDealer_Dealer", "Code");
				put("tblSpecDealer_Desc", "Description");
				put("tblSpecDealer_LotCode", "Lot");
				put("tblSpecDealer_ZoneID", "Zone");
				put("tblSpecDealer_Area", "Row");
				put("tblSpecDealer_ChngWho", "ChngWho");
				put("tblSpecDealer_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
