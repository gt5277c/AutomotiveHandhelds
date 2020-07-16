package com.nscorp.tadsservices.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;

import com.nscorp.tadsservices.model.Enums.*;
import com.nscorp.tadsservices.model.SearchCriteria.HoldSearchCriteria;
import com.nscorp.tadsservices.model.SearchCriteria.VehicleSearchCriteria;
import com.nscorp.tadsservices.model.DAO.VehicleDAO;

@XmlRootElement (name="Vehicle")
@Entity
public class Vehicle extends TADSLocationObject {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String VIN; 
	private String Manufacturer;
	private String VehicleType;
	private String StatusCode;
	private String LotCode;
	private String Zone;
	private String Row;
	private Integer Spot;
	private String ActionCode;
	private String InboundTrainID;
	private String InboundEventTime;
	private String InboundRailcarNumber;
	private String InboundDeckLevel;
	private Integer InboundPos;
	private String OutboundTrainID;
	private String OutboundEventTime;
	private String OutboundRailcarNumber;
	private String OutboundDeckLevel;
	private Integer OutboundPos;
	private Integer InboundDriver;
	private Integer OutboundDriver;
	private String Route;
	private String Dealer;
	private String InboundUpfitter;
	private String OutboundUpfitter;
	private String RoutePrefix;
	private String EmissionCode;
	private String ColorCode;
	private String ProdStatus;
	private String SoldCode;
	private String InboundTruckID;
	private String InboundTruckCo;
	private String OutboundTruckID;
	private String OutboundTruckCo;
	private String ChngWho;
	private Date   ChngDate = new Date();	
	private String HoldReasonCode;
	private String Diversion;
	private Boolean LTD;
	private Boolean ProcessedAsLTD;
	private String LTDRoute;
	private String InboundSCAC;
	private String InboundWayBill;
	private String InboundWayBillSN;
	private String InboundBOL;
	private String DepartureDate;
	private String OutboundSCAC;
	private String OutboundWayBill;
	private String OutboundWayBillSN;
	private String OutboundBOL;
	private Date ArrivalDate;
	private String Origin;
	private String Notes;
	private String LineSeries;
	private String Wheelbase;

	//Class Holds
	private List<Hold> VINHolds = null;
	
	//Has no column in the database
	private String ManufacturerName;
	private Integer NumRecs = 1;
	private boolean VINMoving;
	private String StartLot;
	private String StartZone;
	private String PrevLotCode;
	private String PrevZone;
	private String PrevRow;
	private Integer PrevSpot;
	private String PrevStatusCode;
	private String PrevRoute;
	private String PrevDealer;
	private Integer PrevHoldCount;
	private eStatusCode Status;
	private eBayArrival BayArrival;
	
	private VehicleDAO vDAO;
	// }}

	//Constructor
	public Vehicle() {}
	
	public Vehicle(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	@Override
	public void setLocationDatabase(Database database) { 
		this.database = database;
		vDAO = new VehicleDAO(database);
	}
	
	@XmlElement (name="VIN")
	public String getVIN() { return VIN; }
	public void setVIN(String VIN) { this.VIN = VIN; }
	
	@XmlElement (name="Manufacturer")
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String Manufacturer)  { this.Manufacturer = Manufacturer; }
	
	@XmlElement (name="VehicleType")
	public String getVehicleType() { return VehicleType; }
	public void setVehicleType(String VehicleType) { this.VehicleType = VehicleType; }
		
	@XmlElement (name="StatusCode")
	public String getStatusCode() { return StatusCode; }
	public void setStatusCode(String StatusCode) { this.StatusCode = StatusCode; }

	@XmlElement (name="LotCode")
	public String getLotCode() { return LotCode; }
	public void setLotCode(String LotCode)  { 
		if(this.LotCode != LotCode) VINMoving = true;
		this.LotCode = LotCode; 
	}

