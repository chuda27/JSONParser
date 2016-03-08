package com.example.choirulhuda.jsonparser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choirulhuda.model.City;
import com.example.choirulhuda.model.CityDao;
import com.example.choirulhuda.model.DaoMaster;
import com.example.choirulhuda.model.DaoSession;
import com.google.gson.Gson;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by choirul.huda on 24/02/2016.
 */
public class CityActivity extends Activity implements View.OnClickListener {

    TextView tv_cityID;
    TextView tv_cityName;
    TextView tv_cityState;
    TextView tv_cityDes;
    Button btn_citySubmit;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;
    private ProgressDialog dialog;

    private CityDao cityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citydao);

        tv_cityID = (TextView) findViewById(R.id.et_city_id);
        tv_cityName = (TextView) findViewById(R.id.et_city_name);
        tv_cityState = (TextView) findViewById(R.id.et_city_state);
        tv_cityDes = (TextView) findViewById(R.id.et_city_des);
        btn_citySubmit = (Button) findViewById(R.id.btn_city_submit);

        btn_citySubmit.setOnClickListener(this);

        //create db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "city-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        //progress dialog
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, Please Wait..");

    }

    @Override
    public void onClick(View v) {
        if(v == btn_citySubmit){
            addCity();
            new LoadCity().execute("http://beta.json-generator.com/api/json/get/GAqnlDN");
            Toast.makeText(getApplicationContext(), "Add new City Success", Toast.LENGTH_SHORT).show();
        }
    }

    //insert to table
    private void addCity(){
        String cityName = "";
        String cityState = "";
        String cityDes = "";


        cityName = tv_cityName.getText().toString();
        cityState = tv_cityState.getText().toString();
        cityDes = tv_cityDes.getText().toString();

        City city = new City(null, cityName, cityState, cityDes);
        cityDao = daoSession.getCityDao();
        cityDao.insert(city);
    }

    public class LoadCity extends AsyncTask<String, String, List<City>> {
        String finalJson = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<City> doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            StringBuffer buffer = new StringBuffer();

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
                List<City> listCity = new ArrayList<>();
                Gson gson = new Gson();

                for (int i = 0; i < parentArray.length(); i++){

                    //buat ngambil object dr arraynya
                    JSONObject childObject = parentArray.getJSONObject(i);
                    City city = gson.fromJson(childObject.toString(), City.class);

                    cityDao.insert(city);
                    listCity.add(city);
                }
                return listCity;

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
            return null;
        }

        @Override
        protected void onPostExecute(List<City> s) {
            super.onPostExecute(s);
            dialog.dismiss();
        }
    }
}