package com.nscorp.tadsservices.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class Database {
	public static final String DATABASE_ENV = "jdbc:sqlserver://INTERMODAL2K14PRR.NSCORP.COM;databaseName=";
	public static final String DATABASE_ENV_QA = "jdbc:sqlserver://INTERMODAL2K14QAR.NSCORP.COM;databaseName=";
	public static final String DATABASE_ENV_TEST = "jdbc:sqlserver://INTERMODAL2K14TE.ATLDC.NSCORP.COM\\INTERMODAL;databaseName=";
	
	private Connection connection = null;
	private QueryRunner qrun = null;
	private String env;
	private String location;

	public Database() {
		if(connection == null && location != null) {
			setLocation(location);
		}
	}
	
	public Database(String environment, String location) {
		if(connection == null) {
			this.env = environment;
			setLocation(location);
		}
	}
	
	public Connection getConnection() { return connection; }
	
	public String getEnv() { return env; }
	public void setEnv(String env) {
		this.env = env;
	}
	
	public String getLocation() { return location; }
	public void setLocation(String location) { 
		this.location = location; 
		initialize(location);
	}
	
	public <T extends TADSLocationObject> T getClass(Class<T> beanClass) {
		//Initialize the TADSLocationObject
		T someclass = null;
		
		try {
			Constructor<T> constructor = beanClass.getConstructor(Database.class);
			someclass = constructor.newInstance(new Object[] {this});			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return someclass;
	}
	
	public String executeQueryScalar(String sql, Object[] params) {
		String result = null;
		
		ScalarHandler<String> scalarhandler = new ScalarHandler<>();
		try {
			result = qrun.query(connection, sql, scalarhandler, params);
		} catch (SQLException e) {
			e.printStackTrace();
			initialize(location);
		}
		
		return result;
	}
	
	public String executeStoredProcedureScalar(String procname, Object[] params) {
		String result = null;
		
		procname = wrapStoredProcedureCall(procname, params);
		ScalarHandler<String> scalarHandler = new ScalarHandler<>();
		try {
			result = qrun.query(connection, procname, scalarHandler, params);
		} catch (SQLException e) {
			e.printStackTrace();
			initialize(location);
		}
		
		return result;
	}
	
	public int executeStoredProcedureUpdate(String procname, Object[] params) {
		Integer result = 0;
				
		try {
			procname = wrapStoredProcedureCall(procname, params);
			result = qrun.update(connection, procname, params);
		} catch (SQLException e) {
			e.printStackTrace();
			initialize(location);
		}
		
		return result;
	}	
	
	public int executeQueryUpdate(String procname, Object[] params) {
		Integer result = 0;
		
		try {
			result = qrun.update(connection, procname, params);
		} catch (SQLException e) {
			e.printStackTrace();
			initialize(location);
		}
		
		return result;
	}
	
	public <T extends TADSLocationObject> T executeStoredProcedure(Class<? extends T> beanClass, Map<String,String> fieldsMap, String procname) {
		Object[] params = null;
						
		return executeStoredProcedure(beanClass, fieldsMap, procname, params);
	}	
	
	public <T extends TADSLocationObject> T executeStoredProcedure(Class<? extends T> beanClass, Map<String,String> fieldsMap, String procname, Object[] params) {
		T result = null;
				
		try {
			procname = wrapStoredProcedureCall(procname, params);
			TADSBeanProcessor tbp = new TADSBeanProcessor(fieldsMap);
			tbp.setDatabase(this);			
			BeanHandler<T> bh = new BeanHandler<T>(beanClass,new BasicRowProcessor(tbp));
			result = qrun.query(connection, procname, bh, params);			
		} catch (SQLException e) {
			e.printStackTrace();
			initialize(location);
		}
		return result;
	}
	
	public <T extends TADSLocationObject> List<T> executeStoredProcedureList(Class<? extends T> beanClass, Map<String,String> fieldsMap, String procname) throws SQLException {
		Object[] params = null;
		
		return executeStoredProcedureList(beanClass, fieldsMap, procname, params);
	}	
	
	public <T extends TADSLocationObject> List<T> executeStoredProcedureList(Class<? extends T> beanClass, Map<String,String> fieldsMap, String procname, Object[] params) throws SQLException {
		List<T> results = null;
				
		//try {
			procname = wrapStoredProcedureCall(procname, params);
			TADSBeanProcessor tbp = new TADSBeanProcessor(fieldsMap);
			tbp.setDatabase(this);
			BeanListHandler<T> blh = new BeanListHandler<T>(beanClass, new BasicRowProcessor(tbp));
			results = qrun.query(connection, procname, blh, params);
		//} catch (SQLException e) {
		//	e.printStackTrace();
		//}
		return results;
	}	
	
	public <T extends TADSLocationObject> T executeQuery(Class<? extends T> beanClass, Map<String,String> fieldsMap, String sql) {
		return executeQuery(beanClass, fieldsMap, sql, new Object[] {});
	}
	
	public <T extends TADSLocationObject> T executeQuery(Class<? extends T> beanClass, Map<String,String> fieldsMap, String sql, Object[] params) {
		T result = null;
		
		try {
			TADSBeanProcessor tbp = new TADSBeanProcessor(fieldsMap);
			tbp.setDatabase(this);
			BeanHandler<T> bh = new BeanHandler<T>(beanClass, new BasicRowProcessor(tbp));
			result = qrun.query(connection, sql, bh, params);			
		} catch (SQLException e) {
			e.printStackTrace();
			initialize(location);
		}
		return result;
	}
	
	public <T extends TADSLocationObject> List<T> executeQueryList(Class<? extends T> beanClass, Map<String, String> fieldsMap, String sql){
		Object[] params = null;
		
		return executeQueryList(beanClass, fieldsMap,sql, params);
	}
	
	public <T extends TADSLocationObject> List<T> executeQueryList(Class<? extends T> beanClass, Map<String, String> fieldsMap, String sql,Object [] params){
		List<T> results = null;
		
		try {
			TADSBeanProcessor tbp = new TADSBeanProcessor(fieldsMap);
			tbp.setDatabase(this);
			BeanListHandler<T> blh = new BeanListHandler<T>(beanClass, new BasicRowProcessor(tbp));
			results = qrun.query(connection, sql,blh,params);		
		} catch (SQLException e) {
			e.printStackTrace();
			initialize(location);
		}
		return results;		
	}
	
	private void initialize(String location) {		
		String connectionURL = "";

		//Reset the connection
		connection = null;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			//connectionURL = "jdbc:sqlserver://" + "database server name" + ";databaseName=" + "database name" + ";";
			//Connection user information
			//m_Connection = DriverManager.getConnection(connectionURL,"userid","password");

			if(env.equals("QA")) {
				connectionURL = DATABASE_ENV_QA + location + ";";
				connection = DriverManager.getConnection(connectionURL,"syTADSqa","Gc0-RRhj.6jQdG0rgbfb/pM-6=.V%wN5");
			} else if(env.equals("Test")) {
				connectionURL = DATABASE_ENV_TEST + location + ";";
				connection = DriverManager.getConnection(connectionURL,"syTADSte","OL%j/Yr=n%ZCc%w@Q9N4ydb.k1g?3tpy");
			} else {
				connectionURL = DATABASE_ENV + location + ";";
				connection = DriverManager.getConnection(connectionURL,"syTADSpr","fEuED90kab15bAILS43leGes^");
			}
						
			qrun = new QueryRunner();
			
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private String wrapStoredProcedureCall(String procname, Object[] params) {
		procname = "{call " + procname;
		
		if(params != null && params.length > 0) {
			for(int x = 0; x < params.length; x++) {
				if(x == 0) {
					procname = procname  + "(?";
				} else {
					procname = procname + ",?";	
				}								
			}
			procname = procname + ")";
		}
		procname = procname + "}";
		
		return procname;
	}
}
