package com.nscorp.tadsservices.model.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.UserGroupMember;

public class UserGroupMemberDAO extends TADSDAO<UserGroupMember> {

	public UserGroupMemberDAO(Database database) {
		this.database = database;
		fieldsMap = getColumnsToFieldsMap();
	}

	@Override
	public UserGroupMember get(String id) {
		return null;
	}
	
	public UserGroupMember getUserGroupMember(String groupid, String userid) {
		Object[] params = new Object[] {groupid,userid};
		return database.executeQuery(UserGroupMember.class, fieldsMap, getSQL("GetTADSGroupMember"), params);
	}

	public List<UserGroupMember> getAllTADSGroupMembers(){
		return database.executeQueryList(UserGroupMember.class, fieldsMap, getSQL("GetAllTADSGroupMembers"));
	}
	
	public List<UserGroupMember> getTADSGroupMembersForGroup(String groupID){
		Object[] params = new Object[] {groupID};
		return database.executeQueryList(UserGroupMember.class, fieldsMap, getSQL("GetTADSGroupMembersForGroup"), params);
	}
	
	public List<UserGroupMember> getTADSGroupMembersForUser(String userID){
		Object[] params = new Object[] {userID};
		return database.executeQueryList(UserGroupMember.class, fieldsMap, getSQL("GetTADSGroupMembersForUser"), params);
	}
	
	@Override
	public int set(UserGroupMember obj) {
		Object[] params = new Object[] {obj.getGroupID(),obj.getUserID(),obj.getChangeWho(),obj.getChangeDate()};
		return database.executeStoredProcedureUpdate("spInsertGroupMember", params);
	}

	@Override
	public int delete(UserGroupMember obj) {
		Object[] params = new Object[] {obj.getGroupID(),obj.getUserID()};
		return database.executeStoredProcedureUpdate("spDeleteGroupMember", params);
	}

	@Override
	public String getSQL(String sqlName) {
		String sql = null;
		
		switch(sqlName) {
			case "GetTADSGroupMember":
				sql = "SELECT tblGroupUsers_GroupID AS [Group ID], tblGroupUsers_UserID AS [User ID], " + 
						"tblGroupUsers_ChangeWho AS [Change Who], tblGroupUsers_ChangeDate AS [Change Date] " + 
						"FROM tblGroupUsers " + 
						"WHERE tblGroupUsers_GroupID = ? " + 
						"AND tblGroupUsers_UserID = ? " + 
						"ORDER BY tblGroupUsers_GroupID, tblGroupUsers_UserID ASC";
				break;
			case "GetAllTADSGroupMembers":
				sql = "SELECT tblGroupUsers_GroupID AS [Group ID], tblGroupUsers_UserID AS [User ID], " + 
						"tblGroupUsers_ChangeWho AS [Change Who], tblGroupUsers_ChangeDate AS [Change Date]  " + 
						"FROM tblGroupUsers " + 
						"ORDER BY tblGroupUsers_GroupID, tblGroupUsers_UserID ASC";
				break;
			case "GetTADSGroupMembersForGroup":
				sql = "SELECT tblGroupUsers_GroupID AS [Group ID], tblGroupUsers_UserID AS [User ID], " + 
						"tblGroupUsers_ChangeWho AS [Change Who], tblGroupUsers_ChangeDate AS [Change Date] " + 
						"FROM tblGroupUsers " + 
						"WHERE tblGroupUsers_GroupID = ? " + 
						"ORDER BY tblGroupUsers_GroupID, tblGroupUsers_UserID ASC";
				break;
			case "GetTADSGroupMembersForUser":
				sql = "SELECT tblGroupUsers_GroupID AS [Group ID], tblGroupUsers_UserID AS [User ID], " + 
						"tblGroupUsers_ChangeWho AS [Change Who], tblGroupUsers_ChangeDate AS [Change Date]  " + 
						"FROM tblGroupUsers " + 
						"WHERE tblGroupUsers_UserID = ? " + 
						"ORDER BY tblGroupUsers_GroupID, tblGroupUsers_UserID ASC";
				break;
			default:
				sql = sqlName;
				break;				
		}

		return sql;
	}

	@Override
	public <E extends Enum<?>> String getSQL(E Find) {
		return null;
	}

	public static Map<String,String> getColumnsToFieldsMap(){
		HashMap<String, String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{  
				//Format is put("database column name", "class field name");
				put("Group ID", "GroupID");
				put("User ID", "UserID");
				put("Change Who", "ChangeWho");
				put("Change Date", "ChangeDate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
