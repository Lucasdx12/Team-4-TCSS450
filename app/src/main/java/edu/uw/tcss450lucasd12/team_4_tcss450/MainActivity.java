package edu.uw.tcss450lucasd12.team_4_tcss450;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //for bottom navigation implementation:
    private AppBarConfiguration mAppBarConfiguration;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // For volley request
        context = getApplicationContext();

        setContentView(R.layout.activity_main);

        //adding back button in app bar (top of app):
//        ActionBar actionBar = getSupportActionBar(); //call action bar
//        actionBar.setDisplayHomeAsUpEnabled(true); //TODO: shows back button

        //Bottom navigation:
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.landing, R.id.weather, R.id.chat, R.id.search)
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
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //Let user click the settings options:
    //TODO: add options in the settings menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) { //settings button
            //TODO open a settings fragment
            Log.d("SETTINGS", "Clicked");
            return true;
        }
//        else if (id == android.R.id.home) { //TODO: back button
//            this.finish();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    // For Volley request
    public static Context getContext() {
        return context;
    }

}