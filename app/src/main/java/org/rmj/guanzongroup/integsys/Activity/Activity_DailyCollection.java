package org.rmj.guanzongroup.integsys.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.rmj.guanzongroup.integsys.Fragment.Collection.Fragment_CollectionNonPriority;
import org.rmj.guanzongroup.integsys.Fragment.Collection.Fragment_CollectionPriority;
import org.rmj.guanzongroup.integsys.R;

import java.util.ArrayList;

public class Activity_DailyCollection extends AppCompatActivity {
    private static final String TAG = Activity_DailyCollection.class.getSimpleName();

    private Context mContext = Activity_DailyCollection.this;
    private Intent intent;
    private Toast toast;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_collection);
        setupWidgets();
    }

    private void setupWidgets(){
        toolbar = findViewById(R.id.toolbar_dailyCollection);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewPager_collectionView);
        tabLayout = findViewById(R.id.tablayout_collectionLevel);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragmjent(new Fragment_CollectionPriority(), "Priority");
        adapter.addFragmjent(new Fragment_CollectionNonPriority(), "Non-Priority");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> mFragmentList = new ArrayList<>();
        private ArrayList<String> mTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        public void addFragmjent(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mTitleList.add(title);
        }
    }
}
