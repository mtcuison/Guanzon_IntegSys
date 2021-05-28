package org.rmj.androidprojects.guanzongroup.g3logindriver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3logindriver.Etc.CheckAccountStatus;
import org.rmj.androidprojects.guanzongroup.g3logindriver.Etc.LoginAssets;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.lib.account.AppLogin.LoginUserAccount;
import org.rmj.g3appdriver.lib.account.AppLogin.onUserLoginListener;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.Objects;

public class Activity_Login extends AppCompatActivity {
    private static final String TAG = Activity_Login.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static Activity_Login instance;

    private static int RESOLVE_HINT = 9820;
    private ProgressDialog pDialog;
    private Toast toast;
    private Context mContext = Activity_Login.this;
    private ConnectionUtil connectionUtil;
    private SessionManager sessionManager;
    private SharedPref sharedPref;

    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private TextInputLayout tilMobileNo;

    private TextInputEditText tieEmail;
    private TextInputEditText tiePassword;
    private TextInputEditText tieMobileNo;

    private JSONObject params;

    private boolean isActionSend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_login);
        setupJavaClasses();
        setupWidgets();
        transparentToolbar(Activity_Login.this);
        if(new Telephony(Activity_Login.this).getMobileNoDb().isEmpty()) {
            try {
                requestHint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESOLVE_HINT){
            if(resultCode == RESULT_OK){
                Credential credential = data.getParcelableExtra(com.google.android.gms.auth.api.credentials.Credential.EXTRA_KEY);
                String mobileNo = credential.getId().replace("+63", "0");
                tieMobileNo.setText(mobileNo);
            }
        }
    }

    private void requestHint() throws IntentSender.SendIntentException {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(Activity_Login.this).addApi(Auth.CREDENTIALS_API).build();
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                googleApiClient, hintRequest);
        startIntentSenderForResult(intent.getIntentSender(),
                RESOLVE_HINT, null, 0, 0, 0);
    }

    private void setupWidgets() {
        tilEmail = findViewById(R.id.til_loginEmail);
        tilPassword = findViewById(R.id.til_loginPassword);
        tilMobileNo = findViewById(R.id.til_loginMobileNo);

        tieEmail = findViewById(R.id.tie_loginEmail);
        tiePassword = findViewById(R.id.tie_loginPassword);
        tieMobileNo = findViewById(R.id.tie_loginMobileNo);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnFrgtPassword = findViewById(R.id.btn_forgotPassword);
        Button btnCrateAccount = findViewById(R.id.btn_createAccnt);

        tieEmail.addTextChangedListener(new LoginTextValidation(tieEmail));
        tiePassword.addTextChangedListener(new LoginTextValidation(tiePassword));
        tieMobileNo.addTextChangedListener(new LoginTextValidation(tieMobileNo));

        LinearLayout mobileNoInput = findViewById(R.id.linear_loginMobileNo);
        ImageView loginBackground = findViewById(R.id.linear_loginBackground);

        mobileNoInput.setVisibility(getMobileNoInputVisibility());
        loginBackground.setImageResource(new LoginAssets().getLoginBackground(sharedPref.ProducID()));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAccount();
            }
        });

        btnFrgtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this, Activity_RequestPassword.class));
            }
        });

        btnCrateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this, Activity_CreateAccount.class));
            }
        });
    }

    private void setupJavaClasses(){
        connectionUtil = new ConnectionUtil(mContext);
        pDialog = new ProgressDialog(mContext);
        sessionManager = new SessionManager(mContext);
        sharedPref = new SharedPref(mContext);
    }

    private void LoginAccount(){
        if(!isEmailValid()){
        } else if(!isPasswordValid()){
        } else if(new Telephony(mContext).getMobileNoDb().isEmpty() && !isMobileNoValid()) {
        } else {
            showDialog();
        }
    }

    public void showDialog(){
        new LoginUser().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class LoginUser extends AsyncTask<Integer, Integer, String>{

        String lsMessage;
        String lsResult;

        @Override
        protected void onPreExecute() {
            pDialog.setCancelable(false);
            pDialog.setIcon(R.drawable.guanzo_small_logo);
            pDialog.setTitle("Login");
            pDialog.setMessage("Please wait...");
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            if(connectionUtil.isDeviceConnected()){
                new LoginUserAccount().LoginAccount(mContext, new onUserLoginListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public JSONObject setParams() {
                        params = new JSONObject();
                        try{
                            params.put("user", Objects.requireNonNull(tieEmail.getText()).toString());
                            params.put("pswd", Objects.requireNonNull(tiePassword.getText()).toString());
                            params.put("nmbr", Objects.requireNonNull(tieMobileNo.getText()).toString());
                            return params;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onSuccessLogin() {
                        lsResult = "success";
                        Intent intent;
                        SharedPref sharedPref = new SharedPref(mContext);
                        sessionManager.setLogin(true);
                        dismissDialog();
                        if (!isActionSend) {
                            switch (sharedPref.ProducID().toLowerCase()){
                                case "telecom":
                                    intent = new Intent("android.intent.action.TELECOM_SUCCESS_LOGIN");
                                    Log.e(TAG, "Broadcast login has been sent");
                                    sendBroadcast(intent);
                                    break;
                                case "guanzonapp":
                                    intent = new Intent("android.intent.action.SUCCESS_LOGIN");
                                    Log.e(TAG, "Broadcast login has been sent");
                                    sendBroadcast(intent);
                                    break;
                                case "integsys":
                                    intent = new Intent("android.intent.action.INTEGSYS_SUCCESS_LOGIN");
                                    Log.e(TAG, "Broadcast login has been sent");
                                    sendBroadcast(intent);
                                    break;
                            }
                            isActionSend = true;
                            finish();
                        }
                    }

                    @Override
                    public void onLoginErrorResult(String Message) {
                        dismissDialog();
                        Log.e(TAG, "Login failed. Error Message : " + Message);
                        lsMessage = Message;
                        lsResult = "error";
                    }
                });
            } else {
                dismissDialog();
                lsMessage = "Not connected to internet.";
                lsResult = "error";
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (s.equalsIgnoreCase("error")) {
                    if(isValidJsonResult(lsMessage)){
                        JSONObject loJSon = new JSONObject(lsMessage );
                        JSONObject loError = loJSon.getJSONObject("error");
                        toast = Toast.makeText(mContext, loError.getString("message"), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else {
                        toast = Toast.makeText(mContext, lsMessage, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    private boolean isValidJsonResult(String stringResult){
        try{
            JSONObject jsonObject = new JSONObject(stringResult);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void dismissDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    public static Activity_Login getInstance(){
        return instance;
    }


    public void transparentToolbar(Activity Active_Activity) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(Active_Activity, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            Active_Activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(Active_Activity, false);
            Active_Activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
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


    /***********************************************************************************************
     * methods below this line validates the user input according to what is require by the field...
     * */

    class LoginTextValidation implements TextWatcher{

        private View mView;

        LoginTextValidation(View view){
            this.mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int id = mView.getId();
            if(id == R.id.tie_loginEmail){
                isEmailValid();
            } else if (id == R.id.tie_loginPassword){
                isPasswordValid();
            } else if(id == R.id.tie_loginMobileNo){
                isMobileNoValid();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @SuppressLint("NewApi")
    private boolean isEmailValid(){
        String target = Objects.requireNonNull(tieEmail.getText()).toString();
        if(tieEmail.getText().toString().isEmpty()){
            tilEmail.setError("Please enter you email");
            requestFocus(tieEmail);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            return false;
        } else {
            tilEmail.setErrorEnabled(false);
        }
        return true;
    }

    @SuppressLint("NewApi")
    private boolean isPasswordValid(){
        if(Objects.requireNonNull(tiePassword.getText()).toString().isEmpty()){
            tilPassword.setError("Please enter your password.");
            requestFocus(tiePassword);
            return false;
        } else if(tiePassword.getText().toString().length() <5) {
            tilPassword.setError("Your password is too short.");
            requestFocus(tiePassword);
            return false;
        } else {
            tilPassword.setErrorEnabled(false);
        }
        return true;
    }

    @SuppressLint("NewApi")
    private boolean isMobileNoValid(){
        if(Objects.requireNonNull(tieMobileNo.getText()).toString().isEmpty()){
            tilMobileNo.setError("Please enter your mobile number.");
            requestFocus(tieMobileNo);
            return false;
        } else if(tieMobileNo.getText().toString().length() != 11){
            tilMobileNo.setError("Please provide valid mobile no.");
            requestFocus(tieMobileNo);
            return false;
        } else if(!tieMobileNo.getText().toString().
                substring(0, 2).equalsIgnoreCase("09")){
            tilMobileNo.setError("Valid mobile no. starts with '09'.");
            requestFocus(tieMobileNo);
            return false;
        } else {
            tilMobileNo.setErrorEnabled(false);
        }
        return true;
    }

    private int getMobileNoInputVisibility(){
        if(new Telephony(mContext).getMobileNoDb().isEmpty()){
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public void finishLogin(){
        if(new SharedPref(mContext).ProducID().equalsIgnoreCase("GuanzonApp")){
            finish();



        }
    }

    public void setupReceiver(G3DriverInstanceAction instance){

    }
}
