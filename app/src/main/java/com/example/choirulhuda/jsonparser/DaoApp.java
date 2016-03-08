package com.example.choirulhuda.jsonparser;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.choirulhuda.model.CityDao;
import com.example.choirulhuda.model.DaoMaster;
import com.example.choirulhuda.model.DaoSession;

/**
 * Created by choirul.huda on 24/02/2016.
 */
public class DaoApp extends Application {

    public static CityDao cityDao;

    public static CityDao getCityDao(){
        return cityDao;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "city-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        cityDao = daoSession.getCityDao();
    }
}
