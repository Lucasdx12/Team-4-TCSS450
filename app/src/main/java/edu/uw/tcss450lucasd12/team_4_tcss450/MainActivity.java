package edu.uw.tcss450lucasd12.team_4_tcss450;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

public class MainActivity extends AppCompatActivity {
    //for bottom navigation implementation:
    private AppBarConfiguration mAppBarConfiguration;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())
        ).get(UserInfoViewModel.class);

        // For volley request
        context = getApplicationContext();

        setTheme(R.style.Theme_Team4TCSS450);
        setContentView(R.layout.activity_main);

        //adding back button in app bar (top of app):
//        ActionBar actionBar = getSupportActionBar(); //call action bar
//        actionBar.setDisplayHomeAsUpEnabled(true); //TODO: shows back button

        //Bottom navigation:
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.landing, R.id.weather, R.id.chat, R.id.contacts)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //Adding settings options in top right corner of app's screen:
    //TODO: add options in the settings menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    //Let user click the settings options:
    //TODO: add options in the settings menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Spinner spinTheme = (Spinner) findViewById(R.id.theme_picker);

            switch (item.getItemId()) {
                case R.id.action_settings:
                    Log.d("SETTINGS", "Clicked");
                    openOptionsMenu();
                    return true;
                case R.id.theme_picker:
                case R.id.summer_theme:

                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.Summer);
                    }
                    return true;

                case R.id.uw_theme:

                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.UW);
                    }
                    return true;

                case R.id.fall_theme:

                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.Fall);
                    }
                    return true;

                case R.id.winter_theme:

                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.Winter);
                    }
                    return true;

                case R.id.spring_theme:
                    if (item.isChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.Spring);
                    }
                    return true;
//                case R.id.log_out: //TODO: make log out button for user
                default:
                    return super.onOptionsItemSelected(item);
            }

    }

    // For Volley request
    public static Context getContext() {
        return context;
    }

}