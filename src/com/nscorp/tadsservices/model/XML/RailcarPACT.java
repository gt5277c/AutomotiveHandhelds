package com.nscorp.tadsservices.model.XML;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"previousLocation"})
public class RailcarPACT  extends RailCar{
	private Location previousLocation;
	
	public RailcarPACT() {
		previousLocation = new Location();
	}

	public Location getPreviousLocation() {
		return previousLocation;
	}

	@XmlElement(name="PREVLOC")
	public void setPreviousLocation(Location previousLocation) {
		this.previousLocation = previousLocation;
	}

}
