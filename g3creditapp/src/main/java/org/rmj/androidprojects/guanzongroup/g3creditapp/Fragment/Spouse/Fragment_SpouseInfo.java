package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse;

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
import android.widget.Spinner;

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
public class Fragment_SpouseInfo extends Fragment {
    private static final String TAG = Fragment_SpouseInfo.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static Fragment_SpouseInfo instance;
    private String ProvID = "";
    private String TownID = "";
    private String FbStatus = "";
    private String CtzIDx = "";
    private int liContactCnt = 1;
    private String[] IsPostPaid = new String[3];
    private AutoSuggestAddress address;
    private GOCASApplication loGoCas;
    private View v;
    private TextInputLayout tilLastname;
    private TextInputLayout tilFrstname;
    private TextInputLayout tilMiddname;
    private TextInputLayout tilSuffixxx;
    private TextInputLayout tilBrthDate;
    private TextInputLayout tilBrthProv;
    private TextInputLayout tilBrthTown;
    private TextInputLayout tilNationxx;
    private TextInputLayout tilPrmCntct;
    private TextInputLayout tilScdCntct;
    private TextInputLayout tilTrtCntct;
    private TextInputLayout tilEmailAdd;
    private TextInputEditText tieLastname;
    private TextInputEditText tieFrstname;
    private TextInputEditText tieMiddname;
    private AutoCompleteTextView tieSuffixxx;
    private TextInputEditText tieNickname;
    private TextInputEditText tieBrthDate;
    private AutoCompleteTextView tieBrthProv;
    private AutoCompleteTextView tieBrthTown;
    private AutoCompleteTextView tieNationxx;
    private LinearLayout linearPrmCntct;
    private LinearLayout linearScdCntct;
    private LinearLayout linearTrtCntct;
    private TextInputEditText tiePrmCntct;
    private TextInputEditText tieScdCntct;
    private TextInputEditText tieTrtCntct;
    private TextInputLayout tilPrYrPlan;
    private TextInputLayout tilScYrPlan;
    private TextInputLayout tilTrYrPlan;
    private TextInputEditText tiePrYrPlan;
    private TextInputEditText tieScYrPlan;
    private TextInputEditText tieTrYrPlan;
    private TextInputEditText tieTelephne;
    private TextInputEditText tieEmailAdd;
    private TextInputEditText tieFbaccntx;
    private TextInputEditText tieVberAcct;
    private Spinner spnNoOfContact;
    private Spinner spnPrimaryStat;
    private Spinner spnScndaryStat;
    private Spinner spnTrtiaryStat;

    private MaterialButton btnPrvs;
    private MaterialButton btnNext;

