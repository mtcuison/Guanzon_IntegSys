package org.rmj.androidprojects.guanzongroup.g3logindriver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.lib.account.ManageAccount.G3Accounts;
import org.rmj.g3appdriver.lib.account.ManageAccount.ManageAccountEntry;
import org.rmj.g3appdriver.lib.account.ManageAccount.onSendRequestListener;

public class Activity_RequestPassword extends AppCompatActivity {
    private static final String TAG = Activity_RequestPassword.class.getSimpleName();

    private G3Accounts instance;
    private ManageAccountEntry accountEntry;

    private Handler handler;
    private Runnable runnable;
    private Toast toast;
    private ProgressDialog pDialog;

    private TextInputLayout tilEmail;
    private TextInputEditText tieEmail;

    private Button btnSendEmail;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_password);
        setupWidgets();
        setupJavaClasses();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Activity_RequestPassword.this, Activity_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    private void setupWidgets(){
        tieEmail = findViewById(R.id.tie_prEmail);
        tilEmail = findViewById(R.id.til_prEmail);
        tieEmail.addTextChangedListener(new MyTextWatcher(tieEmail));
        btnSendEmail = findViewById(R.id.btn_prSendEmail);
        btnBack = findViewById(R.id.btn_prBack);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmailValid()){
                    return;
                }
                showDialog();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        sendEmail();
                    }
                };
                handler.postDelayed(runnable, 500);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_RequestPassword.this, Activity_Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupJavaClasses(){
        handler = new Handler();
        pDialog = new ProgressDialog(Activity_RequestPassword.this);
        accountEntry = new ManageAccountEntry();
    }

    private void sendEmail(){
        instance = accountEntry.getAccountInstance(ManageAccountEntry.GetAccountEntry.FORGOT_PASSWORD);
        instance.sendRequest(Activity_RequestPassword.this, new onSendRequestListener() {
            @Override
            public JSONObject setData() {
                JSONObject params = new JSONObject();
                try{
                    params.put("email", tieEmail.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "Forgot password parameters has been set. Value : " + params);
                return params;
            }

            @Override
            public void onSuccessResult(JSONObject jsonResponse) {
                pDialog.dismiss();
                handler.post(runnable);
                handler.removeCallbacks(runnable);
                toast = Toast.makeText(getApplicationContext(), "Email for password retrieval has been sent successfully.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

            @Override
            public void onErrorResult(String Message) {
                pDialog.dismiss();
                handler.post(runnable);
                handler.removeCallbacks(runnable);
                toast = Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void showDialog(){
        pDialog.setTitle("Forgot Password");
        pDialog.setMessage("Sending Email. Please wait...");
        pDialog.setIcon(R.drawable.guanzo_small_logo);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * This provides a listener that everytime users enters a
     * correct/incorrect input it automatically checks the value
     * and returns a boolean value from isEmailValid function...*/
    public class MyTextWatcher implements TextWatcher {

        private View mView;

        MyTextWatcher(View view){
            this.mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mView.getId() == R.id.tie_prEmail){
                isEmailValid();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * Validate email before proceeding to
     * send Email...*/
    private boolean isEmailValid(){
        if(tieEmail.getText().toString().isEmpty()){
            tilEmail.setError("Please provide your email.");
            requestFocus(tieEmail);
            return false;
        } else if(!emailValid()){
            tilEmail.setError("Please provide a valid email address.");
            requestFocus(tieEmail);
            return false;
        } else {
            tilEmail.setErrorEnabled(false);
        }
        return true;
    }
    private boolean emailValid(){
        return !TextUtils.isEmpty(tieEmail.getText().toString()) &&
                Patterns.EMAIL_ADDRESS.matcher(tieEmail.getText().toString()).matches();
    }

    /**
     * Focus the Edittext Component if the user enters invalid
     * email...*/
    private void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
