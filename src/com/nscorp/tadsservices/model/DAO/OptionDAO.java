package com.nscorp.tadsservices.model.DAO;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionDAO extends TADSDAO<Option> {
	
	public OptionDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public Option get(String id) {
		return database.executeQuery(Option.class, fieldsMap, getSQL("GetOption"), new Object[] {id});
	}
	
	public List<Option> getOptionList(){
		return database.executeQueryList(Option.class, fieldsMap, getSQL("GetAllOptions"));
	}
	
	public Option getLastOptionUpdated() {
		return database.executeQuery(Option.class,  fieldsMap, getSQL("GetLastOptionUpdated"));
	}

	@Override
	public int set(Option obj) {
		int result = 0;
		String valueAsString;
		
		//Convert to String
		switch(obj.getType()) {
		case 1: 
		case 2:
			valueAsString = (String) obj.getValue();
			break;
		case 3:
			if(obj.getValue() instanceof Integer) {
				valueAsString = String.valueOf(obj.getValue());
			} else {
				valueAsString = (String) obj.getValue();
			}
			break;
		case 4:
			if((Boolean)obj.getValue() == true) {
				valueAsString = "Y";
			} else {
				valueAsString = "N";
			}
			break;
		default:
			valueAsString = (String) obj.getValue();
			break;
		}
		
		//Insert into tblOption
		Object[] params = new Object[] {obj.getName(),obj.getType(),valueAsString,obj.getChangeWho(),obj.getChangeDate()};
		result = database.executeStoredProcedureUpdate("spInsertOption",params);
		
		return result;
	}

	@Override
	public int delete(Option obj) {
		int result = 0;
		Object[] params = new Object[] {obj.getName()};
		database.executeStoredProcedureUpdate("spDeleteOption", params);
		return result;
	}

	
	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				put("tblOptions_Name", "Name");
				put("tblOptions_Type", "Type");
				put("tblOptions_Value", "Value");
				put("tblOptions_ChngWho", "ChangeWho");
				put("tblOptions_ChngDate", "ChangeDate");
			}
		};
		
		return columnsToFieldsMap;
	}
	
	@Override
	public String getSQL(String queryName) {
		String sql = null;
		
		switch(queryName) {
		case "GetOption":
			sql = "SELECT * FROM tblOptions"
					+ " where tblOptions_name = ? ";
			break;		
		case "GetAllOptions":
			sql = "SELECT * FROM tblOptions " + 
					"Union " +
					"select 'Ramp Code' as tblOptions_Name, " + 
					"	   '1' as tblOptions_type, " + 
					"	   tblRamp_Code as tblOptions_value, " + 
					"	   tblRamp_ChngWho as tblOptions_ChngWho, " + 
					"	   tblRamp_ChngDate as tblOptions_ChngDate  from tblramp " +
					"Union " +
					"select 'Ramp SPLC' as tblOptions_Name, " + 
					"	   '1' as tblOptions_type, " + 
					"	   tblRamp_SPLC as tblOptions_value, " + 
					"	   tblRamp_ChngWho as tblOptions_ChngWho, " + 
					"	   tblRamp_ChngDate as tblOptions_ChngDate  from tblramp " +
					"Union " +
					"select 'Ramp FSAC' as tblOptions_Name, " + 
					"	   '1' as tblOptions_type, " + 
					"	   tblRamp_FSAC as tblOptions_value, " + 
					"	   tblRamp_ChngWho as tblOptions_ChngWho, " + 
					"	   tblRamp_ChngDate as tblOptions_ChngDate  from tblramp ";
			break;
		case "GetLastOptionUpdated":
			sql = "select top 1 * from tbloptions " + 
					"where tblOptions_ChngDate = " +
					"(select max(tblOptions_ChngDate) from tbloptions)";
			break;
		default:
			sql = "SELECT * FROM tblOptions ";
			break;
		}
		
		return sql;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		return null;
	}

}
