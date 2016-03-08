package com.example.choirulhuda.Process;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

/**
 * Created by choirul.huda on 29/02/2016.
 */
public class CallAsync extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        StringBuffer childObjectBuffer = new StringBuffer();



        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