    public Fragment_SpouseInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_spouse_info, container, false);
        instance = this;
        address = new AutoSuggestAddress(getActivity());
        setupWidgets();
        setupActions();
        return v;
    }

    private void setupWidgets(){
        tilLastname = v.findViewById(R.id.til_cap_spslastname);
        tilFrstname = v.findViewById(R.id.til_cap_spsFirstname);
        tilMiddname = v.findViewById(R.id.til_cap_spsMiddname);
        tilSuffixxx = v.findViewById(R.id.til_cap_spsSuffix);
        tilBrthDate = v.findViewById(R.id.til_cap_spsBirthdate);
        tilBrthProv = v.findViewById(R.id.til_cap_spsBirthProv);
        tilBrthTown = v.findViewById(R.id.til_cap_spsBirthTown);
        tilNationxx = v.findViewById(R.id.til_cap_spsNtnlty);
        tilPrmCntct = v.findViewById(R.id.til_cap_spsPrimaryContactNo);
        tilScdCntct = v.findViewById(R.id.til_cap_spsSecondaryContactNo);
        tilTrtCntct = v.findViewById(R.id.til_cap_spsTertiaryContactNo);

        tieLastname = v.findViewById(R.id.tie_cap_spslastname);
        tieFrstname = v.findViewById(R.id.tie_cap_spsFirstname);
        tieMiddname = v.findViewById(R.id.tie_cap_spsMiddname);
        tieSuffixxx = v.findViewById(R.id.tie_cap_spsSuffix);
        tieNickname = v.findViewById(R.id.tie_cap_spsNickname);
        tieBrthDate = v.findViewById(R.id.tie_cap_spsBirthdate);
        tieBrthProv = v.findViewById(R.id.tie_cap_spsBirthProv);
        tieBrthTown = v.findViewById(R.id.tie_cap_spsBirthTown);
        tieNationxx = v.findViewById(R.id.tie_cap_spsNtnlty);
        linearPrmCntct = v.findViewById(R.id.linear_spsPrimaryContact);
        linearScdCntct = v.findViewById(R.id.linear_spsSecondaryContact);
        linearTrtCntct = v.findViewById(R.id.linear_spsTertiaryContact);
        tilPrYrPlan = v.findViewById(R.id.til_cap_spsPrimaryCntctPlan);
        tilScYrPlan = v.findViewById(R.id.til_cap_spsSecondaryCntctPlan);
        tilTrYrPlan = v.findViewById(R.id.til_cap_spsTertiaryCntctPlan);
        tiePrmCntct = v.findViewById(R.id.tie_cap_spsPrimaryContactNo);
        tieScdCntct = v.findViewById(R.id.tie_cap_spsSecondaryContactNo);
        tieTrtCntct = v.findViewById(R.id.tie_cap_spsTertiaryContactNo);
        tiePrYrPlan = v.findViewById(R.id.tie_cap_spsPrimaryCntctPlan);
        tieScYrPlan = v.findViewById(R.id.tie_cap_spsSecondaryCntctPlan);
        tieTrYrPlan = v.findViewById(R.id.tie_cap_spsTertiaryCntctPlan);
        tieTelephne = v.findViewById(R.id.tie_cap_spsTelNo);
        tieEmailAdd = v.findViewById(R.id.tie_cap_spsEmailadd);
        tilEmailAdd = v.findViewById(R.id.til_cap_spsEmailadd);
        tieFbaccntx = v.findViewById(R.id.tie_cap_spsFacebookacc);
        tieVberAcct = v.findViewById(R.id.tie_cap_spsViberAcc);
        spnNoOfContact = v.findViewById(R.id.spinner_cap_spsNoOfContacts);
        spnPrimaryStat = v.findViewById(R.id.spinner_cap_spsPrimaryCntctStats);
        spnScndaryStat = v.findViewById(R.id.spinner_cap_spsSecondaryCntctStats);
        spnTrtiaryStat = v.findViewById(R.id.spinner_cap_spsTertiaryCntctStats);

        btnPrvs = v.findViewById(R.id.btn_fragment_spsInfo_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_spsInfo_next);
    }

    private void setupActions(){
        tieSuffixxx.setAdapter(getSuffixSuggestions());
        spnNoOfContact.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getCount));
        tieBrthDate.addTextChangedListener(new OnBirthDateSetListener());
        tieBrthProv.setAdapter(getProvinceList());
        tieBrthProv.setOnItemClickListener(new OnAddressSelectedListener(tieBrthProv));
        tieBrthTown.setAdapter(getTownList());
        tieBrthTown.setOnItemClickListener(new OnAddressSelectedListener(tieBrthTown));
        tieNationxx.setAdapter(getCitizenshipList());
        tieNationxx.setOnItemClickListener(new OnCitizenshipSelectedListener());
        spnNoOfContact.setOnItemSelectedListener(new OnNetwornStatusSelectionListener(spnNoOfContact));
        spnPrimaryStat.setAdapter(getNetworkStatus());
        spnPrimaryStat.setOnItemSelectedListener(new OnNetwornStatusSelectionListener(spnPrimaryStat));
        spnScndaryStat.setAdapter(getNetworkStatus());
        spnScndaryStat.setOnItemSelectedListener(new OnNetwornStatusSelectionListener(spnScndaryStat));
        spnTrtiaryStat.setAdapter(getNetworkStatus());
        spnTrtiaryStat.setOnItemSelectedListener(new OnNetwornStatusSelectionListener(spnTrtiaryStat));

        btnPrvs.setOnClickListener(v -> ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).getInstance().setCurrentItem(3, true));
        btnNext.setOnClickListener(v -> setupSpouseInfo());
    }

    public static Fragment_SpouseInfo getInstance(){
        return instance;
    }

    private void setupSpouseInfo(){
        String TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(TransNox);
        if(isDataValid()) {
            loGoCas.SpouseInfo().PersonalInfo().setLastName(Objects.requireNonNull(tieLastname.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setFirstName(Objects.requireNonNull(tieFrstname.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setMiddleName(Objects.requireNonNull(tieMiddname.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setSuffixName(Objects.requireNonNull(tieSuffixxx.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setNickName(Objects.requireNonNull(tieNickname.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setBirthdate(getDateFormat());
            loGoCas.SpouseInfo().PersonalInfo().setBirthPlace(TownID);
            setupMobileNo();
            setupMobilePostpaid();
            loGoCas.SpouseInfo().PersonalInfo().setCitizenship(CtzIDx);
            loGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(getParseInt(spnNoOfContact.getSelectedItem().toString()));
            loGoCas.SpouseInfo().PersonalInfo().setPhoneNoQty(1);
            loGoCas.SpouseInfo().PersonalInfo().setPhoneNo(0, Objects.requireNonNull(tieTelephne.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setEmailAddQty(1);
            loGoCas.SpouseInfo().PersonalInfo().setEmailAddress(0, Objects.requireNonNull(tieEmailAdd.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setFBAccount(Objects.requireNonNull(tieFbaccntx.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setViberAccount(Objects.requireNonNull(tieVberAcct.getText()).toString());
            setupFacebookInfo();
            new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, TransNox);
            Log.e(TAG, "Spouse personal information result : " + loGoCas.SpouseInfo().PersonalInfo().toJSONString());
            ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(4, true);
        }
    }

    private void setupMobileNo(){
        loGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(1);
        if(spnNoOfContact.getSelectedItem().toString().equalsIgnoreCase("1")){
            loGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(1);
            loGoCas.SpouseInfo().PersonalInfo().setMobileNo(0, Objects.requireNonNull(tiePrmCntct.getText()).toString());
        } else if(spnNoOfContact.getSelectedItem().toString().equalsIgnoreCase("2")){
            loGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(1);
            loGoCas.SpouseInfo().PersonalInfo().setMobileNo(0, Objects.requireNonNull(tiePrmCntct.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(2);
            loGoCas.SpouseInfo().PersonalInfo().setMobileNo(1, Objects.requireNonNull(tieScdCntct.getText()).toString());
        } else if (spnNoOfContact.getSelectedItem().toString().equalsIgnoreCase("3")){
            loGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(1);
            loGoCas.SpouseInfo().PersonalInfo().setMobileNo(0, Objects.requireNonNull(tiePrmCntct.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(2);
            loGoCas.SpouseInfo().PersonalInfo().setMobileNo(1, Objects.requireNonNull(tieScdCntct.getText()).toString());
            loGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(3);
            loGoCas.SpouseInfo().PersonalInfo().setMobileNo(2, Objects.requireNonNull(tieTrtCntct.getText()).toString());
        }

        for(int x = 0; x < liContactCnt; x++){
            if(IsPostPaid[x].equalsIgnoreCase("1")){
                loGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(x,  IsPostPaid[x]);
            }
        }
    }

    private void setupMobilePostpaid(){
        int count = loGoCas.SpouseInfo().PersonalInfo().getMobileNoQty();
        if(count == 1) {
            if (loGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(0).equalsIgnoreCase("1")) {
                loGoCas.SpouseInfo().PersonalInfo().setPostPaidYears(0, getParseInt(Objects.requireNonNull(tiePrYrPlan.getText()).toString()));
            }
        }
        if(count == 2) {
            if (loGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(0).equalsIgnoreCase("1")) {
                loGoCas.SpouseInfo().PersonalInfo().setPostPaidYears(0, getParseInt(Objects.requireNonNull(tiePrYrPlan.getText()).toString()));
            }
            if (loGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(1).equalsIgnoreCase("1")) {
                loGoCas.SpouseInfo().PersonalInfo().setPostPaidYears(1, getParseInt(Objects.requireNonNull(tieScYrPlan.getText()).toString()));
            }
        }
        if(count == 3) {
            if (loGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(0).equalsIgnoreCase("1")) {
                loGoCas.SpouseInfo().PersonalInfo().setPostPaidYears(0, Integer.parseInt(Objects.requireNonNull(tiePrYrPlan.getText()).toString()));
            }
            if (loGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(1).equalsIgnoreCase("1")) {
                loGoCas.SpouseInfo().PersonalInfo().setPostPaidYears(1, getParseInt(Objects.requireNonNull(tieScYrPlan.getText()).toString()));
            }
            if (loGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(2).equalsIgnoreCase("1")) {
                loGoCas.SpouseInfo().PersonalInfo().setPostPaidYears(2, getParseInt(Objects.requireNonNull(tieTrYrPlan.getText()).toString()));
            }
        }
    }

    private void setupFacebookInfo(){
        if(!Objects.requireNonNull(tieFbaccntx.getText()).toString().isEmpty()){
            loGoCas.SpouseInfo().PersonalInfo().isFBActive(FbStatus);
        }
    }

    private int getParseInt(String value){
        try{
            return Integer.parseInt(value);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private ArrayAdapter<String> getNetworkStatus(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getNetworkStat);
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

    private ArrayAdapter<String> getCitizenshipList(){
        String[] data = new String[address.getNationalityList().get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getNationalityList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getSuffixSuggestions(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getSuffix);
    }

    class OnNetwornStatusSelectionListener implements AdapterView.OnItemSelectedListener{

        View spnView;

        OnNetwornStatusSelectionListener(View view){
            this.spnView = view;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(spnView.getId() == R.id.spinner_cap_spsNoOfContacts){
                switch (spnNoOfContact.getSelectedItem().toString()){
                    case "1":
                        tieScdCntct.setText("");
                        tieTrtCntct.setText("");
                        linearPrmCntct.setVisibility(View.VISIBLE);
                        linearScdCntct.setVisibility(View.GONE);
                        linearTrtCntct.setVisibility(View.GONE);
                        break;
                    case "2":
                        tieTrtCntct.setText("");
                        linearPrmCntct.setVisibility(View.VISIBLE);
                        linearScdCntct.setVisibility(View.VISIBLE);
                        linearTrtCntct.setVisibility(View.GONE);
                        break;
                    case "3":
                        linearPrmCntct.setVisibility(View.VISIBLE);
                        linearScdCntct.setVisibility(View.VISIBLE);
                        linearTrtCntct.setVisibility(View.VISIBLE);
                        break;
                }
            } else if(spnView.getId() == R.id.spinner_cap_spsPrimaryCntctStats){
                liContactCnt = 1;
                if(spnPrimaryStat.getSelectedItem().toString().equalsIgnoreCase("Prepaid")){
                    tilPrYrPlan.setVisibility(View.GONE);
                    IsPostPaid[0] = "0";
                } else {
                    tilPrYrPlan.setVisibility(View.VISIBLE);
                    IsPostPaid[0] = "1";
                }
            } else if(spnView.getId() == R.id.spinner_cap_spsSecondaryCntctStats){
                liContactCnt = 2;
                if(spnScndaryStat.getSelectedItem().toString().equalsIgnoreCase("Prepaid")){
                    tilScYrPlan.setVisibility(View.GONE);
                    IsPostPaid[1] = "0";
                } else {
                    tilScYrPlan.setVisibility(View.VISIBLE);
                    IsPostPaid[1] = "1";
                }
            } else if(spnView.getId() == R.id.spinner_cap_spsTertiaryCntctStats){
                liContactCnt = 3;
                if(spnTrtiaryStat.getSelectedItem().toString().equalsIgnoreCase("Prepaid")){
                    tilTrYrPlan.setVisibility(View.GONE);
                    IsPostPaid[2] = "0";
                } else {
                    tilTrYrPlan.setVisibility(View.VISIBLE);
                    IsPostPaid[2] = "1";
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnAddressSelectedListener implements AdapterView.OnItemClickListener{

        View addView;

        OnAddressSelectedListener(View view){
            this.addView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(addView.getId() == R.id.tie_cap_spsBirthProv) {
                for (int x = 0; x < address.getProvinceList().get(0).size(); x++) {
                    if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))) {
                        ProvID = address.getProvinceList().get(0).get(x);
                        tieBrthTown.setAdapter(getTownList());
                        break;
                    }
                }
            } else if(addView.getId() == R.id.tie_cap_spsBirthTown){
                for (int x = 0; x < address.getTownList(ProvID).get(0).size(); x++) {
                    if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))) {
                        TownID = address.getTownList(ProvID).get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnBirthDateSetListener implements TextWatcher {
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

    class OnCitizenshipSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(int x = 0; x < address.getNationalityList().get(0).size(); x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getNationalityList().get(1).get(x))){
                    CtzIDx = address.getNationalityList().get(0).get(x);
                    break;
                }
            }
        }
    }

    /**
     * Spouse information input validations...
     * ************************************************************************
     * ************************************************************************/

    private boolean isDataValid(){
        if(!isLastnameValid()){
            new CustomToast(getActivity(), "Please enter last name", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isFrstnameValid()){
            new CustomToast(getActivity(), "Please enter first name", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isMiddNameValid()){
            new CustomToast(getActivity(), "Please enter middle name", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isSuffixValid()){
            new CustomToast(getActivity(), "Invalid suffix input", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isBirthdateValid()){
            new CustomToast(getActivity(), "Birth date info is empty or invalid", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isBirthPlaceValid()){
            new CustomToast(getActivity(), "Birth place info is empty or invalid", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isContactValid()){
            new CustomToast(getActivity(), "Please check contact info", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(hasDuplicateContact()){
            new CustomToast(getActivity(), "Contact info has been duplicated. Please check.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isEmailValid()){
            new CustomToast(getActivity(), "Invalid email input.", CustomToast.TYPE.WARNING).show();
            return false;
        }
        return true;
    }

    private boolean isContactValid(){
        int qty = spnNoOfContact.getSelectedItemPosition();
        switch (qty){
            case 0:
                return isPrimaryContact() && isPrimaryContactPlan();
            case 1:
                return isPrimaryContact() && isPrimaryContactPlan() &&
                        isSecondaryContact() && isSecondaryContactPlan();
            case 2:
                return isPrimaryContact() && isPrimaryContactPlan() &&
                        isSecondaryContact() && isSecondaryContactPlan() &&
                        isTertiaryContact() && isTertiaryContactPlan();
        }
        return false;
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isLastnameValid(){
        if(Objects.requireNonNull(tieLastname.getText()).toString().isEmpty()){
            tilLastname.setError("Please enter last name.");
            requestFocus(tieLastname);
            return false;
        }
        tilLastname.setErrorEnabled(false);
        return true;
    }

    private boolean isFrstnameValid(){
        if(Objects.requireNonNull(tieFrstname.getText()).toString().isEmpty()){
            tilFrstname.setError("Please enter first name.");
            requestFocus(tieFrstname);
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

    private boolean isSuffixValid(){
        if(tieSuffixxx.getText().toString().trim().equalsIgnoreCase(".")){
            tilSuffixxx.setError("Characters such as '. , / -' on suffix is invalid please");
            requestFocus(tieSuffixxx);
            return false;
        } else if(tieSuffixxx.getText().toString().trim().equalsIgnoreCase(",")){
            tilSuffixxx.setError("Characters such as '. , / -' on suffix is invalid please");
            requestFocus(tieSuffixxx);
            return false;
        } else if(tieSuffixxx.getText().toString().trim().equalsIgnoreCase("/")){
            tilSuffixxx.setError("Characters such as '. , / -' on suffix is invalid please");
            requestFocus(tieSuffixxx);
            return false;
        } else if(tieSuffixxx.getText().toString().trim().equalsIgnoreCase("-")){
            tilSuffixxx.setError("Characters such as '. , / -' on suffix is invalid please");
            requestFocus(tieSuffixxx);
            return false;
        } else {
            tilSuffixxx.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isBirthdateValid(){
        if(Objects.requireNonNull(tieBrthDate.getText()).toString().isEmpty()){
            tilBrthDate.setError("Please enter birth date.");
            requestFocus(tieBrthDate);
            return false;
        } else if(getDateFormat().equalsIgnoreCase("Invalid Date")){
            tilBrthDate.setError("Please check your input birth date.");
            requestFocus(tieBrthDate);
            return false;
        }
        tilBrthDate.setErrorEnabled(false);
        return true;
    }
    private boolean isBirthPlaceValid(){
        if(ProvID.isEmpty()){
            requestFocus(tieBrthProv);
            tilBrthProv.setError("Invalid province input.");
            return false;
        } else if(TownID.isEmpty()){
            requestFocus(tieBrthTown);
            tilBrthTown.setError("Invalid province input.");
            return false;
        }

        return true;
    }

    private boolean isProvinceValid(){
        if(tieBrthProv.getText().toString().isEmpty()){
            tilBrthProv.setError("Please enter province");
            requestFocus(tieBrthProv);
            return false;
        }
        tilBrthProv.setErrorEnabled(false);
        return true;
    }

    private boolean isTownValid(){
        if(isProvinceValid()){
            if(tieBrthTown.getText().toString().isEmpty()){
                tilBrthTown.setError("Please enter town");
                requestFocus(tieBrthTown);
                return false;
            }
            tilBrthTown.setErrorEnabled(false);
            return true;
        }
        return false;
    }

    private boolean isPrimaryContact(){
        if(Objects.requireNonNull(tiePrmCntct.getText()).toString().isEmpty()){
            tilPrmCntct.setError("Please enter primary contact no.");
            requestFocus(tiePrmCntct);
            return false;
        }

        tilPrmCntct.setErrorEnabled(false);
        return true;
    }

    private boolean isSecondaryContact(){
        if(Objects.requireNonNull(tieScdCntct.getText()).toString().isEmpty()){
            tilScdCntct.setError("Please enter secondary contact no.");
            requestFocus(tieScdCntct);
            return false;
        }

        tilScdCntct.setErrorEnabled(false);
        return true;
    }

    private boolean isTertiaryContact(){
        if(Objects.requireNonNull(tieTrtCntct.getText()).toString().isEmpty()){
            tilTrtCntct.setError("Please enter tertiary contact no.");
            requestFocus(tieTrtCntct);
            return false;
        }

        tilTrtCntct.setErrorEnabled(false);
        return true;
    }

    private boolean isPrimaryContactPlan(){
        if(spnPrimaryStat.getSelectedItemPosition() == 1){
            if(Objects.requireNonNull(tiePrYrPlan.getText()).toString().isEmpty()){
                tilPrmCntct.setError("Please enter year of plan.");
                requestFocus(tiePrYrPlan);
                return false;
            }
            tilPrmCntct.setErrorEnabled(false);
            return true;
        }
        return true;
    }

    private boolean isSecondaryContactPlan(){
        if(spnScndaryStat.getSelectedItemPosition() == 1){
            if(Objects.requireNonNull(tieScYrPlan.getText()).toString().isEmpty()){
                tilScdCntct.setError("Please enter year of plan.");
                requestFocus(tieScYrPlan);
                return false;
            }
            tilScdCntct.setErrorEnabled(false);
            return true;
        }
        return true;
    }

    private boolean isTertiaryContactPlan(){
        if(spnTrtiaryStat.getSelectedItemPosition() == 1){
            if(Objects.requireNonNull(tieTrYrPlan.getText()).toString().isEmpty()){
                tilTrtCntct.setError("Please enter year of plan.");
                requestFocus(tieScYrPlan);
                return false;
            }
            tilTrtCntct.setErrorEnabled(false);
            return true;
        }
        return true;
    }

    private boolean hasDuplicateContact(){
        if(!tiePrmCntct.getText().toString().isEmpty()){
            if(tiePrmCntct.getText().toString().equalsIgnoreCase(tieScdCntct.getText().toString()) ||
                    tiePrmCntct.getText().toString().equalsIgnoreCase(tieTrtCntct.getText().toString())){
                return true;
            }
        }
        if(!tieScdCntct.getText().toString().isEmpty()){
            if(tieScdCntct.getText().toString().equalsIgnoreCase(tiePrmCntct.getText().toString()) ||
                    tieScdCntct.getText().toString().equalsIgnoreCase(tieTrtCntct.getText().toString())){
                return true;
            }
        }
        if(!tieTrtCntct.getText().toString().isEmpty()){
            if(tieTrtCntct.getText().toString().equalsIgnoreCase(tiePrmCntct.getText().toString()) ||
                    tieTrtCntct.getText().toString().equalsIgnoreCase(tieScdCntct.getText().toString())){
                return true;
            }
        }
        return false;
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
