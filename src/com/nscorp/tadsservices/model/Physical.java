package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.PhysicalDAO;

@XmlRootElement (name="Physical")
@Entity
public class Physical extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Code;
	private Date Start;
	private Date End;
	private String Lot;
	private String Zone;
	private String Row;
	private String Manufacturer;
	private boolean UseConvoySpots;
	private String ChngWho;
	private Date ChngDate;
	
	private PhysicalDAO phyDAO;
	// }}
	
	
	//Constructor
	public Physical() {}

	public Physical(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public void setLocationDatabase(Database database) {
		this.database = database;
		phyDAO = new PhysicalDAO(database);
	}
	
	@XmlElement (name="Code")
	public String getCode() { return Code; }
	public void setCode(String code) { Code = code; }

	@XmlElement (name="Start")
	public Date getStart() { return Start; }
	public void setStart(Date start) { Start = start; }

	@XmlElement (name="End")
	public Date getEnd() { return End; }
	public void setEnd(Date end) { End = end; }

	@XmlElement (name="Lot")
	public String getLot() { return Lot; }
	public void setLot(String lot) { Lot = lot; }

	@XmlElement (name="Zone")
	public String getZone() { return Zone; }
	public void setZone(String zone) { Zone = zone; }

	@XmlElement (name="Row")
	public String getRow() { return Row; }
	public void setRow(String row) { Row = row; }

	@XmlElement (name="Manufacturer")
	public String getManufacturer() { return Manufacturer; 	}
	public void setManufacturer(String manufacturer) { Manufacturer = manufacturer; }

	@XmlElement (name="UseConvoySpots")
	public boolean isUseConvoySpots() { return UseConvoySpots; }
	public void setUseConvoySpots(boolean useConvoySpots) { UseConvoySpots = useConvoySpots; }

	@XmlElement (name="ChngWho")
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho; }

	@XmlElement (name="ChngDate")
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date chngDate) { ChngDate = chngDate; }
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Physical GetPhysical(String code) {
		return phyDAO.get(code);
	}
	
	public List<Physical> GetAllPhysicals() throws SQLException{
		return phyDAO.GetAllPhysicals();
	}
	
	public List<Physical> GetAllActivePhysicals() throws SQLException{
		return phyDAO.GetAllActivePhysicals();
	}
	
	public void Save() {
		phyDAO.set(this);
	}
	
	public void Delete() {
		phyDAO.delete(this);
	}
	
	public void CleanLoadLineSpots() {
		phyDAO.CleanLoadLineSpots();
	}
	
	public void CleanConvoySpots() {
		phyDAO.CleanConvoySpots();
	}
}
