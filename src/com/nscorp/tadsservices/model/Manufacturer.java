package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.ManufacturerDAO;

@XmlRootElement (name="Manufacturer")
@Entity
public class Manufacturer extends TADSLocationObject {
	
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String code;
	private String name;
	private String address;
	private String city;
	private String state;
	private String ZIP;
	private String mfgAbrrevName;
	private String VINCheck;
	private String chngWho;
	private Date chngDate;
	
	private ManufacturerDAO mDAO;
	// }}
	
	//Constructor
	public Manufacturer() {}
	
	public Manufacturer(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		mDAO = new ManufacturerDAO(database);
	}
	
	@XmlElement (name="Code")
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }

	@XmlElement (name="Name")
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@XmlElement (name="Address")
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }

	@XmlElement (name="City")
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }

	@XmlElement (name="State")
	public String getState() { return state; }
	public void setState(String state) { this.state = state; }

	@XmlElement (name="ZIP")
	public String getZIP() { return ZIP; }
	public void setZIP(String zIP) { ZIP = zIP; }

	@XmlElement (name="mfgAbrrevName")
	public String getMfgAbrrevName() { return mfgAbrrevName; }
	public void setMfgAbrrevName(String mfgAbrrevName) { this.mfgAbrrevName = mfgAbrrevName; }

	@XmlElement (name="VINCheck")
	public String getVINCheck() { return VINCheck; }
	public void setVINCheck(String vINCheck) { VINCheck = vINCheck; }

	@XmlElement (name="ChngWho")
	public String getChngWho() { return chngWho; }
	public void setChngWho(String chngWho) { this.chngWho = chngWho; }

	@XmlElement (name="ChngDate")
	public Date getChngDate() { return chngDate; }
	public void setChngDate(Date chngDate) { this.chngDate = chngDate; }
	
	// }}

	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Manufacturer getManufacturer(String manufacturer) {
		return mDAO.get(manufacturer);
	}
	
	public Manufacturer getLastManufacturerUpdated() {
		return mDAO.getLastManufacturerUpdated();
	}
	
	public List<Manufacturer> getAllManufacturers(){
		return mDAO.getAllManufacturer();
	}
	
	public void Save() {
		mDAO.set(this);
	}
	
	public void Delete() {
		mDAO.delete(this);
	}
	
	public List<ManfVINCode> getManfVINCodes(){
		return mDAO.getManfVINCodes();
	}
}
