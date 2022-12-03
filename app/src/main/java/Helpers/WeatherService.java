package Helpers;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450lucasd12.team_4_tcss450.MainActivity;
import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatListBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentLandingBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentWeatherBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.io.RequestQueueSingleton;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;


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

    public static void getWeatherInfo(TextView cityText, TextView tempText, TextView tempHighLowText, TextView weatherText, String city, String jwt) {

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


        String url = "https://tcss450-team4-webservice.herokuapp.com/forecast";
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

    // TODO: Get the weather and adjust the icon
    public void setWeatherIcon(String currentWeather) {

    }


//    public void getWeatherData(final String jwt) {
//        String url = "https://tcss450-team4-webservice.herokuapp.com/weather";
//
//        Request request = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                null, // No body for this get request
//                null,
//                this::handleError) {
//
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//
//                // Add headers <key,value>
//                headers.put("Authorization", jwt);
//                return headers;
//            }
//        };
//
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                10_000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        // Instantiate the RequestQueue and add the request to the queue.
//        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
//                .addToRequestQueue(request);
//
//    }

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
}
