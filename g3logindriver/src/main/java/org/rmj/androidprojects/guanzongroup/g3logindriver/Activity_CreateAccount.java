package org.rmj.androidprojects.guanzongroup.g3logindriver;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.lib.account.ManageAccount.G3Accounts;
import org.rmj.g3appdriver.lib.account.ManageAccount.ManageAccountEntry;
import org.rmj.g3appdriver.lib.account.ManageAccount.onSendRequestListener;

import java.util.Objects;

public class Activity_CreateAccount extends AppCompatActivity {
    private static final String TAG = Activity_CreateAccount.class.getSimpleName();

    private static int RESOLVE_HINT = 9820;

    private Intent intent;
    private ImageButton btnBackButton;
    private static G3Accounts instance;
    private static ManageAccountEntry accountEntry;
    private View rootView;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private ProgressDialog pDialog;
    private Handler handler;
    private Runnable runnable;
    private Toast toast;

    private TextInputLayout tilLastName;
    private TextInputLayout tilFirstName;
    private TextInputLayout tilMidName;
    private TextInputLayout tilSuffix;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private TextInputLayout tileRtpPassword;
    private TextInputLayout tilMobileNo;

    private TextInputEditText txtLastname;
    private TextInputEditText txtFirstName;
    private TextInputEditText txtMidName;
    private TextInputEditText txtSuffix;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPassword;
    private TextInputEditText txtRtpPassword;
    private TextInputEditText txtMobileNo;

    private Button btnSubmit;

