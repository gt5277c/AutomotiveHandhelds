package com.nscorp.tadsservices.model.DAO;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Hold;
import com.nscorp.tadsservices.model.Vehicle;
import com.nscorp.tadsservices.model.Enums.eVINObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.VehicleSearchCriteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleDAO extends TADSDAO<Vehicle> {
	
	public VehicleDAO(Database database){
		this.database = database;
		fieldsMap = getColumnsToFieldsMap(); 
	}
	
	@Override
	public Vehicle get(String VIN) {
		// Get the vehicle information
		String sql = "Select * from tblvin where tblvin_vin = ?";
		
		return database.executeQuery(Vehicle.class, fieldsMap, sql, new Object[] {VIN});
	}
	
	public Vehicle get(VehicleSearchCriteria vsc) {
		return database.executeQuery(Vehicle.class, fieldsMap, getSQL(vsc.getSearchType()),vsc.getParameters());
	}
	
	public List<Vehicle> getList(VehicleSearchCriteria vsc) {		
		return database.executeQueryList(Vehicle.class, fieldsMap, getSQL(vsc.getSearchType()),vsc.getParameters());
	}
	
	@Override
	public int set(Vehicle v) {
		//Save the vehicle information
		int result = 0;
		Object[] params = new Object[] {v.getVIN(),v.getLotCode(),Common.PadString(v.getZone(), Constants.ZONE_LEN), Common.PadString(v.getRow(), Constants.AREA_LEN),
				v.getSpot(),v.getRoute(),v.getDealer(),v.getInboundUpfitter(),v.getOutboundUpfitter(), v.getRoutePrefix(),v.getManufacturer(),v.getInboundTrainID(),
				v.getInboundEventTime(),v.getInboundRailcarNumber(),v.getInboundDeckLevel(),v.getInboundPos(),v.getOutboundTrainID(),v.getOutboundEventTime(),
				v.getOutboundRailcarNumber(),v.getOutboundDeckLevel(),v.getOutboundPos(),v.getInboundDriver(), v.getOutboundDriver(), v.getEmissionCode(),
				v.getVehicleType(),v.getColorCode(),v.getStatusCode(),(v.getInboundTruckID() == null) ? " " : v.getInboundTruckID(),v.getInboundTruckCo(),v.getOutboundTruckID(),v.getOutboundTruckCo(),
				v.getActionCode(),v.getHoldReasonCode(),v.getProdStatus(),v.getSoldCode(),v.getDiversion(), (v.getLTD() != null && v.getLTD() ? "Y" : "N") ,
				(v.getProcessedAsLTD() != null && v.getProcessedAsLTD() ? "Y" : "N"),v.getLTDRoute(),v.getOrigin(),v.getNotes(),v.getInboundSCAC(),v.getInboundWayBill(),v.getInboundWayBillSN(),
				v.getInboundBOL(),v.getDepartureDate(),v.getOutboundSCAC(),v.getOutboundWayBill(),v.getOutboundWayBillSN(),v.getOutboundBOL(),v.getLineSeries(),v.getWheelbase(),
				v.getArrivalDate(),v.getChngWho(),v.getChngDate()};
		
		result = database.executeStoredProcedureUpdate("spInsertVIN", params);

		return result;
	}
	
	public int SaveHistory(Vehicle v) {
		//Save the vehicle information to history
		int result = 0;
		Object[] params = new Object[] {v.getVIN(),v.getLotCode(),Common.PadString(v.getZone(), Constants.ZONE_LEN), Common.PadString(v.getRow(), Constants.AREA_LEN),
				v.getSpot(),v.getRoute(),v.getDealer(),v.getInboundUpfitter(),v.getOutboundUpfitter(), v.getRoutePrefix(),v.getManufacturer(),v.getInboundTrainID(),
				v.getInboundEventTime(),v.getInboundRailcarNumber(),v.getInboundDeckLevel(),v.getInboundPos(),v.getOutboundTrainID(),v.getOutboundEventTime(),
				v.getOutboundRailcarNumber(),v.getOutboundDeckLevel(),v.getOutboundPos(),v.getInboundDriver(), v.getOutboundDriver(), v.getEmissionCode(),
				v.getVehicleType(),v.getColorCode(),v.getStatusCode(),(v.getInboundTruckID() == null) ? " " : v.getInboundTruckID(),v.getInboundTruckCo(),v.getOutboundTruckID(),v.getOutboundTruckCo(),
				v.getActionCode(),v.getHoldReasonCode(),v.getProdStatus(),v.getSoldCode(),v.getDiversion(), (v.getLTD() != null && v.getLTD() ? "Y" : "N") ,
				(v.getProcessedAsLTD() != null && v.getProcessedAsLTD() ? "Y" : "N"),v.getLTDRoute(),v.getOrigin(),v.getNotes(),v.getInboundSCAC(),v.getInboundWayBill(),v.getInboundWayBillSN(),
				v.getInboundBOL(),v.getDepartureDate(),v.getOutboundSCAC(),v.getOutboundWayBill(),v.getOutboundWayBillSN(),v.getOutboundBOL(),v.getLineSeries(),v.getWheelbase(),
				v.getArrivalDate(),v.getChngWho(),v.getChngDate()};
		
		result = database.executeStoredProcedureUpdate("spInsertVINHistory", params);

		return result;
	}

	public int addHold(Vehicle vehicle, Hold hold) {
		int result = 0;
		
		Object[] params = new Object[] {vehicle.getVIN(), hold.getManufacturer(), hold.getHoldCode(),hold.getChangeDate()};
		result = database.executeStoredProcedureUpdate("spInsertVINHold", params);
		
		return result;
	}
	
	public int addHolds(Vehicle vehicle, List<Hold> holds) {
		int result = 0;
		String VIN = vehicle.getVIN();
		
		for(Hold hold : holds) {
			Object[] params = new Object[] {VIN, hold.getManufacturer(), hold.getHoldCode(),hold.getChangeDate()};
			result = result + database.executeStoredProcedureUpdate("spInsertVINHold", params);
		}
		
		return result;
	}
	
	@Override
	public int delete(Vehicle v) {
		// Delete the vehicle
		int result = 0;
		
		Object[] params = new Object[] {v.getVIN(),v.getLotCode(),Common.PadString(v.getZone(), Constants.ZONE_LEN), Common.PadString(v.getRow(), Constants.AREA_LEN),
				v.getSpot(),v.getRoute(),v.getDealer(),v.getInboundUpfitter(),v.getOutboundUpfitter(), v.getRoutePrefix(),v.getManufacturer(),v.getInboundTrainID(),
				v.getInboundEventTime(),v.getInboundRailcarNumber(),v.getInboundDeckLevel(),v.getInboundPos(),v.getOutboundTrainID(),v.getOutboundEventTime(),
				v.getOutboundRailcarNumber(),v.getOutboundDeckLevel(),v.getOutboundPos(),v.getInboundDriver(), v.getOutboundDriver(), v.getEmissionCode(),
				v.getVehicleType(),v.getColorCode(),v.getStatusCode(),v.getInboundTruckID(),v.getInboundTruckCo(),v.getOutboundTruckID(),v.getOutboundTruckCo(),
				v.getActionCode(),v.getHoldReasonCode(),v.getProdStatus(),v.getSoldCode(),v.getDiversion(), (v.getLTD() ? "Y" : "N") ,
				(v.getProcessedAsLTD() ? "Y" : "N"),v.getLTDRoute(),v.getOrigin(),v.getNotes(),v.getInboundSCAC(),v.getInboundWayBill(),v.getInboundWayBillSN(),
				v.getInboundBOL(),v.getDepartureDate(),v.getOutboundSCAC(),v.getOutboundWayBill(),v.getOutboundWayBillSN(),v.getOutboundBOL(),v.getLineSeries(),v.getWheelbase(),
				v.getArrivalDate(),v.getChngWho(),v.getChngDate()};
		
		result = database.executeStoredProcedureUpdate("spDeleteVIN", params);
		
		return result;
	}
	
	public int deleteHolds(Vehicle obj) {
		int result = 0;
		
		result = database.executeStoredProcedureUpdate("spDeleteAllVINHolds", new Object[] {obj.getVIN()});
		
		return result;
	}
	
	public boolean GetVINOrigin(String vin) {
		boolean result = false;
		Object[] params = new Object[] {vin,vin};
		
		Vehicle vehicle = database.executeQuery(Vehicle.class, fieldsMap, getSQL("GetVINOrigin"), params);
		
		if(vehicle != null) result = true;
		
		return result;
	}
		
	public boolean GetReturnToPlantPerformed(String vin) {
		boolean result = false;
		Object[] params = new Object[] {vin};
		
		Vehicle vehicle = database.executeStoredProcedure(Vehicle.class, fieldsMap, getSQL("spGetReturnToPlant"), params);		
		if(vehicle != null) result = true;
		
		return result;
	}
	
	public boolean VINCheck(String vin) {
		String response = null;
		boolean result = false;
		
		//Check if Manufacturer has VIN Checking enabled
		response = database.executeStoredProcedureScalar("spGetVINCheck", new Object[] {vin});
		
		if(response != null && response.equals("Y")) {
			result = PerformVINCheck(vin);
		} else if( response == null) {
			result = PerformVINCheck(vin);
		} else {
			//Check for the very basics
			if(vin != null && vin.length() <= Constants.VIN_LENGTH && Common.isAlphaNumeric(vin)) { 
						result = true;
			}			
		}
		return result;
	}
	
	public boolean RemoveNoRecord(Vehicle vehicle) {
		boolean result = false;
		Object[] params = new Object[] {vehicle.getVIN(),vehicle.getLotCode(),vehicle.getZone(),vehicle.getRow(),
				vehicle.getSpot(),vehicle.getRoute(),vehicle.getDealer(),vehicle.getInboundUpfitter(),vehicle.getOutboundUpfitter(),
				vehicle.getRoutePrefix(),vehicle.getManufacturer(),vehicle.getInboundTrainID(),vehicle.getInboundEventTime(),
				vehicle.getInboundRailcarNumber(),vehicle.getInboundDeckLevel(),vehicle.getInboundPos(),vehicle.getOutboundTrainID(),
				vehicle.getOutboundEventTime(),vehicle.getOutboundRailcarNumber(),vehicle.getOutboundDeckLevel(),vehicle.getOutboundPos(),
				vehicle.getInboundDriver(),vehicle.getOutboundDriver(),vehicle.getEmissionCode(),vehicle.getVehicleType(),vehicle.getColorCode(),vehicle.getStatusCode(),
				vehicle.getInboundTruckID(),vehicle.getInboundTruckCo(),vehicle.getOutboundTruckID(),vehicle.getOutboundTruckCo(),
				vehicle.getActionCode(),vehicle.getHoldReasonCode(),vehicle.getProdStatus(),vehicle.getSoldCode(),vehicle.getDiversion(),
				vehicle.getLTD() ? "Y" : "N", vehicle.getProcessedAsLTD() ? "Y" : "N", vehicle.getLTDRoute(),
				vehicle.getOrigin(),vehicle.getNotes(),vehicle.getInboundSCAC(),vehicle.getInboundWayBill(),vehicle.getInboundWayBillSN(),
				vehicle.getInboundBOL(),vehicle.getDepartureDate(),vehicle.getOutboundSCAC(),vehicle.getOutboundWayBill(),vehicle.getOutboundWayBillSN(),
				vehicle.getOutboundBOL(),vehicle.getLineSeries(),vehicle.getWheelbase(),
				vehicle.getArrivalDate(),vehicle.getChngWho(),vehicle.getChngDate()				
				};
		
		database.executeStoredProcedureUpdate("spRemoveVINNoRecord", params);

		result = true;
		
		return result;
	}
	
	public void RemoveFromLot(Vehicle vehicle) {
		Object[] params = new Object[] {vehicle.getVIN(),vehicle.getLotCode(),vehicle.getZone(),vehicle.getRow(),
				vehicle.getSpot(),vehicle.getRoute(),vehicle.getDealer(),vehicle.getInboundUpfitter(),vehicle.getOutboundUpfitter(),
				vehicle.getRoutePrefix(),vehicle.getManufacturer(),vehicle.getInboundTrainID(),vehicle.getInboundEventTime(),
				vehicle.getInboundRailcarNumber(),vehicle.getInboundDeckLevel(),vehicle.getInboundPos(),vehicle.getOutboundTrainID(),
				vehicle.getOutboundEventTime(),vehicle.getOutboundRailcarNumber(),vehicle.getOutboundDeckLevel(),vehicle.getOutboundPos(),
				vehicle.getInboundDriver(),vehicle.getOutboundDriver(),vehicle.getEmissionCode(),vehicle.getVehicleType(),vehicle.getColorCode(),vehicle.getStatusCode(),
				vehicle.getInboundTruckID(),vehicle.getInboundTruckCo(),vehicle.getOutboundTruckID(),vehicle.getOutboundTruckCo(),
				vehicle.getActionCode(),vehicle.getHoldReasonCode(),vehicle.getProdStatus(),vehicle.getSoldCode(),vehicle.getDiversion(),
				vehicle.getLTD() ? "Y" : "N", vehicle.getProcessedAsLTD() ? "Y" : "N", vehicle.getLTDRoute(),
				vehicle.getOrigin(),vehicle.getNotes(),vehicle.getInboundSCAC(),vehicle.getInboundWayBill(),vehicle.getInboundWayBillSN(),
				vehicle.getInboundBOL(),vehicle.getDepartureDate(),vehicle.getOutboundSCAC(),vehicle.getOutboundWayBill(),vehicle.getOutboundWayBillSN(),
				vehicle.getOutboundBOL(),vehicle.getLineSeries(),vehicle.getWheelbase(),
				vehicle.getArrivalDate(),vehicle.getChngWho(),vehicle.getChngDate()				
				};
		
		database.executeStoredProcedureUpdate("spRemoveVIN", params);
	}
	
	public boolean TransmitToTrucker(String Lot, String Zone, String Row) {
		String response = null;
		String procname = null;
		boolean result = false;
		Object[] params = new Object[] { Zone, Row };
		
		switch(Lot) {
			case Constants.TRUCK_LOT:
				procname = "spGetConvoyTransmitToTrucker";
				break;
			case Constants.LOADLINE_LOT:
				procname = "spGetLLineTransmitToTrucker";
				break;
			case Constants.STORE_LOT:
				procname = "spGetStorageTransmitToTrucker";
				break;
			default:
				break;
		}
		
		response = database.executeStoredProcedureScalar(procname, params);
		if(response != null) {
			result = true;
		}
		
		return result;
	}
	
	public String getManufacturerFromVIN(String vin) {
		Object[] params = new Object[] {vin.subSequence(0, 3)};
		String sql = "SELECT tblManfCodes_Manufacturer FROM tblManfCodes WHERE tblManfCodes_VINCode = ?";
		
		return database.executeQueryScalar(sql,params);
	}

	private boolean PerformVINCheck(String vin) {
	        int[] values = { 1, 2, 3, 4, 5, 6, 7, 8, 0, 1,
	                         2, 3, 4, 5, 0, 7, 0, 9, 2, 3,
	                         4, 5, 6, 7, 8, 9 };
	        int[] weights = { 8, 7, 6, 5, 4, 3, 2, 10, 0, 9,
	                          8, 7, 6, 5, 4, 3, 2 };

	        String s = vin;
	        s = s.replaceAll("-", "");
	        s = s.toUpperCase();
	        if (s.length() != 17) return false;
	 
	        int sum = 0;
	        for (int i = 0; i < 17; i++) {
	            char c = s.charAt(i);
	            int value;
	            int weight = weights[i];

	            if (c >= 'A' && c <= 'Z') {
		            // letter
	                value = values[c - 'A'];
	                if (value == 0) return false;
	            } else if (c >= '0' && c <= '9') {
	            	// number
	            	value = c - '0';
	            	
	            } else {
	            	// illegal character
	            	return false;
	            }

	            sum = sum + weight * value;
	        }

	        // check digit
	        sum = sum % 11;
	        char check = s.charAt(8);
	        
	        if(check != 'X' && (check < '0' || check > '9')) return false;
	        
	        if(sum == 10 && check == 'X') {
	        	return true;
	        } else if (sum == check - '0') {
	        	return true;
	        } else {
	        	return false;
	        }
	}
	
	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				put("tblVIN_VIN", "VIN");
				put("tblVIN_Manufacturer", "Manufacturer");
				put("tblVIN_VehicleType", "VehicleType");
				put("tblVIN_StatusCode", "StatusCode");
				put("tblVIN_LotCode", "LotCode");
				put("tblVIN_ZoneID", "Zone");
				put("tblVIN_Area", "Row");
				put("tblVIN_Spot", "Spot");
				put("tblVIN_ActionCode", "ActionCode");
				put("tblVIN_InboundTrainID", "InboundTrainID");
				
				put("tblVIN_InboundEventTime", "InboundEventTime");
				put("tblVIN_InboundRailcarNumber", "InboundRailcarNumber");
				put("tblVIN_InboundDeckLevel", "InboundDeckLevel");
				put("tblVIN_InboundPos", "InboundPos");
				put("tblVIN_OutboundTrainID", "OutboundTrainID");
				put("tblVIN_OutboundEventTime", "OutboundEventTime");
				put("tblVIN_OutboundRailcarNumber", "OutboundRailcarNumber");
				put("tblVIN_OutboundDeckLevel", "OutboundDeckLevel");
				put("tblVIN_OutboundPos", "OutboundPos");
				put("tblVIN_InboundDriver", "InboundDriver");
				
				put("tblVIN_OutboundDriver", "OutboundDriver");
				put("tblVIN_Route", "Route");
				put("tblVIN_Dealer", "Dealer");
				put("tblVIN_InboundUpfitter", "InboundUpfitter");
				put("tblVIN_OutboundUpfitter", "OutboundUpfitter");
				put("tblVIN_RoutePrefix", "RoutePrefix");
				put("tblVIN_EmissionCode", "EmissionCode");
				put("tblVIN_ColorCode", "ColorCode");
				put("tblVIN_ProdStatus", "ProdStatus");
				put("tblVIN_SoldCode", "SoldCode");
				
				put("tblVIN_InboundTruckID", "InboundTruckID");
				put("tblVIN_InboundTruckCompany", "InboundTruckCo");
				put("tblVIN_OutboundTruckID", "OutboundTruckID");
				put("tblVIN_OutboundTruckCompany", "OutboundTruckCo");
				put("tblVIN_ChngWho", "ChngWho");
				put("tblVIN_ChngDate", "ChngDate");
				put("tblVIN_HoldReasonCode", "HoldReasonCode");
				put("tblVIN_Diversion", "Diversion");
				put("tblVIN_LTD", "LTD");
				put("tblVIN_ProcessedAsLTD", "ProcessedAsLTD");
				
				put("tblVIN_LTDRoute", "LTDRoute");
				put("tblVIN_InboundSCAC", "InboundSCAC");
				put("tblVIN_InboundWayBill", "InboundWayBill");
				put("tblVIN_InboundWayBillSN", "InboundWayBillSN");
				put("tblVIN_InboundBOL", "InboundBOL");
				put("tblVIN_DepartureDate", "DepartureDate");
				put("tblVIN_OutboundSCAC", "OutboundSCAC");
				put("tblVIN_OutboundWayBill", "OutboundWayBill");
				put("tblVIN_OutboundWayBillSN", "OutboundWayBillSN");
				put("tblVIN_OutboundBOL", "OutboundBOL");
				
				put("tblVIN_ArrivalDate", "ArrivalDate");
				put("tblVIN_Origin", "Origin");
				put("tblVIN_Notes", "Notes");
				put("tblVIN_LineSeries", "LineSeries");
				put("tblVIN_Wheelbase", "Wheelbase");
			}
		};
		
		return columnsToFieldsMap;
	}
	
	@Override
	public String getSQL(String queryName) {
		String sql = null;
		
		switch(queryName) {
		case "GetVINOrigin":
			sql = "SELECT tblVIN.* " + 
					"FROM tblvin, tblRampPlants " + 
					"WHERE tblvin_vin = ? " + 
					"and ? LIKE tblRampPlants_VINMask";
			break;
		default:
			sql = queryName;
			break;
		}
		
		return sql;
	}
	
	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eVINObjAction eFind;
		
		eFind = (eVINObjAction)Find;
		
		switch(eFind) {
		case eVINFindVIN :
			sql = "SELECT * FROM tblVIN " + 
			"WHERE tblVIN.tblVIN_VIN = ? " + 
			"ORDER BY tblVIN_VIN, tblVIN_ChngDate";
			break;
		case eVINFindVINLike:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE tblVIN.tblVIN_VIN LIKE ? " + 
					"ORDER BY tblVIN_VIN, tblVIN_ChngDate";
			break;
		case eVINFindDealer:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE tblVIN.tblVIN_Dealer = ? " + 
					"ORDER BY tblVIN_VIN, tblVIN_ChngDate";
			break;
		case eVINFindRoute:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE tblVIN.tblVIN_Route = ? " + 
					"ORDER BY tblVIN_VIN, tblVIN_ChngDate";
			break;
		case eVINFindLot:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE tblVIN.tblVIN_LotCode = ? " + 
					"AND tblVIN.tblVIN_ZoneID = ? " + 
					"ORDER BY tblVIN_Area, tblVIN_Spot";
			break;
		case eVINFindZone:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE tblVIN.tblVIN_LotCode = ? " + 
					"AND tblVIN.tblVIN_ZoneID = ? " + 
					"ORDER BY tblVIN_Area, tblVIN_Spot";
			break;
		case eVINFindArea:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE tblVIN.tblVIN_LotCode = ? " + 
					"AND tblVIN.tblVIN_ZoneID = ? " + 
					"AND tblVIN.tblVIN_Area = ? " + 
					"ORDER BY tblVIN_Spot";
			break;
		case eVINFindSpot:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE tblVIN.tblVIN_LotCode = ? " + 
					"AND tblVIN.tblVIN_ZoneID = ? " + 
					"AND tblVIN.tblVIN_Area = ? " + 
					"AND tblVIN.tblVIN_Spot = ? " +
					"ORDER BY tblVIN_Spot";
			break;
		case eVINFindRailCar:
		case eVINFindInboundRailCar:
		case eVINFindOutboundRailCar:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE ( tblVIN.tblVIN_InboundRailcarNumber = ? " + 
					"AND (tblVIN.tblVIN_StatusCode IN ('C','J','P','S'))) " + 
					"OR ( tblVIN.tblVIN_OutboundRailcarNumber = ? " + 
					"AND (tblVIN.tblVIN_StatusCode IN ('A','L'))) " + 
					"ORDER BY tblVIN_VIN, tblVIN_ChngDate";
			break;
		case eVINFindRailCarASN:
			sql = "SELECT *  " + 
					"FROM tblVIN " + 
					"WHERE tblVIN_InboundRailcarNumber = ? " + 
					"AND (tblVIN_StatusCode IS NULL " + 
					"OR tblVIN_StatusCode = 'S')";
			break;
		case eVINFindRailCarLike:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE ( tblVIN.tblVIN_InboundRailcarNumber LIKE ? " + 
					"AND (tblVIN.tblVIN_StatusCode IN ('C','J','P','S'))) " + 
					"OR ( tblVIN.tblVIN_OutboundRailcarNumber LIKE ? " + 
					"AND (tblVIN.tblVIN_StatusCode IN ('A','L'))) " + 
					"ORDER BY tblVIN_VIN, tblVIN_ChngDate";
			break;
		case eVINFindRailcarLTDs:
			sql = "SELECT * FROM tblVIN " + 
					"WHERE ( tblVIN.tblVIN_InboundRailcarNumber = ? " + 
					"OR tblVIN.tblVIN_OutboundRailcarNumber = ?) " + 
					"AND (tblVIN.tblVIN_StatusCode IN ('C','J','S')) " + 
					"ORDER BY tblVIN_VIN, tblVIN_ChngDate";
			break;
		default:
			break;
		}
					
		return sql;
	}
}
