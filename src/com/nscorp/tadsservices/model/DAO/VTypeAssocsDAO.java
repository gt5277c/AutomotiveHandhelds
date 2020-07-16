package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.VTypeAssocs;
import com.nscorp.tadsservices.model.Enums.eVTypeAssocObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.VTypeAssocsSearchCriteria;

public class VTypeAssocsDAO extends TADSDAO<VTypeAssocs> {
	
	public VTypeAssocsDAO(Database database) {
		this.database = database;
		fieldsMap = VTypeAssocsDAO.getColumnsToFieldsMap();
	}
	
	@Override
	public VTypeAssocs get(String id) {
		// Not sure if this should be implemented to return a single vehicle type association
		//	because TADS only returns collections of associations.
		
		return null;
	}
	
	public List<VTypeAssocs> getList(String vtCode) throws SQLException{
		VTypeAssocsSearchCriteria vtsc = new VTypeAssocsSearchCriteria();

		vtsc.setSearchType(eVTypeAssocObjAction.eFindVTypeAssoc);
		vtsc.setVTypeCode(vtCode);
		return database.executeStoredProcedureList(VTypeAssocs.class, fieldsMap, getSQL(vtsc.getSearchType()), vtsc.getParameters());
	}

	@Override
	public int set(VTypeAssocs obj) {
		// Not sure if this is needs to be implemented for handheld code
		//Object[] params = new Object[] {obj.getVTypeAssocCode()};
		
		//d.executeStoredProcedureUpdate("spInsertVTypeAssoc", params);
		return 0;
	}

	@Override
	public int delete(VTypeAssocs obj) {
		// Not sure if this is needs to be implemented for handheld code
		return 0;
	}
	
	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				//Format is put("database column name", "class field name");
				put("tblVehicleTypeAssoc_TypeCode", "VTypeAssocCode");
				put("tblVehicleTypeAssoc_ChngWho", "ChngWho");
				put("tblVehicleTypeAssoc_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}
	
	private String getSQL(eVTypeAssocObjAction eFind) {
		String sql = null;
		
		switch(eFind) {
		case eFindVTypeAssoc:
			sql = "spGetVTypeAssoc";
			break;
		}
		
		return sql;
	}

	@Override
	public String getSQL(String sqlName) {
		return null;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eVTypeAssocObjAction eFind;
		
		eFind = (eVTypeAssocObjAction)Find;
		
		switch(eFind) {
		case eFindVTypeAssoc:
			sql = "spGetVTypeAssoc";
			break;
		}
		
		return sql;
	}
	
}
