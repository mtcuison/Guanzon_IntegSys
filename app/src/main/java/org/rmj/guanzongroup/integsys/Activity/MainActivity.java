package org.rmj.guanzongroup.integsys.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.CoMaker.Fragment_Requirements;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.DatabaseExport;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.guanzongroup.g3msg_notifylib.Fragments.Fragment_NotificationList;
import org.rmj.guanzongroup.integsys.Dialogs.MessageDialog;
import org.rmj.guanzongroup.integsys.Fragment.MainMenu.Fragment_DashBoard;
import org.rmj.guanzongroup.integsys.Objects.CameraInstance;
import org.rmj.guanzongroup.integsys.R;
import org.rmj.guanzongroup.integsys.Services.CameraActionReceiver;
import org.rmj.guanzongroup.integsys.Services.ConnectionStatusReceiver;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;

    private CameraActionReceiver receiver = new CameraActionReceiver();
    private ConnectionStatusReceiver connReceiver = new ConnectionStatusReceiver();

    private int[] TabIconsToogled = {
            R.drawable.ic_dashboard_tab_menu_icon,
            R.drawable.ic_dashboard_tab_notification_icon};

    private int[] TabIcons = {
            R.drawable.ic_dashboard_tab_menu,
            R.drawable.ic_dashboard_tab_notification
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        setupWidgets();
    }

    @Override
    protected void onStart() {
        IntentFilter cameraFilter = new IntentFilter("org.rmj.Camera_Instance");
        registerReceiver(receiver, cameraFilter);
        IntentFilter serviceFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connReceiver, serviceFilter);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        unregisterReceiver(connReceiver);
        super.onDestroy();
    }

    private void setupWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_mainMenu);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        ViewPager viewPager = findViewById(R.id.viewPager_mainMenu);
        TabLayout tabLayout = findViewById(R.id.tablayout_mainMenu);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_DashBoard());
        adapter.addFragment(new Fragment_NotificationList());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(TabIconsToogled[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(TabIcons[1]);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Objects.requireNonNull(tabLayout.getTabAt(tab.getPosition())).setIcon(TabIconsToogled[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tabLayout.getTabAt(tab.getPosition())).setIcon(TabIcons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_logout) {
            viewLogoutDialog();
        } else if(item.getItemId() == R.id.action_menu_exportDb){
            new DatabaseExport().export(MainActivity.this, "GGC_ISysDBF.db");
        }
        return super.onOptionsItemSelected(item);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment){
            mFragmentList.add(fragment);
        }
    }

    public MainActivity getInstance(){
        return instance;
    }

    public void setCameraInstance(int CaptureType){
        new CameraInstance(MainActivity.this).openCamera(CaptureType, (cameraIntent, CaptureType1, currentPhotoPath) -> new Fragment_Requirements().getInstance().openCamera(cameraIntent, CaptureType1, currentPhotoPath));
    }

    private void viewLogoutDialog(){
        MessageDialog messageDialog = new MessageDialog(MainActivity.this, "Logout", "Are you sure you want to logout?");
        messageDialog.setPositiveButton("Yes", (view, dialog) -> {
            dialog.dismiss();
            new SessionManager(MainActivity.this).setLogin(false);
            startActivity(new Intent(MainActivity.this, Activity_Splash.class));
            finish();
        });
        messageDialog.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
        messageDialog.showDialog();
    }
}
