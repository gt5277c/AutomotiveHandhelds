package com.nscorp.tadsservices.model;

import java.sql.SQLException;
import com.nscorp.tadsservices.server.BayLogic;
import com.nscorp.tadsservices.server.RailcarLogic;

/**
 * Will store the ramp specific information.
 * Database, Routes, Dealers
 *
 */
public class Ramp{

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
		// Ramp classes
		private Database database;
		private SystemSettings systemsettings;
		private BayLogic baylogic;
		private Manufacturer manufacturer;
		private Option option;
		private Route route;
		private Dealer  dealer;
		private Upfitter upfitter;
		private Location  location;
	
		// Ramp related information
		/*
		private List<TruckCompany> truckcompanies = null;
		private List<VehicleType> vehicletypes = null;
		private List<SpecRoute> specialroutes = null;
		private List<SpecDealer> specialdealers = null;
		private List<Route> routes = null;
		private List<Dealer> dealers = null;
		private List<Route> ramproutes = null;
		private List<ProdStat> prodstats = null;
		private List<Upfitter> upfitters = null;
		*/
	// }}
		
	//Constructor
	public Ramp(String environment, String location) throws SQLException {
		Database database = new Database(environment, location);
		setLocationDatabase(database);
		InitializeRamp(database);
	}
	
	public Ramp(Database database) throws SQLException {
		setLocationDatabase(database);
		InitializeRamp(database);
	}

	////////////////////////////////////////////////////////////
	//Initializers and Setup
	////////////////////////////////////////////////////////////
	// {{
	public void setLocationDatabase(Database database) {
		this.database = database;
	}
	
	public void InitializeRamp(Database database) throws SQLException {
		//Read the Options from the database
		systemsettings = new SystemSettings(database);

		//Get the Site related information
		//	Collections and setting that will be shared with Baying Logic
		//InitializeCollections(database);
		
		//Initialize the Baying Logic
		baylogic = new BayLogic(this);
		
		new RailcarLogic(this);
		
		//Initialize the ramp variables
		manufacturer = new Manufacturer(database);
		option = new Option(database);
		route = new Route(database);
		dealer = new Dealer(database);
		upfitter = new Upfitter(database);
		location = new Location(database);
	}
	
	/*
	private void InitializeCollections(Database database) throws SQLException {
		//List of Truck Companies
		TruckCompany truckcompany = new TruckCompany(database);
		setTruckCompanies(truckcompany.getTruckCompanies());
		
		//List of Vehicle Types
		VehicleType v = new VehicleType(database);
		setVehicletypes(v.getVehicleTypeList());
		
		//List of Special Routes
		SpecRoute sr = new SpecRoute(database);
		setSpecialroutes(sr.getList());
		
		//List of Special Dealers
		SpecDealer sd = new SpecDealer(database);
		setSpecialdealers(sd.getList());
		
		//List of Routes
		Route r = new Route(database);
		setRoutes(r.getAllRoutes());
		
		//List of Dealers
		Dealer d = new Dealer(database);
		setDealers(d.getAllDealers());
		
		//List of ProdStat
		ProdStat pStat = new ProdStat(database);
		setProdstats(pStat.getList());
		
		//List of Upfitters
		Upfitter up = new Upfitter(database);
		UpfitterSearchCriteria usc = new UpfitterSearchCriteria();
		usc.setSearchType(eUpfitter.eFindUpfitterAll);
		setUpfitters(up.getList(usc));
	}
	*/
	
	//Read Only
	public Database getDatabase() {
		return database;
	}
	
	//Read Only
	public SystemSettings getSystemSettings() {
		return systemsettings;
	}
	
	//Read Only
	public BayLogic getBayLogic() {
		return baylogic;
	}
	// }}

	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	/*
	public List<TruckCompany> getTruckCompanies(){
		return truckcompanies;
	}
	
	public void setTruckCompanies(List<TruckCompany> truckcompanies) {
		this.truckcompanies = truckcompanies;
	}
	
	public List<VehicleType> getVehicletypes() {
		return vehicletypes;
	}

	public void setVehicletypes(List<VehicleType> vehicletypes) {
		this.vehicletypes = vehicletypes;
	}

	public List<SpecRoute> getSpecialroutes() {
		return specialroutes;
	}

	public void setSpecialroutes(List<SpecRoute> specialroutes) {
		this.specialroutes = specialroutes;
	}

	public List<SpecDealer> getSpecialdealers() {
		return specialdealers;
	}

	public void setSpecialdealers(List<SpecDealer> specialdealers) {
		this.specialdealers = specialdealers;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public List<Dealer> getDealers() {
		return dealers;
	}

	public void setDealers(List<Dealer> dealers) {
		this.dealers = dealers;
	}

	public List<Route> getRamproutes() {
		return ramproutes;
	}

	public void setRamproutes(List<Route> ramproutes) {
		this.ramproutes = ramproutes;
	}

	public List<ProdStat> getProdstats() {
		return prodstats;
	}

	public void setProdstats(List<ProdStat> prodstats) {
		this.prodstats = prodstats;
	}

	public List<Upfitter> getUpfitters() {
		return upfitters;
	}

	public void setUpfitters(List<Upfitter> upfitters) {
		this.upfitters = upfitters;
	}

	*/
	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Upfitter getUpfitter() {
		return upfitter;
	}

	public void setUpfitter(Upfitter upfitter) {
		this.upfitter = upfitter;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	// }}

}
