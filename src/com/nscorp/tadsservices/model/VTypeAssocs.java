package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.VTypeAssocsDAO;

public class VTypeAssocs extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private String VTypeAssocCode;
	private String ChngWho;
	private String ChngDate;

	private VTypeAssocsDAO vtaDAO;
	
	//Constructor
	public VTypeAssocs() {}
	
	public VTypeAssocs(Database database) {
		setLocationDatabase(database);
	};
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		vtaDAO = new VTypeAssocsDAO(database);
	}
	
	public VTypeAssocsDAO getVtDAO() {
		return vtaDAO;
	}
	public void setVtDAO(VTypeAssocsDAO vtDAO) {
		this.vtaDAO = vtDAO;
	}
	
	public String getVTypeAssocCode() {
		return VTypeAssocCode;
	}
	public void setVTypeAssocCode(String vTypeAssocCode) {
		VTypeAssocCode = vTypeAssocCode;
	}
	
	public String getChngWho() {
		return ChngWho;
	}
	public void setChngWho(String chngWho) {
		ChngWho = chngWho;
	}
	
	public String getChngDate() {
		return ChngDate;
	}
	public void setChngDate(String chngDate) {
		ChngDate = chngDate;
	}
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////	
	public VTypeAssocs getVTypeAssocs(String code) {
		return vtaDAO.get(code);
	}

	public List<VTypeAssocs> getVTypeAssocsList(String vtCode) throws SQLException{
		return vtaDAO.getList(vtCode);
	}
}
