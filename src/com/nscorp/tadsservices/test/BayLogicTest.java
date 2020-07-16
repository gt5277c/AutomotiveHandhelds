package com.nscorp.tadsservices.test;

import static org.junit.Assert.assertNotNull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.nscorp.tadsservices.model.Common;
import com.nscorp.tadsservices.model.Constants;
import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Enums.eBayArrival;
import com.nscorp.tadsservices.server.BayLogic;
import com.nscorp.tadsservices.model.Ramp;
import com.nscorp.tadsservices.model.Vehicle;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BayLogicTest {
	static Ramp poolecreek;
	static Ramp chicago;
	static Database poolecreekDB;
	static Database chicagoDB;
	static BayLogic poolecreekbaylogic;
	static BayLogic chicagobaylogic;
	/** Setup before any tests are run
	 * @throws SQLException 
    @BeforeAll
    static void initAll() {}

	/** Setup before EVERY test
    @BeforeEach
    void init() {}

	/** Clean up after EVERY test
	@AfterEach
    void tearDown() {}
	
	/** Clean up after all tests are run
	@AfterAll
    static void tearDownAll() {}
	*/
	@BeforeAll
	static void initAll() throws SQLException {
		poolecreek = new Ramp("QA", "PooleCreek");
		poolecreekDB = poolecreek.getDatabase();
		poolecreekbaylogic = poolecreek.getBayLogic();
		
		chicago = new Ramp("QA", "Chicago");
		chicagoDB = chicago.getDatabase();
		chicagobaylogic = chicago.getBayLogic();
	}
	
	@Test
	@Order(1)
	void testBayLogic() {
		assertNotNull(poolecreekbaylogic);
		assertNotNull(chicagobaylogic);
	}
	
	// {{
	@Test
	@Order(2)
	void testPreBayVehicleVehicleEBayArrival() {
		Vehicle vehicle = poolecreekDB.getClass(Vehicle.class);
		
		List<String> vins = new ArrayList<String>();
		vins.add("1C4RJECG8KC597362");
		vins.add("1C4RJEJG0HC628793");
		vins.add("1C4RJEJG4JC141425");
		vins.add("1C4RJEJG9HC614729");
		vins.add("1FM5K8GT0KGA36243");
		vins.add("1FM5K8GT1KGA18494");
		vins.add("1FM5K8GT3JGC38850");
		vins.add("2C3CDXCT7KH526905");
		vins.add("2LMPJ6K99KBL15066");
		vins.add("NM0LS7E29K1394215");
		
		for(String vin : vins) {
			vehicle = vehicle.getVehicle(vin);
			
			if( vehicle != null) {
				//Set the Trucker information
				vehicle.setInboundTruckID("6");
				vehicle.setInboundSCAC("COOJ");
				vehicle.setStatusCode(Constants.STAT_TRUCK_IN);
				vehicle.setChngDate(Common.getCurrentDateTime());
				vehicle.setChngWho("pjgpj");
				try {
					vehicle = poolecreekbaylogic.PreBayVehicle(vehicle, eBayArrival.eBay_TruckIn);
				} catch (Exception e) {
					System.out.println("Bay Error:" + vehicle.getVIN());
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}				
			} else {
				//Vehicle ASN not found
				System.out.println("Vehicle " + vin + " not found");
			}
			//Vehicle should now be parked or have an error in baying.
		}
	}
	// }}
	
	//{{
	@Test
	@Order(3)
	void testTruckInForPooleCreekAndChicago() {
		Vehicle vehicle = poolecreekDB.getClass(Vehicle.class);
		
		List<String> vins = new ArrayList<String>();
		vins.add("1C4RJECG8KC597362");
		vins.add("1C4RJEJG0HC628793");
		vins.add("1C4RJEJG4JC141425");
		vins.add("1C4RJEJG9HC614729");
		vins.add("1FM5K8GT0KGA36243");
		vins.add("1FM5K8GT1KGA18494");
		vins.add("1FM5K8GT3JGC38850");
		vins.add("2C3CDXCT7KH526905");
		vins.add("2LMPJ6K99KBL15066");
		vins.add("NM0LS7E29K1394215");
		
		for(String vin : vins) {
			vehicle = vehicle.getVehicle(vin);
			
			if(vehicle != null) {
				//Set the Trucker information
				vehicle.setInboundTruckID("6");
				vehicle.setInboundSCAC("COOJ");
				vehicle.setStatusCode(Constants.STAT_TRUCK_IN);
				vehicle.setChngDate(new Date());
				vehicle.setChngWho("pjgpj");
				try {
					vehicle = poolecreekbaylogic.PreBayVehicle(vehicle, eBayArrival.eBay_TruckIn);
				} catch (Exception e) {
					System.out.println("Bay Error:" + vehicle.getVIN());
					System.out.println(e.getMessage());
				}				
			} else {
				//Vehicle ASN not found
				System.out.println("Vehicle " + vin + " not found");				
			}
			//Vehicle should now be parked or have an error in baying.
		}		
		
		//Now Chicago
		Vehicle chicagovehicle = new Vehicle(chicagoDB);
		List<String> chivins = new ArrayList<String>();
		chivins.add("1C4RDJAG9KC807308");
		chivins.add("1C4RDJDG1KC799183");
		chivins.add("1C4RDJDG1KC807055");
		chivins.add("1C4RDJDG2KC780741");
		chivins.add("1C4RDJDG2KC799015");
		chivins.add("1C4RDJDG3KC807056");
		chivins.add("1C4RDJDG4KC799016");
		chivins.add("1C4RDJDG7KC803558");
		chivins.add("1C4RDJEGXKC812379");
		chivins.add("1C4RJFAG0KC810538");
		
		for(String vin : chivins) {
			chicagovehicle = chicagovehicle.getVehicle(vin);
			
			if(vehicle != null) {			
				//Set the Trucker information
				chicagovehicle.setInboundTruckID("6");
				chicagovehicle.setInboundSCAC("COOJ");
				chicagovehicle.setStatusCode(Constants.STAT_TRUCK_IN);
				chicagovehicle.setChngDate(new Date());
				chicagovehicle.setChngWho("pjgpj");
				try {
					chicagovehicle = chicagobaylogic.PreBayVehicle(chicagovehicle, eBayArrival.eBay_TruckIn);
				} catch (Exception e) {
					System.out.println("Bay Error:" + chicagovehicle.getVIN());
					System.out.println(e.getMessage());
				}
			} else {
				//Vehicle ASN not found
				System.out.println("Vehicle " + vin + " not found");	
			}
		}
	}
	//}}

	@Test
	void testAddVIN() {	
		//This test assumes that an ASN is not available so this function is called
		//Add the Dealer and Route as needed
		//Then call prebay as usual
		
		//Will first try by finding a vehicle and removing the dealer and route
		//Then add a dealer and route
		//and truck in the vehicle
		Vehicle vehicle = new Vehicle(poolecreekDB);
		vehicle = vehicle.getVehicle("5NMZTDLB6JH091389");
		//has route 02 and dealer F21004
		//change to route 32 and dealer F23196
		//vehicle.setRoute("57");
		//vehicle.setDealer("45575");
		
		try {
			vehicle = poolecreekbaylogic.PreBayVehicle(vehicle, eBayArrival.eBay_TruckIn);
		} catch (Exception e) {
			System.out.println("Bay Error for " + vehicle.getVIN() + ":" + e.getMessage());
		}
		
		//Next will take a vehicle with no information or very little
		//Add a dealer and route
		//and truck in the vehicle
	}

}
