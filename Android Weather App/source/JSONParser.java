package com.example.weatherapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class JSONParser extends AsyncTask<String, Void, String>
{
	MainActivity act;
	public JSONParser(MainActivity activity) 
	{
		// TODO Auto-generated constructor stub
		act = activity;
	}

	@Override
	protected String doInBackground(String... args) 
	{
		return getResultsAndDisplayDetails(args[0], args[1], args[2]);
	}

	@Override
	protected void onPostExecute(String result) 
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		act.createUI(result);
	}
	
	public String getResultsAndDisplayDetails(String type, String location, String unit)
    {
    	String myurl = "http://cs-server.usc.edu:34448/examples/servlet/weathersearch?location=" + URLEncoder.encode(location) + "&type=" + type + "&unit=" + unit;
    	String myJSONString = null;
    	try
    	{
    		
    		URL url = new URL(myurl);
    		InputStream input = url.openStream();
			myJSONString = getStringFromInputStream(input);
    	}
    	catch(MalformedURLException ex)
    	{
    		ex.printStackTrace();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    	catch(Exception ex1)
    	{
    		ex1.printStackTrace();
    	}
    	
    	return myJSONString;
    }
    
    public String getStringFromInputStream(InputStream in)
    {
    	final char[] buffer = new char[10000];
    	final StringBuilder out = new StringBuilder();
    	try 
    	{
    		final Reader reader = new InputStreamReader(in, "UTF-8");
    		try 
    		{
    			for (;;) 
    			{
    				int rsz = reader.read(buffer, 0, buffer.length);
    				if (rsz < 0)
    					break;
    				out.append(buffer, 0, rsz);
    			}
    		}
    		finally 
    		{
    			in.close();
    		}
    	}
    	  
    	catch (UnsupportedEncodingException ex) 
    	{
    	    ex.printStackTrace();
    	}
    	  
    	catch (IOException ex) 
    	{
    	      ex.printStackTrace();
    	}
    	return out.toString();
    }

}

