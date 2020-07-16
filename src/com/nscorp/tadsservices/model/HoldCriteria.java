package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.HoldCriteriaDAO;
import com.nscorp.tadsservices.model.SearchCriteria.HoldCriteriaSearchCriteria;

public class HoldCriteria extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private Integer Key;
	private String Manufacturer;
	private String HoldCode;
	private String VIN;
	private String Route;
	private String Dealer;
	private String Railcar;
	private String Emission;
	private String VehicleType;
	private String ColorCode;
	private String Status;
	private String ProdStatus;
	private String SoldCode;
	private String Upfitter;
	private String ArriveFromDate;
	private String ArriveToDate;
	private String ChangWho;
	private Date ChangeDate;
	
	private HoldCriteriaDAO hcDAO;
	// }}
		
	//Constructor
	public HoldCriteria() {};
	
	public HoldCriteria(Database database) {
		setLocationDatabase(database);
	};
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		hcDAO = new HoldCriteriaDAO(database);
	}
	
	public Integer getKey() { return Key; }
	public void setKey(Integer Key) { this.Key = Key; }
	
	public String getManufacturer() { return Manufacturer; }
	public void setManufacturer(String Manufacturer) { this.Manufacturer = Manufacturer; }

	public String getHoldCode() { return HoldCode; }
	public void setHoldCode(String HoldCode) { this.HoldCode = HoldCode; }

	public String getVIN() { return VIN; }
	public void setVIN(String VIN) { this.VIN = VIN; }

	public String getRoute() { return Route; }
	public void setRoute(String Route) { this.Route = Route; }

	public String getDealer() { return Dealer; }
	public void setDealer(String Dealer) { this.Dealer = Dealer; }

	public String getRailcar() { return Railcar; }
	public void setRailcar(String Railcar) { this.Railcar = Railcar; }

	public String getEmission() { return Emission; }
	public void setEmission(String Emission) { this.Emission = Emission; }

	public String getVehicleType() { return VehicleType; }
	public void setVehicleType(String VehicleType) { this.VehicleType = VehicleType; }

	public String getColorCode() { return ColorCode; }
	public void setColorCode(String ColorCode) { this.ColorCode = ColorCode; }

	public String getStatus() { return Status; }
	public void setStatus(String Status) { this.Status = Status; }

	public String getProdStatus() { return ProdStatus; }
	public void setProdStatus(String ProdStatus) { this.ProdStatus = ProdStatus; }

	public String getSoldCode() { return SoldCode; }
	public void setSoldCode(String SoldCode) { this.SoldCode = SoldCode; }

	public String getUpfitter() { return Upfitter; }
	public void setUpfitter(String Upfitter) { this.Upfitter = Upfitter; }
	
	public String getArriveFromDate() { return ArriveFromDate; }
	public void setArriveFromDate(String ArriveFromDate) { this.ArriveFromDate = ArriveFromDate; }
	
	public String getArriveToDate() { return ArriveToDate; }
	public void setArriveToDate(String ArriveToDate) { this.ArriveToDate = ArriveToDate; }
	
	public String getChangWho() { return ChangWho; }
	public void setChangWho(String ChangWho) { this.ChangWho = ChangWho; }
	
	public Date getChangeDate() { return ChangeDate; }
	public void setChangeDate(Date ChangeDate) { this.ChangeDate = ChangeDate; }
	
	// }}

	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public List<HoldCriteria> GetHoldCriteria(HoldCriteriaSearchCriteria hcsc) throws SQLException{
		List<HoldCriteria> holdcriterias = null;
		
		holdcriterias = hcDAO.getList(hcsc);
		
		return holdcriterias;
	}
	
	public int Save() {
		return hcDAO.set(this);
	}
	
	public int Delete() {
		return hcDAO.delete(this);
	}
	
	public boolean ValidateHoldCriteria() {
		boolean result = false;
		
		//Check to see if any hold criteria information is available
		if(Manufacturer.length() < 1 &&
		   HoldCode.length() < 1 &&
		   VIN.length() < 1 &&
		   Route.length() < 1 &&
		   Dealer.length() < 1 &&
		   Railcar.length() < 1 &&
		   Emission.length() < 1 &&
		   VehicleType.length() < 1 &&
		   ColorCode.length() < 1 &&
		   Status.length() < 1 &&
		   ProdStatus.length() < 1 &&
		   SoldCode.length() < 1 &&
		   Upfitter.length() < 1 &&
		   ArriveFromDate.length() < 1 &&
		   ArriveToDate.length() < 1 &&
		   ChangWho.length() < 1 &&
		   ChangWho.length() < 1 ) 
		{
			//Error No Hold Criteria information
		} else if(VIN.length() > 0 && VIN.length() < 17) {
			//Error invalid VIN length
		} else if(HoldCode.length() < 1) {
			//Error missing hold code
		} else if(HoldCode.length() < 1) {
			//Error missing hold code
		} else if(HoldCode.length() < 1) {
			//Error missing hold code
		} else if(HoldCode.length() < 1) {
			//Error missing hold code
		} else if(HoldCode.length() < 1) {
			//	Error missing hold code
		} else {
			result = true;
		}
		
		return result;
	}
}
