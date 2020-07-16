package com.nscorp.tadsservices.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Quota;
import com.nscorp.tadsservices.model.Enums.eQuota;
import com.nscorp.tadsservices.model.SearchCriteria.QuotaSearchCriteria;

public class QuotaDAO extends TADSDAO<Quota> {

	public QuotaDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap(); 
	}
	
	@Override
	Quota get(String id) {
		//TADS does not implement
		return null;
	}
	
	public Quota checkQuota(QuotaSearchCriteria qsc) {
		return database.executeStoredProcedure(Quota.class, fieldsMap, "spCheckQuota", qsc.getParameters());
	}

	public Quota get(QuotaSearchCriteria qsc) {
		return database.executeStoredProcedure(Quota.class, fieldsMap, getSQL(qsc.getSearchType()),qsc.getParameters());
	}
	
	public List<Quota>getList(QuotaSearchCriteria qsc) throws SQLException{
		return database.executeStoredProcedureList(Quota.class, fieldsMap, getSQL(qsc.getSearchType()), qsc.getParameters());
	}
	
	@Override
	public
	int set(Quota obj) {
		Object[] params = new Object[] {obj.getFromZone(),obj.getToZone(),
				obj.getFromLL(),obj.getToLL(),obj.getQuota(),obj.getvType(),
				obj.getChngWho(),obj.getChngDate()};
		
		return database.executeStoredProcedureUpdate("spInsertQuota", params);
	}

	@Override
	public
	int delete(Quota obj) {
		Object[] params = new Object[] {obj.getFromZone(),obj.getToZone(),obj.getFromLL(),obj.getToLL(),obj.getvType()};
		return database.executeStoredProcedureUpdate("spDeleteQuota", params);
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
			default:
				sql = sqlName;
				break;
		}
		return sql;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eQuota eFind;
		
		eFind = (eQuota)Find;
		
		switch(eFind) {
		case eFindQuota:
			sql = "spGetQuota";
			break;
		case eFindQuotaArea:
			sql = "spGetQuotas";
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
				put("TblQuotas_FromZone", "FromZone");
				put("TblQuotas_ToZone", "ToZone");
				put("TblQuotas_FromLL", "FromLL");
				put("TblQuotas_ToLL", "ToLL");
				put("TblQuotas_Quota", "Quota");
				put("Quota","Quota");
				put("Count","Count");
				put("TblQuotas_VType", "vType");
				put("tblVIN_VehicleType","vType");
				put("TblQuotas_ChngWho", "ChngWho");
				put("TblQuotas_ChngDate", "ChngDate");
			}
		};
		
		return columnsToFieldsMap;
	}

}
