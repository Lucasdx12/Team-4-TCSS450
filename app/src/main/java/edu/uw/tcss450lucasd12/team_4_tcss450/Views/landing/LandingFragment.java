package edu.uw.tcss450lucasd12.team_4_tcss450.Views.landing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


import Helpers.ChatHelper;
import Helpers.WeatherService;
import edu.uw.tcss450lucasd12.team_4_tcss450.MainActivity;
import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.signin.SignInFragmentDirections;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatListFragment;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatListGenerator;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatListRecyclerViewAdapter;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatListBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentLandingBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;


/**
 * @Author Lucas Dahl
 */
public class LandingFragment extends Fragment {

    //******************** Properties *****************************
    private TextView mCurrentCity;
    private TextView mCurrentTemp;
    private TextView mCurrentWeather;
    private TextView mCurrentHL;
    private ImageView mCurrentWeatherIcon;
    private RecyclerView mNotifications;
    private UserInfoViewModel mUserModel;


    //******************** Constructor *****************************

    /**
     *  This is the default constructor
     */
    public LandingFragment() {
        // Required empty public constructor
    }

    //******************** View Methods *****************************


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_landing, container,false);

        mCurrentCity = (TextView) view.findViewById(R.id.currentCity);
        mCurrentTemp = (TextView) view.findViewById(R.id.currentTemp);
        mCurrentWeather = (TextView) view.findViewById(R.id.currentWeather);
        mCurrentHL = (TextView) view.findViewById(R.id.currentHL);
        mCurrentWeatherIcon = (ImageView) view.findViewById(R.id.weatherIcon);

        // Get weather
        WeatherService.getWeatherInfo(mCurrentCity, mCurrentTemp,mCurrentHL,mCurrentWeather, mCurrentWeatherIcon, mUserModel.getJwt());


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentLandingBinding binding = FragmentLandingBinding.bind(getView());

        ChatHelper chatHelper = new ChatHelper();

        binding.recentNotifications.setAdapter(new LandingRecyclerViewAdapter(chatHelper.getChatList()));
    }

    //********************  Methods *****************************

}