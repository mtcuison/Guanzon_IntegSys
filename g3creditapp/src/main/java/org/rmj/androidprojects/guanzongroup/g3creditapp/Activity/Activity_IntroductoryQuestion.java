package org.rmj.androidprojects.guanzongroup.g3creditapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.simple.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.ConstantsAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.FormatUIText;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Other.AutoSetBranch;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.AutoSuggestBrandModel;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pricelist.PriceFactory;
import org.rmj.gocas.pricelist.Pricelist;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

public class Activity_IntroductoryQuestion extends AppCompatActivity {
    private static final String TAG = Activity_IntroductoryQuestion.class.getSimpleName();
    private final DecimalFormat currency_total = new DecimalFormat("###,###,###.###");

    private Context context;
    private AutoSuggestBrandModel model;
    private Pricelist oPrice;
    private JSONObject params;
    private ConstantsAdapter adapter;
    private ProgressDialog loDialog;
    private AppData db;

    private Spinner spnApplicantType;
    private Spinner spnCustomerType;
    private TextInputLayout tilBranch;
    private AutoCompleteTextView txtBranchLst;
    private TextInputLayout tilBrand;
    private AutoCompleteTextView txtBrand;
    private TextInputLayout tilModel;
    private AutoCompleteTextView txtModel;
    private TextInputLayout tilDownpayment;
    private TextInputEditText txtDownpayment;
    private TextInputEditText txtTargetDate;
    private Spinner spnTermx;
    private TextInputEditText txtMonthlyPayment;
    private MaterialButton btnNext;
    private ArrayList<ArrayList<String>> brandData;
    private ArrayList<ArrayList<String>> modelData;

