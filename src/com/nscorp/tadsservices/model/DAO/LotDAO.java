package com.nscorp.tadsservices.model.DAO;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Lot;
import com.nscorp.tadsservices.model.Enums.eLotCode;

public class LotDAO extends TADSDAO<Lot> {
	
	public LotDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap(); 
	}
	
	@Override
	public Lot get(String id) {
		return null;
	}

	@Override
	public int set(Lot obj) {
		int result = 0;
		
		return result;
	}

	@Override
	public int delete(Lot obj) {
		int result = 0;
		
		return result;
	}
	
	/**
	 * Will take the location information and determine if the location is valid and currently set to active
	 * @param eLot - required
	 * @param Zone - optional - validate zone
	 * @param Row - optional - validate zone, row
	 * @param Spot - optional - validate zone, row, spot
	 * @return boolean value for the lot being valid and active
	 */
	public boolean IsLocation(eLotCode eLot, String Zone, String Row, Integer Spot) {
		boolean result = false;
		String sLot = null;
		Lot lot = null;
		Object[] params = null;

		//Get the character code for the lot
		sLot = Common.LotEnumToCode(eLot);
		
		//Determine the level of validation
		if(Zone != null) {
			if(Row != null) {
				//validate the spot
				if(Spot != null && Spot > 0) {
					params = new Object[] {Zone,Row,Spot};
					
					switch(sLot) {
					case Constants.SUPPORT_TRACK_LOT:
						lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_SPOT_IN_SUPPORT_TRACK_LOT"),params);
						break;
					case Constants.TRACK_LOT:
						lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_SPOT_IN_TRACK_LOT"),params);
						break;
					case Constants.TRUCK_LOT:
						lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_SPOT_IN_CONVOY_LOT"),params);
						break;
					case Constants.LOADLINE_LOT:
						lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_SPOT_IN_LL_LOT"),params);
						break;
					case Constants.STORE_LOT:
						lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_SPOT_IN_STORE_LOT"),params);
						break;
					default:
						result = false;
						break;				
					}					
				} else {
					//Validate the row
					params = new Object[] {Zone,Row};

				    switch(sLot) {
				    case Constants.SUPPORT_TRACK_LOT:
				    	lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_AREA_IN_SUPPORT_TRACK_LOT"),params);
				    	break;
				    case Constants.TRACK_LOT:
				    	lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_AREA_IN_TRACK_LOT"),params);
				    	break;
				    case Constants.TRUCK_LOT:
				    	lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_AREA_IN_CONVOY_LOT"),params);
				    	break;
				    case Constants.LOADLINE_LOT:
				    	lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_AREA_IN_LL_LOT"),params);
				    	break;
				    case Constants.STORE_LOT:
				    	lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_AREA_IN_STORE_LOT"),params);
				    	break;
				    default:
				    	result = false;
				    	break;
				    }
				}
			} else {
				//Validate the zone
				params = new Object[] {Zone};
				
			    switch(sLot) {
			    case Constants.SUPPORT_TRACK_LOT:
            		lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_ZONE_IN_SUPPORT_TRACK_LOT"),params);
            		break;
			    case Constants.TRACK_LOT:
            		lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_ZONE_IN_TRACK_LOT"),params);
            		break;
			    case Constants.TRUCK_LOT:
            		lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_ZONE_IN_CONVOY_LOT"),params);
            		break;
			    case Constants.LOADLINE_LOT:
            		lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_ZONE_IN_LL_LOT"),params);
            		break;
			    case Constants.STORE_LOT:
            		lot = database.executeQuery(Lot.class, fieldsMap,getSQL("GET_ZONE_IN_STORE_LOT"),params);
            		break;
		        default:
		        	result = false;
		        	break;
			    }
			}
		} else {
			//The lot is always valid
			result = true;
		}
		

		//If there is an Active indicator then the spot exists
		if(lot.getActiveInd() != null && lot.getActiveInd().length() > 0) result = true;
		
		return result;
	}
		
	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				//Format is put("database column name", "class field name");
				put("ActiveInd", "ActiveInd");
			}
		};
		
		return columnsToFieldsMap;
	}
	
	@Override
	public String getSQL(String query){
		String sql = null;
		
		switch(query) {		
		case "GET_SPOT_IN_SUPPORT_TRACK_LOT":
			sql = "SELECT tblSupportTrackSpot_ActiveInd 'ActiveInd' " + 
					"				FROM tblSupportTrackSpot " + 
					"				WHERE tblSupportTrackSpot_ZoneID = ? " + 
					"				AND tblSupportTrackSpot_TrackNbr = ? " + 
					"				AND tblSupportTrackSpot_Spot = ?";
			break;
		case "GET_SPOT_IN_TRACK_LOT":
			sql = "SELECT tblTrkSpot_ActiveInd 'ActiveInd' " + 
					"			  FROM tblTrkSpot " + 
					"			  WHERE tblTrkSpot_ZoneID = ? " + 
					"			  AND tblTrkSpot_TrackNbr = ? " + 
					"			  AND tblTrkSpot_Spot = ?";
			break;
		case "GET_SPOT_IN_CONVOY_LOT":
			sql = "SELECT tblBaySpot_ActiveInd 'ActiveInd' " + 
					"			  FROM tblBaySpot " + 
					"			  WHERE tblBaySpot_ZoneID = ? " + 
					"			  AND tblBaySpot_RowID = ? " + 
					"			  AND tblBaySpot_Spot = ?";
			break;
		case "GET_SPOT_IN_LL_LOT":
			sql = "SELECT tblLLSpot_ActiveInd 'ActiveInd' " + 
					"			  FROM tblLLSpot " + 
					"			  WHERE tblLLSpot_ZoneID = ? " + 
					"			  AND tblLLSpot_Line = ? " + 
					"			  AND tblLLSpot_Spot = ?";
			break;
		case "GET_SPOT_IN_STORE_LOT":
			sql = "SELECT tblStoreSpot_ActiveInd 'ActiveInd' " + 
					"			  FROM tblStoreSpot " + 
					"			  WHERE tblStoreSpot_ZoneID = ? " + 
					"			  AND tblStoreSpot_AreaID = ? " + 
					"			  AND tblStoreSpot_Spot = ?";
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


}
