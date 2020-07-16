package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.UserGroupMemberDAO;

public class UserGroupMember extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String GroupID;
	private String UserID;
	private String ChangeWho;
	private Date ChangeDate;
	
	private UserGroupMemberDAO ugmDAO;
	// }}
	
	//Constructor
	public UserGroupMember() {}
	
	public UserGroupMember(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		ugmDAO = new UserGroupMemberDAO(database);
	}
	
	public String getGroupID() {
		return GroupID;
	}

	public void setGroupID(String groupID) {
		GroupID = groupID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
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
	public UserGroupMember get(String groupid, String userid) {
		return ugmDAO.getUserGroupMember(groupid, userid);
	}
	
	public List<UserGroupMember> getAllTADSGroupMembers(){
		return ugmDAO.getAllTADSGroupMembers();
	}
	
	public List<UserGroupMember> getTADSGroupMembersForGroup(String groupID){
		return ugmDAO.getTADSGroupMembersForGroup(groupID);
	}
	
	public List<UserGroupMember> getTADSGroupMembersForUser(String userID){
		return ugmDAO.getTADSGroupMembersForUser(userID);
	}
	
	public int Save() {
		return ugmDAO.set(this);
	}
	
	public int Delete() {
		return ugmDAO.delete(this);
	}
}
