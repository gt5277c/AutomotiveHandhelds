package com.nscorp.tadsservices.server;

import com.nscorp.tadsservices.model.Enums.eBayArrival;
import com.nscorp.tadsservices.model.Enums.eHoldCriteriaObjAction;
import com.nscorp.tadsservices.model.Enums.eHoldObjAction;
import com.nscorp.tadsservices.model.Enums.eVINObjAction;
import com.nscorp.tadsservices.model.Enums.eVINSave;
import com.nscorp.tadsservices.model.SearchCriteria.HoldCriteriaSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.HoldSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.QuotaSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.RailspotSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.RouteSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.UpfitterSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.VehicleSearchCriteria;
import com.nscorp.tadsservices.model.XML.Event;
import com.nscorp.tadsservices.model.XML.INVDispMessage;
import com.nscorp.tadsservices.model.XML.RCDISPPACT;
import com.nscorp.tadsservices.model.XML.RcdispDelt;
import com.nscorp.tadsservices.model.XML.RcdispInvl;
import com.nscorp.tadsservices.model.XML.TruckInMessage;
import com.nscorp.tadsservices.model.XML.TruckOutMessage;
import com.nscorp.tadsservices.model.Enums.eRailObjAction;
import com.nscorp.tadsservices.model.Enums.eRouteObjAction;
import com.nscorp.tadsservices.model.Enums.eUpfitter;
import com.nscorp.tadsservices.model.BadASN;
import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Dealer;
import com.nscorp.tadsservices.model.Embargo;
import com.nscorp.tadsservices.model.Enums;
import com.nscorp.tadsservices.model.Hold;
import com.nscorp.tadsservices.model.HoldCriteria;
import com.nscorp.tadsservices.model.Location;
import com.nscorp.tadsservices.model.OutboundJournal;
import com.nscorp.tadsservices.model.Overflow;
import com.nscorp.tadsservices.model.OverflowItem;
import com.nscorp.tadsservices.model.PhysicalDetail;
import com.nscorp.tadsservices.model.PreloadDetail;
import com.nscorp.tadsservices.model.ProdStat;
import com.nscorp.tadsservices.model.Quota;
import com.nscorp.tadsservices.model.RSAAudit;
import com.nscorp.tadsservices.model.Railcar;
import com.nscorp.tadsservices.model.Railspot;
import com.nscorp.tadsservices.model.Ramp;
import com.nscorp.tadsservices.model.Route;
import com.nscorp.tadsservices.model.SortOrderItem;
import com.nscorp.tadsservices.model.SpecDealer;
import com.nscorp.tadsservices.model.SpecRoute;
import com.nscorp.tadsservices.model.SystemSettings;
import com.nscorp.tadsservices.model.Upfitter;
import com.nscorp.tadsservices.model.Vehicle;
import com.nscorp.tadsservices.model.VehicleType;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BayLogic{
	
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private SystemSettings systemSettings;
	private Database database;
	
	// Ramp related information
	//private List<VehicleType> vehicletypes = null;
	//private List<SpecRoute> specialroutes = null;
	//private List<SpecDealer> specialdealers = null;
	//private List<Route> routes = null;
	//private List<Dealer> dealers = null;
	//private List<Route> ramproutes = null;
	//private List<ProdStat> prodstats = null;
	//private List<Upfitter> upfitters = null;
					
	// Classes used in baying
	private Location m_location;
	private Vehicle m_vehicle;
	private Dealer m_dealer;
	private Route m_route;
	private VehicleType m_vehicletype;
	private Hold m_hold;
	private HoldCriteria m_holdcriteria;
	private Railspot m_railspot;
	private ProdStat m_prodstat;
	private SpecRoute m_specroute;
	private SpecDealer m_specdealer;
	private Upfitter m_upfitter;
	private Overflow m_overflow;
	private SortOrderItem m_sortorderitem;
	private Quota m_quota;
	private BadASN m_badasn;
	private OutboundJournal m_outboundjournal;
	
	// }}
	
	//Constructor
	public BayLogic(Ramp ramp) {
		//Get Ramp database connection
		database = ramp.getDatabase();
		
		//Read the Options from the Ramp
		systemSettings = ramp.getSystemSettings();
		
		//Get Site related information
		//vehicletypes = ramp.getVehicletypes();
		//specialroutes = ramp.getSpecialroutes();
		//specialdealers = ramp.getSpecialdealers();
		//routes = ramp.getRoutes();
		//dealers = ramp.getDealers();
		//ramproutes = ramp.getRamproutes();
		//prodstats = ramp.getProdstats();
		//upfitters = ramp.getUpfitters();
		
		//Create classes used in baying 
		m_location = new Location(database);
		m_vehicle = new Vehicle(database);
		m_dealer = new Dealer(database);
		m_route = new Route(database);
		m_vehicletype = new VehicleType(database);
		m_hold = new Hold(database);
		m_holdcriteria = new HoldCriteria(database);
		m_railspot = new Railspot(database);
		m_prodstat = new ProdStat(database);
		m_specroute = new SpecRoute(database);
		m_specdealer = new SpecDealer(database);
		m_upfitter = new Upfitter(database);
		m_overflow = new Overflow(database);
		m_sortorderitem = new SortOrderItem(database);
		m_quota = new Quota(database);
		m_badasn = new BadASN(database);
		m_outboundjournal = new OutboundJournal(database);
	}
	
	////////////////////////////////////////////////////////////
	//Baying Logic
	////////////////////////////////////////////////////////////
	// {{

	public Vehicle AddVIN(Vehicle vehicle) throws Exception {
		return AddVIN(vehicle, null);
	}
	
	public Vehicle AddVIN(Vehicle vehicle, eBayArrival eArrival) throws Exception {
	    //AddVIN.  This entry point is for handheld processing.
	    //Handheld will need to pass a LotCode to indicate origin of PreBay request.
		
		TruckInMessage tim;
		//if adding to rail, check for railcar on lot
		if(eArrival != null && eArrival == eBayArrival.eBay_Rail) {
			RailspotSearchCriteria rsc = new RailspotSearchCriteria();
			rsc.setSearchType(eRailObjAction.eRailFindRailcarByLot);
			rsc.setLotCode(Constants.TRACK_LOT);
			
			//Make sure vehicle has inbound railcar
			if(vehicle.getInboundRailcarNumber() != null) {
				rsc.setRailcarNumber(vehicle.getInboundRailcarNumber());	
			} else {
				throw(new Exception("RAILCAR_NOT_ON_LOT_ERROR"));
			}
			
			List<Railspot> railspots = m_railspot.GetRailspots(rsc);
			if(railspots == null) throw(new Exception("RAILCAR_NOT_ON_LOT_ERROR"));
		}
		
		//Get the ASN
		Vehicle searchvehicle;
		searchvehicle = vehicle.getVehicle(vehicle.getVIN());
		if(searchvehicle != null) {
			
			//Check to see if the vehicle is already on the Lot
			if(searchvehicle.getLotCode() != null) throw(new Exception("VIN_ON_LOT_ERROR"));
			
			//update the vehicle with the ASN information
			vehicle.updateVehicle(searchvehicle);
		}
		
		if(eArrival != null) {
			switch(eArrival) {
				case eBay_Plant:
				case eBay_TruckIn:
					//Go ahead and prebay vehicle
		            //Prebayvehicle will generate parked record for truck-ins
					vehicle = PreBayVehicle(vehicle,eArrival);
					//Update mainframe
					tim = new TruckInMessage();
					tim.setSystemSettings(systemSettings);
					tim.setVehicle(vehicle);
					m_outboundjournal.setType("TruckIN");
					m_outboundjournal.setData(tim.getXML());
					m_outboundjournal.setChngWho(vehicle.getChngWho());
					m_outboundjournal.setChngDate(Common.getCurrentDateTime());
					m_outboundjournal.setStatus("Q");
					m_outboundjournal.save();
					break;
				case eBay_VINAdd:
					//If lotcode is filled out, user is putting VIN in specific spot, just save it
					if(vehicle.getLotCode() != null) {
						//If the vehicle is on Hold then leave it in Hold status
						if(vehicle.getStatusCode() == null || vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_PARKED);
						vehicle.setArrivalDate(Common.getCurrentDateTime());
						vehicle.setChngDate(Common.getCurrentDateTime());
						vehicle.Save();
						//Update mainframe
						tim = new TruckInMessage();
						tim.setSystemSettings(systemSettings);
						tim.setVehicle(vehicle);
						m_outboundjournal.setType("TruckIN");
						m_outboundjournal.setData(tim.getXML());
						m_outboundjournal.setChngWho(vehicle.getChngWho());
						m_outboundjournal.setChngDate(Common.getCurrentDateTime());
						m_outboundjournal.setStatus("Q");
						m_outboundjournal.save();						
					} else {
						//Add VIN only, need to assign, then park
						vehicle = PreBayVehicle(vehicle, eBayArrival.eBay_VINAdd);
						vehicle.setVINMoving(false);						
					}
					break;
				case eBay_Rail:
					//No processing for this arrival through this method 
					//handheld does assignment
		            //PreBayVehicle will generate inventory record if needed
					//Update the inbound railcar and location
					vehicle.Save();
					break;
				default:
					break;
			}
		}
		
		//Check to see if there are any pending holds on this VIN
		Location vehiclelocation = CheckVINForHoldCriteria(vehicle);
		if(vehiclelocation != null) {
			//Update the vehicle location
			vehicle.updateVehicleLocation(vehiclelocation);
			vehicle.Save();
		}
		
		return vehicle;
	}

	/**Cancels prebay of a single vehicle
	 * Cancel a prebay used from the lot maint
	 * @param Vehicle
	 * @return Vehicle
	 */
	public Vehicle CancelPreBay(Vehicle vehicle) {
		String railcar, chngWho = null;
		
		if(vehicle != null && vehicle.getInboundRailcarNumber() != null) {
			railcar = vehicle.getInboundRailcarNumber();
			if(vehicle.getChngWho() != null) chngWho = vehicle.getChngWho();
			
			//Get railspot object for railcar
			Railspot railspot = m_railspot.GetRailspot(railcar);
			if(railspot != null && railspot.getStatusCode() != null) {
				if(railspot.getStatusCode() == Constants.STAT_RAIL_ASSIGNED) {
					railspot.setChngDate(Common.getCurrentDateTime());
					railspot.setChngWho(chngWho);
					railspot.setStatusCode(Constants.STAT_RAIL_ARRIVEDRAIL);
					railspot.setActionCode(Constants.RC_ACTION_CANCEL_ASSIGNMENT);
					railspot.Save(false);
				}
			}
		
			//Cancel the prebay on the vehicle
			vehicle = CancelVehicle(vehicle);
		}
		
		return vehicle;
	}
	
	/**
	 * Cancel a prebay used from the lot maint
	 * @param List<Vehicle> vehicles
	 * @return List<Vehicle>
	 */
	public List<Vehicle> CancelPreBay(List<Vehicle> vehicles) {
		String railcar = "";
		String chngWho = "";
		Railspot railspot;
		
		//Will update the railcar status for vehicles with cancelled assignments
		for(Vehicle vehicle : vehicles) {
			//Cancel each vehicle on the railcar			
			if(vehicle.getInboundRailcarNumber() != null) {
				if(railcar.length() > 0 && railcar != vehicle.getInboundRailcarNumber()) {
					//Now processing different railcar
					//Get railspot object for railcar that we just finished
					railspot = m_railspot.GetRailspot(railcar);
					
					if(railspot != null && railspot.getStatusCode() != null) {
						if(railspot.getStatusCode() == Constants.STAT_RAIL_ASSIGNED) {
							railspot.setChngDate(Common.getCurrentDateTime());
							railspot.setChngWho(chngWho);
							railspot.setStatusCode(Constants.STAT_RAIL_ARRIVEDRAIL);
							railspot.setActionCode(Constants.RC_ACTION_CANCEL_ASSIGNMENT);
							railspot.Save(false);
						}
					}
				}
				//first time thru, just set variable
				railcar = vehicle.getInboundRailcarNumber();
				if(vehicle.getChngWho() != null) chngWho = vehicle.getChngWho();
			}
			//Cancel the prebay on the vehicle
			vehicle = CancelVehicle(vehicle);
		}
		
		//Process last railcar
		if(railcar.length() > 0) {
			//Get railspot object for railcar
			railspot = m_railspot.GetRailspot(railcar);
			
			if(railspot != null && railspot.getStatusCode() != null) {
				if(railspot.getStatusCode() == Constants.STAT_RAIL_ASSIGNED) {
					railspot.setChngDate(Common.getCurrentDateTime());
					railspot.setChngWho(chngWho);
					railspot.setStatusCode(Constants.STAT_RAIL_ARRIVEDRAIL);
					railspot.setActionCode(Constants.RC_ACTION_CANCEL_ASSIGNMENT);
					railspot.Save(false);
				}
			}
		}
		
		return vehicles;
	}
	
	public Vehicle CancelVehicle(Vehicle vehicle) {
		//Cancel Assignment operation
		//	don't raise error for vehicles that cannot have assignment cancelled.
		
		if(vehicle.getStatusCode() != null && vehicle.getStatusCode() == Constants.STAT_ASSIGNED) {
			//save first with stat canceled
			vehicle.setChngDate(Common.getCurrentDateTime());
			vehicle.setVINMoving(false);
			vehicle.setActionCode(Constants.ACTION_CANCEL_ASSIGNMENT);
			vehicle.setStatusCode(Constants.STAT_CANCEL_ASSIGNED);
			try {
				vehicle.Save();
			} catch (Exception e) {
				//Ignore Vehicle Save error
				e.printStackTrace();
			}
			
			//now save with arrived rail for vehicles on railcar
			if(vehicle.getLotCode() != null && vehicle.getLotCode() == Constants.TRACK_LOT) {
				vehicle.setStatusCode(Constants.STAT_ARRIVED_RAIL);
				try {
					vehicle.Save();
				} catch (Exception e) {
					//Ignore Vehicle Save error
					e.printStackTrace();
				}
			}
		}
		
		return vehicle;
	}
	
	public List<Railspot> ClearRail(String Lot, String Zone) throws Exception {
		return ClearRail(Lot,Zone,null,0);
	}
	
	public List<Railspot> ClearRail(String Lot, String Zone, String Track) throws Exception {
		return ClearRail(Lot,Zone,Track,0);
	}
	
	/**
	 * 'this method can be used whether there are VINs on the railcar or not.
	 * @param Lot
	 * @param Zone
	 * @param Track
	 * @param Spot
	 * @return
	 * @throws Exception 
	 */
	public List<Railspot> ClearRail(String Lot, String Zone, String Track, int Spot) throws Exception {
		List<Railspot> railspots;
		List<Vehicle> vehicles;
		
		RailspotSearchCriteria rsc = new RailspotSearchCriteria();
		rsc.setLotCode(Lot);
		rsc.setZoneID(Zone);
		rsc.setTrackNbr(Track);
		rsc.setSpot(Integer.toString(Spot));
		
		//'determine what level of delete we are doing
		//	Find the railspots to be cleared
		if(Spot > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindSpot);
		} else if(Track != null && Track.length() > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindTrack);
		} else if(Zone != null && Zone.length() > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindZone);
		} else if(Zone != null && Zone.length() < 0) {
			rsc.setSearchType( eRailObjAction.eRailFindRailspotByLot);
		}
		
		railspots = m_railspot.GetRailspots(rsc);
		
		//'check that all VINs in the track lot that are loaded, arrived rail, etc.
		if(railspots != null) {
			for(Railspot railspot : railspots) {
				//'Check that railcar is in proper status for clearing
				if(railspot.getStatusCode() != null &&
					!railspot.getStatusCode().equals(Constants.STAT_RAIL_EMPTY) &&
					!railspot.getStatusCode().equals(Constants.STAT_RAIL_ARRIVEDRAIL) &&
					!railspot.getStatusCode().equals(Constants.STAT_RAIL_INSUPPORTYARD) &&
					!systemSettings.getGenRailcarDelMessage()) {
					
					//'Can only clear railcars marked as arrived rail, 
					//		arrived support yard and empty
					throw new Exception("VIN_STATUS_ERROR");
				}
				
				//' karuzis/start 02/2007 
				//	- if the railcar is in RSA status check for waybill
		        if(systemSettings.getRSAWaybillRestriction()) {
		        	if(railspot.getStatusCode().equals(Constants.STAT_RAIL_RAILSHIPPED)) {
		        		if(railspot.getOutboundWayBill() != null && 
		    				railspot.getOutboundWayBill().length() < 1){
		        			
		        			throw new Exception("WAYBILL_REQUIRED_BEFORE_REMOVING_RAILCAR");
		        		}
		        	}
		        }
		
				//Check that the vehicles on the railcar have the right lot code
		        //	and are in the correct status
		        VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		        vsc.setSearchType(eVINObjAction.eVINFindRailCar);
		        vsc.setRailcarNumber(railspot.getRailcarNumber());
		        vehicles = m_vehicle.getVehicleList(vsc);
		        
		        if(vehicles != null) {
		        	
		        	//Check the vehicle lot code is Track or Support Lot
		        	for(Vehicle vehicle : vehicles) {
		        		if(!vehicle.getLotCode().equals(Constants.TRACK_LOT) &&
		        		   !vehicle.getLotCode().equals(Constants.SUPPORT_TRACK_LOT)) {
		        			throw new Exception("VIN_STATUS_ERROR");
		        		}
		        	}
			        
		        	//'Remove from lot
			        //'If the VIN is leaving and is railship approved remove from inventory
			        //'otherwise simply remove the VIN but leave the record.
		        	for(Vehicle vehicle : vehicles) {
		        		if(railspot.getStatusCode().equals(Constants.STAT_RAIL_RAILSHIPPED)) {
		        			vehicle.Delete();
		        		} else {
		        			vehicle.removeVINNoRecord();
		        		}
		        	}
		        }
		        
		        //Clear the railspot
		        railspot.setActionCode(Constants.RC_ACTION_CLEARED_TRACK_SPOT);
		        railspot.setChngDate(Common.getCurrentDateTime());
		        railspot.Delete(false);
		        
		        //'Notify mainframe railcar was pulled from lot.  
		        //'	Only do so if train ID and even time exist
		        //' the train id and the event time tells the railcar 
		        //'	came from the advance consist
		        if(railspot.getInboundTrainID() != null && 
		           railspot.getInboundTrainID().length() > 0) {
		        	//'First check for in rail reportings (PACT, RMTY, PFPS, RLOD)
		        	rsc = new RailspotSearchCriteria();
		        	rsc.setSearchType(eRailObjAction.eRailFindRailspotReportEvents);
		        	rsc.setRailcarNumber(railspot.getRailcarNumber());
		        	rsc.setInboundTrainID(railspot.getInboundTrainID());
		        	rsc.setInboundTrainEvent(railspot.getInboundTrainEvent());
		        	
		        	List<Railspot> eventrailspots = m_railspot.GetRailspots(rsc);
		        	if(eventrailspots == null || eventrailspots.size() < 1) {
		        		//'No rail reportings, so get any movement history
		        		rsc.setSearchType(eRailObjAction.eRailFindRailspotReportEventsAll);
		        		eventrailspots = m_railspot.GetRailspots(rsc);
		        	}
		        	
		        	if(eventrailspots != null) {
		        		for(Railspot eventrailspot : eventrailspots) {
			        		RcdispDelt rcchange = new RcdispDelt();
			        		rcchange.setProdStat("P");
			        		rcchange.setAction("D");
			        		Event e = new Event();
			        		e.setBlock("");
			        		e.setReason("");
			        		e.setClassValue("");
			        		e.value = "DELT";
			        		rcchange.setEvent(e);
			        		rcchange.setSystemSettings(systemSettings);
			        		rcchange.setRailspot(eventrailspot);
			        		
							m_outboundjournal.setType("RCCHANGE");
							m_outboundjournal.setData(rcchange.getXML());
							m_outboundjournal.setChngWho(eventrailspot.getChngWho());
							m_outboundjournal.setChngDate(Common.getCurrentDateTime());
							m_outboundjournal.setStatus("Q");
							m_outboundjournal.save();
		        		}
		        	}
		        } else {
		        	//' added 04/2002 for the purpose of super tads working at the destination ramps. you have
		            //' to be able to shut off the message. mak
		        	if(systemSettings.getGenRailcarDelMessage()) {
		        		RcdispDelt rcchange = new RcdispDelt();
		        		rcchange.setProdStat("P");
		        		rcchange.setAction("D");
		        		Event e = new Event();
		        		e.setBlock("");
		        		e.setReason("");
		        		e.setClassValue("");
		        		e.value = "DELT";
		        		rcchange.setEvent(e);
		        		rcchange.setSystemSettings(systemSettings);
		        		rcchange.setRailspot(railspot);
		        		
						m_outboundjournal.setType("RCCHANGE");
						m_outboundjournal.setData(rcchange.getXML());
						m_outboundjournal.setChngWho(railspot.getChngWho());
						m_outboundjournal.setChngDate(Common.getCurrentDateTime());
						m_outboundjournal.setStatus("Q");
						m_outboundjournal.save();
		        	}
		        }
		        
			}
		}
		
		//The railspots have been updated in database
		//Need to retrieve using the search type
		if(Spot > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindRailspotByLotZoneTrackSpot);
		} else if(Track != null && Track.length() > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindRailspotByLotZoneTrack);
		} else if(Zone != null && Zone.length() > 0) {
			rsc.setSearchType(eRailObjAction.eRailFindRailspotByLotZone);
		} else if(Zone != null && Zone.length() < 0) {
			rsc.setSearchType( eRailObjAction.eRailFindRailspotByLot);
		}
		
		railspots = m_railspot.GetRailspots(rsc);
			
		return railspots;
	}
	
	public Location GetNextEmptyLLSpotNoCheck(String Zone, String Row) throws Exception {	
		return GetNextEmptyLLSpotNoCheck(Zone, Row, null);
	}
	
	public Location GetNextEmptyLLSpotNoCheck(String Zone, String Row, Vehicle vehicle) throws Exception {
		Location bayLocation = null;
		
		m_location.setLot(Constants.LOADLINE_LOT);
		m_location.setZone(Zone);
		m_location.setRow(Row);
		bayLocation = m_location.getLocation("spGetEmptySpotsLLArea");

		//If there is no space throw a bay error
		if(bayLocation == null) throw(new Exception("NO_SPACE_LEFT_IN_LL"));
		
		return bayLocation;
	}
	
	public Vehicle HoldVINInPlace(Vehicle vehicle, String HoldReason) throws Exception {
		return HoldVINInPlace(vehicle, HoldReason, true);
	}
	
	public Vehicle HoldVINInPlace(Vehicle vehicle, String HoldReason, boolean UpdateMainframe) throws Exception {
		Hold foundhold = null;
		
		//Make sure a Hold Reason is passed
		if(HoldReason == null) {
			throw(new Exception("NO_HOLD_REASON"));
		}
		
		//Check for a Diverted Hold because you cannot divert a vehicle in place
		if(HoldReason == Constants.STAT_HOLD_DIVERTED) {
			throw(new Exception("CANNOT_DIVERT_IN_PLACE"));
		}
		
		//Make sure the vehicle is in proper status to be placed on hold
		if(vehicle.getStatusCode() != null && 
				(vehicle.getStatusCode().equals(Constants.STAT_PARKED) || 
				vehicle.getStatusCode().equals(Constants.STAT_HOLD))) {
		
			//Get the list of holds for the vehicle from the database
			List<Hold> holds = vehicle.getVehicleHolds(vehicle.getVIN());
			
			//Check to see if the hold is being added or removed
			if(holds == null) {
				holds = new ArrayList<Hold>();
			} else {
				for(Hold hold : holds) {
					//Remove the hold for the vehicle
					if(hold.getHoldCode().equals(HoldReason)) {
						foundhold = hold;
						holds.remove(hold);
						
						//Set the holdreason if there are any holds left
						if(holds.size() > 0) {
							HoldReason = holds.get(0).getHoldCode();
						} else {
							HoldReason = null;
							vehicle.setStatusCode(Constants.STAT_PARKED);
						}
						break;
					}
				}
			}
			
			//Add the hold to the vehicle
			if(foundhold == null) {
				Hold hold = database.getClass(Hold.class);
				hold.setManufacturer(vehicle.getManufacturer());
				hold.setHoldCode(HoldReason);
				hold.setChangeDate(new Date());
				holds.add(hold);
			}
						
			//Update the holds for the vehicle
			vehicle.setStatusCode(Constants.STAT_HOLD);
			vehicle.setVINHolds(holds);
			vehicle.setHoldReasonCode(HoldReason);
			vehicle.setChngDate(Common.getCurrentDateTime());
			vehicle.Save();
			
			if(UpdateMainframe) {
				//Update Mainframe
				TruckInMessage tim = new TruckInMessage();
				tim.setSystemSettings(systemSettings);
				tim.setVehicle(vehicle);
				m_outboundjournal.setType("TruckIN");
				m_outboundjournal.setData(tim.getXML());
				m_outboundjournal.setChngWho(vehicle.getChngWho());
				m_outboundjournal.setChngDate(Common.getCurrentDateTime());
				m_outboundjournal.setStatus("Q");
				m_outboundjournal.save();
			}
		} else {
			throw new Exception("Vehicle not in proper status to be placed on hold");
		}
		return vehicle;
	}
	
	public void LoadRailcar(Vehicle vehicle, boolean bLTD) throws Exception {
		int lastspot;
		String railcar;
		
		if(!bLTD) {
			if(systemSettings.getUsePreload()) {
				//Find all active preloads
				//Find the vehicle in the active preloads
				PreloadDetail pd = new PreloadDetail(database);
				pd = pd.getPreloadActiveVehicle(vehicle.getVIN());
				if(pd == null) {
					throw(new Exception("PRELOAD_VIN_CANNOT_BE_LOADED"));
				} else if(!pd.getStatusCode().equals(Constants.PRELOAD_STATUS_APPROVED)) {
					throw(new Exception("PRELOAD_VIN_CANNOT_BE_LOADED_APPROVED"));
				}
			}
		}
		
		//Find the railcarspot that has the railcar
		RailspotSearchCriteria rsc = new RailspotSearchCriteria();
		rsc.setSearchType(eRailObjAction.eRailFindRailcar);
		rsc.setLotCode(Constants.TRACK_LOT);
		railcar = vehicle.getOutboundRailcarNumber() != null ? vehicle.getOutboundRailcarNumber() : vehicle.getInboundRailcarNumber();
		rsc.setRailcarNumber(railcar);
		Railspot railspot = m_railspot.GetRailSpot(rsc);
		
		//Make sure the railcar is on the lot
		if(railspot == null) {
			throw(new Exception("RAILCAR_NOT_ON_LOT_ERROR"));		
		}
		
		//Check that railcar is in proper status for loading
		if(!railspot.getStatusCode().equals(Constants.STAT_RAIL_EMPTY) &&
				!railspot.getStatusCode().equals(Constants.STAT_RAIL_LOADING) &&
				!railspot.getStatusCode().equals(Constants.STAT_RAIL_PARKED)) {
			throw(new Exception("INVALID_STATUS_FOR_LOADING"));
		}
		
		//Check that the railcar has a Route
		if(railspot.getRoute() == null && railspot.getRoute().length() < 1) {
			throw(new Exception("MISSING_ROUTE_ON_RAILCAR_ERROR"));
		}
		
		//Check for LTD
		//IF not LTD then vehicle route must match railcar route
		if(!bLTD) {
			if(!this.isOKToLoad(vehicle, railspot.getMfg(), railspot.getRoute())) {
				throw(new Exception("INVALID_ROUTE_FOR_RAILCAR"));
			}
		}
		
		//Get the vehicle type for the vehicle if possible
		VehicleType vType = GetVehicleType(vehicle);
		
		//'Route is OK, so load it up
		vehicle.setActionCode(Constants.ACTION_LOAD);
		
		//Not used by the handhelds
		//if the status in assigned location then this is a hot load
		//if(vehicle.getStatusCode() != null && vehicle.getStatusCode().equals(Constants.STAT_ASSIGNED)) {
		//	HotLoadProcess(vehicle,railspot);
		//}
		
		//Check to see if the vehicle is in parked status
		if(vehicle.getStatusCode() != null && !vehicle.getStatusCode().equals(Constants.STAT_PARKED)) {
			throw(new Exception("INCORRECT_VIN_STATUS_FOR_LOAD"));
		}
		
		//Update the vehicle location with the railspot location
		vehicle.setLotCode(Constants.TRACK_LOT);
		vehicle.setZone(railspot.getZoneID());
		vehicle.setRow(railspot.getTrackNbr());
		vehicle.setSpot(railspot.getSpot());
		
		//Handheld or bLTD
		if(bLTD || (vehicle.getOutboundPos() != null && vehicle.getOutboundPos() > 0)) {
			//'handheld fills out spot, so just save and leave
			vehicle.setStatusCode(Constants.LE_STATUS_LOADED);
			vehicle.setChngDate(new Date());
			if(bLTD) vehicle.setVINMoving(false);
			vehicle.Save();
			
			//Update mainframe
			INVDispMessage inv = new INVDispMessage();
			inv.setSystemSettings(systemSettings);
			inv.setVehicle(vehicle);
			inv.setEvent("LOCC");
			m_outboundjournal.setType("INVDISP");
			m_outboundjournal.setData(inv.getXML());
			m_outboundjournal.setChngWho(vehicle.getChngWho());
			m_outboundjournal.setChngDate(Common.getCurrentDateTime());
			m_outboundjournal.setStatus("Q");
			m_outboundjournal.save();	
			
			//'check the railcar status and update if needed
			if(!railspot.getStatusCode().equals(Constants.STAT_RAIL_LOADING)) {
				railspot.setStatusCode(Constants.STAT_RAIL_LOADING);
			}
			railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
			if(railspot.getMfg() != null && railspot.getMfg().length() < 1) {
				railspot.setMfg(vehicle.getManufacturer());
			}
			if(vType != null) {
				railspot.setOutboundLoadedWeight(railspot.getOutboundLoadedWeight() + vType.getVehicleWeight());
			}
			railspot.setChngDate(new Date());
			railspot.Save(false);
			
			//'Update mainframe only for non-LTD loads
			if(!bLTD) {
				if(systemSettings.getGenLoadMessage()) {
					//Update mainframe
					RcdispInvl invload = new RcdispInvl();
					invload.setProdStat("P");
					invload.setAction("A");
					invload.setSystemSettings(systemSettings);
					invload.setVehicle(vehicle);
					invload.setRailspot(railspot);
					m_outboundjournal.setType("INVLOAD");
					m_outboundjournal.setData(invload.getXML());
					m_outboundjournal.setChngWho(vehicle.getChngWho());
					m_outboundjournal.setChngDate(Common.getCurrentDateTime());
					m_outboundjournal.setStatus("Q");
					m_outboundjournal.save();
				}
			}
			
			//Update Preload
			if(systemSettings.getUsePreload()) {
				PreloadDetail pd = new PreloadDetail(database);
				pd.setVIN(vehicle.getVIN());
				pd.setStatusCode(Constants.PRELOAD_STATUS_LOADED);
				pd.setActionCode(Constants.PRELOAD_ACTION_LOAD);
				pd.setLoadedDate(new Date().toString());
				pd.setChngdate(new Date());
				pd.setChngwho(vehicle.getChngWho());
				pd.updateLoaded();
			}
			
			//Exit
			return;
			
		} else {
			//Get the next available spot
			lastspot = railspot.GetFilledPosInRailcar(vehicle.getOutboundRailcarNumber(),vehicle.getOutboundDeckLevel());
		}

		//'got the next position, update VIN object
		vehicle.setOutboundPos(lastspot + 1);
		vehicle.setStatusCode(Constants.STAT_LOADED);
		vehicle.Save();
		
		//'check the railcar status and update if needed
		//'Update the loaded weight
		if(vType != null) {
			railspot.setOutboundLoadedWeight(railspot.getOutboundLoadedWeight() + vType.getVehicleWeight());
		}
		//'Update the MFG if not already set
		if(railspot.getMfg() != null && railspot.getMfg().length() < 1) {
			railspot.setMfg(vehicle.getManufacturer());
		}
		
		if(!railspot.getStatusCode().equals(Constants.STAT_RAIL_LOADING)) {
			railspot.setStatusCode(Constants.STAT_RAIL_LOADING);
		}
		railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
		railspot.setChngDate(new Date());
		railspot.Save(false);
		
		//If using preload then update the preloaddetail
		if(systemSettings.getUsePreload()) {
			PreloadDetail pd = new PreloadDetail(database);
			pd.setVIN(vehicle.getVIN());
			pd.setStatusCode(Constants.PRELOAD_STATUS_LOADED);
			pd.setActionCode(Constants.PRELOAD_ACTION_LOAD);
			pd.setLoadedDate(new Date().toString());
			pd.setChngdate(new Date());
			pd.updateLoaded();
		}
		
		//'Update mainframe
		if(systemSettings.getGenLoadMessage()) {
			//Update mainframe
			RcdispInvl invload = new RcdispInvl();
			invload.setProdStat("P");
			invload.setAction("A");
			invload.setSystemSettings(systemSettings);
			invload.setVehicle(vehicle);
			invload.setRailspot(railspot);
			m_outboundjournal.setType("INVLOAD");
			m_outboundjournal.setData(invload.getXML());
			m_outboundjournal.setChngWho(vehicle.getChngWho());
			m_outboundjournal.setChngDate(Common.getCurrentDateTime());
			m_outboundjournal.setStatus("Q");
			m_outboundjournal.save();
		}
	}
	
	public void LoadRailcar(Vehicle vehicle) throws Exception {
		LoadRailcar(vehicle,false);
	}
	
	public Vehicle PreBayVehicle(Vehicle vehicle) throws Exception {
		return PreBayVehicle(vehicle, eBayArrival.eBay_VINAdd);
	}
	
	public Vehicle PreBayVehicle(Vehicle vehicle, eBayArrival eArrival) throws Exception {
		Location bayLocation = null;
		
		//If the option is set to exclude Return To Plant vehicles
		//	Check to see if the action is a Plant In
		//		Change the SCAC to SCAC_RETURN_FROM_PLANT
		if(systemSettings != null && systemSettings.getExcludeReturnToPlant() != null) {
			if(eArrival == eBayArrival.eBay_Plant) {
				if( ReturnToPlantPerformed(vehicle)) {
					vehicle.setInboundSCAC(Constants.SCAC_RETURN_FROM_PLANT);
				}
			}
		}
		
		vehicle = SetArrivalInformation(vehicle, eArrival);
		
	//TheStart - label for a goto statement
		
	    //if VIN is being unloaded from a railcar, no need to look it up
	    //				just assign it to a location
		if(vehicle.getStatusCode() != Constants.STAT_LOADED ) {
			//Check for ASN - since incomplete vehicle object may have been passed.
			Vehicle tempVehicle = vehicle.getVehicle(vehicle.getVIN());
			
			//No ASN then generate arrival record
			if( tempVehicle == null) {
				switch(eArrival) {
				case eBay_Rail:
					if(vehicle.getInboundRailcarNumber() != null ) {
						vehicle = VINRailArrival(vehicle, eVINObjAction.eVINFindVIN);
					} else {
						throw(new Exception("Missing Railcar number"));
					}
					break;
				case eBay_Plant:
					if(vehicle.getVINOrigin(vehicle.getVIN())) vehicle.setStatusCode(Constants.STAT_PLANT_IN);
					vehicle.setArrivalDate(new Date());
					break;
				case eBay_TruckIn:
					vehicle.setArrivalDate(new Date());
					break;
				case eBay_Drop:
					vehicle.setArrivalDate(new Date());
					break;
				case eBay_DriveIn:
					vehicle.setArrivalDate(new Date());
					vehicle.setStatusCode(Constants.STAT_DRIVE_IN);
					break;
				case eBay_ReAssign:
					break;
				default:
					break;
				}
			}			
		} else {
	        //VIN is being removed from the railcar 
			//Need to erase railcar deck level and position information		
			
			if( vehicle.getStatusCode() == Constants.STAT_LOADED) {
				//VIN is being removed from an outbound railcar
				vehicle.setOutboundRailcarNumber("");
				vehicle.setOutboundDeckLevel("");
				vehicle.setOutboundPos(0);
			} else {
				//VIN is being removed from an inbound railcar
				vehicle.setInboundDeckLevel("");
				vehicle.setInboundPos(0);
			}
		}
		
		//Must be OK to prebay
		bayLocation = PreBayDecision(vehicle);

		//Check to see if there are any pending holds on the VINS
		//	If the bayLocation has not already been found
		if(bayLocation == null) bayLocation = CheckVINForHoldCriteria(vehicle);
		
		//If a location was found then update the vehicle
		if(bayLocation != null) {
						
			//Check to see if vehicle has a location
			if(vehicle.getLotCode() != null && vehicle.getZone() != null && vehicle.getRow() != null && vehicle.getSpot() != null) {
				//Check to see if it already has this location
				if(vehicle.getLotCode() == bayLocation.getLot() && vehicle.getZone() == bayLocation.getZone() &&
						vehicle.getRow() == bayLocation.getRow() && vehicle.getSpot() == bayLocation.getSpot()) {
					
					//Don't update
				} else {
					//Update with the bayLocation found
					vehicle.setLotCode(bayLocation.getLot());
					vehicle.setZone(bayLocation.getZone());
					vehicle.setRow(bayLocation.getRow());
					vehicle.setSpot(bayLocation.getSpot());
					vehicle.Save();					
				}
			} else {
				//Update with the bayLocation found
				vehicle.setLotCode(bayLocation.getLot());
				vehicle.setZone(bayLocation.getZone());
				vehicle.setRow(bayLocation.getRow());
				vehicle.setSpot(bayLocation.getSpot());
				vehicle.Save();					
			}
		}
		
		//If there is an error then pass the description
		
		return vehicle;
	}
	
	public PhysicalDetail physicalTruckVIN(PhysicalDetail physicaldetail, boolean MovePhysicalVIN) throws Exception {
		//Find the vehicle at the location
		Vehicle vehicle = getVehicleAtScanLocation(physicaldetail);

		if(vehicle != null) {
			//Compare the location vehicle with scan vehicle
			if(physicaldetail.getVIN().equals(vehicle.getVIN())) {
				//Log the physical
				physicaldetail = logPhysicalDetail(physicaldetail,"N","");
				
				//Set the message to "VIN Processed" for the client
				physicaldetail.setMessage("VIN Processed");
			} else {
				//Found vehicle at location but it is not scan vin
				
				//Check the vehicle is in Parked or Hold status
				if(vehicle.getStatusCode().equals(Constants.STAT_PARKED) || 
				   vehicle.getStatusCode().equals(Constants.STAT_HOLD)) {
					//Can the vehicle be moved
					if(MovePhysicalVIN) {
						//Move the location vehicle to physical storage
						vehicle.setChngWho(physicaldetail.getChngWho());
						vehicle.setChngDate(Common.getCurrentDateTime());
						vehicle = MoveToPhysicalStorage(vehicle);
						
						//Now find the scan vehicle and move to the scan location
						vehicle = m_vehicle.getVehicle(physicaldetail.getVIN());
						
						//MOVEVIN(sVIN,LOT_TRUCK,m_sZone,m_sArea,iSpot)
						vehicle = MoveVINPhysical(vehicle,physicaldetail);
						physicaldetail = logPhysicalDetail(physicaldetail,"Y","VIN Moved");
						
						//Set the message for the client
						physicaldetail.setMessage("VIN Moved/Processed");
					} else {
						//Set the message to "VIN Mismatch" and return
						physicaldetail.setMessage("VIN Mismatch");
					}
				} else {
					//Vehicle is not in Parked or Hold status
					physicaldetail = logPhysicalDetail(physicaldetail,"Y","");
					//Set the message for the client
					physicaldetail.setMessage("VIN Scanned ONLY");
				}
			}
		} else {
			//No vehicle found at the scan location
			//Find the vehicle scanned
			vehicle = m_vehicle.getVehicle(physicaldetail.getVIN());
			
			if(vehicle != null) {
				//Check to see if it is in the correct location
				if(isVehicleAtScanLocation(physicaldetail,vehicle)) {
					physicaldetail = logPhysicalDetail(physicaldetail,"Y","");
					//Set the message for the client
					physicaldetail.setMessage("VIN Processed");
					//RFPrompt(1).Text = "VIN Processed"
				} else {
					//Scan vehicle is not in the correct location
					//Check for Parked or Hold status
					if(vehicle.getStatusCode().equals(Constants.STAT_PARKED) || 
					   vehicle.getStatusCode().equals(Constants.STAT_HOLD)) {
						//Move the vehicle to the scan location
						//MOVEVIN(sVIN,LOT_TRUCK,m_sZone,m_sArea,iSpot)
						vehicle = MoveVINPhysical(vehicle,physicaldetail);
						
						physicaldetail = logPhysicalDetail(physicaldetail,"Y","VIN Moved");
						//Set the message for the client
						physicaldetail.setMessage("VIN Moved/Processed");
					} else {
					//Vehicle not in parked or hold status
						//Move the vehicle to the scan location
						//MOVEVIN(sVIN,LOT_TRUCK,m_sZone,m_sArea,iSpot)
						vehicle = MoveVINPhysical(vehicle,physicaldetail);
						
						physicaldetail = logPhysicalDetail(physicaldetail,"Y","");
						//Set the message for the client
						physicaldetail.setMessage("VIN Moved/Processed");
					}
				}
			} else {
				//scan vehicle is not in the database ramp will have to investigate
				physicaldetail = logPhysicalDetail(physicaldetail,"Y","");
				//Set the message for the client
				physicaldetail.setMessage("VIN Added to Physical ONLY!");
			}
		}
		
		if(vehicle != null) {
			//Log the physical for the vehicle
			vehicle.setActionCode(Constants.ACTION_PHYSICAL_SCAN);
			vehicle.setChngWho(physicaldetail.getChngWho());
			vehicle.setChngDate(physicaldetail.getChngDate());
			vehicle.SaveHistory();
		}
		
		return physicaldetail;
	}
	
	public PhysicalDetail physicalVIN(PhysicalDetail physicaldetail, boolean MovePhysicalVIN) throws Exception {
		String reconciled = "Y";
		
		//Get the scan vehicle
		Vehicle vehicle = m_vehicle.getVehicle(physicaldetail.getVIN());
		
		if(vehicle != null) {
			//Found scan vehicle
			if(isVehicleAtScanLocation(physicaldetail,vehicle)) {
				physicaldetail.setMessage("VIN Processed");
				reconciled = "N";
			} else {
				//Vehicle not at the scan location
				if(MovePhysicalVIN) {
					if(vehicle.getLotCode().equals(Constants.LOADLINE_LOT)) {
						vehicle = MoveVINtoLoadLine(vehicle,physicaldetail);
						physicaldetail.setMessage("VIN LL Moved");				
					} else {
						vehicle = MoveVINToStorage(vehicle,physicaldetail);
						physicaldetail.setMessage("VIN Moved/Processed");
					}
				} else {
					physicaldetail.setMessage("VIN Mismatch");
				}
			}
		} else {
			//'VIN not in inventory.  
			//Add to physical and ramp will have to investigate
			physicaldetail.setMessage("VIN Added to Physical ONLY!");
		}
		
		//Log the results
		physicaldetail = logPhysicalDetail(physicaldetail,reconciled,"");
		
		return physicaldetail;
	}
	
	public List<Vehicle> ReAssignVehicle(List<Vehicle> vehicles) throws Exception{
		for(Vehicle vehicle : vehicles) {
			vehicle = ReAssignVehicle(vehicle);
		}
		
		return vehicles;
	}
	
	public Vehicle ReAssignVehicle(Vehicle vehicle) throws Exception {
		
		String status = vehicle.getStatusCode();
		if(status != null) {
			switch(status) {
				case Constants.STAT_ASSIGNED:
					vehicle = CancelPreBay(vehicle);
					vehicle = PreBayVehicle(vehicle, eBayArrival.eBay_VINAdd);
					
					//If the vehicle was in the BAD ASN editor remove it
					m_badasn.setVIN(vehicle.getVIN());
					m_badasn.Delete();
					break;
					
				case Constants.STAT_PARKED:
				case Constants.STAT_HOLD:
					vehicle = PreBayVehicle(vehicle, eBayArrival.eBay_ReAssign);
					
					//Update mainframe
					INVDispMessage inv = new INVDispMessage();
					inv.setSystemSettings(systemSettings);
					inv.setVehicle(vehicle);
					inv.setEvent("LOCC");
					m_outboundjournal.setType("INVDISP");
					m_outboundjournal.setData(inv.getXML());
					m_outboundjournal.setChngWho(vehicle.getChngWho());
					m_outboundjournal.setChngDate(Common.getCurrentDateTime());
					m_outboundjournal.setStatus("Q");
					m_outboundjournal.save();			
					
					//If the vehicle was in the BAD ASN editor remove it
					m_badasn.setVIN(vehicle.getVIN());
					m_badasn.Delete();
					break;
			}
		}
		return vehicle;
	}
	
	/**
	 * Used by the handheld to remove a vehicle ASN from a railcar
	 * @param vehicle
	 * @return vehicle
	 * @throws Exception 
	 */
	public Vehicle RemoveVINFromLot(Vehicle vehicle) throws Exception {
		//'Remove VIN from the lot
		if(!vehicle.getStatusCode().equals(Constants.STAT_ARRIVED_RAIL) &&
		   !vehicle.getStatusCode().equals(Constants.STAT_ARRIVED_SUPPORT_YARD)){
			
			throw new Exception("CANNOT_REMOVE_VIN_ON_THE_LOT");
		} else {
			if(vehicle.getStatusCode().equals(Constants.STAT_ASSIGNED)) {
				//Cancel Prebay
			}
			vehicle.RemoveFromLot();
		}
		
	    //'Check to see if load/empty flag should be updated on the railcar
	    //'Check to see if any VINs are on the railcar
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		vsc.setSearchType(eVINObjAction.eVINFindInboundRailCar);
		vsc.setInboundRailcarNumber(vehicle.getInboundRailcarNumber());
		List<Vehicle> vehicles = m_vehicle.getVehicleList(vsc);
		
		if(vehicles == null || vehicles.size() < 1) {
			//'There are no VINs left, so change load/empty flag to empty
			RailspotSearchCriteria rsc = new RailspotSearchCriteria();
			rsc.setSearchType(eRailObjAction.eRailFindRailcar);
			rsc.setRailcarNumber(vehicle.getInboundRailcarNumber());
			List<Railspot> railspots = m_railspot.GetRailspots(rsc);
			if(railspots != null && railspots.size() >0) {
				//'Railcar found so update the l/e status
				Railspot railspot = railspots.get(0);
				railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
				railspot.setMfg("");
				railspot.setRoute("");
				railspot.setLoadEmptyStatus("E");
				railspot.setChngDate(Common.getCurrentDateTime());
				if(vehicle.getChngWho() != null) railspot.setChngWho(vehicle.getChngWho());
				railspot.Save(false);
			}
		}
	
		return vehicle;
	}
	
	/**
	 * 'remove VINs from railcar after loaded
       'send VIN through system assignment process to get new spot to park
    
	 * @param vehicles
	 * @return
	 * @throws Exception 
	 */
	public List<Vehicle> RemoveFromRailcar(List<Vehicle> vehicles) throws Exception{
		List<Railspot> railspots = new ArrayList<Railspot>();
		Railspot currentrailspot = null;
		RSAAudit rsaaudit = new RSAAudit(database);
		VehicleType vType;
		String railcar = null;
		
		for(Vehicle vehicle : vehicles) {
	
			//'assign vehicle to location
			try {
				vehicle.setActionCode(Constants.ACTION_UNLOAD_VIN);
				vehicle = PreBayVehicle(vehicle);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error: Unable to find a bay location " + e.getMessage());
			}
			
			//'Get the railcar being unloaded
			railcar = vehicle.getOutboundRailcarNumber();
			if(currentrailspot == null || 
			   !currentrailspot.getRailcarNumber().equals(railcar)) {
				
				currentrailspot = m_railspot.GetRailspot(railcar);
				railspots.add(currentrailspot);
			}
			
			//Update loaded weight of the railcar
			vType = GetVehicleType(vehicle);
			if(vType != null && vType.getVehicleWeight() > 0) {
				currentrailspot.setOutboundLoadedWeight(currentrailspot.getOutboundLoadedWeight() - vType.getVehicleWeight());
			}

			//Update mainframe
			INVDispMessage inv = new INVDispMessage();
			inv.setSystemSettings(systemSettings);
			inv.setVehicle(vehicle);
			inv.setEvent("LOCC");
			m_outboundjournal.setType("INVDISP");
			m_outboundjournal.setData(inv.getXML());
			m_outboundjournal.setChngWho(vehicle.getChngWho());
			m_outboundjournal.setChngDate(Common.getCurrentDateTime());
			m_outboundjournal.setStatus("Q");
			m_outboundjournal.save();	
			
			//'Update pre-load if used
			if(systemSettings.getUsePreload()) {
				PreloadDetail pd = new PreloadDetail(database);
				pd.setVIN(vehicle.getVIN());
				pd.setStatusCode(Constants.PRELOAD_STATUS_APPROVED);
				pd.setActionCode(Constants.PRELOAD_ACTION_UNLOAD);
				pd.setLoadedDate("");
				pd.setChngwho(vehicle.getChngWho());
				pd.setChngdate(Common.getCurrentDateTime());
				pd.updateLoaded();
			}
			
			//Delete RSAAudit
			if(systemSettings.getUseRSAAudit()) {
				RSAAudit vehicleaudit = new RSAAudit();
				vehicleaudit.setVIN(vehicle.getVIN());
				vehicleaudit.setChgWho(vehicle.getChngWho());
				vehicleaudit.setChgDate(vehicle.getChngDate());
				rsaaudit.DeleteRSAAudit(rsaaudit);
			}
		}
		
		//'Check to see if load/empty flag should be updated
		if(railspots.size() > 0) {
			//find any vehicles that are on the railcar
			VehicleSearchCriteria vsc = new VehicleSearchCriteria();
			List<Vehicle> railvehicles;
			for(Railspot railspot : railspots) {
				vsc.setSearchType(eVINObjAction.eVINFindOutboundRailCar);
				railvehicles = m_vehicle.getVehicleList(vsc);
				
				if(railvehicles != null && railvehicles.size() > 0) {
					railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
					railspot.setLoadEmptyStatus("E");
					railspot.setChngDate(Common.getCurrentDateTime());
					railspot.setChngWho(railvehicles.get(0).getChngWho());
					railspot.Save(false);
				}
			}
		}
		
		return vehicles;
	}
	
	public Railspot SpotRailcar(Railspot railspot, boolean updatemainframe) throws Exception {
		String railcar,manf,route,status,lotcode;
		List<Vehicle> vehicles;
		Vehicle vehicle = null;
		int numVehicles;
		
		//Make sure the railcar is formatted correctly
		railcar = railspot.getRailcarNumber();
		railcar = Common.FormatRailcar(railcar);
		if(railcar == null ) throw(new Exception("INVALID_RAILCAR_NUM"));
		railspot.setRailcarNumber(railcar);
		
		//Make sure the railcar is in the database
		Railcar rc = new Railcar(database);
		rc = rc.getRailcar(railcar);
		if(rc == null) throw(new Exception("INVALID_RAILCAR_NUM"));		
		
		//'Get VINs on the railcar
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		vsc.setSearchType(eVINObjAction.eVINFindRailCarASN);
		vsc.setRailcarNumber(railcar);
		vehicles = m_vehicle.getVehicleList(vsc);
		numVehicles = vehicles.size();
		if(vehicles != null && vehicles.size() > 0) vehicle = vehicles.get(0);
		
		//'Check to make sure user entered a mfg and route
		manf = railspot.getMfg();
		route = railspot.getRoute();
		status = railspot.getStatusCode();
		if(status == null) status = "";
		
		if(manf != null && route != null && manf.length() > 0 && route.length() > 0 ){
			//'User did, so get the route info
			//		e.g. SCAC and FSAC
			RouteSearchCriteria rsc = new RouteSearchCriteria();
			rsc.setSearchType(eRouteObjAction.eRouteFindByMfgRoute);
			rsc.setManufacturer(manf);
			rsc.setRoute(route);
			List<Route> routes = m_route.getRouteList(rsc);
			
			if(routes != null && routes.size() > 0) {
				Route rte = routes.get(0);
				railspot.setOutboundSCAC(rte.getSCAC());
				railspot.setOutboundFSAC(rte.getFSAC());
			} else {
				railspot.setRoute("");
				railspot.setOutboundSCAC("");
				railspot.setOutboundFSAC("");
			}
		} else if(manf != null && manf.length() < 1 && 
				(status.equals(Constants.STAT_RAIL_RAILSHIPPED) ||
				 status.equals(Constants.STAT_RAIL_ARRIVEDRAIL) ||
				 status.equals(Constants.STAT_RAIL_INSUPPORTYARD) ||
				 status.equals(Constants.STAT_RAIL_LOADING) ||
				 status.equals(Constants.STAT_RAIL_ASSIGNED) ||
				 status.equals(Constants.STAT_RAIL_ERROR))) {
			
			//'Manufacturer is required for these statuses
			throw(new Exception("MANF_REQUIRED_ON_RAILCAR"));
			
		} else if(route != null && route.length() < 1 && 
				(status.equals(Constants.STAT_RAIL_RAILSHIPPED) ||
				 status.equals(Constants.STAT_RAIL_LOADING))) {
			
			//'Route is required for these statuses
			throw(new Exception("ROUTE_REQUIRED_ON_RAILCAR"));
			
		} else {
			//'User did not, so clear the route info
			railspot.setRoute(null);
			railspot.setOutboundSCAC(null);
			railspot.setOutboundFSAC(null);
		}
		
		// 'Check to see what the status should be
		if(route != null && route.length() > 0 && status != null && status.equals(Constants.STAT_RAIL_PARKED)) {
			railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
		}
                
		//Determine status code by railcar is in Track or Support Lot
		lotcode = railspot.getLotCode();
		if(lotcode == null) lotcode = "";
		
		if(lotcode.equals(Constants.TRACK_LOT)) {
			switch(status) {
				case "":
					if(numVehicles > 0) {
						railspot.setStatusCode(Constants.STAT_RAIL_ARRIVEDRAIL);
						railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
					} else {
						railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
						railspot.setLoadEmptyStatus(Constants.LE_STATUS_EMPTY);
					}
					break;
				case Constants.STAT_RAIL_EMPTY:
					if(numVehicles > 0) {
						railspot.setStatusCode(Constants.STAT_RAIL_ARRIVEDRAIL);
						railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
					}
					break;
				case Constants.STAT_RAIL_PARKED:
					railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
					break;
				case Constants.STAT_RAIL_ARRIVEDRAIL:
					//'If railcar is empty, mark as empty
					if(railspot.getLoadEmptyStatus().equals(Constants.LE_STATUS_EMPTY)) {
						railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
					}
					break;
				default:
					break;
			}
		} else if(lotcode.equals(Constants.SUPPORT_TRACK_LOT)) {
			switch(status) {
				case "":
					if(numVehicles > 0) {
						railspot.setStatusCode(Constants.STAT_RAIL_INSUPPORTYARD);
						railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
					} else {
						railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
						railspot.setLoadEmptyStatus(Constants.LE_STATUS_EMPTY);
					}
					break;
				case Constants.STAT_RAIL_EMPTY:
					if(numVehicles > 0) {
						railspot.setStatusCode(Constants.STAT_RAIL_INSUPPORTYARD);
						railspot.setLoadEmptyStatus(Constants.LE_STATUS_EMPTY);
					}
					break;
				case Constants.STAT_RAIL_PARKED:
					railspot.setStatusCode(Constants.STAT_RAIL_EMPTY);
					break;
				default:
					break;
			}
		}
		
		//'Set inbound information to values from first VIN
		if(vehicle != null) {
			railspot.setMfg(vehicle.getManufacturer());
			railspot.setInboundBOL(vehicle.getInboundBOL());
			railspot.setInboundSCAC(vehicle.getInboundSCAC());
			railspot.setDepartureDate(vehicle.getDepartureDate());
			railspot.setInboundWayBill(vehicle.getInboundWayBill());
			railspot.setInboundWayBillSN(vehicle.getInboundWayBillSN());
			railspot.setLTD(vehicle.getLTD());
			if(railspot.getLTD()) railspot.setRoute(vehicle.getLTDRoute());
		}
		
		//'Save the railcar data
        //	.ChngWho = m_sChngWho
        //	.ChngDate = Now()
		railspot.setChngDate(Common.getCurrentDateTime());
		
		//' check to see if there is an Embargo record for this railcar
		Embargo embargo = new Embargo(database);
		embargo = embargo.get(railspot.getRailcarNumber());
		if(embargo != null) {
			railspot.setOutboundEmbargo(embargo.getEmbargo());
			railspot.setOutboundPermit(embargo.getPermit());
		}
		
		//'Update mainframe
		if(updatemainframe) {
			if((railspot.getPrevLotCode() == null || !railspot.getPrevLotCode().equals(Constants.TRACK_LOT)) 
					&& railspot.getLotCode().equals(Constants.TRACK_LOT)) {
				
				railspot.setActionCode(Constants.RC_ACTION_SPOTTED_RAILCAR);
				railspot.Save(false);
				status = railspot.getStatusCode();
				railspot.setStatusCode(Constants.STAT_RAIL_PACT);
				railspot.setActionCode(Constants.RC_ACTION_GENERATE_PACT);
				railspot.SaveHistory();
				railspot.Save(false);
				railspot.setStatusCode(status);

				//Update Mainframe
				RCDISPPACT pact = new RCDISPPACT();
				pact.setProdStat("P");
				Event e = new Event();
				e.setValue(Constants.eXMLRailcarEventCode_PACT);
				pact.setEvent(e);
				pact.setSystemSettings(systemSettings);
				pact.setRailspot(railspot);
				m_outboundjournal.setType("RCDISP");
				m_outboundjournal.setData(pact.getXML());
				m_outboundjournal.setChngWho(railspot.getChngWho());
				m_outboundjournal.setChngDate(Common.getCurrentDateTime());
				m_outboundjournal.setStatus("Q");
				m_outboundjournal.save();
			} else {
				railspot.setActionCode(Constants.RC_ACTION_SPOTTED_RAILCAR);
				railspot.Save(false);
			}
		}
		
		//'Generate the arrival records for the VINs
		GenerateArrival(railspot);
		
		return railspot;
	}
	
	public Railspot SpotRailcar(Railspot railspot) throws Exception {
		return SpotRailcar(railspot,true);
	}
	
	public Vehicle UpdateVIN(Vehicle vehicle) {
		String EventType = "ASNC";
		
		//Check to see if the vehicle has moved so that the event type is LOCC or location change
		if(vehicle.getLotCode() + vehicle.getZone() + vehicle.getRow() + vehicle.getSpot() !=
				vehicle.getPrevLotCode() + vehicle.getPrevZone() + vehicle.getPrevRow() + vehicle.getPrevSpot()){
			EventType = "LOCC";
		}
		
		//Check to see if there are any pending holds on the vehicle
		if(vehicle.getLotCode() != null) {
			try {
				Location location = CheckVINForHoldCriteria(vehicle);
				if(location != null) {
					vehicle.updateVehicleLocation(location);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** Update mainframe when the vehicle has a status code and
    	 *	the status code is not assigned ,arrived rail or arrived rail support yard. */
		if(vehicle.getStatusCode() != null && vehicle.getStatusCode() != Constants.STAT_ASSIGNED && 
				vehicle.getStatusCode() != Constants.STAT_ARRIVED_RAIL &&
				vehicle.getStatusCode() != Constants.STAT_ARRIVED_SUPPORT_YARD) {

			INVDispMessage inv = new INVDispMessage();
			inv.setSystemSettings(systemSettings);
			inv.setVehicle(vehicle);
			inv.setEvent(EventType);
			m_outboundjournal.setType("INVDISP");
			m_outboundjournal.setData(inv.getXML());
			m_outboundjournal.setChngWho(vehicle.getChngWho());
			m_outboundjournal.setChngDate(Common.getCurrentDateTime());
			m_outboundjournal.setStatus("Q");
			m_outboundjournal.save();			
		}
						
		return vehicle;
	}
	
	public Vehicle DriveOut(Vehicle vehicle) throws Exception {
		TruckOutMessage tom;
		String status = vehicle.getStatusCode();
		
		//Set Status to "" if null
		if(status == null) status = "";
		
		switch(status) {
		case Constants.STAT_PARKED:
		case Constants.STAT_HOLD:
			vehicle.setStatusCode(Constants.STAT_DRIVE_OUT);
			vehicle.Save();
			//Send the Drive out message to mainframe
			tom = new TruckOutMessage();
			tom.setSystemSettings(systemSettings);
			tom.setVehicle(vehicle);
			m_outboundjournal.setType("TruckOut");
			m_outboundjournal.setData(tom.getXML());
			m_outboundjournal.setChngWho(vehicle.getChngWho());
			m_outboundjournal.setChngDate(Common.getCurrentDateTime());
			m_outboundjournal.setStatus("Q");
			m_outboundjournal.save();
			break;
		default:
			//Set the error message to Truck out not allowed
			throw(new Exception("Drive out not allowed for vehicle in status " + status));
		}
		
		return vehicle;
	}
	
	public Vehicle ReturnToPlant(Vehicle vehicle) throws Exception{
		TruckOutMessage tom;
		
		vehicle.setStatusCode(Constants.STAT_RETURNED_PLANT);
		
		//Remove any holds and save them as Hold Criterias
		if(vehicle.getHoldReasonCode() != null && vehicle.getHoldReasonCode().length() > 0) {
			HoldCriteria hc = new HoldCriteria();
			hc.setManufacturer(vehicle.getManufacturer());
			hc.setHoldCode(vehicle.getHoldReasonCode());
			hc.setVIN(vehicle.getVIN());
			hc.setChangWho("SYSTEM");
			hc.setChangeDate(Common.getCurrentDateTime());
			hc.Save();
			vehicle.setHoldReasonCode("");
		}
		
		vehicle.setChngDate(Common.getCurrentDateTime());
		vehicle.setOutboundSCAC(Constants.SCAC_RETURN_FROM_PLANT);
		vehicle.Save();
		
		//Send the Drive out to the mainframe
		tom = new TruckOutMessage();
		tom.setSystemSettings(systemSettings);
		tom.setVehicle(vehicle);
		m_outboundjournal.setType("TruckOut");
		m_outboundjournal.setData(tom.getXML());
		m_outboundjournal.setChngWho(vehicle.getChngWho());
		m_outboundjournal.setChngDate(Common.getCurrentDateTime());
		m_outboundjournal.setStatus("Q");
		m_outboundjournal.save();
		
		//Remove from tblVIN, history record remains, Inventory record remains
		vehicle.removeVINNoRecord();
		
		return vehicle;
	}
	
	public Vehicle TruckOut(Vehicle vehicle) throws Exception {
		TruckOutMessage tom;
		String status = vehicle.getStatusCode();
		
		//Set Status to "" if null
		if(status == null) status = "";
		
		switch(status) {
		case Constants.STAT_PARKED:
		case Constants.STAT_HOLD:
			vehicle.setStatusCode(Constants.STAT_TRUCK_OUT);
			vehicle.setVINMoving(Constants.STAT_PARKED.equals(status) ? true : null);
			vehicle.Save();
			//Send the Truck out message to mainframe
			tom = new TruckOutMessage();
			tom.setSystemSettings(systemSettings);
			tom.setVehicle(vehicle);
			m_outboundjournal.setType("TruckOut");
			m_outboundjournal.setData(tom.getXML());
			m_outboundjournal.setChngWho(vehicle.getChngWho());
			m_outboundjournal.setChngDate(Common.getCurrentDateTime());
			m_outboundjournal.setStatus("Q");
			m_outboundjournal.save();
			break;
		default:
			//Set the error message to Truck out not allowed
			throw(new Exception("Truck out not allowed for vehicle in status " + status));
		}
		
		return vehicle;
	}
	
	public List<Vehicle> Truckout(List<Vehicle> vehicles) throws Exception{
		
		for(Vehicle vehicle : vehicles) {
			vehicle = TruckOut(vehicle);
		}
		
		return vehicles;
	}
	// }}
	
	////////////////////////////////////////////////////////////
	//Utility
	////////////////////////////////////////////////////////////
	// {{
	
	private boolean isVehicleAtScanLocation(PhysicalDetail physicaldetail, Vehicle vehicle) {
		String scanLot = physicaldetail.getScanLot();
		String scanZone = physicaldetail.getScanZone();
		String scanRow = physicaldetail.getScanRow();
		String scanSpot = physicaldetail.getScanSpot();
				
		if(scanLot.equals(vehicle.getLotCode()) &&
		   scanZone.equals(vehicle.getZone()) &&
		   scanRow.equals(vehicle.getRow()) &&
		   scanSpot.equals(vehicle.getSpot().toString())) {
			return true;
		}
		
		return false;
	}

	private PhysicalDetail logPhysicalDetail(PhysicalDetail physicaldetail,String reconciled, String message) {
		//Make sure the database is set
		physicaldetail.setLocationDatabase(database);
		
		//Find the physicaldetail
		PhysicalDetail currentphysical = physicaldetail.getPhysicalDetailVIN(physicaldetail.getCode(), physicaldetail.getVIN());
		
		//Update the currentphysical with the scan information
		if(currentphysical != null) {
			currentphysical.setScanLot(physicaldetail.getScanLot());
			currentphysical.setScanZone(physicaldetail.getScanZone());
			currentphysical.setScanRow(physicaldetail.getScanRow());
			currentphysical.setScanSpot(physicaldetail.getScanSpot());
			currentphysical.setChngWho(physicaldetail.getChngWho());
			currentphysical.setChngDate(physicaldetail.getChngDate());
		} else {
			currentphysical = physicaldetail;
		}
		
		currentphysical.setReconciled(reconciled);
		currentphysical.setMessage(message);
		currentphysical.Save();
		
		return currentphysical;
	}
	
	private Vehicle getVehicleAtScanLocation(PhysicalDetail physicaldetail) {
		//Find the vehicle at the location
		Vehicle vehicle = new Vehicle(database);
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		vsc.setSearchType(eVINObjAction.eVINFindSpot);
		vsc.setLotCode(physicaldetail.getScanLot());
		vsc.setZone(physicaldetail.getScanZone());
		vsc.setRow(physicaldetail.getScanRow());
		vsc.setSpot(Integer.parseInt(physicaldetail.getScanSpot()));

		return vehicle.getVehicle(vsc);
	}
	
	private Vehicle MoveVINPhysical(Vehicle vehicle, PhysicalDetail physicaldetail) {
		//Update the vehicle with the physical detail information
		vehicle.setLotCode(physicaldetail.getScanLot());
		vehicle.setZone(physicaldetail.getScanZone());
		vehicle.setRow(physicaldetail.getScanRow());
		vehicle.setSpot(Integer.parseInt(physicaldetail.getScanSpot()));
		vehicle.setActionCode(Constants.ACTION_VIN_MOVED);
		vehicle.setChngWho(physicaldetail.getChngWho());
		vehicle.setChngDate(Common.getCurrentDateTime());
		
		//Inbound if needed
		if(!vehicle.getStatusCode().equals(Constants.STAT_PARKED) &&
				!vehicle.getStatusCode().equals(Constants.STAT_HOLD)) {
			//Set the Inbound Status
			if(vehicle.getVINOrigin(vehicle.getVIN())) {
				vehicle.setStatusCode(Constants.STAT_PLANT_IN);
			} else {
				vehicle.setStatusCode(Constants.STAT_TRUCK_IN);
			}
			vehicle.setArrivalDate(new Date());
		}
		
		//Update the vehicle location
		vehicle = this.UpdateVIN(vehicle);
		
		return vehicle;
	}
	
	private Vehicle MoveToPhysicalStorage(Vehicle vehicle) throws Exception {
		List<Location> locations;
		Location bayLocation = null;
		
		//Find a bay location in storage
		m_location.setLot(Constants.STORE_LOT);
		m_location.setZone("PHY");
		m_location.setRow("   1");
		
		//Find first available spot in storage area (no overflow for now)
		locations = m_location.getLocationList("spGetEmptyStorageZone");
		
		if(locations != null && locations.size() > 0) {
			//Assign the first location
			bayLocation = locations.get(0);
			bayLocation.setLot(Constants.STORE_LOT);
			
			//update the vehicle with the location
			vehicle.updateVehicleLocation(bayLocation);
			vehicle.Save();
			
			//Update mainframe
			INVDispMessage inv = new INVDispMessage();
			inv.setSystemSettings(systemSettings);
			inv.setVehicle(vehicle);
			inv.setEvent("LOCC");
			m_outboundjournal.setType("INVDISP");
			m_outboundjournal.setData(inv.getXML());
			m_outboundjournal.setChngWho(vehicle.getChngWho());
			m_outboundjournal.setChngDate(Common.getCurrentDateTime());
			m_outboundjournal.setStatus("Q");
			m_outboundjournal.save();	
		} else {
			//No available spot
			throw new Exception("NO_EMPTY_STORAGE_SPOTS");
		}
		
		return vehicle;
	}

	private Vehicle MoveVINToStorage(Vehicle vehicle, PhysicalDetail physicaldetail) throws Exception {
		//Get the Zone and Row information fromm the physical
		String Lot = physicaldetail.getScanLot();
		String Zone = physicaldetail.getScanZone();
		String Row = physicaldetail.getScanRow();
		int Spot = 0;
		
		//Inbound if needed by setting the status and arrival information
		if(!vehicle.getStatusCode().equals(Constants.STAT_PARKED) &&
				!vehicle.getStatusCode().equals(Constants.STAT_HOLD)) {
			//Set the Inbound Status
			if(vehicle.getVINOrigin(vehicle.getVIN())) {
				vehicle.setStatusCode(Constants.STAT_PLANT_IN);
			} else {
				vehicle.setStatusCode(Constants.STAT_TRUCK_IN);
			}
			vehicle.setArrivalDate(new Date());
		}
		
		//Update baying information
		Location bayLocation = GetEmptyStoreSpot(Lot,Zone,Row,Spot);
		if(bayLocation != null) {
			vehicle.updateVehicleLocation(bayLocation);
			vehicle.setActionCode(Constants.ACTION_VIN_MOVED);
			vehicle.setChngDate(Common.getCurrentDateTime());
			vehicle.Save();

			//Update mainframe
			INVDispMessage inv = new INVDispMessage();
			inv.setSystemSettings(systemSettings);
			inv.setVehicle(vehicle);
			inv.setEvent("LOCC");
			m_outboundjournal.setType("INVDISP");
			m_outboundjournal.setData(inv.getXML());
			m_outboundjournal.setChngWho(vehicle.getChngWho());
			m_outboundjournal.setChngDate(Common.getCurrentDateTime());
			m_outboundjournal.setStatus("Q");
			m_outboundjournal.save();	
		} else {
			throw new Exception("NO_EMPTY_STORAGE_SPOTS");
		}
		
		return vehicle;
	}
	
	private Vehicle MoveVINtoLoadLine(Vehicle vehicle, PhysicalDetail physicaldetail) throws SQLException {
		//Get the Zone and Row information fromm the physical
		String Lot = physicaldetail.getScanLot();
		String Zone = physicaldetail.getScanZone();
		String Row = physicaldetail.getScanRow();
		int Spot = 0;
		boolean bActivatedSpot = false;
		boolean bAddedSpot = false;
		
		m_location.setLot(Lot);
		m_location.setZone(Zone);
		m_location.setRow(Row);
		List<Location> locations = m_location.getLocationList("GetLoadLineSpotsAvail");
		
		//Find available spots
		if(locations != null && locations.size() > 0) {
			Spot = locations.get(0).getSpot();
		} else {
			//Get the unavailable spots and activate
			locations = m_location.getLocationList("GetLoadLineSpotsUnAvail");
			if(locations != null && locations.size() > 0) {
				Spot = locations.get(0).getSpot();
				m_location.setLot(Constants.LOADLINE_LOT);
				m_location.setZone(Zone);
				m_location.setRow(Row);
				m_location.setSpot(Spot);
				m_location.setActive(true);
				m_location.setChngWho("PHYSICAL");
				m_location.setChngDate(Common.getCurrentDateTime());
				m_location.setLocation("spUpdLLineSpot");
				bActivatedSpot = true;
			} else {
				//Create a spot for the vehicle
				locations = m_location.getLocationList("GetLoadLineSpots");
				if(locations != null) {
					//Iterate through all of the spots and add one
					//	Note: Spots may start at any integer value.
					for(Location l : locations) {
						Spot = l.getSpot();
					}
					Spot++;
					
					//Now create the spot
					m_location.setLot(Constants.LOADLINE_LOT);
					m_location.setZone(Zone);
					m_location.setRow(Row);
					m_location.setSpot(Spot);
					m_location.setActive(true);
					m_location.setChngWho("PHYSICAL");
					m_location.setChngDate(Common.getCurrentDateTime());
					m_location.setLocation("spUpdLLineSpot");
					bAddedSpot = true;
				}
			}
		}

		//Now park the vehicle in the found / inactive / created location
		vehicle.setLotCode(Lot);
		vehicle.setZone(Zone);
		vehicle.setRow(Row);
		vehicle.setSpot(Spot);
		vehicle.setChngDate(Common.getCurrentDateTime());
		vehicle.setChngWho(physicaldetail.getChngWho());
	
		//Inbound if needed by setting the status and arrival information
		if(!vehicle.getStatusCode().equals(Constants.STAT_PARKED) &&
				!vehicle.getStatusCode().equals(Constants.STAT_HOLD)) {
			//Set the Inbound Status
			if(vehicle.getVINOrigin(vehicle.getVIN())) {
				vehicle.setStatusCode(Constants.STAT_PLANT_IN);
			} else {
				vehicle.setStatusCode(Constants.STAT_TRUCK_IN);
			}
			vehicle.setArrivalDate(new Date());
		}

		//Update the baying information
		vehicle = UpdateVIN(vehicle);

		//' inactivate the spot just created or activated
		if(bAddedSpot || bActivatedSpot) {
			m_location.setLot(Constants.LOADLINE_LOT);
			m_location.setZone(Zone);
			m_location.setRow(Row);
			m_location.setSpot(Spot);
			m_location.setActive(false);
			m_location.setChngWho("PHYSICAL");
			m_location.setChngDate(Common.getCurrentDateTime());
			m_location.setLocation("spUpdLLineSpot");
		}
		
		return vehicle;
	}
	
	/**
	 * This is the bay process for the loadline lot
	 * @param vehicle
	 * @return Location
	 * @throws Exception 
	 */
	private Location BayLoadLine(Vehicle vehicle) throws Exception {
		Location bayLocation = null;
		VehicleType vType;
		boolean BayVINOnHold;
		String manf,route,lotcode,zone,row,startzone;
		int spot;
		
		//Get the vehicle type
		vType = GetVehicleType(vehicle);
		
		//Check for allowing vehicle to bay if on hold
		BayVINOnHold = systemSettings.getBayVINOnHold();
		
		if(vehicle.getStatusCode() == Constants.STAT_HOLD && vehicle.getHoldReasonCode() != null  && !BayVINOnHold) {
			//VIN has been placed on hold, can only be prebayed if there is no reason code
			throw(new Exception("PREBAY_VIN_ON_HOLD_ERROR"));
		} else {
			manf = vehicle.getManufacturer();
			route = vehicle.getRoute();
			lotcode = vehicle.getLotCode();
			zone = vehicle.getZone();
			row = vehicle.getRow();
			spot = vehicle.getSpot() == null ? 0 : vehicle.getSpot();
			startzone = vehicle.getStartZone();
			
			bayLocation = GetEmptyLLSpot(manf,route,lotcode,zone,row,spot,vType,startzone);
		}

		if(bayLocation == null) {
			throw(new Exception("Loadline zone " + vehicle.getStartZone() + " is full"));
		} else {
			//If the location is missing a lot code add it
			if(bayLocation.getLot() == null) bayLocation.setLot(Constants.LOADLINE_LOT);
			
			//Update VIN object with new location
			if(vehicle.getStatusCode() != null) {
				switch(vehicle.getStatusCode()) {
				case Constants.STAT_TRUCK_IN:
				case Constants.STAT_DRIVE_IN:
				case Constants.STAT_PLANT_IN:
					//Save the vehicle information
					vehicle.updateVehicleLocation(bayLocation);
					vehicle.Save();
					
					//If the vehicle is on Hold then leave it in Hold status
					if(vehicle.getStatusCode() == null || vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_PARKED);
					
					//Set the vehicle moving to false
					vehicle.setVINMoving(false);
					
					//Update Mainframe
					TruckInMessage tim = new TruckInMessage();
					tim.setSystemSettings(systemSettings);
					tim.setVehicle(vehicle);
					m_outboundjournal.setType("TruckIN");
					m_outboundjournal.setData(tim.getXML());
					m_outboundjournal.setChngWho(vehicle.getChngWho());
					m_outboundjournal.setChngDate(Common.getCurrentDateTime());
					m_outboundjournal.setStatus("Q");
					m_outboundjournal.save();
					break;
				case Constants.STAT_ARRIVED_RAIL:
				case Constants.STAT_CANCEL_ASSIGNED:
					//If the vehicle is on Hold then leave it in Hold status
					if(vehicle.getStatusCode() == null || vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_ASSIGNED);
					break;
				default:
					//If the vehicle is on Hold then leave it in Hold status
					if(vehicle.getStatusCode() == null || vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_PARKED);			
					break;
				}
			}

			//Save the vehicle information
			vehicle.Save();			
		}
		return bayLocation;
	}
	
	/**
	 *Park vehicles that require special handling.
     *These will be VINs that arrive some way other than rail
	 * @param vehicle
	 * @param lot
	 * @param zone
	 * @param row
	 * @param vType
	 * @return Location
	 * @throws Exception 
	 */
	private Location BaySiteSpecific(Vehicle vehicle, String Lot, String Zone, String Row, VehicleType vType) throws Exception {
		Location bayLocation = null;
		Integer Spot = 0;
		String DealerZone = null;
		
		switch(Lot) {
			case Constants.LOADLINE_LOT:
				bayLocation = GetEmptyLLSpot(vehicle.getManufacturer(),vehicle.getRoute(),Lot,Zone,Row,Spot,vType,Zone);
				if(bayLocation != null && bayLocation.getLot() == null) bayLocation.setLot(Constants.LOADLINE_LOT);
				break;
			case Constants.TRUCK_LOT:
				
				//Try to get the zone from the dealer (determine whether to use lines or zone baying)
				DealerZone = GetDealerZoneInfo(vehicle.getManufacturer(),vehicle.getDealer(),Zone);
				
				if(DealerZone != null ) {					
					//If Zone is not passed use the Dealer Zone preference
					if(Zone == null) Zone = DealerZone;
					
					//found zone for the dealer, use zone from vehicle type for assignment
					bayLocation = GetEmptyConvoySpot(Lot,Zone,Row,Spot);										
				} else {
					//use convoy loadline logic, get master dealer
					bayLocation = GetConvoyLineSpot(vehicle, Lot, Zone, Row, Spot, vType, Zone);										
				}
				if(bayLocation != null && bayLocation.getLot() == null) bayLocation.setLot(Constants.TRUCK_LOT);
				break;
			case Constants.STORE_LOT:
				bayLocation = GetConvoyLineSpot(vehicle,Lot,Zone,Row,Spot,vType,Zone);
				if(bayLocation != null && bayLocation.getLot() == null) bayLocation.setLot(Constants.STORE_LOT);
				break;
		}
		
		//No location was found
		if(bayLocation == null) {
			switch(Lot) {
				case Constants.LOADLINE_LOT:
					throw(new Exception("Loadline zone " + Zone + " is full"));
			case Constants.TRUCK_LOT:
					throw(new Exception("Convoy zone " + Zone + " is full"));
				case Constants.STORE_LOT:
					throw(new Exception("Storage zone " + Zone + " is full"));
			}
		} else {
			//Update tblVIN with new location, if no error occurred
			vehicle.updateVehicleLocation(bayLocation);
			vehicle.setChngDate(new Date());
			
			switch(vehicle.getStatusCode()) {
				case Constants.STAT_TRUCK_IN:
				case Constants.STAT_DRIVE_IN:
				case Constants.STAT_PLANT_IN:
					switch(vehicle.getStatusCode()) {
						case Constants.STAT_TRUCK_IN:
							if(vehicle.getActionCode() != Constants.ACTION_DROPZONE) vehicle.setActionCode(Constants.ACTION_TRUCK_IN);
							break;
						case Constants.STAT_DRIVE_IN:
							vehicle.setActionCode(Constants.ACTION_DRIVE_IN);
							break;
						case Constants.STAT_PLANT_IN:
							vehicle.setActionCode(Constants.ACTION_PLANT_IN);
							break;
						default:
							break;
					}
					
					//Generate origin record and save
					vehicle.Save();
					
					//Update Mainframe
					TruckInMessage tim = new TruckInMessage();
					tim.setSystemSettings(systemSettings);
					tim.setVehicle(vehicle);
					m_outboundjournal.setType("TruckIN");
					m_outboundjournal.setData(tim.getXML());
					m_outboundjournal.setChngWho(vehicle.getChngWho());
					m_outboundjournal.setChngDate(Common.getCurrentDateTime());
					m_outboundjournal.setStatus("Q");
					m_outboundjournal.save();
					
					//Change status to parked and show it there, not a move
                    //		Also if the vehicle is on hold leave it in Hold status.
					if(vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_PARKED);
					vehicle.setVINMoving(false);
					break;
				case Constants.STAT_ARRIVED_RAIL:
				case Constants.STAT_CANCEL_ASSIGNED:
					vehicle.setActionCode(Constants.ACTION_ASSIGN_TO_LOCATION);
					//If the vehicle is on hold leave it in Hold status.
					if(vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_ASSIGNED);
					break;
				default:
					if(vehicle.getActionCode() == null) vehicle.setActionCode(Constants.ACTION_BAY_SITE_SPECIFIC);
					//If the vehicle is on hold leave it in Hold status.
					if(vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_PARKED);
					break;
			}
			
			//Save the vehicle information
			vehicle.Save();
		}
		
		return bayLocation;
	}
	
	private void BayVehicle(Vehicle vehicle) throws Exception {
		
		if(vehicle.getStatusCode().equals(Constants.STAT_ASSIGNED)) {
			vehicle.setArrivalDate(new Date());
		}
		vehicle.setStatusCode(Constants.STAT_PARKED);
		//vehicle.setChngWho(m_user);
		vehicle.setChngDate(new Date());
		vehicle.Save();
	}
	
	/**
	 * Check to see if the VIN has been defined as one requiring special handling
	 * @param vehicle
	 * @return
	 * @throws Exception 
	 */
	private Location CheckSiteSpecific(Vehicle vehicle) throws Exception {
		Location bayLocation = null;
		VehicleType vType;
		ProdStat prodstat;
		SpecRoute specroute;
		SpecDealer specdealer;
		Upfitter upfitter;
		
		//Make sure the vehicle has a Route and a Dealer
		if(vehicle.getRoute() == null && vehicle.getDealer() == null) {
			throw(new Exception("MISSING_DEALER_AND_ROUTE_ERROR"));
		}
		
		//Handle production status codes (first)
		vType = GetVehicleType(vehicle);
		prodstat = m_prodstat.get(vehicle.getProdStatus());
		
		if(prodstat != null && prodstat.getZone() != null ) {
			bayLocation = ParkByVTypeProdStat(vehicle, vType, prodstat);
			if(bayLocation != null) return bayLocation;
		}
		
		//Handle special routes (second)
		specroute = m_specroute.get(vehicle.getManufacturer(), vehicle.getRoute());
		if(specroute != null && specroute.getZone() != null ) {
			bayLocation = ParkBySpecialRoute(vehicle,vType,specroute);
			if(bayLocation != null) return bayLocation;
		}		
		
		//Handle special dealers (third)
		specdealer = m_specdealer.get(vehicle.getManufacturer(), vehicle.getDealer());
		if(specdealer != null && specdealer.getZone() != null ) {
			bayLocation = ParkBySpecialDealer(vehicle,vType, specdealer);
			if(bayLocation != null) return bayLocation;
		}	
		
		//Handle upfitters (fourth)
		if(systemSettings.getUseUpfitter() && vehicle.getOutboundUpfitter() != null ) {
			//Now get the lot information for the upfitter
			UpfitterSearchCriteria usc = new UpfitterSearchCriteria();
			usc.setSearchType(eUpfitter.eFindUpfitterVIN);
			usc.setManufacturer(vehicle.getManufacturer());
			usc.setCode(vehicle.getOutboundUpfitter());
			upfitter = m_upfitter.get(usc);
			
			if(upfitter != null && upfitter.getLot() != null  ){
				bayLocation = ParkByUpfitter(vehicle,vType,upfitter);
				if(bayLocation != null) return bayLocation;
			}			
		}		
		
		//Handle vehicle type information (fifth)
		if(vehicle.getRoute() != null  && !IsAtDestinationRamp(vehicle.getManufacturer(),vehicle.getRoute())){
			//Needs to go back out rail to get to destination, so send to loadline
			//Rail side			
			
			//See if a rail preference was specified
			if(vType != null && vType.getRailZoneID() != null ) {
				bayLocation = ParkByVTypeLoadLine(vehicle,vType);
			}
		} else {
			//Truck side
			if(vType != null && vType.getTruckZone() != null ) {
				bayLocation = ParkByVTypeConvoy(vehicle,vType);
			}			
		}
			
		return bayLocation;
	}
	
	private Location GetConvoyLineSpot(Vehicle vehicle, String Lot, String Zone, String Row, int Spot, VehicleType vType, String StartZone) throws SQLException {
		return GetConvoyLineSpot(vehicle,Lot,Zone,Row,Spot,vType,StartZone,true);
	}

	/**
	 * Use Convoy load line processing to park vehicle
	 * @param vehicle
	 * @param Lot
	 * @param Zone
	 * @param Area
	 * @param Spot
	 * @param vType
	 * @param StartZone
	 * @return Vehicle
	 * @throws SQLException 
	 */
	private Location GetConvoyLineSpot(Vehicle vehicle, String Lot, String Zone, String Row, int Spot, VehicleType vType, String StartZone, boolean AllowOverflow) throws SQLException {
		Location bayLocation = null;
		
		//Look for a line with dealer first
		bayLocation = GetConvoySpotByDealer(vehicle.getManufacturer(),vehicle.getDealer(),Lot,Zone,Row,Spot,vType);
		
		if(bayLocation != null) {
			return bayLocation;
		} else {
			if(AllowOverflow) {
				//Get the Overflow sequence
				List<OverflowItem> overflowSeq = m_overflow.GetOverflowSeq(Lot, Zone);
				
				for(OverflowItem seq : overflowSeq) {
					switch(seq.getLot()) {
						case Constants.TRUCK_LOT:
							bayLocation = GetConvoySpotByDealer(vehicle.getManufacturer(),vehicle.getDealer(),seq.getLot(),seq.getZone(),Row,Spot,vType);
							if(bayLocation != null) return bayLocation;
							break;
						case Constants.STORE_LOT:
							bayLocation = GetEmptyStoreSpot(seq.getLot(),seq.getZone(),Row,Spot);
							if(bayLocation != null) return bayLocation;
							break;
					}
				}
				
				//Did not find a spot
				if(bayLocation == null) {
					//Check for Auto Assign Dealers or "Lane Stealing"
					if(systemSettings.getAutoAssignDealers()) {
						
						bayLocation = GetConvoySpotByDealer2(vehicle.getManufacturer(),vehicle.getDealer(),Lot,Zone,Row,Spot);
						if(bayLocation != null) {
							return bayLocation;
						} else {
							//Look in overflow
							int seqNum = 1;
							for(OverflowItem seq : overflowSeq) {
								if(seq.getLot() == Constants.TRUCK_LOT) {
									if(seqNum < overflowSeq.size()) {
										bayLocation = GetConvoySpotByDealer2(vehicle.getManufacturer(),vehicle.getDealer(),seq.getLot(),seq.getZone(),Row,Spot);
										if(bayLocation != null) return bayLocation;										
									} else {
										//   'IMPORTANT: if we have not found a line in the convoy area with correct dealer,
										//	 'type then we must assume that the last zone is an overflow area and
		                    			//	 'go ahead and park it there without checking dealer/vehicle type									
										bayLocation = GetEmptyConvoySpot(seq.getLot(),seq.getZone(),Row,Spot);
										if(bayLocation != null) return bayLocation;
									}
									seqNum++;
								} 								
							}
						}
					} else {
						//No lane stealing
						//Find last overflow sequence
						if(overflowSeq.size() > 0) {
							OverflowItem seq = overflowSeq.get(overflowSeq.size());
							if(seq.getLot() == Constants.TRUCK_LOT) {
                    			//	'IMPORTANT: if we have not found a line in the convoy area with correct dealer,
                    			//	 type then we must assume that the last zone is an overflow area and
                    			//	'go ahead and park it there without checking dealer/vehicle type
								//If GetEmptyConvoySpot(sLotCode, sZone, sArea, iSpot) Then
								bayLocation = GetEmptyConvoySpot(seq.getLot(),seq.getZone(),Row,Spot);
								if(bayLocation != null) return bayLocation;
							}
						}
					}
				}				
			}			
		}

		return bayLocation;
	}
	
	/**
	 * Finds an empty spot for a vehicle by using the Dealer baying information
	 * @param Manufacturer
	 * @param Dealer
	 * @param Lot
	 * @param Zone
	 * @param Area
	 * @param Spot
	 * @param vType
	 * @return boolean
	 * @throws SQLException 
	 */
	private Location GetConvoySpotByDealer(String Manufacturer, String Dealer, String Lot, String Zone, String Row, int Spot, VehicleType vType) throws SQLException {
		Location bayLocation = null;
		SortOrderItem soi;
		
		//Retrieve Sort Order
		soi = m_sortorderitem.get(Lot, Zone);		
		
		m_location.setZone(Zone);
		m_location.setManufacturer(Manufacturer);
		m_location.setDealer(Dealer);
		m_location.setRowOrder(soi.getRowOrder());
		m_location.setSpotOrder(soi.getSpotOrder());

		//Look for rows with a dealer that matches the vehicle then look for empty rows
		List<Location> locations = m_location.getLocationList("spGetConvoySpotByDealer");

		for(Location l : locations) {
			if(CheckFillFactor(l.getLot(),l.getZone(),l.getRow(),vType.getFillFactor())) {
				bayLocation = l;
				break;
			}
		}	
		
		return bayLocation;
	}
	
	/**
	 * Finds an empty spot for a vehicle by using the Dealer baying information
	 * @param Manufacturer
	 * @param Dealer
	 * @param Lot
	 * @param Zone
	 * @param Row
	 * @param Spot
	 * @return
	 * @throws SQLException 
	 */
	private Location GetConvoySpotByDealer2(String Manufacturer, String Dealer, String Lot, String Zone, String Row, int Spot) throws SQLException {
		Location bayLocation = null;
		List<Location> locations;
		SortOrderItem soi;
		
		//Retrieve Sort Order
		soi = m_sortorderitem.get(Lot, Zone);
		
		//Look for empty rows without a Dealer assigned
		m_location.setZone(Zone);
		m_location.setRowOrder(soi.getRowOrder());
		m_location.setSpotOrder(soi.getSpotOrder());
		locations = m_location.getLocationList("spGetEmptyConvoyRowsNoDealer");
		
		//Assign the first bay location found
		if(locations != null && locations.size() > 0) {
			//Update the bayLocation
			bayLocation = locations.get(0);
			
			//Update the row with the Dealer
			bayLocation.setLocation("UPDATE_BAYROW_DEALER");
		}

		return bayLocation;
	}
	
	private Location GetEmptyConvoySpot(String Lot, String Zone, String Row, int Spot) throws SQLException {
		return GetEmptyConvoySpot(Lot,Zone,Row,Spot,true,true);
	}

	private Location GetEmptyConvoySpot(String Lot, String Zone, String Row, int Spot, boolean AllowOverflow) throws SQLException {
		return GetEmptyConvoySpot(Lot,Zone,Row,Spot,AllowOverflow,true);
	}

	private Location GetEmptyConvoySpot(String Lot, String Zone, String Row, int Spot, boolean AllowOverflow, boolean EndRow) throws SQLException {
		List<Location> locations;
		Location bayLocation = null;
		
		SortOrderItem sortorderitem = RetrieveZoneSortOrder(Lot,Zone);

		//Set the location search
		m_location.setZone(Zone);
		m_location.setRow(Row);
		m_location.setRowOrder(sortorderitem.getRowOrder());
		m_location.setSpotOrder(sortorderitem.getSpotOrder());
		m_location.setEndBay(true);

		if(Row != null ) {
			//Look in the Zone and Row
			locations = m_location.getLocationList("spGetEmptySpotsConvoyArea2");
		} else {
			//Look in the entire Zone
			locations = m_location.getLocationList("spGetEmptySpotsConvoy2");
		}
		
		if(locations != null && locations.size() > 0) {
			bayLocation = locations.get(0);
			bayLocation.setLot(Constants.TRUCK_LOT);
		} else if (AllowOverflow) {
			//Get the Overflow sequence
			List<OverflowItem> overflowSeq = m_overflow.GetOverflowSeq(Lot, Zone);
			
			if(overflowSeq != null) {
				for(OverflowItem seq : overflowSeq) {
					switch(seq.getLot()) {					
						case Constants.TRUCK_LOT:
							bayLocation = GetEmptyConvoySpot(seq.getLot(),seq.getZone(),Row,Spot,false,EndRow);
							if(bayLocation != null) {
								bayLocation.setLot(seq.getLot());
								return bayLocation;
							}
							break;
						case Constants.STORE_LOT:
							bayLocation = GetEmptyStoreSpot(seq.getLot(),seq.getZone(),Row,Spot,false);
							if(bayLocation != null) {
								bayLocation.setLot(seq.getLot());
								return bayLocation;
							}
							break;
					}
				}
			}
		}

		return bayLocation;
	}

	private Location GetEmptyLLSpot(String Manufacturer, String Route, String Lot, String Zone, String Row, int Spot, VehicleType vType, String StartZone) throws Exception {
		return GetEmptyLLSpot(Manufacturer, Route, Lot, Zone, Row, Spot, vType, StartZone,true);
	}

	/**
	 * Find an empty location in a load line where the load line vehicle type matches the vehicles type
	 * @param Manufacturer
	 * @param Route
	 * @param Lot
	 * @param Zone
	 * @param Row
	 * @param Spot
	 * @param vType
	 * @param StartZone
	 * @param AllowOverflow
	 * @return Location
	 * @throws Exception 
	 */
	private Location GetEmptyLLSpot(String Manufacturer, String Route, String Lot, String Zone, String Row, int Spot, VehicleType vType, String StartZone, boolean AllowOverflow) throws Exception {
		Location bayLocation = null;
		
		//If the Zone was not passed in then get it from the vehicle type baying preference for Zone
		if(Zone == null) {
			if(vType == null || vType.getRailZoneID() == null) {
				//Vehicle type does not have a preference.
				//Use the Route bay zone preference
				Zone = GetRouteZoneInfo(Manufacturer,Route,Zone);
				
				//If there is not baying preference then exit 
				if(Zone == null) return null;
			} else {
				Zone = vType.getRailZoneID();
			}
		}
		
		//Check for empty load line spot that fits vehicle type and route in the specified zone
		bayLocation = GetLLSpotByVehTypeRouteZone(Manufacturer, Route, Lot, Zone, Row, Spot, vType);
		if(bayLocation == null) {
			//Did not find vehicle type/route match,
        	//	check for empty loadline with matching route
			//If Not GetUnassignedLLSpot(sMfg, sRoute, sLotCode, sZone, sArea, iSpot, oVtype) Then
			bayLocation = GetUnassignedLLSpot(Manufacturer, Route, Lot, Zone, Row, Spot, vType);
			
			//Check for empty load lines in this zone
			if(bayLocation == null) {
				if(systemSettings.getAutoAssignRoutes()) {
					bayLocation = GetLLSpotEmptyLine(Manufacturer, Route, Lot, Zone, Row, Spot, vType);					
				}
			}			
		}
		
		if(bayLocation == null && AllowOverflow) {
			//No empty loadlines with matching route.
			//Will now search in overflow
			
			//Get the overflow sequence
			List<OverflowItem> overflowSeq = m_overflow.GetOverflowSeq(Lot, Zone);
			
			for(OverflowItem seq : overflowSeq) {
				switch(seq.getLot()) {
					case Constants.LOADLINE_LOT:
						//Check for empty loadline spot that fits vehicle type and route in specified zone
						bayLocation = GetLLSpotByVehTypeRouteZone(Manufacturer,Route,Lot, Zone, Row, Spot, vType);
						
						if(bayLocation == null) {
        					//Check for totally empty loadline with matching route
							bayLocation = GetLLSpotByRouteZone(Manufacturer, Route, Lot, Zone, Row, Spot, vType);
							
							if(bayLocation == null) {
	    						//Check for space in loadlines with no route assignment
								bayLocation = GetUnassignedLLSpot(Manufacturer, Route, Lot, Zone, Row, Spot, vType);
								
								if(bayLocation == null) {
									//Check for empty load lines in this zone
									if(systemSettings.getAutoAssignRoutes()) {
										bayLocation = GetLLSpotEmptyLine(Manufacturer, Route, Lot, Zone, Row, Spot, vType);
									}
								}
							}
						}
						if(bayLocation != null && bayLocation.getLot() == null) {
							//Add the lot
							bayLocation.setLot(Constants.LOADLINE_LOT);
						}
						break;
					case Constants.STORE_LOT:
						bayLocation = GetEmptyStoreSpot(Lot, Zone, Row, Spot, false);
						if(bayLocation != null && bayLocation.getLot() == null) {
							//Add the lot
							bayLocation.setLot(Constants.STORE_LOT);
						}
						break;
					case Constants.TRUCK_LOT:
						bayLocation = GetEmptyConvoySpot(Lot, Zone, Row, Spot, false);
						bayLocation = GetEmptyStoreSpot(Lot, Zone, Row, Spot, false);
						if(bayLocation != null && bayLocation.getLot() == null) {
							//Add the lot
							bayLocation.setLot(Constants.TRUCK_LOT);
						}
						break;
					default:
						break;
				}
			}
		}
				
		return bayLocation;
	}	

	
	private Location GetEmptyStoreSpot(String Lot, String Zone, String Row, int Spot) throws SQLException {		
		return GetEmptyStoreSpot(Lot,Zone,Row,Spot,true);
	}

	/**
	 * Find an empty spot in the storage zone
	 * @param Lot
	 * @param Zone
	 * @param Row
	 * @param Spot
	 * @param AllowOverflow
	 * @return
	 * @throws SQLException 
	 */
	private Location GetEmptyStoreSpot(String Lot, String Zone, String Row, int Spot, boolean AllowOverflow) throws SQLException {
		List<Location> locations;
		Location bayLocation = null;
		m_location.setZone(Zone);
		m_location.setRow(Row);
		
		//Find first available spot in storage area (no overflow for now)
		if(Row != null && Row.length() > 0 ) {
			locations = m_location.getLocationList("spGetEmptyStorageArea");
		} else {
			locations = m_location.getLocationList("spGetEmptyStorageZone");
		}

		if(locations != null && locations.size() > 0) {
			//Assign the first location
			bayLocation = locations.get(0);
			bayLocation.setLot(Constants.STORE_LOT);
			return bayLocation;
			
		} else if(AllowOverflow) {
			//Find a spot in the overflow
			List<OverflowItem> overflowSeq = m_overflow.GetOverflowSeq(Lot, Zone);
			
			//Loop until an empty spot is found
			for(OverflowItem seq : overflowSeq) {
				if( seq.getLot() == Constants.TRUCK_LOT) {
					bayLocation = GetEmptyConvoySpot(Lot, Zone,Row,Spot,false,false);
					if(bayLocation != null) {
						bayLocation.setLot(seq.getLot());
						return bayLocation;					
					}
				} else if (seq.getLot() == Constants.STORE_LOT) {
					bayLocation = GetEmptyStoreSpot(Lot,Zone,Row,Spot,false);
					if(bayLocation != null) {
						bayLocation.setLot(seq.getLot());
						return bayLocation;
					}
				}
			}
		}
		
		return bayLocation;
	}

	/**
	 * Find empty loadline in a given zone that matches route
	 * @param Manufacturer
	 * @param Route
	 * @param Lot
	 * @param Zone
	 * @param Row
	 * @param Spot
	 * @param vType
	 * @return Location
	 * @throws SQLException 
	 */
	private Location GetLLSpotByRouteZone(String Manufacturer, String Route, String Lot, String Zone, String Row, int Spot, VehicleType vType) throws SQLException {
		Location bayLocation = null;
		
		m_location.setZone(Zone);
		m_location.setManufacturer(Manufacturer);
		m_location.setRoute(Route);
		List<Location> locations = m_location.getLocationList("spGetEmptyLLRouteZone");
		
		for(Location location : locations) {
			//found one, so load information to be used from calling procedure
			
			//Have to make sure that we aren't dealing with a vehicle type that
            //	fits less than the standard number of spots.
			if(CheckFillFactor(location.getLot(),location.getZone(),location.getRow(),vType.getFillFactor())){
				if(CheckQuota(location.getZone(),location.getRow(),vType.getCode())) {
					//Found a spot
					bayLocation = location;
					break;
				}
			}
		}		
		return bayLocation;
	}

	/**
	 * Find an empty loadline spot that matches Vehicle Type and Route
	 * @param manufacturer
	 * @param lot
	 * @param zone
	 * @param row
	 * @param spot
	 * @param vType
	 * @return
	 * @throws SQLException 
	 */
	private Location GetLLSpotByVehTypeRouteZone(String Manufacturer, String Route, String Lot, String Zone, String Row, int Spot, VehicleType vType) throws SQLException {
		Location bayLocation = null;
		List<Location> locations;

	    //Check for loadlines that are the same vehicle type, zone specified
		m_location.setZone(Zone);
		m_location.setVehicleType(vType.getCode());
		m_location.setManufacturer(Manufacturer);
		m_location.setRoute(Route);
		locations = m_location.getLocationList("spGetEmptyLLByZoneVType");
		
		//Loop through the locations
		for(Location location : locations) {
			if(CheckFillFactor(location.getLot(),location.getZone(),location.getRow(),vType.getFillFactor())) {
				if(CheckQuota(location.getZone(),location.getRow(),location.getVehicleType())){
					//Found a location so return
					bayLocation = location;
					break;
				}
			}
		}

		return bayLocation;
	}

	/**
	 *Try to find a completely empty load line
     *and assign this route to it and get the first spot
	 * @param Manufacturer
	 * @param Route
	 * @param Lot
	 * @param Zone
	 * @param Row
	 * @param Spot
	 * @param vType
	 * @return
	 * @throws SQLException 
	 */
	private Location GetLLSpotEmptyLine(String Manufacturer, String Route, String Lot, String Zone, String Row, int Spot, VehicleType vType) throws SQLException {
		Location bayLocation = null;
		List<Location> locations = null;
		
		//First look for an empty line in this zone with this route
		m_location.setZone(Zone);
		locations = m_location.getLocationList("spGetEmptyLineNoRoute");
		
		for(Location location : locations) {			
			//Should not need to check fill factor since it is empty
			bayLocation = location;
			//Add the lot
			bayLocation.setLot(Constants.LOADLINE_LOT);
			break;
		}
		
		if(bayLocation == null) {
			//Second look for an empty line in this zone with a different route
			locations = m_location.getLocationList("spGetEmptyLineAnyRoute");
			
			for(Location location : locations) {
				//Should not need to check fill factor since it is empty
				bayLocation = location;
				//Add the lot
				bayLocation.setLot(Constants.LOADLINE_LOT);
				break;
			}
		}
		
		//Now we need to update the load line with route
		if(bayLocation != null) {
			bayLocation.setManufacturer(Manufacturer);
			bayLocation.setRoute(Route);
			bayLocation.setActive(true);
			
			//bayLocation.setChngWho("current user");
			bayLocation.setChngDate(new Date());
			bayLocation.setLocation("UpdLLine");
		}
		
		return bayLocation;
	}
	
	/**
	 * Look for empty spots in loadline that match vehicle types
	 * 
	 * //This function is on the site specific processing side
	 * Change Desc: This method is used to add look for a totally empty load line with 
	 * 				no route assignment.  If one is not found, it then look for a 
	 * 				partially empty load line with no route assignment but has VINs that 
	 * 				match the given vehicle type and mix with the given route.
	 * @param manufacturer
	 * @param route
	 * @param lot
	 * @param zone
	 * @param row
	 * @param spot
	 * @param vType
	 * @return
	 * @throws SQLException 
	 */
	private Location GetUnassignedLLSpot(String Manufacturer, String Route, String Lot, String Zone, String Row, int Spot, VehicleType vType) throws SQLException {
		Location bayLocation = null;
		List<Location> locations = null;

		//Find partially empty loadline or totally empty load line in a given Zone 
		//	that matches input route and vehicle type 
		//	where the load line does not have a route assignment
		m_location.setZone(Zone);
		m_location.setVehicleType(vType.getCode());
		m_location.setManufacturer(Manufacturer);
		m_location.setRoute(Route);
		locations = m_location.getLocationList("spGetUnassignedLLSpotByVehTypeRouteZone");
		
		for(Location location : locations) {
			//Found one, so load information to be used from calling procedure
			
			//Have to make sure that we aren't dealing with a vehicle type that
            //fits less than the standard number of spots
			if(CheckFillFactor(location.getLot(),location.getZone(),location.getRow(),vType.getFillFactor())) {
				if(CheckQuota(location.getZone(),location.getRow(),vType.getCode())) {
					//Found a spot
					bayLocation = location;
					//Add the lot
					bayLocation.setLot(Constants.LOADLINE_LOT);
					break;
				}
			}
		}
		
		//If no location found so look for an empty location in the Zone
		if(bayLocation == null) {
			locations = m_location.getLocationList("spGetEmptyUnassignedLLSpot");
			
			for(Location location : locations) {
				//found one, so load information to be used from calling procedure
				bayLocation = location;
				//Add the lot
				bayLocation.setLot(Constants.LOADLINE_LOT);
				break;
			}		

		}
		return bayLocation;
	}
	
	private void HotLoadProcess(Vehicle vehicle, Railspot railspot) throws Exception {
		
		vehicle.setActionCode(Constants.ACTION_HOTLOAD);
		BayVehicle(vehicle);
		
		//Send a railcar park message
		//m_oMFTransactions.GenRailcarParkMsg oRail, oVINs
		
		//check to see if the whole railcar has been hot loaded 
		//and if so then send a release empty message
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		vsc.setSearchType(eVINObjAction.eVINFindInboundRailCar);
		vsc.setInboundRailcarNumber(railspot.getRailcarNumber());
		List<Vehicle> vehicles = m_vehicle.getVehicleList(vsc);
		
		if(vehicles != null) {
			if(vehicles.isEmpty()) {
				//m_oMFTransactions.GenRailcarReleaseEmptyMsg oRail, oVINs
				railspot.setLoadEmptyStatus(Constants.LE_STATUS_EMPTY);
				railspot.setHeadLight("");
				railspot.setLTD(false);
				railspot.setSwitchInstructions("");
				railspot.setBlockToInstrucions("");
				railspot.setStatusCode(Constants.STAT_RAIL_PARKED);
				railspot.Save();
				railspot.setStatusCode(Constants.STAT_RAIL_RMTY);
				railspot.SaveHistory();
				railspot.setStatusCode(Constants.STAT_RAIL_PARKED);
			}
		}
	}
	
	//check to see if it's ok to load a VIN on a railcar
	private boolean isOKToLoad(Vehicle vehicle, String manufacturer, String route) throws SQLException {
		String vmfg, vrte;
		
		vmfg = vehicle.getManufacturer();
		vrte = vehicle.getRoute();
		
		if(vmfg.equals(manufacturer) && vrte.equals(route)){
			return true;
		}
		
		//'get list of routes that are associated with the railcar route
		RouteSearchCriteria rsc = new RouteSearchCriteria();
		rsc.setSearchType(eRouteObjAction.eRouteFindRailcarAssoc);
		List<Route> routes = m_route.getRouteList(rsc);
		
		for(Route r : routes) {
			if(vmfg.equals(manufacturer) && vrte.equals(r)) {
				return true;
			}
		}
		
		return false;
	}
	
	private Location ParkBySpecialDealer(Vehicle vehicle, VehicleType vType, SpecDealer specdealer) throws Exception {
		Location bayLocation = null;
		String Lot, Zone, Row;
		
		Lot = specdealer.getLot();
		Zone = specdealer.getZone();
		Row = specdealer.getRow();
		
		//if special handling is required, the zone field will be filled out
		if(vehicle.getLotCode() == Constants.TRACK_LOT && vehicle.getPrevStatusCode() != Constants.STAT_LOADED) {
			//VINs coming from railcars are the only ones that are assigned
            //		provided they aren't being unloaded, that is
			bayLocation = PreBaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		} else {
			bayLocation = BaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		}		
		return bayLocation;
	}
	
	private Location ParkBySpecialRoute(Vehicle vehicle, VehicleType vType, SpecRoute specroute) throws Exception {
		Location bayLocation = null;
		String Lot, Zone, Row;
		
		Lot = specroute.getLot();
		Zone = specroute.getZone();
		Row = specroute.getRow();
		
		//If special handling is required, the zone field will be filled out
		if(vehicle.getLotCode() == Constants.TRACK_LOT && vehicle.getStatusCode() != Constants.STAT_LOADED) {
			//VINs coming from railcars are the only ones that are assigned
            //		provided they aren't being unloaded, that is
			bayLocation = PreBaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		} else {
			bayLocation = BaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		}
		
		return bayLocation;
	}
	
	/**
	 * Upfitter specific parking
	 * @param vehicle
	 * @param vType
	 * @param upfitter
	 * @return Location
	 * @throws Exception 
	 */
	private Location ParkByUpfitter(Vehicle vehicle, VehicleType vType, Upfitter upfitter) throws Exception {
		Location bayLocation = null;
		String Lot, Zone = null, Row = null;
		
		Lot = upfitter.getLot();
		
		//Override any vehicle type preferences if there are upfitter zone / row preferences
		if(upfitter.getZone() != null  ) Zone = upfitter.getZone();
		if(upfitter.getRow() != null  ) Row = upfitter.getRow();
		
		//if special handling is required, the zone field will be filled out
		if(vehicle.getLotCode() == Constants.TRACK_LOT && vehicle.getStatusCode() != Constants.STAT_LOADED) {
			//VINs coming from railcars are the only ones that are assigned
            //		provided they aren't being unloaded, that is
			bayLocation = PreBaySiteSpecific(vehicle, Lot, Zone, Row, vType);			
		} else {
			bayLocation = BaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		}
		return bayLocation;
	}
	
	private Location ParkByUser(Vehicle vehicle) throws Exception {
		//Place holders in the old code for a location
		String Lot = null, Zone = null, Row = null;
		Location bayLocation = null;
		
		//Find the vehicle type for the vehicle
		VehicleType vType = GetVehicleType(vehicle);
		
		if(vehicle.getLotCode() == Constants.TRUCK_LOT && vehicle.getStatusCode() != Constants.STAT_LOADED) {
			bayLocation = PreBaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		} else {
			bayLocation = BaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		}
		
		return bayLocation;
	}
	
	/**
	 * Site specific parking for Convoy Lot
	 * @param vehicle
	 * @param vType
	 * @return
	 * @throws Exception 
	 */
	private Location ParkByVTypeConvoy(Vehicle vehicle, VehicleType vType) throws Exception {
		Location bayLocation = null;
		String Lot, Zone, Row;
		
		//Default to Truck 
		if(vType.getTruckLotCode() == null) vType.setTruckLotCode(Constants.TRUCK_LOT);
		
		//This may be recursive so set the initial values.
		//Set the Lot, Zone, and Row from the vehicle type
		Lot = vType.getTruckLotCode();
		Zone = vType.getTruckZone();
		Row = vType.getTruckArea();
		
		//If special handling is required, the zone field will be filled out
		if(vehicle.getLotCode() == Constants.TRACK_LOT && vehicle.getStatusCode() != Constants.STAT_LOADED) {
			//VINs coming from railcars are the only ones that are assigned
			bayLocation = PreBaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		} else {
			bayLocation = BaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		}
		
		return bayLocation;
	}
	

	private Location ParkByVTypeLoadLine(Vehicle vehicle, VehicleType vType) throws Exception {
		Location bayLocation = null;
		String Lot, Zone, Row;
		
		//Original comment 
		//found mask for VIN
		
		//Default to Loadline 
		if(vType.getTruckLotCode() == null) vType.setTruckLotCode(Constants.LOADLINE_LOT);
		
		//This may be recursive so set the initial values.
		//Set the Lot, Zone, and Row from the vehicle type
		Lot = vType.getRailLotCode();
		Zone = vType.getRailZoneID();
		Row = vType.getRailArea();
		
		//If special handling is required, the zone field will be filled out
		if(vehicle.getLotCode() == Constants.TRACK_LOT && vehicle.getStatusCode() != Constants.STAT_LOADED) {
			//VINs coming from railcars are the only ones that are assigned
            //		Provided they aren't being unloaded, that is
			bayLocation = PreBaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		} else {
			bayLocation = BaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		}
		return bayLocation;
	}
	
	private Location PrebayConvoy(Vehicle vehicle) throws Exception {
		Location bayLocation = null;
		String Lot = Constants.TRUCK_LOT;
		String Zone,Row;
		int Spot;
		boolean AllowOverflow = false;
		boolean EndRow = false;
		
		//Get the Dealer info
		Zone = GetDealerZoneInfo(vehicle.getManufacturer(),vehicle.getDealer(),vehicle.getZone());
		
		if(Zone != null ) {
			//found the dealer and has a Zone pref

			//get the first available convoy spot using overflow seq. if necessary
			if(vehicle.getStatusCode() == Constants.STAT_HOLD && vehicle.getHoldReasonCode() != null  && !systemSettings.getBayVINOnHold()) {
				//VIN has been placed on hold, can only be prebayed if there is no reason code
				throw(new Exception("PREBAY_VIN_ON_HOLD_ERROR"));
			} else {
				//Get the vehicle type for end row baying check
				VehicleType vType = GetVehicleType(vehicle);			
				if(vType != null && vType.getConvoyEndRow() != null && vType.getConvoyEndRow().equals("Y")) EndRow = true;
				
				//Set the Row and Spot in case they are null
				Row = vehicle.getRow();
				Spot = vehicle.getSpot() == null ? 0 : vehicle.getSpot();
				
				bayLocation = GetEmptyConvoySpot(Lot,Zone,Row,Spot,AllowOverflow,EndRow);
			}
		} else {
			//No Dealer information
			throw(new Exception("NO_DEALER_ZONE_ERROR"));
		}
		
		if(bayLocation == null) {
			//No spot was found
			throw(new Exception("Convoy zone " + Zone + " is full."));
		} else {
			//Update the vehicle with the location information
			vehicle.setLotCode(bayLocation.getLot());
			vehicle.setZone(bayLocation.getZone());
			vehicle.setRow(bayLocation.getRow());
			vehicle.setSpot(bayLocation.getSpot());
			
			//Update the vehicle location	
			if(vehicle.getStatusCode() != null) {
				switch(vehicle.getStatusCode()) {
					case Constants.STAT_TRUCK_IN:
					case Constants.STAT_DRIVE_IN:
					case Constants.STAT_PLANT_IN:
						switch(vehicle.getStatusCode()) {
							case Constants.STAT_TRUCK_IN:
								if(vehicle.getActionCode() != Constants.ACTION_DROPZONE) vehicle.setActionCode(Constants.ACTION_TRUCK_IN);
								break;
							case Constants.STAT_DRIVE_IN:
								vehicle.setActionCode(Constants.ACTION_DRIVE_IN);
								break;
							case Constants.STAT_PLANT_IN:
								vehicle.setActionCode(Constants.ACTION_PLANT_IN);
								break;
							default:
								break;
						}
						//Save the vehicle location
						vehicle.Save();

						//Update Mainframe
						TruckInMessage tim = new TruckInMessage();
						tim.setSystemSettings(systemSettings);
						tim.setVehicle(vehicle);
						m_outboundjournal.setType("TRUCKIN");
						m_outboundjournal.setData(tim.getXML());
						m_outboundjournal.setChngWho(vehicle.getChngWho());
						m_outboundjournal.setChngDate(Common.getCurrentDateTime());
						m_outboundjournal.setStatus("Q");
						m_outboundjournal.save();
						
						//If the vehicle is on Hold then leave it in Hold status
						if(vehicle.getStatusCode() == null || vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_PARKED);
						break;
					case Constants.STAT_ARRIVED_RAIL:
					case Constants.STAT_CANCEL_ASSIGNED:
						//If the vehicle is on Hold then leave it in Hold status
						if(vehicle.getStatusCode() == null || vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_ASSIGNED);						
						break;
					default:
						if(vehicle.getActionCode() == null) vehicle.setActionCode(Constants.ACTION_PREBAY_CONVOY);
						
						//If the vehicle is on Hold then leave it in Hold status
						if(vehicle.getStatusCode() == null || vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_PARKED);
						break;
				}
			} else {
				if(vehicle.getActionCode() == null) vehicle.setActionCode(Constants.ACTION_PREBAY_CONVOY);
				vehicle.setStatusCode(Constants.STAT_PARKED);
			}
			//Save the vehicle location
			vehicle.Save();
		}
		return bayLocation;
	}
	
	/**
	 * Make decision about where the vehicle will be parked
	 * @param vehicle
	 * @return vehicle
	 * @throws Exception 
	 */
	private Location PreBayDecision(Vehicle vehicle) throws Exception {
		Location bayLocation = null;
		boolean bayOnHold = false;
		boolean UseRoute = false;
		
	    //Check to see if baying on hold VINS is allowed.
		if(systemSettings != null) {
			bayOnHold = systemSettings.getBayVINOnHold();	
		}
		
		
		if(bayOnHold && vehicle.getStatusCode() == Constants.STAT_HOLD) {
			//Get the Holds on the vehicle
			Hold hold;
			
			HoldSearchCriteria hsc = new HoldSearchCriteria();
			hsc.setManufacturer(vehicle.getManufacturer());
			hsc.setHoldCode(vehicle.getHoldReasonCode());
			hold = m_hold.GetHolds(hsc).get(0);
			
			//Check to see if the Hold has baying preferences
			if(hold.getBayLot() != null ) {				
				//Find the vehicle type so you can use PreBaySiteSpecific
				VehicleType vType = GetVehicleType(vehicle);
				bayLocation = PreBaySiteSpecific(vehicle,hold.getBayLot(),hold.getBayZone(),hold.getBayRow(),vType);
				
				if(bayLocation != null) return bayLocation;
			}
		}
		
	    //Check to see if VIN will be put on a hold that should be bayed to a specific area in the lot
		bayLocation = PrebayToHoldArea(vehicle);
		if(bayLocation != null) return bayLocation;
	        
		if(vehicle.getStartZone() != null ) {
			bayLocation = ParkByUser(vehicle);
			if(bayLocation != null) return bayLocation;
		}
	    
	    //Determine how to assign location by using Route or Dealer
		if(vehicle.getManufacturer() != null && vehicle.getRoute() != null && IsAtDestinationRamp(vehicle.getManufacturer(), vehicle.getRoute())) {
			UseRoute = false;
			if(vehicle.getDealer() == null) {
				throw(new Exception("MISSING_DEALER_AND_ROUTE_ERROR"));
			}
		} else { 
			UseRoute = true;
		}
				
	    //Check to see if the VIN has been defined as one requiring special handling.
	    //		VINs can be restricted to specific zones or not, as needed.
	    //		VINs that are not site specific, are normally parked without a zone restriction
		bayLocation = CheckSiteSpecific(vehicle);
		if(bayLocation != null) return bayLocation;
	    
	    //VIN does not require special handling
	    //Have to determine which lot the vehicle is to be moved into
	    //	If route present, use route to assign location.
	    //	If route not present, use dealer to assign location.
	    
		if(UseRoute && vehicle.getVehicleType() != null) {
			
			//Check for Vehicle Type to make sure exists
			VehicleType vType = GetVehicleType(vehicle);
			if(vType == null) {
				throw(new Exception("VEHICLE_TYPE_NOT_FOUND_ERROR"));
			}
		}

		if(UseRoute) {
			//Needs to go back out rail to get to destination, so send to loadline
			bayLocation = BayLoadLine(vehicle);
		} else {
			//Move to convoy lot
			bayLocation = PrebayConvoy(vehicle);
		}
	    
		return bayLocation;
	}

	/**
	 * Site specific parking for loadline lot
	 * @param vehicle
	 * @param vType
	 * @param pStat
	 * @return
	 * @throws Exception 
	 */
	private Location ParkByVTypeProdStat(Vehicle vehicle, VehicleType vType, ProdStat pStat) throws Exception {
		Location bayLocation = null;
		String Lot, Zone, Row;
		
		Lot = pStat.getLot();
		Zone = pStat.getZone();
		Row = pStat.getRow();
		
		//If special handling is required, the zone field will be filled out
		if(vehicle.getLotCode() == Constants.TRACK_LOT && vehicle.getStatusCode() != Constants.STAT_LOADED) {
			//VINs coming from railcars are the only ones that are assigned
            //		provided they aren't being unloaded, that is
			bayLocation = PreBaySiteSpecific(vehicle, Lot, Zone, Row, vType);
		} else {
			bayLocation = BaySiteSpecific(vehicle, Lot, Zone, Row, vType);
			
			//Check to see if we should put this vehicle on hold now
			if(pStat.getHoldReasonCode() != null ) {
				vehicle.setHoldReasonCode(pStat.getHoldReasonCode());
				vehicle.setStatusCode(Constants.STAT_HOLD);
				vehicle.setChngDate(new Date());
				//Save 
				vehicle.Save();
			}
		}
		
		return bayLocation;
	}
	
	/**
	 * 	 Prebay VINs that require special handling these VINs will be coming off of a railcar
	 * @param vehicle
	 * @param baylot		Hold baying preferences
	 * @param bayzone		Hold baying preferences
	 * @param bayrow		Hold baying preferences
	 * @param vehicletype   
	 * @return
	 * @throws Exception 
	 */
	private Location PreBaySiteSpecific(Vehicle vehicle, String Lot, String Zone, String Row, VehicleType vType) throws Exception {
		//Status variables
		Location bayLocation = null;
		String DealerZone;
		int Spot = 0;

		switch(Lot){
			case Constants.TRUCK_LOT:								
				//Check to see if the Dealer has a Zone baying preference
				DealerZone = GetDealerZoneInfo(vehicle.getManufacturer(),vehicle.getDealer(),Zone);
				
				if(DealerZone != null ) {					
					//If Zone is not passed use the Dealer Zone preference
					if(Zone == null) Zone = DealerZone;
					
					//found zone for the dealer, use zone from vehicle type for assignment
					bayLocation = GetEmptyConvoySpot(Lot,Zone,Row,Spot);										
				} else {
					//use convoy loadline logic, get master dealer
					bayLocation = GetConvoyLineSpot(vehicle, Lot, Zone, Row, Spot, vType, Zone);										
				}
				
				if(bayLocation == null) throw(new Exception("Convoy zone " + vehicle.getStartZone() + " is full "));
				break;
			case Constants.LOADLINE_LOT:
				bayLocation = GetEmptyLLSpot(vehicle.getManufacturer(),vehicle.getRoute(),Lot,Zone,Row,Spot,vType,Zone);
				
				if(bayLocation == null) throw(new Exception("Load zone " + vehicle.getStartZone() + " is full "));
				break;
			case Constants.STORE_LOT:
				bayLocation = GetEmptyStoreSpot(Lot, Zone, Row, Spot);
				
				if(bayLocation == null) throw(new Exception("Store zone " + vehicle.getStartZone() + " is full "));
				break;
			default:
				//do default stuff
				break;
		}
		
		if(bayLocation != null) {
			//Update tblVIN with the new location
			
			if(vehicle.getStatusCode() == Constants.STAT_TRUCK_IN || vehicle.getStatusCode() == Constants.STAT_DRIVE_IN || vehicle.getStatusCode() == Constants.STAT_PLANT_IN) {
				//Generate arrival record and send to mainframe
				
				if(vehicle.getStatusCode() == Constants.STAT_TRUCK_IN && vehicle.getActionCode() != Constants.ACTION_DROPZONE) {
					vehicle.setActionCode(Constants.ACTION_TRUCK_IN);
				} else if(vehicle.getStatusCode() == Constants.STAT_DRIVE_IN) {
					vehicle.setActionCode(Constants.ACTION_DRIVE_IN);
				} else if(vehicle.getStatusCode() == Constants.STAT_PLANT_IN) {
					vehicle.setActionCode(Constants.ACTION_PLANT_IN);
				}
				
				//Save vehicle information
				vehicle.Save();
				
				//Update Mainframe
				TruckInMessage tim = new TruckInMessage();
				tim.setSystemSettings(systemSettings);
				tim.setVehicle(vehicle);
				m_outboundjournal.setType("TruckIN");
				m_outboundjournal.setData(tim.getXML());
				m_outboundjournal.setChngWho(vehicle.getChngWho());
				m_outboundjournal.setChngDate(Common.getCurrentDateTime());
				m_outboundjournal.setStatus("Q");
				m_outboundjournal.save();
			}
			
			if(vehicle.getStatusCode() == Constants.STAT_ARRIVED_RAIL || vehicle.getStatusCode() == Constants.STAT_CANCEL_ASSIGNED) {
				vehicle.setActionCode(Constants.ACTION_ASSIGN_TO_LOCATION);
				
				//If the vehicle is on hold and allowed to bay then keep Hold status
				if(vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_ASSIGNED);
			} else {
				if(vehicle.getActionCode() == null) vehicle.setActionCode(Constants.ACTION_PREBAY_SITE_SPECIFIC);
				
				//If the vehicle is on hold and allowed to bay then keep Hold status
				if(vehicle.getStatusCode() != Constants.STAT_HOLD) vehicle.setStatusCode(Constants.STAT_PARKED);
			}
			
			vehicle.Save();
		}
		return bayLocation;
	}
	
	
	/**
	 Purpose: This procedure will check to see if a vehicle is going to be put on hold after it is bayed.  
	 If so, it checks to see if any of the pending holds have a baying preference.  
	 If so, the VIN will be bayed according to the assigned Lot/Zone/Row.  
	 This procedure will quit after the first hold is found with an assignment preference set.
	 
	 * @param vehicle
	 * @return vehicle
	 * @throws Exception 
	 */
	private Location PrebayToHoldArea(Vehicle vehicle) throws Exception {
		Hold searchHold = null;
		HoldSearchCriteria hsc = new HoldSearchCriteria();
		Location bayLocation = null;
		
		//Get all of the current hold criterias
		HoldCriteriaSearchCriteria hcsc = new HoldCriteriaSearchCriteria();
		hcsc.setSearchType(eHoldCriteriaObjAction.eHoldCriteriaFindAll);
		List<HoldCriteria> holdcriterias = m_holdcriteria.GetHoldCriteria(hcsc);

		//Check to see if the vehicle matches any of the hold criteria
		for(HoldCriteria hc : holdcriterias) {
			if(MeetsHoldCriteria(hc,vehicle)) {
				//Look up the hold
				hsc.setSearchType(eHoldObjAction.eHoldFindMfgCode);
				hsc.setManufacturer(hc.getManufacturer());
				hsc.setHoldCode(hc.getHoldCode());
				searchHold = m_hold.GetHold(hsc);
				
				//Found the hold
				if(searchHold != null) {
					//Check for a baying preference
					if(searchHold.getBayLot() != null ) {
						//Look up the vehicle type
						VehicleType vType = GetVehicleType(vehicle);
						
						//Prebay to lot/zone/row
						bayLocation = PreBaySiteSpecific(vehicle,searchHold.getBayLot(),searchHold.getBayZone(),searchHold.getBayRow(),vType);
						
						//Found a location
						if(bayLocation != null) break;						
					}					
				}
			}
		}
		
		return bayLocation;
	}
	
	private boolean ReturnToPlantPerformed(Vehicle vehicle) {
		return vehicle.getReturnToPlantPerformed(vehicle.getVIN());
	}
	
	private Vehicle VINRailArrival(Vehicle pVIN, eVINObjAction findType) throws Exception {
		Vehicle vehicle = null;
		Railspot railspot;
		
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		RailspotSearchCriteria rsc = new RailspotSearchCriteria();
				
		vsc.setSearchType(findType);
		vsc.setVIN(pVIN.getVIN());

		rsc.setSearchType(eRailObjAction.eRailFindRailcarByLot);
		rsc.setLotCode(Constants.TRACK_LOT);

		if(findType == eVINObjAction.eVINFindVIN || findType == eVINObjAction.eVINFindVIN) {
			vehicle = m_vehicle.getVehicle(vsc);
		} 
		
		if(vehicle != null) {
			//Coming from the handheld, there is not info on the location of the railcar
			if(vehicle.getZone().length() < 1 || vehicle.getRow().length() < 1 || vehicle.getSpot() < 1) {
				rsc.setRailcarNumber(vehicle.getInboundRailcarNumber());
				railspot = m_railspot.GetRailSpot(rsc);

				vehicle.setZone(railspot.getZoneID());
				vehicle.setRow(railspot.getTrackNbr());
				vehicle.setSpot(railspot.getSpot());
			}
		} else {
			vehicle = pVIN;
			rsc.setRailcarNumber(pVIN.getInboundRailcarNumber());
			railspot = m_railspot.GetRailSpot(rsc);

			vehicle.setZone(railspot.getZoneID());
			vehicle.setRow(railspot.getTrackNbr());
			vehicle.setSpot(railspot.getSpot());
		}

		vehicle.setLotCode(Constants.TRACK_LOT);
		vehicle.setStatusCode(Constants.STAT_ARRIVED_RAIL);
		vehicle.setChngDate(new Date());
		vehicle.setVINMoving(false);
		vehicle.Save();
		
		return vehicle;
	}

	private boolean CheckFillFactor(String Lot, String Zone, String Row, double FillFactor) {
		
		if( Lot == Constants.TRUCK_LOT || Lot == Constants.LOADLINE_LOT) {
			Location checkLocation;

			m_location.setZone(Zone);
			m_location.setRow(Row);
			
			if(Lot == Constants.TRUCK_LOT) {
				checkLocation = m_location.getLocation("GET_CONVOY_AVAIL");
			} else {
				checkLocation = m_location.getLocation("GET_LL_AVAIL");
			}

			//Check to see if there is enough space in the row
			if( (checkLocation.getTotalSpots() - checkLocation.getUsedSpots()) >= FillFactor) return true;

		} else {
			return true;
		}
		
		return false;
	}
	
	private boolean CheckQuota(String zone, String row, String vehicleType) {
		QuotaSearchCriteria qsc = new QuotaSearchCriteria();
		qsc.setFromZone(zone);
		qsc.setFromLL(row);
		qsc.setvType(vehicleType);
		
		return m_quota.checkQuota(qsc);
	}

	private Location CheckVINForHoldCriteria(Vehicle vehicle) throws Exception {
		Location bayLocation = null;
		
		//Can only apply Holds to vehicles that are in Parked or Hold status
		if(vehicle.getStatusCode() != Constants.STAT_PARKED && vehicle.getStatusCode() != Constants.STAT_HOLD) {
			HoldCriteriaSearchCriteria hcsc = new HoldCriteriaSearchCriteria();
			hcsc.setSearchType(eHoldCriteriaObjAction.eHoldCriteriaFindAll);
			List<HoldCriteria> holdcriterias = m_holdcriteria.GetHoldCriteria(hcsc);
			
			for(HoldCriteria hc : holdcriterias) {
				if(MeetsHoldCriteria(hc,vehicle)) {
					//Set the Hold action
					vehicle.setActionCode(Constants.ACTION_HOLD_CRITERIA);
					
					//Don't send SYSTEM Holds to AMS
					if(hc.getChangWho() == null || hc.getChangWho() == "SYSTEM") {
						vehicle = HoldVINInPlace(vehicle, hc.getHoldCode(),false);
					} else {
						vehicle = HoldVINInPlace(vehicle, hc.getHoldCode(),true);
					}
				}
			}
		}		
		return bayLocation;
	}
	
	private String GetDealerZoneInfo(String Manufacturer, String Dealer, String Zone) throws Exception {
		String foundZone = null;
		Dealer dealer;
		
		dealer = m_dealer.getDealer(Manufacturer, Dealer);
		
		if(dealer == null) {
			throw(new Exception("DEALER_NOT_FOUND_ERROR"));
		} else if (dealer.getZone() != null) {
			foundZone = dealer.getZone();
		}
		
		return foundZone;
	}
	
	private String GetRouteZoneInfo(String Manufacturer, String Route, String Zone) throws Exception {
		String foundZone = null;
		Route route;
			
		route = m_route.getRoute(Manufacturer, Route);
		
		if(route != null && route.getZone() != null ) return route.getZone();
		
		if(route == null) {
			throw(new Exception("ROUTE_NOT_FOUND_ERROR"));
		} else if(route.getZone() == null) {
			throw(new Exception("ROUTE_WO_ZONE"));
		}
		
		return foundZone;
	}
	
	private VehicleType GetVehicleType(Vehicle vehicle) {
		VehicleType foundType = null;
		
		//Get  Vehicle Type
		if(vehicle.getVehicleType() == null) {
			//Lookup the vehicle type from VIN
			foundType = m_vehicletype.getVehicleTypeFromVIN(vehicle.getVIN());
			
			if(foundType != null && foundType.getCode() != null) vehicle.setVehicleType(foundType.getCode());
		} else {
			//Find the Vehicle Type that matches the String VType
			foundType = m_vehicletype.getVehicleType(vehicle.getVehicleType());
		}
		return foundType;
	}

	/**
	 * 'Generate arrival records for all VINs on the railcar
	 * @param railspot
	 * @throws Exception 
	 */
	private void GenerateArrival(Railspot railspot) throws Exception {
		List<Vehicle> vehicles;
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		String railstatus,vehiclestatus;
		
		//'Get collection of VINs on this railcar
		railstatus = railspot.getStatusCode();
		if(railstatus.equals(Constants.STAT_RAIL_RAILSHIPPED) || railstatus.equals(Constants.STAT_RAIL_LOADING) ) {
			//Outbound railcar
			vsc.setSearchType(eVINObjAction.eVINFindOutboundRailCar);
			vsc.setRailcarNumber(railspot.getRailcarNumber());
			vehicles = m_vehicle.getVehicleList(vsc);
			
		} else if(railstatus.equals(Constants.STAT_ARRIVED_RAIL) ||
				railstatus.equals(Constants.STAT_RAIL_INSUPPORTYARD) ||
				railstatus.equals(Constants.STAT_RAIL_EMPTY)) {
			//Inbound railcar
			vsc.setSearchType(eVINObjAction.eVINFindInboundRailCar);
			vsc.setRailcarNumber(railspot.getRailcarNumber());
			vehicles = m_vehicle.getVehicleList(vsc);
			
			if(vehicles == null || vehicles.size() < 1) {
				//'No VINs found on railcar, so get ASNs
				vsc.setSearchType(eVINObjAction.eVINFindRailCarASN);
				vehicles = m_vehicle.getVehicleList(vsc);
			}
		} else {
			//'STAT_RAIL_ASSIGNED, STAT_RAIL_ARRIVEDRAIL, STAT_RAIL_INSUPPORTYARD, STAT_RAIL_ERROR
			//Inbound railcar
			vsc.setSearchType(eVINObjAction.eVINFindInboundRailCar);
			vsc.setRailcarNumber(railspot.getRailcarNumber());
			vehicles = m_vehicle.getVehicleList(vsc);
		}
				
		//'Loop through each VIN and update location and generate arrival record if needed
		for(Vehicle vehicle : vehicles) {
			vehiclestatus = vehicle.getStatusCode();
			if(vehiclestatus == null ||
					vehiclestatus.equals(Constants.STAT_RAILSHIP_APPROVED) ||
					vehiclestatus.equals(Constants.STAT_ARRIVED_RAIL) ||
					vehiclestatus.equals(Constants.STAT_LOADED) ||
					vehiclestatus.equals(Constants.STAT_ARRIVED_SUPPORT_YARD))
				{
				
                //'Update inbound train id and event time if needed
				if(vehicle.getLotCode() == null || vehicle.getLotCode().isEmpty()) {
					vehicle.setInboundTrainID(railspot.getInboundTrainID());
					vehicle.setInboundEventTime(railspot.getInboundTrainEvent());
				}
                
				//'Set VINs location to railcars location
				vehicle.setLotCode(railspot.getLotCode());
				vehicle.setZone(railspot.getZoneID());
				vehicle.setRow(railspot.getTrackNbr());
				vehicle.setSpot(railspot.getSpot());
				
                //'Check to see if VIN status needs updated
				if(railspot.getLotCode().equals(Constants.TRACK_LOT)) {
					//'Railcar is in the track lot, so check to see if it was previously in the support yard
					if(vehiclestatus == null ||
					   vehiclestatus.equals(Constants.STAT_ARRIVED_SUPPORT_YARD)) 
					{
						//'VIN was in the support yard and is marked arrived support yard, so change to arrived rail
						vehicle.setStatusCode(Constants.STAT_ARRIVED_RAIL);
						vehicle.setArrivalDate(Common.getCurrentDateTime());
					}
				} else if(railspot.getLotCode().equals(Constants.SUPPORT_TRACK_LOT)) {
					//'Railcar is in the support yard, so check to see if it was previously in the track lot
					if(vehiclestatus.equals(Constants.STAT_ARRIVED_RAIL) || 
							vehiclestatus.length() < 1) {
						//'VIN was in the track lot and is marked arrived rail, so change to arrived support yard
						vehicle.setStatusCode(Constants.STAT_ARRIVED_SUPPORT_YARD);
					}
				}
				vehicle.setChngWho(railspot.getChngWho());
				vehicle.setChngDate(Common.getCurrentDateTime());
				vehicle.setVINMoving(false);
				vehicle.Save(eVINSave.eNoVINCheck);
			}
		}
	}
	
	/**
	 * Determine if the vehicle is destined for this ramp
	 * @param manufacturer
	 * @param route
	 * @return boolean
	 * @throws SQLException 
	 */
	private boolean IsAtDestinationRamp(String manufacturer, String route) throws SQLException {
		boolean result = false;
		List<Route> ramproutes;
		
		//Create collection of ramp routes if needed
		//if(ramproutes == null) {
			RouteSearchCriteria rsc = new RouteSearchCriteria();
			rsc.setSearchType(eRouteObjAction.eRouteFindRampRoute);
			ramproutes = m_route.getRouteList(rsc); 
		//}

		//search collection for matching route 
		//		Any match and we are at destination
		for(Route r : ramproutes) {
			if(r.getManufacturer().equals(manufacturer) && r.getRoute().equals(route)) {
				result = true;
				break;
			}
		}
		
		return result;
	}

	private boolean MeetsHoldCriteria(HoldCriteria holdcriteria, Vehicle vehicle) {
		boolean result = true;
		
		//Check for the vehicle characteristics holds
		if(holdcriteria.getManufacturer() != null && vehicle.getManufacturer() != null && holdcriteria.getManufacturer() != vehicle.getManufacturer()) return false;
		if(holdcriteria.getDealer() != null && vehicle.getDealer() != null && holdcriteria.getDealer() != vehicle.getDealer()) return false;
		if(holdcriteria.getRoute() != null &&  vehicle.getRoute() != null && holdcriteria.getRoute() != vehicle.getRoute()) return false;
		if(holdcriteria.getRailcar() != null && vehicle.getInboundRailcarNumber() != null && holdcriteria.getRailcar() != vehicle.getInboundRailcarNumber()) return false;
		if(holdcriteria.getEmission() != null && vehicle.getEmissionCode() != null && holdcriteria.getEmission() != vehicle.getEmissionCode()) return false;
		if(holdcriteria.getSoldCode() != null && vehicle.getSoldCode() != null && holdcriteria.getSoldCode() != vehicle.getSoldCode()) return false;
		if(holdcriteria.getProdStatus() != null && vehicle.getProdStatus() != null && holdcriteria.getProdStatus() != vehicle.getProdStatus()) return false;
		if(holdcriteria.getVehicleType() != null && vehicle.getVehicleType() != null && holdcriteria.getVehicleType() != vehicle.getVehicleType()) return false;
		if(holdcriteria.getColorCode() != null && vehicle.getColorCode() != null && holdcriteria.getColorCode() != vehicle.getColorCode()) return false;
		if(holdcriteria.getStatus() != null && vehicle.getStatusCode() != null && holdcriteria.getStatus() != vehicle.getStatusCode()) return false;
		
		//Check for Holds based on Arrival Date
		if(holdcriteria.getArriveFromDate() != null && holdcriteria.getArriveToDate() != null ) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyy");
			Date hcArrivalFrom = null, hcArrivalTo = null;
			
			try { hcArrivalFrom = formatter.parse(holdcriteria.getArriveFromDate());
				  hcArrivalTo = formatter.parse(holdcriteria.getArriveToDate());
			} catch (ParseException e) { e.printStackTrace(); }			
			
			if(vehicle.getArrivalDate().after(hcArrivalFrom) && vehicle.getArrivalDate().before(hcArrivalTo)) {
				//Meets the criteria
				return true;
			} else {
				return false;
			}			
		}
		
		//Check the VIN for a match but allow masks using '_'		
		if(holdcriteria.getVIN().length() == 17) {
			char[] hcvin = holdcriteria.getVIN().toCharArray();
			char[] vin = vehicle.getVIN().toCharArray();
			
			for(int index = 0; index < 17; index++) {
				if(hcvin[index] != '_') {
					if(hcvin[index] != vin[index]) {
						//No match!
						return false;
					}
				}
			}
		} else {
			return false;
		}
		
		return result;
	}

	private SortOrderItem RetrieveZoneSortOrder(String Lot, String Zone) {
		return m_sortorderitem.get(Lot, Zone);
	}

	/**
	 * @param vehicle
	 * @param eArrival
	 * @return vehicle
	 * @throws Exception 
	 */
	private Vehicle SetArrivalInformation(Vehicle vehicle, eBayArrival eArrival) throws Exception {
		Railspot railspot;
		
		switch(eArrival) 
		{
			case eBay_TruckIn :
				vehicle.setActionCode(Constants.ACTION_TRUCK_IN);
				vehicle.setArrivalDate(new Date());
				//Delete from Bad ASN editor
				break;
			case eBay_Drop :
				vehicle.setActionCode(Constants.ACTION_DROPZONE);
				vehicle.setArrivalDate(new Date());
				break;
			case eBay_Rail :
				if(vehicle.getActionCode() != Constants.ACTION_ADD_SHIPMENT) {
					//Find the railcar
					railspot = m_railspot.GetRailspot(vehicle.getInboundRailcarNumber());
					
					if(railspot == null) throw(new Exception("RAILCAR_NOT_ON_LOT_ERROR"));
					
					switch(railspot.getStatusCode()) {
						case Constants.STAT_RAIL_EMPTY:
						case Constants.STAT_RAIL_ARRIVEDRAIL:
							 railspot.setStatusCode(Constants.STAT_RAIL_ASSIGNED);
							 railspot.setLoadEmptyStatus(Constants.LE_STATUS_LOADED);
							 railspot.Save();
							 break;
						case Constants.STAT_RAIL_ASSIGNED:
							//Do nothing simply continue
							break;
						default:
							//Throw RAILCAR_NOT_PROPER_STATUS_TO_ADD_VIN
							break;							
					}
				}
				
				vehicle.setActionCode(Constants.ACTION_RAIL_IN);
				vehicle.setArrivalDate(new Date());
				break;
			case eBay_Plant:
				vehicle.setActionCode(Constants.ACTION_PLANT_IN);
				vehicle.setArrivalDate(new Date());
				break;				
			case eBay_DriveIn:
				vehicle.setActionCode(Constants.ACTION_DRIVE_IN);
				vehicle.setArrivalDate(new Date());				
				break;
			case eBay_ReAssign:
				vehicle.setActionCode(Constants.ACTION_VIN_REASSIGNED);
				break;
			default:
				//No need to set any arrival information for other arrival types
				break;				
		}
		
		return vehicle;
	}

	// }}

}
