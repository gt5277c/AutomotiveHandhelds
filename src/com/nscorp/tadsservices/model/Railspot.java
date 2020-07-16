package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.RailspotDAO;
import com.nscorp.tadsservices.model.SearchCriteria.RailspotSearchCriteria;

@XmlRootElement (name="Railspot")
@Entity
public class Railspot extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private double key;
	private String RailcarNumber;
	private String LotCode;
	private String ZoneID;
	private String TrackNbr;
	private int    Spot;
	private String PrevLotCode;
	private String PrevZoneID;
	private String PrevTrackNbr;
	private int    PrevSpot;
	private String Group;
	private String Mfg;
	private String Route;
	private String StatusCode;
	private String ActionCode;
	private String BadOrderCode;
	private int TotalVINsLoaded;
	private String HeadLight;
	private double DeckHeight;
	private String DeckType;                           
	private String TieDown;               
	private String Articulated; 
	private boolean LTD;
	private boolean ProcessedAsLTD;
	private String LoadEmptyStatus;
	private String SwitchInstructions;
	private String BlockToInstrucions;
	private String InboundSCAC;
	private String InboundFSAC;
	private double InboundLoadedWeight;
	private String InboundTrainID;
	private String InboundTrainEvent;
	private String InboundWayBill;
	private String InboundWayBillDate;
	private String InboundWayBillSN;
	private String InboundBOL;
	private Date InboundBOLDate;
	private String InboundCity;
	private String InboundState;
	private String InboundDestination;
	private String InboundSTCC;
	private String InboundEmbargo;
	private String InboundPermit;
	private String DepartureDate;
	private String OutboundSCAC;
	private String OutboundFSAC;
	private double OutboundLoadedWeight;
	private String OutboundTrainID;
	private String OutboundTrainEvent;
	private String OutboundWayBill;
	private Date   OutboundWayBillDate;
	private String OutboundWayBillSN;
	private String OutboundBOL;
	private Date   OutboundBOLDate;
	private String OutboundEmbargo;
	private String OutboundPermit;
	private String Notes;
	private String Consignee;
	private String Shipper;
	private Date   ArrivalDate;
	private Date   RailShipApproveDate;
	private String ChngWho;
	private Date ChngDate;
	
	private RailspotDAO rsDAO;
	// }}

	//Constructor
	public Railspot() {}
	
	public Railspot(Database database) {
		setLocationDatabase(database);
	};

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		rsDAO = new RailspotDAO(database);
	}
	
	@XmlElement (name="RailcarNumber")
	public String getRailcarNumber() { return RailcarNumber; }
	public void setRailcarNumber(String RailcarNumber) { this.RailcarNumber = RailcarNumber; }

	@XmlElement (name="LotCode")
	public String getLotCode() { return LotCode; }
	public void setLotCode(String LotCode) { this.LotCode = LotCode; }
	
	@XmlElement (name="ZoneID")
	public String getZoneID() { return ZoneID; }
	public void setZoneID(String ZoneID) { this.ZoneID = ZoneID; }

	@XmlElement (name="TrackNbr")
	public String getTrackNbr() { return TrackNbr; }
	public void setTrackNbr(String TrackNbr) { this.TrackNbr = TrackNbr; }

	@XmlElement (name="Spot")
	public int getSpot() { return Spot; }
	public void setSpot(int Spot) { this.Spot = Spot; }
	
	@XmlElement (name="PrevLotCode")
	public String getPrevLotCode() { return PrevLotCode; }
	public void setPrevLotCode(String PrevLotCode) { this.PrevLotCode = PrevLotCode; }

	@XmlElement (name="PrevZoneID")
	public String getPrevZoneID() { return PrevZoneID; }
	public void setPrevZoneID(String PrevZoneID) { this.PrevZoneID = PrevZoneID; }
	
	@XmlElement (name="PrevTrackNbr")
	public String getPrevTrackNbr() { return PrevTrackNbr; }
	public void setPrevTrackNbr(String PrevTrackNbr) { this.PrevTrackNbr = PrevTrackNbr; }
	
	@XmlElement (name="PrevSpot")
	public int getPrevSpot() { return PrevSpot; }
	public void setPrevSpot(int PrevSpot) { this.PrevSpot = PrevSpot; }

	@XmlElement (name="Group")
	public String getGroup() { return Group; }
	public void setGroup(String Group) { this.Group = Group; }

	@XmlElement (name="Mfg")
	public String getMfg() { return Mfg; }
	public void setMfg(String Mfg) { this.Mfg = Mfg; }

	@XmlElement (name="Route")
	public String getRoute() { return Route; }
	public void setRoute(String Route) { this.Route = Route; }
	
	@XmlElement (name="StatusCode")
	public String getStatusCode() { return StatusCode; }
	public void setStatusCode(String StatusCode) { this.StatusCode = StatusCode; }

	@XmlElement (name="ActionCode")
	public String getActionCode() { return ActionCode; }
	public void setActionCode(String ActionCode) { this.ActionCode = ActionCode; }
	
	@XmlElement (name="BadOrderCode")
	public String getBadOrderCode() { return BadOrderCode; }
	public void setBadOrderCode(String BadOrderCode) { this.BadOrderCode = BadOrderCode; }

	@XmlElement (name="HeadLight")
	public String getHeadLight() { return HeadLight; }
	public void setHeadLight(String HeadLight) { this.HeadLight = HeadLight; }

	@XmlElement (name="DeckHeight")
	public double getDeckHeight() { return DeckHeight; }
	public void setDeckHeight(double DeckHeight) { this.DeckHeight = DeckHeight; }
	
	@XmlElement (name="DeckType")
	public String getDeckType() { return DeckType; }
	public void setDeckType(String DeckType) { this.DeckType = DeckType; }
	
	@XmlElement (name="TieDown")
	public String getTieDown() { return TieDown; }
	public void setTieDown(String TieDown) { this.TieDown = TieDown; }
	
	@XmlElement (name="Articulated")
	public String getArticulated() { return Articulated; }
	public void setArticulated(String Articulated) { this.Articulated = Articulated; }

	@XmlElement (name="LTD")
	public boolean getLTD() { return LTD; }
	public void setLTD(boolean LTD) { this.LTD = LTD; }
	
	@XmlElement (name="ProcessedAsLTD")
	public boolean getProcessedAsLTD() { return ProcessedAsLTD; }
	public void setProcessedAsLTD(boolean ProcessedAsLTD) { this.ProcessedAsLTD = ProcessedAsLTD; }
	
	@XmlElement (name="LoadEmptyStatus")
	public String getLoadEmptyStatus() { return LoadEmptyStatus; }
	public void setLoadEmptyStatus(String LoadEmptyStatus) { this.LoadEmptyStatus = LoadEmptyStatus; }
	
	@XmlElement (name="SwitchInstructions")
	public String getSwitchInstructions() { return SwitchInstructions; }
	public void setSwitchInstructions(String SwitchInstructions) { this.SwitchInstructions = SwitchInstructions; }

	@XmlElement (name="BlockToInstrucions")
	public String getBlockToInstrucions() { return BlockToInstrucions; }
	public void setBlockToInstrucions(String BlockToInstrucions) { this.BlockToInstrucions = BlockToInstrucions; }

	@XmlElement (name="InboundSCAC")
	public String getInboundSCAC() { return InboundSCAC; }
	public void setInboundSCAC(String InboundSCAC) { this.InboundSCAC = InboundSCAC; }

	@XmlElement (name="InboundFSAC")
	public String getInboundFSAC() { return InboundFSAC; }
	public void setInboundFSAC(String InboundFSAC) { this.InboundFSAC = InboundFSAC; }
	
	@XmlElement (name="InboundLoadedWeight")
	public double getInboundLoadedWeight() { return InboundLoadedWeight; }
	public void setInboundLoadedWeight(double InboundLoadedWeight) { this.InboundLoadedWeight = InboundLoadedWeight; }
	
	@XmlElement (name="InboundTrainID")
	public String getInboundTrainID() { return InboundTrainID; }
	public void setInboundTrainID(String InboundTrainID) { this.InboundTrainID = InboundTrainID; }
	
	@XmlElement (name="InboundTrainEvent")
	public String getInboundTrainEvent() { return InboundTrainEvent; }
	public void setInboundTrainEvent(String InboundTrainEvent) { this.InboundTrainEvent = InboundTrainEvent; }
	
	@XmlElement (name="InboundWayBill")
	public String getInboundWayBill() { return InboundWayBill; }
	public void setInboundWayBill(String InboundWayBill) { this.InboundWayBill = InboundWayBill; }
	
	@XmlElement (name="InboundWayBillDate")
	public String getInboundWayBillDate() { return InboundWayBillDate; }
	public void setInboundWayBillDate(String InboundWayBillDate) { this.InboundWayBillDate = InboundWayBillDate; }
	
	@XmlElement (name="InboundWayBillSN")
	public String getInboundWayBillSN() { return InboundWayBillSN; }
	public void setInboundWayBillSN(String InboundWayBillSN) { this.InboundWayBillSN = InboundWayBillSN; }
	
	@XmlElement (name="InboundBOL")
	public String getInboundBOL() { return InboundBOL; }
	public void setInboundBOL(String InboundBOL) { this.InboundBOL = InboundBOL; }
	
	@XmlElement (name="InboundBOLDate")
	public Date getInboundBOLDate() { return InboundBOLDate; }
	public void setInboundBOLDate(Date InboundBOLDate) { this.InboundBOLDate = InboundBOLDate; }
	
	@XmlElement (name="InboundCity")
	public String getInboundCity() { return InboundCity; }
	public void setInboundCity(String InboundCity) { this.InboundCity = InboundCity; }
	
	@XmlElement (name="InboundState")
	public String getInboundState() { return InboundState; }
	public void setInboundState(String InboundState) { this.InboundState = InboundState; }
	
	@XmlElement (name="InboundDestination")
	public String getInboundDestination() { return InboundDestination; }
	public void setInboundDestination(String InboundDestination) { this.InboundDestination = InboundDestination; }
	
	@XmlElement (name="InboundSTCC")
	public String getInboundSTCC() { return InboundSTCC; }
	public void setInboundSTCC(String InboundSTCC) { this.InboundSTCC = InboundSTCC; }
	
	@XmlElement (name="InboundEmbargo")
	public String getInboundEmbargo() { return InboundEmbargo; }
	public void setInboundEmbargo(String InboundEmbargo) { this.InboundEmbargo = InboundEmbargo; }
	
	@XmlElement (name="InboundPermit")
	public String getInboundPermit() { return InboundPermit; }
	public void setInboundPermit(String InboundPermit) { this.InboundPermit = InboundPermit; }
	
	@XmlElement (name="DepartureDate")
	public String getDepartureDate() { return DepartureDate; }
	public void setDepartureDate(String DepartureDate) { this.DepartureDate = DepartureDate; }
	
	@XmlElement (name="OutboundSCAC")
	public String getOutboundSCAC() { return OutboundSCAC; }
	public void setOutboundSCAC(String OutboundSCAC) { this.OutboundSCAC = OutboundSCAC; }
	
	@XmlElement (name="OutboundFSAC")
	public String getOutboundFSAC() { return OutboundFSAC; }
	public void setOutboundFSAC(String OutboundFSAC) { this.OutboundFSAC = OutboundFSAC; }
	
	@XmlElement (name="OutboundLoadedWeight")
	public double getOutboundLoadedWeight() { return OutboundLoadedWeight; }
	public void setOutboundLoadedWeight(double OutboundLoadedWeight) { this.OutboundLoadedWeight = OutboundLoadedWeight; }
	
	@XmlElement (name="OutboundTrainID")
	public String getOutboundTrainID() { return OutboundTrainID; }
	public void setOutboundTrainID(String OutboundTrainID) { this.OutboundTrainID = OutboundTrainID; }
	
	@XmlElement (name="OutboundTrainEvent")
	public String getOutboundTrainEvent() { return OutboundTrainEvent; }
	public void setOutboundTrainEvent(String OutboundTrainEvent) { this.OutboundTrainEvent = OutboundTrainEvent; }
	
	@XmlElement (name="OutboundWayBill")
	public String getOutboundWayBill() { return OutboundWayBill; }
	public void setOutboundWayBill(String OutboundWayBill) { this.OutboundWayBill = OutboundWayBill; }
	
	@XmlElement (name="OutboundWayBillDate")
	public Date getOutboundWayBillDate() { return OutboundWayBillDate; }
	public void setOutboundWayBillDate(Date OutboundWayBillDate) { this.OutboundWayBillDate = OutboundWayBillDate; }
	
	@XmlElement (name="OutboundWayBillSN")
	public String getOutboundWayBillSN() { return OutboundWayBillSN; }
	public void setOutboundWayBillSN(String OutboundWayBillSN) { this.OutboundWayBillSN = OutboundWayBillSN; }
	
	@XmlElement (name="OutboundBOL")
	public String getOutboundBOL() { return OutboundBOL; }
	public void setOutboundBOL(String OutboundBOL) { this.OutboundBOL = OutboundBOL; }
	
	@XmlElement (name="OutboundBOLDate")
	public Date getOutboundBOLDate() { return OutboundBOLDate; }
	public void setOutboundBOLDate(Date OutboundBOLDate) { this.OutboundBOLDate = OutboundBOLDate; }
	
	@XmlElement (name="OutboundEmbargo")
	public String getOutboundEmbargo() { return OutboundEmbargo; }
	public void setOutboundEmbargo(String OutboundEmbargo) { this.OutboundEmbargo = OutboundEmbargo; }
	
	@XmlElement (name="OutboundPermit")
	public String getOutboundPermit() { return OutboundPermit; }
	public void setOutboundPermit(String OutboundPermit) { this.OutboundPermit = OutboundPermit; }
	
	@XmlElement (name="Notes")
	public String getNotes() { return Notes; }
	public void setNotes(String Notes) { this.Notes = Notes; }
	
	@XmlElement (name="Consignee")
	public String getConsignee() { return Consignee; }
	public void setConsignee(String Consignee) { this.Consignee = Consignee; }
	
	@XmlElement (name="Shipper")
	public String getShipper() { return Shipper; }
	public void setShipper(String Shipper) { this.Shipper = Shipper; }
	
	@XmlElement (name="ArrivalDate")
	public Date getArrivalDate() { return ArrivalDate; }
	public void setArrivalDate(Date ArrivalDate) { this.ArrivalDate = ArrivalDate; }
	
	@XmlElement (name="RailShipApproveDate")
	public Date getRailShipApproveDate() { return RailShipApproveDate; }
	public void setRailShipApproveDate(Date RailShipApproveDate) { this.RailShipApproveDate = RailShipApproveDate; }
	
	@XmlElement (name="ChngWho")
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String ChngWho) { this.ChngWho = ChngWho; }
	
	@XmlElement (name="ChngDate")
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date ChngDate) { this.ChngDate = ChngDate; }
	
	@XmlElement (name="TotalVINsLoaded")
	public int getTotalVINsLoaded() { return TotalVINsLoaded; }
	public void setTotalVINsLoaded(int TotalVINsLoaded) { this.TotalVINsLoaded = TotalVINsLoaded; }
	
	public double getKey() {
		return key;
	}

	public void setKey(double key) {
		this.key = key;
	}
	// }}
	
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Railspot GetRailspot(String railcar) {
		Railspot railspot = null;
		railspot = rsDAO.get(railcar);
		return railspot;
	}
	
	public Railspot GetRailSpot(RailspotSearchCriteria rsc) {
		Railspot railspot = null;
		railspot = rsDAO.get(rsc);
		return railspot;
	}
	
	public List<Railspot> GetRailspots(RailspotSearchCriteria rsc) throws SQLException{
		List<Railspot> railspots = null;
		
		railspots = rsDAO.getList(rsc);
		
		return railspots;
	}
	
	public Railcar GetRailcar(String railcarnum) {
		if(railcarnum == null) railcarnum = this.getRailcarNumber();
		
		return rsDAO.getRailcar(railcarnum);
	}

	public void Save() {
		Save(false);
	}
	
	public void Save(boolean OverRideDate) {
		if(OverRideDate) this.setChngDate(new Date());
		
		if(Validate()) {
			rsDAO.set(this);
		} else {
			//Throw railcar information is not valid?
		}		
		
		//Possible errors are Unknown Manufacturer
		// and Invalid Manufacturer & Route combination
		
	}
	
	public void SaveHistory() {
		rsDAO.saveHistory(this);
	}
	
	public void Delete() {
		this.Delete(true);
	}
	
	/**
	 * '*********************************************************************************************
	'
	' Changed By : Greg Macklem
	' Changed On : 08/17/2001
	' Change Desc: Removed the check of the VIN status.  This method should only delete the
	'              the railspot, all checking of VIN status should be done in the Bay Logic
	'              ClearRail method.  This method should not be used directly to clear a track
	'              spot.
	'
	'*********************************************************************************************
	 * @param overridedate
	 */
	public void Delete(boolean overridedate) {
		
		//Make sure a railcarnumber is set
		if(this.getRailcarNumber() == null || this.getRailcarNumber().length() < 1) {
			return;
		}
		
		//Set the date
		if(overridedate) {
			this.setChngDate(Common.getCurrentDateTime());
		}
		
		//Call the DAO
		rsDAO.delete(this);
		
		//Remove RSA Audit
		RSAAudit rsa = new RSAAudit(database);
		rsa.setRailcar(this.getRailcarNumber());
		rsa.DeleteRSAAudit(rsa);
		
		//Remove Embargo
		Embargo embargo = new Embargo(database);
		embargo.setRailcar(this.getRailcarNumber());
		embargo.setAction(Constants.EMBARGO_ACTION_DELETE);
		embargo.setChngDate(Common.getCurrentDateTime());
		embargo.Delete();
	}
	
	public int GetFilledPosInRailcar(String railcar, String deck) {
		return rsDAO.GetFilledPosInRailcar(railcar,deck);
	}
	
	private boolean Validate() {
		if(this.LotCode.length() != 3) {
			 //Throw INVALID_TRACKLOT
		}
			
		if(this.ZoneID.length() != Constants.ZONE_LEN) {
			//Throw INVALID_TRACKZONE
		}
		
		if(this.TrackNbr.equals("0") || this.TrackNbr.length() != Constants.AREA_LEN) {
			//Throw INVALID_TRACK_NBR
		}
		
		if(this.Spot == 0) {
			//Throw NO_SPOT_SPECIFIED
		}
		
		if(this.RailcarNumber.length() != Constants.RAILCAR_LENGTH ) {
			//Throw INVALID_RAILCAR_NUM
		}
		
		if(this.StatusCode.length() == 0 ) {
			this.StatusCode = Constants.STAT_RAIL_EMPTY;
		}
		
		return true;
	}


}
