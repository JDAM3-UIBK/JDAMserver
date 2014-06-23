package com.jdam.logic;

import com.jdam.enums.InputType;
import com.jdam.model.LatLng;

/**
 * 
 * @author Andre Bernecker und Martin Solderer
 * URLParser welcher für die zerlegung der URL-Anfrage zustaendig ist.
 *
 */
public class UrlParser {

	private char delimiterFromTo = '=';
	private char delimiterDestOrigin = '&';
	private char delimiterLatLng = ',';
	private char delimiterType = '$';

	public UrlParser(){}

	//url/Server/from=---&to=---
	// --- lat,long oder string

	//parsing text /Server/latlng?from=innsbruck&to=wien
	//	latlngstr
	//	str

	/**
	 * prueft ob eine Anfrage gueltig ist
	 * @param str URL-Anfrage
	 * @return boolean ob die Anfrage akzeptiert wird oder nicht
	 */
	public boolean checkInput(String str){
		boolean result = false;
		if(inputType(str) != null && inputType(str).toString().equals(InputType.str.toString()))
		{
			if(!parserFrom(str).equals(""))
			{
				if(!parserTo(str).equals("")){				
					result = true;
				}
			}
		}
		if(inputType(str) != null && inputType(str).toString().equals(InputType.latlngstr.toString()))
		{
			if(parserFromLatLng(str) != null)
			{
				if(!parserTo(str).equals("")){				
					result = true;
				}
			}
		}	
		if(inputType(str) != null && inputType(str).toString().equals(InputType.latlng.toString()))
		{
			
			if(parserFromLatLng(str) != null && parserToLatLng(str) != null)
			{
					result = true;
			}
		}	
		return result;
	}

	/**
	 * sieht nach um was für eine Anfrage es sich genau handelt -> String/LngLatStr...
	 * @param str URL-Anfrage
	 * @return enum des InputTypes
	 */
	public Enum inputType(String str){
		int start = 0;
		int stop = str.indexOf(delimiterType, start);
		if(stop == -1)
			return null;

		String text = str.substring(start, stop);

		if(text.equals(InputType.latlng.toString()))
			return InputType.latlng;
		else if(text.equals(InputType.latlngstr.toString()))
			return InputType.latlngstr;
		else if(text.equals(InputType.str.toString()))
			return InputType.str;
		else
			return null;
	}

	/**
	 * Gibt Startpunkt als String zurueck
 * @param str URL-Anfrage
	 * @return Startpunkt
	 */
	public String parserFrom(String str){
		int start = 0;
		start = str.indexOf(delimiterFromTo, start)+1;
		int stop = str.indexOf(delimiterDestOrigin, start);

		if(start == -1 || stop == -1)
			return "";

		return str.substring(start, stop);
	}
	
	/**
	 * Gibt Endpunkt als String zurueck
 * @param str URL-Anfrage
	 * @return Endpunkt
	 */
	public String parserTo(String str){
		int start = 0;
		start = str.indexOf(delimiterDestOrigin, start);
		start = str.indexOf(delimiterFromTo, start)+1;
		int stop = str.length();

		if(start == -1 || stop == -1)
			return "";

		return str.substring(start, stop);
	}

	/**
	 * Gibt Startpunkt als LatLng zurueck
 * @param str URL-Anfrage
	 * @return Startpunkt
	 */
	public LatLng parserFromLatLng(String str){	
		int start = 0;
		start = str.indexOf(delimiterFromTo, start)+1;
		int stop = str.indexOf(delimiterLatLng, start);

		if(start == -1 || stop == -1)
			return null;		
		Double lat = 0.0;
		try{
			lat = Double.valueOf(str.substring(start, stop));
		}catch(NumberFormatException e){
			return null;
		}

		start = stop+1;
		stop = str.indexOf(delimiterDestOrigin, start);
		if(start == -1 || stop == -1)
			return null;	

		Double lng = 0.0;
		try{
//			System.out.println("SUBSTRING: " +str.substring(start,stop));
			lng = Double.valueOf(str.substring(start, stop));
		} catch (NumberFormatException e){
			return null;
		}
		
//		System.out.println("FROM: " + lat + " / " +lng);
		return new LatLng(lat, lng);
	}

	/**
	 * Gibt Endpunkt als LatLng zurueck
 * @param str URL-Anfrage
	 * @return Endpunkt
	 */
	public LatLng parserToLatLng(String str){
		int start = 0;
		start = str.indexOf(delimiterDestOrigin, start);
		start = str.indexOf(delimiterFromTo, start)+1;
		int stop = str.indexOf(delimiterLatLng, start);
		if(start == -1 || stop == -1)
			return null;		
		Double lat = 0.0;
		try{
			lat = Double.valueOf(str.substring(start, stop));
		}catch(NumberFormatException e){
			return null;
		}
		start = stop+1;
		stop = str.length();
		if(start == -1 || stop == -1)
			return null;	

		Double lng = 0.0;
		try{
			lng = Double.valueOf(str.substring(start, stop));
		} catch (NumberFormatException e){
			return null;
		}
//		System.out.println("TO: " + lat + " / " +lng);
		return new LatLng(lat, lng);
	}

	/**
	 * Ersetzt alle Leerzeichen mit +
	 * @param url URL-Anfrage
	 * @return Anfrage ohne Leerzeichen
	 */
	public String delSpace(String url){	
		return url.replaceAll(" ", "+");
	}


	public static void main(String[] args) {

	}
}
