package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Dealer;
import com.nscorp.tadsservices.model.Enums.eDealerObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.DealerSearchCriteria;

public class DealerDAO extends TADSDAO<Dealer> {
	
	public DealerDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	public Dealer get(String id) {
		//Need a manufacturer and dealer
		return null;
	}
	
	public Dealer get(DealerSearchCriteria dsc) {
		return database.executeQuery(Dealer.class, fieldsMap, getSQL(dsc.getSearchType()),dsc.getParameters());
	}
	
	public Dealer getLastDealerUpdated() {
		String sql = "select top 1 * from tbldealer where tbldealer_chngdate = (Select max(tbldealer_chngdate) from tbldealer)";
		return database.executeQuery(Dealer.class, fieldsMap, sql);
	}

	public List<Dealer> getList(DealerSearchCriteria dsc){
		return database.executeQueryList(Dealer.class, fieldsMap, getSQL(dsc.getSearchType()),dsc.getParameters());
	}
	
	@Override
	public
	int set(Dealer obj) {
		int result = 0;
		
		DealerSearchCriteria dsc = new DealerSearchCriteria();
		dsc.setSearchType(eDealerObjAction.eDealerFindByMfgDealer);
		dsc.setManufacturer(obj.getManufacturer());
		dsc.setDealer(obj.getCode());

		//Check to see if Dealer exists
		Dealer d = get(dsc);
		if( d != null) {
			Object[] params = new Object[] {obj.getName(),
					obj.getAddress(),obj.getCity(),obj.getState(),
					obj.getZone(),obj.getChngWho(),obj.getChngDate(),
					obj.getManufacturer(),obj.getCode()};
			result = database.executeQueryUpdate(getSQL("UPDATE_DEALER_REC"),params);
		} else {
			Object[] params = new Object[] {obj.getManufacturer(),obj.getCode(),
					obj.getName(),obj.getAddress(),obj.getCity(),obj.getState(),
					obj.getZone(),obj.getChngWho(),obj.getChngDate()};
			result = database.executeQueryUpdate(getSQL("INSERT_DEALER_REC"),params);
		}
		
		return result;
	}

	@Override
	public
	int delete(Dealer obj) {
		int result = 0;
		
		Object[] params = new Object[] {obj.getManufacturer(),obj.getCode()};
		result = database.executeQueryUpdate(getSQL("DELETE_DEALER_REC"),params);
		
		return result;
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
		case "GetAllDealers":
			sql = "SELECT * " + 
				  "FROM tblDealer " + 
				  "WHERE tblDealer_Manufacturer = ?";			
			break;
		case "UPDATE_DEALER_REC":
			sql = "UPDATE tblDealer  " + 
					"SET tblDealer_Name = ?,  " + 
					"    tblDealer_Address= ?,  " + 
					"    tblDealer_City = ?,  " + 
					"    tblDealer_State = ?,  " + 
					"    tblDealer_ZoneID = ?,  " + 
					"    tblDealer_ChngWho = ?,  " + 
					"    tblDealer_ChngDate = ?  " + 
					"WHERE tblDealer_Manufacturer = ? " + 
					"AND tblDealer_Code = ?";
			break;
		case "INSERT_DEALER_REC":
			sql = "INSERT INTO tblDealer  " + 
					"VALUES (?,?,?,?,?,?,?,?,?)";			
			break;
		case "DELETE_DEALER_REC":
			sql = "DELETE FROM tblDealer  " + 
					"WHERE tblDealer_Manufacturer = ? " + 
					"AND tblDealer_code = ?";
			break;
		}

		return sql;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eDealerObjAction eFind;
		
		eFind = (eDealerObjAction)Find;
		
		switch(eFind) {
			case eDealerFindByMfg:
				sql = "SELECT * " + 
					  "FROM tblDealer " + 
					  "WHERE tblDealer_Manufacturer = ?";
				break;
			case eDealerFindByMfgDealer:
				sql = "SELECT * " + 
						"FROM  tblDealer  " + 
						"WHERE tblDealer_Manufacturer = ? " + 
						"AND tblDealer_Code = ?";
				break;
			case eDealerFindAll:
				sql = "SELECT * " + 
						"FROM tblDealer ";
				break;
			default:
				sql = "SELECT * " + 
						  "FROM tblDealer " + 
						  "WHERE tblDealer_Manufacturer = ?";
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
				put("tblDealer_Manufacturer", "Manufacturer");
				put("tblDealer_Code", "Code");
				put("tblDealer_Name", "Name");
				put("tblDealer_Address", "Address");
				put("tblDealer_City", "City");
				put("tblDealer_State", "State");
				put("tblDealer_ZoneID", "Zone");
				put("tblDealer_ChngWho", "ChngWho");
				put("tblDealer_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
