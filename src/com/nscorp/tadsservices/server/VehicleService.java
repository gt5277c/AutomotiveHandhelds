package com.nscorp.tadsservices.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.binary.Base64;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Dealer;
import com.nscorp.tadsservices.model.TruckCompany;
import com.nscorp.tadsservices.model.Upfitter;
import com.nscorp.tadsservices.model.User;
import com.nscorp.tadsservices.model.Vehicle;
import com.google.gson.Gson;
import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Enums.eBayArrival;
import com.nscorp.tadsservices.model.Enums.eHoldObjAction;
import com.nscorp.tadsservices.model.Enums.eRailObjAction;
import com.nscorp.tadsservices.model.Enums.eUpfitter;
import com.nscorp.tadsservices.model.Enums.eVINObjAction;
import com.nscorp.tadsservices.model.Hold;
import com.nscorp.tadsservices.model.HoldCriteria;
import com.nscorp.tadsservices.model.Location;
import com.nscorp.tadsservices.model.SearchCriteria.HoldSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.RailspotSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.UpfitterSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.VehicleSearchCriteria;
import com.nscorp.tadsservices.model.ManfVINCode;
import com.nscorp.tadsservices.model.Manufacturer;
import com.nscorp.tadsservices.model.Option;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.Ramp;
import com.nscorp.tadsservices.model.Route;
import com.nscorp.tadsservices.model.SystemSettings;


@Path("/VehicleService")
public class VehicleService {
    // Layer 7 constants
	public static final String LAYER_7_URL = "https://esb.nscorp.com/IDS7/Authenticate/";
	public static final String LAYER_7_URL_QA = "https://qaesb.nscorp.com/IDS7/Authenticate/";
	public static final String LAYER_7_URL_TEST = "https://testesb.nscorp.com/IDS7/Authenticate/";

	private Map<String,Ramp> ramps = null;
	private URL authurl;
	
	//Startup
	public VehicleService(){}
		
	public VehicleService(Map<String,Ramp> ramps, String env, URL authurl) {
		this.ramps = ramps;
		this.authurl = authurl;
	}
	
	public void setRamps(Map<String,Ramp> ramps) { this.ramps = ramps; }
	public void setEnv(String env) { }
	public void setAuthURL(URL authurl) { this.authurl = authurl; }
	
	@GET
	@Path("/")
	public String baseHelloVehicle() {
		return "Hello Vehicle!";
	}
	
