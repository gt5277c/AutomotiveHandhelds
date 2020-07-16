package com.nscorp.tadsservices.model;

public class Constants {

	//Lot constants (defined in tblLot)
	public static final String TRUCK_LOT = "C";
	public static final String LOADLINE_LOT = "L";
	public static final String STORE_LOT = "S";
	public static final String TRACK_LOT = "T";
	public static final String SUPPORT_TRACK_LOT = "U";
	public static final String ADVANCE_CONSIST = "A";
	
	//Miscellaneous lengths
	public static final Integer LOT_LEN = 1;
	public static final Integer ZONE_LEN = 3;
	public static final Integer AREA_LEN = 4;
	public static final Integer SPOT_LEN = 4;
	
	//VIN Actions (defined in tblActionCodes)
	public static final String ACTION_ADD_SHIPMENT = "A";
	public static final String ACTION_RAIL_IN = "B";
	public static final String ACTION_CANCEL_RAILSHIP_APPROVE = "C";
	public static final String ACTION_DRIVE_IN = "D";
	public static final String ACTION_DELETE = "E";
	public static final String ACTION_HOLD_VIN_MOVE = "F";
	public static final String ACTION_ASSIGN_TO_LOCATION = "G";
	public static final String ACTION_HOTLOAD = "H";
	public static final String ACTION_INVENTORY = "I";
	public static final String ACTION_RETURN_TO_PLANT = "J";
	public static final String ACTION_TRUCK_OUT = "K";
	public static final String ACTION_LOAD = "L";
	public static final String ACTION_VIN_MOVED = "M";
	public static final String ACTION_CANCEL_ASSIGNMENT = "N";
	public static final String ACTION_REMOVE = "O";
	public static final String ACTION_PLANT_IN = "P";
	public static final String ACTION_REMOVE_HOLD = "Q";
	public static final String ACTION_VIN_REASSIGNED = "R";
	public static final String ACTION_RAILSHIP_APPROVE = "S";
	public static final String ACTION_TRUCK_IN = "T";
	public static final String ACTION_DROPZONE = "DZ";
	public static final String ACTION_UNLOAD_VIN = "U";
	public static final String ACTION_SAVE_VIN = "V";
	public static final String ACTION_WASH = "W";
	public static final String ACTION_RAILCAR_UPDATED = "X";
	public static final String ACTION_PHYSICAL_SCAN = "Y";
	public static final String ACTION_HOLD_VIN_IN_PLACE = "Z";
	public static final String ACTION_HOLD_CRITERIA = "1";
	public static final String ACTION_XML_INVDISP = "2";
	public static final String ACTION_XML_INVENTORY = "3";
	public static final String ACTION_PRELOAD_APPROVED = "4";
	public static final String ACTION_PRELOAD_REJECTED = "5";
	public static final String ACTION_PRELOAD_SUBMITTED = "6";
	public static final String ACTION_PRELOAD_CANCELLED = "7";
	public static final String ACTION_PRELOAD_CANCEL_PENDING = "CP";
	public static final String ACTION_PRELOAD_OVERRIDE = "8";
	public static final String ACTION_ASN_EDITOR_ADD = "BA";
	public static final String ACTION_BAY_SITE_SPECIFIC = "BS";
	public static final String ACTION_FORCE_TO_LOADLINE = "FL";
	public static final String ACTION_ADD_LL_DIRECT = "LD";
	public static final String ACTION_MOVE_TO_LOST = "LF";
	public static final String ACTION_ADDED_CHANGED_NOTES = "LN";
	public static final String ACTION_MOVE_FROM_LOST = "LR";
	public static final String ACTION_MODIFICATION = "MO";
	public static final String ACTION_PREBAY_CONVOY = "PC";
	public static final String ACTION_PHYSICAL_INVENTORY_FIX = "PF";
	public static final String ACTION_PHYSICAL_INVENTORY_UNFIX = "PU";
	public static final String ACTION_CHANGE_RAILCAR_NUMBER = "RC";
	public static final String ACTION_RAILSHIP_APPROVE_AUDIT_SAVE = "RS";
	public static final String ACTION_RAILSHIP_APPROVE_AUDIT_DELETE = "RD";
	public static final String ACTION_PREBAY_SITE_SPECIFIC = "SP";
	public static final String ACTION_SAVE_VEHICLE_AUDIT = "VS";
	public static final String ACTION_DELETE_VEHICLE_AUDIT = "VD";
	
