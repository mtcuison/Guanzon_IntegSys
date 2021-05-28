package org.rmj.guanzongroup.integsys.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.CreditAppLocalData;
import org.rmj.androidprojects.guanzongroup.g3logindriver.Activity_Login;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.InAppUpdate;
import org.rmj.g3cm.android.g3cashmanager.LocalData.CashCountLocalData;
import org.rmj.guanzongroup.bullseye.LocalData.BullseyeLocalData;
import org.rmj.guanzongroup.g3msg_notifylib.DbHelper.NotificationData;
import org.rmj.guanzongroup.integsys.BuildConfig;
import org.rmj.guanzongroup.integsys.Etc.SessionExpiration;
import org.rmj.guanzongroup.integsys.R;
import org.rmj.guanzongroup.integsys.Services.IntegSys_ServiceProvider;
import org.rmj.guanzongroup.integsys.Services.LoginActionReceiver;
import org.rmj.guanzongroup.integsys.Services.MessagingService;
import org.rmj.guanzongroup.promotions.Local.PromoLocalData;

public class Activity_Splash extends AppCompatActivity {
    private static final String TAG = Activity_Splash.class.toString();
    @SuppressLint("StaticFieldLeak")
    private static Activity_Splash instance;

    private LoginActionReceiver actionReceiver = new LoginActionReceiver();
    private Context mContext = Activity_Splash.this;
    private SharedPref sharedPref;
    private Telephony telephony;
    private Context context;
    private SessionManager sessionManager;

    private ProgressBar progressBar;

    private String[] PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        context = Activity_Splash.this;
        setContentView(R.layout.activity_splash);
        new InAppUpdate(Activity_Splash.this, Activity_Splash.this).Check_Update();
        sessionManager = new SessionManager(context);
        startService(new Intent(Activity_Splash.this, MessagingService.class));
        transparentToolbar();
        setupJavaClasses();
        setupIntegsys();
        setupWidgets();
        checkPermissions();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob(){
        ComponentName loComponent = new ComponentName(this, IntegSys_ServiceProvider.class);
        JobInfo loJob = new JobInfo.Builder(123, loComponent)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(900000)
                .build();
        JobScheduler loScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int liResult = loScheduler.schedule(loJob);
        if(liResult == JobScheduler.RESULT_SUCCESS){
            Log.e(TAG, "Job Scheduled");
        } else {
            Log.e(TAG, "Job Schedule Failed.");
        }
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.INTEGSYS_SUCCESS_LOGIN");
        registerReceiver(actionReceiver, intentFilter);
        Log.e(TAG, "Login action registration has been registered.");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scheduleJob();
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(actionReceiver);
        Log.e(TAG, "Login action has been unregistered.");
        super.onDestroy();
    }

    private void setupIntegsys(){
        sharedPref.setTemp_ProductID("IntegSys");
        sharedPref.setTemp_DeviceID(telephony.getDeviceID());
        new CreditAppLocalData(mContext).setupCreditAppData();
        new CashCountLocalData(mContext).setupCashCountData();
        new NotificationData(mContext).setupNotificationData();
        new BullseyeLocalData(mContext).setupBullseyeData();
        new PromoLocalData(mContext).createPromoTable();
    }

    private void setupJavaClasses(){
        sharedPref = new SharedPref(mContext);
        telephony = new Telephony(mContext);
    }

    @SuppressLint("SetTextI18n")
    private void setupWidgets(){
        TextView lblAppBuildVrsion = findViewById(R.id.lbl_splashAppBuild);
        progressBar = findViewById(R.id.progress_splash);

        lblAppBuildVrsion.setText("Build Version. "
                + BuildConfig.VERSION_NAME + " - "
                + BuildConfig.VERSION_CODE + "Vc.");
    }

    private void startProgressBar(){
        new DoBackgroundLoading().execute(20);
    }

    private void checkPermissions() {
        while (!hasPermissions(Activity_Splash.this, PERMISSIONS)) {
            int PERMISSION_ALL = 10;
            ActivityCompat.requestPermissions(Activity_Splash.this, PERMISSIONS, PERMISSION_ALL);
        }
        startProgressBar();
    }

    public static boolean hasPermissions(Context context, String...permissions){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && context !=null && permissions != null) {
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    private void transparentToolbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setWindowFlag(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        } else {
            winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        win.setAttributes(winParams);
    }

    public static Activity_Splash getInstance(){
        return instance;
    }

    @SuppressLint("StaticFieldLeak")
    class DoBackgroundLoading extends AsyncTask<Integer, Integer, String>{

        @Override
        protected String doInBackground(Integer... integers) {
            for(int x = 0; x < integers[0]; x++){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress((int) ((x / (float) integers[0]) * 100));
            }

            boolean lbIsValidSession = new SessionExpiration(Activity_Splash.this).isSessionExpired();
            if(sessionManager.isLoggedIn()) {
                if(!lbIsValidSession) {
                    startActivity(new Intent(context, Activity_Login.class));
                } else {
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(context, Activity_Login.class));
            }
            return "Finished loading app.";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }
}
