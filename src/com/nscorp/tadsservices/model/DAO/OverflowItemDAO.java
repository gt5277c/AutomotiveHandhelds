package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.OverflowItem;

public class OverflowItemDAO extends TADSDAO<OverflowItem> {

	public OverflowItemDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap(); 
	}
	
	@Override
	public OverflowItem get(String id) {
		// Does not make sense in a TADS context
		//  	to return an overflow sequence that 
		//		only has one overflow item.
		return null;
	}
	
	public List<OverflowItem> getList(OverflowItem obj){
		Object[] params = new Object[] {obj.getStartLot(), obj.getStartZone()};
		
		return database.executeQueryList(OverflowItem.class, fieldsMap, getSQL("GET_OVERFLOW_SEQ"),params);
	}
	
	@Override
	public
	int set(OverflowItem obj) {
		int result = 0;
		
		Object[] params = new Object[] {obj.getStartLot(),obj.getStartZone(),
				obj.getSeqNbr(),obj.getLot(),obj.getZone(),
				obj.getChngWho(),obj.getChngDate()};

		result = database.executeQueryUpdate(getSQL("INSERT_OVERFLOW_SEQ"), params);
			
		return result;
	}

	@Override
	public
	int delete(OverflowItem obj) {
		int result = 0;
		
		Object[] params = new Object[] {obj.getStartLot(),obj.getStartZone()};		
		result = database.executeQueryUpdate("DELETE_OVERFLOW_SEQ", params);
		
		return result;
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
		case "GET_OVERFLOW_SEQ":
			sql = "SELECT * FROM tblOverflow " +
				  "WHERE tblOverflow_LotCode = ? " +
				  "AND tblOverflow_ZoneID = ? " + 
				  "ORDER BY tblOverflow_Seq";
			break;
		case "INSERT_OVERFLOW_SEQ":
			sql = "INSERT INTO tblOverflow (tblOverflow_LotCode, tblOverflow_ZoneID, "
					+ "tblOverflow_Seq, tblOverflow_OverflowLotCode, tblOverflow_OverflowZoneID, "
					+ "tblOverflow_ChngWho, tblOverflow_ChngDate) VALUES (?,?,?,?,?,?,?)";
			break;
		case "DELETE_OVERFLOW_SEQ":
			sql = "DELETE FROM tblOverflow WHERE tblOverflow_LotCode = ? AND tblOverflow_ZoneID = ?";
			break;
		default:
			sql = "SELECT * FROM tblOverflow " +
					  "WHERE tblOverflow_LotCode = ? " +
					  "AND tblOverflow_ZoneID = ? " + 
					  "ORDER BY tblOverflow_Seq";			
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
				put("tblOverflow_LotCode", "StartLot");
				put("tblOverflow_ZoneID", "StartZone");
				put("tblOverflow_Seq", "SeqNbr");
				put("tblOverflow_OverflowLotCode", "Lot");
				put("tblOverflow_OverflowZoneID", "Zone");				
				put("tblOverflow_ChngDate", "ChngDate");
				put("tblOverflow_ChngWho", "ChngWho");
			}
		};
		
		return columnsToFieldsMap;
	}
}