    public Activity_CreateAccount() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        setupWidgets();
        setupJavaClasses();
        setupActions();
        try {
            requestHint();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESOLVE_HINT){
            if(resultCode == RESULT_OK){
                Credential credential = data.getParcelableExtra(com.google.android.gms.auth.api.credentials.Credential.EXTRA_KEY);
                String mobileNo = credential.getId().replace("+63", "0");
                txtMobileNo.setText(mobileNo);
            }
        }
    }

    private void requestHint() throws IntentSender.SendIntentException {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(Activity_CreateAccount.this).addApi(Auth.CREDENTIALS_API).build();
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                googleApiClient, hintRequest);
        startIntentSenderForResult(intent.getIntentSender(),
                RESOLVE_HINT, null, 0, 0, 0);
    }

    private void setupWidgets(){
        tilLastName = findViewById(R.id.til_signupLastNm);
        tilFirstName = findViewById(R.id.til_signupFirstNm);
        tilMidName = findViewById(R.id.til_signupMidName);
        tilSuffix = findViewById(R.id.til_signupSuffix);
        tilEmail = findViewById(R.id.til_signupEmail);
        tilPassword = findViewById(R.id.til_signupPassword);
        tileRtpPassword = findViewById(R.id.til_signupCfmPassword);
        tilMobileNo = findViewById(R.id.til_signupMobileNo);

        txtLastname = findViewById(R.id.tie_signupLastNm);
        txtFirstName = findViewById(R.id.tie_signupFirstNm);
        txtMidName = findViewById(R.id.tie_signupMidname);
        txtSuffix = findViewById(R.id.tie_signupSuffix);
        txtEmail = findViewById(R.id.tie_signupEmail);
        txtPassword = findViewById(R.id.tie_signupPassword);
        txtRtpPassword = findViewById(R.id.tie_signupCfmPassword);
        txtMobileNo = findViewById(R.id.tie_signupMobileNo);

        btnSubmit = findViewById(R.id.btn_signupSubmit);

        txtLastname.addTextChangedListener(new MyTextWatcher(txtLastname));
        txtFirstName.addTextChangedListener(new MyTextWatcher(txtFirstName));
        txtMidName.addTextChangedListener(new MyTextWatcher(txtMidName));
        txtEmail.addTextChangedListener(new MyTextWatcher(txtEmail));
        txtPassword.addTextChangedListener(new MyTextWatcher(txtPassword));
        txtRtpPassword.addTextChangedListener(new MyTextWatcher(txtRtpPassword));
        txtMobileNo.addTextChangedListener(new MyTextWatcher(txtMobileNo));

        btnBackButton = findViewById(R.id.btn_signupBack);
    }

    private void setupJavaClasses() {
        accountEntry = new ManageAccountEntry();
        dialogBuilder = new AlertDialog.Builder(Activity_CreateAccount.this);
        pDialog = new ProgressDialog(Activity_CreateAccount.this);
        handler = new Handler();
    }

    private void setupActions(){
        btnBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Activity_CreateAccount.this, Activity_Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(!isLastNameValid()){
                    return;
                }
                if(!isFirstNameValid()){
                    return;
                }
                if(!isMidNameValid()){
                    return;
                }
                if(!isSuffixValid()){
                    return;
                }
                if(!isEmailValid()){
                    return;
                }
                if(!isPasswordValid()){
                    return;
                }
                if(!isPasswordSame()){
                    return;
                }
                if(!isMobileNoValid()){
                    return;
                }
                submitRegistration();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void submitRegistration(){
        final String email = Objects.requireNonNull(txtEmail.getText()).toString();
        final String password = Objects.requireNonNull(txtPassword.getText()).toString();
        final String mobileno = Objects.requireNonNull(txtMobileNo.getText()).toString();
        showDialog();
        instance = accountEntry.getAccountInstance(ManageAccountEntry.GetAccountEntry.ACCOUNT_REGISTRATION);
        runnable = new Runnable() {
            @Override
            public void run() {
                instance.sendRequest(Activity_CreateAccount.this, new onSendRequestListener() {
                    @Override
                    public JSONObject setData() {
                        JSONObject params = new JSONObject();
                        try{
                            params.put("name", getFullName());
                            params.put("mail", email);
                            params.put("pswd", password);
                            params.put("mobile", mobileno);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return params;
                    }

                    @Override
                    public void onSuccessResult(JSONObject jsonResponse) {
                        Log.e(TAG, "Sign up success...");
                        pDialog.dismiss();
                        handler.post(runnable);
                        handler.removeCallbacks(runnable);
                        dialogBuilder.setMessage("An email verification has been sent to your account.");
                        dialogBuilder.setTitle("Account Registration");
                        dialogBuilder.setIcon(R.drawable.guanzo_small_logo);
                        dialogBuilder.setCancelable(false);
                        dialogBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                intent = new Intent(Activity_CreateAccount.this, Activity_Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                finish();
                            }
                        });
                        alertDialog = dialogBuilder.create();
                        alertDialog.setCancelable(false);
                        pDialog.dismiss();
                        handler.removeCallbacks(runnable);
                        alertDialog.show();
                    }

                    @Override
                    public void onErrorResult(String Message) {
                        Log.e(TAG, "Sign up failed with some error message. Message : " + Message );
                        pDialog.dismiss();
                        handler.post(runnable);
                        handler.removeCallbacks(runnable);
                        dialogBuilder.setMessage(Message);
                        dialogBuilder.setTitle("Account Registration");
                        dialogBuilder.setIcon(R.drawable.guanzo_small_logo);
                        dialogBuilder.setCancelable(false);
                        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                intent = new Intent(Activity_CreateAccount.this, Activity_Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialogBuilder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog = dialogBuilder.create();
                        alertDialog.setCancelable(false);
                        pDialog.dismiss();
                        handler.removeCallbacks(runnable);
                        alertDialog.show();
                    }
                });
            }
        };
        handler.postDelayed(runnable, 500);
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private String getFullName(){
        return txtLastname.getText().toString() + ", " +
                "" + txtFirstName.getText().toString() + " " +
                "" + txtMidName.getText().toString() + " " +
                "" + txtSuffix.getText().toString();
    }

    private boolean isLastNameValid(){
        if(txtLastname.getText().toString().isEmpty()){
            tilLastName.setError("Please provide your last name.");
            requestFocus(txtLastname);
            return false;
        } else if(txtLastname.getText().toString().length() < 1){
            tilLastName.setError("Please provide valid last name.");
            requestFocus(txtLastname);
            return false;
        } else {
            tilLastName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isFirstNameValid(){
        if(txtFirstName.getText().toString().isEmpty()){
            tilFirstName.setError("Please provide your first name.");
            requestFocus(txtFirstName);
            return false;
        } else if(txtFirstName.getText().length()<1){
            tilFirstName.setError("Please provide valid first name.");
            requestFocus(txtFirstName);
            return false;
        } else {
            tilFirstName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isMidNameValid(){
        if(txtMidName.getText().toString().isEmpty()){
            tilMidName.setError("Please provide your middle name");
            requestFocus(txtMidName);
            return false;
        } else if(txtMidName.getText().toString().length()<1){
            tilMidName.setError("Please provide valid middle name");
            requestFocus(txtMidName);
            return false;
        } else {
            tilMidName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isSuffixValid(){
        if(txtSuffix.getText().toString().length() == 1){
            tilSuffix.setError("Please provide valid middle name");
            requestFocus(txtMidName);
            return false;
        } else {
            tilMidName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isPasswordValid(){
        if(txtPassword.getText().toString().length() < 7){
            tilPassword.setError("Password lenght is not enough.");
            requestFocus(txtPassword);
            return false;
        } else {
            tilPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isPasswordSame(){
        String password = txtPassword.getText().toString();
        String rtypPswd = txtRtpPassword.getText().toString();
        if(!password.equalsIgnoreCase(rtypPswd)){
            tilPassword.setError("Password does not match");
            tileRtpPassword.setError("Password does not match");
            requestFocus(tilPassword);
            return false;
        } else {
            tilPassword.setErrorEnabled(false);
            tileRtpPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isEmailValid(){
        String email = txtEmail.getText().toString();
        return (!TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isMobileNoValid() {
        String target = txtMobileNo.getText().toString();
        if(target.isEmpty()){
            tilMobileNo.setError("Please provide your mobile no.");
            requestFocus(txtMobileNo);
            return false;
        } else if(target.length()!=11){
            tilMobileNo.setError("Please provide a valid mobile no.");
            requestFocus(txtMobileNo);
            return false;
        } else if (!target.substring(0, 2).equalsIgnoreCase("09")){
            tilMobileNo.setError("Valid mobile no. starts with \'09\'.");
            requestFocus(txtMobileNo);
            return false;
        } else {
            tilMobileNo.setErrorEnabled(false);
        }
        return true;
    }

    class MyTextWatcher implements TextWatcher {

        private View mView;

        MyTextWatcher(View view){
            this.mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int itemID = mView.getId();
            if(itemID == R.id.tie_signupLastNm){
                isLastNameValid();
            } else if(itemID == R.id.tie_signupFirstNm){
                isFirstNameValid();
            } else if(itemID == R.id.tie_signupMidname){
                isMidNameValid();
            } else if(itemID == R.id.tie_signupSuffix){
                isSuffixValid();
            } else if(itemID == R.id.tie_signupEmail){
                isEmailValid();
            } else if(itemID == R.id.tie_signupMobileNo){
                isMobileNoValid();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void showDialog(){
        pDialog.setIcon(R.drawable.guanzo_small_logo);
        pDialog.setCancelable(false);
        pDialog.setMessage("Sending Request. Please wait...");
        pDialog.setTitle("Sign Up");
        pDialog.show();
    }
}