	/**
	 * Given a User object will take the user and password
	 * and base64 encode to authorize.  Also the user object
	 * will have the current location to authorize against.
	 * 
	 * Will return a response that includes
	 * Response Code: 200 is Okay
	 * success: T/F
	 * principal: User ID
	 * assertion: Token for auth
	 * User object
	 */
	@POST
	@Path("/logon/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public AuthResponse logon(ConnectionInformation connectioninformation) {
	    String errormessage = null;
	    User authUser = null;
		AuthResponse authresponse = null;
		HttpURLConnection connection = null;
		int responseCode = 0;
		Gson gson = new Gson();
		String status = "Starting logon";
		
		//Check the user object for the necessary fields
		User user = connectioninformation.getUser();
		if(user.getUserID() == null) {
			errormessage = "UserID missing from Vehicle Request";
		} else if(user.getLocation() == null) {
			errormessage = "Current location is missing from Vehicle Request";
		} else {

			try {			
				//Get the location, user + pass encoded64 string
				String location = user.getLocation();
				String authstring = user.getUserID() + ":" + user.getPassword();
				
				status = "about to encode the authstring";
				authstring = Base64.encodeBase64String(authstring.getBytes());
				status = "finished encoding the authstring";
	
				//Make the call to auth
				status = "calling the auth";
	            connection = (HttpURLConnection) authurl.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setRequestProperty("Accept", "*/*");
	            connection.setRequestProperty("Authorization", "Basic " + authstring);
	            status = "made the call to auth";
	            
	            status = "getting the response code";
	            responseCode = connection.getResponseCode();
	            status = "got the response code";
	
	        	//Parse the response
	            InputStream is = connection.getInputStream();
	            BufferedReader in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	            status = "parsing the result";
	            authresponse = gson.fromJson(in, AuthResponse.class);
	            status = "parsed the result";
	            
	            //Check for success or failure
	            if(authresponse.getSuccess()) {
	            	status = "auth is success";
	        		//Get the ramps information
	        		Ramp ramp = ramps.get(user.getLocation());
	        		Database database = ramp.getDatabase();
	        		User m_user = new User(database);
	        		
	        		//Get the ramp variables
	        		Manufacturer manf = ramp.getManufacturer();
	        		Option option = ramp.getOption();
	        		Route route = ramp.getRoute();
	        		Dealer dealer = ramp.getDealer();
	        		Upfitter upfitter = ramp.getUpfitter();
	        		Location alocation = ramp.getLocation();
	        		
	            	//Validate the user against the location
	            	authUser = m_user.get(authresponse.getResult().getPrincipal());      
	            	if(authUser != null) {
	            		//Add the user to the connection information for the response
	            		authUser.setLocation(location);
	            		connectioninformation.setUser(authUser);
	            		
	            		//Now get the last time the ramp information was loaded.
	            		//Option
	            		option = option.getLastOptionUpdated();
	            		connectioninformation.setOptionLoadedDate(option.getChangeDate());	            		
	            		
	            		//TruckCompany no date in the TruckCompany table
	            		//connectioninformation.setTruckCompanyLoadedDate(truckcompany.);
	            		
	            		//Route
	            		route = route.getLastRouteUpdated();
	            		connectioninformation.setRouteLoadedDate(route.getChngDate());
	            		
	            		//Dealer
	            		dealer = dealer.getLastDealerUpdated();
	            		connectioninformation.setDealerLoadedDate(dealer.getChngDate());
	            		
	            		//Upfitter
	            		upfitter = upfitter.getLastUpfitterUpdated();
	            		connectioninformation.setUpfitterLoadedDate(upfitter.getChngDate());
	            		
	            		//Manufacturer
	            		manf = manf.getLastManufacturerUpdated();
	            		connectioninformation.setManufacturerLoadedDate(manf.getChngDate());
	            		
	            		//ManfVINCode no date in the ManfCodes table
	            		//connectioninformation.setManfVINCodeLoadedDate(manf.getChngDate());
	            		
	            		//Location
	            		List<Location> somelocations;
	            		somelocations = alocation.getLastLocationUpdated();
	            		Date somedate = somelocations.get(0).getChngDate();
	            		for(Location l : somelocations) {
	            			if(l.getChngDate().after(somedate)) somedate = l.getChngDate();
	            		}
	            		
	            		connectioninformation.setLocationLoadedDate(somedate);
	            		            		
	            		//Add the connection information to the response
	            		authresponse.setConnectionInformation(connectioninformation);
	            	} else {
	            		//Not authorized at the location
	            		authresponse.getResult().setAssertion("");
	            		authresponse.getResult().setDescription("Not Authorized for TADS at " + location + ".");
	            		authresponse.getResult().setStatusCode("400");
	            		authresponse.getResult().setUIMessage("Status: " + status);
	            		authresponse.setSuccess(false);
	            	}
	            } else {
	            	System.out.println("Auth response was " + authresponse.getSuccess());
	            	            	
	            	if(authresponse.getResult() == null) {
	            		System.out.println("Creating result");
	            		Result result = new Result();
	            		authresponse.setResult(result);
	            	}
	        		authresponse.getResult().setAssertion("");
	        		authresponse.getResult().setDescription("Error: problem with auth.");
	        		authresponse.getResult().setStatusCode("" + responseCode);
	        		authresponse.getResult().setUIMessage("Status: " + status);
	        		authresponse.setSuccess(false);   
	            }
	        } catch (IOException e) {
	        	errormessage = e.getMessage();
	            e.printStackTrace();
	        }
		}
		
		if(errormessage != null) {
        	if(authresponse == null) {
        		System.out.println("Creating auth response");
        		authresponse = new AuthResponse();        		
        	}
        	
        	if(authresponse.getResult() == null) {
        		System.out.println("Creating result");
        		Result result = new Result();
        		authresponse.setResult(result);
        	}
    		authresponse.getResult().setAssertion("");
    		authresponse.getResult().setDescription("Error: " + errormessage);
    		authresponse.getResult().setStatusCode("" + responseCode);
    		authresponse.getResult().setUIMessage("Status: " + status);
    		authresponse.setSuccess(false);       	
		}
		
		return authresponse;
	}
	
