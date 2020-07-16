package com.nscorp.tadsservices.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.TruckCompanyDAO;

@XmlRootElement (name="TruckCompany")
@Entity
public class TruckCompany extends TADSLocationObject {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String code;
	private String description;
	private String scac;
	private TruckCompanyDAO tDAO;
	// }}
	
	//Constructor
	public TruckCompany() {}
	
	public TruckCompany(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		tDAO = new TruckCompanyDAO(database);
	}
	
	@XmlElement (name="Code")
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	
	@XmlElement (name="Description")
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	@XmlElement (name="Scac")
	public String getScac() { return scac; }
	public void setScac(String scac) { this.scac = scac; }

	@Override
	public String toString() {
		return "Code: " + code + "\n" + 
				"Description: " + description + "\n" + 	
				"SCAC: " + scac + "\n";
	}
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public List<TruckCompany> getTruckCompanies(){
		return tDAO.getTruckCompanies();
	}
	
	public static Map<String,String> getColumnsToFieldsMap() {
		Map<String,String> columnsToFieldsMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("tblTruckCompany_Code", "code");
				put("tblTruckCompany_Desc", "description");
				put("tblTruckCompany_SCAC", "scac");				
			}
		};
		
		return columnsToFieldsMap;
	}	
}
