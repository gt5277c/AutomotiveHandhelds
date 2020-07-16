package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.UserGroupDAO;

public class UserGroup extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String GroupID;
	private String GroupName;
	private Boolean CanAddVIN;
	private Boolean CanCreateVIN;
	private Boolean CanDeleteVIN;
	private Boolean CanModifyVIN;
	private Boolean CanHoldVIN;
	private Boolean CanReleaseHoldVIN;
	private Boolean CanTruckIn;
	private Boolean CanTruckOut;
	private Boolean CanDriveIn;
	private Boolean CanDriveOut;
	private Boolean CanPlantIn;
	private Boolean CanReturnToPlant;
	private Boolean CanInboundDirect;
	private Boolean CanPreBay;
	private Boolean CanCancelPreBay;
	private Boolean CanBay;
	private Boolean CanReassignVIN;
	private Boolean CanLoadRailcar;
	private Boolean CanUnloadRailcar;
	private Boolean CanRailshipApprove;
	private Boolean CanLTDRailcar;
	private Boolean CanClearRailcar;
	private Boolean CanSpotRailcar;
	private Boolean CanModifyLot;
	private Boolean CanRunReports;
	private Boolean CanPhysicalInv;
	private Boolean CanASNMntc;
	private Boolean CanDealerMntc;
	private Boolean CanMfgMntc;
	private Boolean CanRailcarMntc;
	private Boolean CanRouteMntc;
	private Boolean CanVehicleTypeMntc;
	private Boolean CanHoldMntc;
	private Boolean CanBayingMntc;
	private Boolean CanModifyAdvConsist;
	private Boolean CanPreLoad;
	private Boolean CanReverseTrack;
	private Boolean CanAdminVHA;
	private Boolean CanAdminUpfitter;
	private Boolean MQMessages;
	private Boolean Embargos;
	private Boolean Administrator;
	private String ChangeWho;
	private Date ChangeDate;
	
	private UserGroupDAO ugDAO;
	// }}
	
	//Constructor
	public UserGroup() {}
	
	public UserGroup(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		ugDAO = new UserGroupDAO(database);
	}
	
	public String getGroupID() {
		return GroupID;
	}

	public void setGroupID(String groupID) {
		GroupID = groupID;
	}

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	public Boolean getCanAddVIN() {
		return CanAddVIN;
	}

	public void setCanAddVIN(Boolean canAddVIN) {
		CanAddVIN = canAddVIN;
	}

	public Boolean getCanCreateVIN() {
		return CanCreateVIN;
	}

	public void setCanCreateVIN(Boolean canCreateVIN) {
		CanCreateVIN = canCreateVIN;
	}

	public Boolean getCanDeleteVIN() {
		return CanDeleteVIN;
	}

	public void setCanDeleteVIN(Boolean canDeleteVIN) {
		CanDeleteVIN = canDeleteVIN;
	}

	public Boolean getCanModifyVIN() {
		return CanModifyVIN;
	}

	public void setCanModifyVIN(Boolean canModifyVIN) {
		CanModifyVIN = canModifyVIN;
	}

	public Boolean getCanHoldVIN() {
		return CanHoldVIN;
	}

	public void setCanHoldVIN(Boolean canHoldVIN) {
		CanHoldVIN = canHoldVIN;
	}

	public Boolean getCanReleaseHoldVIN() {
		return CanReleaseHoldVIN;
	}

	public void setCanReleaseHoldVIN(Boolean canReleaseHoldVIN) {
		CanReleaseHoldVIN = canReleaseHoldVIN;
	}

	public Boolean getCanTruckIn() {
		return CanTruckIn;
	}

	public void setCanTruckIn(Boolean canTruckIn) {
		CanTruckIn = canTruckIn;
	}

	public Boolean getCanTruckOut() {
		return CanTruckOut;
	}

	public void setCanTruckOut(Boolean canTruckOut) {
		CanTruckOut = canTruckOut;
	}

	public Boolean getCanDriveIn() {
		return CanDriveIn;
	}

	public void setCanDriveIn(Boolean canDriveIn) {
		CanDriveIn = canDriveIn;
	}

	public Boolean getCanDriveOut() {
		return CanDriveOut;
	}

	public void setCanDriveOut(Boolean canDriveOut) {
		CanDriveOut = canDriveOut;
	}

	public Boolean getCanPlantIn() {
		return CanPlantIn;
	}

	public void setCanPlantIn(Boolean canPlantIn) {
		CanPlantIn = canPlantIn;
	}

	public Boolean getCanReturnToPlant() {
		return CanReturnToPlant;
	}

	public void setCanReturnToPlant(Boolean canReturnToPlant) {
		CanReturnToPlant = canReturnToPlant;
	}

	public Boolean getCanInboundDirect() {
		return CanInboundDirect;
	}

	public void setCanInboundDirect(Boolean canInboundDirect) {
		CanInboundDirect = canInboundDirect;
	}

	public Boolean getCanPreBay() {
		return CanPreBay;
	}

	public void setCanPreBay(Boolean canPreBay) {
		CanPreBay = canPreBay;
	}

	public Boolean getCanCancelPreBay() {
		return CanCancelPreBay;
	}

	public void setCanCancelPreBay(Boolean canCancelPreBay) {
		CanCancelPreBay = canCancelPreBay;
	}

	public Boolean getCanBay() {
		return CanBay;
	}

	public void setCanBay(Boolean canBay) {
		CanBay = canBay;
	}

	public Boolean getCanReassignVIN() {
		return CanReassignVIN;
	}

	public void setCanReassignVIN(Boolean canReassignVIN) {
		CanReassignVIN = canReassignVIN;
	}

	public Boolean getCanLoadRailcar() {
		return CanLoadRailcar;
	}

	public void setCanLoadRailcar(Boolean canLoadRailcar) {
		CanLoadRailcar = canLoadRailcar;
	}

	public Boolean getCanUnloadRailcar() {
		return CanUnloadRailcar;
	}

	public void setCanUnloadRailcar(Boolean canUnloadRailcar) {
		CanUnloadRailcar = canUnloadRailcar;
	}

	public Boolean getCanRailshipApprove() {
		return CanRailshipApprove;
	}

	public void setCanRailshipApprove(Boolean canRailshipApprove) {
		CanRailshipApprove = canRailshipApprove;
	}

	public Boolean getCanLTDRailcar() {
		return CanLTDRailcar;
	}

	public void setCanLTDRailcar(Boolean canLTDRailcar) {
		CanLTDRailcar = canLTDRailcar;
	}

	public Boolean getCanClearRailcar() {
		return CanClearRailcar;
	}

	public void setCanClearRailcar(Boolean canClearRailcar) {
		CanClearRailcar = canClearRailcar;
	}

	public Boolean getCanSpotRailcar() {
		return CanSpotRailcar;
	}

	public void setCanSpotRailcar(Boolean canSpotRailcar) {
		CanSpotRailcar = canSpotRailcar;
	}

	public Boolean getCanModifyLot() {
		return CanModifyLot;
	}

	public void setCanModifyLot(Boolean canModifyLot) {
		CanModifyLot = canModifyLot;
	}

	public Boolean getCanRunReports() {
		return CanRunReports;
	}

	public void setCanRunReports(Boolean canRunReports) {
		CanRunReports = canRunReports;
	}

	public Boolean getCanPhysicalInv() {
		return CanPhysicalInv;
	}

	public void setCanPhysicalInv(Boolean canPhysicalInv) {
		CanPhysicalInv = canPhysicalInv;
	}

	public Boolean getCanASNMntc() {
		return CanASNMntc;
	}

	public void setCanASNMntc(Boolean canASNMntc) {
		CanASNMntc = canASNMntc;
	}

	public Boolean getCanDealerMntc() {
		return CanDealerMntc;
	}

	public void setCanDealerMntc(Boolean canDealerMntc) {
		CanDealerMntc = canDealerMntc;
	}

	public Boolean getCanMfgMntc() {
		return CanMfgMntc;
	}

	public void setCanMfgMntc(Boolean canMfgMntc) {
		CanMfgMntc = canMfgMntc;
	}

	public Boolean getCanRailcarMntc() {
		return CanRailcarMntc;
	}

	public void setCanRailcarMntc(Boolean canRailcarMntc) {
		CanRailcarMntc = canRailcarMntc;
	}

	public Boolean getCanRouteMntc() {
		return CanRouteMntc;
	}

	public void setCanRouteMntc(Boolean canRouteMntc) {
		CanRouteMntc = canRouteMntc;
	}

	public Boolean getCanVehicleTypeMntc() {
		return CanVehicleTypeMntc;
	}

	public void setCanVehicleTypeMntc(Boolean canVehicleTypeMntc) {
		CanVehicleTypeMntc = canVehicleTypeMntc;
	}

	public Boolean getCanHoldMntc() {
		return CanHoldMntc;
	}

	public void setCanHoldMntc(Boolean canHoldMntc) {
		CanHoldMntc = canHoldMntc;
	}

	public Boolean getCanBayingMntc() {
		return CanBayingMntc;
	}

	public void setCanBayingMntc(Boolean canBayingMntc) {
		CanBayingMntc = canBayingMntc;
	}

	public Boolean getCanModifyAdvConsist() {
		return CanModifyAdvConsist;
	}

	public void setCanModifyAdvConsist(Boolean canModifyAdvConsist) {
		CanModifyAdvConsist = canModifyAdvConsist;
	}

	public Boolean getCanPreLoad() {
		return CanPreLoad;
	}

	public void setCanPreLoad(Boolean canPreLoad) {
		CanPreLoad = canPreLoad;
	}

	public Boolean getCanReverseTrack() {
		return CanReverseTrack;
	}

	public void setCanReverseTrack(Boolean canReverseTrack) {
		CanReverseTrack = canReverseTrack;
	}

	public Boolean getCanAdminVHA() {
		return CanAdminVHA;
	}

	public void setCanAdminVHA(Boolean canAdminVHA) {
		CanAdminVHA = canAdminVHA;
	}

	public Boolean getCanAdminUpfitter() {
		return CanAdminUpfitter;
	}

	public void setCanAdminUpfitter(Boolean canAdminUpfitter) {
		CanAdminUpfitter = canAdminUpfitter;
	}

	public Boolean getMQMessages() {
		return MQMessages;
	}

	public void setMQMessages(Boolean mQMessages) {
		MQMessages = mQMessages;
	}

	public Boolean getEmbargos() {
		return Embargos;
	}

	public void setEmbargos(Boolean embargos) {
		Embargos = embargos;
	}

	public Boolean getAdministrator() {
		return Administrator;
	}

	public void setAdministrator(Boolean administrator) {
		Administrator = administrator;
	}

	public String getChangeWho() {
		return ChangeWho;
	}

	public void setChangeWho(String changeWho) {
		ChangeWho = changeWho;
	}

	public Date getChangeDate() {
		return ChangeDate;
	}

	public void setChangeDate(Date changeDate) {
		ChangeDate = changeDate;
	}
	
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	
	public UserGroup get(String groupname) {
		return ugDAO.get(groupname);
	}
	
	public List<UserGroup> getUserGroupForUser(String user) throws SQLException{
		return ugDAO.getUserGroupUser(user);
	}
	
	public List<UserGroup> getAll(){
		return ugDAO.getAll();
	}
	
	public int Save() {
		return ugDAO.set(this);
	}
	
	public int Delete() {
		return ugDAO.delete(this);
	}
}
