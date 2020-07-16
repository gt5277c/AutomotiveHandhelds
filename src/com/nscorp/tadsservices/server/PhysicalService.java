package com.nscorp.tadsservices.server;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Physical;
import com.nscorp.tadsservices.model.PhysicalDetail;
import com.nscorp.tadsservices.model.Ramp;
import com.nscorp.tadsservices.model.User;

@Path("/PhysicalService")
public class PhysicalService {

	private Map<String,Ramp> ramps = null;
	//Startup
	public PhysicalService() {
	}

	public PhysicalService(Map<String,Ramp> ramps, String env, URL authurl) {
		this.ramps = ramps;		
	}
	
	public void setRamps(Map<String,Ramp> ramps) { this.ramps = ramps; }
	public void setEnv(String env) { }
	public void setAuthURL(URL authurl) { }

	@GET
	@Path("/")
	public String baseHelloPhysical() {
		return "Hello Physical!";
	}
	
	@POST
	@Path("/getActivePhysicals/")
	@Produces(MediaType.APPLICATION_XML)
	public List<Physical> getOpenPhysicals(PhysicalRequest physicalrequest) throws SQLException{
		//Get the request information
		User user = physicalrequest.getUser();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		Physical physical = new Physical(database);
		List<Physical> physicals = physical.GetAllActivePhysicals();
		
		return physicals;
	}
	
	@POST
	@Path("/getPhysicalDetails/")
	@Produces(MediaType.APPLICATION_XML)
	public List<PhysicalDetail> getPhysicalDetails(PhysicalRequest physicalrequest) throws SQLException{
		//Get the request information
		User user = physicalrequest.getUser();
		PhysicalDetail physicaldetail = physicalrequest.getPhysicalDetail();
		
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		physicaldetail.setLocationDatabase(database);
		List<PhysicalDetail> physicaldetails = physicaldetail.getPhysicalDetail(physicaldetail.getCode());
		
		return physicaldetails;
	}
	
	@POST
	@Path("/logPhysical/")
	@Produces(MediaType.APPLICATION_XML)
	public Response logPhysical(PhysicalRequest physicalrequest) {
		Response response = null;
		String errormessage = null;
		
		//Get the request information
		User user = physicalrequest.getUser();
		PhysicalDetail physicaldetail = physicalrequest.getPhysicalDetail();
				
		Ramp ramp = ramps.get(user.getLocation());
		Database database = ramp.getDatabase();
		
		//Add the database to the physical detail and save
		physicaldetail.setLocationDatabase(database);
		
		try {
			physicaldetail.Save();
		} catch(Exception e) {
			errormessage = "" + e.getMessage();
		}
		
		//Check for error
		if(errormessage == null) {
			System.out.println("Physica detail for  " + physicaldetail.getVIN() + " updated");
			response = Response.ok().entity(physicaldetail).build();	
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}

	@POST
	@Path("/performPhysical/")
	@Produces(MediaType.APPLICATION_XML)
	public Response performPhysical(PhysicalRequest physicalrequest) {
		Response response = null;
		String errormessage = null;
		boolean MovePhysicalVIN;
		
		//Get the request information
		User user = physicalrequest.getUser();
		MovePhysicalVIN = physicalrequest.getMovePhysicalVIN();
		PhysicalDetail physicaldetail = physicalrequest.getPhysicalDetail();

		//Get the ramp information
		Ramp ramp = ramps.get(user.getLocation());
		BayLogic baylogic = ramp.getBayLogic();
		
		//Make sure the physical detail has information needed
		if(physicaldetail == null || physicaldetail.getScanLot() == null ||
		   physicaldetail.getScanZone() == null ||
		   physicaldetail.getScanRow() == null) {
			
			errormessage = "Scan detail missing";
		} else {
			try {
				if(physicaldetail.getScanLot().equals(Constants.TRUCK_LOT)){
					physicaldetail = baylogic.physicalTruckVIN(physicaldetail,MovePhysicalVIN);
				} else {
					physicaldetail = baylogic.physicalVIN(physicaldetail,MovePhysicalVIN);
				}
				
			} catch(NullPointerException npe) {
				errormessage = "Null pointer";
				npe.printStackTrace();
			}catch(Exception e) {
				errormessage = e.getMessage();
			}			
		}
		
		//If there is a "VIN Mismatch" then set the error message
		if(physicaldetail.getMessage() != null && physicaldetail.getMessage().equals("VIN Mismatch")) errormessage = "VIN Mismatch";
		
		//Check for error
		if(errormessage == null) {
			System.out.println("Physica detail for  " + physicaldetail.getVIN() + " updated");
			response = Response.ok().entity(physicaldetail).build();	
		} else {
			System.out.println("Error:" + errormessage + ".");
			response = Response.status(404).entity(new String(errormessage)).build();
		}
		
		return response;
	}
}