	@XmlElement (name="Zone")
	public String getZone() { return Zone; }
	public void setZone(String Zone) {
		if(this.Zone != Zone) VINMoving = true;
		this.Zone = Zone; 
	}

	@XmlElement (name="Row")
	public String getRow() { return Row; }
	public void setRow(String Row)  {
		if(this.Row != Row) VINMoving = true;
		this.Row = Row; 
	}

	@XmlElement (name="Spot")
	public Integer getSpot() { return Spot; }
	public void setSpot(Integer Spot) {
		if(this.Spot != Spot) VINMoving = true;
		this.Spot = Spot; 
	}

	@XmlElement (name="ActionCode")
	public String getActionCode() { return ActionCode; }
	public void setActionCode(String ActionCode) { this.ActionCode = ActionCode; }

	@XmlElement (name="InboundTrainID")
	public String getInboundTrainID() { return InboundTrainID; }
	public void setInboundTrainID(String InboundTrainID) { this.InboundTrainID = InboundTrainID; }
		
	@XmlElement (name="InboundEventTime")
	public String getInboundEventTime() { return InboundEventTime; }
	public void setInboundEventTime(String InboundEventTime) { this.InboundEventTime = InboundEventTime; }
	
	@XmlElement (name="InboundRailcarNumber")
	public String getInboundRailcarNumber() { return InboundRailcarNumber; }
	public void setInboundRailcarNumber(String InboundRailcarNumber) { this.InboundRailcarNumber = InboundRailcarNumber; }
	
	@XmlElement (name="InboundDeckLevel")
	public String getInboundDeckLevel() { return InboundDeckLevel; }
	public void setInboundDeckLevel(String InboundDeckLevel) {
		if(this.InboundDeckLevel != InboundDeckLevel) VINMoving = true;
		this.InboundDeckLevel = InboundDeckLevel; 
	}

	@XmlElement (name="InboundPos")
	public Integer getInboundPos() { return InboundPos; }
	public void setInboundPos(Integer InboundPos) {
		if(this.InboundPos != InboundPos) VINMoving = true;
		this.InboundPos = InboundPos; 
	}
	
	@XmlElement (name="OutboundTrainID")
	public String getOutboundTrainID() { return OutboundTrainID; }
	public void setOutboundTrainID(String OutboundTrainID) { this.OutboundTrainID = OutboundTrainID; }
	
	@XmlElement (name="OutboundEventTime")
	public String getOutboundEventTime() { return OutboundEventTime; }
	public void setOutboundEventTime(String OutboundEventTime) { this.OutboundEventTime = OutboundEventTime; }
	
	@XmlElement (name="OutboundRailcarNumber")
	public String getOutboundRailcarNumber() { return OutboundRailcarNumber; }
	public void setOutboundRailcarNumber(String OutboundRailcarNumber) { this.OutboundRailcarNumber = OutboundRailcarNumber; }
	
	@XmlElement (name="OutboundDeckLevel")
	public String getOutboundDeckLevel() { return OutboundDeckLevel; }
	public void setOutboundDeckLevel(String OutboundDeckLevel) {
		if(this.OutboundDeckLevel != OutboundDeckLevel) VINMoving = true;
		this.OutboundDeckLevel = OutboundDeckLevel; 
	}

	@XmlElement (name="OutboundPos")
	public Integer getOutboundPos() { return OutboundPos; }
	public void setOutboundPos(Integer OutboundPos) {
		if(this.OutboundPos != OutboundPos) VINMoving = true;
		this.OutboundPos = OutboundPos; 
	}
	
	@XmlElement (name="InboundDriver")
	public Integer getInboundDriver() { return InboundDriver; }
	public void setInboundDriver(Integer InboundDriver) { this.InboundDriver = InboundDriver; }
	
	@XmlElement (name="OutboundDriver")
	public Integer getOutboundDriver() { return OutboundDriver; }
	public void setOutboundDriver(Integer OutboundDriver) { this.OutboundDriver = OutboundDriver; }
	
