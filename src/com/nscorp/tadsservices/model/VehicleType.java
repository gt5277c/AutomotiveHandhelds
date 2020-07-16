package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.VehicleTypeDAO;

public class VehicleType extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String Code;
	private String Desc;
	private double MaxLength;
	private double MaxHeight;
	private String DeckType;
	private String TieDown;
	private double FillFactor;
	private String RailLotCode;
	private String RailZoneID;
	private String RailArea;
	private String TruckLotCode;
	private String TruckZone;
	private String TruckArea;
	private double VehicleWeight;
	private String RailcarType;
	private String ConvoyEndRow;
	private String ChngWho;
	private Date ChngDate;
	private VTypeAssocs vtypeassoc;
	private VehicleTypeDAO vtDAO;
	private Database database;
	// }}
	
	//Constructor
	public VehicleType() {}
	
	public VehicleType(Database database) {
		setLocationDatabase(database);
	};
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		vtDAO = new VehicleTypeDAO(database);
	}
	
	public String getCode() { return Code; }
	public void setCode(String code) { Code = code; }

	public String getDesc() { return Desc; }
	public void setDesc(String desc) { Desc = desc; }

	public double getMaxLength() { return MaxLength; }
	public void setMaxLength(double maxLength) { MaxLength = maxLength; }

	public double getMaxHeight() { return MaxHeight; }
	public void setMaxHeight(double maxHeight) { MaxHeight = maxHeight; }

	public String getDeckType() { return DeckType; }
	public void setDeckType(String deckType) { DeckType = deckType; }

	public String getTieDown() { return TieDown; }
	public void setTieDown(String tieDown) { TieDown = tieDown; }

	public double getFillFactor() { return FillFactor; }
	public void setFillFactor(double fillFactor) { FillFactor = fillFactor; }

	public String getRailLotCode() { return RailLotCode; }
	public void setRailLotCode(String railLotCode) { RailLotCode = railLotCode; }

	public String getRailZoneID() { return RailZoneID; }
	public void setRailZoneID(String railZoneID) { RailZoneID = railZoneID; }

	public String getRailArea() { return RailArea; }
	public void setRailArea(String railArea) { RailArea = railArea; }

	public String getTruckLotCode() { return TruckLotCode; }
	public void setTruckLotCode(String truckLotCode) { TruckLotCode = truckLotCode; }

	public String getTruckZone() { return TruckZone; }
	public void setTruckZone(String truckZone) { TruckZone = truckZone; }

	public String getTruckArea() { return TruckArea; }
	public void setTruckArea(String truckArea) { TruckArea = truckArea;	}

	public double getVehicleWeight() { return VehicleWeight; }
	public void setVehicleWeight(double vehicleWeight) { VehicleWeight = vehicleWeight; }

	public String getRailcarType() { return RailcarType; }
	public void setRailcarType(String railcarType) { RailcarType = railcarType; }

	public String getConvoyEndRow() { return ConvoyEndRow; }
	public void setConvoyEndRow(String convoyEndRow) { ConvoyEndRow = convoyEndRow; }

	public VTypeAssocs getVtypeassoc() { return vtypeassoc; }
	public void setVtypeassoc(VTypeAssocs vtypeassoc) { this.vtypeassoc = vtypeassoc; }

	public String getChngWho() { return ChngWho; }
	public void setChngWho(String chngWho) { ChngWho = chngWho; }

	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date chngDate) { ChngDate = chngDate; }
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////	
	public VehicleType getVehicleType(String code) {
		return vtDAO.get(code);
	}
	
	public List<VehicleType> getVehicleTypeList(){
		return vtDAO.getList();
	}
	
	public VehicleType getVehicleTypeFromVIN(String vin) {
		return vtDAO.getFromVIN(vin);
	}
	
	public List<VTypeAssocs> getVTypeAssocs(String vtCode) throws SQLException{
		VTypeAssocs vta = new VTypeAssocs(database);
		
		return vta.getVTypeAssocsList(vtCode);
	}
	
	public void Save() {
		//Check for valid vehicle and railcar type
		if(Code.length() < 1 ) {
			//Throw INVALID_VEHICLE_TYPE
		} else if(RailcarType.length() < 1 ) {
			//Throw MISSING_RAILCAR_TYPE
		}
		
		vtDAO.set(this);		
	}
	
	public void Delete() {
		//Check for valid vehicle type
		if(Code.length() < 1 ) {
			//Throw INVALID_VEHICLE_TYPE
		}
		
		vtDAO.delete(this);
	}
	
	
}
