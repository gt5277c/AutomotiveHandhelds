package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.RSAAuditDAO;

public class RSAAudit extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String railcar;
	private String VIN;
	private String Success;
	private String ChgWho;
	private Date ChgDate;
	
	private RSAAuditDAO rsaDAO;
	// }}
	
	//Constructor
	public RSAAudit() {}
	
	public RSAAudit(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		rsaDAO = new RSAAuditDAO(database);
	}
	
	public String getRailcar() {
		return railcar;
	}

	public void setRailcar(String railcar) {
		this.railcar = railcar;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getSuccess() {
		return Success;
	}

	public void setSuccess(String success) {
		Success = success;
	}

	public String getChgWho() {
		return ChgWho;
	}

	public void setChgWho(String chgWho) {
		ChgWho = chgWho;
	}

	public Date getChgDate() {
		return ChgDate;
	}

	public void setChgDate(Date chgDate) {
		ChgDate = chgDate;
	}
	// }}

	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////

	public List<RSAAudit> FindAuditVIN(String vehicle) throws SQLException {	
		return rsaDAO.FindAuditVIN(vehicle);
	}

	public List<RSAAudit> FindAuditRailcar(String Railcar) throws SQLException {	
		return rsaDAO.FindAuditRailcar(Railcar);
	}
	
	public List<RSAAudit> FindAuditAll() throws SQLException {	
		return rsaDAO.FindAuditAll();
	}
	
	public List<RSAAudit> FindAuditSuccesss() throws SQLException {	
		return rsaDAO.FindAuditSuccesss();
	}
	
	public int LogAudit(String vehicle, String Railcar, String ChgWho, Date ChgDate) {
		this.setVIN(vehicle);
		this.setRailcar(Railcar);
		this.setChgWho(ChgWho);
		this.setChgDate(ChgDate);
		
		return rsaDAO.set(this);
	}
	
	public boolean CheckAudit(String Railcar) throws SQLException {
		List<RSAAudit> audits;
		audits = this.FindAuditRailcar(Railcar);
		if(audits != null) {
			for(RSAAudit audit : audits) {
				if(audit.getSuccess().equals("N")) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void DeleteRSAAudit(RSAAudit audit) {
		rsaDAO.delete(audit);
	}
	
	public RSAAudit UpdateRSAAudit(String NewValue, String OldValue) {
		return null;
	}
}
