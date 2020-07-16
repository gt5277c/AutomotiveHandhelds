package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.PhysicalDetailDAO;

@XmlRootElement (name="PhysicalDetail")
@Entity
public class PhysicalDetail extends TADSLocationObject {
	
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Code;
	private String VIN;
	private String Reconciled;
	private String CurrentLot;
	private String CurrentZone;
	private String CurrentRow;
	private String CurrentSpot;
	private String ScanLot;
	private String ScanZone;
	private String ScanRow;
	private String ScanSpot;
	private String Outbound;
	private String Message;
	private String ChngWho;
	private Date ChngDate;
	
	private PhysicalDetailDAO phydetDAO;
	// }}
	
	//Constructor
	public PhysicalDetail() {}

	public PhysicalDetail(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public void setLocationDatabase(Database database) {
		this.database = database;
		phydetDAO = new PhysicalDetailDAO(database);
	}
	
	@XmlElement (name="Code")
	public String getCode() { return Code; 	}
	public void setCode(String code) { Code = code; }

	@XmlElement (name="VIN")
	public String getVIN() { return VIN; }
	public void setVIN(String vIN) { VIN = vIN; }

	@XmlElement (name="Reconciled")
	public String getReconciled() { return Reconciled; }
	public void setReconciled(String reconciled) { Reconciled = reconciled; }

	@XmlElement (name="CurrentLot")
	public String getCurrentLot() { return CurrentLot; }
	public void setCurrentLot(String currentLot) { CurrentLot = currentLot; }

	@XmlElement (name="CurrentZone")
	public String getCurrentZone() { return CurrentZone; }
	public void setCurrentZone(String currentZone) { CurrentZone = currentZone; }

	@XmlElement (name="CurrentRow")
	public String getCurrentRow() { return CurrentRow; }
	public void setCurrentRow(String currentRow) { CurrentRow = currentRow; }

	@XmlElement (name="CurrentSpot")
	public String getCurrentSpot() { return CurrentSpot; }
	public void setCurrentSpot(String currentSpot) { CurrentSpot = currentSpot; }

	@XmlElement (name="ScanLot")
	public String getScanLot() { return ScanLot; }
	public void setScanLot(String scanLot) { ScanLot = scanLot; }

	@XmlElement (name="ScanZone")
	public String getScanZone() { return ScanZone; }
	public void setScanZone(String scanZone) { ScanZone = scanZone; }

	@XmlElement (name="ScanRow")
	public String getScanRow() { return ScanRow; }
	public void setScanRow(String scanRow) { ScanRow = scanRow; }

	@XmlElement (name="ScanSpot")
	public String getScanSpot() { return ScanSpot; }
	public void setScanSpot(String scanSpot) { ScanSpot = scanSpot; }

	@XmlElement (name="Outbound")
	public String getOutbound() { return Outbound; }
	public void setOutbound(String outbound) { Outbound = outbound; }

	@XmlElement (name="Message")
	public String getMessage() { return Message; }
	public void setMessage(String message) { Message = message; }

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
	public void Save() {
		phydetDAO.set(this);
	}
	
	public PhysicalDetail getPhysicalDetailVIN(String code, String vin) {
		return phydetDAO.getPhysicalDetailVIN(code, vin);
	}
	
	public List<PhysicalDetail> getPhysicalDetail(String code) throws SQLException{
		return phydetDAO.getAll(code);
	}
	
	public List<PhysicalDetail> getDataNonScanned(String code) throws SQLException{
		return phydetDAO.getDataNonScanned(code);
	}
	
	public List<PhysicalDetail> getDataDiscrepancies(String code) throws SQLException{
		return phydetDAO.getDataDiscrepancies(code);
	}
	
	public List<PhysicalDetail> getPhysicalOutboundDate(String vin) throws SQLException{
		return phydetDAO.getPhysicalOutboundDate(vin);
	}
	
	public List<PhysicalDetail> getDataAddedVins(String code) throws SQLException{
		return phydetDAO.getDataAddedVins(code);
	}
	
	public void UpdatePhysicalFixes() {
		phydetDAO.UpdatePhysicalFixes(this);
	}
}
