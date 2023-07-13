package com.weatherinfodisplay.datasource.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */

public class OpenWeatherMapClient {
    //https://api.openweathermap.org/data/2.5/weather?q=Dharwad&units=metric&appId=159e6a70a4f89f3540e51873ed4903cd
    private static final String BASE_URL = "https://api.openweathermap.org";
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
