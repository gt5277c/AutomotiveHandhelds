package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"type","value","manufacturer","inventoryLocation",
		"transmitToTrucker","transmitToTruckerSCAC","route","diversion","controller","color"})
public class PieceIdentification {
	private String value;
	private String type;
	private Manufacturer manufacturer;
	private InventoryLocation inventoryLocation;
	private String transmitToTrucker;
	private String transmitToTruckerSCAC;
	private String route;
	private String diversion;
	private Controller controller;
	private Color color;
	
	public String getValue() {
		return value;
	}

	@XmlAttribute(name="VALUE")
	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	@XmlAttribute(name="TYPE")
	public void setType(String type) {
		this.type = type;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	@XmlElement(name="MFR")
	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public InventoryLocation getInventoryLocation() {
		return inventoryLocation;
	}

	@XmlElement(name="INVLOC")
	public void setInventoryLocation(InventoryLocation inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}

	public String getRoute() {
		return route;
	}

	@XmlElement(name="RTE")
	public void setRoute(String route) {
		this.route = route;
	}

	public String getDiversion() {
		return diversion;
	}

	@XmlElement(name="DIVERSION")
	public void setDiversion(String diversion) {
		this.diversion = diversion;
	}

	public Controller getController() {
		return controller;
	}

	@XmlElement(name="CONTROLLER")
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Color getColor() {
		return color;
	}

	@XmlElement(name="COLOR")
	public void setColor(Color color) {
		this.color = color;
	}
	
	public String getTransmitToTrucker() {
		return transmitToTrucker;
	}

	@XmlElement(name="TRANSMITTOTRUCKER")
	public void setTransmitToTrucker(String transmitToTrucker) {
		this.transmitToTrucker = transmitToTrucker;
	}
	
	public String getTransmitToTruckerSCAC() {
		return transmitToTruckerSCAC;
	}

	@XmlElement(name="TRANSMITTOTRUCKERSCAC")
	public void setTransmitToTruckerSCAC(String transmitToTruckerSCAC) {
		this.transmitToTruckerSCAC = transmitToTruckerSCAC;
	}
	
}