	//Utilities
	// {{
	@POST
	@Path("/getRailspots/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Railspot> getRailspots(RailcarRequest railcarrequest) throws SQLException{
		//Get the request information
		User user = railcarrequest.getUser();
		Railspot railspot = railcarrequest.getRailspot();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		Railspot searchrailspot = new Railspot(database);
		RailspotSearchCriteria rsc = new RailspotSearchCriteria();
		
		//Now find if looking for railspots by Lot, Zone, Track, or Spot
		if(railspot.getSpot() > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindRailspotByLotZoneTrackSpot);
			rsc.setLotCode(railspot.getLotCode());
			rsc.setZoneID(railspot.getZoneID());
			rsc.setTrackNbr(railspot.getTrackNbr());
			rsc.setSpot(Integer.toString(railspot.getSpot()));
		} else if(railspot.getTrackNbr() != null && railspot.getTrackNbr().length() > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindRailspotByLotZoneTrack);
			rsc.setLotCode(railspot.getLotCode());
			rsc.setZoneID(railspot.getZoneID());
			rsc.setTrackNbr(railspot.getTrackNbr());
		} else if(railspot.getZoneID() != null && railspot.getZoneID().length() > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindRailspotByLotZone);
			rsc.setLotCode(railspot.getLotCode());
			rsc.setZoneID(railspot.getZoneID());
		} else {
			rsc.setSearchType(eRailObjAction.eRailFindRailspotByLot);
			rsc.setLotCode(railspot.getLotCode());
		}

		List<Railspot> railspots = searchrailspot.GetRailspots(rsc);
		
		return railspots;
	}
	
	@POST
	@Path("/getOptions/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Option> getOptions(User user){
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Option option = new Option(database);
		return option.getOptionList();
	}
	
	@POST
	@Path("/setOptions/")
	@Produces(MediaType.APPLICATION_XML)
	public Response setOptions(OptionRequest optionrequest){
		Response response;
		String errormessage = null;
		
		//Get the request information
		SystemSettings ss = optionrequest.getSystemSettings();
		User user = optionrequest.getUser();
		
		//Get the ramp information
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		try {
			//Get the option for the deck count
			Option option = new Option(database);
			option.setName("HHDeckCount");
			option.setType(3);
			Integer ideckcount = ss.getHHDeckCount();
			option.setValue(String.valueOf(ideckcount));
			option.setChangeDate(Common.getCurrentDateTime());
			option.setChangeWho(user.getUserID());
			option.Save();
			
			//Get the option for the deck order or HHDeckSeq
			option = new Option(database);
			option.setName("HHLoadSeq");
			option.setType(1);
			option.setValue(ss.getHHLoadSeq());
			option.setChangeDate(Common.getCurrentDateTime());
			option.setChangeWho(user.getUserID());
			option.Save();
		} catch(Exception e) {
			e.printStackTrace();
			errormessage = "Error in saving options";
		}
		
		//Check for error
		if(errormessage == null) {
			System.out.println("Options set");
			Option o = new Option();
			o.setName("Options set");
			response = Response.ok().entity(o).build();						
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}

	@POST
	@Path("/getTruckCompanies/")
	@Produces(MediaType.APPLICATION_XML)
	public List<TruckCompany> getTruckCompanies(User user){
		//Get the Truck Companies
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		TruckCompany truckcompany = new TruckCompany(database);
		
		return truckcompany.getTruckCompanies();
	}
	
	@POST
	@Path("/getRoutes/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Route> getRoutes(User user){
		//Get the ramp information
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Route route = new Route(database);
		
		try {
			return route.getAllRoutes();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@POST
	@Path("/getDealers/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Dealer> getDealers(User user){
		//Get the ramp information
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Dealer dealer = new Dealer(database);
		
		return dealer.getAllDealers();
	}
	
	@POST
	@Path("/getUpfitters/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Upfitter> getUpfitters(User user){
		//Get the ramp information
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Upfitter upfitter = new Upfitter(database);
		UpfitterSearchCriteria usc = new UpfitterSearchCriteria();
		usc.setSearchType(eUpfitter.eFindUpfitterAll);
		
		try {
			return upfitter.getList(usc);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	@POST
	@Path("/getManufacturers/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Manufacturer> getManufacturers(User user){
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Manufacturer manf = new Manufacturer(database);
		
		return manf.getAllManufacturers();
	}
	
	@POST
	@Path("/getManfVINCodes/")
	@Produces(MediaType.APPLICATION_XML)
	public List<ManfVINCode> getManfVINCodes(User user){
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Manufacturer manf = new Manufacturer(database);
		
		return manf.getManfVINCodes();
	}
	
	@POST
	@Path("/getAllLocations/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Location> getLotLayout(User user) throws SQLException{
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Location location = new Location(database);
		List<Location> locations = new ArrayList<Location>();
		List<Location> tmplocations;
		
		//Now get all of the Lots, Zones, Rows, Spots
		
		//Convoy
		tmplocations = location.getLocationList("GetAllConvoySpots");
		if(tmplocations != null && tmplocations.size() > 0) locations.addAll(tmplocations);
		
		//Loadline
		tmplocations = location.getLocationList("GetAllLoadlineSpots");
		if(tmplocations != null && tmplocations.size() > 0) locations.addAll(tmplocations);
		
		//Storage
		tmplocations = location.getLocationList("GetAllStorageSpots");
		if(tmplocations != null && tmplocations.size() > 0) locations.addAll(tmplocations);
		
		//Track
		tmplocations = location.getLocationList("GetAllTrackSpots");
		if(tmplocations != null && tmplocations.size() > 0) locations.addAll(tmplocations);
		
		//SupportTrack
		tmplocations = location.getLocationList("GetAllSupportTrackSpots");
		if(tmplocations != null && tmplocations.size() > 0) locations.addAll(tmplocations);
				
		return locations;
	}
	
	@POST
	@Path("/getConvoyLocations/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Location> getBayLocations(User user) throws SQLException{
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Location location = new Location(database);
		
		List<Location> locations = location.getLocationList("GetAllConvoySpots");
		
		return locations;
	}
	
	@POST
	@Path("/getLoadlineLocations/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Location> getLoadlineLocations(User user) throws SQLException{
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Location location = new Location(database);
		
		List<Location> locations = location.getLocationList("GetAllLoadlineSpots");
		
		return locations;
	}
	
	@POST
	@Path("/getStorageLocations/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Location> getStorageLocations(User user) throws SQLException{
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Location location = new Location(database);
		
		List<Location> locations = location.getLocationList("GetAllStorageSpots");
		
		return locations;
	}
	
	@POST
	@Path("/getTrackLocations/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Location> getTrackLocations(User user) throws SQLException{
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Location location = new Location(database);
		
		List<Location> locations = location.getLocationList("GetAllTrackSpots");
		
		return locations;
	}
	
	@POST
	@Path("/getSupportTrackLocations/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Location> getSupportTrackLocations(User user) throws SQLException{
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		Location location = new Location(database);
		
		List<Location> locations = location.getLocationList("GetAllSupportTrackSpots");
		
		return locations;
	}
	
	@POST
	@Path("/getAllHolds/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Hold> getAllHolds(User user){
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();

		Hold hold = new Hold(database);
		HoldSearchCriteria hsc = new HoldSearchCriteria();
		hsc.setSearchType(eHoldObjAction.eHoldFindAll);
		
		List<Hold> holds = hold.GetHolds(hsc);
		
		return holds;
	}
	
	@POST
	@Path("/getAllHoldsVehicle/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Hold> getAllHoldsVehicle(VehicleRequest vehiclerequest){
		User user = vehiclerequest.getUser();
		Vehicle vehicle = vehiclerequest.getVehicle();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();

		Hold hold = new Hold(database);
		HoldSearchCriteria hsc = new HoldSearchCriteria();
		hsc.setSearchType(eHoldObjAction.eHoldFindVIN);
		hsc.setVIN(vehicle.getVIN());
		
		List<Hold> holds = hold.GetHolds(hsc);
		
		return holds;
	}
	// }}
	
	//Actions
	// {{
	@POST
	@Path("/findVehicle/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Vehicle> findVehicle(VehicleRequest vehiclerequest) {

		//Get the ramps information
		User user = vehiclerequest.getUser();
		String vin = vehiclerequest.getVehicle().getVIN();
				
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		//Make a vehicle search criteria out of the vehicle
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		vsc.setSearchType(eVINObjAction.eVINFindVINLike);
		vsc.setVIN(vin);

		List<Vehicle> vehicles = null;
		Vehicle vehicle = new Vehicle(database);
		vehicles = vehicle.getVehicleList(vsc);
		
		//Now get all of the holds for the vehicles
		for(Vehicle v : vehicles) {
			//See if the vehicles has a hold
			if(v.getHoldReasonCode() != null && v.getHoldReasonCode().length() > 0) {
				//Get the holds
				v.setVINHolds(v.getVehicleHolds(v.getVIN()));
			}
		}
		
		if (vehicles == null || vehicles.isEmpty() || vehicles.size() < 1 ){
			System.out.println("Not found using search type:" + vsc.getSearchType() + " and parameters " + vsc.getParameters());
		} else {
			System.out.println("Found " + vehicles.size() + " vehicles");
		}
		
		return vehicles;
	}

	@POST
	@Path("/addVIN/")
	@Produces(MediaType.APPLICATION_XML)
	public Response addVIN(VehicleRequest vehiclerequest) {
		Vehicle requestvehicle = null;
		String errormessage = null;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			
			//Set the optional fields
			if(requestvehicle.getInboundTruckID() == null) requestvehicle.setInboundTruckID("");
			if(requestvehicle.getChngDate() == null) requestvehicle.setChngDate(new Date());

			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			requestvehicle.setLocationDatabase(database);
									
			//BayLogic - AddVIN
			try {
				if(requestvehicle.getBayArrival() != null && requestvehicle.getBayArrival().equals(eBayArrival.eBay_Rail)) {
					requestvehicle = baylogic.AddVIN(requestvehicle, eBayArrival.eBay_Rail);
				}else {
					//Default to Truck In
					requestvehicle = baylogic.AddVIN(requestvehicle, eBayArrival.eBay_TruckIn);	
				}
															
			} catch (Exception e) {
				e.printStackTrace();
				errormessage = "Error:" + e.getMessage();							
			}
		}
		
		//Check for error
		if(errormessage == null) {
			if(requestvehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("ADD Vin complete for " + requestvehicle.getVIN() + ".");
				response = Response.ok().entity(requestvehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}

	@POST
	@Path("/addVINLLDirect/")
	@Produces(MediaType.APPLICATION_XML)
	public Response addVINLLDirect(VehicleRequest vehiclerequest) {
		Vehicle requestvehicle = null;
		String errormessage = null,vin;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			
			//Set the optional fields
			if(requestvehicle.getInboundTruckID() == null) requestvehicle.setInboundTruckID("");
			if(requestvehicle.getChngDate() == null) requestvehicle.setChngDate(new Date());

			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			SystemSettings systemsettings = ramp.getSystemSettings();
			requestvehicle.setLocationDatabase(database);
				
			//First check for an ASN
			vin = requestvehicle.getVIN();
			Vehicle findvehicle = requestvehicle.getVehicle(vin);
			
			if(findvehicle != null || (requestvehicle.getRoute() != null && requestvehicle.getDealer() != null)) {
				//Found ASN or the user has input the Route and Dealer for the vehicle. 

				//Update the requestvehicle with ASN information
				if(findvehicle != null) {
					requestvehicle.updateVehicle(findvehicle);					
				}			

				//BayLogic
				try {					
					Location baylocation = baylogic.GetNextEmptyLLSpotNoCheck(requestvehicle.getZone(), requestvehicle.getRow(), requestvehicle);
					if(baylocation != null) {
						requestvehicle.updateVehicleLocation(baylocation);
						requestvehicle.Save();
					}
				} catch (Exception e) {
					e.printStackTrace();
					errormessage = "Error:" + e.getMessage();							
				}				
			} else {
				//Check for No ASN Hold logic
				if(systemsettings.getUseNoASNHold()) {			
					//Create a Criteria Hold for the vehicle
					HoldCriteria holdcriteria = new HoldCriteria(database);
					
					//Based on the VIN find the manufacturer
					holdcriteria.setManufacturer(requestvehicle.getManufacturerFromVIN(vin));
					holdcriteria.setVIN(vin);
					holdcriteria.setHoldCode("NA");
					holdcriteria.setChangWho("System");
					holdcriteria.setChangeDate(new Date());
					holdcriteria.Save();
					
					//BayLogic - AddVIN
					try {
						requestvehicle = baylogic.AddVIN(requestvehicle, eBayArrival.eBay_TruckIn);												
					} catch (Exception e) {
						e.printStackTrace();
						errormessage = e.getMessage();							
					}
				} else {
					//No ASN
					//Will go back to the client to get the Route and Dealer information
					errormessage = "No ASN found";
				}
			}
		}
		
		//Check for error
		if(errormessage == null) {
			if(requestvehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("found location for " + requestvehicle.getVIN() + ".");
				response = Response.ok().entity(requestvehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
	
	@POST
	@Path("/addVINBayDirect/")
	@Produces(MediaType.APPLICATION_XML)
	public Response addVINBayDirect(VehicleRequest vehiclerequest) {
		Vehicle requestvehicle = null;
		Vehicle findvehicle = null;
		String errormessage = null,vin;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			
			//Set the optional fields
			if(requestvehicle.getInboundTruckID() == null) requestvehicle.setInboundTruckID("");
			if(requestvehicle.getChngDate() == null) requestvehicle.setChngDate(new Date());

			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			SystemSettings systemsettings = ramp.getSystemSettings();
			requestvehicle.setLocationDatabase(database);
				
			//First check for an ASN
			vin = requestvehicle.getVIN();
			findvehicle = requestvehicle.getVehicle(vin);
			
			if(findvehicle != null || (requestvehicle.getRoute() != null && requestvehicle.getDealer() != null)) {
				//Found ASN or the user has input the Route and Dealer for the vehicle. 
		
				//Update requestvehicle with ASN information
				if(findvehicle != null) {
					requestvehicle.updateVehicle(findvehicle);
				}

				try {
					if(requestvehicle.getLotCode().equals(Constants.LOADLINE_LOT)) {
						Location baylocation = baylogic.GetNextEmptyLLSpotNoCheck(requestvehicle.getZone(), requestvehicle.getRow(), requestvehicle);
						if(baylocation != null) {
							requestvehicle.updateVehicleLocation(baylocation);
							requestvehicle.Save();
						}
					} else {
						requestvehicle = baylogic.AddVIN(requestvehicle, eBayArrival.eBay_VINAdd);
					}
				} catch (Exception e) {
					e.printStackTrace();
					errormessage = "Error:" + e.getMessage();							
				}		
			} else {
				//Check for No ASN Hold logic
				if(systemsettings.getUseNoASNHold()) {			
					//Create a Criteria Hold for the vehicle
					HoldCriteria holdcriteria = new HoldCriteria(database);
					
					//Based on the VIN find the manufacturer
					holdcriteria.setManufacturer(requestvehicle.getManufacturerFromVIN(vin));
					holdcriteria.setVIN(vin);
					holdcriteria.setHoldCode("NA");
					holdcriteria.setChangWho("System");
					holdcriteria.setChangeDate(new Date());
					holdcriteria.Save();
					
					//BayLogic - AddVIN
					try {
						requestvehicle = baylogic.AddVIN(requestvehicle, eBayArrival.eBay_TruckIn);												
					} catch (Exception e) {
						e.printStackTrace();
						errormessage = e.getMessage();							
					}
				} else {
					//No ASN
					//Will go back to the client to get the Route and Dealer information
					errormessage = "No ASN found";
				}
			}
		}
		
		//Check for error
		if(errormessage == null) {
			if(requestvehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("found location for " + requestvehicle.getVIN() + ".");
				response = Response.ok().entity(requestvehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
	
	@POST
	@Path("/driveOut/")
	@Produces(MediaType.APPLICATION_XML)
	public Response driveOut(VehicleRequest vehiclerequest) {
		Vehicle vehicle = null, requestvehicle;
		String errormessage = null, vin = null;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			vin = requestvehicle.getVIN();
			
			//Set the optional fields with default values
			if(requestvehicle.getInboundTruckID() == null) requestvehicle.setInboundTruckID("");
			if(requestvehicle.getChngDate() == null) requestvehicle.setChngDate(new Date());

			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			ramp.getSystemSettings();
			
			//Add a connection to the location database to the request vehicle
			requestvehicle.setLocationDatabase(database);
			
			//VINCheck
			if(requestvehicle.VINCheck(vin)) {
				//Find ASN vehicle information
				vehicle = requestvehicle.getVehicle(vin);

				//ASN found
				if( vehicle != null) {
					System.out.println("found ASN for vehicle " + vin + ".  Now look for a bay location");
						
					//Update the vehicle with the request user,time,truck company, and load number
					vehicle.updateVehicle(requestvehicle);

					//BayLogic - Prebay vehicle
					try {
						vehicle = baylogic.DriveOut(vehicle);											
					} catch (Exception e) {
						e.printStackTrace();
						errormessage = "Drive out Error: " + e.getMessage();	
					}
				} else {
					//No ASN
					//Will go back to the client to get the Route and Dealer information
					errormessage = "Vehicle not on Lot";
				}
					
			} else {
				//Failed VINCheck
				errormessage = "Invalid VIN";
			}
		}

		//Check for error
		if(errormessage == null) {
			if(vehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("Drive out for " + vin + " successful.");
				response = Response.ok().entity(vehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}

		return response;
	}

	@POST
	@Path("/getNextEmptyLLSpotNoCheck/")
	@Produces(MediaType.APPLICATION_XML)
	public Response getNextEmptyLLSpotNoCheck(VehicleRequest vehiclerequest) {
		Vehicle requestvehicle = null;
		String errormessage = null;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();

			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			ramp.getSystemSettings();
			requestvehicle.setLocationDatabase(database);

			//BayLogic - getNextEmptyLLSpotNoCheck
			try {
				Location location = baylogic.GetNextEmptyLLSpotNoCheck("Zone","Row",requestvehicle);
				//Update the vehicle with the location information
				requestvehicle.updateVehicleLocation(location);
			
			} catch (Exception e) {
				e.printStackTrace();
				errormessage = e.getMessage();							
			}
		}
		
		//Check for error
		if(errormessage == null) {
			if(requestvehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("found location for " + requestvehicle.getVIN() + ".");
				response = Response.ok().entity(requestvehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		return response;
	}
	
	@POST
	@Path("/holdVehicleInPlace/")
	@Produces(MediaType.APPLICATION_XML)
	public Response holdVehicleInPlace(VehicleRequest vehiclerequest) {
		Vehicle vehicle = null, requestvehicle;
		String errormessage = null;
		Response response = null;
		
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			requestvehicle.setChngWho(user.getUserID());
			String holdcode = requestvehicle.getHoldReasonCode();
			
			//Work around to store the hold code
			if(holdcode == null) holdcode = requestvehicle.getLineSeries();
			
			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();

			//Find the ASN
			vehicle = new Vehicle(database);
			vehicle = vehicle.getVehicle(requestvehicle.getVIN());
			
			if(vehicle != null) {
				//Update the user id and change date
				vehicle.setChngWho(user.getUserID());
				vehicle.setChngDate(Common.getCurrentDateTime());
			} else {
				vehicle = requestvehicle;
				vehicle.setLocationDatabase(database);
			}
			
			//BayLogic - Hold vehicle in place
			try {
				vehicle = baylogic.HoldVINInPlace(vehicle, holdcode, true);										
			} catch (Exception e) {
				e.printStackTrace();
				errormessage = "Hold vehicle in Place Error: " + e.getMessage();	
			}
		}
		
		//Check for error
		if(errormessage == null) {
			if(vehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("Hold vehicle in place for " + vehicle.getVIN() + " successful.");
				response = Response.ok().entity(vehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}

		return response;
	}
	
	@POST
	@Path("/returnToPlant/")
	@Produces(MediaType.APPLICATION_XML)
	public Response returnToPlant(VehicleRequest vehiclerequest) {
			Vehicle vehicle = null, requestvehicle;
			String errormessage = null, vin = null;
			Response response = null;
		
			//Validate the Vehicle Request
			errormessage = validateVehicleRequest(vehiclerequest);
			if(errormessage == null) {
				User user = vehiclerequest.getUser();
				requestvehicle = vehiclerequest.getVehicle();
				vin = requestvehicle.getVIN();
				
				//Set the optional fields with default values
				if(requestvehicle.getInboundTruckID() == null) requestvehicle.setInboundTruckID("");
				if(requestvehicle.getChngDate() == null) requestvehicle.setChngDate(new Date());

				//Get the ramps information
				Ramp ramp = ramps.get(user.getLocation());
				Database database = ramp.getDatabase();
				BayLogic baylogic = ramp.getBayLogic();
				ramp.getSystemSettings();
				
				//Add a connection to the location database to the request vehicle
				requestvehicle.setLocationDatabase(database);
				
				//VINCheck
				if(requestvehicle.VINCheck(vin)) {
					//Find ASN vehicle information
					vehicle = requestvehicle.getVehicle(vin);

					//ASN found
					if( vehicle != null) {
						System.out.println("found ASN for vehicle " + vin + ".  Now return to plant.");
							
						//Update the vehicle with the request user,time,truck company, and load number
						vehicle.updateVehicle(requestvehicle);

						//BayLogic - Prebay vehicle
						try {
							vehicle = baylogic.ReturnToPlant(vehicle);											
						} catch (Exception e) {
							e.printStackTrace();
							errormessage = "Return to Plant Error: " + e.getMessage();	
						}
					} else {
						//No ASN
						//Will go back to the client to get the Route and Dealer information
						errormessage = "Vehicle not on Lot";
					}
						
				} else {
					//Failed VINCheck
					errormessage = "Invalid VIN";
				}
			}

			//Check for error
			if(errormessage == null) {
				if(vehicle == null) {
					response = Response.status(404).entity("No ASN found").build();
				} else {
					System.out.println("Return to Plant for " + vin + " successful.");
					response = Response.ok().entity(vehicle).build();						
				}
			} else {
				System.out.println("Error:" + errormessage + ".");
				response = Response.status(404).entity(new String(errormessage)).build();
			}

			return response;
	}
	
	@POST
	@Path("/truckIn/")
	@Produces(MediaType.APPLICATION_XML)
	public Response truckIn(VehicleRequest vehiclerequest) {
		Vehicle vehicle = null, requestvehicle;
		String errormessage = null, vin = null;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			vin = requestvehicle.getVIN();
			
			//Set the optional fields with default values
			if(requestvehicle.getInboundTruckID() == null) requestvehicle.setInboundTruckID("");
			if(requestvehicle.getChngDate() == null) requestvehicle.setChngDate(new Date());

			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			SystemSettings systemsettings = ramp.getSystemSettings();
			
			//Add a connection to the location database to the request vehicle
			requestvehicle.setLocationDatabase(database);
			
			//VINCheck
			if(requestvehicle.VINCheck(vin)) {
				//Find ASN vehicle information
				vehicle = requestvehicle.getVehicle(vin);

				//ASN found
				if( vehicle != null) {
					//Make sure the vehicle is not already on the lot
					if(vehicle.getLotCode() != null && 
							(vehicle.getLotCode().equals("L") || vehicle.getLotCode().equals("C") || vehicle.getLotCode().equals("S"))) {
						errormessage = "VIN_ALREADY_ON_LOT";			
					} else {
						System.out.println("found ASN for vehicle " + vin + ".  Now look for a bay location");
						
						//Update the vehicle with the request user,time,truck company, and load number
						vehicle.updateVehicle(requestvehicle);

						//BayLogic - Prebay vehicle
						try {
							vehicle = baylogic.PreBayVehicle(vehicle, eBayArrival.eBay_TruckIn);											
						} catch (Exception e) {
							e.printStackTrace();
							errormessage = "Baying Error: " + e.getMessage();	
						}
					}
				} else {
					//No ASN found
					
					//Check for No ASN Hold logic
					if(systemsettings.getUseNoASNHold()) {			
						//Create a Criteria Hold for the vehicle
						HoldCriteria holdcriteria = new HoldCriteria(database);
						
						//Based on the VIN find the manufacturer
						holdcriteria.setManufacturer(requestvehicle.getManufacturerFromVIN(vin));
						holdcriteria.setVIN(vin);
						holdcriteria.setHoldCode("NA");
						holdcriteria.setChangWho("System");
						holdcriteria.setChangeDate(new Date());
						holdcriteria.Save();
						
						//BayLogic - AddVIN
						try {
							vehicle = baylogic.AddVIN(requestvehicle, eBayArrival.eBay_TruckIn);												
						} catch (Exception e) {
							e.printStackTrace();
							errormessage = e.getMessage();							
						}

					} else {
						//No ASN
						//Will go back to the client to get the Route and Dealer information
						errormessage = "No ASN found";
					}
				}	
			} else {
				//Failed VINCheck
				errormessage = "Invalid VIN";
			}
		}

		//Check for error
		if(errormessage == null) {
			if(vehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("found location for " + vin + ".");
				response = Response.ok().entity(vehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}

		return response;
	}

	@POST
	@Path("/truckOut/")
	@Produces(MediaType.APPLICATION_XML)
	public Response truckOut(VehicleRequest vehiclerequest) {
		Vehicle vehicle = null, requestvehicle;
		String errormessage = null, vin = null;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			vin = requestvehicle.getVIN();
			
			//Set the optional fields with default values
			if(requestvehicle.getInboundTruckID() == null) requestvehicle.setInboundTruckID("");
			if(requestvehicle.getChngDate() == null) requestvehicle.setChngDate(new Date());

			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			ramp.getSystemSettings();
			
			//Add a connection to the location database to the request vehicle
			requestvehicle.setLocationDatabase(database);
			
			//VINCheck
			if(requestvehicle.VINCheck(vin)) {
				//Find ASN vehicle information
				vehicle = requestvehicle.getVehicle(vin);

				//ASN found
				if( vehicle != null) {
					System.out.println("found ASN for vehicle " + vin + ".  Now Truck out.");
						
					//Update the vehicle with the request user,time,truck company, and load number
					vehicle.updateVehicle(requestvehicle);

					//BayLogic - Truck out vehicle
					try {
						vehicle = baylogic.TruckOut(vehicle);											
					} catch (Exception e) {
						e.printStackTrace();
						errormessage = "TruckOut Error: " + e.getMessage();	
					}
				} else {
					//No ASN
					//Will go back to the client to get the Route and Dealer information
					errormessage = "Vehicle not on Lot";
				}
					
			} else {
				//Failed VINCheck
				errormessage = "Invalid VIN";
			}
		}

		//Check for error
		if(errormessage == null) {
			if(vehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("Truck out for " + vin + " successful.");
				response = Response.ok().entity(vehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}

		return response;
	}

	@POST
	@Path("/inboundDropZone/")
	@Produces(MediaType.APPLICATION_XML)
	public Response inboundDropZone(VehicleRequest vehiclerequest) {
		Vehicle vehicle = null, requestvehicle = null;
		String errormessage = null;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			
			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			ramp.getSystemSettings();
			requestvehicle.setLocationDatabase(database);
			
			//Find an ASN
			String vin = requestvehicle.getVIN();
			vehicle = requestvehicle.getVehicle(vin);
			if(vehicle != null) {
				vehicle.updateVehicle(requestvehicle);
			} else {
				vehicle = requestvehicle;
			}

			//BayLogic - UpdateVIN
			try {
				vehicle = baylogic.UpdateVIN(vehicle);
				
				//Now reset the location so it will not appear in inventory
				vehicle.setLotCode("");
				vehicle.setZone("");
				vehicle.setRow("");
				vehicle.setSpot(null);
			} catch (Exception e) {
				e.printStackTrace();
				errormessage = "Error: " + e.getMessage();							
			}			
		}
		
		//Check for error
		if(errormessage == null) {
			if(vehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("found location for " + vehicle.getVIN() + ".");
				response = Response.ok().entity(vehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		return response;
	}
	
	@POST
	@Path("/updateVIN/")
	@Produces(MediaType.APPLICATION_XML)
	public Response updateVIN(VehicleRequest vehiclerequest) {
		Vehicle vehicle = null, requestvehicle = null;
		String errormessage = null;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			
			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			ramp.getSystemSettings();
			requestvehicle.setLocationDatabase(database);
			
			//Find an ASN
			String vin = requestvehicle.getVIN();
			vehicle = requestvehicle.getVehicle(vin);
			if(vehicle != null) {
				vehicle.updateVehicle(requestvehicle);
			} else {
				vehicle = requestvehicle;
			}

			//BayLogic - UpdateVIN
			try {
				vehicle = baylogic.UpdateVIN(vehicle);
			} catch (Exception e) {
				e.printStackTrace();
				errormessage = "Error: " + e.getMessage();							
			}			
		}
		
		//Check for error
		if(errormessage == null) {
			if(vehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("found location for " + vehicle.getVIN() + ".");
				response = Response.ok().entity(vehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		return response;
	}
	
	@POST
	@Path("/reassign/")
	@Produces(MediaType.APPLICATION_XML)
	public Response reassign(VehicleRequest vehiclerequest) {
		Vehicle requestvehicle = null, vehicle = null;
		String errormessage = null;
		Response response = null;
	
		//Validate the Vehicle Request
		errormessage = validateVehicleRequest(vehiclerequest);
		if(errormessage == null) {
			User user = vehiclerequest.getUser();
			requestvehicle = vehiclerequest.getVehicle();
			
			//Get the ramps information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			BayLogic baylogic = ramp.getBayLogic();
			
			//Find an ASN
			vehicle = new Vehicle(database);
			vehicle = vehicle.getVehicle(requestvehicle.getVIN());
			
			if(vehicle != null) {
				vehicle.updateVehicle(requestvehicle);
			} else {
				vehicle = requestvehicle;
			}
			
			//BayLogic - ReAssign
			try {
				vehicle = baylogic.ReAssignVehicle(vehicle);
			} catch (Exception e) {
				e.printStackTrace();
				errormessage = "Error: " + e.getMessage();							
			}			
		}
		
		//Check for error
		if(errormessage == null) {
			if(vehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("found location for " + vehicle.getVIN() + ".");
				response = Response.ok().entity(vehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		return response;
	}
	
	@POST
	@Path("/removeVIN/")
	@Produces(MediaType.APPLICATION_XML)
	public Response removeVIN(VehicleRequest vehiclerequest){
		Vehicle requestvehicle, vehicle;
		String errormessage = null;
		Response response = null;
		User user = vehiclerequest.getUser();
		requestvehicle = vehiclerequest.getVehicle();
		
		//Get the ramps information
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		BayLogic baylogic = ramp.getBayLogic();
		requestvehicle.setLocationDatabase(database);

		//Find an ASN
		String vin = requestvehicle.getVIN();
		vehicle = requestvehicle.getVehicle(vin);
		if(vehicle != null) {
			vehicle.updateVehicle(requestvehicle);
		} else {
			vehicle = requestvehicle;
		}

		//BayLogic - RemoveVINFromLot
		try {
			vehicle = baylogic.RemoveVINFromLot(vehicle);
		} catch (Exception e) {
			e.printStackTrace();
			errormessage = "Error: " + e.getMessage();							
		}	
		
		//Check for error
		if(errormessage == null) {
			if(vehicle == null) {
				response = Response.status(404).entity("No ASN found").build();
			} else {
				System.out.println("found location for " + vehicle.getVIN() + ".");
				response = Response.ok().entity(vehicle).build();						
			}
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		return response;
	}
	// }}
	
	//Helper
	// {{
	private String validateVehicleRequest(VehicleRequest vehiclerequest) {
		String errormessage = null;
		
		//Validate the request object
		if(vehiclerequest == null) {
			errormessage ="Empty Vehicle Request";
		} else {
			//Check the user object for null
			User user = vehiclerequest.getUser();
			if(user == null) {
				errormessage = "User missing from Vehicle Request";
			} else if(user.getLocation() == null) {
				errormessage = "Location missing from Vehicle Request";
			} else {
				//Check the vehicle object for null
				Vehicle requestvehicle = vehiclerequest.getVehicle();
				if(requestvehicle == null) {
					errormessage = "Vehicle missing from Vehicle Request";					
				} else {
					//Check the user object for the necessary fields
					if(user.getUserID() == null) {
						errormessage = "UserID missing from Vehicle Request";
					} else if(user.getLocation() == null) {
						errormessage = "Current location is missing from Vehicle Request";
					} else {
						//Check the vehicle object for the necessary fields
						if(requestvehicle.getVIN() == null) {
							errormessage = "VIN missing from vehicle request";
						} else if(requestvehicle.getInboundSCAC() == null && requestvehicle.getOutboundSCAC() == null) {
							//Don't worry about SCAC if rail
							if(requestvehicle.getBayArrival() != null && requestvehicle.getBayArrival().equals(eBayArrival.eBay_Rail)) {
								//Rail so SCAC may not be known
							} else if(requestvehicle.getArrivalDate() != null) {
								//Vehicle is already on the lot so don't worry about the SCAC
							} else {
								errormessage = "SCAC missing from Vehicle Request";
							}
						} else if(requestvehicle.getChngWho() == null) {
							errormessage = "Vehicle chngWho missing from Vehicle Request";
						}							
						
						//For Truck in need an inbound truck company
						if(requestvehicle.getActionCode() != null && requestvehicle.getActionCode() == Constants.ACTION_TRUCK_IN) {
							if(requestvehicle.getInboundTruckCo() == null) {
								errormessage = "Truck Company missing from Vehicle Request";
							}
						}
					}
				}
			}
		}
		return errormessage;
	}
	// }}
}
