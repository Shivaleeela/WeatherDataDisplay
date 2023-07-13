package com.weatherinfodisplay.weatherdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.weatherinfodisplay.datasource.constant.Languages;
import com.weatherinfodisplay.datasource.constant.Units;
import com.weatherinfodisplay.datasource.implementation.OpenWeatherMapHelper;
import com.weatherinfodisplay.datasource.implementation.callback.CurrentWeatherCallback;
import com.weatherinfodisplay.datasource.model.currentweather.CurrentWeather;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    EditText city_et;
    ImageButton search_BT;
    TextView weatherdesc, temp, wind, humidity, pressure;
    LinearLayout weatherdetails_LL;
    SharedPreferences SharedPreference_Object;
    String str_city = "", saved = "";
    public static final String sharedpreferencebook = "sharedpreferencebook";
    OpenWeatherMapHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreference_Object = getSharedPreferences(sharedpreferencebook, Context.MODE_PRIVATE);
        str_city = SharedPreference_Object.getString("mykey", "").trim();


        weatherdetails_LL = findViewById(R.id.weatherdetails_LL);
        weatherdesc = findViewById(R.id.weatherdesc);

        temp = findViewById(R.id.temp);
        wind = findViewById(R.id.wind);


        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        city_et = findViewById(R.id.searchcity_et);
        search_BT = (ImageButton) findViewById(R.id.searchbutton);

        helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));

        //Set Units
        helper.setUnits(Units.METRIC);

        //Set Languages
        helper.setLanguage(Languages.ENGLISH);
        if (str_city.equals("")) {
            Log.e("str_city", "null");
        } else {
            Log.e("str_city", str_city);
            ApiCall();
        }

        if (city_et.getText().toString().length() == 0) {
            weatherdetails_LL.setVisibility(View.GONE);

        } else {
            saved = city_et.getText().toString();
            SharedPreference_Object.edit().putString("mykey", saved).apply();

            weatherdetails_LL.setVisibility(View.VISIBLE);
        }
        search_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiCall();
            }
        });
    }

    public void ApiCall() {
        helper.getCurrentWeatherByCityName(city_et.getText().toString(), new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                if (city_et.getText().toString().equals("") || city_et.getText().toString() == null || city_et.getText().toString().length() == 0) {
                    weatherdetails_LL.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Please enter city name to search", Toast.LENGTH_SHORT).show();
                } else {
                    saved = city_et.getText().toString();
                    SharedPreference_Object.edit().putString("mykey", saved).apply();

                    weatherdetails_LL.setVisibility(View.VISIBLE);
                    weatherdesc.setText("Weather Description: " + currentWeather.getWeather().get(0).getDescription());
                    temp.setText("Temperature: " + currentWeather.getMain().getTempMax());
                    wind.setText("Wind Speed: " + currentWeather.getWind().getSpeed());
                    humidity.setText("Humidity: " + currentWeather.getMain().getHumidity());
                    pressure.setText("Pressure: " + currentWeather.getMain().getPressure());


                    Log.v(TAG,
                            "Coordinates: " + currentWeather.getCoord().getLat() + ", " + currentWeather.getCoord().getLon() + "\n"
                                    + "Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                                    + "Temperature: " + currentWeather.getMain().getTempMax() + "\n"
                                    + "Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                                    + "Main,feelslike: " + currentWeather.getMain().getFeelsLike() + ", " + currentWeather.getMain().getTempKf()
                    );
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                weatherdetails_LL.setVisibility(View.GONE);
                Log.v(TAG, throwable.getMessage());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        saved = SharedPreference_Object.getString("mykey", null);
        if (saved == null) {
            Log.e("saved", "null");
        } else {
            city_et.setText(saved);
            ApiCall();
            Log.e("pojo city ", saved);
        }
    }

    @Override
    protected void onStop() {
        SharedPreference_Object.edit().putString("mykey", saved).apply();
        super.onStop();
    }

    @Override
    protected void onPause() {
        SharedPreference_Object.edit().putString("mykey", saved).apply();
        super.onPause();
    }
}