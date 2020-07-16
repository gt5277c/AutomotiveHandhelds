package com.nscorp.tadsservices.model;

import com.nscorp.tadsservices.model.Enums.eLotCode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class Common {
	private static Pattern pAlphaNumeric = Pattern.compile("^[a-zA-Z0-9]*$");
	private static final String FORMATTER_TEXT = "yyyyMMddHHmm";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(FORMATTER_TEXT);
	private static final SimpleDateFormat SECONDS_FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String LotEnumToCode(eLotCode eLot) {
		
		switch(eLot) {
		case eTruck:
			return Constants.TRUCK_LOT;
		case eLoadLine:
			return Constants.LOADLINE_LOT;
		case eStorage:
			return Constants.STORE_LOT;
		case eRail:
			return Constants.TRACK_LOT;
		case eSupportTrack:
			return Constants.SUPPORT_TRACK_LOT;
		default:
			return null;
		}
	}
	
	public static String PadString(String sString, Integer field_length) {

		sString = String.format("%1$" + field_length + "s", sString);
		
		return sString;
	}
	
	public static String FormatRailcar(String railcar) {
		String regex = "((?<=[a-zA-Z])(?=[0-9]))|((?<=[0-9])(?=[a-zA-Z]))";
		String railcarinit = null;
		String railcarnum = null;
		
		if(railcar != null) {
			List<String> railcararray = Arrays.asList(railcar.split(regex));
			if(railcararray.size() > 1) {
			    //Get the railcar initials
			    railcarinit = railcararray.get(0).toUpperCase();    
			    railcarinit = railcarinit.length() > 4 ? railcarinit.substring(0,4) : String.format("%-4s",railcarinit);
			    
			    //Get the railcar number
			    railcarnum = railcararray.get(1);
			    railcarnum = railcarnum.length() > 6 ? railcarnum.substring(0,6) : String.format("%6s",railcarnum);
			    
			    railcar = railcarinit + railcarnum;
			}
		}
		return railcar;
	}
	
	public static String getRailcarInit(String railcar) {
		String regex = "((?<=[a-zA-Z])(?=[0-9]))|((?<=[0-9])(?=[a-zA-Z]))";
		
		if(railcar != null) {
			List<String> railcararray = Arrays.asList(railcar.split(regex));
			if(railcararray.size() > 1) {
			    //Get the railcar initials
			    railcar = railcararray.get(0).toUpperCase();    
			}
		}
		return railcar;
	}
	
	public static String getRailcarNum(String railcar) {
		String regex = "((?<=[a-zA-Z])(?=[0-9]))|((?<=[0-9])(?=[a-zA-Z]))";
		
		if(railcar != null) {
			List<String> railcararray = Arrays.asList(railcar.split(regex));
			if(railcararray.size() > 1) {
			    //Get the railcar number
			    railcar = railcararray.get(1);
			}
		}
		return railcar;
	}
	
	public static boolean isAlphaNumeric(String s) {
		return pAlphaNumeric.matcher(s).find();
	}
	
	public static String getCurrentDateTimeAsString() {
		Date date = new Date();
		return FORMATTER.format(date);
	}
	
	public static Date getCurrentDateTime() {
		Date date = new Date();
		return date;
	}
	
	public static String getCurrentDateTimeSecondsAsString() {
		Date date = new Date();
		return SECONDS_FORMATTER.format(date);
	}
	
	public static String getDateTime(Date somedate) {
		return FORMATTER.format(somedate);
	}
	
	public static String getDateTimeSeconds(Date somedate) {
		return SECONDS_FORMATTER.format(somedate);
	}
	
	public static XMLGregorianCalendar getXMLGregorianCalendarCurrentDateTime(){
		XMLGregorianCalendar now = null;
		
	 	try{
	        GregorianCalendar gregorianCalendar = new GregorianCalendar();
	        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
	        now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
	        
	        return now;
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
	 	
		return now;		
	}
	
	public static XMLGregorianCalendar createGregorianDate(String date, String fieldName) {
		try {
			SimpleDateFormat formatter = FORMATTER;
			if (date != null && date.length() != FORMATTER_TEXT.length()) {
				formatter = SECONDS_FORMATTER;
			}
			Date dt = formatter.parse(date);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dt);
			XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			return xmlGregCal;
		}
		catch (Exception e) {
			return getXMLGregorianCalendarCurrentDateTime();
		}
	}
}
