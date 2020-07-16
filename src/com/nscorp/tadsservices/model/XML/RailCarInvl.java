package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlElement;

public class RailCarInvl extends RailCar {
	private String scac;
	private LocationType destinationType;
	
	public RailCarInvl() {
		
	}

	public String getScac() {
		return scac;
	}

	@XmlElement(name="SCAC")
	public void setScac(String scac) {
		this.scac = scac;
	}

	public LocationType getDestinationType() {
		return destinationType;
	}

	@XmlElement(name="DEST")
	public void setDestinationType(LocationType destinationType) {
		this.destinationType = destinationType;
	}

	@Override
	public String toString() {
		return "RailCarInvl [scac=" + scac + ", destinationType=" + destinationType + ", toString()=" + super.toString()
				+ "]";
	}
	
}
