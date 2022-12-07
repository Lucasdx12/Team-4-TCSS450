package Helpers;

import android.app.Application;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450lucasd12.team_4_tcss450.MainActivity;
import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentLandingBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentWeatherBinding;


public class WeatherService extends AndroidViewModel {

    //******************** Properties *****************************
    private final MutableLiveData<JSONObject> mResponse;

    private FragmentLandingBinding mBinding;

    //******************** Constructor *****************************

    public WeatherService(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    //********************  Methods *****************************

    public static void getWeatherInfo(TextView cityText, TextView tempText, TextView tempHighLowText, TextView weatherText, ImageView weatherIcon, String jwt) {

        // Properties
        String url = "https://tcss450-2022au-group4.herokuapp.com/weather";
        JSONObject body = new JSONObject();

        try {
            body.put("selectedCity", weatherIcon);
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

                    // Weather Icon
                    weatherIcon.setImageResource(getWeatherIcon(weatherIcon, weather));

                    String lowTempStr = jsonObject.getString("maxTemp");
                    tempHighLowText.setText("L:" + convertKelToFer(lowTempStr) + "  ");

                    // Get High
                    String highTempStr = jsonObject.getString("lowTemp");
                    tempHighLowText.setText(tempHighLowText.getText() + " H: " + convertKelToFer(highTempStr));



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

        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();

                // Add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.getContext());
        requestQueue.add(stringRequest);

    }

    public static void getForecast(FragmentWeatherBinding binding, String jwt) {

        String url = "https://tcss450-2022au-group4.herokuapp.com/forecast";
        JSONObject body = new JSONObject();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // Call the API
                try {

                    // Set the temp labels
                    JSONObject jsonObject = new JSONObject(response);
                    String tempStr =  "L:"  + convertKelToFer(jsonObject.getString("dayOneTempMin")) + " H: " + convertKelToFer(jsonObject.getString("dayOneTempMax"));
                    binding.tempDayOne.setText((tempStr));

                    tempStr =  "L:"  + convertKelToFer(jsonObject.getString("dayTwoTempMin")) + " H: " + convertKelToFer(jsonObject.getString("dayTwoTempMax"));
                    binding.tempDayTwo.setText((tempStr));

                    tempStr =  "L:"  + convertKelToFer(jsonObject.getString("dayThreeTempMin")) + " H: " + convertKelToFer(jsonObject.getString("dayThreeTempMax"));
                    binding.tempDayThree.setText((tempStr));

                    tempStr =  "L:"  + convertKelToFer(jsonObject.getString("dayFourTempMin")) + " H: " + convertKelToFer(jsonObject.getString("dayFourTempMax"));
                    binding.tempDayFour.setText((tempStr));

                    tempStr =  "L:"  + convertKelToFer(jsonObject.getString("dayFiveTempMin")) + " H: " + convertKelToFer(jsonObject.getString("dayFiveTempMax"));
                    binding.tempDayFive.setText((tempStr));

                    tempStr =  jsonObject.getString("dayOneWeather");
                    binding.dayOneIcon.setImageResource(getWeatherIcon(binding.dayOneIcon, tempStr));

                    tempStr =  jsonObject.getString("dayTwoWeather");
                    binding.dayTwoIcon.setImageResource(getWeatherIcon(binding.dayTwoIcon, tempStr));

                    tempStr =  jsonObject.getString("dayThreeWeather");
                    binding.dayThreeIcon.setImageResource(getWeatherIcon(binding.dayThreeIcon, tempStr));

                    tempStr =  jsonObject.getString("dayFourWeather");
                    binding.dayFourIcon.setImageResource(getWeatherIcon(binding.dayFourIcon, tempStr));

                    tempStr =  jsonObject.getString("dayFiveWeather");
                    binding.dayFiveIcon.setImageResource(getWeatherIcon(binding.dayFiveIcon, tempStr));

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

        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();

                // Add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.getContext());
        requestQueue.add(stringRequest);

    }

    public static void getHourlyForecast(FragmentWeatherBinding binding, String jwt) {

        String url = "https://tcss450-2022au-group4.herokuapp.com/hourlyForecast";
        JSONObject body = new JSONObject();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // Call the API
                try {

                    // Set the temp labels
                    JSONObject jsonObject = new JSONObject(response);

                    // Temp
                    String tempStr =  "H:"  + convertKelToFer(jsonObject.getString("slotOneTemp"));
                    binding.slotOneTemp.setText((tempStr));

                    tempStr =  "H:"  + convertKelToFer(jsonObject.getString("slotTwoTemp"));
                    binding.slotTwoTemp.setText((tempStr));

                    tempStr =  "H:"  + convertKelToFer(jsonObject.getString("slotThreeTemp"));
                    binding.slotThreeTemp.setText((tempStr));

                    tempStr =  "H:"  + convertKelToFer(jsonObject.getString("slotFourTemp"));
                    binding.slotFourTemp.setText((tempStr));

                    tempStr =  "H:"  + convertKelToFer(jsonObject.getString("slotFiveTemp"));
                    binding.slotFiveTemp.setText((tempStr));

                    // Time
                    tempStr =  jsonObject.getString("slotOneTime");
                    binding.slotOneTime.setText((convertTime(tempStr)));

                    tempStr =  jsonObject.getString("slotTwoTime");
                    binding.slotTwoTime.setText((convertTime(tempStr)));

                    tempStr =  jsonObject.getString("slotThreeTime");
                    binding.slotThreeTime.setText((convertTime(tempStr)));

                    tempStr =  jsonObject.getString("slotFourTime");
                    binding.slotFourTime.setText((convertTime(tempStr)));

                    tempStr =  jsonObject.getString("slotFiveTime");
                    binding.slotFiveTime.setText((convertTime(tempStr)));

                    // Icons
                    tempStr =  jsonObject.getString("slotOneWeather");
                    binding.slotOneWeather.setImageResource(getWeatherIcon(binding.slotOneWeather, tempStr));

                    tempStr =  jsonObject.getString("slotTwoWeather");
                    binding.slotTwoWeather.setImageResource(getWeatherIcon(binding.slotTwoWeather, tempStr));

                    tempStr =  jsonObject.getString("slotThreeWeather");
                    binding.slotThreeWeather.setImageResource(getWeatherIcon(binding.slotThreeWeather, tempStr));

                    tempStr =  jsonObject.getString("slotFourWeather");
                    binding.slotFourWeather.setImageResource(getWeatherIcon(binding.slotFourWeather, tempStr));

                    tempStr =  jsonObject.getString("slotFiveWeather");
                    binding.slotFiveWeather.setImageResource(getWeatherIcon(binding.slotFiveWeather, tempStr));

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

        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();

                // Add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.getContext());
        requestQueue.add(stringRequest);

    }



    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }

    //******************** Helpers *****************************

    // This method will convert a string in Kel into Fer.
    public static String convertKelToFer(String temp) {

        DecimalFormat df = new DecimalFormat("#0.00");
        Double num = (((Double.parseDouble(temp) - 273) * 9/5) + 32);
        return  df.format(num) + " °F";

    }

    public static String convertTime(String time) {

        Date date = new Date(Integer.parseInt(time) * 1000L);
        return (String) DateFormat.format("hh",   date);

    }

    public static int getWeatherIcon(ImageView currentWeather, String weather) {


        switch(weather) {
            case "fog":
                return R.drawable.fogicon;
            case "sun":
                return R.drawable.summericon;
            case "sunny":
                return R.drawable.summericon;
            case "snow":
                return R.drawable.snowicon;
            case "rain":
                return R.drawable.rainicon;
            case "raining":
                return R.drawable.rainicon;
            case "light rain":
                return R.drawable.rainicon;
            case "heavy rain":
                return R.drawable.rainicon;
            case "drizzle":
                return R.drawable.rainicon;
            case "haze":
                return R.drawable.hazeicon;
            case "wind":
                return R.drawable.windicon;
            case "clouds":
                return R.drawable.cloudicon;
            default:
                return R.drawable.partlycloudyicon;
        }
    }
}
