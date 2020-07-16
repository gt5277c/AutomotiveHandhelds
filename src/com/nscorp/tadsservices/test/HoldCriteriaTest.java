package com.nscorp.tadsservices.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.Enums.eHoldCriteriaObjAction;
import com.nscorp.tadsservices.model.HoldCriteria;
import com.nscorp.tadsservices.model.Ramp;
import com.nscorp.tadsservices.model.SearchCriteria.HoldCriteriaSearchCriteria;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HoldCriteriaTest {
	static Ramp ramp;
	static Database database;
	
	@BeforeAll
	static void initAll() throws SQLException {
		ramp = new Ramp("QA", "PooleCreek");
		database = ramp.getDatabase();
	}
	
	@Test
	@Order(1)
	void test() {
		HoldCriteria holdcriteria = new HoldCriteria(database);
		assertNotNull(holdcriteria);
	}
	
	@Test
	@Order(2)
	void testSavehold() {
		HoldCriteria holdcriteria = new HoldCriteria(database);
		holdcriteria.setManufacturer("F");
		holdcriteria.setHoldCode("NA");
		holdcriteria.setVIN("NM0LS7E29K1395686");
		holdcriteria.Save();
	}
	
	@Test
	@Order(3)
	void testDeleteHold() throws SQLException {
		HoldCriteria holdcriteria = new HoldCriteria(database);
		HoldCriteriaSearchCriteria hcsc = new HoldCriteriaSearchCriteria();
		hcsc.setSearchType(eHoldCriteriaObjAction.eHoldCriteriaFindVIN);
		hcsc.setVIN("NM0LS7E29K1395686");
		List<HoldCriteria> holdcrits = holdcriteria.GetHoldCriteria(hcsc);
		if(holdcrits != null) {
			for(HoldCriteria holdcrit : holdcrits) {
				holdcrit.Delete();				
			}
		}
	}

}
