package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nscorp.tadsservices.model.Common;

@XmlRootElement(name="HEADER")
@XmlType(propOrder= {"originSystem","destinationSystem","confirmation","racf","program","transactionTime"})
public class Header {
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private SystemID originSystem;
	private SystemID destinationSystem;
	private Confirmation confirmation;
	private String racf;
	private String program = "TADS";
	private String transactionTime;
	// }}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	public Header() {
		this.setTransactionTime(Common.getCurrentDateTimeSecondsAsString());
	}
		
	public String getRacf() {
		return racf;
	}

	@XmlElement(name="RACF")
	public void setRacf(String racf) {
		this.racf = racf;
	}
	
	public String getProgram() {
		return program;
	}

	@XmlElement(name="PROGRAM")
	public void setProgram(String program) {
		this.program = program;
	}
	
	public String getTransactionTime() {
		return transactionTime;
	}

	@XmlElement(name="TRXNTIME")
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	
	public SystemID getOriginSystem() {
		return originSystem;
	}

	@XmlElement(name="ORIG-SYS")
	public void setOriginSystem(SystemID originSystem) {
		this.originSystem = originSystem;
	}

	public SystemID getDestinationSystem() {
		return destinationSystem;
	}

	@XmlElement(name="DEST-SYS")
	public void setDestinationSystem(SystemID destinationSystem) {
		this.destinationSystem = destinationSystem;
	}

	public Confirmation getConfirmation() {
		return confirmation;
	}

	@XmlElement(name="CONFIRMATION")
	public void setConfirmation(Confirmation confirmation) {
		this.confirmation = confirmation;
	}
	// }}
}
