package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Personal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.CreditSourceObjects;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
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
public class Fragment_PersonalInfo extends Fragment {
    private static final String TAG = Fragment_PersonalInfo.class.getSimpleName();

    private View v;
    private String applGender = "";
    private AutoSuggestAddress address;
    private GOCASApplication loGoCas;
    private String ProvID = "";
    private String TownID = "";
    private String CtznID = "";

    private TextInputLayout tilLastname;
    private TextInputLayout tilFrstname;
    private TextInputLayout tilMiddname;
    private TextInputLayout tilSuffixxx;
    private TextInputLayout tilBrthDate;
    private TextInputLayout tilBrthProv;
    private TextInputLayout tilBrthTown;
    private TextInputLayout tilNtnlty;
    private TextInputLayout tilMtherNme;
    private TextInputLayout tilPrmCntct;
    private TextInputLayout tilScnCntct;
    private TextInputLayout tilTrtCntct;
    private TextInputLayout tilPrmryPlan;
    private TextInputLayout tilScndryPln;
    private TextInputLayout tilTrtryPlan;
    private TextInputLayout tilEmailAdd;
    private TextInputLayout tilViberAdd;
    private TextInputEditText tiePrmryPlan;
    private TextInputEditText tieScndryPln;
    private TextInputEditText tieTrtryPlan;
    private TextInputEditText tieLastname;
    private TextInputEditText tieFirstname;
    private TextInputEditText tieMiddname;
    private TextInputEditText tieNickname;
    private TextInputEditText tieBirthdate;
    private TextInputEditText tiePrimaryContact;
    private TextInputEditText tieScndaryContact;
    private TextInputEditText tieTrTiaryContact;
    private TextInputEditText tieTelephone;
    private TextInputEditText tieEmailAdd;
    private TextInputEditText tieFbAccntx;
    private TextInputEditText tieViberAccx;
    private TextInputEditText tieMothrName;
    private AutoCompleteTextView tieSuffix;
    private AutoCompleteTextView tieBpTownx;
    private AutoCompleteTextView tieBpPrvnx;
    private AutoCompleteTextView tieNtnlty;
    private RadioGroup rgGender;
    private Spinner spnrCvlStatus;
    private Spinner spnCntctNo;
    private Spinner spnPrmryCt;
    private Spinner spnScndCnt;
    private Spinner spnTrtryCt;
    private LinearLayout linearScndaryCt;
    private LinearLayout linearTrtiaryCt;
    private MaterialButton btnNext;
    private TextView lblGenderErrorMsg;
    private TextView lblCvlStatErrMsg;