	//status codes (defined in tblStatusCode)
	public static final String STAT_RAILSHIP_APPROVED = "A";
	public static final String STAT_PARKED = "B";
	public static final String STAT_CANCEL_ASSIGNED  = "C";
	public static final String STAT_BO_IN_PLACE = "D";
	public static final String STAT_DRIVE_IN = "E";
	public static final String STAT_DRIVE_OUT = "F";
	public static final String STAT_HOLD = "H";
	public static final String STAT_ARRIVED_RAIL = "J";
	public static final String STAT_LOADED = "L";
	public static final String STAT_PLANT_IN = "N";
	public static final String STAT_BO_SICK_BAY = "O";
	public static final String STAT_ASSIGNED = "P";
	public static final String STAT_DELETED = "Q";
	public static final String STAT_ARRIVED_SUPPORT_YARD = "S";
	public static final String STAT_TRUCK_OUT = "T";
	public static final String STAT_TRUCK_IN = "V";
	public static final String STAT_RETURNED_PLANT = "X";
	public static final String STAT_DELIVERED_OFF = "I";
	public static final String STAT_DRIVER_SCAN = "K";
	public static final String STAT_DROP_ZONE = "Z";
	
	//VIN constants
	public static final  Integer VIN_LENGTH = 17;
	public static final  Integer VIN_CHECK_DIGIT_POS = 9;
	public static final  Integer VIN_MODEL_YEAR_POS = 10;
	public static final String VIN_PADDING = "*****************";
	
	//Railcar Actions
	public static final String RC_ACTION_REFRESH_ASN= "AA";
	public static final String RC_ACTION_SEND_TRAIN_LIST= "AB";
	public static final String RC_ACTION_CANCEL_RAILSHIP_APPROVE= "AC";
	public static final String RC_ACTION_DELETE_FROM_TRACK= "AD";
	public static final String RC_ACTION_CANCEL_LTD= "AE";
	public static final String RC_ACTION_CLEARED_TRACK_SPOT= "AF";
	public static final String RC_ACTION_UNLOAD= "AG";
	public static final String RC_ACTION_CHANGE_INFO= "AH";
	public static final String RC_ACTION_UPDATE_RAILCAR_INFO= "AI";
	public static final String RC_ACTION_GENERATE_PACT= "AJ";
	public static final String RC_ACTION_GENERATE_PFPS= "AK";
	public static final String RC_ACTION_GENERATE_LOAD= "AL";
	public static final String RC_ACTION_MOVE= "AM";
	public static final String RC_ACTION_CHANGE_INFORMATION= "AN";
	public static final String RC_ACTION_ASSIGNED_LOCATION= "AO";
	public static final String RC_ACTION_PLACEMENT= "AP";
	public static final String RC_ACTION_CANCEL_ASSIGNMENT= "AQ";
	public static final String RC_ACTION_RAILSHIP_APPROVE= "AR";
	public static final String RC_ACTION_SAVE_LOAD_RECORD_ONLY= "AS";
	public static final String RC_ACTION_LTD= "AT";
	public static final String RC_ACTION_UPDATE_RAILCAR_INFO_AND_VINS= "AU";
	public static final String RC_ACTION_DELIVER= "AV";
	public static final String RC_ACTION_CLEARED_WAYBILL= "AW";
	public static final String RC_ACTION_VINS_PARKED= "AX";
	public static final String RC_ACTION_APPLY_WAYBILL= "AY";
	public static final String RC_ACTION_SAVE_RELEASE_EMPTY= "AZ";
	public static final String RC_ACTION_SPOTTED_RAILCAR= "BA";
	public static final String RC_ACTION_ADD_NOTES= "BB";
	public static final String RC_ACTION_XML_RCDisp= "BC";
	public static final String RC_ACTION_XML_WBNote= "BD";
	public static final String RC_ACTION_XML_TRIGGER_LTD= "BE";
	public static final String RC_ACTION_APPLY_INBOUND_WEIGHT= "BF";
	public static final String RC_ACTION_APPLY_OUTBOUND_WEIGHT= "BG";
	public static final String RC_ACTION_APPLY_INBOUND_EMBARGO= "BH";
	public static final String RC_ACTION_APPLY_OUTBOUND_EMBARGO= "BI";
	public static final String RC_ACTION_APPLY_SEAL_UPDATE= "BJ";
	
