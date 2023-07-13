package com.weatherinfodisplay.datasource.network;


import com.weatherinfodisplay.datasource.model.currentweather.CurrentWeather;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 */

public interface OpenWeatherMapService {

    String CURRENT = "/data/2.5/weather";

    //Current Weather Endpoints start
    @GET(CURRENT)
    Call<CurrentWeather> getCurrentWeatherByCityName(@QueryMap Map<String, String> options);



}
