package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.OutboundJournal;
import com.nscorp.tadsservices.model.Enums.eMessageObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.OutboundJournalSearchCriteria;

public class OutboundJournalDAO extends TADSDAO<OutboundJournal> {

	public OutboundJournalDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public OutboundJournal get(String id) {
		return null;
	}
	
	public OutboundJournal get(Integer recnum) {
		Object[] params = new Object[] {recnum};
		return database.executeQuery(OutboundJournal.class, fieldsMap, getSQL("GetOutboundJournalRec"), params);
	}

	public OutboundJournal get(OutboundJournalSearchCriteria osc) {
		return database.executeQuery(OutboundJournal.class, fieldsMap, getSQL(osc.getSearchType()),osc.getParameters());
	}
	
	@Override
	public int set(OutboundJournal obj) {
		Object[] params = new Object[] {obj.getType(),obj.getData(),obj.getStatus(),obj.getErrormessage(),obj.getChngWho(),obj.getChngDate()};
		return database.executeStoredProcedureUpdate("spInsertOutboundJournal", params);
	}

	@Override
	public int delete(OutboundJournal obj) {
		Object[] params = new Object[] {obj.getKey()};
		return database.executeStoredProcedureUpdate("spDeleteOutboundJournal", params);
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
			case "GetOutboundJournalRec":
				sql = "SELECT tblOutboundJournal_Key AS [Key], tblOutboundJournal_Type AS [Type], " +
						"tblOutboundJournal_Status AS [Status], tblOutboundJournal_Data AS [Data], " +
						"tblOutboundJournal_ErrorMessage AS [ErrorMessage], tblOutboundJournal_ChngWho AS [ChangeWho], " +
						"tblOutboundJournal_ChngDate AS [ChangeDate] FROM tblOutboundJournal " + 
						"WHERE tblOutboundJournal_Key = ?";
				break;
			default:
				break;
		}
		return sql;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eMessageObjAction eFind;
		
		eFind = (eMessageObjAction)Find;
		
		switch(eFind) {
		case eMessageFindRec:
			//GetOutboundJournalRec
			sql = "SELECT tblOutboundJournal_Key AS [Key], tblOutboundJournal_Type AS [Type], " +
					"tblOutboundJournal_Status AS [Status], tblOutboundJournal_Data AS [Data], " +
					"tblOutboundJournal_ErrorMessage AS [ErrorMessage], tblOutboundJournal_ChngWho AS [ChangeWho], " +
					"tblOutboundJournal_ChngDate AS [ChangeDate] FROM tblOutboundJournal " + 
					"WHERE tblOutboundJournal_Key = ?";
			break;
		case eMessageFindQueued:
			//GetOutboundJournalQueued
			sql = "SELECT tblOutboundJournal_Key AS [Key], tblOutboundJournal_Type AS [Type], " +
					"tblOutboundJournal_Status AS [Status], tblOutboundJournal_Data AS [Data], " +
					"tblOutboundJournal_ErrorMessage AS [ErrorMessage], tblOutboundJournal_ChngWho AS [ChangeWho], " +
					"tblOutboundJournal_ChngDate AS [ChangeDate] " + 
					"FROM tblOutboundJournal " + 
					"WHERE tblOutboundJournal_Status = 'Q' " + 
					"ORDER BY tblOutboundJournal_Key";
			break;
		case eMessageFindAll:
			//GetOutboundJournalDateRange
			sql = "SELECT tblOutboundJournal_Key AS [Key], tblOutboundJournal_Type AS [Type], " +
					"tblOutboundJournal_Status AS [Status], tblOutboundJournal_Data AS [Data], " +
					"tblOutboundJournal_ErrorMessage AS [ErrorMessage], tblOutboundJournal_ChngWho AS [ChangeWho], " +
					"tblOutboundJournal_ChngDate AS [ChangeDate] FROM tblOutboundJournal " + 
					"WHERE (DATEDIFF(dd, tblOutboundJournal_ChngDate, GETDATE()) <= ?) " + 
					"ORDER BY tblOutboundJournal_Key";
			break;
		case eMessageFindUnSent:
			//GetOutboundJournalUnSent
			sql = "SELECT tblOutboundJournal_Key AS [Key], tblOutboundJournal_Type AS [Type], " +
					"tblOutboundJournal_Status AS [Status], tblOutboundJournal_Data AS [Data], " +
					"tblOutboundJournal_ErrorMessage AS [ErrorMessage], tblOutboundJournal_ChngWho AS [ChangeWho], " +
					"tblOutboundJournal_ChngDate AS [ChangeDate] FROM tblOutboundJournal " + 
					"WHERE tblOutboundJournal_Status <> 'S' " + 
					"ORDER BY tblOutboundJournal_Key";
			break;
		case eMessageFindTypes:
			//GetOutboundJournalTypes
			sql = "Select DISTINCT(tblOutboundJournal_Type) as [Type] from tblOutboundJournal order by tblOutboundJournal_Type";
			break;
		default:
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
				put("Key", "key");
				put("Type", "type");
				put("Data", "data");
				put("Status", "status");
				put("ErrorMessage", "errormessage");
				put("ChangeWho", "chngWho");
				put("ChangeDate", "chngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
