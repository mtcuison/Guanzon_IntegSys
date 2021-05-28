package org.rmj.g3cm.android.g3cashmanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.lib.cashcount.CashRegister;
import org.rmj.g3cm.android.g3cashmanager.JavaExtras.SharedPref;
import org.rmj.g3cm.android.g3cashmanager.kwiksearch.KwikSearchDialog;

import java.text.SimpleDateFormat;
import java.util.Random;

public class Activity_CashCountSubmit extends AppCompatActivity {

    private static Activity_CashCountSubmit instance;
    private SharedPref sharedPref;
    private ProgressDialog pDialog;
    private Handler handler;
    private Runnable runnable;
    private Toolbar toolbar;
    private Toast toast;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    TextInputLayout tilRequestID;
    TextInputLayout tilOfflRecpt;
    TextInputLayout tilSalesRcpt;
    TextInputLayout tilPrvnlRcpt;
    TextInputLayout tilCllctRcpt;

    EditText txtCurr_DateTime,
            txtRequestID,
            txtOfficialReceipt,
            txtSalesInvoice,
            txtProvisionalReceipt,
            txtCollectionReceipt,
            txtTransNox;
    Button btnSendToServer;

    ImageButton btnQuickSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_summary);
        instance = this;

        setupJavaClassess();
        setupWidgets();
        generateTransNox();

        btnSendToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntry();
            }
        });

        btnQuickSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getWindow().getDecorView();
                KwikSearchDialog kwikSearchDialog = new KwikSearchDialog(Activity_CashCountSubmit.this, view);
                kwikSearchDialog.createKwikSearch(txtRequestID.getText().toString());
            }
        });

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat curr_date = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
                                String dateString = curr_date.format(date);
                                txtCurr_DateTime.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void validateEntry(){
        if(!isRequestNameValid()){
            return;
        }
        if(!isORValid()){
            return;
        }
        if(!isSIValid()){
            return;
        }
        if(!isPRValid()){
            return;
        }
        if(!isCRValid()){
            return;
        }

        pDialog.setCancelable(false);
        pDialog.setIcon(R.drawable.guanzo_small_logo);
        pDialog.setMessage("Sending cash count. Please wait...");
        pDialog.setTitle("Cash Count");
        pDialog.show();
        runnable = new Runnable() {
            @Override
            public void run() {
                submitCashCountDetails();
            }
        };
        handler.postDelayed(runnable, 100);
    }

    private void setupWidgets(){
        toolbar = findViewById(R.id.toolbar_cashCountSubmit);
        toolbar.setTitle("Cash Count");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtRequestID = findViewById(R.id.txtSumRequestID);
        txtOfficialReceipt = findViewById(R.id.txtOfficialReceipt);
        txtSalesInvoice = findViewById(R.id.txtSalesInvoice);
        txtProvisionalReceipt = findViewById(R.id.txtProvisionalReceipt);
        txtCollectionReceipt = findViewById(R.id.txtCollectionReceipt);
        txtTransNox = findViewById(R.id.txtTransNox);
        btnSendToServer = findViewById(R.id.btnSendToServer);
        btnQuickSearch = findViewById(R.id.btn_quick_search);
        txtCurr_DateTime = findViewById(R.id.txtCurrentDateTime);

        tilRequestID = findViewById(R.id.til_requestName);
        tilOfflRecpt = findViewById(R.id.til_ccOR);
        tilSalesRcpt = findViewById(R.id.til_ccSI);
        tilPrvnlRcpt = findViewById(R.id.til_ccPR);
        tilCllctRcpt = findViewById(R.id.til_ccCR);

        txtRequestID.addTextChangedListener(new MyTextWatcher(txtRequestID));
        txtOfficialReceipt.addTextChangedListener(new MyTextWatcher(txtOfficialReceipt));
        txtSalesInvoice.addTextChangedListener(new MyTextWatcher(txtSalesInvoice));
        txtProvisionalReceipt.addTextChangedListener(new MyTextWatcher(txtProvisionalReceipt));
        txtCollectionReceipt.addTextChangedListener(new MyTextWatcher(txtCollectionReceipt));
    }

    private void setupJavaClassess(){
        pDialog = new ProgressDialog(Activity_CashCountSubmit.this);
        builder = new AlertDialog.Builder(Activity_CashCountSubmit.this);
        sharedPref = new SharedPref(Activity_CashCountSubmit.this);
        handler = new Handler();
    }

    private void submitCashCountDetails(){
        new CashRegister().submitCashCount(Activity_CashCountSubmit.this, new CashRegister.g3CashRegistrationManager() {
            @Override
            public JSONObject setParameter() {
                /**
                 * Convert the string parameters into JSONOBJECT from the last activity
                 * and add more values inside the json parameters...
                 * */
                JSONObject parameters = getParameters();
                try{
                    parameters.put("transnox", txtTransNox.getText().toString());
                    parameters.put("ornoxxxx", txtOfficialReceipt.getText().toString());
                    parameters.put("sinoxxxx", txtSalesInvoice.getText().toString());
                    parameters.put("prnoxxxx", txtProvisionalReceipt.getText().toString());
                    parameters.put("crnoxxxx", txtCollectionReceipt.getText().toString());
                    parameters.put("entrytme", txtCurr_DateTime.getText().toString());
                    parameters.put("reqstdid", txtRequestID.getText().toString());
                    return parameters;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onSendSuccessResult() {
                handler.post(runnable);
                handler.removeCallbacks(runnable);
                pDialog.dismiss();
                showDialogNotice("Cash count has been sent successfully.");
            }

            @Override
            public void onSendErrorResult(String ErrorMessage) {
                handler.post(runnable);
                handler.removeCallbacks(runnable);
                pDialog.dismiss();
                showDialogNotice(ErrorMessage);
            }
        });
    }

    private void showDialogNotice(String Message){
        builder.setCancelable(false);
        builder.setIcon(R.drawable.guanzo_small_logo);
        builder.setTitle("Sending Result");
        builder.setMessage(Message);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startParentActivity(sharedPref.MenuActivity());
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void startParentActivity(String ParentActivity){
        Class<?> classActivity;
        try {
            classActivity = Class.forName(ParentActivity);
            Intent intent = new Intent(Activity_CashCountSubmit.this, classActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Get the parameters from
     * Cash counter activity which is pass through intent
     * JSON is converted into string...*/
    private JSONObject getParameters(){
        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("params"));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generate transNox
     * check Existing TransNox
     * Compare transNox if Same
     * Generate Another*/
    private String generateTransNox(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();

        Random rnd = new Random();
        while (salt.length() < 12) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        String uuid_transno = salt.toString();
        txtTransNox.setText(uuid_transno);
        return uuid_transno;
    }

    /**
     * This method triggers the validation of inputs of users
     *
     * displays a notice if an input is invalid...*/
    class MyTextWatcher implements TextWatcher{

        private View mView;

        MyTextWatcher(View view){
            this.mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int id = mView.getId();
            if(id == R.id.txtSumRequestID){
                isRequestNameValid();
            }
            if(id == R.id.txtOfficialReceipt){
                isORValid();
            }
            if(id == R.id.txtSalesInvoice){
                isSIValid();
            }
            if(id == R.id.txtProvisionalReceipt){
                isPRValid();
            }
            if(id == R.id.txtCollectionReceipt){
                isCRValid();
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

    /**
     * KwikSearch sets the selected value from recyclerview
     * this catches the value instead of passing it through another variable.
     * */
    public static Activity_CashCountSubmit getInstance(){
        return instance;
    }

    public void setName(final String Name){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                EditText reqName = findViewById(R.id.txtSumRequestID);
                reqName.setText(Name);
            }
        });
    }

    /**
     * Request Name/ID validations..
     * returns false if input field is empty...*/
    private boolean isRequestNameValid(){
        if(txtRequestID.getText().toString().isEmpty()){
            tilRequestID.setError("Please provide input on this field.");
            requestFocus(txtRequestID);
            return false;
        } else {
            tilRequestID.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * All other validations returns if the fields are null...*/
    private boolean isORValid(){
        if(txtOfficialReceipt.getText().toString().isEmpty()){
            tilOfflRecpt.setError("Please provide Official Receipt.");
            requestFocus(tilOfflRecpt);
            return false;
        } else {
            tilOfflRecpt.setErrorEnabled(false);
        }
        return true;
    }
    private boolean isSIValid(){
        if(txtSalesInvoice.getText().toString().isEmpty()){
            tilSalesRcpt.setError("Please provide Sales Invoice");
            requestFocus(tilSalesRcpt);
            return false;
        } else {
            tilSalesRcpt.setErrorEnabled(false);
        }
        return true;
    }
    private boolean isPRValid(){
        if(txtProvisionalReceipt.getText().toString().isEmpty()){
            tilPrvnlRcpt.setError("Please provide Provisional Receipt.");
            requestFocus(tilPrvnlRcpt);
            return false;
        } else {
            tilPrvnlRcpt.setErrorEnabled(false);
        }
        return true;
    }
    private boolean isCRValid(){
        if(txtCollectionReceipt.getText().toString().isEmpty()){
            tilCllctRcpt.setError("Please provide Collection Receipt.");
            requestFocus(tilCllctRcpt);
            return false;
        } else {
            tilCllctRcpt.setErrorEnabled(false);
        }
        return true;
    }
}
