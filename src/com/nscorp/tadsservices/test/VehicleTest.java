package com.nscorp.tadsservices.test;

import static org.junit.Assert.assertNotNull;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Enums.eVINObjAction;
import com.nscorp.tadsservices.model.Vehicle;
import com.nscorp.tadsservices.model.SearchCriteria.VehicleSearchCriteria;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleTest {
	Database database = new Database("QA", "PooleCreek");

	@Test
	@Order(1)
	void testVehicle() {
		Vehicle vehicle = new Vehicle(database);
		assertNotNull(vehicle);
	}

	@Test
	@Order(2)
	void testGetVehicleString() {
		Vehicle vehicle = new Vehicle(database);
		vehicle = vehicle.getVehicle("NM0LS7E27K1395685");
		assertNotNull(vehicle);
	}

	@Test
	@Order(3)
	void testGetVehicleVehicleSearchCriteria() {
		Vehicle vehicle = new Vehicle(database);
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		vsc.setSearchType(eVINObjAction.eVINFindVIN);
		vsc.setVIN("NM0LS7E27K1395685");
		vehicle = vehicle.getVehicle(vsc);		
		assertNotNull("Not found vehicle 2C3CDZAG7KH538411",vehicle);
	}

	@Test
	@Order(4)
	void testGetVehicleList() {
		List<Vehicle> vehicles = null;
		Vehicle vehicle = new Vehicle(database);
		VehicleSearchCriteria vsc = new VehicleSearchCriteria();
		vsc.setSearchType(eVINObjAction.eVINFindVINLike);
		vsc.setVIN("______CB3________");
		vehicles = vehicle.getVehicleList(vsc);
		
		assertNotNull(vehicles);
	}

}
