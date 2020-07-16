package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.ProdStat;

public class ProdStatDAO extends TADSDAO<ProdStat> {

	public ProdStatDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	public
	ProdStat get(String id) {
		//id is the prod status code
		Object[] params = new Object[] {id};
		return database.executeQuery(ProdStat.class, fieldsMap, getSQL("GET_PROD_STATUS"), params);
	}
	
	public List<ProdStat> getList(){
		return database.executeQueryList(ProdStat.class, fieldsMap, getSQL("GET_ALL_PROD_STATUS"));
	}

	@Override
	public
	int set(ProdStat obj) {
		int result = 0;
		
		//Check to see if exists
		ProdStat p = this.get(obj.getCode());
				
		if(p != null) {
			Object[] params = new Object[] {obj.getDescription(),obj.getLot(),
					obj.getZone(),obj.getRow(),obj.getHoldManufacturer(),
					obj.getHoldReasonCode(),obj.getChngWho(),obj.getChngDate(),
					obj.getCode()
			};
			
			result = database.executeQueryUpdate(getSQL("UPDATE_PROD_STATUS"), params);
		} else {
			Object[] params = new Object[] {obj.getDescription(),obj.getLot(),
					obj.getZone(),obj.getRow(),obj.getHoldManufacturer(),
					obj.getHoldReasonCode(),obj.getChngWho(),obj.getChngDate()
			};
			
			result = database.executeQueryUpdate(getSQL("INSERT_PROD_STATUS"), params);
		}
		
		return result;
	}

	@Override
	public
	int delete(ProdStat obj) {
		Object[] params = new Object[] {obj.getCode()};
		return database.executeQueryUpdate(getSQL("Delete_PROD_STATUS"), params);
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
			case "GET_PROD_STATUS":
				sql = "SELECT * FROM tblProdStatus WHERE tblProdStatus_Code = ?";
				break;
			case "GET_ALL_PROD_STATUS":
				sql = "SELECT * FROM tblProdStatus ORDER BY tblProdStatus_Code";
				break;
			case "INSERT_PROD_STATUS":
				sql = "INSERT INTO tblProdStatus VALUES(?,?,?,?,?,?,?,?,?)";
				break;
			case "UPDATE_PROD_STATUS":
				sql = "UPDATE tblProdStatus SET " + 
						"tblProdStatus_Desc = ?, " + 
						"tblProdStatus_LotCode = ?, " + 
						"tblProdStatus_Zone = ?, " + 
						"tblProdStatus_Area = ?, " + 
						"tblProdStatus_HoldManufacturer = ?, " + 
						"tblProdStatus_HoldReasonCode = ?, " + 
						"tblProdStatus_ChngWho = ?, " + 
						"tblProdStatus_ChngDate = ? " + 
						"WHERE tblProdStatus_Code = ?";
				break;
			case "Delete_PROD_STATUS":
				sql = "DELETE FROM tblProdStatus WHERE tblProdStatus_Code = ?";
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
				put("tblProdStatus_Code", "Code");
				put("tblProdStatus_Desc", "Description");
				put("tblProdStatus_LotCode", "Lot");
				put("tblProdStatus_Zone", "Zone");
				put("tblProdStatus_Area", "Row");
				put("tblProdStatus_HoldManufacturer", "HoldManufacturer");
				put("tblProdStatus_HoldReasonCode", "HoldReasonCode");
				put("tblProdStatus_ChngWho", "ChngWho");
				put("tblProdStatus_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
