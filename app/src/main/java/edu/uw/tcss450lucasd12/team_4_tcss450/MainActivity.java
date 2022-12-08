package edu.uw.tcss450lucasd12.team_4_tcss450;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.app.Activity;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatListViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoom;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoomViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.ActivityMainBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatRoomBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.NewMessageCountViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.PushyTokenViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.services.PushReceiver;

public class MainActivity extends AppCompatActivity {

    // For Pushy (Notifications) to the user.
    private MainPushMessageReceiver mPushMessageReceiver;

    private NewMessageCountViewModel mNewMessageModel;

    //for bottom navigation implementation:
    private AppBarConfiguration mAppBarConfiguration;
    private static Context context;

    Handler mHandler; //use to refresh activity to see change in themes



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())
        ).get(UserInfoViewModel.class);

        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class);

        // For volley request
        context = getApplicationContext();


        setContentView(R.layout.activity_main);

        this.mHandler = new Handler();
//        m_Runnable.run();

        //adding back button in app bar (top of app):
//        ActionBar actionBar = getSupportActionBar(); //call action bar
//        actionBar.setDisplayHomeAsUpEnabled(true); //TODO: shows back button

        //Bottom navigation:
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.landing, R.id.weather, R.id.chat, R.id.contacts,R.id.ChangePassword)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        messageCount(navView, navController);

        //Action Bar (bar at top with settings and activity names):
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#acc8d7"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //Status Bar (where notifications are):
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar, null));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //Adding settings options in top right corner of app's screen:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        //Action Bar (bar at top with settings and activity names):
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#acc8d7"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //TODO: this currently crashes the app, investigate to refresh activity to show theme change
//        MenuItem item = menu.findItem(R.id.theme_picker);
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });

        return true;
    }


    //Let user click the settings options:
    //TODO: add options in the settings menu
    @SuppressLint("ResourceType")
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
                        this.recreate();
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.Summer);
                        this.recreate();
                    }
                    return true;

                case R.id.uw_theme:

                    if (item.isChecked()) {
                        item.setChecked(false);
                        this.recreate();
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.UW);
                        this.recreate();
                    }

                    return true;

                case R.id.fall_theme:

                    if (item.isChecked()) {
                        item.setChecked(false);
                        this.recreate();
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.Fall);
                        this.recreate();

                    }
                    return true;

                case R.id.winter_theme:

                    if (item.isChecked()) {
                        item.setChecked(false);
                        this.recreate();
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.Winter);
                        this.recreate();
                    }
                    refreshPage();
                    return true;

                case R.id.spring_theme:
                    if (item.isChecked()) {
                        item.setChecked(false);
                        this.recreate();
                    } else {
                        item.setChecked(true);
                        setTheme(R.style.Spring);
                        this.recreate();
                    }
                    return true;
                case R.id.log_out: //TODO: make log out button for user
                    signOut();
                    return true;

                case R.id.ChangePassword:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

    }

    private void signOut() {
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);

        prefs.edit().remove(getString(R.string.keys_prefs_jwt)).apply();
        PushyTokenViewModel model = new ViewModelProvider(this)
                .get(PushyTokenViewModel.class);

        // When we hear back from the web service quit
        model.addResponseObserver(this, result -> finishAndRemoveTask());

        model.deleteTokenFromWebservice(
                new ViewModelProvider(this)
                        .get(UserInfoViewModel.class)
                        .getJwt()
        );
    }

    private void refreshPage() {
        finish();
        startActivity(getIntent());
    }
    private void messageCount(BottomNavigationView navView, NavController navController) {

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.chatRoomFragment) {
                // When the user navigates to the chats page, reset the new message count.
                // This will need some extra logic for the project as it should have
                // multiple chat rooms.
                mNewMessageModel.reset();
            }
        });

        mNewMessageModel.addMessageCountObserver(this, count -> {
            BadgeDrawable badge = navView.getOrCreateBadge(R.id.chat);
            badge.setMaxCharacterCount(10);
            if (count > 0) {
                // New messages!!! Update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                // User did some action to clear the new messages, remove the badge.
                badge.clearNumber();
                badge.setVisible(false);
            }
        });
    }

    //Refresh page. SOURCE: https://stackoverflow.com/questions/6134832/auto-refresh-the-activity
    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            Toast.makeText(MainActivity.this,"Refreshing",Toast.LENGTH_SHORT).show();

            MainActivity.this.mHandler.postDelayed(m_Runnable,5000);
        }
    };

    // For Volley request
    public static Context getContext() {
        return context;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPushMessageReceiver == null) {
            mPushMessageReceiver = new MainPushMessageReceiver();
        }
        IntentFilter intentFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        registerReceiver(mPushMessageReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null) {
            unregisterReceiver(mPushMessageReceiver);
        }
    }

    /**
     * A BroadcastReceiver that listens for messages sent from PushReceiver
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {
        private ChatRoomViewModel mModel = new ViewModelProvider(MainActivity.this).get(ChatRoomViewModel.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            NavController navController =
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);

            NavDestination navDestination = navController.getCurrentDestination();

            if (intent.hasExtra("chatMessage")) {

                ChatRoom chatRoom = (ChatRoom) intent.getSerializableExtra("chatMessage");

                // If the user is not on the chat screen, update the
                // NewMessageCountView Model
                if (navDestination.getId() != R.id.chatRoomFragment) {
                    mNewMessageModel.increment();
                }

                // Inform the view model holding chatroom messages of the new
                // message.
                mModel.addMessage(intent.getIntExtra("chatid", -1), chatRoom);
            }
        }
    }

}