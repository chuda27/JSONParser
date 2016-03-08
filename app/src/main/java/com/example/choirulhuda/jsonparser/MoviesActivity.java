package com.example.choirulhuda.jsonparser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
 * Created by choirul.huda on 17/02/2016.
 */
public class MoviesActivity extends Activity{

    ProgressDialog dialog;
    ListView lv_Movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //progress dialog
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, Please Wait..");


        lv_Movie = (ListView) findViewById(R.id.lv_movies);

        /*Loader Image Universal*/
        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh){
            new JsonMovies().execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class JsonMovies extends AsyncTask<String, String, List<MoviesModel>>{

        String movieName = "";
        int movieYear = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List doInBackground(String... params) {

            HttpURLConnection conn;
            BufferedReader reader;
            StringBuffer buffer = new StringBuffer();
            StringBuffer childObjectBuffer = new StringBuffer();
            String line = "";
            String finalJson = "";
            URL url;
            InputStream stream;


            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                stream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                finalJson = buffer.toString();

                //String dr buffer, akan dipilah ke JSONObject & JSONArray
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");

                /*karena filmnya byk*/
                List<MoviesModel> moviesModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++){
                    JSONObject childObject = parentArray.getJSONObject(i);
                    /*masukkan ke dalam modelnya pake gson*/
                    MoviesModel moviesModel = gson.fromJson(childObject.toString(), MoviesModel.class);

                    /*masukkan ke dalam modelnya manual*/
                    /*MoviesModel moviesModel = new MoviesModel();
                    moviesModel.setMovie(childObject.getString("movie"));
                    moviesModel.setYear(childObject.getInt("year"));
                    moviesModel.setRating((float) childObject.getDouble("rating"));
                    moviesModel.setDuration(childObject.getString("duration"));
                    moviesModel.setDirector(childObject.getString("director"));
                    moviesModel.setTagline(childObject.getString("tagline"));
                    moviesModel.setImage(childObject.getString("image"));
                    moviesModel.setStory(childObject.getString("story"));

                    List<MoviesModel.Cast> castList = new ArrayList<>();
                    for (int j = 0; j<childObject.getJSONArray("cast").length(); j++){
                        MoviesModel.Cast cast = new MoviesModel.Cast();
                        cast.setName(childObject.getJSONArray("cast").getJSONObject(j).getString("name"));

                        castList.add(cast);
                    }
                    moviesModel.setCastList(castList);*/
                    /*masukkan hasil JSON ke dalam list*/
                    moviesModelList.add(moviesModel);
                }

                return moviesModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<MoviesModel> movieResult) {
            super.onPostExecute(movieResult);
            dialog.dismiss();
            MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), R.layout.row, movieResult);
            lv_Movie.setAdapter(movieAdapter);

        }
    }

    public class MovieAdapter extends ArrayAdapter {

        private List<MoviesModel> movieModelList;
        private int resources ;
        private LayoutInflater inflater;

        /*objectnya ngambil dr movie*/
        public MovieAdapter(Context context, int resource, List<MoviesModel> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.resources = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE); //ngambil dr activity
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                /*mengembalikan semua nilai dr row.xml*/
                convertView = inflater.inflate(resources, null);

                ImageView img_Movie;
                TextView tv_Movie_Name;
                TextView tv_Tagline;
                TextView tv_Year;
                TextView tv_Duration;
                TextView tv_Director;
                TextView tv_Cast;
                TextView tv_Story;
                RatingBar rb_Movie;

                img_Movie = (ImageView)convertView.findViewById(R.id.img_movie);
                tv_Movie_Name = (TextView)convertView.findViewById(R.id.tv_movie_name);
                tv_Tagline = (TextView)convertView.findViewById(R.id.tv_tagline);
                tv_Year = (TextView)convertView.findViewById(R.id.tv_year);
                tv_Duration = (TextView)convertView.findViewById(R.id.tv_duration);
                tv_Director = (TextView)convertView.findViewById(R.id.tv_director);
                tv_Cast = (TextView)convertView.findViewById(R.id.tv_cast);
                tv_Story = (TextView)convertView.findViewById(R.id.tv_story);
                rb_Movie = (RatingBar)convertView.findViewById(R.id.rb_movie);

                // Then later, when you want to display image
                ImageLoader.getInstance().displayImage(movieModelList.get(position).getImage(), img_Movie);

                //TextView
                tv_Movie_Name.setText(movieModelList.get(position).getMovie());
                tv_Tagline.setText(movieModelList.get(position).getTagline());
                tv_Year.setText("Year : "+movieModelList.get(position).getYear());
                tv_Duration.setText("Duration : "+movieModelList.get(position).getDuration());
                tv_Director.setText("Director : "+movieModelList.get(position).getDirector());

                /*buat nampung cast*/
                StringBuffer stringBuffer = new StringBuffer();
                for (MoviesModel.Cast cast : movieModelList.get(position).getCastList()){
                    stringBuffer.append(cast.getName()+", ");
                }
                tv_Cast.setText("Cast : "+stringBuffer);
                tv_Story.setText(movieModelList.get(position).getStory());

                //RatingBar
                rb_Movie.setRating(movieModelList.get(position).getRating()/2);


            }

            return convertView;
        }
    }
}


