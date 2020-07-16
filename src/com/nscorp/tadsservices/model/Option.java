package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.OptionDAO;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

@XmlRootElement (name="Option")
@Entity
public class Option extends TADSLocationObject{
	
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Name;
	private int OptionType;
	private Object Value;
	private String ChangeWho;
	private Date ChangeDate;
	private static OptionDAO oDAO;
	// }}
	
	//Constructor
	public Option() {}
	
	public Option(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		oDAO = new OptionDAO(database);
	}
	
	@XmlElement (name="Name")
	public String getName() { return Name; }
	public void setName(String Name) { this.Name = Name; }
	
	@XmlElement (name="OptionType")
	public int getType() { return OptionType; }
	public void setType(int OptionType) { this.OptionType = OptionType; }
	
	@XmlElement (name="Value")
	public Object getValue() { return Value; }
	public void setValue(Object Value) {	
		switch(OptionType) {
		case 1:
		case 2:
			try {
				this.Value = (String)Value;
			} catch(Exception e) {
				this.Value = "";
			}
			break;
		case 3:
			try {
				this.Value = Integer.parseInt((String) Value);
			} catch (Exception e) {
				Value = 0;
			}
			break;
		case 4:
			if(Value.equals("Y")) {
				this.Value = true;
			} else {
				this.Value = false;
			}
			break;
		default:
			try {
				this.Value = (String)Value;
			} catch(Exception e) {
				this.Value = "";
			}
			break;
		}
	}

	@XmlElement (name="ChangeWho")
	public String getChangeWho() { return ChangeWho; }
	public void setChangeWho(String ChangeWho) { this.ChangeWho = ChangeWho; }

	@XmlElement (name="ChangeDate")
	public Date getChangeDate() { return ChangeDate; }
	public void setChangeDate(Date ChangeDate) { this.ChangeDate = ChangeDate; }
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Option getOption(String name) {
		return oDAO.get(name);
	}
	
	public List<Option> getOptionList(){
		return oDAO.getOptionList();
	}
	
	public Option getLastOptionUpdated() {
		return oDAO.getLastOptionUpdated();
	}
	
	public void Save() {
		oDAO.set(this);
	}
	
	public void Save(Option obj) {
		oDAO.set(obj);
	}
	
	public void Delete() {
		oDAO.delete(this);
	}
	
	public void Delete(Option obj) {
		oDAO.delete(obj);
	}
}
