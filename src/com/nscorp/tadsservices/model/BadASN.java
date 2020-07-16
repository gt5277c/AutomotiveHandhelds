package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.nscorp.tadsservices.model.DAO.BadASNDAO;

public class BadASN extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String VIN;
	private String Route;
	private String Dealer;
	private String Manufacturer;
	private String RailCar;
	private String Emission;
	private String ColorCode;
	private String ColorCommon;
	private String ColorMfrName;
	private String StatusCode;
	private String ActionCode;
	private String HoldReasonCode;
	private String ProdStatus;
	private String SoldCode;
	private String Notes;
	private String Diversion;
	private String LTD;
	private String InboundSCAC;
	private String InboundWayBill;
	private String InboundWayBillSN;
	private String DepartureDate;
	private String Origin;
	private String InboundUpfitter;
	private String OutboundUpfitter;
	private String LineSeries;
	private String Wheelbase;
	private String ChngWho;
	private Date ChngDate;
	
	private BadASNDAO badDAO;
	// }}
	
	//Constructor
	public BadASN() {}
	
	public BadASN(Database database) {
		setLocationDatabase(database);
	}

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		badDAO = new BadASNDAO(database);
	}
	
	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getRoute() {
		return Route;
	}

	public void setRoute(String route) {
		Route = route;
	}

	public String getDealer() {
		return Dealer;
	}

	public void setDealer(String dealer) {
		Dealer = dealer;
	}

	public String getManufacturer() {
		return Manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}

	public String getRailCar() {
		return RailCar;
	}

	public void setRailCar(String railCar) {
		RailCar = railCar;
	}

	public String getEmission() {
		return Emission;
	}

	public void setEmission(String emission) {
		Emission = emission;
	}

	public String getColorCode() {
		return ColorCode;
	}

	public void setColorCode(String colorCode) {
		ColorCode = colorCode;
	}

	public String getColorCommon() {
		return ColorCommon;
	}

	public void setColorCommon(String colorCommon) {
		ColorCommon = colorCommon;
	}

	public String getColorMfrName() {
		return ColorMfrName;
	}

	public void setColorMfrName(String colorMfrName) {
		ColorMfrName = colorMfrName;
	}

	public String getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}

	public String getActionCode() {
		return ActionCode;
	}

	public void setActionCode(String actionCode) {
		ActionCode = actionCode;
	}

	public String getHoldReasonCode() {
		return HoldReasonCode;
	}

	public void setHoldReasonCode(String holdReasonCode) {
		HoldReasonCode = holdReasonCode;
	}

	public String getProdStatus() {
		return ProdStatus;
	}

	public void setProdStatus(String prodStatus) {
		ProdStatus = prodStatus;
	}

	public String getSoldCode() {
		return SoldCode;
	}

	public void setSoldCode(String soldCode) {
		SoldCode = soldCode;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public String getDiversion() {
		return Diversion;
	}

	public void setDiversion(String diversion) {
		Diversion = diversion;
	}

	public String getLTD() {
		return LTD;
	}

	public void setLTD(String lTD) {
		LTD = lTD;
	}

	public String getInboundSCAC() {
		return InboundSCAC;
	}

	public void setInboundSCAC(String inboundSCAC) {
		InboundSCAC = inboundSCAC;
	}

	public String getInboundWayBill() {
		return InboundWayBill;
	}

	public void setInboundWayBill(String inboundWayBill) {
		InboundWayBill = inboundWayBill;
	}

	public String getInboundWayBillSN() {
		return InboundWayBillSN;
	}

	public void setInboundWayBillSN(String inboundWayBillSN) {
		InboundWayBillSN = inboundWayBillSN;
	}

	public String getDepartureDate() {
		return DepartureDate;
	}

	public void setDepartureDate(String departureDate) {
		DepartureDate = departureDate;
	}

	public String getOrigin() {
		return Origin;
	}

	public void setOrigin(String origin) {
		Origin = origin;
	}

	public String getInboundUpfitter() {
		return InboundUpfitter;
	}

	public void setInboundUpfitter(String inboundUpfitter) {
		InboundUpfitter = inboundUpfitter;
	}

	public String getLineSeries() {
		return LineSeries;
	}

	public void setLineSeries(String lineSeries) {
		LineSeries = lineSeries;
	}

	public String getOutboundUpfitter() {
		return OutboundUpfitter;
	}

	public void setOutboundUpfitter(String outboundUpfitter) {
		OutboundUpfitter = outboundUpfitter;
	}

	public String getWheelbase() {
		return Wheelbase;
	}

	public void setWheelbase(String wheelbase) {
		Wheelbase = wheelbase;
	}

	public String getChngWho() {
		return ChngWho;
	}

	public void setChngWho(String chngWho) {
		ChngWho = chngWho;
	}

	public Date getChngDate() {
		return ChngDate;
	}

	public void setChngDate(Date chngDate) {
		ChngDate = chngDate;
	}
		
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public BadASN getBadASN(String vin) {
		return badDAO.get(vin);
	}
	
	public List<BadASN> getAllBadASN() throws SQLException{
		return badDAO.getAllBadASN();
	}
	
	public void Save() {
		badDAO.set(this);
	}

	public void Delete() {
		badDAO.delete(this);
	}
}