    private String TargDte = "";
    private String BrandID = "";
    private String ModelID = "";
    private String BrnchID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory_question);
        context = this;
        db = AppData.getInstance(context);
        model = new AutoSuggestBrandModel(context);
        adapter = new ConstantsAdapter(context);
        oPrice = PriceFactory.make(PriceFactory.ProductType.MOTORCYCLE);
        loDialog = new ProgressDialog(context);
        brandData = model.getBrandList();
        modelData = new ArrayList<>();
        setupWidgets();
        setupWidgetData();
        setupDefaultInfo();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_introQuestion);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        spnApplicantType = findViewById(R.id.spinner_cap_applicationType);
        spnCustomerType = findViewById(R.id.spinner_cap_customerType);
        tilBranch = findViewById(R.id.til_cap_branch);
        txtBranchLst = findViewById(R.id.txt_cap_branch);
        tilBrand = findViewById(R.id.til_cap_brand);
        txtBrand = findViewById(R.id.txt_cap_Brand);
        tilModel = findViewById(R.id.til_cap_model);
        txtModel = findViewById(R.id.txt_cap_model);
        tilDownpayment = findViewById(R.id.til_cap_downpayment);
        txtDownpayment = findViewById(R.id.txt_cap_downpayment);
        txtTargetDate = findViewById(R.id.txt_cap_targetDate);
        spnTermx = findViewById(R.id.txt_cap_creditTerm);
        txtMonthlyPayment = findViewById(R.id.txt_cap_monthlyPayment);
        btnNext = findViewById(R.id.btn_fragment_intro_next);
    }

    private void setupWidgetData(){
        BrnchID = new AutoSetBranch(context).getBranchCode();
        txtBranchLst.setText(new AutoSetBranch(context).getBranchName());
        spnApplicantType.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Loan_Application));
        spnApplicantType.setOnItemSelectedListener(new onApplicationSelectedListener());
        spnCustomerType.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Customer_Type));
        txtBranchLst.setOnItemClickListener(new OnBranchSelectedListener());
        txtBrand.setAdapter(getBrandListAdapter());
        //txtBranchLst.addTextChangedListener(new OnIntroQuestionTextChangeListener(txtBranchLst));
        //txtBrand.addTextChangedListener(new OnIntroQuestionTextChangeListener(txtBrand));
        //txtModel.addTextChangedListener(new OnIntroQuestionTextChangeListener(txtModel));
        //txtDownpayment.addTextChangedListener(new OnIntroQuestionTextChangeListener(txtDownpayment));
        txtBrand.setOnItemClickListener(new onBrandSelectedListener());
        txtModel.setOnItemClickListener(new OnModelSelectedListener());
        txtDownpayment.addTextChangedListener(new OnSetDownpayment());
        spnTermx.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Installment_Term));
        spnTermx.setOnItemSelectedListener(new OnTermSelectedListener());
        txtTargetDate.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(Activity_IntroductoryQuestion.this, (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String lsDate = dateFormatter.format(newDate.getTime());
                txtTargetDate.setText(lsDate);
                try {
                    String lsSelectedDate = Objects.requireNonNull(lsDate);
                    @SuppressLint("SimpleDateFormat") Date parseDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsSelectedDate);
                    @SuppressLint("SimpleDateFormat") String lsFrmDate = new SimpleDateFormat("yyyy-MM-dd").format(Objects.requireNonNull(parseDate));
                    TargDte = lsFrmDate;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            StartTime.show();
        });
        btnNext.setOnClickListener(v -> validateInfo());
    }

    private void validateInfo(){
        if(!isValidBranch()){
            new CustomToast(context, "Some important fields are empty or invalid.", CustomToast.TYPE.WARNING).show();
        } else if(!isValidBrand()){
            new CustomToast(context, "Brand name is empty or invalid", CustomToast.TYPE.WARNING).show();
        } else if(!isValidModel()){
            new CustomToast(context, "Model info is empty or invalid", CustomToast.TYPE.WARNING).show();
        } else if(!isValidDownpayment()){
            new CustomToast(context, "Downpayment is empty or invalid", CustomToast.TYPE.WARNING).show();
        } else if(!isTermValid(spnTermx.getSelectedItem().toString())) {
            new CustomToast(context, "Please select installment term", CustomToast.TYPE.WARNING).show();
        } else if(!isValidMonthlyPayment()){
            new CustomToast(context, "Some important info are invalid.", CustomToast.TYPE.WARNING).show();
        } else {
            setupGoCasInfo();
        }
    }

    private void setupGoCasInfo(){
        GOCASApplication loGoCas = new GOCASApplication();
        loGoCas.PurchaseInfo().setAppliedFor(String.valueOf(spnApplicantType.getSelectedItemPosition()));
        loGoCas.PurchaseInfo().setCustomerType(String.valueOf(spnCustomerType.getSelectedItemPosition()));
        loGoCas.PurchaseInfo().setPreferedBranch(BrnchID);
        loGoCas.PurchaseInfo().setTargetPurchase(TargDte);
        loGoCas.PurchaseInfo().setModelID(ModelID);
        loGoCas.PurchaseInfo().setDownPayment(new FormatUIText().getParseDouble(Objects.requireNonNull(txtDownpayment.getText()).toString()));
        loGoCas.PurchaseInfo().setAccountTerm(getTerm(spnTermx.getSelectedItem().toString()));
        loGoCas.PurchaseInfo().setMonthlyAmortization(new FormatUIText().getParseDouble(Objects.requireNonNull(txtMonthlyPayment.getText()).toString()));
        loGoCas.PurchaseInfo().setDateApplied(getDateApplied());
        loGoCas.PurchaseInfo().setBrandName(txtBrand.getText().toString());
        loGoCas.PurchaseInfo().setPreferedBranch(BrnchID);
        loGoCas.PurchaseInfo().setBrandName(txtBrand.getText().toString());
        new CreditApplication(context).NewCreditApplication(loGoCas, getDateTransact(), new CreditApplication.OnNewApplicationCreateListener() {
            @Override
            public void OnCreateApplication(String TransNox) {
                Intent loIntent = new Intent(context, Activity_CreditApplication.class);
                loIntent.putExtra("transno", TransNox);
                startActivity(loIntent);
                finish();
            }

            @Override
            public void OnError(String message) {
                new CustomToast(context, "Failed to create new application. Please try again", CustomToast.TYPE.WARNING).show();
                finish();
            }
        });
        Log.e(TAG, "Introductory Question Result : " + loGoCas.PurchaseInfo().toJSONString());
    }

    private String getDateTransact(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    private void setupDefaultInfo(){
        SharedPref loPreferences = new SharedPref(Objects.requireNonNull(context));
        switch (loPreferences.ProducID()){
            case "IntegSys":
                spnApplicantType.setSelection(0);
                break;
            case "Telecom":
                spnApplicantType.setSelection(3);
                break;
        }
    }

    private ArrayAdapter<String> getBrandListAdapter(){
        String[] data = new String[brandData.get(0).size()];
        for(int x = 0; x < brandData.get(0).size(); x++){
            data[x] = brandData.get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(context), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getModelsList(){
        modelData = model.getModelList(BrandID);
        String[] data = new String[modelData.get(0).size()];
        for(int x = 0; x < modelData.get(0).size(); x++){
            data[x] = modelData.get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(context), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getMotorcycleBranchList(){
        String[] data = new String[model.getMCBranchesList().get(0).size()];
        for(int x = 0; x < model.getMCBranchesList().get(0).size(); x++){
            data[x] = model.getMCBranchesList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(context), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getMobilephoneBranchList(){
        String[] data = new String[model.getMPBranchesList().get(0).size()];
        for(int x = 0; x < model.getMPBranchesList().get(0).size(); x++){
            data[x] = model.getMPBranchesList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(context), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private String getDateApplied(){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void setupModelActions(){
        try {
            params = new JSONObject();
            String lsSql = oPrice.getSQ_Model(ModelID, true, true);
            Log.e(TAG, "Model database query : " + lsSql);
            Cursor cursor = db.getReadableDb().rawQuery(lsSql, null);
            cursor.moveToFirst();
            params.put("sModelIDx", cursor.getString(cursor.getColumnIndex("sModelIDx")));
            params.put("sModelNme", cursor.getString(cursor.getColumnIndex("sModelNme")));
            params.put("nRebatesx", cursor.getString(cursor.getColumnIndex("nRebatesx")));
            params.put("nMiscChrg", cursor.getString(cursor.getColumnIndex("nMiscChrg")));
            params.put("nEndMrtgg", cursor.getString(cursor.getColumnIndex("nEndMrtgg")));
            params.put("nMinDownx", cursor.getString(cursor.getColumnIndex("nMinDownx")));
            params.put("nSelPrice", cursor.getString(cursor.getColumnIndex("nSelPrice")));
            params.put("nLastPrce", cursor.getString(cursor.getColumnIndex("nLastPrce")));

            if (oPrice.setModelInfo(params)) {
                txtDownpayment.setText(String.valueOf(oPrice.getDownPayment()));
                oPrice.setPaymentTerm(getTerm(spnTermx.getSelectedItem().toString()));
                Log.e(TAG, "Monthly : " + oPrice.getSQ_Monthly());
            }

            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isTermValid(String TermText){
        try {
            String[] data = TermText.split(" ");
            int num = Integer.parseInt(data[0]);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private int getTerm(String TermText){
        try {
            String[] data = TermText.split(" ");
            return Integer.parseInt(data[0]);
        } catch (Exception e){
            e.printStackTrace();
            return 6;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setupTermActions(){
        try {
            params = new JSONObject();
            oPrice.setPaymentTerm(getTerm(spnTermx.getSelectedItem().toString()));
            String lsSql = oPrice.getSQ_Monthly();
            Log.e(TAG, "oPrice Database Query : " + lsSql);
            Cursor cursor = db.getReadableDb().rawQuery(lsSql, null);
            cursor.moveToFirst();
            params.put("nSelPrice", cursor.getString(cursor.getColumnIndex("nSelPrice")));
            params.put("nMinDownx", cursor.getString(cursor.getColumnIndex("nMinDownx")));
            params.put("nMiscChrg", cursor.getString(cursor.getColumnIndex("nMiscChrg")));
            params.put("nRebatesx", cursor.getString(cursor.getColumnIndex("nRebatesx")));
            params.put("nEndMrtgg", cursor.getString(cursor.getColumnIndex("nEndMrtgg")));
            params.put("nAcctThru", cursor.getString(cursor.getColumnIndex("nAcctThru")));
            params.put("nFactorRt", cursor.getString(cursor.getColumnIndex("nFactorRt")));
            String lsDownpayment = Objects.requireNonNull(txtDownpayment.getText()).toString().replace(",", "");
            double ldDownpayment =  Double.parseDouble(lsDownpayment);
            oPrice.setDownPayment(ldDownpayment);
            BigDecimal price = new BigDecimal(String.valueOf(oPrice.getMonthlyAmort(params)));
            if(isTermValid(spnTermx.getSelectedItem().toString())){
                txtMonthlyPayment.setText(currency_total.format(price));
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    class onApplicationSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Motorcycle")){
                txtBranchLst.setAdapter(getMotorcycleBranchList());
            } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Mobile Phone")){
                txtBranchLst.setAdapter(getMobilephoneBranchList());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class onBrandSelectedListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(int x = 0; x < brandData.get(1).size(); x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(brandData.get(1).get(x))){
                    BrandID = brandData.get(0).get(x);
                    break;
                }
            }
            txtModel.setAdapter(getModelsList());
        }
    }

    class OnModelSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(int x = 0; x < modelData.get(0).size(); x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(modelData.get(1).get(x))){
                    ModelID = modelData.get(0).get(x);
                    break;
                }
            }
            setupModelActions();
        }
    }

    class OnTermSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != 0) {
                setupTermActions();
            } else {
                txtMonthlyPayment.setText("");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnBranchSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(spnApplicantType.getSelectedItem().toString().equalsIgnoreCase("Motorcycle")){
                for(int x = 0; x < model.getMCBranchesList().get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(model.getMCBranchesList().get(1).get(x))){
                        BrnchID = model.getMCBranchesList().get(0).get(x);
                        break;
                    }
                }
            } else if(spnApplicantType.getSelectedItem().toString().equalsIgnoreCase("Mobile Phone")){
                for(int x = 0; x < model.getMPBranchesList().get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(model.getMPBranchesList().get(1).get(x))){
                        BrnchID = model.getMPBranchesList().get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnSetDownpayment implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                setupTermActions();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class OnIntroQuestionTextChangeListener implements TextWatcher{

        View txt;

        OnIntroQuestionTextChangeListener(View view){
            this.txt = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int id = txt.getId();
            if(id == R.id.txt_cap_branch){
                isValidBranch();
            } else if(id == R.id.txt_cap_Brand){
                txtBrand.removeTextChangedListener(this);
                isValidBrand();
                txtBrand.addTextChangedListener(this);
            } else if(id == R.id.txt_cap_model){
                txtModel.removeTextChangedListener(this);
                isValidModel();
                txtModel.addTextChangedListener(this);
            } else if(id == R.id.txt_cap_downpayment){
                txtDownpayment.removeTextChangedListener(this);
                if(isValidModel()) {
                    try
                    {
                        txtDownpayment.removeTextChangedListener(this);
                        String value = Objects.requireNonNull(txtDownpayment.getText()).toString();


                        if (!value.equals(""))
                        {

                            if(value.startsWith(".")){
                                txtDownpayment.setText("0.");
                            }
                            if(value.startsWith("0") && !value.startsWith("0.")){
                                txtDownpayment.setText("");

                            }

                            String str = txtDownpayment.getText().toString().replaceAll(",", "");
                            txtDownpayment.setText(getDecimalFormattedString(str));
                            txtDownpayment.setSelection(txtDownpayment.getText().toString().length());
                        }
                        txtDownpayment.addTextChangedListener(this);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                        txtDownpayment.addTextChangedListener(this);
                    }
                }
                txtDownpayment.addTextChangedListener(this);
            }
        }
    }

    private static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        StringBuilder str3 = new StringBuilder();
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = new StringBuilder(".");
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3.append(".").append(str2);
                return str3.toString();
            }
            if (i == 3)
            {
                str3.insert(0, ",");
                i = 0;
            }
            str3.insert(0, str1.charAt(k));
            i++;
        }

    }

    /**********************************
     *
     * Validations for Brand, Model, Downpayment, Term, Monthly Payment...
     *
     *
     * */

    private void requestFocus(View view){
        if(view.requestFocus()){
           this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isValidBranch(){
        if(txtBranchLst.getText().toString().isEmpty() || BrnchID.isEmpty()){
            tilBranch.setError("Please select branch.");
            requestFocus(txtBranchLst);
            return false;
        }
        tilBranch.setErrorEnabled(false);
        return true;
    }

    private boolean isValidBrand(){
        if(isValidBranch()) {
            if (txtBrand.getText().toString().isEmpty()) {
                tilBrand.setError("Please select brand.");
                return false;
            }
            tilBrand.setErrorEnabled(false);
            return true;
        }
        txtBrand.setText("");
        requestFocus(txtBranchLst);
        return false;
    }

    private boolean isValidModel(){
        if(isValidBrand()) {
            if (txtModel.getText().toString().isEmpty()) {
                requestFocus(txtModel);
                tilModel.setError("Please select brand model.");
                return false;
            }
            tilModel.setErrorEnabled(false);
            return true;
        }
        txtModel.setText("");
        requestFocus(txtBrand);
        return false;
    }

    private boolean isValidDownpayment(){
        if (Objects.requireNonNull(txtDownpayment.getText()).toString().isEmpty()) {
            tilDownpayment.setError("Please set your desired downpayment.");
            return false;
        }
        tilDownpayment.setErrorEnabled(false);
        return true;
    }

    private boolean isValidMonthlyPayment(){
        if(Objects.requireNonNull(txtMonthlyPayment.getText()).toString().isEmpty()){
            tilDownpayment.setError("Please select your payment term.");
            return false;
        }
        tilDownpayment.setErrorEnabled(false);
        return true;
    }
}
