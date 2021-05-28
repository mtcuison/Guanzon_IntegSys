package org.rmj.androidprojects.guanzongroup.g3creditapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.FragmentAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.ApplicantInfo.CI_Disbursement;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.ApplicantInfo.CI_IncomeInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.ApplicantInfo.CI_PersonalInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.ApplicantInfo.CI_SpouseInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;

public class Activity_InformationCI extends AppCompatActivity {
    private static final String TAG = Activity_InformationCI.class.getSimpleName();
    private static Activity_InformationCI instance;
    private GOCASApplication loGOCas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_investigator);
        instance = this;
        loGOCas = new GOCASApplication();
        String lsGoCas = getIntent().getStringExtra("gocas");
        loGOCas.setData(lsGoCas);
        setupWidgets();
    }

    private void setupWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_CiInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_CiInfo);
        TabLayout tabLayout = findViewById(R.id.tablayout_CiInfo);

        FragmentAdapter loAdapter = new FragmentAdapter(getSupportFragmentManager());
        loAdapter.addFragment(new CI_PersonalInfo());
        loAdapter.addFragment(new CI_SpouseInfo());
        loAdapter.addFragment(new CI_IncomeInfo());
        loAdapter.addFragment(new CI_Disbursement());
        viewPager.setAdapter(loAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public Activity_InformationCI getInstance(){
        return instance;
    }

    public GOCASApplication getGOCasInfo(){
        return loGOCas;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
