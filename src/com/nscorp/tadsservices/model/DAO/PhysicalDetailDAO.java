package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.PhysicalDetail;

public class PhysicalDetailDAO extends TADSDAO<PhysicalDetail> {

	public PhysicalDetailDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	public PhysicalDetail getPhysicalDetailVIN(String code, String vin) {
		Object[] params = new Object[] {code, vin};
		String sql = "spGet_Physical_Data_Vin";
		
		return database.executeStoredProcedure(PhysicalDetail.class, fieldsMap, sql, params);
	}
	public List<PhysicalDetail> getAll(String id) throws SQLException {
		//Set GetDataAll = ExecuteSP2("spGet_Physical_Data_All", sCode)
		Object[] params = new Object[] {id};
		String sql = "spGet_Physical_Data_All";
		
		return database.executeStoredProcedureList(PhysicalDetail.class, fieldsMap, sql, params);
	}
	
	public List<PhysicalDetail> getDataNonScanned(String code) throws SQLException{
		Object[] params = new Object[] {code};
		String sql = "spGet_Physical_Data_NonScanned";
		
		return database.executeStoredProcedureList(PhysicalDetail.class, fieldsMap, sql, params);
	}
	
	public List<PhysicalDetail> getDataDiscrepancies(String code) throws SQLException{
		Object[] params = new Object[] {code};
		String sql = "spGet_Physical_Data_Discrepancies";
		
		return database.executeStoredProcedureList(PhysicalDetail.class, fieldsMap, sql, params);
	}
	
	public List<PhysicalDetail> getPhysicalOutboundDate(String code) throws SQLException{
		Object[] params = new Object[] {code};
		String sql = "spGet_Physical_OutBound_Date";
		
		return database.executeStoredProcedureList(PhysicalDetail.class, fieldsMap, sql, params);
	}

	public List<PhysicalDetail> getDataAddedVins(String code) throws SQLException{
		Object[] params = new Object[] {code};
		String sql = "spGet_Physical_Data_Added_VINs";
		
		return database.executeStoredProcedureList(PhysicalDetail.class, fieldsMap, sql, params);
	}
	
	public void UpdatePhysicalFixes(PhysicalDetail obj) {
		Object[] params = new Object[] {obj.getCode()};
		String sql = "spUpdatePhysicalFixes";
		
		database.executeStoredProcedureUpdate(sql, params);
	}
	
	@Override
	public int set(PhysicalDetail obj) {
		Object[] params = new Object[] {obj.getCode(),obj.getVIN(),
				obj.getReconciled(),obj.getCurrentLot(),obj.getCurrentZone(),
				obj.getCurrentRow(),obj.getCurrentSpot(),
				obj.getScanLot(),obj.getScanZone(),
				obj.getScanRow(),obj.getScanSpot(),
				obj.getOutbound(),obj.getMessage(),
				obj.getChngWho(),obj.getChngDate()};
		String sql = "spUpdatePhysicalDetail";
		
		return database.executeStoredProcedureUpdate(sql, params);
	}

	@Override
	public int delete(PhysicalDetail obj) {
		// Not implemented in TADS
		return 0;
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
				//Code       VIN               Reconciled CurLot CurZone CurArea CurSpot     
				//tblPhysicalDetail_Code tblPhysicalDetail_VIN tblPhysicalDetail_Reconciled 
				//tblPhysicalDetail_CurLot tblPhysicalDetail_CurZone tblPhysicalDetail_CurArea tblPhysicalDetail_CurSpot 
				//tblPhysicalDetail_ScanLot tblPhysicalDetail_ScanZone tblPhysicalDetail_ScanArea tblPhysicalDetail_ScanSpot 
				//tblPhysicalDetail_OutBound tblPhysicalDetail_Message                                    
				//tblPhysicalDetail_ChngWho tblPhysicalDetail_ChngDate
				put("tblPhysicalDetail_Code", "Code");
				put("tblPhysicalDetail_VIN", "VIN");
				put("tblPhysicalDetail_Reconciled", "Reconciled");
				put("tblPhysicalDetail_CurLot", "CurrentLot");
				put("tblPhysicalDetail_CurZone", "CurrentZone");
				put("tblPhysicalDetail_CurArea", "CurrentRow");
				put("tblPhysicalDetail_CurSpot", "CurrentSpot");
				put("tblPhysicalDetail_ScanLot","ScanLot");
				put("tblPhysicalDetail_ScanZone","ScanZone");
				put("tblPhysicalDetail_ScanArea","ScanRow");
				put("tblPhysicalDetail_ScanSpot","ScanSpot");
				put("tblPhysicalDetail_Message","Message");
				put("tblPhysicalDetail_OutBound","Outbound");
				put("tblPhysicalDetail_ChngWho", "ChngWho");
				put("tblPhysicalDetail_ChngDate", "ChngDate");
				
				put("Code", "Code");
				put("VIN", "VIN");
				put("Reconciled", "Reconciled");
				put("CurLot", "CurrentLot");
				put("CurZone", "CurrentZone");
				put("CurArea", "CurrentRow");
				put("CurSpot", "CurrentSpot");
				put("ScanLot","ScanLot");
				put("ScanZone","ScanZone");
				put("ScanArea","ScanRow");
				put("ScanSpot","ScanSpot");
				put("Message","Message");
				put("OutBound","Outbound");
				put("Change Who", "ChngWho");
				put("Change Date", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}

	@Override
	PhysicalDetail get(String id) {
		return null;
	}
}
