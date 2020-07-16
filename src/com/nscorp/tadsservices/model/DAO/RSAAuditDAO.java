package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.RSAAudit;
import com.nscorp.tadsservices.model.Vehicle;
import com.nscorp.tadsservices.model.SearchCriteria.VehicleSearchCriteria;
import com.nscorp.tadsservices.model.Enums.eVINObjAction;

public class RSAAuditDAO extends TADSDAO<RSAAudit> {

	public RSAAuditDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	RSAAudit get(String id) {
		return null;
	}
	
	public List<RSAAudit> FindAuditVIN(String vehicle) throws SQLException {	
		Object[] params = new Object[] {vehicle};
		String sql = "spFindRSAAuditVIN";
		
		return database.executeStoredProcedureList(RSAAudit.class, fieldsMap, sql, params);
	}

	public List<RSAAudit> FindAuditRailcar(String Railcar) throws SQLException {	
		Object[] params = new Object[] {Railcar};
		String sql = "spFindRSAAuditRailcar";
		
		return database.executeStoredProcedureList(RSAAudit.class, fieldsMap, sql, params);
	}
	
	public List<RSAAudit> FindAuditAll() throws SQLException {
		String sql = "spFindRSAAuditAll";
		
		return database.executeStoredProcedureList(RSAAudit.class, fieldsMap, sql);
	}
	
	public List<RSAAudit> FindAuditSuccesss() throws SQLException {	
	String sql = "spFindRSAAuditSuccess";
		
		return database.executeStoredProcedureList(RSAAudit.class, fieldsMap, sql);
	}
	
	public void UpdateRSAAudit(String NewValue, String OldValue) {
		Object[] params = new Object[] {NewValue, OldValue};
		String sql = "spUpdateRSAAudit";
		
		database.executeStoredProcedureUpdate(sql, params);
	}
	
	@Override
	public int set(RSAAudit obj) {
		Object[] params = new Object[] {obj.getVIN(),obj.getRailcar(),
				obj.getChgWho(),obj.getChgDate()
		};
		String sql = "spRSAAudit";
		
		int result = database.executeStoredProcedureUpdate(sql, params);
		
		//Update the vehicle history
		Vehicle vehicle = new Vehicle(database);
		vehicle = vehicle.getVehicle(obj.getVIN());
		if(vehicle != null ) {
			vehicle.setChngWho(obj.getChgWho());
			vehicle.setActionCode(Constants.ACTION_RAILSHIP_APPROVE_AUDIT_SAVE);
			vehicle.SaveHistory();
		}
		return result;
	}

	@Override
	public int delete(RSAAudit obj) {
		Vehicle vehicle = new Vehicle(database);
		Object [] params = null;
		int result;
		
		if(obj.getVIN() != null && obj.getVIN().length() > 0) {
			params = new Object[] {obj.getVIN()};
			vehicle = vehicle.getVehicle(obj.getVIN());
		} else if(obj.getRailcar() != null && obj.getRailcar().length() > 0) {
			params = new Object[] {obj.getRailcar()};
			VehicleSearchCriteria vsc = new VehicleSearchCriteria();
			vsc.setSearchType(eVINObjAction.eVINFindRailCar);
			vsc.setRailcarNumber(obj.getRailcar());
			List<Vehicle> vehicles = vehicle.getVehicleList(vsc);
			if( vehicles != null && vehicles.size() > 0) {
				vehicle = vehicles.get(0);
			} else {
				vehicle = null;
			}
		}
		
		result = database.executeStoredProcedureUpdate("spDeleteRSAAudit", params);
		
		//Update the vehicle history
		if(vehicle != null ) {
			vehicle.setChngWho(obj.getChgWho());
			vehicle.setActionCode(Constants.ACTION_RAILSHIP_APPROVE_AUDIT_DELETE);
			vehicle.SaveHistory();
		}
		return result;
	}

	@Override
	public String getSQL(String sqlName) {
		return null;
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