    public Fragment_PersonalInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_personal_info, container, false);
        address = new AutoSuggestAddress(getActivity());
        setupWidgets();
        setupAdapterData();
        return v;
    }

    private void setupWidgets(){
        tieLastname = v.findViewById(R.id.tie_cap_lastname);
        tieFirstname = v.findViewById(R.id.tie_cap_firstname);
        tieSuffix = v.findViewById(R.id.tie_cap_suffix);
        tilSuffixxx = v.findViewById(R.id.til_cap_suffix);
        tieMiddname = v.findViewById(R.id.tie_cap_middname);
        tieNickname = v.findViewById(R.id.tie_cap_nickname);
        tieBirthdate = v.findViewById(R.id.tie_cap_applBirthdate);
        tieBpPrvnx = v.findViewById(R.id.autoComplete_applBpProvince);
        tieBpTownx = v.findViewById(R.id.autoComplete_applBpTown);
        rgGender = v.findViewById(R.id.radiogroup_gender);
        spnrCvlStatus = v.findViewById(R.id.spinner_cap_civilStats);
        tieNtnlty = v.findViewById(R.id.tie_cap_applNtnlty);
        spnCntctNo = v.findViewById(R.id.spinner_cap_NoOfContacts);
        linearScndaryCt = v.findViewById(R.id.linear_secondaryContact);
        linearTrtiaryCt = v.findViewById(R.id.linear_tertiaryContact);
        spnPrmryCt = v.findViewById(R.id.spinner_cap_PrimaryCntctStats);
        tilPrmryPlan = v.findViewById(R.id.til_cap_primaryCntctPlan);
        tiePrmryPlan = v.findViewById(R.id.tie_cap_primaryCntctPlan);
        tilScndryPln = v.findViewById(R.id.til_cap_secondaryCntctPlan);
        tieScndryPln = v.findViewById(R.id.tie_cap_secondaryCntctPlan);
        tilTrtryPlan = v.findViewById(R.id.til_cap_tertiaryCntctPlan);
        tieTrtryPlan = v.findViewById(R.id.tie_cap_tertiaryCntctPlan);
        spnScndCnt = v.findViewById(R.id.spinner_cap_secondaryCntctStats);
        spnTrtryCt = v.findViewById(R.id.spinner_cap_tertiaryCntctStats);
        tiePrimaryContact = v.findViewById(R.id.tie_cap_primaryContactNo);
        tieScndaryContact = v.findViewById(R.id.tie_cap_secondaryContactNo);
        tieTrTiaryContact = v.findViewById(R.id.tie_cap_tertiaryContactNo);
        tieTelephone = v.findViewById(R.id.tie_cap_applTelNo);
        tieEmailAdd = v.findViewById(R.id.tie_cap_emailadd);
        tieFbAccntx = v.findViewById(R.id.tie_cap_facebookacc);
        tieViberAccx = v.findViewById(R.id.tie_cap_viberAcc);
        tieMothrName = v.findViewById(R.id.tie_cap_motherName);
        btnNext = v.findViewById(R.id.btn_fragment_intro_next);
        lblGenderErrorMsg = v.findViewById(R.id.lbl_genderErrorMessage);
        lblCvlStatErrMsg = v.findViewById(R.id.lbl_CvlStatErrorMessage);

        tilLastname = v.findViewById(R.id.til_cap_lastname);
        tilFrstname = v.findViewById(R.id.til_cap_firstname);
        tilMiddname = v.findViewById(R.id.til_cap_middname);
        tilBrthDate = v.findViewById(R.id.til_cap_applBirthdate);
        tilBrthProv = v.findViewById(R.id.til_cap_applBirthProv);
        tilBrthTown = v.findViewById(R.id.til_cap_applBirthTown);
        tilNtnlty = v.findViewById(R.id.til_cap_applNtnlty);
        tilMtherNme = v.findViewById(R.id.til_cap_motherName);
        tilPrmCntct = v.findViewById(R.id.til_cap_primaryContactNo);
        tilScnCntct = v.findViewById(R.id.til_cap_secondaryContactNo);
        tilTrtCntct = v.findViewById(R.id.til_cap_tertiaryContactNo);
        tilEmailAdd = v.findViewById(R.id.til_cap_emailadd);
        tilViberAdd = v.findViewById(R.id.til_cap_viberAcc);
    }

    private void setupAdapterData(){
        tieLastname.addTextChangedListener(new PersonalInfoValidatorListener(tieLastname));
        tieFirstname.addTextChangedListener(new PersonalInfoValidatorListener(tieFirstname));
        tieMiddname.addTextChangedListener(new PersonalInfoValidatorListener(tieMiddname));
        tieBirthdate.addTextChangedListener(new PersonalInfoValidatorListener(tieBirthdate));
        tieBpPrvnx.addTextChangedListener(new PersonalInfoValidatorListener(tieBpPrvnx));
        tieBpTownx.addTextChangedListener(new PersonalInfoValidatorListener(tieBpTownx));
        tieNtnlty.addTextChangedListener(new PersonalInfoValidatorListener(tieNtnlty));
        tiePrimaryContact.addTextChangedListener(new PersonalInfoValidatorListener(tiePrimaryContact));
        tieSuffix.setAdapter(getSuffixRecommendations());
        tieSuffix.setThreshold(1);
        tieBirthdate.addTextChangedListener(new OnBirtSetListener());
        tieBpPrvnx.setAdapter(getProvinceList());
        tieBpPrvnx.setThreshold(1);
        tieBpPrvnx.setOnItemClickListener(new OnProvinceSelectedListener());
        tieBpTownx.setAdapter(getTownList());
        tieBpTownx.setOnItemClickListener(new OnTownSelectedListener());
        tieBpTownx.setThreshold(1);
        spnCntctNo.setAdapter(getCount());
        spnCntctNo.setOnItemSelectedListener(new OnContactNoSelectionListener());
        tieNtnlty.setAdapter(getNationalityList());
        tieNtnlty.setOnItemClickListener(new OnCitizenshipSelectedListener());
        tieFbAccntx.addTextChangedListener(new PersonalInfoValidatorListener(tieFbAccntx));
        spnPrmryCt.setAdapter(getNetworkStatus());
        spnPrmryCt.setOnItemSelectedListener(new OnNetworkStatusListener(spnPrmryCt));
        spnScndCnt.setAdapter(getNetworkStatus());
        spnScndCnt.setOnItemSelectedListener(new OnNetworkStatusListener(spnScndCnt));
        spnTrtryCt.setAdapter(getNetworkStatus());
        spnTrtryCt.setOnItemSelectedListener(new OnNetworkStatusListener(spnTrtryCt));
        rgGender.setOnCheckedChangeListener(new GenderSelectionListener());
        spnrCvlStatus.setAdapter(getCivilStatusList());
        btnNext.setOnClickListener(new ButtonClick());
    }

    private boolean isDataValid(){
        if(!isLastNameValid()){
            new CustomToast(getActivity(), "Please enter last name", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isFirstNameValid()){
            new CustomToast(getActivity(), "Please enter first name", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isMiddNameValid()){
            new CustomToast(getActivity(), "Please enter middle name", CustomToast.TYPE.WARNING).show();
            return false;
        } if(!isSuffixValid()) {
            new CustomToast(getActivity(), "Invalid suffix input. Leave blank if not applicable", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isBirthdateValid()){
            new CustomToast(getActivity(), "Birth date is empty or invalid. Please check.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isBirthPlaceValid()){
            new CustomToast(getActivity(), "Invalid birthplace input", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isGenderValid()){
            new CustomToast(getActivity(), "Please select gender", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isCivilStatValid()){
            new CustomToast(getActivity(), "Please select civil status.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isCitizenshipValid()) {
            new CustomToast(getActivity(), "Please enter citizenship.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isMaidentNameValid()){
            new CustomToast(getActivity(), "Please enter maiden name", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isPrimaryContactValid()){
            new CustomToast(getActivity(), "Please enter primary contact number.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isContactValid()){
            new CustomToast(getActivity(), "Contact info invalid. Please check.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(hasDuplicateContact()){
            new CustomToast(getActivity(), "Contact info has been duplicated. Please check.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isEmailValid()){
            new CustomToast(getActivity(), "Invalid email address", CustomToast.TYPE.WARNING).show();
            return false;
        }
        return true;
    }

    private void setupPersonalInfo(){
        String lsTransno = new Activity_CreditApplication().getInstance().getTransNox();
        loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(lsTransno);
        loGoCas.ApplicantInfo().setLastName(Objects.requireNonNull(tieLastname.getText()).toString());
        loGoCas.ApplicantInfo().setFirstName(Objects.requireNonNull(tieFirstname.getText()).toString());
        loGoCas.ApplicantInfo().setMiddleName(Objects.requireNonNull(tieMiddname.getText()).toString());
        loGoCas.ApplicantInfo().setSuffixName(Objects.requireNonNull(tieSuffix.getText()).toString());
        loGoCas.ApplicantInfo().setNickName(Objects.requireNonNull(tieNickname.getText()).toString());
        loGoCas.ApplicantInfo().setBirthdate(getDateFormat());
        loGoCas.ApplicantInfo().setBirthPlace(TownID);
        loGoCas.ApplicantInfo().setGender(applGender);
        loGoCas.ApplicantInfo().setCivilStatus(getCivilStatus());
        loGoCas.ApplicantInfo().setCitizenship(CtznID);
        loGoCas.ApplicantInfo().setMaidenName(Objects.requireNonNull(tieMothrName.getText()).toString());
        loGoCas.ApplicantInfo().setMaidenName(Objects.requireNonNull(tieMothrName.getText()).toString());
        setupMobileNo();
        loGoCas.ApplicantInfo().setPhoneNoQty(1);
        loGoCas.ApplicantInfo().setPhoneNo(0, Objects.requireNonNull(tieTelephone.getText()).toString());
        loGoCas.ApplicantInfo().setEmailAddQty(1);
        loGoCas.ApplicantInfo().setEmailAddress(0, Objects.requireNonNull(tieEmailAdd.getText()).toString());
        loGoCas.ApplicantInfo().setFBAccount(Objects.requireNonNull(tieFbAccntx.getText()).toString());
        loGoCas.ApplicantInfo().setViberAccount(Objects.requireNonNull(tieViberAccx.getText()).toString());
        Log.e(TAG, "Personal information result : " + loGoCas.ApplicantInfo().toJSONString());
        new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, lsTransno);
    }

    private void setupMobileNo(){
        if(spnCntctNo.getSelectedItem().toString().equalsIgnoreCase("1")){
            loGoCas.ApplicantInfo().setMobileNoQty(1);
            loGoCas.ApplicantInfo().setMobileNo(0, Objects.requireNonNull(tiePrimaryContact.getText()).toString());
            if(spnPrmryCt.getSelectedItemPosition() == 1) {
                loGoCas.ApplicantInfo().IsMobilePostpaid(0, "1");
                loGoCas.ApplicantInfo().setPostPaidYears(0, getParseInt(Objects.requireNonNull(tiePrmryPlan.getText()).toString()));
            }
        } else if(spnCntctNo.getSelectedItem().toString().equalsIgnoreCase("2")) {
            loGoCas.ApplicantInfo().setMobileNoQty(1);
            loGoCas.ApplicantInfo().setMobileNo(0, Objects.requireNonNull(tiePrimaryContact.getText()).toString());
            if (spnPrmryCt.getSelectedItemPosition() == 1) {
                loGoCas.ApplicantInfo().IsMobilePostpaid(0, "1");
                loGoCas.ApplicantInfo().setPostPaidYears(0, getParseInt(Objects.requireNonNull(tiePrmryPlan.getText()).toString()));
            }
            loGoCas.ApplicantInfo().setMobileNoQty(2);
            loGoCas.ApplicantInfo().setMobileNo(1, Objects.requireNonNull(tieScndaryContact.getText()).toString());
            if(spnScndCnt.getSelectedItemPosition() == 1) {
                loGoCas.ApplicantInfo().IsMobilePostpaid(1, "1");
                loGoCas.ApplicantInfo().setPostPaidYears(1, getParseInt(Objects.requireNonNull(tieScndryPln.getText()).toString()));
            }
        } else if(spnCntctNo.getSelectedItem().toString().equalsIgnoreCase("3")) {
            loGoCas.ApplicantInfo().setMobileNoQty(1);
            loGoCas.ApplicantInfo().setMobileNo(0, Objects.requireNonNull(tiePrimaryContact.getText()).toString());
            if(spnPrmryCt.getSelectedItemPosition() == 1) {
                loGoCas.ApplicantInfo().IsMobilePostpaid(0, "1");
                loGoCas.ApplicantInfo().setPostPaidYears(0, getParseInt(Objects.requireNonNull(tiePrmryPlan.getText()).toString()));
            }
            loGoCas.ApplicantInfo().setMobileNoQty(2);
            loGoCas.ApplicantInfo().setMobileNo(1, Objects.requireNonNull(tieScndaryContact.getText()).toString());
            if(spnScndCnt.getSelectedItemPosition() == 1) {
                loGoCas.ApplicantInfo().IsMobilePostpaid(1, "1");
                loGoCas.ApplicantInfo().setPostPaidYears(1, getParseInt(Objects.requireNonNull(tieScndryPln.getText()).toString()));
            }
            loGoCas.ApplicantInfo().setMobileNoQty(3);
            loGoCas.ApplicantInfo().setMobileNo(2, Objects.requireNonNull(tieTrTiaryContact.getText()).toString());
            if(spnTrtryCt.getSelectedItemPosition() == 1) {
                loGoCas.ApplicantInfo().IsMobilePostpaid(2, "1");
                loGoCas.ApplicantInfo().setPostPaidYears(2, getParseInt(Objects.requireNonNull(tieTrtryPlan.getText()).toString()));
            }
        }
    }

    private int getParseInt(String value){
        try{
            return Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private ArrayAdapter<String> getSuffixRecommendations(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getSuffix);
    }

    private ArrayAdapter<String> getCivilStatusList(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getCivilStatus);
    }

    private ArrayAdapter<String> getProvinceList(){
        String[] arr_name = new String[address.getProvinceList().get(1).size()];
        for(int x = 0; x < arr_name.length; x++){
            arr_name[x] = address.getProvinceList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, arr_name);
    }

    private ArrayAdapter<String> getTownList(){
        String[] arr_name = new String[address.getTownList(ProvID).get(1).size()];
        for(int x = 0; x < arr_name.length; x++){
            arr_name[x] = address.getTownList(ProvID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, arr_name);
    }

    private ArrayAdapter<String> getNationalityList(){
        String[] arr_name = new String[address.getNationalityList().get(1).size()];
        for(int x = 0; x < address.getNationalityList().get(1).size(); x++){
            arr_name[x] = address.getNationalityList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, arr_name);
    }

    private ArrayAdapter<String> getCount(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getCount);
    }

    private ArrayAdapter<String> getNetworkStatus(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getNetworkStat);
}

    @SuppressLint("SimpleDateFormat")
    private String getDateFormat(){
        try{
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse((Objects.requireNonNull(tieBirthdate.getText()).toString()));
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    private String getCivilStatus(){
        switch (spnrCvlStatus.getSelectedItemPosition()){
            case 0:
                return "";
            case 1:
                return "0";
            case 2:
                return "1";
            case 3:
                return "2";
            case 4:
                return "3";
            case 5:
                return "4";
            case 6:
                return "5";
        }
        return "";
    }

    class OnBirtSetListener implements TextWatcher{
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

                    mon = mon < 1 ? 1 : Math.min(mon, 12);
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900: Math.min(year, Calendar.getInstance().get(Calendar.YEAR));
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = Math.max(sel, 0);
                current = clean;
                tieBirthdate.setText(current);
                tieBirthdate.setSelection(Math.min(sel, current.length()));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class GenderSelectionListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.rb_cap_genderMale){
                applGender = "0";
            } else if (checkedId == R.id.rb_cap_genderFemale) {
                applGender = "1";
            } else if(checkedId == R.id.rb_cap_genderLGBT){
                applGender = "2";
            }
        }
    }

    class OnProvinceSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(int x = 0; x < address.getProvinceList().get(1).size(); x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                    ProvID = address.getProvinceList().get(0).get(x);
                    break;
                }
            }
            tieBpTownx.setAdapter(getTownList());
        }
    }

    class OnTownSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(int x = 0;  x < address.getTownList(ProvID).get(0).size(); x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                    TownID = address.getTownList(ProvID).get(0).get(x);
                }
            }
        }
    }

    class ButtonClick implements View.OnClickListener{

        @SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            if(isDataValid()) {
                setupPersonalInfo();
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(1, true);
            }
        }
    }

    class OnContactNoSelectionListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    tieScndaryContact.setText("");
                    tieTrTiaryContact.setText("");
                    linearScndaryCt.setVisibility(View.GONE);
                    linearTrtiaryCt.setVisibility(View.GONE);
                    break;
                case 1:
                    tieTrTiaryContact.setText("");
                    linearScndaryCt.setVisibility(View.VISIBLE);
                    linearTrtiaryCt.setVisibility(View.GONE);
                    break;
                case 2:
                    linearScndaryCt.setVisibility(View.VISIBLE);
                    linearTrtiaryCt.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnNetworkStatusListener implements AdapterView.OnItemSelectedListener{

        View linearView;

        OnNetworkStatusListener(View view){
            this.linearView = view;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (linearView.getId() == R.id.spinner_cap_PrimaryCntctStats) {
                switch (position){
                    case 0:
                        tilPrmryPlan.setVisibility(View.GONE);
                        break;
                    case 1:
                        tilPrmryPlan.setVisibility(View.VISIBLE);
                        break;
                }
            } else if (linearView.getId() == R.id.spinner_cap_secondaryCntctStats) {
                switch (position){
                    case 0:
                        tilScndryPln.setVisibility(View.GONE);
                        break;
                    case 1:
                        tilScndryPln.setVisibility(View.VISIBLE);
                        break;
                }
            } else if (linearView.getId() == R.id.spinner_cap_tertiaryCntctStats) {
                switch (position){
                    case 0:
                        tilTrtryPlan.setVisibility(View.GONE);
                        break;
                    case 1:
                        tilTrtryPlan.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnCitizenshipSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int count = address.getNationalityList().get(1).size();
            for(int x = 0; x < count; x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getNationalityList().get(1).get(x))){
                    CtznID = address.getNationalityList().get(0).get(x);
                }
            }
        }
    }

    /**
     * Input validations methods...
     * ***************************************************************************************************
     * ***************************************************************************************************/

    class PersonalInfoValidatorListener implements TextWatcher{

        View txt;

        PersonalInfoValidatorListener(View view){
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
            if(id == R.id.tie_cap_lastname){
                isLastNameValid();
            } else if (id == R.id.tie_cap_firstname){
                isFirstNameValid();
            } else if(id == R.id.tie_cap_middname){
                isMiddNameValid();
            } else if(id == R.id.tie_cap_applBirthdate){
                isBirthdateValid();
            } else if(id == R.id.autoComplete_applBpProvince){
                tieBpPrvnx.removeTextChangedListener(this);
                ProvID = "";
                isBirthProvValid();
                tieBpPrvnx.addTextChangedListener(this);
            } else if(id == R.id.autoComplete_applBpTown){
                tieBpTownx.removeTextChangedListener(this);
                isBirthTownValid();
                TownID = "";
                tieBpTownx.addTextChangedListener(this);
            } else if(id == R.id.tie_cap_primaryContactNo){
                isPrimaryContactValid();
            }
        }
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isLastNameValid(){
        if(Objects.requireNonNull(tieLastname.getText()).toString().isEmpty()){
            tilLastname.setError("Please enter last name.");
            requestFocus(tieLastname);
            return false;
        }
        tilLastname.setErrorEnabled(false);
        return true;
    }

    private boolean isFirstNameValid(){
        if(Objects.requireNonNull(tieFirstname.getText()).toString().isEmpty()){
            tilFrstname.setError("Please enter first name.");
            requestFocus(tieFirstname);
            return false;
        }
        tilFrstname.setErrorEnabled(false);
        return true;
    }

    private boolean isMiddNameValid(){
        if(Objects.requireNonNull(tieMiddname.getText()).toString().trim().isEmpty()){
            tilMiddname.setError("Please enter middle name.");
            requestFocus(tieMiddname);
            return false;
        } else if (tieMiddname.getText().toString().trim().length() ==1 &&
                !tieMiddname.getText().toString().trim().equalsIgnoreCase(".")){
            tilMiddname.setError("Invalid middle name input. Enter '.' if middle name is not applicable");
            requestFocus(tieMiddname);
            return false;
        }
        tilMiddname.setErrorEnabled(false);
        return true;
    }

    public boolean isSuffixValid(){
        String lsText = tieSuffix.getText().toString().trim();
        if(lsText.equalsIgnoreCase(".")){
            tilSuffixxx.setError("Characters such as '. , / -' on suffix is invalid.");
            requestFocus(tieSuffix);
            return false;
        } else if(lsText.equalsIgnoreCase(",")){
            tilSuffixxx.setError("Characters such as '. , / -' on suffix is invalid.");
            requestFocus(tieSuffix);
            return false;
        } else if(lsText.equalsIgnoreCase("/")){
            tilSuffixxx.setError("Characters such as '. , / -' on suffix is invalid.");
            requestFocus(tieSuffix);
            return false;
        } else if(lsText.equalsIgnoreCase("-")){
            tilSuffixxx.setError("Characters such as '. , / -' on suffix is invalid.");
            requestFocus(tieSuffix);
            return false;
        } else {
            tilSuffixxx.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isBirthdateValid(){
        if(Objects.requireNonNull(tieBirthdate.getText()).toString().isEmpty()){
            tilBrthDate.setError("Please enter your birth date.");
            requestFocus(tieBirthdate);
            return false;
        } else if(getDateFormat().equalsIgnoreCase("Invalid Date")){
            tilBrthDate.setError("Please check your entered birthdate.");
            requestFocus(tieBirthdate);
            return false;
        }
        tilBrthDate.setErrorEnabled(false);
        return true;
    }

    private boolean isBirthPlaceValid(){
        if(ProvID.isEmpty()){
            tilBrthProv.setError("Invalid province entered.");
            requestFocus(tieBpPrvnx);
            return false;
        }

        if(TownID.isEmpty()){
            tilBrthTown.setError("Invalid town entered.");
            requestFocus(tieBpTownx);
            return false;
        }

        return true;
    }

    private boolean isBirthProvValid(){
        if(tieBpPrvnx.getText().toString().isEmpty()){
            tilBrthProv.setError("Please enter or select province.");
            requestFocus(tieBpPrvnx);
            return false;
        }

        tilBrthProv.setErrorEnabled(false);
        return true;
    }

    private boolean isBirthTownValid(){
        if (isBirthProvValid()) {
            if (tieBpTownx.getText().toString().isEmpty()) {
                tilBrthTown.setError("Please enter or select town.");
                requestFocus(tieBpTownx);
                return false;
            }
            tilBrthTown.setErrorEnabled(false);
            return true;
        }

        requestFocus(tieBpPrvnx);
        tieBpTownx.setText("");
        return false;
    }

    private boolean isGenderValid(){
        if(applGender.isEmpty()){
            lblGenderErrorMsg.setVisibility(View.VISIBLE);
            return false;
        }
        lblGenderErrorMsg.setVisibility(View.GONE);
        return true;
    }

    private boolean isCitizenshipValid(){
        if(CtznID.isEmpty()){
            tilNtnlty.setError("Invalid citizenship entered.");
            requestFocus(tieNtnlty);
            return false;
        }
        return true;
    }

    private boolean isMaidentNameValid(){
        if(applGender.equalsIgnoreCase("1") &&
                spnrCvlStatus.getSelectedItemPosition() == 1 &&
                Objects.requireNonNull(tieMothrName.getText()).toString().isEmpty()){
            tilMtherNme.setError("Please enter mother maiden name.");
            requestFocus(tieMothrName);
            return false;
        }
        tilMtherNme.setErrorEnabled(false);
        return true;
    }

    @SuppressLint("SetTextI18n")
    private boolean isCivilStatValid(){
        if(getCivilStatus().isEmpty()){
            lblCvlStatErrMsg.setVisibility(View.VISIBLE);
            lblCvlStatErrMsg.setText("Please Select Civil Status");
            return false;
        }
        return true;
    }

    private boolean isContactValid(){
        if(spnCntctNo.getSelectedItemPosition() == 0){
            return isPrimaryContactValid() && isPrimaryPlanValid();
        } else if (spnCntctNo.getSelectedItemPosition() == 1){
            return isPrimaryContactValid() && isPrimaryPlanValid() &&
                    isSecondaryContactValid() && isSecondaryPlanValid();
        } else {
            return isPrimaryContactValid() && isPrimaryPlanValid() &&
                    isSecondaryContactValid() && isSecondaryPlanValid() &&
                    isTertiaryContactValid() && isTertiaryPlanValid();
        }
    }

    private boolean isPrimaryContactValid(){
        if (Objects.requireNonNull(tiePrimaryContact.getText()).toString().isEmpty()) {
            tilPrmCntct.setError("Please provide atleast one mobile no.");
            requestFocus(tiePrimaryContact);
            return false;
        } else if (tiePrimaryContact.getText().toString().length() != 11) {
            tilPrmCntct.setError("Please enter a valid mobile no.");
            requestFocus(tiePrimaryContact);
            return false;
        } else if (tiePrimaryContact.getText().toString().substring(0, 1).equalsIgnoreCase("09")) {
            tilPrmCntct.setError("Mobile No. must start with '09'.");
            requestFocus(tiePrimaryContact);
            return false;
        }
        tilPrmCntct.setErrorEnabled(false);
        return true;
    }

    private boolean isSecondaryContactValid(){
        if(isPrimaryContactValid()) {
            if (Objects.requireNonNull(tieScndaryContact.getText()).toString().isEmpty()) {
                tilScnCntct.setError("Please enter secondary contact.");
                requestFocus(tieScndaryContact);
                return false;
            } else if (tieScndaryContact.getText().toString().length() != 11) {
                tilScnCntct.setError("Please enter a valid contact no.");
                requestFocus(tieScndaryContact);
                return false;
            } else if (Objects.requireNonNull(tieScndaryContact.getText()).toString().substring(0, 1).equalsIgnoreCase("09")) {
                tilScnCntct.setError("Mobile No. must start with '09'");
                requestFocus(tieScndaryContact);
                return false;
            }
            tilScnCntct.setErrorEnabled(false);
            return true;
        }
        return false;
    }

    private boolean isTertiaryContactValid(){
        if(isSecondaryContactValid()) {
            if (Objects.requireNonNull(tieTrTiaryContact.getText()).toString().isEmpty()) {
                tilTrtCntct.setError("Please enter tertiary contact.");
                requestFocus(tieTrTiaryContact);
                return false;
            } else if (tieTrTiaryContact.getText().toString().length() != 11) {
                tilTrtCntct.setError("Please enter a valid contact no.");
                requestFocus(tieTrTiaryContact);
                return false;
            } else if (tieTrTiaryContact.getText().toString().substring(0, 1).equalsIgnoreCase("09")) {
                tilTrtCntct.setError("Mobile No. must start with '09'");
                requestFocus(tieTrTiaryContact);
                return false;
            }
            tilTrtCntct.setErrorEnabled(false);
            return true;
        } return false;
    }

    private boolean hasDuplicateContact(){
        if(!Objects.requireNonNull(tiePrimaryContact.getText()).toString().isEmpty()){
            if(tiePrimaryContact.getText().toString().equalsIgnoreCase(Objects.requireNonNull(tieScndaryContact.getText()).toString()) ||
                    tiePrimaryContact.getText().toString().equalsIgnoreCase(Objects.requireNonNull(tieTrTiaryContact.getText()).toString())){
                return true;
            }
        }
        if(!Objects.requireNonNull(tieScndaryContact.getText()).toString().isEmpty()){
            if(tieScndaryContact.getText().toString().equalsIgnoreCase(tiePrimaryContact.getText().toString()) ||
                    tieScndaryContact.getText().toString().equalsIgnoreCase(Objects.requireNonNull(tieTrTiaryContact.getText()).toString())){
                return true;
            }
        }
        if(!Objects.requireNonNull(tieTrTiaryContact.getText()).toString().isEmpty()){
            if(tieTrTiaryContact.getText().toString().equalsIgnoreCase(tiePrimaryContact.getText().toString()) ||
                    tieTrTiaryContact.getText().toString().equalsIgnoreCase(tieScndaryContact.getText().toString())){
                return true;
            }
        }
        return false;
    }

    private boolean isPrimaryPlanValid(){
        if(spnPrmryCt.getSelectedItemPosition() == 1){
            if(Objects.requireNonNull(tiePrmryPlan.getText()).toString().isEmpty()){
                tilPrmryPlan.setError("Please enter years of plan.");
                requestFocus(tiePrmryPlan);
                return false;
            }
            tilPrmryPlan.setErrorEnabled(false);
            return true;
        }
        return true;
    }

    private boolean isSecondaryPlanValid(){
        if(spnScndCnt.getSelectedItemPosition() == 1){
            if(Objects.requireNonNull(tieScndryPln.getText()).toString().isEmpty()){
                tilScndryPln.setError("Please enter years of plan.");
                requestFocus(tieScndryPln);
                return false;
            }
            tilScndryPln.setErrorEnabled(false);
            return true;
        }
        return true;
    }

    private boolean isTertiaryPlanValid(){
        if(spnTrtryCt.getSelectedItemPosition() == 1){
            if (Objects.requireNonNull(tieTrtryPlan.getText()).toString().isEmpty()) {
                tilTrtryPlan.setError("Please enter years of plan.");
                requestFocus(tieTrtryPlan);
                return false;
            }
            tilTrtryPlan.setErrorEnabled(false);
            return true;
        }
        return true;
    }

    private boolean isEmailValid(){
        if(!Objects.requireNonNull(tieEmailAdd.getText()).toString().trim().isEmpty()){
            String email = tieEmailAdd.getText().toString();
            if((!Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                tilEmailAdd.setError("Please enter valid email.");
                requestFocus(tieEmailAdd);
                return false;
            }
        }
        tilEmailAdd.setErrorEnabled(false);
        return true;
    }
}