	@XmlElement (name="InboundUpfitter")
	public String getInboundUpfitter() { return InboundUpfitter; }
	public void setInboundUpfitter(String InboundUpfitter) { this.InboundUpfitter = InboundUpfitter; }
	
	@XmlElement (name="OutboundUpfitter")
	public String getOutboundUpfitter() { return OutboundUpfitter; }
	public void setOutboundUpfitter(String OutboundUpfitter) { this.OutboundUpfitter = OutboundUpfitter; }

	@XmlElement (name="RoutePrefix")
	public String getRoutePrefix() { return RoutePrefix; }
	public void setRoutePrefix(String RoutePrefix) { this.RoutePrefix = RoutePrefix; }

	@XmlElement (name="EmissionCode")
	public String getEmissionCode() { return EmissionCode; }
	public void setEmissionCode(String EmissionCode) { this.EmissionCode = EmissionCode; }

	@XmlElement (name="ColorCode")
	public String getColorCode() { return ColorCode; }
	public void setColorCode(String ColorCode) { this.ColorCode = ColorCode; }

	@XmlElement (name="ProdStatus")
	public String getProdStatus() { return ProdStatus; }
	public void setProdStatus(String ProdStatus) { this.ProdStatus = ProdStatus; }

	@XmlElement (name="SoldCode")
	public String getSoldCode() { return SoldCode; }
	public void setSoldCode(String SoldCode) { this.SoldCode = SoldCode; }

	@XmlElement (name="InboundTruckID")
	public String getInboundTruckID() { return InboundTruckID; }
	public void setInboundTruckID(String InboundTruckID) { this.InboundTruckID = InboundTruckID; }

	@XmlElement (name="InboundTruckCo")
	public String getInboundTruckCo() { return InboundTruckCo; }
	public void setInboundTruckCo(String InboundTruckCo) { this.InboundTruckCo = InboundTruckCo; }

	@XmlElement (name="OutboundTruckID")
	public String getOutboundTruckID() { return OutboundTruckID; }
	public void setOutboundTruckID(String OutboundTruckID) { this.OutboundTruckID = OutboundTruckID; }

	@XmlElement (name="OutboundTruckCo")
	public String getOutboundTruckCo() { return OutboundTruckCo; }
	public void setOutboundTruckCo(String OutboundTruckCo) { this.OutboundTruckCo = OutboundTruckCo; }

	@XmlElement (name="NumRecs")
	public Integer getNumRecs() { return NumRecs; }
	public void setNumRecs(Integer NumRecs) { this.NumRecs = NumRecs; }
	
	@XmlElement (name="HoldReasonCode")
	public String getHoldReasonCode() { return HoldReasonCode; }
	public void setHoldReasonCode(String HoldReasonCode) {
		
		if(VINHolds == null && this.HoldReasonCode != null) {
			//Initialize the list of holds for this vehicle
			VINHolds = new ArrayList<Hold>();
			
			//Get a list of holds for this vehicle from the database
			Hold hold = new Hold(database);			
			HoldSearchCriteria hsc = new HoldSearchCriteria();
			hsc.setSearchType(eHoldObjAction.eHoldFindVIN);
			hsc.setVIN(this.getVIN());
			List<Hold> holds = hold.GetHolds(hsc);
			
			//If no holds are found then no need to update VINHolds
			if(holds != null) VINHolds = holds;
		}		
				
		//Check to see if a Hold is being added or removed
		if(HoldReasonCode == null) {
			//Hold is being removed
			
			//Check to see if there are any Holds to remove first
			if(VINHolds != null && this.HoldReasonCode != null) {
				for(Hold hold : VINHolds) {
					if(hold.getHoldCode() == this.HoldReasonCode) {
						VINHolds.remove(hold);
						break;
					}
				}
				
				//If there are more Holds set the Hold Reason to first hold
				if(VINHolds.size() > 0) {
					this.HoldReasonCode = VINHolds.get(0).getHoldCode();
				} else {
					this.HoldReasonCode = HoldReasonCode;
					this.StatusCode = Constants.STAT_PARKED;
				}
			}
		} else {
			//Hold is being added
			Hold hold = database.getClass(Hold.class);
			hold.setManufacturer(this.getManufacturer());
			hold.setHoldCode(HoldReasonCode);
			hold.setChangeDate(new Date());
			
			//Add to the Hold list for the vehicle
			if(VINHolds == null) VINHolds = new ArrayList<Hold>();
			VINHolds.add(hold);
			
			//Set the HoldReasonCode
			this.HoldReasonCode = HoldReasonCode;
		}
	}
	