	//Railcar Events
	public static final String eXMLRailcarEventCode_PACT = "PACT";
	public static final String eXMLRailcarEventCode_RMTY = "RMTY";
	public static final String eXMLRailcarEventCode_MOVE = "MOVE";
	public static final String eXMLRailcarEventCode_LOAD = "LOAD";
	public static final String eXMLRailcarEventCode_RLOD = "RLOD";
	public static final String eXMLRailcarEventCode_PFPS = "PFPS";
	public static final String eXMLRailcarEventCode_SNAP = "SNAP";
	public static final String eXMLRailcarEventCode_BOL = "BOL";
	public static final String eXMLRailcarEventCode_PARK = "PARK";
	public static final String eXMLRailcarEventCode_DELT = "DELT";
	public static final String eXMLRailcarEventCode_SEAL = "SEAL";
	public static final String eXMLRailcarEventCode_PBAY = "PBAY";

	//Railcar Status Constants
	//(defined in tblRailcarStatus)
	public static final String STAT_RAIL_EMPTY= "E";
	public static final String STAT_RAIL_RAILSHIPPED = "R";
	public static final String STAT_RAIL_PARKED = "P";
	public static final String STAT_RAIL_ARRIVEDRAIL = "J";
	public static final String STAT_RAIL_INSUPPORTYARD = "I";
	public static final String STAT_RAIL_LOADING = "L";
	public static final String STAT_RAIL_ASSIGNED = "A";
	public static final String STAT_RAIL_BADORDER = "B";
	public static final String STAT_RAIL_ERROR = "X";
	public static final String STAT_RAIL_PACT = "F";
	public static final String STAT_RAIL_RMTY = "G";
	public static final String STAT_RAIL_RLOD = "H";
	public static final String STAT_RAIL_PFPS = "K";
	public static final String STAT_RAIL_LOAD = "M";
	public static final String STAT_RAIL_DELETED = "Q";
	public static final String STAT_RAIL_SEAL = "S";
	public static final String STAT_RAIL_PBAY = "Y";
	
	//Load/Empty Status constants
	public static final String LE_STATUS_LOADED = "L";
	public static final String LE_STATUS_EMPTY = "E";
	
	//railcar constants
	public static final int RAILCAR_LENGTH = 10;
	
	//SCAC
	public static final String SCAC_DRIVE = "DRIV";
	public static final String SCAC_RETURN_FROM_PLANT = "PLRT";

	//hold reason codes
	public static final String STAT_HOLD_DIVERTED = "D";
	
	//Pre-Load Status
	public static final String PRELOAD_STATUS_APPROVED = "A";
	public static final String PRELOAD_STATUS_REJECTED = "R";
	public static final String PRELOAD_STATUS_SUBMITTED = "S";
	public static final String PRELOAD_STATUS_LOADED = "L";
	public static final String PRELOAD_STATUS_CANCELLED = "C";
	public static final String PRELOAD_STATUS_PENDING = "P";

	//Pre-Load Actions
	public static final String PRELOAD_ACTION_ADDED = "A";
	public static final String PRELOAD_ACTION_CANCELLED = "C";
	public static final String PRELOAD_ACTION_DELETED = "D";
	public static final String PRELOAD_ACTION_EXTENDED = "E";
	public static final String PRELOAD_ACTION_LOAD = "L";
	public static final String PRELOAD_ACTION_NEW = "N";
	public static final String PRELOAD_ACTION_MFG_RESPONSE = "M";
	public static final String PRELOAD_ACTION_OVERRIDE = "O";
	public static final String PRELOAD_ACTION_NON_PLANT_APPROVE = "P";
	public static final String PRELOAD_ACTION_RESUBMITTED = "R";
	public static final String PRELOAD_ACTION_SUBMITTED = "S";
	public static final String PRELOAD_ACTION_AUTONEW = "T";
	public static final String PRELOAD_ACTION_UNLOAD = "U";
	public static final String PRELOAD_ACTION_PENDING = "CP";
	

	//Embargo Actions
	public static final String EMBARGO_ACTION_ADD = "A";
	public static final String EMBARGO_ACTION_DELETE = "D";
}
