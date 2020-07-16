package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.SortOrderItem;

public class SortOrderItemDAO extends TADSDAO<SortOrderItem> {

	public SortOrderItemDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	public SortOrderItem get(String id) {
		// Not implemented in TADS
		return null;
	}
	
	public SortOrderItem get(String Lot, String Zone) {
		Object[] params = new Object[] {Lot,Zone};
		return database.executeQuery(SortOrderItem.class, fieldsMap, getSQL("GET_SORT_ITEM"), params);
	}
	
	public List<SortOrderItem> getAllSortItems(){
		return database.executeQueryList(SortOrderItem.class, fieldsMap, getSQL("GET_ALL_SORT"));
	}

	@Override
	int set(SortOrderItem obj) {
		// Not implemented in TADS
		return 0;
	}

	public void setAllSortItems(List<SortOrderItem> soil) {
		
		//Clear the SortItems
		database.executeQueryUpdate(getSQL("DELETE_SORT_ITEMS"), null);
		
		for(SortOrderItem soi : soil) {
			Object[] params = new Object[] {soi.getLot(),soi.getZone(),soi.getRowOrder(),soi.getSpotOrder()};
			database.executeQueryUpdate(getSQL("INSERT_SORT_ITEM"), params);
		}
	}
	
	@Override
	int delete(SortOrderItem obj) {
		return 0;
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
		case "INSERT_SORT_ITEM":
			sql = "INSERT INTO tblSort "
					+ "( tblSort_LotCode, tblSort_ZoneID, tblSort_RowOrder, tblSort_SpotOrder) "
					+ "VALUES ( ?, ?, ?, ? )";
			break;
		case "DELETE_SORT_ITEMS":
			sql = "DELETE FROM tblSort";
			break;
		case "GET_ALL_SORT":
				sql = "SELECT * " + 
				"FROM tblSort " + 
				"ORDER BY tblSort_LotCode, tblSort_ZoneID";
			break;
		case "GET_SORT_ITEM":
			sql = "Select * from tblSort " +
				  "where tblSort_LotCode = ? " +
				  "and tblSort_ZoneID = ? " +
				  "order by tblSort_LotCode, tblSort_ZoneID";
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
				put("tblSort_LotCode", "Lot");
				put("tblSort_ZoneID", "Zone");
				put("tblSort_RowOrder", "RowOrder");
				put("tblSort_SpotOrder", "SpotOrder");
			}
		};
		
		return columnsToFieldsMap;
	}
}
