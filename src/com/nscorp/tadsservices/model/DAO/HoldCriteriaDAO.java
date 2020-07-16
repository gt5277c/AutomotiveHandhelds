package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.HoldCriteria;
import com.nscorp.tadsservices.model.Enums.eHoldCriteriaObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.HoldCriteriaSearchCriteria;

public class HoldCriteriaDAO extends TADSDAO<HoldCriteria> {
	
	public HoldCriteriaDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	public HoldCriteria get(String id) {
		return null;
	}
	
	public List<HoldCriteria> getList(HoldCriteriaSearchCriteria hcsc) throws SQLException{
		List<HoldCriteria> holdcriterias = null;
		
		holdcriterias = database.executeStoredProcedureList(HoldCriteria.class, fieldsMap, getSQL(hcsc.getSearchType()),hcsc.getParameters());
		
		return holdcriterias;
	}

	@Override
	public int set(HoldCriteria obj) {
		int outReturnVal = 0;
		Object[] params = new Object[] {
				0,
				obj.getManufacturer(),
				obj.getHoldCode(),
				obj.getVIN(),
				obj.getRoute(),
				obj.getDealer(),
				obj.getRailcar(),
				obj.getEmission(),
				obj.getVehicleType(),
				obj.getColorCode(),
				obj.getStatus(),
				obj.getProdStatus(),
				obj.getSoldCode(),
				obj.getArriveFromDate(),
				obj.getArriveToDate(),
				obj.getUpfitter(),
				obj.getChangWho(),
				obj.getChangeDate(),
				outReturnVal
		};
		
		outReturnVal = database.executeStoredProcedureUpdate("spInsertHoldCriteria", params);
		
		return outReturnVal;
	}

	@Override
	public int delete(HoldCriteria obj) {
		Object [] params = new Object[] {obj.getKey(), obj.getChangWho(), new Date()};
		return database.executeStoredProcedureUpdate("spDeleteHoldCriteria", params);
	}

	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				//Format is put("database column name", "class field name");
				put("CriteriaKey", "Key");
				put("Manf", "Manufacturer");
				put("Code", "HoldCode");
				put("VIN", "VIN");
				put("Route", "Route");
				put("Dealer", "Dealer");
				put("RailCar", "Railcar");
				put("Emission", "Emission");
				put("Vehicle Type", "VehicleType");
				put("Color Code", "ColorCode");
				put("Status Code", "Status");
				put("Prod Status", "ProdStatus");
				put("Sold Code", "SoldCode");
				put("ArriveFrom", "ArriveFromDate");
				put("ArriveTo", "ArriveToDate");
				put("Upfitter", "Upfitter");
				put("tblHoldCriteriaChngWho", "Change Who");
				put("tblHoldCriteriaChngDate", "Change Date");
			}
		};
		
		return columnsToFieldsMap;
	}

	@Override
	public String getSQL(String sqlName) {
		return null;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eHoldCriteriaObjAction eFind;
		
		eFind = (eHoldCriteriaObjAction) Find;

		switch(eFind) {
		case eHoldCriteriaFindCode:
			sql = "spGetHoldCriteriaCode";
			break;
		case eHoldCriteriaFindMfgCodeVIN:
			sql = "spGetHoldCriteriaByMfgCodeVIN";
			break;
		case eHoldCriteriaFindVIN:
			sql = "spGetHoldCriteriaByVIN";
			break;
		case eHoldCriteriaFindAll:
			sql = "spGetAllHoldCriteria";
			break;
		default:
			break;
		}
		
		return sql;
	}
}
