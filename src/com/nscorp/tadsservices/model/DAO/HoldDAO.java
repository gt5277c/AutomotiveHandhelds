package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Hold;
import com.nscorp.tadsservices.model.Enums.eHoldObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.HoldSearchCriteria;

public class HoldDAO extends TADSDAO<Hold> {
	
	public HoldDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap(); 
	}
	
	@Override
	public Hold get(String id) {
		// This method does not make much sense for Hold
		//	 	so it is not implemented.
		return null;
	}
	
	public Hold get(HoldSearchCriteria hsc) {
		return database.executeQuery(Hold.class, fieldsMap, getSQL(hsc.getSearchType()), hsc.getParameters());
	}
	
	public List<Hold> getList(HoldSearchCriteria hsc){
		return database.executeQueryList(Hold.class, fieldsMap, getSQL(hsc.getSearchType()),hsc.getParameters());
	}

	@Override
	public int set(Hold obj) {
		Object[] params = new Object[] {obj.getManufacturer(),obj.getHoldCode(), obj.getDescription(),
				obj.getHoldType(),obj.getHoldClass(),obj.getMfgHoldCode(),obj.getMfgReleaseCode(),
				obj.getMfgAuthorizationCode(),(obj.isRemoveOnTruckOut() ? "Y" : "N"),
				obj.getBayLot(),obj.getBayZone(),obj.getBayRow(),
				(obj.isUserUpdateable() ? "Y" : "N"),obj.getChangeWho(),obj.getChangeDate()
		};
		
		return database.executeStoredProcedureUpdate("spInsertHoldCode", params);
	}

	@Override
	public int delete(Hold obj) {
		Object[] params = new Object[] {obj.getManufacturer(),obj.getHoldCode()};
		return database.executeStoredProcedureUpdate("spDeleteHoldCode", params);
	}

	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				//Format is put("database column name", "class field name");
				put("tblHoldReasonCodes_Mfg", "Manufacturer");
				put("Mfg", "Manufacturer");
				put("tblHoldReasonCodes_Code", "HoldCode");
				put("Code", "HoldCode");
				put("Hold Reason Code", "HoldCode");				
				put("tblHoldReasonCodes_Desc", "Description");
				put("Desc", "Description");				
				put("Description", "Description");				
				put("tblHoldReasonCodes_Type", "HoldType");
				put("Type", "HoldType");
				put("tblHoldReasonCodes_Type", "HoldType");				
				put("tblHoldReasonCodes_Class", "HoldClass");
				put("Class", "HoldClass");
				put("tblHoldReasonCodes_MfgHoldCode", "MfgHoldcode");
				put("Mfg Hold Code", "MfgHoldcode");
				put("tblHoldReasonCodes_MfgReleaseCode", "MfgReleaseCode");
				put("Mfg Release Code", "MfgReleaseCode");
				put("tblHoldReasonCodes_MfgAuthCode", "MfgAuthorizationCode");
				put("Mfg Auth Code", "MfgAuthorizationCode");
				put("tblHoldReasonCodes_RemoveOnTruckOut", "RemoveOnTruckOut");
				put("Remove On Truckout", "RemoveOnTruckOut");
				put("tblHoldReasonCodes_BayLot", "BayLot");
				put("Bay Lot", "BayLot");
				put("tblHoldReasonCodes_BayZone", "BayZone");
				put("Bay Zone", "BayZone");
				put("tblHoldReasonCodes_BayRow", "BayRow");
				put("Bay Row", "BayRow");
				put("tblHoldReasonCodes_UserUpdateable", "UserUpdateable");
				put("User Updateable", "UserUpdateable");
				put("tblHoldReasonCodes_ChngWho", "ChangeWho");
				put("Change Who", "ChangeWho");
				put("tblHoldReasonCodes_ChngDate", "ChangeDate");
				put("Change Date", "ChangeDate");
				put("Hold Date", "ChangeDate");
			}
		};
		
		return columnsToFieldsMap;
	}
	
	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eHoldObjAction eFind;

		eFind = (eHoldObjAction)Find;
		
		switch(eFind) {
		case eHoldFindVIN:
			sql = "SELECT tblVINHolds_Mfg as 'Mfg', tblVINHolds_HoldReasonCode AS 'Hold Reason Code',  " + 
					"       tblHoldReasonCodes_Desc AS 'Description', tblVINHolds_HoldDate AS 'Hold Date' " + 
					"FROM tblVINHolds LEFT OUTER JOIN tblHoldReasonCodes ON " + 
					"     tblVINHolds_Mfg = tblHoldReasonCodes_Mfg AND " + 
					"     tblVINHolds_HoldReasonCode = tblHoldReasonCodes_Code " + 
					"WHERE tblVINHolds_VIN = ? " + 
					"ORDER BY tblVINHolds_HoldDate ASC";
			break;
		case eHoldFindMfg:
			sql = "SELECT tblHoldReasonCodes_Mfg AS 'Mfg', tblHoldReasonCodes_Code AS 'Code', tblHoldReasonCodes_Desc AS 'Desc', " + 
					"       tblHoldReasonCodes_Type AS 'Type', tblHoldReasonCodes_Class AS 'Class', tblHoldReasonCodes_MfgHoldCode AS 'Mfg Hold Code', " + 
					"       tblHoldReasonCodes_MfgReleaseCode AS 'Mfg Release Code', tblHoldReasonCodes_MfgAuthCode AS 'Mfg Auth Code', " + 
					"       tblHoldReasonCodes_RemoveOnTruckOut AS 'Remove On Truckout', tblHoldReasonCodes_UserUpdateable AS 'User Updateable', " + 
					"       tblHoldReasonCodes_BayLot AS 'Bay Lot', tblHoldReasonCodes_BayZone AS 'Bay Zone', tblHoldReasonCodes_BayRow AS 'Bay Row', " + 
					"       tblHoldReasonCodes_ChngWho AS 'Change Who', tblHoldReasonCodes_ChngDate AS 'Change Date' " + 
					"FROM tblHoldReasonCodes " + 
					"WHERE tblHoldReasonCodes_Mfg = ? " + 
					"ORDER BY tblHoldReasonCodes_Mfg, tblHoldReasonCodes_Code";
			break;
			
		case eHoldFindMfgCode:
			sql = "SELECT tblHoldReasonCodes_Mfg AS 'Mfg', tblHoldReasonCodes_Code AS 'Code', tblHoldReasonCodes_Desc AS 'Desc', " + 
					"       tblHoldReasonCodes_Type AS 'Type', tblHoldReasonCodes_Class AS 'Class', tblHoldReasonCodes_MfgHoldCode AS 'Mfg Hold Code', " + 
					"       tblHoldReasonCodes_MfgReleaseCode AS 'Mfg Release Code', tblHoldReasonCodes_MfgAuthCode AS 'Mfg Auth Code', " + 
					"       tblHoldReasonCodes_RemoveOnTruckOut AS 'Remove On Truckout', tblHoldReasonCodes_UserUpdateable AS 'User Updateable', " + 
					"       tblHoldReasonCodes_BayLot AS 'Bay Lot', tblHoldReasonCodes_BayZone AS 'Bay Zone', tblHoldReasonCodes_BayRow AS 'Bay Row', " + 
					"       tblHoldReasonCodes_ChngWho AS 'Change Who', tblHoldReasonCodes_ChngDate AS 'Change Date' " + 
					"FROM tblHoldReasonCodes " + 
					"WHERE tblHoldReasonCodes_Mfg = ? " + 
					"AND tblHoldReasonCodes_Code = ? " + 
					"ORDER BY tblHoldReasonCodes_Mfg, tblHoldReasonCodes_Code";
			break;
			
		case eHoldFindAll:
			sql = "SELECT tblHoldReasonCodes_Mfg AS 'Mfg', tblHoldReasonCodes_Code AS 'Code', tblHoldReasonCodes_Desc AS 'Desc', " + 
					"       tblHoldReasonCodes_Type AS 'Type', tblHoldReasonCodes_Class AS 'Class', tblHoldReasonCodes_MfgHoldCode AS 'Mfg Hold Code', " + 
					"       tblHoldReasonCodes_MfgReleaseCode AS 'Mfg Release Code', tblHoldReasonCodes_MfgAuthCode AS 'Mfg Auth Code', " + 
					"       tblHoldReasonCodes_RemoveOnTruckOut AS 'Remove On Truckout', tblHoldReasonCodes_UserUpdateable AS 'User Updateable', " + 
					"       tblHoldReasonCodes_BayLot AS 'Bay Lot', tblHoldReasonCodes_BayZone AS 'Bay Zone', tblHoldReasonCodes_BayRow AS 'Bay Row', " + 
					"       tblHoldReasonCodes_ChngWho AS 'Change Who', tblHoldReasonCodes_ChngDate AS 'Change Date' " + 
					"FROM tblHoldReasonCodes " + 
					"ORDER BY tblHoldReasonCodes_Mfg, tblHoldReasonCodes_Code";
			break;
		default:
			break;
		}
		
		return sql;
	}

	@Override
	public String getSQL(String sqlName) {
		return null;
	}
}
