package com.weatherinfodisplay.datasource.implementation.callback;


import com.weatherinfodisplay.datasource.model.currentweather.CurrentWeather;

public interface CurrentWeatherCallback{
    void onSuccess(CurrentWeather currentWeather);
    void onFailure(Throwable throwable);
}