	@XmlElement (name="Diversion")
	public String getDiversion() { return Diversion; }
	public void setDiversion(String Diversion) { this.Diversion = Diversion; }
	
	@XmlElement (name="LTD")
	public Boolean getLTD() { return LTD; }
	public void setLTD(Boolean LTD) { this.LTD = LTD; }
	
	@XmlElement (name="ProcessedAsLTD")
	public Boolean getProcessedAsLTD() { return ProcessedAsLTD; }
	public void setProcessedAsLTD(Boolean ProcessedAsLTD) { this.ProcessedAsLTD = ProcessedAsLTD; }
	
	@XmlElement (name="LTDRoute")
	public String getLTDRoute() { return LTDRoute; }
	public void setLTDRoute(String LTDRoute) { this.LTDRoute = LTDRoute; }
	
	@XmlElement (name="InboundSCAC")
	public String getInboundSCAC() { return InboundSCAC; }
	public void setInboundSCAC(String InboundSCAC) { this.InboundSCAC = InboundSCAC; }
	
	@XmlElement (name="InboundWayBill")
	public String getInboundWayBill() { return InboundWayBill; }
	public void setInboundWayBill(String InboundWayBill) { this.InboundWayBill = InboundWayBill; }
	
	@XmlElement (name="InboundWayBillSN")
	public String getInboundWayBillSN() { return InboundWayBillSN; }
	public void setInboundWayBillSN(String InboundWayBillSN) { this.InboundWayBillSN = InboundWayBillSN; }
	
	@XmlElement (name="InboundBOL")
	public String getInboundBOL() { return InboundBOL; }
	public void setInboundBOL(String InboundBOL) { this.InboundBOL = InboundBOL; }
	
	@XmlElement (name="DepartureDate")
	public String getDepartureDate() { return DepartureDate; }
	public void setDepartureDate(String DepartureDate) { this.DepartureDate = DepartureDate; }
	
	@XmlElement (name="OutboundSCAC")
	public String getOutboundSCAC() { return OutboundSCAC; }
	public void setOutboundSCAC(String OutboundSCAC) { this.OutboundSCAC = OutboundSCAC; }
	
	@XmlElement (name="OutboundWayBill")
	public String getOutboundWayBill() { return OutboundWayBill; }
	public void setOutboundWayBill(String OutboundWayBill) { this.OutboundWayBill = OutboundWayBill; }
	
	@XmlElement (name="OutboundWayBillSN")
	public String getOutboundWayBillSN() { return OutboundWayBillSN; }
	public void setOutboundWayBillSN(String OutboundWayBillSN) { this.OutboundWayBillSN = OutboundWayBillSN; }
	
	@XmlElement (name="OutboundBOL")
	public String getOutboundBOL() { return OutboundBOL; }
	public void setOutboundBOL(String OutboundBOL) { this.OutboundBOL = OutboundBOL; }
	
	@XmlElement (name="Origin")
	public String getOrigin() { return Origin; }
	public void setOrigin(String Origin) { this.Origin = Origin; }
	
	@XmlElement (name="Notes")
	public String getNotes() { return Notes; }
	public void setNotes(String Notes) { this.Notes = Notes; }
	
	@XmlElement (name="LineSeries")
	public String getLineSeries() { return LineSeries; }
	public void setLineSeries(String LineSeries) { this.LineSeries = LineSeries; }
	
