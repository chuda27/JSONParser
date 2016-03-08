package com.example.generator;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MainClass {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.example.choirulhuda.model"); //targer package

        //tabel City
        Entity city = schema.addEntity("City");
        city.addIdProperty().autoincrement();
        city.addStringProperty("name");
        city.addStringProperty("state");
        city.addStringProperty("description");

        //tabel Movies
        /*Entity movie = schema.addEntity("Movie");
        movie.addIdProperty();
        movie.addStringProperty("movie");
        movie.addIntProperty("year");
        movie.addFloatProperty("rating");
        movie.addStringProperty("duration");
        movie.addStringProperty("director");
        movie.addStringProperty("tagline");
        movie.addStringProperty("castLine");
        movie.addStringProperty("image");
        movie.addStringProperty("story");*/

        //digenerate ke folder tujuan
        DaoGenerator dg = new DaoGenerator();
        dg.generateAll(schema, "./app/src/main/java");

    }
}
