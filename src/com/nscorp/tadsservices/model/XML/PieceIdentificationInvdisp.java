package com.nscorp.tadsservices.model.XML;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"transmitToTrucker","vrProdState"})
public class PieceIdentificationInvdisp extends PieceIdentification {
	private String transmitToTrucker;
	private String vrProdState;
	
	@Override
	public String getTransmitToTrucker() {
		return transmitToTrucker;
	}

	@Override
	@XmlElement(name="TRANSMITTOTRUCKER")
	public void setTransmitToTrucker(String transmitToTrucker) {
		this.transmitToTrucker = transmitToTrucker;
	}

	public String getVrProdState() {
		return vrProdState;
	}

	@XmlElement(name="VRPRODSTAT")
	public void setVrProdState(String vrProdState) {
		this.vrProdState = vrProdState;
	}
}
