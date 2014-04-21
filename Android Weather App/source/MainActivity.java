package com.example.weatherapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.facebook.samples.sessionlogin.LoginUsingLoginFragmentActivity;
//import com.facebook.samples.sessionlogin.SessionLoginSampleActivity;

//import com.facebook.android.DialogError;
//import com.facebook.android.Facebook;
//import com.facebook.android.Facebook.DialogListener;
//import com.facebook.android.FacebookError;









import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.sax.TextElementListener;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
//import android.widget.LinearLayout;
import android.widget.RelativeLayout;
//import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();      
        StrictMode.setThreadPolicy(policy);
        
        //SetBackGroundImage();
        return true;
    }
    
    public static String unit = "f";
    
    private Bitmap getBitMap(String myurl)
    {
    	try 
    	{
    		URL url = new URL(myurl);
    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    		connection.setDoInput(true);
    		connection.connect();
    		InputStream input = connection.getInputStream();
    		Bitmap myBitmap = BitmapFactory.decodeStream(input);
    		return myBitmap;
    	} 
    	catch (IOException e) 
    	{
    		e.printStackTrace();
    		return null;
    	}
    }
   
    public void displayData()
	{
		try
		{
	    	TextView city = (TextView) findViewById(R.id.city);
	    	city.setText(JSONObj.locationCity);
	    	TextView loc = (TextView) findViewById(R.id.loc);
	    	if(JSONObj.locationRegion == "")
	    	{
	    		loc.setText(JSONObj.locationRegion);
	    	}
	    	else
	    	{
	    		loc.setText(JSONObj.locationRegion + ", " + JSONObj.locationCountry);
	    	}
	    	ImageView img = (ImageView) findViewById(R.id.condition_img);
	    	
	    	TextView condition = (TextView) findViewById(R.id.condition);
	    	condition.setText(JSONObj.conditionText);
	    	if(JSONObj.img == "")
	    	{
	    		img.setImageDrawable(null);
	    	}
	    	else
	    	{
	    		BitmapDrawable d = new BitmapDrawable(getBitMap(JSONObj.img));
	    		img.setImageDrawable(d);
	    	}
	    	TextView temperature = (TextView) findViewById(R.id.tempreature);
	    	if(JSONObj.conditionTemp == "")
	    	{
	    		temperature.setText("");
	    		TableLayout myTable = (TableLayout) findViewById(R.id.forecastTable);
	        	myTable.setVisibility(View.INVISIBLE);
	        	
	        	ImageView fbPost = (ImageView) findViewById(R.id.facebook_img);
	        	fbPost.setVisibility(View.INVISIBLE);
	        	
	    	}
	    	else
	    	{
	    		temperature.setText(JSONObj.conditionTemp + "\u00B0" +JSONObj.tempUnit);
	    		setForecastTableContents();
	    		
	    		ImageView fbPost = (ImageView) findViewById(R.id.facebook_img);
	        	fbPost.setVisibility(View.VISIBLE);
	    	}
	    	TextView forecast = (TextView) findViewById(R.id.forecastText);
	    	forecast.setText(JSONObj.forecastText);
	    	
	    	
	    }
	    catch(Exception ex)
	    {
	    		ex.printStackTrace();
	    }	
	}
    
    public void setForecastTableContents()
    {
    	try
    	{
    		TableLayout myTable = (TableLayout) findViewById(R.id.forecastTable);
    		myTable.setVisibility(View.VISIBLE);
    		TextView day1 = (TextView) findViewById(R.id.Day1);
    		day1.setText(JSONObj.forecast.getJSONObject(0).getString("day"));
    		TextView weather1 = (TextView) findViewById(R.id.Weather1);
    		weather1.setText(JSONObj.forecast.getJSONObject(0).getString("text"));
    		TextView high1 = (TextView) findViewById(R.id.High1);
    		high1.setText(JSONObj.forecast.getJSONObject(0).getString("high") + "\u00B0" +JSONObj.tempUnit);
    		TextView low1 = (TextView) findViewById(R.id.Low1);
    		low1.setText(JSONObj.forecast.getJSONObject(0).getString("low") + "\u00B0" +JSONObj.tempUnit);
    		
    		TextView day2 = (TextView) findViewById(R.id.Day2);
    		day2.setText(JSONObj.forecast.getJSONObject(1).getString("day"));
    		TextView weather2 = (TextView) findViewById(R.id.Weather2);
    		weather2.setText(JSONObj.forecast.getJSONObject(1).getString("text"));
    		TextView high2 = (TextView) findViewById(R.id.High2);
    		high2.setText(JSONObj.forecast.getJSONObject(1).getString("high") + "\u00B0" +JSONObj.tempUnit);
    		TextView low2 = (TextView) findViewById(R.id.Low2);
    		low2.setText(JSONObj.forecast.getJSONObject(1).getString("low") + "\u00B0" +JSONObj.tempUnit);
    		
    		TextView day3 = (TextView) findViewById(R.id.Day3);
    		day3.setText(JSONObj.forecast.getJSONObject(2).getString("day"));
    		TextView weather3 = (TextView) findViewById(R.id.Weather3);
    		weather3.setText(JSONObj.forecast.getJSONObject(2).getString("text"));
    		TextView high3 = (TextView) findViewById(R.id.High3);
    		high3.setText(JSONObj.forecast.getJSONObject(2).getString("high") + "\u00B0" +JSONObj.tempUnit);
    		TextView low3 = (TextView) findViewById(R.id.Low3);
    		low3.setText(JSONObj.forecast.getJSONObject(2).getString("low") + "\u00B0" +JSONObj.tempUnit);
    		
    		TextView day4 = (TextView) findViewById(R.id.Day4);
    		day4.setText(JSONObj.forecast.getJSONObject(3).getString("day"));
    		TextView weather4 = (TextView) findViewById(R.id.Weather4);
    		weather4.setText(JSONObj.forecast.getJSONObject(3).getString("text"));
    		TextView high4 = (TextView) findViewById(R.id.High4);
    		high4.setText(JSONObj.forecast.getJSONObject(3).getString("high") + "\u00B0" +JSONObj.tempUnit);
    		TextView low4= (TextView) findViewById(R.id.Low4);
    		low4.setText(JSONObj.forecast.getJSONObject(3).getString("low") + "\u00B0" +JSONObj.tempUnit);
    		
    		TextView day5 = (TextView) findViewById(R.id.Day5);
    		day5.setText(JSONObj.forecast.getJSONObject(4).getString("day"));
    		TextView weather5 = (TextView) findViewById(R.id.Weather5);
    		weather5.setText(JSONObj.forecast.getJSONObject(4).getString("text"));
    		TextView high5 = (TextView) findViewById(R.id.High5);
    		high5.setText(JSONObj.forecast.getJSONObject(4).getString("high") + "\u00B0" +JSONObj.tempUnit);
    		TextView low5 = (TextView) findViewById(R.id.Low5);
    		low5.setText(JSONObj.forecast.getJSONObject(4).getString("low") + "\u00B0" +JSONObj.tempUnit);
    		
    	}
    	catch(JSONException ex)
    	{
    		ex.printStackTrace();
    	}

    }
    
    public boolean CheckInt(String myString)
    {
    	int val;
    	try 
    	{ 
            val = Integer.parseInt(myString); 
        } 
    	catch(NumberFormatException e) 
    	{ 
    		return false;
        }
    	return true;
    }
    
    /*public boolean ValidateLocString(String myString)
    {
    	return true;
    }*/
    
    public void createUI(String json)
    {	
    	try
    	{
    		JSONObj.flushData();
    		displayData();
    		JSONObject myJSON = new JSONObject(json);
    		JSONObject weather = myJSON.getJSONObject("weather");
    		if(weather.getString("hasValue") == "false")
    		{
    			// Weather info not found.
    			TextView city = (TextView) findViewById(R.id.city);
    	    	city.setText("Weather Information not found");
    			return;
    		}
    		boolean hasValue =  JSONObj.populateValues(weather);
    		if(!hasValue)
    		{
    			TextView city = (TextView) findViewById(R.id.city);
    	    	city.setText("Weather Information not found");
    			return;
    		}
    		displayData();
    	}
    	
    	catch(JSONException ex)
    	{
    		ex.printStackTrace();
    		TextView city = (TextView) findViewById(R.id.city);
	    	city.setText("Weather Information not found");
    	}
    	catch(Exception ex)
    	{
    		TextView city = (TextView) findViewById(R.id.city);
	    	city.setText("Weather Information not found");
    	}
    	return;
    }
    
    // Event handler for "F" Radio Button
    public void fClick(View view)
    {
    	RadioButton cButton = (RadioButton) findViewById(R.id.celsius);
    	cButton.setChecked(false);
    	unit = "f";
    }
    
    // Event handler for "F" Radio Button
    public void cClick(View view)
    {
    	RadioButton fButton = (RadioButton) findViewById(R.id.fahrenheit);
    	fButton.setChecked(false);
    	unit = "c";
    }
    
    public void postToFB(View view)
    {
		Intent intent = new Intent(MainActivity.this, LoginUsingActivityActivity.class);
		startActivity(intent);
    }    

    //Event handler for Search Button 
    public void ValidateLocAndDisplayWeather(View view)
    {	
    	String type = "city";
    	EditText editText = (EditText) findViewById(R.id.Location);
    	String locString = editText.getText().toString();
    	
    	if(locString.isEmpty())
    	{
    		JSONObj.flushData();
    		displayData();
    		Toast myToast = Toast.makeText(getApplicationContext(), "Location Value Cannot be empty", Toast.LENGTH_SHORT);
    		myToast.setGravity(Gravity.CENTER, 0, 0);
        	myToast.show();
        	return;
    	}
    	
    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
    	dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() 
    	{	 
            public void onClick(DialogInterface dialog, int which) 
            {
        		dialog.dismiss();
        		return;
            }
        });
    	
    	if(CheckInt(locString))
    	{
    		type = "zip";
    		if((locString.length()) != 5)
    		{
    			JSONObj.flushData();
        		displayData();
    	    	dlgAlert.setMessage("Invalid zipcode: must be five digits \nExample: 90089");    	
    	    	dlgAlert.create().show();
    	    	return;
    		}
    	}
    	
    	else if(locString.indexOf(',') == -1)
    	{
    		JSONObj.flushData();
    		displayData();
    		dlgAlert.setMessage("Invalid Location: Must include state or country sepertaed by comma \nExample: Los Angeles, CA");    	
	    	dlgAlert.create().show();
	    	return;
    	}
      	locString=locString.replace("'", "");
    	JSONParser myParser = new JSONParser(this);
    	myParser.execute(type, locString, unit);
    }
}
