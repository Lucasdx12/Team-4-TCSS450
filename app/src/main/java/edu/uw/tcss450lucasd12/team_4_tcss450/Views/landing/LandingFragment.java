package edu.uw.tcss450lucasd12.team_4_tcss450.Views.landing;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


import edu.uw.tcss450lucasd12.team_4_tcss450.MainActivity;
import edu.uw.tcss450lucasd12.team_4_tcss450.R;

/**
 *
 */
public class LandingFragment extends Fragment {

    //******************** Properties *****************************

    TextView mcurrentCity;
    TextView mcurrentTemp;
    TextView mcurrentWeather;
    TextView mcurrentHL;
    ImageView mcurrentWeatherIcon;

    // Final
    private final String url = "https://api.openweathermap.org/data/2.5/weather?q={City name}}&appid=e14ade6fcbf627d2e5d2e4e88da1c03e";
    private final String appID = "e14ade6fcbf627d2e5d2e4e88da1c03e";

    // Formatter properties
    DecimalFormat decimalFormat = new DecimalFormat("#.##");


    //******************** Constructor *****************************

    /**
     *  This is the default constructor
     */
    public LandingFragment() {
        // Required empty public constructor
    }


    //******************** View Methods *****************************

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a reference to the UI Elements.
//        mcurrentCity = (TextView) getActivity().findViewById(R.id.currentCity);
//        mcurrentTemp = (TextView) getActivity().findViewById(R.id.currentTemp);
//        mcurrentWeather = (TextView) getActivity().findViewById(R.id.currentWeather);
//        mcurrentHL = (TextView) getActivity().findViewById(R.id.currentHL);
       // mcurrentWeatherIcon

        getWeatherInfo();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_landing, container,false);

        mcurrentCity = (TextView) myInflatedView.findViewById(R.id.currentCity);
        mcurrentCity = (TextView) myInflatedView.findViewById(R.id.currentCity);
        mcurrentTemp = (TextView) myInflatedView.findViewById(R.id.currentTemp);
        mcurrentWeather = (TextView) myInflatedView.findViewById(R.id.currentWeather);
        mcurrentHL = (TextView) myInflatedView.findViewById(R.id.currentHL);


        return inflater.inflate(R.layout.fragment_landing, container, false);
    }

    //********************  Methods *****************************

    public void getWeatherInfo() {

        // Properties
        String city = "Tacoma"; // TODO: Change to user location
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+ city + "&appid=e14ade6fcbf627d2e5d2e4e88da1c03e";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // Call the API
                try {

                    JSONObject jsonObject = new JSONObject(response);


                    // Get the country
//                    JSONObject object = jsonObject.getJSONObject("sys");
//                    String country =  object.getString("Country");
//                    // TODO: Set label

                    // Get City
                    String cityStr = jsonObject.getString("name");
                    // TODO: Set label
                    mcurrentCity.setText(cityStr);

                    // Get current Temp
                    JSONObject object2 = jsonObject.getJSONObject("main");
                    String tempStr = object2.getString("temp");
                    // TODO: Set label
                    mcurrentTemp.setText(tempStr);

                    // TODO: Figure out why it wont find the min and max
                    // Get Low
                    // TODO: Set label
//                    String lowTempStr = jsonObject.getString("temp_min");
//
//                    mcurrentHL.setText("L:" + lowTempStr + "  ");
//
//                    // Get High
//                    // TODO: Set label
//                    String highTempStr = jsonObject.getString("temp_max");
//                    mcurrentHL.setText(mcurrentHL.getText() + highTempStr);

                } catch (JSONException e) {
                    //e.printStackTrace();
                    System.out.println("Error: " + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: " + error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.getContext());
        requestQueue.add(stringRequest);

    }

    // TODO: Get the weather and adjust the icon
    public void setWeatherIcon(String currentWeather) {

    }




}