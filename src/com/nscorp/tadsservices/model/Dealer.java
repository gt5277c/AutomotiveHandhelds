package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.DealerDAO;
import com.nscorp.tadsservices.model.Enums.eDealerObjAction;
import com.nscorp.tadsservices.model.SearchCriteria.DealerSearchCriteria;

@XmlRootElement (name="Dealer")
@Entity
public class Dealer extends TADSLocationObject {

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
	private String Zone;
	private String ChngWho;
	private Date ChngDate;

	private DealerDAO dDAO;
	// }}
	
	//Constructor
	public Dealer() {}
	
	public Dealer(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		dDAO = new DealerDAO(database);
	}
	
	@XmlElement (name="Manufacturer")
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String manufacturer) { Manufacturer = manufacturer; }

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

	@XmlElement (name="Zone")
	public String getZone() { return Zone; }
	public void setZone(String zone) { Zone = zone; }

	@XmlElement (name="ChngWho")
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho; }

	@XmlElement (name="ChngDate")
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date chngDate) { ChngDate = chngDate; };
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Dealer getDealer(String Manufacturer, String code) {
		DealerSearchCriteria dsc = new DealerSearchCriteria();
		dsc.setSearchType(eDealerObjAction.eDealerFindByMfgDealer);
		dsc.setManufacturer(Manufacturer);
		dsc.setDealer(code);
		return dDAO.get(dsc);
	}
	
	public List<Dealer> getManufacturerDealers(String Manufacturer){
		DealerSearchCriteria dsc = new DealerSearchCriteria();
		dsc.setSearchType(eDealerObjAction.eDealerFindByMfg);
		dsc.setManufacturer(Manufacturer);
		return dDAO.getList(dsc);
	}
	
	public List<Dealer> getAllDealers(){
		DealerSearchCriteria dsc = new DealerSearchCriteria();
		dsc.setSearchType(eDealerObjAction.eDealerFindAll);
		return dDAO.getList(dsc);
	}
	
	public Dealer getLastDealerUpdated() {
		return dDAO.getLastDealerUpdated();
	}
	
	public void Save() {
		dDAO.set(this);
	}
	
	public void Delete() {
		dDAO.delete(this);
	}
}
