package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Railcar;
import com.nscorp.tadsservices.model.Enums.eHoldObjAction;

public class RailcarDAO extends TADSDAO<Railcar> {
	
	public RailcarDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	@Override
	public Railcar get(String id) {
		Railcar railcar = null;
		
		String sql = "spGetRailcarRec";
		Object[] params = new Object[] {id};
		
		railcar = database.executeStoredProcedure(Railcar.class, fieldsMap, sql,params);
		
		return railcar;
	}

	@Override
	public int set(Railcar obj) {
		return 0;
	}

	@Override
	public int delete(Railcar obj) {
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
				//Class Railcar fields
				put("tblRailcars_Nbr", "CarNumber");				
				put("tblRailcars_Type", "Type");
				put("tblRailcars_TieDown", "TieDownType");
				put("tblRailcars_DeckType", "DeckType");
				put("tblRailcars_BiTriIndicator", "BiTriIndicator");
				put("tblRailcars_Articulated", "ArtCode");
				put("tblRailcars_Length", "Length");
				put("tblRailcars_OuterLength", "OuterLength");
				put("tblRailcars_Width", "Width");
				put("tblRailcars_DeckA", "DeckAHeight");
				put("tblRailcars_DeckB", "DeckBHeight");
				put("tblRailcars_DeckC", "DeckCHeight");
				put("tblRailcars_Weight", "Weight");
				put("tblRailcars_ChngWho", "ChngWho");
				put("tblRailcars_ChngDate", "ChngDate");											
			}
		};
		
		return columnsToFieldsMap;
	}
	
	@Override
	public String getSQL(String sqlName) {
		return null;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		String sql = null;
		eHoldObjAction eFind;
		
		eFind = (eHoldObjAction)Find;
		
		switch(eFind) {
		case eHoldFindMfg:
			sql = "spGetRailcarRec";
			break;

		default:
			sql = "spGetRailcarRec";
			break;
		}
		
		return sql;
	}
	
}
