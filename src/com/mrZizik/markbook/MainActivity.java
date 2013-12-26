package com.mrZizik.markbook;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView LNameInput;
	private TextView FNameInput;
	private TextView PInput;
	private TextView NZachKnTxt;
	private Intent intent;
	public static final String APP_PREFERENCES = "mysettings";
	public static final String APP_PREFERENCES_LNAME = "lname";
	public static final String APP_PREFERENCES_FNAME = "fname";
	public static final String APP_PREFERENCES_PNAME = "pname";
	public static final String APP_PREFERENCES_ZACH = "zach";
	SharedPreferences mSettings;
	private TextView title;
	private int ok;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    	intent = new Intent(this, SubjectsListActivity.class);
    	if (mSettings.contains(APP_PREFERENCES_LNAME)) {
    		signInBackground();
    	}
		registerUI();
	}

	public void registerUI() {
		LNameInput = (TextView) findViewById(R.id.lastNameInput);
		FNameInput = (TextView) findViewById(R.id.firstNameInput);
		title = (TextView) findViewById(R.id.subjectName);
		PInput = (TextView) findViewById(R.id.patronymcInput);
		NZachKnTxt = (TextView) findViewById(R.id.facebookNumInput);
		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void signIn(View view) {
		signInBackground();

	}

	private void signInBackground() {
        new AsyncTask<Void, Void, String>() {

			

			private int ok;
			private String LName;
			private String FName;
			private String PName;
			private String NZach;

			@Override
            protected String doInBackground(Void... params) {
				LName = LNameInput.getText().toString().trim();
				FName = FNameInput.getText().toString().trim();
				PName = PInput.getText().toString().trim();
				NZach = NZachKnTxt.getText().toString().trim();
				Log.i("response","Beginning");
				if (mSettings.contains(APP_PREFERENCES_LNAME)) {
				if ((mSettings.getString(APP_PREFERENCES_LNAME, "someString") != "someString")) {
						LName = mSettings.getString(APP_PREFERENCES_LNAME, "someString");
						FName = mSettings.getString(APP_PREFERENCES_FNAME, "someString");
						PName = mSettings.getString(APP_PREFERENCES_PNAME, "someString");
						NZach = mSettings.getString(APP_PREFERENCES_ZACH, "someString");
						Log.e("coord","asdada");
				}}
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost request = new HttpPost("http://isu.dgu.ru/Students/login.aspx?&cookieCheck=true");
					request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
					request.setHeader("Accept-Language", "ru,en;q=0.8");
					request.setHeader("Accept-Encoding", "gzip,deflate,sdch");
					request.setHeader("Content-Type","application/x-www-form-urlencoded");
					request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:26.0) Gecko/20100101 Firefox/26.0");
					request.setHeader("Referer", "http://isu.dgu.ru/Students/login.aspx?cookieCheck=true");
					request.setHeader("Host", "isu.dgu.ru");
					request.setHeader("Cookie","SupportCookies=true");
					
					List<NameValuePair> postParametrs = new ArrayList<NameValuePair>(8);
					postParametrs.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWBgLS+veQDQK4jZRCAqaKlEICypChgggCl/KPlQYCm4zxugSw6m3BilByVPaR+XVjHSIaS8dK1ApqDPCLBeudu28kdA=="));
					postParametrs.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUKMTE3NTc2NzczNWRkFJtzZ5imTy8h8IwDOAKCdoI0FnWioZLX3x+IdBJmcLE="));
					postParametrs.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
					postParametrs.add(new BasicNameValuePair("__EVENTTARGET", "EnterBtn"));
					postParametrs.add(new BasicNameValuePair("LNameTxt", LName));
					postParametrs.add(new BasicNameValuePair("FNameTxt", FName));
					postParametrs.add(new BasicNameValuePair("PatrTxt", PName));
					postParametrs.add(new BasicNameValuePair("NZachKnTxt", NZach));
					try {
						request.setEntity(new UrlEncodedFormEntity(postParametrs, "windows-1251"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					try {
						HttpResponse Response = httpClient.execute(request);
						Log.e("code","code:"+Response.getStatusLine().getStatusCode());
					} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
						CookieStore cookieStore = new BasicCookieStore();
                        // Create local HTTP context
                        HttpContext localContext = new BasicHttpContext();
                        // Bind custom cookie store to the local context
                        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                        //***********
                         try {
							HttpResponse response1 = httpClient.execute(request, localContext);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        //***********
                         List<Cookie> cookies = cookieStore.getCookies();
                         Map<String,String> cookie = new HashMap<String, String>();;
                         ok = cookies.size();
                                    for (int i = 0; i < cookies.size(); i++) {
                                        Log.e("response3","Local cookie: " +cookies.get(i).getName()+"|||"+cookies.get(i).getValue());
                                        
                                        intent.putExtra(cookies.get(i).getName(), cookies.get(i).getValue());
                                    }
					
				 
                return null;
            }

			@SuppressLint("NewApi")
			@Override
            protected void onPostExecute(String msg) {
				if (ok>0) {
					Editor editor = mSettings.edit();
					editor.putString(APP_PREFERENCES_LNAME, LNameInput.getText().toString().trim());
					editor.putString(APP_PREFERENCES_FNAME, FNameInput.getText().toString().trim());
					editor.putString(APP_PREFERENCES_PNAME, PInput.getText().toString().trim());
					editor.putString(APP_PREFERENCES_ZACH, NZachKnTxt.getText().toString().trim());
					editor.apply();
					startActivity(intent);
					} else {
					mSettings.edit().clear().commit();
					title.setText("НЕВЕРНЫЕ ДАННЫЕ!!! ПОПРОБУЙТЕ ЕЩЕ РАЗ!!!");
					}
            }
        }.execute(null, null, null);
        
       
     
}

}