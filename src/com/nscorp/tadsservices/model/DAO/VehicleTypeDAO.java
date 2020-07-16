package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.VehicleType;

public class VehicleTypeDAO extends TADSDAO<VehicleType> {

	public VehicleTypeDAO(Database database) {
		this.database = database;
		fieldsMap = VehicleTypeDAO.getColumnsToFieldsMap(); 
	}
	
	@Override
	public
	VehicleType get(String id) {
		Object[] params = new Object[] {id};
		return database.executeQuery(VehicleType.class, fieldsMap, getSQL("GET_VEHICLE_TYPE"),params);
	}
	
	public List<VehicleType> getList(){
		return database.executeQueryList(VehicleType.class, fieldsMap, getSQL("GET_VEHICLE_TYPES"));
	}
	
	public VehicleType getFromVIN(String vin) {
		Object[] params = new Object[] {vin};
		return database.executeQuery(VehicleType.class, fieldsMap, getSQL("GetVehicleType"),params);
	}

	@Override
	public
	int set(VehicleType obj) {
		int result = 0;
		
		//Does this VehicleType already exist?
		VehicleType vt = get(obj.getCode());
        
		Object[] params = new Object[] {obj.getCode(),obj.getDesc(), obj.getMaxLength(),obj.getMaxHeight(),
									    obj.getTruckLotCode(), obj.getTruckZone(),obj.getTruckArea(),obj.getConvoyEndRow(),
									    obj.getRailLotCode(),obj.getRailZoneID(),obj.getRailArea(),obj.getDeckType(),
										obj.getFillFactor(),obj.getTieDown(),obj.getVehicleWeight(),obj.getRailcarType(),
										obj.getChngWho(),obj.getChngDate()};
		
		if( vt!= null) {
			result = database.executeQueryUpdate(getSQL("UPDATE_VEHICLE_TYPE"), params);
		} else {
			result = database.executeQueryUpdate(getSQL("INSERT_VEHICLE_TYPE"), params);
		}		
		
		return result;
	}

	@Override
	public
	int delete(VehicleType obj) {
		int result = 0;
		Object[] params = new Object[] {obj.getCode()};
		
		result = database.executeQueryUpdate(getSQL("Delete_VIN_MASKS"), params);
		
		result = database.executeStoredProcedureUpdate(getSQL("spDeleteVehicleType"), params);
		
		return result;
	}
	

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
		case "GET_VEHICLE_TYPE":
			sql = "SELECT * FROM tblVehicleType WHERE tblVehicleType_TypeCode = ?";
			break;
		case "GetVehicleType":
			sql = "SELECT * " + 
					"FROM tblVehicleTypeMask " + 
					"LEFT JOIN tblVehicleType " + 
					"ON tblVehicleType_TypeCode = tblVehicleTypeMask_Type " + 
					"WHERE ? LIKE tblVehicleTypeMask_Mask";
			break;
		case "GET_VEHICLE_TYPES":
			sql = "SELECT * FROM tblVehicleType ORDER BY tblVehicleType_TypeCode";
			break;
		case "INSERT_VEHICLE_TYPE":
			sql = "INSERT INTO tblVehicleType VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			break;
		case "UPDATE_VEHICLE_TYPE":
			sql = "UPDATE tblVehicleType\r\n" + 
					"SET 	tblVehicleType_Desc = ?, " + 
					"	tblVehicleType_MaxLength = ?, " + 
					"	tblVehicleType_MaxHeight = ?, " + 
					"	tblVehicleType_ConvoyLotCode = ?, " + 
					"	tblVehicleType_ConvoyZoneID = ?, " + 
					"	tblVehicleType_ConvoyArea = ?, " + 
					"	tblVehicleType_ConvoyEndBay = ?, " + 
					"	tblVehicleType_RailLotCode = ?, " + 
					"	tblVehicleType_RailZoneID = ?, " + 
					"	tblVehicleType_RailArea = ?, " + 
					"	tblVehicleType_DeckType = ?, " + 
					"	tblVehicleType_FillFactor = ?, " + 
					"	tblVehicleType_TieDown = ?, " + 
					"	tblVehicleType_Weight = ?, " + 
					"	tblVehicleType_RailcarType = ?, " + 
					"	tblVehicleType_ChngWho = ?, " + 
					"	tblVehicleType_ChngDate = ? WHERE tblVehicleType_TypeCode = ?";
			break;
		case "GET_VIN_MASKS":
			sql = "SELECT * FROM tblVehicleTypeMask WHERE tblVehicleTypeMask_Type = ?";
			break;
		case "FIND_VIN_MASK":
			sql = "SELECT * FROM tblVehicleTypeMask WHERE tblVehicleTypeMask_Mask LIKE ?";
			break;
		case "GET_DUPLICATE_VTYPES_MASKS":
			sql = "SELECT COUNT(tblVehicleTypeMask_Type) AS MaskCount, tblVehicleTypeMask_Mask  " + 
					"FROM tblVehicleTypeMask " + 
					"GROUP BY tblVehicleTypeMask_Mask " + 
					"HAVING COUNT(tblVehicleTypeMask_Type) > 1";
			break;
		case "GET_DUPLICATE_VIN_VTYPES":
			sql = "select count(tblVIN_VIN), tblVIN_VIN   " + 
					"from tblVIN, tblVehicleTypeMask " + 
					"where tblVIN_VIN LIKE tblVehicleTypeMask_Mask " + 
					"and tblVIN_LotCode is null " + 
					"group by tblVIN_VIN " + 
					"having count(tblVIN_VIN) > 1";
			break;
		case "spInsertVTypeAssoc":
			sql = "spInsertVTypeAssoc";
			break;
		case "spDeleteVTypeAssoc":
			sql = "spDeleteVTypeAssoc";
			break;
		case "spUpdateVTypesMasks":
			sql = "spUpdateVTypesMasks";
			break;
		case "spDeleteVehicleType":
			sql = "spDeleteVehicleType";
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
				put("tblVehicleType_TypeCode", "Code");
				put("tblVehicleType_Desc", "Desc");
				put("tblVehicleType_MaxLength", "MaxLength");
				put("tblVehicleType_MaxHeight", "MaxHeight");
				put("tblVehicleType_ConvoyLotCode", "TruckLotCode");
				put("tblVehicleType_ConvoyZoneID", "TruckZone");
				put("tblVehicleType_ConvoyArea", "TruckArea");
				put("tblVehicleType_ConvoyEndBay", "ConvoyEndRow");
				put("tblVehicleType_RailLotCode", "RailLotCode");
				put("tblVehicleType_RailZoneID", "RailZoneID");
				put("tblVehicleType_RailArea", "RailArea");
				put("tblVehicleType_DeckType", "DeckType");
				put("tblVehicleType_FillFactor", "FillFactor");
				put("tblVehicleType_TieDown", "TieDown");
				put("tblVehicleType_Weight", "VehicleWeight");
				put("tblVehicleType_RailcarType", "RailcarType");
				put("tblVehicleType_ChngWho", "ChangeDate");
				put("tblVehicleType_ChngDate", "ChangeWho");				
			}
		};
		
		return columnsToFieldsMap;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		return null;
	}
}
