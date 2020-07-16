package com.nscorp.tadsservices.model.DAO;

import java.util.Map;

import com.nscorp.tadsservices.model.Database;

public abstract class TADSDAO<T>{
	protected Database database;
	protected Map<String,String> fieldsMap;
	
	public TADSDAO() {}
	
	public TADSDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}
	
	//CRUD
	abstract T get(String id);
	
	abstract int set(T obj);
	
	abstract int delete(T obj);
		
	//Any additional methods needed
	
	//Map for the TADS Object
	public static Map<String,String> getColumnsToFieldsMap() {
		return null;
	}

	public abstract String getSQL(String sqlName);
	
	public abstract <E extends Enum<?>> String getSQL(E Find);
}
