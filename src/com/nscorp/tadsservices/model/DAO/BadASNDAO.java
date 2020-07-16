package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.BadASN;
import com.nscorp.tadsservices.model.Database;

public class BadASNDAO extends TADSDAO<BadASN> {

	public BadASNDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public BadASN get(String id) {
		Object[] params = new Object[] {id};
		return database.executeStoredProcedure(BadASN.class, fieldsMap, "spGetBadAsnVIN", params);
	}
	
	public List<BadASN> getAllBadASN() throws SQLException{
		return database.executeStoredProcedureList(BadASN.class, fieldsMap, "spGetBadASN");
	}

	@Override
	public int set(BadASN obj) {
		Object[] params = new Object[] {obj.getVIN(),obj.getRoute(),obj.getDealer(),obj.getManufacturer(),
				obj.getRailCar(),obj.getEmission(),obj.getColorCode(),obj.getColorCommon(),
				obj.getColorMfrName(),obj.getStatusCode(),obj.getActionCode(),obj.getHoldReasonCode(),
				obj.getProdStatus(),obj.getSoldCode(),obj.getNotes(),obj.getDiversion(),
				obj.getLTD(),obj.getInboundSCAC(),obj.getInboundWayBill(),obj.getInboundWayBillSN(),
				obj.getDepartureDate(),obj.getOrigin(),obj.getLineSeries(),obj.getWheelbase(),
				obj.getChngWho(),obj.getChngDate()};
		
		return database.executeStoredProcedureUpdate("spInsertBadASN", params);
	}

	@Override
	public int delete(BadASN obj) {
		Object[] params = new Object[] {obj.getVIN()};
		return database.executeStoredProcedureUpdate("spDeleteBadAsnVIN", params);
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
				put("VIN", "VIN");
				put("Route", "Route");
				put("Dealer", "Dealer");
				put("Manufacturer", "Manufacturer");
				put("Railcar", "RailCar");
				put("Emission", "Emission");
				put("Color Code", "ColorCode");
				put("Color Common", "ColorCommon");
				put("Color MfrName", "ColorMfrName");
				put("Status Code ", "StatusCode");
				put("Action Code", "ActionCode");
				put("Hold Code", "HoldReasonCode");
				put("Prod", "ProdStatus");
				put("Sold Code", "SoldCode");
				put("Notes", "Notes");
				put("Diversion", "Diversion");
				put("LTD", "LTD");
				put("InboundSCAC", "InboundSCAC");
				put("InboundWayBill", "InboundWayBill");
				put("InboundWayBillSN", "InboundWayBillSN");
				put("DepartureDate", "DepartureDate");
				put("Origin", "Origin");
				put("InboundUpfitter", "InboundUpfitter");
				put("OutboundUpfitter", "OutboundUpfitter");
				put("LineSeries", "LineSeries");
				put("Wheelbase", "Wheelbase");
				put("ChngWho", "ChngWho");
				put("ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
