package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"pos","deck","spot","row","zone","lot"})
public class InventoryLocation {
	private String row;
	private String spot;
	private String zone;
	private String lot;
	private String pos;
	private String deck;
	
	public String getRow() {
		return row;
	}

	@XmlAttribute(name="ROW")
	public void setRow(String row) {
		this.row = row;
	}

	public String getSpot() {
		return spot;
	}

	@XmlAttribute(name="SPOT")
	public void setSpot(String spot) {
		this.spot = spot;
	}

	public String getZone() {
		return zone;
	}

	@XmlAttribute(name="ZONE")
	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getLot() {
		return lot;
	}

	@XmlAttribute(name="LOT")
	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getPos() {
		return pos;
	}

	@XmlAttribute(name="POS")
	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getDeck() {
		return deck;
	}

	@XmlAttribute(name="DECK")
	public void setDeck(String deck) {
		this.deck = deck;
	}
}
