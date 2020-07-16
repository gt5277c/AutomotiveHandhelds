package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.UserDAO;

@XmlRootElement (name="User")
@Entity
public class User extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String UserID;
	private String DriverID = "";
	private String FirstName = "";
	private String LastName = "";
	private String Password = "";
	private String token;
	
	////////////////////////////////////////////////////////////
	//User Permissions
	////////////////////////////////////////////////////////////
	// {{
	private Boolean HandheldUserOnly = false;
	private Boolean CanAddVIN = false;
	private Boolean CanCreateVIN = false;
	private Boolean CanDeleteVIN = false;
	private Boolean CanModifyVIN = false;
	private Boolean CanHoldVIN = false;
	private Boolean CanReleaseHoldVIN = false;
	private Boolean CanTruckIn = false;
	private Boolean CanTruckOut = false;
	private Boolean CanDriveIn = false;
	private Boolean CanDriveOut = false;
	private Boolean CanPlantIn = false;
	private Boolean CanReturnToPlant = false;
	private Boolean CanInboundDirect = false;
	private Boolean CanPreBay = false;
	private Boolean CanCancelPreBay = false;
	private Boolean CanBay = false;
	private Boolean CanReassignVIN = false;
	private Boolean CanLoadRailcar = false;
	private Boolean CanUnloadRailcar = false;
	private Boolean CanRailshipApprove = false;
	private Boolean CanLTDRailcar = false;
	private Boolean CanClearRailcar = false;
	private Boolean CanSpotRailcar = false;
	private Boolean CanModifyLot = false;
	private Boolean CanRunReports = false;
	private Boolean CanPhysicalInv = false;
	private Boolean CanASNMntc = false;
	private Boolean CanDealerMntc = false;
	private Boolean CanMfgMntc = false;
	private Boolean CanRailcarMntc = false;
	private Boolean CanRouteMntc = false;
	private Boolean CanVehicleTypeMntc = false;
	private Boolean CanHoldMntc = false;
	private Boolean CanBayingMntc = false;
	private Boolean CanModifyAdvConsist = false;
	private Boolean CanPreLoad = false;
	private Boolean CanReverseTrack = false;
	private Boolean CanAdminVHA = false;
	private Boolean CanAdminUpfitter = false;
	private Boolean MQMessages = false;
	private Boolean Embargos = false;
	private Boolean Administrator = false;
	// }}
	
	//expiry = java.text.DateFormat.getDateInstance().parse(dt);
	//private Date LastPWChangeDate = new Date("1/1/1899");
	private Date LastPWChangeDate;
	private String ChangeWho ="";
	private Date ChangeDate;
	//private m_oGroups As clsGroups
	//private m_oUserOption As clsUserOption
	private String Location;
	
	private UserDAO uDAO;
	// }}
	
	//Constructor
	public User() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			
			LastPWChangeDate = format.parse("01/01/1899");
			ChangeDate = format.parse("01/01/1899");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public User(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		uDAO = new UserDAO(database);
	}
	
	@XmlElement (name="UserID")
	public String getUserID() { return UserID; }
	public void setUserID(String userID) { UserID = userID; }

	@XmlElement (name="Password")
	public String getPassword() { return Password; }
	public void setPassword(String password) { Password = password; }

	@XmlElement (name="Location")
	public String getLocation() { return Location; }
	public void setLocation(String Location) { this.Location = Location; }
	
	@XmlElement (name="DriverID")
	public String getDriverID() { return DriverID; }
	public void setDriverID(String driverID) { DriverID = driverID; }

	@XmlElement (name="FirstName")
	public String getFirstName() { return FirstName; }
	public void setFirstName(String firstName) { FirstName = firstName; }

	@XmlElement (name="LastName")
	public String getLastName() { return LastName; }
	public void setLastName(String lastName) { LastName = lastName; }
	
	@XmlElement (name="token")
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	
	@XmlElement (name="HandheldUserOnly")
	public Boolean getHandheldUserOnly() { return HandheldUserOnly; }
	public void setHandheldUserOnly(Boolean handheldUserOnly) { HandheldUserOnly = handheldUserOnly; }

	@XmlElement (name="CanAddVIN")
	public Boolean getCanAddVIN() { return CanAddVIN; }
	public void setCanAddVIN(Boolean canAddVIN) { CanAddVIN = canAddVIN; }

	@XmlElement (name="CanCreateVIN")
	public Boolean getCanCreateVIN() { return CanCreateVIN; }
	public void setCanCreateVIN(Boolean canCreateVIN) { CanCreateVIN = canCreateVIN; }

	@XmlElement (name="CanDeleteVIN")
	public Boolean getCanDeleteVIN() { return CanDeleteVIN; }
	public void setCanDeleteVIN(Boolean canDeleteVIN) { CanDeleteVIN = canDeleteVIN; }

	@XmlElement (name="CanModifyVIN")
	public Boolean getCanModifyVIN() { return CanModifyVIN; }
	public void setCanModifyVIN(Boolean canModifyVIN) { CanModifyVIN = canModifyVIN; }

	@XmlElement (name="CanHoldVIN")
	public Boolean getCanHoldVIN() { return CanHoldVIN; }
	public void setCanHoldVIN(Boolean canHoldVIN) { CanHoldVIN = canHoldVIN; }

	@XmlElement (name="CanReleaseHoldVIN")
	public Boolean getCanReleaseHoldVIN() { return CanReleaseHoldVIN; }
	public void setCanReleaseHoldVIN(Boolean canReleaseHoldVIN) { CanReleaseHoldVIN = canReleaseHoldVIN; }

	@XmlElement (name="CanTruckIn")
	public Boolean getCanTruckIn() { return CanTruckIn; }
	public void setCanTruckIn(Boolean canTruckIn) { CanTruckIn = canTruckIn; }

	@XmlElement (name="CanTruckOut")
	public Boolean getCanTruckOut() { return CanTruckOut; }
	public void setCanTruckOut(Boolean canTruckOut) { CanTruckOut = canTruckOut; }

	@XmlElement (name="CanDriveIn")
	public Boolean getCanDriveIn() { return CanDriveIn; }
	public void setCanDriveIn(Boolean canDriveIn) { CanDriveIn = canDriveIn; }

	@XmlElement (name="CanDriveOut")
	public Boolean getCanDriveOut() { return CanDriveOut; }
	public void setCanDriveOut(Boolean canDriveOut) { CanDriveOut = canDriveOut; }

	@XmlElement (name="CanPlantIn")
	public Boolean getCanPlantIn() { return CanPlantIn; }
	public void setCanPlantIn(Boolean canPlantIn) { CanPlantIn = canPlantIn; }

	@XmlElement (name="CanReturnToPlant")
	public Boolean getCanReturnToPlant() { return CanReturnToPlant; }
	public void setCanReturnToPlant(Boolean canReturnToPlant) { CanReturnToPlant = canReturnToPlant; }

	@XmlElement (name="CanInboundDirect")
	public Boolean getCanInboundDirect() { return CanInboundDirect; }
	public void setCanInboundDirect(Boolean canInboundDirect) { CanInboundDirect = canInboundDirect; }

	@XmlElement (name="CanPreBay")
	public Boolean getCanPreBay() { return CanPreBay; }
	public void setCanPreBay(Boolean canPreBay) { CanPreBay = canPreBay; }

	@XmlElement (name="CanCancelPreBay")
	public Boolean getCanCancelPreBay() { return CanCancelPreBay; }
	public void setCanCancelPreBay(Boolean canCancelPreBay) { CanCancelPreBay = canCancelPreBay; }

	@XmlElement (name="CanBay")
	public Boolean getCanBay() { return CanBay; }
	public void setCanBay(Boolean canBay) { CanBay = canBay; }

	@XmlElement (name="CanReassignVIN")
	public Boolean getCanReassignVIN() { return CanReassignVIN; }
	public void setCanReassignVIN(Boolean canReassignVIN) { CanReassignVIN = canReassignVIN; }

	@XmlElement (name="CanLoadRailcar")
	public Boolean getCanLoadRailcar() { return CanLoadRailcar; }
	public void setCanLoadRailcar(Boolean canLoadRailcar) { CanLoadRailcar = canLoadRailcar; }

	@XmlElement (name="CanUnloadRailcar")
	public Boolean getCanUnloadRailcar() { return CanUnloadRailcar; }
	public void setCanUnloadRailcar(Boolean canUnloadRailcar) { CanUnloadRailcar = canUnloadRailcar; }

	@XmlElement (name="CanRailshipApprove")
	public Boolean getCanRailshipApprove() { return CanRailshipApprove; }
	public void setCanRailshipApprove(Boolean canRailshipApprove) { CanRailshipApprove = canRailshipApprove; }

	@XmlElement (name="CanLTDRailcar")
	public Boolean getCanLTDRailcar() { return CanLTDRailcar; }
	public void setCanLTDRailcar(Boolean canLTDRailcar) { CanLTDRailcar = canLTDRailcar; }

	@XmlElement (name="CanClearRailcar")
	public Boolean getCanClearRailcar() { return CanClearRailcar; }
	public void setCanClearRailcar(Boolean canClearRailcar) { CanClearRailcar = canClearRailcar; }

	@XmlElement (name="CanSpotRailcar")
	public Boolean getCanSpotRailcar() { return CanSpotRailcar; }
	public void setCanSpotRailcar(Boolean canSpotRailcar) { CanSpotRailcar = canSpotRailcar; }

	@XmlElement (name="CanModifyLot")
	public Boolean getCanModifyLot() { return CanModifyLot; }
	public void setCanModifyLot(Boolean canModifyLot) { CanModifyLot = canModifyLot; }

	@XmlElement (name="CanRunReports")
	public Boolean getCanRunReports() { return CanRunReports; }
	public void setCanRunReports(Boolean canRunReports) { CanRunReports = canRunReports; }

	@XmlElement (name="CanPhysicalInv")
	public Boolean getCanPhysicalInv() { return CanPhysicalInv; }
	public void setCanPhysicalInv(Boolean canPhysicalInv) { CanPhysicalInv = canPhysicalInv; }

	@XmlElement (name="CanASNMntc")
	public Boolean getCanASNMntc() { return CanASNMntc; }
	public void setCanASNMntc(Boolean canASNMntc) { CanASNMntc = canASNMntc; }

	@XmlElement (name="CanDealerMntc")
	public Boolean getCanDealerMntc() { return CanDealerMntc; }
	public void setCanDealerMntc(Boolean canDealerMntc) { CanDealerMntc = canDealerMntc; }

	@XmlElement (name="CanMfgMntc")
	public Boolean getCanMfgMntc() { return CanMfgMntc; }
	public void setCanMfgMntc(Boolean canMfgMntc) { CanMfgMntc = canMfgMntc; }

	@XmlElement (name="CanRailcarMntc")
	public Boolean getCanRailcarMntc() { return CanRailcarMntc; }
	public void setCanRailcarMntc(Boolean canRailcarMntc) { CanRailcarMntc = canRailcarMntc; }

	@XmlElement (name="CanRouteMntc")
	public Boolean getCanRouteMntc() { return CanRouteMntc; }
	public void setCanRouteMntc(Boolean canRouteMntc) { CanRouteMntc = canRouteMntc; }

	@XmlElement (name="CanVehicleTypeMntc")
	public Boolean getCanVehicleTypeMntc() { return CanVehicleTypeMntc; }
	public void setCanVehicleTypeMntc(Boolean canVehicleTypeMntc) { CanVehicleTypeMntc = canVehicleTypeMntc; }

	@XmlElement (name="CanHoldMntc")
	public Boolean getCanHoldMntc() { return CanHoldMntc; }
	public void setCanHoldMntc(Boolean canHoldMntc) { CanHoldMntc = canHoldMntc; }

	@XmlElement (name="CanBayingMntc")
	public Boolean getCanBayingMntc() { return CanBayingMntc; }
	public void setCanBayingMntc(Boolean canBayingMntc) { CanBayingMntc = canBayingMntc; }

	@XmlElement (name="CanModifyAdvConsist")
	public Boolean getCanModifyAdvConsist() { return CanModifyAdvConsist; }
	public void setCanModifyAdvConsist(Boolean canModifyAdvConsist) { CanModifyAdvConsist = canModifyAdvConsist; }

	@XmlElement (name="CanPreLoad")
	public Boolean getCanPreLoad() { return CanPreLoad; }
	public void setCanPreLoad(Boolean canPreLoad) { CanPreLoad = canPreLoad; }

	@XmlElement (name="CanReverseTrack")
	public Boolean getCanReverseTrack() { return CanReverseTrack; }
	public void setCanReverseTrack(Boolean canReverseTrack) { CanReverseTrack = canReverseTrack; }

	@XmlElement (name="CanAdminVHA")
	public Boolean getCanAdminVHA() { return CanAdminVHA; }
	public void setCanAdminVHA(Boolean canAdminVHA) { CanAdminVHA = canAdminVHA; }

	@XmlElement (name="CanAdminUpfitter")
	public Boolean getCanAdminUpfitter() { return CanAdminUpfitter; }
	public void setCanAdminUpfitter(Boolean canAdminUpfitter) { CanAdminUpfitter = canAdminUpfitter; }

	@XmlElement (name="MQMessages")
	public Boolean getMQMessages() { return MQMessages;	}
	public void setMQMessages(Boolean mQMessages) { MQMessages = mQMessages; }
	
	@XmlElement (name="Embargos")
	public Boolean getEmbargos() { return Embargos; }
	public void setEmbargos(Boolean embargos) { Embargos = embargos; }

	@XmlElement (name="Administrator")
	public Boolean getAdministrator() { return Administrator; }
	public void setAdministrator(Boolean administrator) { this.Administrator = administrator; }

	@XmlElement (name="LastPWChangeDate")
	public Date getLastPWChangeDate() { return LastPWChangeDate; }
	public void setLastPWChangeDate(Date lastPWChangeDate) { LastPWChangeDate = lastPWChangeDate; }

	@XmlElement (name="ChangeWho")
	public String getChangeWho() { return ChangeWho; }
	public void setChangeWho(String changeWho) { ChangeWho = changeWho; }

	@XmlElement (name="ChangeDate")
	public Date getChangeDate() { return ChangeDate; }
	public void setChangeDate(Date changeDate) { ChangeDate = changeDate; }
	
	// }}

	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public User get(String userID) {
		return uDAO.get(userID);
	}
	
	public List<User> getAll() throws SQLException{
		return uDAO.getAll();
	}
	
	public int Save() {
		return uDAO.set(this);
	}
	
	public int Save(User user) {
		return uDAO.set(user);
	}
	
	public int Delete() {
		return uDAO.delete(this);
	}

}
