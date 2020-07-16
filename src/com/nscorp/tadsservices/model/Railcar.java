package com.nscorp.tadsservices.model;

import java.util.Date;
import com.nscorp.tadsservices.model.DAO.RailcarDAO;

public class Railcar extends TADSLocationObject {

	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	// {{
	private String CarNumber;
	private String DeckDesc;
	private String DeckType;
	private String BiTriIndicator;
	private String TieDownDesc;
	private String TieDownType;
	private String ArtCode;
	private String Type;
	private double DeckAHeight;
	private double DeckBHeight;
	private double DeckCHeight;
	private double Length;
	private double OuterLength;
	private double Width;
	private double Weight;
	private String STCC;
	private String ChngWho;
	private Date ChngDate;
	private RailcarDAO rDAO;
	// }}

	//Constructor
	public Railcar() {}
	
	public Railcar(Database database) {
		setLocationDatabase(database);
	};

	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////	
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		rDAO = new RailcarDAO(database);
	}
	
	public String getCarNumber() { return CarNumber; }
	public void setCarNumber(String CarNumber) { this.CarNumber = CarNumber; }
	
	public String getDeckDesc() { return DeckDesc; }
	public void setDeckDesc(String DeckDesc) { this.DeckDesc = DeckDesc; }
	
	public String getDeckType() { return DeckType; }
	public void setDeckType(String DeckType) { this.DeckType = DeckType; }
	
	public String getBiTriIndicator() { return BiTriIndicator; }
	public void setBiTriIndicator(String BiTriIndicator) { this.BiTriIndicator = BiTriIndicator; }
	
	public String getTieDownDesc() { return TieDownDesc; }
	public void setTieDownDesc(String TieDownDesc) { this.TieDownDesc = TieDownDesc; }
	
	public String getTieDownType() { return TieDownType; }
	public void setTieDownType(String TieDownType) { this.TieDownType = TieDownType; }
	
	public String getArtCode() { return ArtCode; }
	public void setArtCode(String ArtCode) { this.ArtCode = ArtCode; }
	
	public String getType() { return Type; }
	public void setType(String Type) { this.Type = Type; }
	
	public double getDeckAHeight() { return DeckAHeight; }
	public void setDeckAHeight(double DeckAHeight) { this.DeckAHeight = DeckAHeight; }
	
	public double getDeckBHeight() { return DeckBHeight; }
	public void setDeckBHeight(double DeckBHeight) { this.DeckBHeight = DeckBHeight; }
	
	public double getDeckCHeight() { return DeckCHeight; }
	public void setDeckCHeight(double DeckCHeight) { this.DeckCHeight = DeckCHeight; }
	
	public double getLength() { return Length; }
	public void setLength(double Length) { this.Length = Length; }
	
	public double getOuterLength() { return OuterLength; }
	public void setOuterLength(double OuterLength) { this.OuterLength = OuterLength; }
	
	public double getWidth() { return Width; }
	public void setWidth(double Width) { this.Width = Width; }
	
	public double getWeight() { return Weight; }
	public void setWeight(double Weight) { this.Weight = Weight; }
	
	public String getSTCC() { return STCC; }
	public void setSTCC(String STCC) { this.STCC = STCC; }
	
	public String getChngWho() { return ChngWho; }
	public void setChngWho(String ChngWho) { this.ChngWho = ChngWho; }
	
	public Date getChngDate() { return ChngDate; }
	public void setChngDate(Date ChngDate) { this.ChngDate = ChngDate; }
	// }}
	
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////	
	public Railcar getRailcar(String railcar) {
		return rDAO.get(railcar);
	}
}
