package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Personal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.ConstantsAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.CreditSourceObjects;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.Dialog_IncomeSource;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.FormatUIText;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_IncomStatus;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.AutoSuggestAddress;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment_ResidenceInfo extends Fragment {
    private static final String TAG = Fragment_ResidenceInfo.class.getSimpleName();

    private GOCASApplication loGoCas;
    private View v;
    private AutoSuggestAddress address;
    private ConstantsAdapter adapter;
    private String ProvID = "";
    private String TownID = "";
    private String BrgyID = "";
    private String PermProvID = "";
    private String PermTownID = "";
    private String PermBrgyID = "";
    private String HouseOwnership = "0";
    private String hasGarage = "1";
    private String PermLandmark = "";
    private String PermHouseNox = "";
    private String PermSittioLt = "";
    private String PermStreetxx = "";
    private String PermProvince = "";
    private String PermTownCity = "";
    private String PermBarangay = "";

    private TextInputLayout tilprovince;
    private TextInputLayout tilCityTown;
    private TextInputLayout tilBarangay;
    private TextInputLayout tilPermBarangy;
    private TextInputLayout tilPermTownCty;
    private TextInputLayout tilPermProvnce;
    private TextInputEditText tieLandmark;
    private TextInputEditText tieHouseNox;
    private TextInputEditText tieSitioLot;
    private TextInputEditText tieStreestx;
    private AutoCompleteTextView tieBarangay;
    private AutoCompleteTextView tieCityTown;
    private AutoCompleteTextView tieProvince;
    private RadioGroup rgOwnerShip;
    private CheckBox cbPermntAdd;
    private LinearLayout linearRentx;
    private LinearLayout linearCareT;
    private LinearLayout linearPermt;
    private TextInputEditText tiePermLndMark;
    private TextInputEditText tiePermHouseNo;
    private TextInputEditText tiePermSitioLt;
    private TextInputEditText tiePermStreetx;
    private AutoCompleteTextView tiePermProvnce;
    private AutoCompleteTextView tiePermTownCty;
    private AutoCompleteTextView tiePermBarangy;
    private TextView lblErrorMessage;
    private Spinner sprLivngStat;

    /*Rental Fields
    * ********************* */
    private TextInputEditText tieLngtSty;
    private TextInputEditText tieRentExp;
    private Spinner sprRentLvngLnght;

    /*CareTaker Fields
    * ******************** */
    private TextInputEditText tieRelation;
    private Spinner sprHouseType;
    private TextInputEditText tieLenghtStay;
    private TextInputEditText tieCrtkrExpns;
    private Spinner sprCtkrLvngLngth;

    private MaterialButton btnPrevious;
    private MaterialButton btnNext;

    public Fragment_ResidenceInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_residence_info, container, false);
        address = new AutoSuggestAddress(getActivity());
        adapter = new ConstantsAdapter(getActivity());
        setupWidgets();
        setupListeners();
        return v;
    }

    private void setupWidgets(){
        tilprovince = v.findViewById(R.id.til_cap_rsdnProvince);
        tilCityTown = v.findViewById(R.id.til_cap_rsdnTown);
        tilBarangay = v.findViewById(R.id.til_cap_rdsnBrgy);
        tilPermBarangy = v.findViewById(R.id.til_cap_prmntRdsnBrgy);
        tilPermTownCty = v.findViewById(R.id.til_cap_prmntRsdnTown);
        tilPermProvnce = v.findViewById(R.id.til_cap_prmntRsdnProvince);
        tieLandmark = v.findViewById(R.id.tie_cap_residenceLandM);
        tieHouseNox = v.findViewById(R.id.tie_cap_applHouseNo);
        tieSitioLot = v.findViewById(R.id.tie_cap_rsdnSittio);
        tieStreestx = v.findViewById(R.id.tie_cap_rsdnStreet);
        tieBarangay = v.findViewById(R.id.tie_cap_rsdnBrgy);
        tieCityTown = v.findViewById(R.id.tie_cap_rsdnTown);
        tieProvince = v.findViewById(R.id.tie_cap_rsdnProvince);
        rgOwnerShip = v.findViewById(R.id.rg_cap_Ownership);
        linearRentx = v.findViewById(R.id.linear_rentStats);
        linearCareT = v.findViewById(R.id.linear_careTakerStats);
        linearPermt = v.findViewById(R.id.linear_permanentAddress);
        tieLngtSty = v.findViewById(R.id.tie_cap_rsdnLngthStay);
        tieRentExp = v.findViewById(R.id.tie_cap_rsdnRentExp);
        tieRelation = v.findViewById(R.id.tie_cap_crtkrRelation);
        sprHouseType = v.findViewById(R.id.spinner_cap_crtkrHouseType);
        RadioGroup rgGargType = v.findViewById(R.id.rg_cap_garage);
        btnPrevious = v.findViewById(R.id.btn_fragment_residence_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_residence_next);
        cbPermntAdd = v.findViewById(R.id.cb_cap_permanentAddress);
        sprRentLvngLnght = v.findViewById(R.id.spinner_cap_rentLengthStay);
        sprCtkrLvngLngth = v.findViewById(R.id.spinner_cap_ctkrLengthStay);

        tiePermLndMark = v.findViewById(R.id.tie_cap_prmntResidenceLandM);
        tiePermHouseNo = v.findViewById(R.id.tie_cap_prmntApplHouseNo);
        tiePermSitioLt = v.findViewById(R.id.tie_cap_prmntRsdnSittio);
        tiePermStreetx = v.findViewById(R.id.tie_cap_prmntRsdnStreet);
        tiePermBarangy = v.findViewById(R.id.tie_cap_prmntRsdnBrgy);
        tiePermTownCty = v.findViewById(R.id.tie_cap_prmntRsdnTown);
        tiePermProvnce = v.findViewById(R.id.tie_cap_prmntRsdnProvince);
        lblErrorMessage = v.findViewById(R.id.lbl_houseOwnErrorMessage);

        sprLivngStat = v.findViewById(R.id.spinner_cap_ownHouse);
        tieLenghtStay = v.findViewById(R.id.tie_cap_crtkrLngthStay);
        tieCrtkrExpns = v.findViewById(R.id.tie_cap_crtkrExpense);

        tieProvince.setAdapter(getProvinceList());
        tieProvince.setOnItemClickListener(new OnAddressSelectedListener(tieProvince));
        tieCityTown.setOnItemClickListener(new OnAddressSelectedListener(tieCityTown));
        tieBarangay.setOnItemClickListener(new OnAddressSelectedListener(tieBarangay));
        rgGargType.setOnCheckedChangeListener(new OnGarageTypeSelectListener());
        sprHouseType.setAdapter(getHouseType());
        sprLivngStat.setAdapter(getOwnHouseOptions());
    }

    private void setupListeners(){
        tieLandmark.addTextChangedListener(new OnAddressChangeListener(tieLandmark));
        tieHouseNox.addTextChangedListener(new OnAddressChangeListener(tieHouseNox));
        tieSitioLot.addTextChangedListener(new OnAddressChangeListener(tieSitioLot));
        tieStreestx.addTextChangedListener(new OnAddressChangeListener(tieStreestx));
        tieBarangay.addTextChangedListener(new OnAddressChangeListener(tieBarangay));
        tieProvince.addTextChangedListener(new OnAddressChangeListener(tieProvince));
        tieCityTown.addTextChangedListener(new OnAddressChangeListener(tieCityTown));
        tiePermProvnce.setAdapter(getProvinceList());
        tiePermProvnce.setOnItemClickListener(new OnPermanentAddressSelectionListener(tiePermProvnce));
        tiePermTownCty.setOnItemClickListener(new OnPermanentAddressSelectionListener(tiePermTownCty));
        tiePermBarangy.setOnItemClickListener(new OnPermanentAddressSelectionListener(tiePermBarangy));
        rgOwnerShip.setOnCheckedChangeListener(new OwnershipSelectionListener());
        cbPermntAdd.setOnCheckedChangeListener(new OnPermanentAddressSelectedListener());
        btnPrevious.setOnClickListener(new ButtonCLickListener());
        btnNext.setOnClickListener(new ButtonCLickListener());
        sprRentLvngLnght.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.TimeLength));
        sprCtkrLvngLngth.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.TimeLength));
    }

    private boolean isDataValid(){
        return isProvinceValid() &&
                isCityTownValid() &&
                isBarangayValid() &&
                isOwnershipValid() &&
                isValidPresentAdd() &&
                isValidPermanentAddress();
    }

    private void setupResidenceInfo(){
        String TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(TransNox);
        loGoCas.ResidenceInfo().PresentAddress().setLandMark(Objects.requireNonNull(tieLandmark.getText()).toString());
        loGoCas.ResidenceInfo().PresentAddress().setHouseNo(Objects.requireNonNull(tieHouseNox.getText()).toString());
        loGoCas.ResidenceInfo().PresentAddress().setAddress1(Objects.requireNonNull(tieSitioLot.getText()).toString());
        loGoCas.ResidenceInfo().PresentAddress().setAddress2(getStreetValue());
        loGoCas.ResidenceInfo().PresentAddress().setBarangay(BrgyID);
        loGoCas.ResidenceInfo().PresentAddress().setTownCity(TownID);
        loGoCas.ResidenceInfo().setOwnership(HouseOwnership);
        loGoCas.ResidenceInfo().setHouseType(String.valueOf(sprHouseType.getSelectedItemPosition()));
        loGoCas.ResidenceInfo().hasGarage(hasGarage);
        setupOwnershipInfo();
        if(cbPermntAdd.isChecked()) {
            loGoCas.ResidenceInfo().PermanentAddress().setLandMark(tieLandmark.getText().toString());
            loGoCas.ResidenceInfo().PermanentAddress().setHouseNo(tieHouseNox.getText().toString());
            loGoCas.ResidenceInfo().PermanentAddress().setAddress1(tieSitioLot.getText().toString());
            loGoCas.ResidenceInfo().PermanentAddress().setAddress2(getStreetValue());
            loGoCas.ResidenceInfo().PermanentAddress().setTownCity(TownID);
            loGoCas.ResidenceInfo().PermanentAddress().setBarangay(BrgyID);
        } else {
            loGoCas.ResidenceInfo().PermanentAddress().setLandMark(Objects.requireNonNull(tiePermLndMark.getText()).toString());
            loGoCas.ResidenceInfo().PermanentAddress().setHouseNo(Objects.requireNonNull(tiePermHouseNo.getText()).toString());
            loGoCas.ResidenceInfo().PermanentAddress().setAddress1(Objects.requireNonNull(tiePermSitioLt.getText()).toString());
            loGoCas.ResidenceInfo().PermanentAddress().setAddress2(Objects.requireNonNull(tiePermStreetx.getText()).toString());
            loGoCas.ResidenceInfo().PermanentAddress().setTownCity(PermTownID);
            loGoCas.ResidenceInfo().PermanentAddress().setBarangay(PermBrgyID);
        }
        new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, TransNox);
        Log.e(TAG, "Residence information result : " + loGoCas.ResidenceInfo().PresentAddress().toJSONString());
        Log.e(TAG, "Residence information result : " + loGoCas.ResidenceInfo().PermanentAddress().toJSONString());
        if(Fragment_IncomStatus.getInstance().getFragmentSize() == 0) {
            new Dialog_IncomeSource(getActivity(), TransNox, Dialog_IncomeSource.TYPE.APPLICANT).showDialog();
        }
    }

    private String getStreetValue(){
        if(!Objects.requireNonNull(tieStreestx.getText()).toString().isEmpty()){
            return tieStreestx.getText().toString();
        }
        return tieBarangay.getText().toString();
    }

    private void setupOwnershipInfo(){
        switch (HouseOwnership){
            case "0":
                setupOwnedHouseInfo();
                break;
            case "1":
                setupRentalInfo();
                break;
            case "2":
                setupCareTakerInfo();
                break;
        }
    }

    private void setupOwnedHouseInfo(){
        loGoCas.ResidenceInfo().setOwnedResidenceInfo(String.valueOf(sprLivngStat.getSelectedItemPosition()));
    }

    private void setupRentalInfo(){
        loGoCas.ResidenceInfo().setRentedResidenceInfo(String.valueOf(sprLivngStat.getSelectedItemPosition()));
        loGoCas.ResidenceInfo().setRentNoYears(new FormatUIText().getParseBusinessLength(Objects.requireNonNull(tieLngtSty.getText()).toString(), sprRentLvngLnght.getSelectedItemPosition()));
        loGoCas.ResidenceInfo().setRentExpenses(new FormatUIText().getParseLong(Objects.requireNonNull(tieRentExp.getText()).toString()));
    }

    private void setupCareTakerInfo(){
        loGoCas.ResidenceInfo().setCareTakerRelation(Objects.requireNonNull(tieRelation.getText()).toString());
        loGoCas.ResidenceInfo().setRentedResidenceInfo(String.valueOf(sprLivngStat.getSelectedItemPosition()));
        loGoCas.ResidenceInfo().setRentNoYears(new FormatUIText().getParseBusinessLength(Objects.requireNonNull(tieLenghtStay.getText()).toString(), sprCtkrLvngLngth.getSelectedItemPosition()));
        loGoCas.ResidenceInfo().setRentExpenses(new FormatUIText().getParseLong(Objects.requireNonNull(tieCrtkrExpns.getText()).toString()));
    }

    @SuppressLint("NewApi")
    private ArrayAdapter<String> getOwnHouseOptions(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getLivingStatus);
    }

    @SuppressLint("NewApi")
    private ArrayAdapter<String> getHouseType(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getHouseType);
    }

    class OwnershipSelectionListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_cap_Owned) {
                HouseOwnership = "0";
                setupSpinnerField();
            } else if(checkedId == R.id.rb_cap_Rent){
                HouseOwnership = "1";
                setupRentfoField();
            } else if(checkedId == R.id.rb_cap_CareTaker){
                HouseOwnership = "2";
                setupCareTakerField();
            }
        }
    }

    @SuppressLint("NewApi")
    private ArrayAdapter<String> getProvinceList(){
        String[] arr_name = new String[address.getProvinceList().get(1).size()];
        for(int x = 0; x < address.getProvinceList().get(1).size(); x++){
            arr_name[x] = address.getProvinceList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, arr_name);
    }

    @SuppressLint("NewApi")
    private ArrayAdapter<String> getTownList(String ProvinceID){
        String[] arr_name = new String[address.getTownList(ProvinceID).get(1).size()];
        for(int x = 0; x < address.getTownList(ProvinceID).get(1).size(); x++){
            arr_name[x] = address.getTownList(ProvinceID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, arr_name);
    }

    @SuppressLint("NewApi")
    private ArrayAdapter<String> getBarangayList(String TownCityID){
        String[] arr_name = new String[address.getBarangayList(TownCityID).get(1).size()];
        for(int x = 0; x < address.getBarangayList(TownCityID).get(1).size(); x++){
            arr_name[x] = address.getBarangayList(TownCityID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, arr_name);
    }

    private void setupRentfoField(){
        linearRentx.setVisibility(View.VISIBLE);
        if(linearCareT.getVisibility() == View.VISIBLE){
            linearCareT.setVisibility(View.GONE);
        }
    }

    private void setupSpinnerField(){
        if(linearRentx.getVisibility() == View.VISIBLE){
            linearRentx.setVisibility(View.GONE);
        }
        if(linearCareT.getVisibility() == View.VISIBLE){
            linearCareT.setVisibility(View.GONE);
        }
    }

    private void setupCareTakerField(){
        linearCareT.setVisibility(View.VISIBLE);
        if(linearRentx.getVisibility() == View.VISIBLE){
            linearRentx.setVisibility(View.GONE);
        }
    }

    class ButtonCLickListener implements View.OnClickListener{

        @SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_fragment_residence_prevs){
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(0, true);
            } else if(v.getId() == R.id.btn_fragment_residence_next){
                if(isDataValid()) {
                    setupResidenceInfo();
                    ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(2, true);
                }
            }
        }
    }

    class OnGarageTypeSelectListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.rb_cap_garageYes){
                hasGarage = "1";
            } else if(checkedId == R.id.rb_cap_garageNo){
                hasGarage = "0";
            }
        }
    }

    class OnAddressSelectedListener implements AdapterView.OnItemClickListener{

        View addView;

        OnAddressSelectedListener(View view){
            this.addView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(addView.getId() == R.id.tie_cap_rsdnProvince) {
                for (int x = 0; x < address.getProvinceList().get(1).size(); x++) {
                    if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))) {
                        ProvID = address.getProvinceList().get(0).get(x);
                        tieCityTown.setAdapter(getTownList(ProvID));
                        break;
                    }
                }
            } else if(addView.getId() == R.id.tie_cap_rsdnTown){
                for(int x = 0; x < address.getTownList(ProvID).get(1).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                        TownID = address.getTownList(ProvID).get(0).get(x);
                        tieBarangay.setAdapter(getBarangayList(TownID));
                        break;
                    }
                }
            } else if(addView.getId() == R.id.tie_cap_rsdnBrgy){
                for(int x = 0; x < address.getBarangayList(TownID).get(1).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getBarangayList(TownID).get(1).get(x))){
                        BrgyID = address.getBarangayList(TownID).get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnPermanentAddressSelectedListener implements CheckBox.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                linearPermt.setVisibility(View.GONE);
                PermLandmark = Objects.requireNonNull(tieLandmark.getText()).toString();
                PermHouseNox = Objects.requireNonNull(tieHouseNox.getText()).toString();
                PermStreetxx = Objects.requireNonNull(tieStreestx.getText()).toString();
                PermProvince = tieProvince.getText().toString();
                PermTownCity = tieCityTown.getText().toString();
                PermBarangay = tieBarangay.getText().toString();
                PermBrgyID = BrgyID;
                PermTownID = TownID;
            } else {
                linearPermt.setVisibility(View.VISIBLE);
            }
        }
    }

    class OnPermanentAddressSelectionListener implements AdapterView.OnItemClickListener{

        View permAdd;

        OnPermanentAddressSelectionListener(View view){
            this.permAdd = view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(permAdd.getId() == R.id.tie_cap_prmntRsdnProvince){
                for(int x = 0; x < address.getProvinceList().get(0).size(); x++) {
                    if (parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                        PermProvID = address.getProvinceList().get(0).get(x);
                        tiePermTownCty.setAdapter(getTownList(PermProvID));
                        break;
                    }
                }
            } else if(permAdd.getId() == R.id.tie_cap_prmntRsdnTown){
                for(int x = 0; x < address.getTownList(PermProvID).get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(PermProvID).get(1).get(x))){
                        PermTownID = address.getTownList(PermProvID).get(0).get(x);
                        tiePermBarangy.setAdapter(getBarangayList(PermTownID));
                        break;
                    }
                }
            } else if(permAdd.getId() == R.id.tie_cap_prmntRsdnBrgy){
                for(int x = 0; x < address.getBarangayList(PermTownID).get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getBarangayList(PermTownID).get(1).get(x))){
                        PermBrgyID = address.getBarangayList(PermTownID).get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnAddressChangeListener implements TextWatcher{

        View v;

        OnAddressChangeListener(View view){
            this.v = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int id = v.getId();

            if(cbPermntAdd.isChecked()) {
                if(id == R.id.tie_cap_residenceLandM) {
                    PermLandmark = Objects.requireNonNull(tieLandmark.getText()).toString();
                } else if(id == R.id.tie_cap_applHouseNo) {
                    PermHouseNox = Objects.requireNonNull(tieHouseNox.getText()).toString();
                } else if(id == R.id.tie_cap_rsdnStreet) {
                    PermStreetxx = Objects.requireNonNull(tieStreestx.getText()).toString();
                } else if(id == R.id.tie_cap_rsdnSittio){
                    PermSittioLt = Objects.requireNonNull(tieSitioLot.getText()).toString();
                } else if(id == R.id.tie_cap_rsdnProvince) {
                    PermProvince = tieProvince.getText().toString();
                } else if(id == R.id.tie_cap_rsdnTown) {
                    PermTownCity = tieCityTown.getText().toString();
                } else if(id == R.id.tie_cap_rsdnBrgy) {
                    PermBarangay = tieBarangay.getText().toString();
                }
                PermTownID = TownID;
                PermBrgyID = BrgyID;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            int id = v.getId();
            if(id == R.id.tie_cap_rsdnProvince){
                tieProvince.removeTextChangedListener(this);
                isProvinceValid();
                tieProvince.addTextChangedListener(this);
            } else if(id == R.id.tie_cap_rsdnTown){
                tieCityTown.removeTextChangedListener(this);
                isCityTownValid();
                tieCityTown.addTextChangedListener(this);
            } else if(id == R.id.tie_cap_rsdnBrgy){
                tieBarangay.removeTextChangedListener(this);
                isBarangayValid();
                tieBarangay.addTextChangedListener(this);
            }

            if(!cbPermntAdd.isChecked()){
                if(id == R.id.tie_cap_prmntRsdnProvince) {
                    tiePermProvnce.removeTextChangedListener(this);
                    tiePermProvnce.addTextChangedListener(this);
                } else if(id == R.id.tie_cap_prmntRsdnTown){
                    tiePermTownCty.removeTextChangedListener(this);
                    tiePermTownCty.addTextChangedListener(this);
                } else if(id == R.id.tie_cap_prmntRsdnBrgy){
                    tiePermBarangy.removeTextChangedListener(this);
                    tiePermBarangy.addTextChangedListener(this);
                }
            }
        }
    }

    /**
     * Resident info validation methods...
     * **************************************************************
     * **************************************************************/

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isProvinceValid(){
        if(Objects.requireNonNull(tieProvince.getText()).toString().isEmpty()){
            tilprovince.setError("Please enter or select province.");
            requestFocus(tieProvince);
            return false;
        }
        tilprovince.setErrorEnabled(false);
        return true;
    }

    private boolean isCityTownValid(){
        if(ProvID.isEmpty()){
            tieCityTown.setText("");
            tilprovince.setError("Invalid province entered.");
            requestFocus(tieProvince);
            return false;
        } else {
            if (Objects.requireNonNull(tieCityTown.getText()).toString().isEmpty()) {
                tilCityTown.setError("Please enter or select city/town.");
                requestFocus(tilCityTown);
                return false;
            }
            tilCityTown.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isBarangayValid(){
        if(TownID.isEmpty()){
            tieBarangay.setText("");
            tilCityTown.setError("Invalid town entered.");
            requestFocus(tieCityTown);
            return false;
        } else {
            if (Objects.requireNonNull(tieBarangay.getText()).toString().isEmpty()) {
                tilBarangay.setError("Please enter or select barangay.");
                requestFocus(tilBarangay);
                return false;
            }
            tilBarangay.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isValidPresentAdd(){
        if(ProvID.isEmpty()){
            tilprovince.setError("Invalid province entered.");
            requestFocus(tieProvince);
            new CustomToast(getActivity(), "Invalid province input", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(TownID.isEmpty()){
            tilCityTown.setError("Invalid town entered.");
            requestFocus(tieCityTown);
            new CustomToast(getActivity(), "Invalid town input", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(BrgyID.isEmpty()){
            tilBarangay.setError("Invalid barangay entered.");
            requestFocus(tieBarangay);
            new CustomToast(getActivity(), "Invalid barangay input", CustomToast.TYPE.WARNING).show();
            return false;
        }
        return true;
    }

    private boolean isValidPermanentAddress(){
        if(!cbPermntAdd.isChecked()){
            return isPermProvinceValid() &&
                    isPermCityTownValid() &&
                    isPermBarangayValid() &&
                    isPermanentAddValid();
        }
        return true;
    }

    private boolean isPermProvinceValid(){
        if(Objects.requireNonNull(tiePermProvnce.getText()).toString().isEmpty()){
            tilprovince.setError("Please enter or select province.");
            requestFocus(tieProvince);
            new CustomToast(getActivity(), "Invalid province input", CustomToast.TYPE.WARNING).show();
            return false;
        }
        tilprovince.setErrorEnabled(false);
        return true;
    }

    private boolean isPermCityTownValid(){
        if(PermProvID.isEmpty()) {
            tieCityTown.setText("");
            tilPermTownCty.setError("Invalid province entered.");
            requestFocus(tiePermProvnce);
            return false;
        } else {
            if (Objects.requireNonNull(tiePermTownCty.getText()).toString().isEmpty()) {
                tilPermTownCty.setError("Please enter or select city/town.");
                requestFocus(tiePermTownCty);
                new CustomToast(getActivity(), "Invalid town input", CustomToast.TYPE.WARNING).show();
                return false;
            }
        }
        tilPermTownCty.setErrorEnabled(false);
        return true;
    }

    private boolean isPermBarangayValid(){
        if(PermTownID.isEmpty()) {
            tiePermBarangy.setText("");
            tilPermTownCty.setError("Invalid town entered.");
            requestFocus(tiePermTownCty);
            return false;
        } else {
            if (Objects.requireNonNull(tiePermBarangy.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Invalid barangay input", CustomToast.TYPE.WARNING).show();
                requestFocus(tiePermBarangy);
                return false;
            }
            tilPermBarangy.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isPermanentAddValid(){
        if(PermProvID.isEmpty()){
            requestFocus(tiePermProvnce);
            return false;
        } else if(PermTownID.isEmpty()){
            requestFocus(tiePermTownCty);
            return false;
        } else if(PermBrgyID.isEmpty()){
            requestFocus(tiePermBarangy);
            return false;
        } /*else if(Objects.requireNonNull(tiePermStreetx.getText()).toString().isEmpty()){
            requestFocus(tiePermStreetx);
            return false;
        }*/
        return true;
    }

    private boolean isOwnershipValid(){
        if(HouseOwnership.isEmpty()){
            lblErrorMessage.setVisibility(View.VISIBLE);
            return false;
        }
        lblErrorMessage.setVisibility(View.GONE);
        return true;
    }
}
