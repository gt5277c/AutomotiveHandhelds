package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlElement;

public class PieceIdentificationTruckOut extends PieceIdentification {
	private String vrProdStat;
	
	public String getVrProdStat() {
		return vrProdStat;
	}

	@XmlElement(name="VRPRODSTAT")
	public void setVrProdStat(String vrProdStat) {
		this.vrProdStat = vrProdStat;
	}
}
