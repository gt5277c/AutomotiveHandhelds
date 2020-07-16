package com.nscorp.tadsservices.server;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Enums.eRailObjAction;
import com.nscorp.tadsservices.model.RSAAudit;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.Ramp;
import com.nscorp.tadsservices.model.Seal;
import com.nscorp.tadsservices.model.User;
import com.nscorp.tadsservices.model.Vehicle;
import com.nscorp.tadsservices.model.SearchCriteria.RailspotSearchCriteria;

@Path("/RailcarService")
public class RailcarService {

	private Map<String,Ramp> ramps = null;
	//Startup
	public RailcarService() {
	}
	
	public RailcarService(Map<String,Ramp> ramps, String env, URL authurl) {
		this.ramps = ramps;		
	}
	
	public void setRamps(Map<String,Ramp> ramps) { this.ramps = ramps; }
	public void setEnv(String env) { }
	public void setAuthURL(URL authurl) { }

	@GET
	@Path("/")
	public String baseHelloRailcar() {
		return "Hello Railcar!";
	}
	
	@POST
	@Path("/changeRailspot/")
	@Produces(MediaType.APPLICATION_XML)
	public Response changeRailspot(RailcarRequest railcarrequest) {
		Response response = null;
		String errormessage = null;
		String railcar,manf,route,action;
		
		//Get the request information
		User user = railcarrequest.getUser();
		Railspot railspot = railcarrequest.getRailspot();
		railcar = railspot.getRailcarNumber();
		manf = railspot.getMfg();
		route = railspot.getRoute();
		action = railspot.getActionCode();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		//Find the railspot and update
		railspot.setLocationDatabase(database);
		railspot = railspot.GetRailspot(railcar);
		
		if(railspot != null) {
			railspot.setRailcarNumber(railcar);
			railspot.setMfg(manf);
			railspot.setRoute(route);
			railspot.setActionCode(action);
			railspot.setChngDate(Common.getCurrentDateTime());
			railspot.setChngWho(user.getUserID());
			railspot.Save();
		} else {
			errormessage = "Unable to find railcar " + railcar;
		}
		
		//Check for error
		if(errormessage == null) {
			System.out.println("Railcar " + railcar + " updated");
			response = Response.ok().entity(railspot).build();	
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
	
	@POST
	@Path("/clearRailspot/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Railspot> clearRailspot(RailcarRequest railcarrequest) {
		List<Railspot> clearedspots = new ArrayList<Railspot>();
		String errormessage = null;
		String Lot, Zone, Track;
		int Spot;
		
		//Get the request information
		User user = railcarrequest.getUser();
		Railspot railspot = railcarrequest.getRailspot();
		
		//Make sure the user id is set on the railspot
		railspot.setChngWho(user.getUserID());
		
		Ramp ramp = ramps.get(user.getLocation());
		BayLogic baylogic = ramp.getBayLogic();
		
		//Get the range of spots to be cleared
		Lot = railspot.getLotCode();
		Zone = railspot.getZoneID();
		Track = railspot.getTrackNbr();
		Spot = railspot.getSpot();
		
		try {
			if((Lot != null && Lot.length() > 0) &&
					(Zone != null && Zone.length() > 0) &&
					(Track != null && Track.length() > 0) && 
					(Spot > 0)){
				clearedspots = baylogic.ClearRail(Lot, Zone, Track, Spot);
			} else if((Lot != null && Lot.length() > 0) &&
					(Zone != null && Zone.length() > 0) &&
					(Track != null && Track.length() > 0)) {
				clearedspots = baylogic.ClearRail(Lot, Zone, Track);
			} else if((Lot != null && Lot.length() > 0) &&
					(Zone != null && Zone.length() > 0)) {
				clearedspots = baylogic.ClearRail(Lot, Zone);
			} else {
				//Error - need at least Lot and Zone
				errormessage = "Need a Lot and Zone to clear tracks.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Check for error
		if(errormessage == null) {
			System.out.println("Cleared rail.");
		} else {
			System.out.println("Error:" + errormessage + ".");
		}
		
		return clearedspots;
	}
	
	@POST
	@Path("/getRailcar/")
	@Produces(MediaType.APPLICATION_XML)
	public Railspot getRailcar(RailcarRequest railcarrequest) {
		//Get the request information
		User user = railcarrequest.getUser();
		Railspot railspot = railcarrequest.getRailspot();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		Railspot searchrailspot = new Railspot(database);		
		railspot = searchrailspot.GetRailspot(railspot.getRailcarNumber());
		
		return railspot;
	}
	
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
	@Path("/getSealInfo/")
	@Produces(MediaType.APPLICATION_XML)
	public Response getSealInfo(RailcarRequest railcarrequest) {
		String errormessage = null;
		Response response = null;
		Railspot railspot;
		
		//Get the request information
		User user = railcarrequest.getUser();
		railspot = railcarrequest.getRailspot();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		//Use railspot to find the inbound waybill
		Seal seal = new Seal(database);
		seal.setRailcar(railspot.getRailcarNumber());
		seal.setWaybill(railspot.getInboundWayBill());
		seal = seal.findNewest();
		
		if(seal == null) {
			errormessage = "Unable to find Seal";
		}

		//Check for error
		if(errormessage == null) {
			System.out.println("Found seal information");
			response = Response.ok().entity(seal).build();	
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
	
	@POST
	@Path("/loadRailcar/")
	@Produces(MediaType.APPLICATION_XML)
	public Response loadRailcar(RailcarRequest railcarrequest) {
		String errormessage = null;
		Response response = null;
		Vehicle vehicle, requestvehicle;
		
		//Get the request information
		User user = railcarrequest.getUser();
		requestvehicle = railcarrequest.getVehicle();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		BayLogic baylogic = ramp.getBayLogic();
		vehicle = new Vehicle(database);
		
		try {
			//Look up the vehicle
			vehicle = vehicle.getVehicle(requestvehicle.getVIN());
			
			if(vehicle != null) {
				vehicle.updateVehicle(requestvehicle);
			
				baylogic.LoadRailcar(vehicle);
			} else {
				errormessage = "No ASN found";
			}
		} catch (Exception e) {
			errormessage = "Unable to load vehicle: " + e.getMessage();
			e.printStackTrace();
		}
		
		//Check for error
		if(errormessage == null) {
			System.out.println("Vehicle loaded ");
			response = Response.ok().entity(vehicle).build();	
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
		
	@POST
	@Path("/performRSAAudit/")
	@Produces(MediaType.APPLICATION_XML)
	public Response performRSAAudit(RailcarRequest railcarrequest) {
		String errormessage = null;
		Response response = null;
		Vehicle vehicle = null;
		try {
			//Get the request information
			User user = railcarrequest.getUser();
			vehicle = railcarrequest.getVehicle();
			railcarrequest.getRailspot();
			
			//Get the ramp information
			Ramp ramp = ramps.get(user.getLocation());
			Database database = ramp.getDatabase();
			
			//Create and perform the RSA Audit
			RSAAudit audit = new RSAAudit(database);
			audit.LogAudit(vehicle.getVIN(), vehicle.getOutboundRailcarNumber(), user.getUserID(), Common.getCurrentDateTime());
		} catch(Exception e) {
			e.printStackTrace();
			errormessage = "Unable to perform RSAAudit";
		}
		
		//Check for error
		if(errormessage == null) {
			System.out.println("Railship Approve Audit logged");
			response = Response.ok().entity(vehicle).build();	
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
	
	@POST
	@Path("/spotRailcar/")
	@Produces(MediaType.APPLICATION_XML)
	public Response spotRailcar(RailcarRequest railcarrequest){
		String errormessage = null;
		Response response = null;
		
		//Get the request information
		User user = railcarrequest.getUser();
		Railspot railspot = railcarrequest.getRailspot();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		BayLogic baylogic = ramp.getBayLogic();
		railspot.setLocationDatabase(database);
		railspot.setChngWho(user.getUserID());
		
		try {
			railspot = baylogic.SpotRailcar(railspot);
		} catch (Exception e) {
			errormessage = e.getMessage();
			e.printStackTrace();
		}
		
		//Check for error
		if(errormessage == null) {
			System.out.println("Railcar spotted");
			response = Response.ok().entity(railspot).build();	
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
	
	@POST
	@Path("/unloadRailcar/")
	@Produces(MediaType.APPLICATION_XML)
	public Response unloadRailcar(RailcarRequest railcarrequest) {
		String errormessage = null;
		Response response = null;
		Vehicle vehicle, requestvehicle;
		
		//Get the request information
		User user = railcarrequest.getUser();
		requestvehicle = railcarrequest.getVehicle();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		BayLogic baylogic = ramp.getBayLogic();
		vehicle = new Vehicle(database);
		
		try {
			//Look up the vehicle
			vehicle = vehicle.getVehicle(requestvehicle.getVIN());
			
			if(vehicle != null) {
				vehicle.updateVehicle(requestvehicle);
			
				List<Vehicle> vehicles = new ArrayList<Vehicle>();
				vehicles.add(vehicle);
				vehicles = baylogic.RemoveFromRailcar(vehicles);
				if(vehicles != null) vehicle = vehicles.get(0);
			} else {
				errormessage = "No ASN found";
			}
		} catch (Exception e) {
			errormessage = "Unable to unload vehicle: " + e.getMessage();
			e.printStackTrace();
		}
		
		//Check for error
		if(errormessage == null) {
			System.out.println("Vehicle unloaded ");
			response = Response.ok().entity(vehicle).build();	
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
	
}
