/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.weatherapp;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class LoginUsingActivityActivity extends Activity {

    private Button buttonLoginLogout;
    private Button buttonshareWeather;
    private Button buttonshareForecast;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    public boolean firstLogin = true;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        buttonLoginLogout = (Button)findViewById(R.id.buttonLoginLogout);
        buttonshareWeather = (Button) findViewById(R.id.shareWeather);
        buttonshareForecast = (Button) findViewById(R.id.shareForecast);

        Settings.addLoggingBehavior(LoggingBehavior.REQUESTS);
        //Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        Session session = Session.getActiveSession();
        if (session == null) 
        {
            if (savedInstanceState != null && !firstLogin) 
            {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) 
            {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) 
            {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }
        if(firstLogin)
        {
        	firstLogin = false;
        }
        updateView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        
        
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) 
    {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

    private void updateView() 
    {
        Session session = Session.getActiveSession();
        if (session.isOpened()) 
        {
        	
        	buttonshareForecast.setVisibility(View.VISIBLE);
        	buttonshareForecast.setOnClickListener(new OnClickListener() 
        	{
				
				@Override
				public void onClick(View v) 
				{
					JSONObj.weather_data = POST_DATA.WEATHER_FORECAST;
				
			    	post();
				}
			});
        	buttonshareWeather.setVisibility(View.VISIBLE);
        	buttonshareWeather.setOnClickListener(new OnClickListener() 
        	{
				
				@Override
				public void onClick(View v) 
				{
					JSONObj.weather_data = POST_DATA.CURRENT_WEATHER;	
					post();				
				}
			});
            buttonLoginLogout.setText(R.string.logout);
            buttonLoginLogout.setOnClickListener(new OnClickListener() 
            {
                public void onClick(View view) 
                { 
                	onClickLogout(); 
                }
            });
            
        }
        else 
        {
        	buttonshareForecast.setVisibility(View.INVISIBLE);
        	buttonshareWeather.setVisibility(View.INVISIBLE);
            buttonLoginLogout.setText(R.string.login);
            buttonLoginLogout.setOnClickListener(new OnClickListener() 
            {
                public void onClick(View view) 
                { 
                	onClickLogin(); 
                }
            });
        }
    }
    
public void post()
{
	Bundle postParams = new Bundle();
	try 
	{
    	JSONObject innerJSON = new JSONObject();
		JSONObject outerJSON = new JSONObject();
		innerJSON.put("text", "here");
		innerJSON.put("href", JSONObj.link);
		outerJSON.put("Look at details", innerJSON);
    	if(JSONObj.weather_data == POST_DATA.CURRENT_WEATHER)
        {
        	// Post current weather
            postParams.putString("name", JSONObj.locationCity + "," + JSONObj.locationRegion + "," + JSONObj.locationCountry);
            postParams.putString("caption", "The current condition for " + JSONObj.locationCity + " is " + JSONObj.conditionText);
            postParams.putString("description", "Temperature is: " + JSONObj.conditionTemp + "\u00B0" + JSONObj.tempUnit);
            postParams.putString("link", JSONObj.feedValue);
            postParams.putString("picture", JSONObj.img);
            postParams.putString("properties", outerJSON.toString());
        }    
    	else
    	{
    		// Post weather forecast	            		
			String day1 = JSONObj.forecast.getJSONObject(0).getString("day") + ":" + JSONObj.forecast.getJSONObject(0).getString("text") + "," + JSONObj.forecast.getJSONObject(0).getString("high") + "/" + JSONObj.forecast.getJSONObject(0).getString("low") + "\u00B0" + JSONObj.tempUnit + ";" ;
			String day2 = JSONObj.forecast.getJSONObject(1).getString("day") + ":" + JSONObj.forecast.getJSONObject(1).getString("text") + "," + JSONObj.forecast.getJSONObject(1).getString("high") + "/" + JSONObj.forecast.getJSONObject(1).getString("low") + "\u00B0" + JSONObj.tempUnit + ";" ;
			String day3 = JSONObj.forecast.getJSONObject(2).getString("day") + ":" + JSONObj.forecast.getJSONObject(2).getString("text") + "," + JSONObj.forecast.getJSONObject(2).getString("high") + "/" + JSONObj.forecast.getJSONObject(2).getString("low") + "\u00B0" + JSONObj.tempUnit + ";" ;
			String day4 = JSONObj.forecast.getJSONObject(3).getString("day") + ":" + JSONObj.forecast.getJSONObject(3).getString("text") + "," + JSONObj.forecast.getJSONObject(3).getString("high") + "/" + JSONObj.forecast.getJSONObject(3).getString("low") + "\u00B0" + JSONObj.tempUnit + ";" ;
			String day5 = JSONObj.forecast.getJSONObject(4).getString("day") + ":" + JSONObj.forecast.getJSONObject(4).getString("text") + "," + JSONObj.forecast.getJSONObject(4).getString("high") + "/" + JSONObj.forecast.getJSONObject(4).getString("low") + "\u00B0" + JSONObj.tempUnit + ";" ;
			
            postParams.putString("name", JSONObj.locationCity + "," + JSONObj.locationRegion + "," + JSONObj.locationCountry);
            postParams.putString("caption", "Weather Forecast for " + JSONObj.locationCity);
            postParams.putString("description", day1 + day2 + day3 + day4 + day5);
            postParams.putString("link", JSONObj.feedValue);
            postParams.putString("picture", "http://www-scf.usc.edu/~csci571/2013Fall/hw8/weather.jpg");
            postParams.putString("properties", outerJSON.toString());
    	}
	}
	catch (JSONException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}

	try
	{
		WebDialog feedDialog = (
	        new WebDialog.FeedDialogBuilder(LoginUsingActivityActivity.this,
	            Session.getActiveSession(),
	            postParams))
	        .setOnCompleteListener(new OnCompleteListener() 
	        {

	            @Override
	            public void onComplete(Bundle values,
	                FacebookException error) 
	            {
	                if (error == null) 
	                {
	                    // When the story is posted, echo the success
	                    // and the post Id.
	                    final String postId = values.getString("post_id");
	                    if (postId != null) 
	                    {
	                        Toast.makeText(LoginUsingActivityActivity.this,
	                            "Posted story, id: "+postId,
	                            Toast.LENGTH_SHORT).show();
	                        finish();
	                        return;
	                    } 
	                    else 
	                    {
	                        // User clicked the Cancel button
	                        Toast.makeText(LoginUsingActivityActivity.this, 
	                            "Publish cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                        finish();
	                        return;
	                    }
	                }
	                else if (error instanceof FacebookOperationCanceledException) 
	                {
	                    // User clicked the "x" button
	                    Toast.makeText(LoginUsingActivityActivity.this, 
	                        "Publish cancelled", 
	                        Toast.LENGTH_SHORT).show();
	                    finish();
	                    return;
	                }
	                else 
	                {
	                    // Generic, ex: network error
	                    Toast.makeText(LoginUsingActivityActivity.this, 
	                        "Error posting story", 
	                        Toast.LENGTH_SHORT).show();
	                    finish();
	                    return;
	                }
	            }

	        }).build();
	    feedDialog.show();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
}
    
    private void onClickLogin() 
    {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) 
        {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } 
        else 
        {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    private void onClickLogout() 
    {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) 
        {
            session.closeAndClearTokenInformation();
        }
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            updateView();
        }
    }
}
