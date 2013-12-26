package com.mrZizik.markbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class SubjectsListActivity extends Activity {

	private HashMap<String, String> cookie;
	private final ArrayList<Subject> subjects = new ArrayList<Subject>();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subjects_list);
		cookie = new HashMap<String,String>();
		cookie.put("ASP.NET_SessionId", getIntent().getStringExtra("ASP.NET_SessionId"));
		cookie.put("StudentsAuth", getIntent().getStringExtra("StudentsAuth"));
		signInBackground();
		
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subjects_list, menu);
		return true;
	}
	
	
	private void signInBackground() {
        new AsyncTask<Void, Void, String>() {

			

			private SubjectsAdapter adapter;
			private ListView lv;

			@Override
            protected String doInBackground(Void... params) {
				Log.i("response","Beginning");
				Response res2;
				
					try {
						res2 = Jsoup
						        .connect("http://isu.dgu.ru/Students/stat.aspx")
						        .header("Content-Type","application/x-www-form-urlencoded")
						        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
						        .header("Accept-Encoding", "gzip,deflate,sdch")
						        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:26.0) Gecko/20100101 Firefox/26.0")
						        .header("Accept-Language", "ru,en;q=0.8")
						        .header("Referer", "http://isu.dgu.ru/Students/login.aspx?cookieCheck=true")
						        .header("Host", "isu.dgu.ru")
						        //.header("Connection", "keep-alive")
						        //.header("Content-Length", "356")
						        .cookies(cookie)
						        .method(Method.GET)
						        .execute();
								Document doc = res2.parse();
								subjects.add(
										new Subject(
												"Название предмета:",
												"Мод. 1",
												"Мод. 2",
												"Мод. 3",
												"Мод. 4",
												"Курсовая",
												"Зачет",
												"Экзамен"
												)
										);
								for (int i=0;i<doc.select("table#ContentPlaceHolder1_ModGrid > tbody > tr > td:nth-child(1)").size()-2;i++) {
									subjects.add(
											new Subject(
													doc.select("table#ContentPlaceHolder1_ModGrid > tbody > tr > td:nth-child(1)").get(i+1).text(),
													doc.select("a#ContentPlaceHolder1_ModGrid_Mod1Lnk_"+i).text()+" ",
													doc.select("a#ContentPlaceHolder1_ModGrid_Mod2Lnk_"+i).text()+" ",
													doc.select("a#ContentPlaceHolder1_ModGrid_Mod3Lnk_"+i).text()+" ",
													doc.select("a#ContentPlaceHolder1_ModGrid_Mod4Lnk_"+i).text()+" ",
													doc.select("a#ContentPlaceHolder1_ModGrid_CMarkLnk_"+i).text()+" ",
													doc.select("a#ContentPlaceHolder1_ModGrid_ZMarkLnk_"+i).text()+" ",
													doc.select("a#ContentPlaceHolder1_ModGrid_EMarkLnk_"+i).text()+" "
													)
											);
									Log.e("response3",doc.select("table#ContentPlaceHolder1_ModGrid > tbody > tr > td:nth-child(1)").get(i+1).text());
									Log.e("response3",doc.select("a#ContentPlaceHolder1_ModGrid_Mod1Lnk_"+i).text());
									Log.e("response3",doc.select("a#ContentPlaceHolder1_ModGrid_ZMarkLnk_"+i).text());
								}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 
                return null;
            }

            @Override
            protected void onPostExecute(String msg) {
                adapter = new SubjectsAdapter(SubjectsListActivity.this, R.id.listView1, subjects);
                lv = (ListView)findViewById(R.id.listView1);
                lv.setAdapter(adapter);
            }
        }.execute(null, null, null);
	}
	
	protected String formatString(String str, int position) {
		        final StringBuffer buffer = new StringBuffer(position);
		        final int to = position - str.length();
		        buffer.append(str);
		        for (int i = 0; i < to; i++) {
		            buffer.append(" ");
		        }
		        return buffer.toString();
		    }
		

}
