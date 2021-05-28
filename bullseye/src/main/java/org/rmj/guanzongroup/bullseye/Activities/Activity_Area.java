package org.rmj.guanzongroup.bullseye.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.guanzongroup.bullseye.Fragment.Fragment_AreaList;
import org.rmj.guanzongroup.bullseye.LocalData.SaveMonitorData;
import org.rmj.guanzongroup.bullseye.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_Area extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        Toolbar toolbar = findViewById(R.id.toolbar_bullseye_area);
        toolbar.setTitle("Area Monitoring");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new SaveMonitorData(Activity_Area.this).DownloadAreaPerformance(new SaveMonitorData.OnDownloadListener() {
            @Override
            public void OnSuccess() {

                ViewPager viewPager = findViewById(R.id.viewpager_menu);
                Fragment_Adapter adapter = new Fragment_Adapter(getSupportFragmentManager());
                adapter.addFragment(new Fragment_AreaList());
                viewPager.setAdapter(adapter);

            }

            @Override
            public void OnError() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class Fragment_Adapter extends FragmentPagerAdapter{

        private List<Fragment> mFragment = new ArrayList<>();

        public Fragment_Adapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }
         void addFragment(Fragment fragment){
            this.mFragment.add(fragment);
         }
    }
}
