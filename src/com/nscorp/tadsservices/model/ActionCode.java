package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="ActionCode")
@Entity
public class ActionCode extends TADSLocationObject{

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String code;
	private String description;
	private String chngwho;
	private Date chngdate;
	// }}
	
	//Constructor
	public ActionCode() {}
	
	public ActionCode(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public String getCode() { return code; }
	public void setCode(String code) {	this.code = code; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getChngwho() { return chngwho; }
	public void setChngwho(String chngwho) { this.chngwho = chngwho; }
	
	public Date getChngdate() { return chngdate; }
	public void setChngdate(Date chngdate) { this.chngdate = chngdate; }
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		return "Action Code: " + code + "\n" +
				"Description: " + description + "\n" +
				"User ID: " + chngwho + "\n" +
				"Date: " + chngdate + "\n";
	}
	    
	public static Map<String, String> getColumnsToFieldsMap() {
		@SuppressWarnings("serial")
		Map<String,String> columnsToFieldsMap = new HashMap<String,String>(){
			{
				put("tblActionCodes_Code", "code");
				put("tblActionCodes_Desc", "description");
				put("tblActionCodes_ChngWho", "chngwho");
				put("tblActionCodes_ChngDate", "chngdate");
			}
		};
		
		return columnsToFieldsMap;
	}
}
