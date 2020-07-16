package com.nscorp.tadsservices.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.nscorp.tadsservices.model.Database;
import com.nscorp.tadsservices.model.OutboundJournal;

class OutboundJournalTest {
	Database database = new Database("QA", "Chicago");
	
	@Test
	void test() {
		OutboundJournal outboundjournal = new OutboundJournal();
		assertNotNull(outboundjournal);
	}
	
	@Test
	void testCreateOutboundJournal() {
		OutboundJournal outboundjournal = new OutboundJournal(database);
		assertNotNull(outboundjournal);
	}
	
	@Test
	void testGetRecord() {
		OutboundJournal outboundjournal = new OutboundJournal(database);
		assertNotNull(outboundjournal);
		
		int recnum = 20820233;		
		outboundjournal = outboundjournal.get(recnum);
		assertNotNull(outboundjournal);
		
		outboundjournal.save();
	}

	@Test
	void testSave() {
		OutboundJournal outboundjournal = new OutboundJournal(database);
		assertNotNull(outboundjournal);
		
		outboundjournal.setType("TRUCKIN");
		outboundjournal.setData("dataaaaaa");
		outboundjournal.setStatus("S");
		outboundjournal.setErrormessage("");
		outboundjournal.setChngWho("pjpjg");
		outboundjournal.setChngDate(new Date());
		outboundjournal.save();
	}

}