	@XmlElement (name="Wheelbase")
	public String getWheelbase() { return Wheelbase; }
	public void setWheelbase(String Wheelbase) { this.Wheelbase = Wheelbase; }
	
	@XmlElement (name="VINHolds")
	public List<Hold> getVINHolds() { return VINHolds; }
	public void setVINHolds(List<Hold> VINHolds) { this.VINHolds = VINHolds; }

	@XmlElement (name="VINMoving")
	public Boolean getVINMoving() { return VINMoving; }
	public void setVINMoving(Boolean VINMoving) { this.VINMoving = VINMoving; }
	
	@XmlElement (name="ManufacturerName")
	public String getManufacturerName() { return ManufacturerName; }
	public void setManufacturerName(String ManufacturerName) { this.ManufacturerName = ManufacturerName; }
	
	@XmlElement (name="StartZone")
	public String getStartZone() { return StartZone; }
	public void setStartZone(String StartZone) { this.StartZone = StartZone; }
	
	@XmlElement (name="StartLot")
	public String getStartLot() { return StartLot; }
	public void setStartLot(String StartLot) { this.StartLot = StartLot; }
	
	@XmlElement (name="PrevLotCode")
	public String getPrevLotCode() { return PrevLotCode; }
	public void setPrevLotCode(String PrevLotCode) { this.PrevLotCode = PrevLotCode; }
	
	@XmlElement (name="PrevZone")
	public String getPrevZone() { return PrevZone; }
	public void setPrevZone(String PrevZone) { this.PrevZone = PrevZone; }
	
	@XmlElement (name="PrevRow")
	public String getPrevRow() { return PrevRow; }
	public void setPrevRow(String PrevRow) { this.PrevRow = PrevRow; }
	
	@XmlElement (name="PrevSpot")
	public Integer getPrevSpot() { return PrevSpot; }
	public void setPrevSpot(Integer PrevSpot) { this.PrevSpot = PrevSpot; }
	
	@XmlElement (name="PrevStatusCode")
	public String getPrevStatusCode() { return PrevStatusCode; }
	public void setPrevStatusCode(String PrevStatusCode) { this.PrevStatusCode = PrevStatusCode; }
	
	@XmlElement (name="PrevRoute")
	public String getPrevRoute() { return PrevRoute; }
	public void setPrevRoute(String PrevRoute) { this.PrevRoute = PrevRoute; }
	
	@XmlElement (name="PrevDealer")
	public String getPrevDealer() { return PrevDealer; }
	public void setPrevDealer(String PrevDealer) { this.PrevDealer = PrevDealer; }

	@XmlElement (name="PrevHoldCount")
	public Integer getPrevHoldCount() { return PrevHoldCount; }
	public void setPrevHoldCount(Integer PrevHoldCount) { this.PrevHoldCount = PrevHoldCount; }
	
	@XmlElement (name="Status")
	public eStatusCode getMainframeStatus() { 
		if(Status == null) {
			return eStatusCode.eStatusNone;
		} else {
			return Status;
		}
	}
	public void setMainframeStatus(eStatusCode Status) { this.Status = Status; }
	
	@XmlElement (name="BayArrival")
	public eBayArrival getBayArrival() { return this.BayArrival; }
	public void setBayArrival(eBayArrival BayArrival) { this.BayArrival = BayArrival; }
	
	@XmlElement (name="Route")
	public String getRoute() { return Route; }
	public void setRoute(String Route)  { this.Route = Route; }

	@XmlElement (name="Dealer")
	public String getDealer() { return Dealer; }
	public void setDealer(String Dealer)  { this.Dealer = Dealer; }

