package com.nscorp.tadsservices.test;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.User;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTest {

	@Test
	@Order(1)
	void testUser() {
		Database database = new Database("QA", "PooleCreek");
		User user = new User(database);
		
		assertNotNull("Unable to create user",user);
	}

	@Test
	@Order(2)
	void testGet() {
		Database database = new Database("QA", "PooleCreek");
		User user = new User(database);
		
		assertNotNull("Unable to create user",user);
		user = user.get("pjgpj");
	
		if(user != null) System.out.println("Found user pjgpj ");
	}

	@Test
	@Order(3)
	void testSave() {
		Database database = new Database("QA", "PooleCreek");
		User user = new User(database);
		assertNotNull("Unable to create user",user);
		
		user.setUserID("blargh");
		user.setAdministrator(false);
		user.Save();
		
		User blarghuser = user.get("blargh");
		assertNotNull("Unable to find user",blarghuser);
	}

	@Test
	@Order(4)
	void testDelete() {
		Database database = new Database("QA", "PooleCreek");
		User user = new User(database);
		assertNotNull("Unable to create user",user);
		
		user = user.get("blargh");
		user.Delete();
		
	}

}
