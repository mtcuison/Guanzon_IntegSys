package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.CoMaker;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.CreditSourceObjects;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.AutoSuggestAddress;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment_Co_MakerInfo extends Fragment {
    private static final String TAG = Fragment_Co_MakerInfo.class.getSimpleName();

    private CreditApplication poCreditApp;
    private String TransNox;
    private GOCASApplication poGoCas;

    private View v;
    private AutoSuggestAddress address;
    private String ProvID = "";
    private String TownID = "";

    private TextInputEditText tieLastname;
    private TextInputEditText tieFrstname;
    private TextInputEditText tieMiddname;
    private AutoCompleteTextView tieSuffixxx;
    private TextInputEditText tieNickname;
    private TextInputEditText tieBrthDate;
    private AutoCompleteTextView tieBrthProv;
    private AutoCompleteTextView tieBrthTown;
    private TextInputEditText tiePrmCntct;
    private TextInputEditText tieScnCntct;
    private TextInputEditText tieTrtCntct;
    private TextInputLayout tilPrmCntctPlan;
    private TextInputLayout tilScnCntctPlan;
    private TextInputLayout tilTrtCntctPlan;
    private TextInputEditText tiePrmCntctPlan;
    private TextInputEditText tieScnCntctPlan;
    private TextInputEditText tieTrtCntctPlan;
    private TextInputEditText tieFbAcctxx;
    private Spinner spnNoOfCont;
    private Spinner spnIncmSrce;
    private Spinner spnBrwrRltn;
    private LinearLayout linearPrmContact;
    private LinearLayout linearScnContact;
    private LinearLayout linearTrtContact;
    private Spinner spnPrmCntct;
    private Spinner spnScnCntct;
    private Spinner spnTrtCntct;

    private MaterialButton btnPrvs;
    private MaterialButton btnNext;

    public Fragment_Co_MakerInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_co_maker_info, container, false);
        address = new AutoSuggestAddress(getActivity());
        poCreditApp = new CreditApplication(getActivity());
        TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        setupWidgets();
        setupActions();
        return v;
    }

    private void setupWidgets(){
        tieLastname = v.findViewById(R.id.tie_cap_cmrLastname);
        tieFrstname = v.findViewById(R.id.tie_cap_cmrFirstname);
        tieMiddname = v.findViewById(R.id.tie_cap_cmrMiddname);
        tieSuffixxx = v.findViewById(R.id.tie_cap_cmrSuffix);
        tieNickname = v.findViewById(R.id.tie_cap_cmrNickname);
        tieBrthDate = v.findViewById(R.id.tie_cap_cmrBirthdate);
        tieBrthProv = v.findViewById(R.id.tie_cap_cmrBirthProv);
        tieBrthTown = v.findViewById(R.id.tie_cap_cmrBirthTown);
        tiePrmCntct = v.findViewById(R.id.tie_cap_cmrPrimaryContactNo);
        tieScnCntct = v.findViewById(R.id.tie_cap_cmrSecondaryContactNo);
        tieTrtCntct = v.findViewById(R.id.tie_cap_cmrTertiaryContactNo);
        tilPrmCntctPlan = v.findViewById(R.id.til_cap_cmrPrimaryCntctPlan);
        tilScnCntctPlan = v.findViewById(R.id.til_cap_cmrSecondaryCntctPlan);
        tilTrtCntctPlan = v.findViewById(R.id.til_cap_cmrTertiaryCntctPlan);
        tiePrmCntctPlan = v.findViewById(R.id.tie_cap_cmrPrimaryCntctPlan);
        tieScnCntctPlan = v.findViewById(R.id.tie_cap_cmrSecondaryCntctPlan);
        tieTrtCntctPlan = v.findViewById(R.id.tie_cap_cmrTertiaryCntctPlan);
        tieFbAcctxx = v.findViewById(R.id.tie_cap_cmrFacebookacc);
        spnIncmSrce = v.findViewById(R.id.spinner_cap_cmrIncomeSrc);
        spnBrwrRltn = v.findViewById(R.id.spinner_cap_cmrBarrowerRelation);
        spnNoOfCont = v.findViewById(R.id.spinner_cap_cmrNoOfContacts);
        linearPrmContact = v.findViewById(R.id.linear_cmrPrimaryContact);
        linearScnContact = v.findViewById(R.id.linear_cmrSecondaryContact);
        linearTrtContact = v.findViewById(R.id.linear_cmrTertiaryContact);
        spnPrmCntct = v.findViewById(R.id.spinner_cap_cmrPrimaryCntctStats);
        spnScnCntct = v.findViewById(R.id.spinner_cap_cmrSecondaryCntctStats);
        spnTrtCntct = v.findViewById(R.id.spinner_cap_cmrTertiaryCntctStats);
        btnPrvs = v.findViewById(R.id.btn_fragment_cmr_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_cmr_next);
    }

    private void setupActions(){
        tieBrthProv.setAdapter(getProvinceList());
        tieBrthProv.setOnItemClickListener(new OnAddressSelectedListener(tieBrthProv));
        tieBrthTown.setOnItemClickListener(new OnAddressSelectedListener(tieBrthTown));
        tieBrthDate.addTextChangedListener(new OnBirthDateSetListener());
        spnIncmSrce.setAdapter(getIncomeSource());
        spnBrwrRltn.setAdapter(getBarrowerRelation());
        spnNoOfCont.setAdapter(getMobileCount());
        spnNoOfCont.setOnItemSelectedListener(new OnNumberOfContactSelectedLister());
        spnPrmCntct.setAdapter(getNetworkStat());
        spnPrmCntct.setOnItemSelectedListener(new OnNetworkStatListener(spnPrmCntct));
        spnScnCntct.setAdapter(getNetworkStat());
        spnScnCntct.setOnItemSelectedListener(new OnNetworkStatListener(spnScnCntct));
        spnTrtCntct.setAdapter(getNetworkStat());
        spnTrtCntct.setOnItemSelectedListener(new OnNetworkStatListener(spnTrtCntct));
        btnPrvs.setOnClickListener(new OnButtonClickListener(btnPrvs));
        btnNext.setOnClickListener(new OnButtonClickListener(btnNext));
    }

    private ArrayAdapter<String> getProvinceList(){
        String[] data = new String[address.getProvinceList().get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getProvinceList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getTownList(){
        String[] data = new String[address.getTownList(ProvID).get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getTownList(ProvID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getMobileCount(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getCount);
    }

    private ArrayAdapter<String> getNetworkStat(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getNetworkStat);
    }

    private ArrayAdapter<String> getIncomeSource(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getComakerIncomeSource);
    }

    private ArrayAdapter<String> getBarrowerRelation(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getComakerRelation);
    }

    class OnAddressSelectedListener implements AdapterView.OnItemClickListener{

        View autoView;

        OnAddressSelectedListener(View view){
            this.autoView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(autoView.getId() == R.id.tie_cap_cmrBirthProv){
                for(int x = 0; x < address.getProvinceList().get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                        ProvID = address.getProvinceList().get(0).get(x);
                        tieBrthTown.setAdapter(getTownList());
                        break;
                    }
                }
            } else if(autoView.getId() == R.id.tie_cap_cmrBirthTown){
                for(int x = 0; x < address.getTownList(ProvID).get(1).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                        TownID = address.getTownList(ProvID).get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnNumberOfContactSelectedLister implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0){
                linearPrmContact.setVisibility(View.VISIBLE);
                linearScnContact.setVisibility(View.GONE);
                linearTrtContact.setVisibility(View.GONE);
            } else if(position == 1){
                linearPrmContact.setVisibility(View.VISIBLE);
                linearScnContact.setVisibility(View.VISIBLE);
                linearTrtContact.setVisibility(View.GONE);
            } else {
                linearPrmContact.setVisibility(View.VISIBLE);
                linearScnContact.setVisibility(View.VISIBLE);
                linearTrtContact.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnNetworkStatListener implements AdapterView.OnItemSelectedListener{

        View spnView;

        OnNetworkStatListener(View view){
            this.spnView = view;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(spnView.getId() == R.id.spinner_cap_cmrPrimaryCntctStats){
                if(position == 1){
                    tilPrmCntctPlan.setVisibility(View.VISIBLE);
                } else {
                    tilPrmCntctPlan.setVisibility(View.GONE);
                }
            } else if(spnView.getId() == R.id.spinner_cap_cmrSecondaryCntctStats){
                if(position == 1){
                    tilScnCntctPlan.setVisibility(View.VISIBLE);
                } else {
                    tilScnCntctPlan.setVisibility(View.GONE);
                }
            } else if(spnView.getId() == R.id.spinner_cap_cmrTertiaryCntctStats){
                if(position == 1){
                    tilTrtCntctPlan.setVisibility(View.VISIBLE);
                } else {
                    tilTrtCntctPlan.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnButtonClickListener implements View.OnClickListener{

        View btnView;

        OnButtonClickListener(View view){
            this.btnView = view;
        }

        @Override
        public void onClick(View v) {
            if(btnView.getId() == R.id.btn_fragment_cmr_prevs){
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).getInstance().setCurrentItem(9, true);
            } else if(btnView.getId() == R.id.btn_fragment_cmr_next){
                if(isDataValid()) {
                    setupCoMakerInfo();
                }
            }
        }
    }

    private void setupCoMakerInfo(){
        poGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        poGoCas.CoMakerInfo().setLastName(Objects.requireNonNull(tieLastname.getText()).toString());
        poGoCas.CoMakerInfo().setFirstName(Objects.requireNonNull(tieFrstname.getText()).toString());
        poGoCas.CoMakerInfo().setMiddleName(Objects.requireNonNull(tieMiddname.getText()).toString());
        poGoCas.CoMakerInfo().setSuffixName(Objects.requireNonNull(tieSuffixxx.getText()).toString());
        poGoCas.CoMakerInfo().setNickName(Objects.requireNonNull(tieNickname.getText()).toString());
        poGoCas.CoMakerInfo().setBirthdate(getDateFormat());
        poGoCas.CoMakerInfo().setBirthPlace(TownID);
        poGoCas.CoMakerInfo().setIncomeSource(String.valueOf(spnIncmSrce.getSelectedItemPosition()));
        poGoCas.CoMakerInfo().setRelation(String.valueOf(spnBrwrRltn.getSelectedItemPosition()));
        setupContactNo();
        poGoCas.CoMakerInfo().setFBAccount(Objects.requireNonNull(tieFbAcctxx.getText()).toString());
        Log.e(TAG, "Co-Maker information result : " + poGoCas.CoMakerInfo().toJSONString());
        new CreditApplication(getActivity()).UpdateApplicationInfo(poGoCas, TransNox);
        ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).getInstance().setCurrentItem(11, true);
    }

    private void setupContactNo(){
        if(spnNoOfCont.getSelectedItem().toString().equalsIgnoreCase("1")){
            poGoCas.CoMakerInfo().setMobileNoQty(1);
            poGoCas.CoMakerInfo().setMobileNo(0, Objects.requireNonNull(tiePrmCntct.getText()).toString());
            if(spnPrmCntct.getSelectedItemPosition() == 1){
                poGoCas.CoMakerInfo().IsMobilePostpaid(0, "1");
                poGoCas.CoMakerInfo().setPostPaidYears(0, Integer.parseInt(Objects.requireNonNull(tiePrmCntctPlan.getText()).toString()));
            }
        } else if(spnNoOfCont.getSelectedItem().toString().equalsIgnoreCase("2")){
            poGoCas.CoMakerInfo().setMobileNoQty(1);
            poGoCas.CoMakerInfo().setMobileNo(0, Objects.requireNonNull(tiePrmCntct.getText()).toString());
            if(spnPrmCntct.getSelectedItemPosition() == 1){
                poGoCas.CoMakerInfo().IsMobilePostpaid(0, "1");
                poGoCas.CoMakerInfo().setPostPaidYears(0, Integer.parseInt(Objects.requireNonNull(tiePrmCntctPlan.getText()).toString()));
            }
            poGoCas.CoMakerInfo().setMobileNoQty(2);
            poGoCas.CoMakerInfo().setMobileNo(1, Objects.requireNonNull(tieScnCntct.getText()).toString());
            if(spnScnCntct.getSelectedItemPosition() == 1){
                poGoCas.CoMakerInfo().IsMobilePostpaid(1, "1");
                poGoCas.CoMakerInfo().setPostPaidYears(1, Integer.parseInt(Objects.requireNonNull(tieScnCntctPlan.getText()).toString()));
            }
        } else if(spnNoOfCont.getSelectedItem().toString().equalsIgnoreCase("3")){
            poGoCas.CoMakerInfo().setMobileNoQty(1);
            poGoCas.CoMakerInfo().setMobileNo(0, Objects.requireNonNull(tiePrmCntct.getText()).toString());
            if(spnPrmCntct.getSelectedItemPosition() == 1){
                poGoCas.CoMakerInfo().IsMobilePostpaid(0, "1");
                poGoCas.CoMakerInfo().setPostPaidYears(0, Integer.parseInt(Objects.requireNonNull(tiePrmCntctPlan.getText()).toString()));
            }
            poGoCas.CoMakerInfo().setMobileNoQty(2);
            poGoCas.CoMakerInfo().setMobileNo(1, Objects.requireNonNull(tieScnCntct.getText()).toString());
            if(spnScnCntct.getSelectedItemPosition() == 1){
                poGoCas.CoMakerInfo().IsMobilePostpaid(1, "1");
                poGoCas.CoMakerInfo().setPostPaidYears(1, Integer.parseInt(Objects.requireNonNull(tieScnCntctPlan.getText()).toString()));
            }
            poGoCas.CoMakerInfo().setMobileNoQty(3);
            poGoCas.CoMakerInfo().setMobileNo(2, Objects.requireNonNull(tieTrtCntct.getText()).toString());
            if(spnTrtCntct.getSelectedItemPosition() == 1){
                poGoCas.CoMakerInfo().IsMobilePostpaid(2, "1");
                poGoCas.CoMakerInfo().setPostPaidYears(2, Integer.parseInt(Objects.requireNonNull(tieTrtCntctPlan.getText()).toString()));
            }
        }
    }

    class OnBirthDateSetListener implements TextWatcher{
        Calendar cal = Calendar.getInstance();
        String current = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @SuppressLint("DefaultLocale")
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    String ddmmyyyy = "DDMMYYYY";
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                tieBrthDate.setText(current);
                tieBrthDate.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getDateFormat(){
        try{
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse((Objects.requireNonNull(tieBrthDate.getText()).toString()));
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    private boolean isDataValid(){
        return isNameValid() &&
                isBirthDateValid() &&
                isContactValid();
    }

    private boolean isNameValid(){
        if(Objects.requireNonNull(tieLastname.getText()).toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter last name", Toast.LENGTH_LONG).show();
            requestFocus(tieLastname);
            return false;
        }
        if(Objects.requireNonNull(tieFrstname.getText()).toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter first name", Toast.LENGTH_LONG).show();
            requestFocus(tieFrstname);
            return false;
        }
        if(Objects.requireNonNull(tieMiddname.getText()).toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter middle name", Toast.LENGTH_LONG).show();
            requestFocus(tieMiddname);
            return false;
        }
        return true;
    }

    private boolean isBirthDateValid(){
        try {
            @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("dd/MM/yyyy").parse((Objects.requireNonNull(tieBrthDate.getText()).toString()));
            @SuppressLint("SimpleDateFormat") String lsBDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            if (lsBDate.trim().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter birth date", Toast.LENGTH_LONG).show();
                requestFocus(tieBrthDate);
                return false;
            }

        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Invalid Birth Date", Toast.LENGTH_LONG).show();
            requestFocus(tieBrthDate);
            return false;
        }
        return true;
    }

    private boolean isContactValid(){
        if(Objects.requireNonNull(tiePrmCntct.getText()).toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Please enter contact no", Toast.LENGTH_LONG).show();
            requestFocus(tiePrmCntct);
            return false;
        }
        return true;
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
