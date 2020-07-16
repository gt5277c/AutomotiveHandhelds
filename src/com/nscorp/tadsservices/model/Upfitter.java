package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.UpfitterDAO;
import com.nscorp.tadsservices.model.SearchCriteria.UpfitterSearchCriteria;

@XmlRootElement (name="Upfitter")
@Entity
public class Upfitter extends TADSLocationObject{
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Manufacturer;
	private String Code;
	private String Name;
	private String Address;
	private String City;
	private String State;
	private String ZIP;
	private String ChngWho;
	private Date ChngDate;
	private String Lot;
	private String Zone;
	private String Row;
	
	private UpfitterDAO uDAO;
	// }}
	
	//Constructor
	public Upfitter() {}
	
	public Upfitter(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		uDAO = new UpfitterDAO(database);
	}
	
	@XmlElement (name="Manufacturer")
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String manufacturer) { Manufacturer = manufacturer;	}

	@XmlElement (name="Code")
	public String getCode() { return Code; }
	public void setCode(String code) { Code = code; }

	@XmlElement (name="Name")
	public String getName() { return Name; }
	public void setName(String name) { Name = name; }

	@XmlElement (name="Address")
	public String getAddress() { return Address; }
	public void setAddress(String address) { Address = address; }

	@XmlElement (name="City")
	public String getCity() { return City; }
	public void setCity(String city) { City = city; }

	@XmlElement (name="State")
	public String getState() { return State; }
	public void setState(String state) { State = state; }

	@XmlElement (name="ZIP")
	public String getZIP() { return ZIP; }
	public void setZIP(String ZIP) { this.ZIP = ZIP; }

	@XmlElement (name="ChngWho")
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho; }

	@XmlElement (name="ChngDate")
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date chngDate) { ChngDate = chngDate; }

	@XmlElement (name="Lot")
	public String getLot() { return Lot; }
	public void setLot(String Lot) { this.Lot = Lot; }

	@XmlElement (name="Zone")
	public String getZone() { return Zone; }
	public void setZone(String Zone) { this.Zone = Zone; }

	@XmlElement (name="Row")
	public String getRow() { return Row; }
	public void setRow(String Row) { this.Row = Row; }	
	
	// }}

	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Upfitter get(UpfitterSearchCriteria usc) {
		return uDAO.get(usc);
	}
	
	public List<Upfitter> getList(UpfitterSearchCriteria usc) throws SQLException{
		return uDAO.getList(usc);
	}
	
	public Upfitter getLastUpfitterUpdated() {
		return uDAO.getLastUpfitterUpdated();
	}
	
	public void Save() {
		uDAO.set(this);
	}
	
	public void Delete() {
		uDAO.delete(this);
	}
}
