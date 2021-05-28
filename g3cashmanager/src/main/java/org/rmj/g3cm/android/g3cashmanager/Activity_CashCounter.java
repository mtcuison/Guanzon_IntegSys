package org.rmj.g3cm.android.g3cashmanager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3cm.android.g3cashmanager.JavaExtras.DatePickerFragment;
import org.rmj.g3cm.android.g3cashmanager.JavaExtras.SharedPref;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Activity_CashCounter extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private boolean openDatePickerDialog = false;
    private String BranchCode, BranchName;
    private SharedPref sharedPref;

    private Toast toast;

    private TextView tot_thousandBill,
            tot_fiveHundredBill,
            tot_twoHundredBill,
            tot_oneHundredbill,
            tot_fiftyPesobill,
            tot_twentyPesoBill,
            tot_tenPesoCoin,
            tot_fivePesoCoin,
            tot_pesoCoin,
            tot_25Cents,
            tot_tenCents,
            tot_fiveCents;
    private EditText txtThousanBill,
            txtfiveHundredBill,
            txtTwoHundredBill,
            txtOneHundredBill,
            txtFiftyPeso,
            txtTwentyPeso,
            txtTenPeso,
            txtFivePeso,
            txtPesoCoin,
            txt25Cents,
            txt10Cents,
            txt5Cents;

    private TextView UserID, txtGrandTotal;
    private TextView displayDate, networkStatus;
    private Button btnContinue;


    private final DecimalFormat currency = new DecimalFormat("#########.###");
    private final DecimalFormat currency_total = new DecimalFormat("₱ ###,###,###.###");
    private BigDecimal xxP1000;
    private BigDecimal xxxP500;
    private BigDecimal xxxP200;
    private BigDecimal xxxP100;
    private BigDecimal xxxxP50;
    private BigDecimal xxxxP20;
    private BigDecimal xxxxP10;
    private BigDecimal xxxxxP5;
    private BigDecimal xxxxxP1;
    private BigDecimal xxxxC25;
    private BigDecimal xxxxC5;
    private BigDecimal xxxxxC1;

    private String currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_count);

        setupJavaClassess();
        setupWidgets();
        setupValues();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtGrandTotal.getText().toString().trim().isEmpty()) {
                    Intent intent = new Intent(Activity_CashCounter.this, Activity_CashCountSubmit.class);
                    intent.putExtra("params", String.valueOf(createJSONParameters()));
                    startActivity(intent);
                } else {
                    toast = Toast.makeText(getApplicationContext(), "Cash Details are Empty.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openDatePickerDialog){
                    OpenDialogDatePicker();
                } else {
                    Toast.makeText(getApplicationContext(), "Date Today : " + currentDate, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Setting all important UI components...*/
    private void setupWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_cashCounter);
        toolbar.setTitle("Cash Count");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * Check Internet Connectivity,
         * Displays the current connection status realtime.*/
        networkStatus = findViewById(R.id.lblNetStatus);

        /**
         * Display Labels*/
        displayDate = findViewById(R.id.lblDate);
        displayDate.setText(currentDate);
        TextView branchCode = findViewById(R.id.txtBranchCode);
        TextView branchName = findViewById(R.id.txtBranchName);
        branchName.setText(BranchName);
        branchCode.setText(BranchCode);

        /**
         * Quantity TextBox
         * Bills Quantity*/
        txtThousanBill = findViewById(R.id.txtOneThousanBill);
        txtfiveHundredBill = findViewById(R.id.txtFiveHundred);
        txtTwoHundredBill = findViewById(R.id.txtTwoHundred);
        txtOneHundredBill = findViewById(R.id.txtOneHundred);
        txtFiftyPeso = findViewById(R.id.txtFifty);
        txtTwentyPeso = findViewById(R.id.txtTwenty);
        txtTenPeso = findViewById(R.id.txtTenPeso);
        txtFivePeso = findViewById(R.id.txtFivePeso);
        txtPesoCoin = findViewById(R.id.txtOnePeso);
        txt25Cents = findViewById(R.id.txtTwentyFiveC);
        txt10Cents = findViewById(R.id.txtTenCents);
        txt5Cents = findViewById(R.id.txtFiveCents);

        /**
         * Total Textbox
         * Show total of each bills*/
        tot_thousandBill = findViewById(R.id.totOneThousan);
        tot_fiveHundredBill = findViewById(R.id.totFiveHundred);
        tot_twoHundredBill = findViewById(R.id.totTwoHundred);
        tot_oneHundredbill = findViewById(R.id.totOneHundred);
        tot_fiftyPesobill = findViewById(R.id.totFifty);
        tot_twentyPesoBill = findViewById(R.id.totTwenty);
        tot_tenPesoCoin = findViewById(R.id.totTenPeso);
        tot_fivePesoCoin = findViewById(R.id.totFivePeso);
        tot_pesoCoin = findViewById(R.id.totOnePeso);
        tot_25Cents = findViewById(R.id.totTwentyFiveC);
        tot_tenCents = findViewById(R.id.totTenCents);
        tot_fiveCents = findViewById(R.id.totFiveCents);

        btnContinue = findViewById(R.id.btnContinue);
        txtGrandTotal = findViewById(R.id.txtGrandtotal);

        /**
         * TextChanged Listeners
         * Shows the Total of each Bills*/
        txtThousanBill.addTextChangedListener(new AutoComputeTotal(txtThousanBill));
        txtfiveHundredBill.addTextChangedListener(new AutoComputeTotal(txtfiveHundredBill));
        txtTwoHundredBill.addTextChangedListener(new AutoComputeTotal(txtTwoHundredBill));
        txtOneHundredBill.addTextChangedListener(new AutoComputeTotal(txtOneHundredBill));
        txtFiftyPeso.addTextChangedListener(new AutoComputeTotal(txtFiftyPeso));
        txtTwentyPeso.addTextChangedListener(new AutoComputeTotal(txtTwentyPeso));
        txtTenPeso.addTextChangedListener(new AutoComputeTotal(txtTenPeso));
        txtFivePeso.addTextChangedListener(new AutoComputeTotal(txtFivePeso));
        txtPesoCoin.addTextChangedListener(new AutoComputeTotal(txtPesoCoin));
        txt25Cents.addTextChangedListener(new AutoComputeTotal(txt25Cents));
        txt10Cents.addTextChangedListener(new AutoComputeTotal(txt10Cents));
        txt5Cents.addTextChangedListener(new AutoComputeTotal(txt5Cents));
    }

    private void setupJavaClassess(){
        sharedPref = new SharedPref(getApplicationContext());
        try {
            openDatePickerDialog = getIntent().getExtras().getBoolean("OpenDialog");
            if (openDatePickerDialog == true) {
                OpenDialogDatePicker();
            }
        } catch(Exception e){

        }


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        currentDate = (String) android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date());

        AppData sqlStatements = AppData.getInstance(Activity_CashCounter.this);
        BranchCode = sqlStatements.getBranchCode();
        BranchName = sqlStatements.getBranchName();
    }

    private void setupValues(){
        xxP1000 = new BigDecimal("0");
        xxxP500 = new BigDecimal("0");
        xxxP200 = new BigDecimal("0");
        xxxP100 = new BigDecimal("0");
        xxxxP50 = new BigDecimal("0");
        xxxxP20 = new BigDecimal("0");
        xxxxP10 = new BigDecimal("0");
        xxxxxP5 = new BigDecimal("0");
        xxxxxP1 = new BigDecimal("0");
        xxxxC25 = new BigDecimal("0");
        xxxxC5 = new BigDecimal("0");
        xxxxxC1 = new BigDecimal("0");

        final DecimalFormat currency = new DecimalFormat("₱ ###,###,###.###");
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(connectionReceiver, intentFilter);
    }

    /*Unregister Receiver upon activity finish*/
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectionReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startParentActivity(sharedPref.MenuActivity());
        finish();
    }

    private JSONObject createJSONParameters(){
        JSONObject param = new JSONObject();
        try {
            param.put("trandate", displayDate.getText().toString());
            param.put("cn0001cx", tot_fiveCents.getText().toString());
            param.put("cn0005cx", tot_tenCents.getText().toString());
            param.put("cn0025cx", tot_25Cents.getText().toString());
            param.put("cn0001px", tot_pesoCoin.getText().toString());
            param.put("cn0005px", tot_fivePesoCoin.getText().toString());
            param.put("cn0010px",  tot_tenPesoCoin.getText().toString());
            param.put("nte0020p", tot_twentyPesoBill.getText().toString());
            param.put("nte0050p", tot_fiftyPesobill.getText().toString());
            param.put("nte0100p", tot_oneHundredbill.getText().toString());
            param.put("nte0200p", tot_twoHundredBill.getText().toString());
            param.put("nte0500p", tot_fiveHundredBill.getText().toString());
            param.put("nte1000p", tot_thousandBill.getText().toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return param;
    }

    private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int connectionState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (connectionState){
                case WifiManager.WIFI_STATE_ENABLED:
                    networkStatus.setText("Connected");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    networkStatus.setText("Disconnected");
            }
        }
    };


    /*SET PARENT ACTIVITY HERE...
    * GET CLASS NAME*/
    private void startParentActivity(String ParentActivity){
        Class<?> classActivity;
        try {
            classActivity = Class.forName(ParentActivity);
            Intent intent = new Intent(Activity_CashCounter.this, classActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Set Cash Count Date
     * */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String DateTimeDisplay = dateFormat.format(calendar.getTime());
        displayDate.setText(DateTimeDisplay);
    }

    /**
     * Dialog picker pops up if the user
     * selected the set date upon opening the cash count...*/
    private void OpenDialogDatePicker() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.setCancelable(false);
        datePicker.show(getSupportFragmentManager(), "Calendar");
    }

    /**
     * Cash Count Calculations
     * */
    private class AutoComputeTotal implements TextWatcher {

        private View view;

        private AutoComputeTotal(View view){ this.view = view; }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {
            int i = view.getId();
            if (i == R.id.txtOneThousanBill) {
                calc_thousandBills();
                calc_grandTotal();

            } else if (i == R.id.txtFiveHundred) {
                calc_fivehundredBills();
                calc_grandTotal();

            } else if (i == R.id.txtTwoHundred) {
                calc_twohundredBills();
                calc_grandTotal();

            } else if (i == R.id.txtOneHundred) {
                calc_onehundredBills();
                calc_grandTotal();

            } else if (i == R.id.txtFifty) {
                calc_fiftyBills();
                calc_grandTotal();

            } else if (i == R.id.txtTwenty) {
                calc_twentyBills();
                calc_grandTotal();

            } else if (i == R.id.txtTenPeso) {
                calc_TenPeso();
                calc_grandTotal();

            } else if (i == R.id.txtFivePeso) {
                calc_FivePeso();
                calc_grandTotal();

            } else if (i == R.id.txtOnePeso) {
                calc_OnePeso();
                calc_grandTotal();

            } else if (i == R.id.txtTwentyFiveC) {
                calc_25Cents();
                calc_grandTotal();

            } else if (i == R.id.txtTenCents) {
                calc_5Cents();
                calc_grandTotal();

            } else if (i == R.id.txtFiveCents) {
                calc_OneCent();
                calc_grandTotal();
            }
        }
    }
    private void calc_thousandBills(){
        BigDecimal total = new BigDecimal("0");
        try {
            txtThousanBill.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtThousanBill.getText().toString()));
            tot_thousandBill.setText(tot_thousandBill.getText().toString());
            if (!txtThousanBill.getText().toString().trim().isEmpty()) {
                BigDecimal bill_1000 = new BigDecimal("1000");
                total = quantity.multiply(bill_1000);
                tot_thousandBill.setText(currency.format(total));
                xxP1000 = total;
            }
        } catch (Exception e) {
            tot_thousandBill.setText(currency.format(total));
            xxP1000 = total;
        }
    }
    private void calc_fivehundredBills(){
        BigDecimal total = new BigDecimal("0");
        try{
            txtfiveHundredBill.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtfiveHundredBill.getText().toString()));
            tot_fiveHundredBill.setText(tot_fiveHundredBill.getText().toString());
            if(!txtfiveHundredBill.getText().toString().trim().isEmpty()){
                BigDecimal bill_500 = new BigDecimal("500");
                total = quantity.multiply(bill_500);
                tot_fiveHundredBill.setText(currency.format(total));
                xxxP500 = total;
            }
        } catch (Exception e){
            tot_fiveHundredBill.setText(currency.format(total));
            xxxP500 = total;
        }
    }
    private void calc_twohundredBills(){
        BigDecimal total = new BigDecimal("0");
        try{
            txtTwoHundredBill.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtTwoHundredBill.getText().toString()));
            tot_twoHundredBill.setText(tot_twoHundredBill.getText().toString());
            if(!txtTwoHundredBill.getText().toString().trim().isEmpty()){
                BigDecimal bill_200 = new BigDecimal("200");
                total = quantity.multiply(bill_200);
                tot_twoHundredBill.setText(currency.format(total));
                xxxP200 = total;
            }
        } catch (Exception e){
            tot_twoHundredBill.setText(currency.format(total));
            xxxP200 = total;
        }
    }
    private void calc_onehundredBills(){
        BigDecimal total = new BigDecimal("0");
        try{
            txtOneHundredBill.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtOneHundredBill.getText().toString()));
            tot_oneHundredbill.setText(tot_oneHundredbill.getText().toString());
            if(!txtOneHundredBill.getText().toString().trim().isEmpty()){
                BigDecimal bill_100 = new BigDecimal("100");
                total = quantity.multiply(bill_100);
                tot_oneHundredbill.setText(currency.format(total));
                xxxP100 = total;
            }
        } catch (Exception e){
            tot_oneHundredbill.setText(currency.format(total));
            xxxP100 = total;
        }
    }
    private void calc_fiftyBills(){
        BigDecimal total = new BigDecimal("0");
        try{
            txtFiftyPeso.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtFiftyPeso.getText().toString()));
            tot_fiftyPesobill.setText(tot_fiftyPesobill.getText().toString());
            if(!txtFiftyPeso.getText().toString().trim().isEmpty()){
                BigDecimal bill_50 = new BigDecimal("50");
                total = quantity.multiply(bill_50);
                tot_fiftyPesobill.setText(currency.format(total));
                xxxxP50 = total;
            }
        } catch (Exception e){
            tot_fiftyPesobill.setText(currency.format(total));
            xxxxP50 = total;
        }
    }
    private void calc_twentyBills(){
        BigDecimal total = new BigDecimal("0");
        try{
            txtTwentyPeso.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtTwentyPeso.getText().toString()));
            tot_twentyPesoBill.setText(tot_twentyPesoBill.getText().toString());
            if(!txtTwentyPeso.getText().toString().trim().isEmpty()){
                BigDecimal bill_20 = new BigDecimal("20");
                total = quantity.multiply(bill_20);
                tot_twentyPesoBill.setText(currency.format(total));
                xxxxP20 = total;
            }
        }catch (Exception e){
            tot_twentyPesoBill.setText(currency.format(total));
            xxxxP20 = total;
        }
    }
    private  void calc_TenPeso(){
        BigDecimal total = new BigDecimal("0");
        try{
            txtTenPeso.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtTenPeso.getText().toString()));
            tot_tenPesoCoin.setText(tot_tenPesoCoin.getText().toString());
            if(!txtTenPeso.getText().toString().trim().isEmpty()){
                BigDecimal bill_10 = new BigDecimal("10");
                total = quantity.multiply(bill_10);
                tot_tenPesoCoin.setText(currency.format(total));
                xxxxP10 = total;
            }
        }catch (Exception e){
            tot_tenPesoCoin.setText(currency.format(total));
            xxxxP10 = total;
        }
    }
    private void calc_FivePeso(){
        BigDecimal total = new BigDecimal("0");
        try{
            txtFivePeso.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtFivePeso.getText().toString()));
            tot_fivePesoCoin.setText(tot_fivePesoCoin.getText().toString());
            if(!txtFivePeso.getText().toString().trim().isEmpty()){
                BigDecimal bill_5 = new BigDecimal("5");
                total = quantity.multiply(bill_5);
                tot_fivePesoCoin.setText(currency.format(total));
                xxxxxP5 = total;
            }
        }catch (Exception e){
            tot_fivePesoCoin.setText(currency.format(total));
            xxxxxP5 = total;
        }
    }
    private void calc_OnePeso(){
        BigDecimal total = new BigDecimal("0");
        try{
            txtPesoCoin.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txtPesoCoin.getText().toString()));
            tot_pesoCoin.setText(tot_pesoCoin.getText().toString());
            if(!txtPesoCoin.getText().toString().trim().isEmpty()){
                BigDecimal bill_1 = new BigDecimal("1");
                total = quantity.multiply(bill_1);
                tot_pesoCoin.setText(currency.format(total));
                xxxxxP1 = total;
            }
        }catch (Exception e){
            tot_pesoCoin.setText(currency.format(total));
            xxxxxP1 = total;
        }
    }
    private void calc_25Cents(){
        BigDecimal total = new BigDecimal("0");
        try{
            txt25Cents.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txt25Cents.getText().toString()));
            tot_25Cents.setText(tot_25Cents.getText().toString());
            if(!txt25Cents.getText().toString().trim().isEmpty()){
                BigDecimal bill_25C = new BigDecimal(".25");
                total = quantity.multiply(bill_25C);
                tot_25Cents.setText(currency.format(total));
                xxxxC25 = total;
            }
        }catch (Exception e){
            tot_25Cents.setText(currency.format(total));
            xxxxC25 = total;
        }
    }
    private void calc_5Cents(){
        BigDecimal total = new BigDecimal("0");
        try{
            txt10Cents.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txt10Cents.getText().toString()));
            tot_tenCents.setText(tot_tenCents.getText().toString());
            if(!txt10Cents.getText().toString().trim().isEmpty()){
                BigDecimal bill_10C = new BigDecimal(".10");
                total = quantity.multiply(bill_10C);
                tot_tenCents.setText(currency.format(total));
                xxxxC5 = total;
            }
        }catch (Exception e){
            tot_tenCents.setText(currency.format(total));
            xxxxC5 = total;
        }
    }
    private void calc_OneCent(){
        BigDecimal total = new BigDecimal("0");
        try{
            txt5Cents.getText().toString();
            BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(txt5Cents.getText().toString()));
            tot_fiveCents.setText(tot_fiveCents.getText().toString());
            if(!txt5Cents.getText().toString().trim().isEmpty()){
                BigDecimal bill_5C = new BigDecimal(".05");
                total = quantity.multiply(bill_5C);
                tot_fiveCents.setText(currency.format(total));
                xxxxxC1 = total;
            }
        }catch (Exception e){
            tot_fiveCents.setText(currency.format(total));
            xxxxxC1 = total;
        }
    }
    private void calc_grandTotal(){
        BigDecimal grandtotal = xxP1000.add(xxxP500).add(xxxP200).add(xxxP100).add(xxxxP50).add(xxxxP20).add(xxxxP10).add(xxxxxP5).add(xxxxxP1).add(xxxxC25).add(xxxxC5).add(xxxxxC1);
        txtGrandTotal.setText(currency_total.format(grandtotal));
    }
}
