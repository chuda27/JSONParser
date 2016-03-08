package com.example.choirulhuda.jsonparser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends Activity implements View.OnClickListener{

    TextView hasil_json;
    Button btn_tampil;
    Button btn_tampil_cities;
    Button btn_movie;
    Button btn_cityDao;
    Button btn_timer;
    Button btn_ke_timer;

    public int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasil_json = (TextView) findViewById(R.id.text_json);
        btn_tampil = (Button) findViewById(R.id.btn_tampil);
        btn_tampil_cities = (Button) findViewById(R.id.btn_tampil2);
        btn_movie = (Button) findViewById(R.id.btn_to_movie);
        btn_cityDao = (Button) findViewById(R.id.btn_cityDao);
        btn_timer =(Button) findViewById(R.id.btn_timer);
        btn_ke_timer = (Button) findViewById(R.id.btn_ke_timer);

        btn_tampil.setOnClickListener(this);
        btn_tampil_cities.setOnClickListener(this);
        btn_movie.setOnClickListener(this);
        btn_cityDao.setOnClickListener(this);
        btn_timer.setOnClickListener(this);
        btn_ke_timer.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
        //city
        /*String url = "http://beta.json-generator.com/api/json/get/GAqnlDN";*/

        /*String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";*/

        /*Buat Http Request nya*/
    public class JsonTask extends AsyncTask<String, String, String>{

            String finalJson = "";
            String cityName = "";
            String cityState = "";
            String cityDes = "";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog();
            }

            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection conn = null;
                BufferedReader reader = null;
                StringBuffer buffer = new StringBuffer();
                StringBuffer childObjectBuffer = new StringBuffer();

                try {
                    String line = "";


                    URL url = new URL(params[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    /*simpan ke dalam stream*/
                    InputStream stream = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    while ((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    finalJson = buffer.toString();
                    JSONObject parentObject = new JSONObject(finalJson);
                    JSONArray parentArray = parentObject.getJSONArray("cities");

                    for (int i = 0; i < parentArray.length(); i++){

                        //buat ngambil object dr arraynya
                        JSONObject childObject = parentArray.getJSONObject(i);

                        cityName = childObject.getString("name");
                        cityState = childObject.getString("state");
                        cityDes = childObject.getString("description");
                        childObjectBuffer.append("Name : " +cityName+"\nState : "+cityState+"\nDes : "+cityDes+"\n\n");
                    }

                    /*nilai return buat onPostExecute()*/
                    return childObjectBuffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    /*jgn lupa tutup koneksinya*/
                    if (conn != null){
                        conn.disconnect();
                    }
                    try {
                        if (reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return  null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                hasil_json.setText(s);
            }
        }


    private void json_Employee(){
        /*Menampilkan data JSON*/
        String jsonnya = "{\"Employee\" :[{\"id\":\"01\",\"name\":\"Gopal Varma\",\"salary\":\"500000\"},{\"id\":\"02\",\"name\":\"Sairam Krishna\",\"salary\":\"500000\"},{\"id\":\"03\",\"name\":\"Sathish kallakuri\",\"salary\":\"600000\"}]}";
        String hasil = "";
        try {
            JSONObject jsonData = new JSONObject(jsonnya);
                /*intansiasi JSON Array dr JSONObject*/
            JSONArray jsonArray = jsonData.optJSONArray("Employee");

                /*tampilkan jsonObject*/
            for (int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                    /*Ambil value dari key pada JSON*/
                int id = Integer.parseInt(jsonObject.optString("id").toString());
                String name = jsonObject.optString("name").toString();
                Double salary = Double.parseDouble(jsonObject.optString("salary").toString());

                hasil += "Baris ke-"+i+": " +
                        " \n ID = "+id+
                        " \n Name : "+name+
                        " \n Salary : "+salary+"\n";
            }
            hasil_json.setText(hasil);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void progressDialog(){
        ProgressDialog progDialog = new ProgressDialog(this);
        progDialog.setTitle("Loading..");
        progDialog.setMessage("Please Wait");
        progDialog.show();
        progDialog.dismiss();
    }

    public void SetTimer(){
        i = 0 ;
        new CountDownTimer(30000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                hasil_json.setText("second remaining : "+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                hasil_json.setText("Nilai i = "+i);
                json_Employee();
            }
        }.start();

    }

    @Override
    public void onClick(View view) {
        if (view == btn_tampil){
            json_Employee();
        }else if (view == btn_tampil_cities){
            new JsonTask().execute("http://beta.json-generator.com/api/json/get/GAqnlDN");
        }else if(view == btn_movie){
            Intent intent = new Intent(MainActivity.this, MoviesActivity.class);
            startActivity(intent);
        }else if(view == btn_cityDao){
            Intent intent = new Intent(this, CityActivity.class);
            startActivity(intent);
        }else if(view == btn_timer){
            SetTimer();
        }else if (view == btn_ke_timer){
            Intent intent = new Intent(this, RecorderActivity.class);
            startActivity(intent);
        }
    }
}