	@XmlElement (name="ChngWho")
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String ChngWho)  { this.ChngWho = ChngWho; }

	@XmlElement (name="ChngDate")
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date ChngDate)  { this.ChngDate = ChngDate; }

	@XmlElement (name="ArrivalDate")
	public Date getArrivalDate() { return ArrivalDate; }
	public void setArrivalDate(Date ArrivalDate)  { this.ArrivalDate = ArrivalDate; }
	// }}
	
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////	
	public Vehicle getVehicle(String vin) {
		return vDAO.get(vin);
	}
	
	public Vehicle getVehicle(VehicleSearchCriteria vsc) {
		return vDAO.get(vsc);
	}
	
	public List<Vehicle> getVehicleList(VehicleSearchCriteria vsc) {
		return vDAO.getList(vsc);
	}
	
	public List<Hold> getVehicleHolds(String vin){

		//Get a list of holds for this vehicle from the database
		Hold hold = new Hold(database);			
		HoldSearchCriteria hsc = new HoldSearchCriteria();
		hsc.setSearchType(eHoldObjAction.eHoldFindVIN);
		hsc.setVIN(this.getVIN());
		List<Hold> holds = hold.GetHolds(hsc);
		VINHolds = holds;
		
		return VINHolds;
	}
	
	public boolean getVINOrigin(String vin) {
		return vDAO.GetVINOrigin(vin);
	}
	
	public boolean getReturnToPlantPerformed(String vin) {
		return vDAO.GetReturnToPlantPerformed(vin);
	}
	
	public String getManufacturerFromVIN(String vin) {
		return vDAO.getManufacturerFromVIN(vin);
	}
	
	public boolean removeVINNoRecord() {
		return vDAO.RemoveNoRecord(this);
	}
	
	public void RemoveFromLot() {
		this.setInboundTrainID("");
		this.setInboundEventTime("");
		this.setInboundRailcarNumber("");
		this.setInboundDeckLevel("");
		this.setInboundPos(0);
		this.setStatusCode(Constants.STAT_DELETED);
		
		vDAO.RemoveFromLot(this);
	}
	
	/**
	 * Update this vehicle with the location information passed.
	 * @param location
	 * @return
	 */
	public void updateVehicleLocation(Location location) {
		if(location != null) {
			if(location.getLot() != null) this.setLotCode(location.getLot());
			if(location.getZone() != null) this.setZone(location.getZone());
			if(location.getRow() != null) this.setRow(location.getRow());
			if(location.getSpot() > 0) this.setSpot(location.getSpot());
		}
	}
	
	/**
	 * Update this vehicle with the information from the vehicle passed.
	 * @param vehicle
	 * @return
	 */
	public void updateVehicle(Vehicle vehicle) {

		if(vehicle != null) {
			//Identification
			if(vehicle.getVIN() != null) this.setVIN(vehicle.getVIN());
			if(vehicle.getManufacturer() != null) this.setManufacturer(vehicle.getManufacturer());
			
			//Inbound
			if(vehicle.getInboundBOL() != null) this.setInboundBOL(vehicle.getInboundBOL());
			if(vehicle.getInboundDeckLevel() != null) this.setInboundDeckLevel(vehicle.getInboundDeckLevel());
			if(vehicle.getInboundDriver() != null) this.setInboundDriver(vehicle.getInboundDriver());
			if(vehicle.getInboundEventTime() != null) this.setInboundEventTime(vehicle.getInboundEventTime());
			if(vehicle.getInboundPos() != null) this.setInboundEventTime(vehicle.getInboundEventTime());
			if(vehicle.getInboundRailcarNumber() != null) this.setInboundRailcarNumber(vehicle.getInboundRailcarNumber());
			if(vehicle.getInboundSCAC() != null) this.setInboundSCAC(vehicle.getInboundSCAC());
			if(vehicle.getInboundTrainID() != null) this.setInboundTrainID(vehicle.getInboundTrainID());
			if(vehicle.getInboundTruckCo() != null) this.setInboundTruckCo(vehicle.getInboundTruckCo());
			if(vehicle.getInboundTruckID() != null) this.setInboundTruckID(vehicle.getInboundTruckID());
			if(vehicle.getInboundUpfitter() != null) this.setInboundUpfitter(vehicle.getInboundUpfitter());
			if(vehicle.getInboundWayBill() != null) this.setInboundWayBill(vehicle.getInboundWayBill());
			if(vehicle.getInboundWayBillSN() != null) this.setInboundWayBillSN(vehicle.getInboundWayBillSN());
			
			//Outbound
			if(vehicle.getOutboundBOL() != null) this.setOutboundBOL(vehicle.getOutboundBOL());
			if(vehicle.getOutboundDeckLevel() != null) this.setOutboundDeckLevel(vehicle.getOutboundDeckLevel());
			if(vehicle.getOutboundDriver() != null) this.setOutboundDriver(vehicle.getOutboundDriver());
			if(vehicle.getOutboundEventTime() != null) this.setOutboundEventTime(vehicle.getOutboundEventTime());
			if(vehicle.getOutboundPos() != null) this.setOutboundPos(vehicle.getOutboundPos());
			if(vehicle.getOutboundRailcarNumber() != null) this.setOutboundRailcarNumber(vehicle.getOutboundRailcarNumber());
			if(vehicle.getOutboundSCAC() != null) this.setOutboundSCAC(vehicle.getOutboundSCAC());
			if(vehicle.getOutboundTrainID() != null) this.setOutboundTrainID(vehicle.getOutboundTrainID());
			if(vehicle.getOutboundTruckCo() != null) this.setOutboundTruckCo(vehicle.getOutboundTruckCo());
			if(vehicle.getOutboundTruckID() != null) this.setOutboundTruckID(vehicle.getOutboundTruckID());
			if(vehicle.getOutboundUpfitter() != null) this.setOutboundUpfitter(vehicle.getOutboundUpfitter());
			if(vehicle.getOutboundWayBill() != null) this.setOutboundWayBill(vehicle.getOutboundWayBill());
			if(vehicle.getOutboundWayBillSN() != null) this.setOutboundWayBillSN(vehicle.getOutboundWayBillSN());
			
			//Destination information
			if(vehicle.getDealer() != null) this.setDealer(vehicle.getDealer());
			if(vehicle.getRoute() != null) this.setRoute(vehicle.getRoute());
			
			//Status information
			if(vehicle.getArrivalDate() != null) this.setArrivalDate(vehicle.getArrivalDate());
			if(vehicle.getVehicleType() != null) this.setVehicleType(vehicle.getVehicleType());
			if(vehicle.getActionCode() != null) this.setActionCode(vehicle.getActionCode());
			if(vehicle.getStatusCode() != null) this.setStatusCode(vehicle.getStatusCode());
			
			//Location
			if(vehicle.getLotCode() != null) this.setLotCode(vehicle.getLotCode());
			if(vehicle.getZone() != null) this.setZone(vehicle.getZone());
			if(vehicle.getRow() != null) this.setRow(vehicle.getRow());
			if(vehicle.getSpot() != null && vehicle.getSpot() > 0) this.setSpot(vehicle.getSpot());
				
			//Holds
			if(vehicle.getHoldReasonCode() != null) {
				this.setHoldReasonCode(vehicle.getHoldReasonCode());
				if(vehicle.getVINHolds().size() > 0) this.setVINHolds(vehicle.getVINHolds());
			}
			
			//User information
			if(vehicle.ChngWho != null) this.setChngWho(vehicle.ChngWho);
			if(vehicle.ChngDate != null) this.setChngDate(vehicle.getChngDate());
		}		
	}
	
	@Override
	public String toString() {
		return "Vehicle: " + VIN + "\n" +
				"Route: " + Route + "\n" +
				"Dealer: " + Dealer + "\n" +
				"Arrived: " + ArrivalDate + "\n" +
				"Date: " + ChngDate + "\n";
	}
	
	/**
	 * Checks the location to determine if the spot is available
	 * 
	 * @param eSaveType
	 * @return boolean
	 */
	private Boolean CanMove(eVINSave eSaveType) {
		String sLotCode;
		boolean result = false;
		
		sLotCode = LotCode;
		Common.PadString(Zone, Constants.ZONE_LEN);
		Common.PadString(Row, Constants.AREA_LEN);
		//Determine which lot
		switch(sLotCode) {
			case Constants.TRUCK_LOT:
			break;
			case Constants.LOADLINE_LOT:
			break;
			case Constants.STORE_LOT:
			break;
			default:
				break;
		}
		
		//If the lot is Truck/Loadline/Storage see if spot is empty
		if(LotCode == Constants.TRUCK_LOT || 
				LotCode == Constants.LOADLINE_LOT || 
				LotCode == Constants.STORE_LOT) {
			
			//Assume the location exists for now
			//Check the location to make sure it exists
			//lot = new Lot(database);
			//if(lot.IsLocation(eLot, sZone, sArea, iSpot)) {

				//Check to see if a vehicle is in the location
				VehicleSearchCriteria vsc = new VehicleSearchCriteria(eVINObjAction.eVINFindSpot,this);
				Vehicle v = vDAO.get(vsc);
				
				if( v == null) {
					//Nothing there
					result = true;
				} else {
					//This vehicle may already be bayed in this location
					if(this.getVIN().equals(v.getVIN())) { 
						result = true;
					} else {		
						//May have been bayed to the location during TruckIn or Plantin
						if(v.getStatusCode() != null) {
							if(v.getStatusCode().equals(Constants.STAT_TRUCK_IN) ||
									v.getStatusCode().equals(Constants.STAT_PLANT_IN)){
								result = true;
							}
						} else {
							result = false;
						}
					}
				}
			//}			
		} else {
			//Non structured lot
			result = true;
		}		
				
		return result;
	}
	
	/**
	 * Based on the vehicles location will determine if the trucker is notified.
	 * @return Boolean
	 */
	public Boolean TransmitToTrucker() {
		return vDAO.TransmitToTrucker(this.getLotCode(),this.getZone(),this.getRow());
	}
	
	public void Save() throws Exception {
		Save(eVINSave.eSystem);
	}
	
	public void Save(eVINSave eSaveType) throws Exception {
		
		//Check the vehicle format
		if( eSaveType != eVINSave.eNoVINCheck) {
			if( !vDAO.VINCheck(getVIN())) {
				throw(new Exception("Vehicle is not in the correct format " + getVIN()));
			}
		}
		
		//Check to see if the vehicle is moving
		if(VINMoving) {
			if(CanMove(eSaveType)) {
				vDAO.set(this);
			} else {
				throw(new Exception("Spot is already filled."));
			}	
		} else {
			vDAO.set(this);
		}
		
		vDAO.deleteHolds(this);

		//Now add the holds from the VIN object to the database
		if(VINHolds != null) vDAO.addHolds(this,VINHolds);
		
		//Check for truck out or drive out and remove records from tblVIN
		if(StatusCode == Constants.STAT_TRUCK_OUT || StatusCode == Constants.STAT_DRIVE_OUT) {
			Delete();
		}
		
		VINMoving = false;
	}
	
	public void SaveHistory() {
		vDAO.SaveHistory(this);
	}
	
	public void Delete() {
		
		//Check for any vehicle Holds
		if(VINHolds != null) {
			//Save all holds from tblVINHolds to tblHoldCriterias for this vehicle
			for(Hold h : VINHolds) {
				HoldCriteria hc = new HoldCriteria(database);
				hc.setManufacturer(h.getManufacturer());
				hc.setHoldCode(h.getHoldCode());
				hc.setVIN(this.getVIN());
				hc.Save();
			}
			
			vDAO.deleteHolds(this);
		}
				
		//Delete the Vehicle record from tblVIN
		vDAO.delete(this);
		
	}
		
	public boolean VINCheck(String vin) {
		return vDAO.VINCheck(vin);
	}
}
