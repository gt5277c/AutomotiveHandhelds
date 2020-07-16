package com.nscorp.tadsservices.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nscorp.tadsservices.model.DAO.HoldDAO;
import com.nscorp.tadsservices.model.SearchCriteria.HoldSearchCriteria;

@XmlRootElement (name="Hold")
@Entity
public class Hold extends TADSLocationObject {
	
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Manufacturer;
	private String HoldCode;
	private String Description;
	private String HoldType;
	private String HoldClass;
	private String MfgHoldcode;
	private String MfgReleaseCode;
	private String MfgAuthorizationCode;
	private boolean RemoveOnTruckOut = false;
	private String BayLot;
	private String BayZone;
	private String BayRow;
	private boolean UserUpdateable = true;
	private String ChangeWho;
	private Date ChangeDate;
	
	private HoldDAO hDAO;
	// }}
	
	//Constructor
	public Hold() {
		
	}
	
	public Hold(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		hDAO = new HoldDAO(database);
	}
	
	@XmlElement (name="Manufacturer")
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String manufacturer) { Manufacturer = manufacturer; }

	@XmlElement (name="HoldCode")
	public String getHoldCode() { return HoldCode; }
	public void setHoldCode(String holdCode) { HoldCode = holdCode; }

	@XmlElement (name="Description")
	public String getDescription() { return Description; }
	public void setDescription(String description) { Description = description; }

	@XmlElement (name="HoldType")
	public String getHoldType() { return HoldType; }
	public void setHoldType(String holdType) { HoldType = holdType; }

	@XmlElement (name="HoldClass")
	public String getHoldClass() { return HoldClass; }
	public void setHoldClass(String holdClass) { HoldClass = holdClass; }

	@XmlElement (name="MfgHoldcode")
	public String getMfgHoldCode() { return MfgHoldcode; }
	public void setMfgHoldCode(String mfgHoldCode) { MfgHoldcode = mfgHoldCode; }

	@XmlElement (name="MfgReleaseCode")
	public String getMfgReleaseCode() { return MfgReleaseCode; }
	public void setMfgReleaseCode(String mfgReleaseCode) { MfgReleaseCode = mfgReleaseCode; }
	
	@XmlElement (name="MfgAuthorizationCode")
	public String getMfgAuthorizationCode() { return MfgAuthorizationCode; }
	public void setMfgAuthorizationCode(String mfgAuthorizationCode) { MfgAuthorizationCode = mfgAuthorizationCode; }
	
	@XmlElement (name="RemoveOnTruckOut")
	public boolean isRemoveOnTruckOut() { return RemoveOnTruckOut; }
	public void setRemoveOnTruckOut(boolean removeOnTruckOut) { RemoveOnTruckOut = removeOnTruckOut; }
	
	@XmlElement (name="BayLot")
	public String getBayLot() { return BayLot; }
	public void setBayLot(String bayLot) { BayLot = bayLot; }
	
	@XmlElement (name="BayZone")
	public String getBayZone() { return BayZone; }
	public void setBayZone(String bayZone) { BayZone = bayZone; }
	
	@XmlElement (name="BayRow")
	public String getBayRow() { return BayRow; }
	public void setBayRow(String bayRow) { BayRow = bayRow; }
	
	@XmlElement (name="UserUpdateable")
	public boolean isUserUpdateable() { return UserUpdateable; }
	public void setUserUpdateable(boolean userUpdateable) { UserUpdateable = userUpdateable; }

	@XmlElement (name="ChangeWho")
	public String getChangeWho() { return ChangeWho; }
	public void setChangeWho(String changeWho) { ChangeWho = changeWho; }
	
	@XmlElement (name="ChangeDate")
	public Date getChangeDate() { return ChangeDate; }
	public void setChangeDate(Date changeDate) { ChangeDate = changeDate; }
	// }}

	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public Hold GetHold(HoldSearchCriteria hsc) {
		return hDAO.get(hsc);
	}
	
	public List<Hold> GetHolds(HoldSearchCriteria hsc){
		return hDAO.getList(hsc);
	}
	
	public int Delete() {
		return hDAO.delete(this);		
	}
	
	public int Save() {
		return hDAO.set(this);
	}
	
	//Check to see that required fields are present and contain valid values
	public boolean ValidHold() {
		boolean result = false;
		
		if(this.Manufacturer.length() <= 0) {
			//Manufacturer missing
		} else if(this.HoldCode.length() <= 0) {
			//Hold code missing
		} else if(this.Description.length() <= 0) {
			//Description missing
		} else if(this.HoldType.length() <= 0) {
			//Hold Type missing
		} else if( !this.HoldType.equals("P") && !this.HoldType.equals("G")) {
			//Hold type invalid
		} else if(this.HoldClass.length() <= 0) {
			//Hold class missing
		} else if( !this.HoldClass.equals("B") && !this.HoldClass.equals("M") && !this.HoldClass.equals("O")){
			//Hold class invalid
		} else if(this.ChangeWho.length() <= 0) {
			//Hold change user ID is missing
		} else {
			result = true;
		}
		
		return result;
	}
	

}
