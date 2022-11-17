package Helpers;

import android.icu.text.NumberFormat;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import edu.uw.tcss450lucasd12.team_4_tcss450.MainActivity;

public class WeatherService {

    //******************** Properties *****************************

    //******************** Constructor *****************************

    public WeatherService() {

    }

    //********************  Methods *****************************


    public static void getWeatherInfo(TextView cityText, TextView tempText, TextView tempHighLowText, TextView weatherText, String city) {


        // Properties
//        String url = "https://tcss450-team4-webservice.herokuapp.com/weather?selectedCity=" + city;
        String url = "https://tcss450-team4-webservice.herokuapp.com/weather";

        JSONObject body = new JSONObject();

        try {
            body.put("selectedCity", city);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // Call the API
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    // Get City
                    String cityStr = jsonObject.getString("city");
                    cityText.setText(cityStr);

                    // Get current Temp
                    String tempStr = jsonObject.getString("CurrentTemp");
                    tempText.setText(convertKelToFer(tempStr));


                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject currentWeather = jsonArray.getJSONObject(0);
                    String weather  = currentWeather.getString("main");
                    weatherText.setText(weather);

                    String lowTempStr = jsonObject.getString("maxTemp");
                    tempHighLowText.setText("L:" + convertKelToFer(lowTempStr) + "  ");

                    // Get High
                    String highTempStr = jsonObject.getString("lowTemp");
                    tempHighLowText.setText(tempHighLowText.getText() + convertKelToFer(highTempStr));

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

    // This method will convert a string in Kel into Fer.
    public static String convertKelToFer(String temp) {

        DecimalFormat df = new DecimalFormat("#0.00");
        Double num = (((Double.parseDouble(temp) - 273) * 9/5) + 32);
        return  df.format(num) + " °F";

    }

}