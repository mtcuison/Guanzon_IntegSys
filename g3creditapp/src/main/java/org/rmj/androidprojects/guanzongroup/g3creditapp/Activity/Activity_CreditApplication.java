package org.rmj.androidprojects.guanzongroup.g3creditapp.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.FragmentAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.CoMaker.Fragment_CM_ResidenceInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse.Fragment_SpouseInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse.Fragment_SpouseMeans;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse.Fragment_SpouseResidence;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditOnlineApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.MessageDialog;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Disbursement.Fragment_Dependencies;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Disbursement.Fragment_Properties;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_IncomStatus;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Other.Fragment_OtherInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Personal.Fragment_PersonalInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.CoMaker.Fragment_Co_MakerInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Personal.Fragment_ResidenceInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Disbursement.Fragment_Disbursement;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import java.util.Objects;

public class Activity_CreditApplication extends AppCompatActivity {
    private static Activity_CreditApplication instance;
    private ViewPager viewPager;
    private String TransNox = "";

    private Fragment[] FormPages = {
            new Fragment_PersonalInfo(),        //0
            new Fragment_ResidenceInfo(),       //1
            new Fragment_IncomStatus(),         //2
            new Fragment_SpouseInfo(),          //3
            new Fragment_SpouseResidence(),     //4
            new Fragment_SpouseMeans(),         //5
            new Fragment_Disbursement(),        //6
            new Fragment_Dependencies(),        //7
            new Fragment_OtherInfo(),           //8
            new Fragment_Properties(),          //9
            new Fragment_Co_MakerInfo(),        //10
            new Fragment_CM_ResidenceInfo()};       //11

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        TransNox = getIntent().getStringExtra("transno");
        setContentView(R.layout.activity_credit_application);
        setupWidgets();
        viewPager.setOffscreenPageLimit(FormPages.length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.drawable.ic_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            exitDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getTransNox(){
        return TransNox;
    }

    @SuppressLint("NewApi")
    private void setupWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_creditApplication);
        toolbar.setTitle("Credit Application");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager_creditApp);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        for (Fragment formPage : FormPages) {
            adapter.addFragment(formPage);
        }
        viewPager.setAdapter(adapter);
    }

    private void exitDialog(){
        MessageDialog messageDialog = new MessageDialog(Activity_CreditApplication.this);
        messageDialog.setMessage("Are you sure you want to quit Credit App?");
        messageDialog.setNegativeButton("Yes", dialog -> {
            new CreditApplication(Activity_CreditApplication.this).CancelApplication(TransNox, Message -> new CustomToast(Activity_CreditApplication.this, Message, CustomToast.TYPE.INFORMATION).show());
            dialog.dismiss();
            finish();
        });

        messageDialog.setPositiveButton("No", Dialog::dismiss);
        messageDialog.showDialog();
    }

    public void setCurrentItem(int fragmentPage, boolean smoothScroll){
        viewPager.setCurrentItem(fragmentPage, smoothScroll);
    }

    public Activity_CreditApplication getInstance(){
        return instance;
    }

    public void saveCreditApplication(String transNox){
        new GOCasInfoSender(transNox).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class GOCasInfoSender extends AsyncTask<Integer, Integer, Boolean>{

        ProgressDialog loDialog;
        String transNox;
        boolean isSent;
        String lsMessage;
        GOCasInfoSender(String lsTransNox){
            this.transNox = lsTransNox;
        }

        @Override
        protected void onPreExecute() {
            loDialog = new ProgressDialog(Activity_CreditApplication.this);
            loDialog.setCancelable(false);
            loDialog.setMessage("Saving Application. Please wait...");
            loDialog.setIcon(R.drawable.guanzo_small_logo);
            loDialog.show();
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Integer... integers) {

            new CreditOnlineApplication(Activity_CreditApplication.this).saveApplication(transNox,new CreditOnlineApplication.OnSaveCreditOnlineApplication() {
            @Override
            public void onSaveSuccessResult(String ClientName) {
                loDialog.dismiss();
                isSent = true;
                lsMessage = ClientName + "s' has been sent to server. To check application status open 'Credit Application List' on menu.";
            }

            @Override
            public void onSaveFailedResult(String message) {
                loDialog.dismiss();
                isSent = false;
                lsMessage = message;
            }
        });
            return isSent;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            viewMessageDialog(lsMessage);
            super.onPostExecute(aBoolean);
        }

        void viewMessageDialog(String message){
            MessageDialog messageDialog = new MessageDialog(Activity_CreditApplication.this);
            messageDialog.setPositiveButton("Okay", dialog -> {
                startActivity(new Intent(Activity_CreditApplication.this, Activity_ApplicationLog.class));
                dialog.dismiss();
                finish();
            });
            messageDialog.setMessage(message);
            messageDialog.showDialog();
        }
    }
}

