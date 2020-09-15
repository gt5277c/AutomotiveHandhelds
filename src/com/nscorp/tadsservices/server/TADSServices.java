package com.nscorp.tadsservices.server;

import javax.ws.rs.core.Application;
import com.nscorp.tadsservices.model.Ramp;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/TADSServices")
public class TADSServices extends Application {
    // Layer 7 constants
	public static final String LAYER_7_URL = "https://esb.nscorp.com/IDS7/Authenticate/";
	public static final String LAYER_7_URL_QA = "https://qaesb.nscorp.com/IDS7/Authenticate/";
	public static final String LAYER_7_URL_TEST = "https://testesb.nscorp.com/IDS7/Authenticate/";
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	//Services
	VehicleService vs;
	RailcarService rs;
	PhysicalService ps;
	
	//Initialization and Logon
	private String configfile = "config.xml";
	private String env;
	private URL authurl;
	
	//Ramp information
	private Map<String,Ramp> ramps = null;
	private List<String> rampsNotStarted;
	private Ramp ramp = null;

	public TADSServices() {		

		try{
			InputStream input = getClass().getClassLoader().getResourceAsStream(configfile);
			Properties prop = new Properties();
			
			if(input == null) {
				System.out.println("Sorry, unable to find " + configfile);
			} else {
				//Load the locations from the properties file
				prop.loadFromXML(input);
								
				//Set the Environment variable for authorization
				env = prop.getProperty("environment");
				System.out.println("Environment is " + env);
	        	if(env.equals("QA")) {	
	        		authurl = new URL(LAYER_7_URL_QA);
	        	} else if(env.equals("Test")) {
	        		authurl = new URL(LAYER_7_URL_TEST);
	        	} else {  //Prod
	        		authurl = new URL(LAYER_7_URL);
	        	}
	        	
				//Initialize the Ramps
				ramps = new HashMap<String,Ramp>();
				rampsNotStarted = new ArrayList<String>();
			
				String locations = (String) prop.get("locations");
				for(String location : locations.split(",")) {
					System.out.println("Location ramp information for " + location );
					
					try {
						ramp = new Ramp(env, location);
						ramps.put(location, ramp);		
						
			            // delay 5 seconds
						System.out.println("Start..." + new Date());
			            TimeUnit.SECONDS.sleep(5);
			            System.out.println("End..." + new Date());
						
					} catch (Exception e) {
						rampsNotStarted.add(location);
						System.out.println("Unable to initialize ramp " + location);
						e.printStackTrace();
					}
				}		
				
				//Try to load any ramps unable to load the first time
				for(String location : rampsNotStarted) {
					try {
						ramp = new Ramp(env, location);
						ramps.put(location, ramp);
						rampsNotStarted.remove(location);
					} catch (Exception e) {
						System.out.println("Unable to initialize ramp 2nd try " + location);
						e.printStackTrace();
					}
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}		
		
		vs = new VehicleService(ramps,env,authurl);		
		rs = new RailcarService(ramps,env,authurl);
		ps = new PhysicalService(ramps,env,authurl);
		
		singletons.add(vs);
		singletons.add(rs);
		singletons.add(ps);
		//classes.add(VehicleService.class);
	}
	
	@Override
	public Set<Object> getSingletons(){
		return singletons;
	}
	
    @Override
	public Set<Class<?>> getClasses() {
        return classes;
    }    
}
