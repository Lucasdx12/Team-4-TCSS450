package edu.uw.tcss450lucasd12.team_4_tcss450.Views.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import Helpers.WeatherService;
import edu.uw.tcss450lucasd12.team_4_tcss450.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    TextView mCurrentCity;
    TextView mCurrentTemp;
    TextView mCurrentWeather;
    TextView mCurrentHL;
    ImageView mCurrentWeatherIcon;
    RecyclerView mNotifications;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container,false);

        mCurrentCity = (TextView) view.findViewById(R.id.currentCity);
        mCurrentTemp = (TextView) view.findViewById(R.id.currentTemperature);
        mCurrentWeather = (TextView) view.findViewById(R.id.tempStyle);
        mCurrentHL = (TextView) view.findViewById(R.id.highAndLow);

//        WeatherService.getWeatherInfo(mCurrentCity, mCurrentTemp,mCurrentHL,mCurrentWeather, "Buckley");
        WeatherService.getWeatherInfo(mCurrentCity, mCurrentTemp, mCurrentHL, mCurrentWeather, "Tacoma", "Tacoma");


        return view;
    }
}