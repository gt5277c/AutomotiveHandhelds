package com.nscorp.tadsservices.model;

import java.util.List;

import com.nscorp.tadsservices.model.DAO.SortOrderItemDAO;

public class SortOrderItem extends TADSLocationObject{
	////////////////////////////////////////////////////////////
	//Member variables
	////////////////////////////////////////////////////////////
	private String Lot;
	private String Zone;
	private int RowOrder = 0;
	private int SpotOrder = 0;
	private SortOrderItemDAO soDAO;
	
	//Constructor
	public SortOrderItem() {}
	
	public SortOrderItem(Database database) {
		setLocationDatabase(database);
	}
	
	////////////////////////////////////////////////////////////
	//Getters and Setters
	////////////////////////////////////////////////////////////
	// {{
	@Override
	public void setLocationDatabase(Database database) {
		this.database = database;
		soDAO = new SortOrderItemDAO(database);
	}
	
	public String getLot() { return Lot; }
	public void setLot(String lot) { Lot = lot; }
	
	public String getZone() { return Zone; }
	public void setZone(String zone) { Zone = zone;	}
	
	public int getRowOrder() { return RowOrder;	}
	public void setRowOrder(int rowOrder) { RowOrder = rowOrder; }
	
	public int getSpotOrder() { return SpotOrder; }
	public void setSpotOrder(int spotOrder) { SpotOrder = spotOrder; }	
	// }}
	
	////////////////////////////////////////////////////////////
	//Methods
	////////////////////////////////////////////////////////////
	public void Save(List<SortOrderItem> soil) {
		soDAO.setAllSortItems(soil);
	}
	
	public SortOrderItem get(String Lot, String Zone) {
		SortOrderItem soi = soDAO.get(Lot,Zone);
		
		//If there is not a sort order return the default
		if(soi == null) soi = this;
				
		return soi;
	}
	
	public List<SortOrderItem> getAllSortItems(){
		return soDAO.getAllSortItems();
	}
	
}
