package com.nscorp.tadsservices.server;

import java.util.List;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Embargo;
import com.nscorp.tadsservices.model.Enums;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.Ramp;
import com.nscorp.tadsservices.model.Route;
import com.nscorp.tadsservices.model.Vehicle;
import com.nscorp.tadsservices.model.SearchCriteria.VehicleSearchCriteria;

public class RailcarLogic {

	private Database database;
	private String ChngWho;
	
	// }}
	
	//Constructor
	public RailcarLogic(Ramp ramp) {
		//Get Ramp database connection
		database = ramp.getDatabase();
		
		ramp.getSystemSettings();
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
		public String getChngWho() { return ChngWho; }
		public void setChngWho(String ChngWho) { this.ChngWho = ChngWho; }
	// }}
	
	////////////////////////////////////////////////////////////
	//Railcar Logic
	////////////////////////////////////////////////////////////
	public void SpotRailcar(Railspot railspot) throws Exception {
		SpotRailcar(railspot,true);
	}
	
	public void SpotRailcar(Railspot railspot, boolean updateMainframe) throws Exception {
		Route m_route = new Route(database);
		Vehicle vehicle = new Vehicle(database);
		Embargo embargo = new Embargo(database);
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		
		List<Vehicle> vehicles = null;
		Route route = null;
		String railspotStatus;
		
		if(railspot != null) {
			//Get any vehicles on the railcar
			if(railspot.getRailcarNumber() != null) {
				vsc.setRailcarNumber(railspot.getRailcarNumber());
				vehicles = vehicle.getVehicleList(vsc);
			}
			
			//Get the railspot status
			railspotStatus = railspot.getStatusCode();
			
			//Check to make sure user entered a mfg and route using the railspot object
			if(railspot.getMfg() != null && railspot.getRoute() != null) {
				//Get the Route information
				route = m_route.getRoute(railspot.getMfg(), railspot.getRoute());
				
				//If the route was not found then clear the information from the railspot
				if(route == null) {
					railspot.setRoute("");
					railspot.setOutboundSCAC("");
					railspot.setOutboundFSAC("");
				}
			} else if(railspot.getMfg() == null &&
					 (railspotStatus.equals(Constants.STAT_RAIL_RAILSHIPPED) ||
					  railspotStatus.equals(Constants.STAT_RAIL_ARRIVEDRAIL) ||
					  railspotStatus.equals(Constants.STAT_RAIL_INSUPPORTYARD) ||
					  railspotStatus.equals(Constants.STAT_RAIL_LOADING) || 
					  railspotStatus.equals(Constants.STAT_RAIL_ASSIGNED) ||
					  railspotStatus.equals(Constants.STAT_RAIL_ERROR))) {
				//Manufacturer is required for these statuses
				throw new Exception("MANF_REQUIRED_ON_RAILCAR");
			} else if(railspot.getRoute() == null && 
					(railspotStatus.equals(Constants.STAT_RAIL_RAILSHIPPED) ||
					 railspotStatus.equals(Constants.STAT_RAIL_LOADING))) {
				//Route is required for these statuses
				throw new Exception("ROUTE_REQUIRED_ON_RAILCAR");
			} else {
				//Manufacturer and Route not provided so clear the information from the railspot
				railspot.setRoute("");
				railspot.setOutboundSCAC("");
				railspot.setOutboundFSAC("");
			}
					
			//Check to see if the railcar status needs to be updated
			if(route != null && railspotStatus.equals(Constants.STAT_RAIL_PARKED)) railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
			
			if(railspot.getLotCode() != null ) {
				
				if(railspot.getLotCode().equals(Constants.TRACK_LOT)) {
					if(railspotStatus == null) {
						if(vehicles != null && vehicles.size() > 0) {
							railspot.setStatusCode(Constants.STAT_RAIL_ARRIVEDRAIL);
							railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
						} else {
							railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
							railspot.setLoadEmptyStatus(Constants.LE_STATUS_EMPTY);
						}
					} else if(railspotStatus.equals(Constants.STAT_RAIL_EMPTY)) {
						if(vehicles != null && vehicles.size() > 0) {
							railspot.setStatusCode(Constants.STAT_RAIL_ARRIVEDRAIL);
							railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
						}
					} else if(railspotStatus.equals(Constants.STAT_RAIL_PARKED)) {
						railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
					} else if(railspotStatus.equals(Constants.STAT_RAIL_ARRIVEDRAIL)) {
						//If the railcar is empty then mark the status as empty
						if(railspot.getLoadEmptyStatus().equals(Constants.LE_STATUS_EMPTY)) {
							railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
						}
					}
				} else if(railspot.getLotCode().equals(Constants.SUPPORT_TRACK_LOT)){
					if(railspotStatus == null) {
						if(vehicles != null && vehicles.size() > 0 ){
							railspot.setStatusCode(Constants.STAT_RAIL_INSUPPORTYARD);
							railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
						} else {
							railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
							railspot.setLoadEmptyStatus(Constants.LE_STATUS_EMPTY);							
						}
					} else if(railspotStatus.equals(Constants.STAT_RAIL_EMPTY)) {
						if(vehicles != null && vehicles.size() > 0 ) {
							railspot.setStatusCode(Constants.STAT_RAIL_INSUPPORTYARD);
							railspot.setLoadEmptyStatus(Constants.LE_STATUS_EMPTY);
						}
					} else if(railspotStatus.equals(Constants.STAT_RAIL_PARKED)) {
						railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
					}
				}
			}
			
			//Set the Inbound information to values from the first vehicle on the railcar.
			if(vehicles != null && vehicles.size() > 0) {
				Vehicle fv = vehicles.get(0);
				ChngWho = fv.getChngWho();
				railspot.setMfg(fv.getManufacturer());
				railspot.setInboundBOL(fv.getInboundBOL());
				railspot.setInboundSCAC(fv.getInboundSCAC());
				railspot.setDepartureDate(fv.getDepartureDate());
				railspot.setInboundWayBill(fv.getInboundWayBill());
				railspot.setInboundWayBillSN(fv.getInboundWayBillSN());
				railspot.setLTD(fv.getLTD());
				if(railspot.getLTD()) railspot.setRoute(fv.getLTDRoute());
			}
			
			//Save the railcar data
			railspot.setChngWho(ChngWho);
			railspot.setChngDate(Common.getCurrentDateTime());
			
			//Check to see if there is an Embargo record for this railcar
			Embargo em = embargo.get(railspot.getRailcarNumber());
			if(em != null) {
				railspot.setOutboundEmbargo(em.getEmbargo());
				railspot.setOutboundPermit(em.getPermit());
			}
			
			//Update Mainframe
			if(updateMainframe) {
				if(railspot.getLotCode().equals(Constants.TRACK_LOT) && railspot.getLotCode() != railspot.getPrevLotCode()) {
					railspot.setActionCode(Constants.RC_ACTION_SPOTTED_RAILCAR);
					railspot.Save(false);
					String originalstatus = railspot.getStatusCode();
					railspot.setStatusCode(Constants.STAT_RAIL_PACT);
					railspot.setActionCode(Constants.RC_ACTION_GENERATE_PACT);
					railspot.SaveHistory();
					railspot.setStatusCode(originalstatus);
					//Generate mainframe message
					//m_oMFTransactions.GenRailcarPlacementMsg oRailSpot
				} else {
					//Generate mainframe message
					//m_oMFTransactions.GenRailcarMoveMsg oRailSpot
					railspot.Save(false);
				}
			} else {
				railspot.setActionCode(Constants.RC_ACTION_SPOTTED_RAILCAR);
				railspot.Save(false);
			}
			
			//Generate the arrival records for the VINs
			railspot = GenerateArrival(railspot);
		}
	}

	//Generate arrival records for all VINs on the railcar
	private Railspot GenerateArrival(Railspot railspot) throws Exception {
		List<Vehicle> vehicles;
		Vehicle vehicle = new Vehicle(database);
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();

		//Check to see whether to use the Inbound or Outbound railcar
		String railspotStatus = railspot.getStatusCode();
		
		if(railspotStatus.equals(Constants.STAT_RAIL_RAILSHIPPED) || railspotStatus.equals(Constants.STAT_RAIL_LOADING)) {
			//Outbound railcar
			vsc.setSearchType(Enums.eVINObjAction.eVINFindOutboundRailCar);
			vsc.setOutboundRailcarNumber(railspot.getRailcarNumber());
			vehicles = vehicle.getVehicleList(vsc);
		} else if(railspotStatus.equals(Constants.STAT_RAIL_ARRIVEDRAIL) ||
				  railspotStatus.equals(Constants.STAT_RAIL_INSUPPORTYARD) ||
				  railspotStatus.equals(Constants.STAT_RAIL_EMPTY)) {
			//Inbound railcar
			vsc.setSearchType(Enums.eVINObjAction.eVINFindInboundRailCar);
			vsc.setInboundRailcarNumber(railspot.getRailcarNumber());
			vehicles = vehicle.getVehicleList(vsc);
			if(vehicles == null) {
				//No VINs found on railcar, so get ASNs
				vsc.setSearchType(Enums.eVINObjAction.eVINFindRailCarASN);
				vehicles = vehicle.getVehicleList(vsc);
			}
		} else {
			//Inbound railcar
			vsc.setSearchType(Enums.eVINObjAction.eVINFindInboundRailCar);
			vsc.setInboundRailcarNumber(railspot.getRailcarNumber());
			vehicles = vehicle.getVehicleList(vsc);
		}
				
		//'Loop through each VIN and update location and generate arrival record if needed
		for(Vehicle v : vehicles) {
			if(v.getStatusCode() == null || 
			   v.getStatusCode().equals(Constants.STAT_RAILSHIP_APPROVED) ||
			   v.getStatusCode().equals(Constants.STAT_ARRIVED_RAIL) ||
			   v.getStatusCode().equals(Constants.STAT_LOADED) ||
			   v.getStatusCode().equals(Constants.STAT_ARRIVED_SUPPORT_YARD)) {
				
				//Update inbound train id and event time if needed
				if(v.getLotCode() == null || v.getLotCode().length() < 1) {
					v.setInboundTrainID(railspot.getInboundTrainID());
					v.setInboundEventTime(railspot.getInboundTrainEvent());
				}
				
				//Set VINs location to railcars location
				v.setLotCode(railspot.getLotCode());
				v.setZone(railspot.getZoneID());
				v.setRow(railspot.getTrackNbr());
				v.setSpot(railspot.getSpot());
				
				//Check to see if VIN status needs updated
				if(railspot.getLotCode().equals(Constants.TRACK_LOT)) {
					//Railcar is in the track lot, so check to see if it was previously in the support yard
					if(v.getStatusCode() == null || v.getStatusCode().equals(Constants.STAT_ARRIVED_SUPPORT_YARD)) {
						v.setStatusCode(Constants.STAT_ARRIVED_RAIL);
						v.setArrivalDate(Common.getCurrentDateTime());
					}
				} else if(railspot.getLotCode().equals(Constants.SUPPORT_TRACK_LOT)) {
					//Railcar is in the support yard, so check to see if it was previously in the track lot
					if(v.getStatusCode() == null || v.getStatusCode().equals(Constants.STAT_ARRIVED_RAIL)) {
						v.setStatusCode(Constants.STAT_ARRIVED_SUPPORT_YARD);
					}
				}
				v.setChngWho(ChngWho);
				v.setChngDate(Common.getCurrentDateTime());
				v.setVINMoving(false);
				v.Save(Enums.eVINSave.eNoVINCheck);
			}
		}
		
		return railspot;
	}
}
