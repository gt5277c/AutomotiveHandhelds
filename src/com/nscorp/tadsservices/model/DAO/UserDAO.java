package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.User;

public class UserDAO extends TADSDAO<User> {

	public UserDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public	User get(String id) {
		Object[] params = new Object[] {id};
		return database.executeStoredProcedure(User.class, fieldsMap, "spGetTADSUser", params);
	}
	
	public List<User> getAll() throws SQLException{
		return database.executeStoredProcedureList(User.class, fieldsMap, "spGetAllTADSUsers");
	}

	@Override
	public int set(User obj) {
		Object[] params = new Object[] {obj.getUserID(),obj.getFirstName(),obj.getLastName(),obj.getDriverID(),
				(obj.getHandheldUserOnly() ? "Y" : "N"), (obj.getCanAddVIN() ? "Y" : "N"),
				(obj.getCanCreateVIN() ? "Y" : "N"), (obj.getCanDeleteVIN() ? "Y" : "N"),
				(obj.getCanModifyVIN() ? "Y" : "N"), (obj.getCanHoldVIN() ? "Y" : "N"), 
				(obj.getCanReleaseHoldVIN() ? "Y" : "N"), (obj.getCanTruckIn() ? "Y" : "N"), 
				(obj.getCanTruckOut() ? "Y" : "N"), (obj.getCanDriveIn() ? "Y" : "N"), 
				(obj.getCanDriveOut() ? "Y" : "N"), (obj.getCanPlantIn() ? "Y" : "N"),
				(obj.getCanReturnToPlant() ? "Y" : "N"), (obj.getCanInboundDirect() ? "Y" : "N"),
				(obj.getCanPreBay() ? "Y" : "N"), (obj.getCanCancelPreBay() ? "Y" : "N"),
				(obj.getCanBay() ? "Y" : "N"), (obj.getCanReassignVIN() ? "Y" : "N"),
				(obj.getCanLoadRailcar() ? "Y" : "N"), (obj.getCanUnloadRailcar() ? "Y" : "N"),
				(obj.getCanRailshipApprove() ? "Y" : "N"), (obj.getCanLTDRailcar() ? "Y" : "N"),
				(obj.getCanClearRailcar() ? "Y" : "N"), (obj.getCanSpotRailcar() ? "Y" : "N"), 
				(obj.getCanModifyLot() ? "Y" : "N"), (obj.getCanRunReports() ? "Y" : "N"), 
				(obj.getCanPhysicalInv() ? "Y" : "N"), (obj.getCanASNMntc() ? "Y" : "N"), 
				(obj.getCanDealerMntc() ? "Y" : "N"), (obj.getCanMfgMntc() ? "Y" : "N"), 
				(obj.getCanRailcarMntc() ? "Y" : "N"), (obj.getCanRouteMntc() ? "Y" : "N"),
				(obj.getCanVehicleTypeMntc() ? "Y" : "N"), (obj.getCanHoldMntc() ? "Y" : "N"),
				(obj.getCanBayingMntc() ? "Y" : "N"), (obj.getCanModifyAdvConsist() ? "Y" : "N"),
				(obj.getCanPreLoad() ? "Y" : "N"), (obj.getCanReverseTrack() ? "Y" : "N"), 
				(obj.getCanAdminVHA() ? "Y" : "N"), (obj.getCanAdminUpfitter() ? "Y" : "N"),
				(obj.getMQMessages() ? "Y" : "N"), (obj.getEmbargos() ? "Y" : "N"),
				(obj.getAdministrator() ? "Y" : "N"), obj.getLastPWChangeDate(),
				obj.getChangeWho(),obj.getChangeDate()
		};
				
		return database.executeStoredProcedureUpdate("spInsertUser", params);
	}

	@Override
	public int delete(User obj) {
		Object[] params = new Object[] {obj.getUserID()};
		return database.executeStoredProcedureUpdate("spDeleteUser", params);
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
			default:
				sql = sqlName;
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
				put("User ID", "UserID");
				put("First Name", "FirstName");
				put("Last Name", "LastName");
				put("Driver Id", "DriverID");
				put("Handheld User Only", "HandheldUserOnly");
				put("Can Add VIN", "CanAddVIN");
				put("Can Create VIN", "CanCreateVIN");
				put("Can Delete VIN", "CanDeleteVIN");
				put("Can Modify VIN", "CanModifyVIN");
				put("Can Hold VIN", "CanHoldVIN");
				put("Can Release Hold VIN", "CanReleaseHoldVIN");
				put("Can Truck In", "CanTruckIn");
				put("Can Truck Out", "CanTruckOut");
				put("Can Drive In", "CanDriveIn");
				put("Can Drive Out", "CanDriveOut");
				put("Can Plant In", "CanPlantIn");
				put("Can Return To Plant", "CanReturnToPlant");
				put("Can Inbound Direct", "CanInboundDirect");
				put("Can Pre Bay", "CanPreBay");
				put("Can Cancel Prebay", "CanCancelPreBay");
				put("Can Bay", "CanBay");
				put("Can Reassign VIN", "CanReassignVIN");
				put("Can Load Railcar", "CanLoadRailcar");
				put("Can Unload Railcar", "CanUnloadRailcar");
				put("Can Railship Approve", "CanRailshipApprove");
				put("Can LTD Railcar", "CanLTDRailcar");
				put("Can Clear Railcar", "CanClearRailcar");
				put("Can Spot Railcar", "CanSpotRailcar");
				put("Can Modify Lot", "CanModifyLot");
				put("Can Run Reports", "CanRunReports");
				put("Can Physical Inv", "CanPhysicalInv");
				put("Can ASN Mntc", "CanASNMntc");
				put("Can Dealer Mntc", "CanDealerMntc");
				put("Can Mfg Mntc", "CanMfgMntc");
				put("Can Railcar Mntc", "CanRailcarMntc");
				put("Can Route Mntc", "CanRouteMntc");
				put("Can VType Mntc", "CanVehicleTypeMntc");
				put("Can Hold Mntc", "CanHoldMntc");
				put("Can Baying Mntc", "CanBayingMntc");
				put("Can Modify Adv Consist", "CanModifyAdvConsist");
				put("Can PreLoad", "CanPreLoad");
				put("Can Reverse Track", "CanReverseTrack");
				put("Can Admin VHA", "CanAdminVHA");
				put("Can Admin Upfitter", "CanAdminUpfitter");
				put("MQMessages", "MQMessages");
				put("Embargos", "Embargos");
				put("Administrator", "Administrator");
				put("Last PW Change Date", "LastPWChangeDate");
				put("Change Who", "ChangeWho");
				put("Change Date", "ChangeDate");
			}
		};
		
		return columnsToFieldsMap;
	}
	
}
