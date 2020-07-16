package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.TruckCompany;

public class TruckCompanyDAO extends TADSDAO<TruckCompany> {

	public TruckCompanyDAO(Database database) {
		this.database = database;
		fieldsMap = TruckCompanyDAO.getColumnsToFieldsMap();
	}

	@Override
	TruckCompany get(String id) {
		return null;
	}
	
	public List<TruckCompany> getTruckCompanies(){
		String sql = "Select * from tblTruckCompany";
		return database.executeQueryList(TruckCompany.class, fieldsMap, sql);
	}

	@Override
	int set(TruckCompany obj) {
		return 0;
	}

	@Override
	int delete(TruckCompany obj) {
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

	public static Map<String,String> getColumnsToFieldsMap() {
		Map<String,String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("tblTruckCompany_Code", "code");
				put("tblTruckCompany_Desc", "description");
				put("tblTruckCompany_SCAC", "scac");				
			}
		};
		
		return columnsToFieldsMap;
	}	
}
