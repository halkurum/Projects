package com.example.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

enum POST_DATA
{
	CURRENT_WEATHER,
	WEATHER_FORECAST
}

public class JSONObj 
{
	static POST_DATA weather_data;
	static String conditionTemp = "";
	static String conditionText = "";
	static String feedValue = "";
	static String img = "";
	static String link = "";
	static String locationCity = "";
	static String locationCountry = "";
	static String locationRegion = "";
	static String tempUnit = "";
	static String forecastText = "Forecast";
	static String degree = "";
	static JSONArray forecast = null;
	static int count = 0;
	
	public static boolean populateValues(JSONObject weather)
	{
		boolean retval = true;
		try
		{
			conditionTemp = weather.getJSONObject("condition").getString("temp");
			conditionText = weather.getJSONObject("condition").getString("text");
			feedValue = weather.getJSONObject("feed").getString("value");
			img = weather.getString("img");
			link = weather.getString("link");
			locationCity = weather.getJSONObject("location").getString("city");
			locationCountry = weather.getJSONObject("location").getString("country");
			locationRegion = weather.getJSONObject("location").getString("region");
			tempUnit = weather.getJSONObject("units").getString("temperature");
			forecast = weather.getJSONArray("forecast");
			forecastText = "Forecast";
			degree = "\u00B0";
		}
		catch(JSONException ex)
		{
			ex.printStackTrace();
			retval = false;
		}
		return retval;
	}
	
	public static void flushData()
	{
		conditionTemp = "";
    	conditionText = "";
    	feedValue = "";
    	img = "";
    	link = "";
    	locationCity = "";
    	locationCountry = "";
    	locationRegion = "";
    	tempUnit = "";
    	forecast = null;
    	forecastText = "";
    	degree = "";
	}	

}
