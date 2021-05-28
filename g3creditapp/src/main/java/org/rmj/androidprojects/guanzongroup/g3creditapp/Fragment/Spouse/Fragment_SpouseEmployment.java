package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse;

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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.ConstantsAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.FormatUIText;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.AutoSuggestAddress;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.Occupations;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_SpouseEmployment extends Fragment {
    private static final String TAG = Fragment_SpouseEmployment.class.getSimpleName();

    private View v;
    private GOCASApplication loGoCas;
    private AutoSuggestAddress address;
    private ConstantsAdapter adapter;
    private String EmpSector = "1";
    private String MlPersonl = "";
    private String UnPersonl = "";
    private String ProvID = "";
    private String TownID = "";
    private String CntryID = "";
    private String JobIDx = "";
    private String EmpStats = "";
    private String TransNox = "";
    private ArrayList<ArrayList<String>> arr_data;
    private ArrayList<ArrayList<String>> arr_countries;

    private RadioGroup rgEmpSector;
    private RadioGroup rgUniformed;
    private RadioGroup rgMilitaryx;
    private LinearLayout linearGovSector;
    private LinearLayout linearPrvSector;
    private LinearLayout linearOfwSector;
    private LinearLayout linearEmpInfoxx;
    private Spinner spnEmpBuss;
    private Spinner spnEmpStat;
    private Spinner spnGovLevl;
    private Spinner spnCompLvl;
    private Spinner spnEmpLevl;
    private Spinner spnOfwWork;
    private Spinner spnOfwRegn;
    private Spinner spnLnthSty;
    private Spinner spnSvcLnth;
    private TextInputLayout tilEmpBuss;
    private TextInputLayout tilJobTitl;
    private TextInputLayout tilProv;
    private TextInputLayout tilTown;
    private AutoCompleteTextView tieBussNtr;
    private AutoCompleteTextView tieJobTitl;
    private AutoCompleteTextView tieCountry;
    private TextInputEditText tieCompNme;
    private TextInputEditText tieCompAdd;
    private TextInputEditText tieLngtSrv;
    private TextInputEditText tieMnthInc;
    private TextInputEditText tieCompTel;
    private TextInputEditText tieOFWStay;
    private AutoCompleteTextView tieCompPrv;
    private AutoCompleteTextView tieCompTwn;
    private MaterialButton btnPrvs;
    private MaterialButton btnNext;

    public Fragment_SpouseEmployment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_spouse_income, container, false);
        address = new AutoSuggestAddress(getActivity());
        adapter = new ConstantsAdapter(getActivity());
        TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        setupWidgets();
        setupActions();
        return v;
    }

    private void setupWidgets(){
        rgEmpSector = v.findViewById(R.id.rg_cap_spsEmpSector);
        rgUniformed = v.findViewById(R.id.rg_cap_spsUniformedPersonnel);
        rgMilitaryx = v.findViewById(R.id.rg_cap_spsMilitaryPersonnel);
        linearGovSector = v.findViewById(R.id.linear_spsGovernmentSector);
        linearPrvSector = v.findViewById(R.id.linear_spsPrivateSector);
        linearOfwSector = v.findViewById(R.id.linear_spsOfwSector);
        linearEmpInfoxx = v.findViewById(R.id.linear_spsEmpInfo);
        tilEmpBuss = v.findViewById(R.id.til_cap_spsEmpBusinessNature);
        tilJobTitl = v.findViewById(R.id.til_cap_spsJobTitle);
        spnEmpBuss = v.findViewById(R.id.spinner_cap_spsEmpBussNature);
        spnEmpStat = v.findViewById(R.id.spinner_cap_spsEmpStatus);
        spnGovLevl = v.findViewById(R.id.spinner_cap_spsGovtLevel);
        spnCompLvl = v.findViewById(R.id.spinner_cap_spsCompLevel);
        spnEmpLevl = v.findViewById(R.id.spinner_cap_spsApplEmpLvl);
        spnOfwWork = v.findViewById(R.id.spinner_cap_spsOfwWorkCategory);
        spnOfwRegn = v.findViewById(R.id.spinner_cap_spsOfwRegion);
        spnSvcLnth = v.findViewById(R.id.spinner_cap_spsServiceLength);
        tieCountry = v.findViewById(R.id.tie_cap_spsOfwNation);
        spnLnthSty = v.findViewById(R.id.spinner_cap_spsYearsOFOfw);
        tieCompNme = v.findViewById(R.id.tie_cap_spsCompName);
        tieCompAdd = v.findViewById(R.id.tie_cap_spsCompAddress);
        tieCompPrv = v.findViewById(R.id.tie_cap_spsCompAddProv);
        tilTown = v.findViewById(R.id.til_cap_spsCompAddTown);
        tilProv = v.findViewById(R.id.til_cap_spsCompAddProv);
        tieCompTwn = v.findViewById(R.id.tie_cap_spsCompAddTown);
        tieBussNtr = v.findViewById(R.id.tie_cap_spsEmpBusiness);
        tieJobTitl = v.findViewById(R.id.tie_cap_spsJobTitle);
        tieLngtSrv = v.findViewById(R.id.tie_cap_spsLenghtSrvc);
        tieMnthInc = v.findViewById(R.id.tie_cap_spsMnthlyIncm);
        tieOFWStay = v.findViewById(R.id.tie_cap_spsOFWLenghtSrvc);
        tieCompTel = v.findViewById(R.id.tie_cap_spsCompContact);
        btnPrvs = v.findViewById(R.id.btn_fragment_spsJobInfo_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_spsJobInfo_next);
    }

    private void setupActions(){
        rgEmpSector.setOnCheckedChangeListener(new OnSectorSelectedListener());
        rgMilitaryx.setOnCheckedChangeListener(new OnGovPersonelSelectionListener(rgMilitaryx));
        rgUniformed.setOnCheckedChangeListener(new OnGovPersonelSelectionListener(rgUniformed));
        spnEmpBuss.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Nature_Of_Business));
        spnEmpStat.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Employment_Status));
        spnEmpStat.setOnItemSelectedListener(new OnJobStatusSelectedListener());
        tieJobTitl.setAdapter(getJobTitles());
        spnEmpBuss.setOnItemSelectedListener(new OnBusinessNatureSelectedListener());
        tieJobTitl.setOnItemClickListener(new OnOccupationSelectedListener());
        spnGovLevl.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Government_Level));
        spnCompLvl.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Company_Level));
        spnEmpLevl.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Employment_Level));
        spnOfwWork.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.OFW_Work_Type));
        spnOfwRegn.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Country_Region));
        spnSvcLnth.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.TimeLength));
        tieCountry.setAdapter(getCountry());
        spnLnthSty.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.TimeLength));
        tieCompPrv.setAdapter(getProvinceList());
        tieCompPrv.setOnItemClickListener(new OnAddressSelectionListener(tieCompPrv));
        tieCompTwn.setOnItemClickListener(new OnAddressSelectionListener(tieCompTwn));
        tieCountry.setOnItemClickListener(new OnAddressSelectionListener(tieCountry));
        tieCompPrv.addTextChangedListener(new OnAddressSetListener(tieCompPrv));
        tieCompTwn.addTextChangedListener(new OnAddressSetListener(tieCompTwn));
        btnPrvs.setOnClickListener(new OnButtonClickListener());
        btnNext.setOnClickListener(new OnButtonClickListener());
    }

    private void setupSpouseIncomeInfo(){
        loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(TransNox);
        loGoCas.SpouseMeansInfo().EmployedInfo().setEmploymentSector(EmpSector);
        setupSector();
        loGoCas.SpouseMeansInfo().EmployedInfo().setNatureofBusiness(getBusinessNature());
        if (!EmpSector.equalsIgnoreCase("2")) {
            loGoCas.SpouseMeansInfo().EmployedInfo().setCompanyName(Objects.requireNonNull(tieCompNme.getText()).toString());
            loGoCas.SpouseMeansInfo().EmployedInfo().setCompanyAddress(Objects.requireNonNull(tieCompAdd.getText()).toString());
            loGoCas.SpouseMeansInfo().EmployedInfo().setCompanyTown(TownID);
            loGoCas.SpouseMeansInfo().EmployedInfo().setPosition(JobIDx);
            loGoCas.SpouseMeansInfo().EmployedInfo().setEmployeeStatus(spnEmpStat.getSelectedItem().toString());
            loGoCas.SpouseMeansInfo().EmployedInfo().setJobDescription(Objects.requireNonNull(tieJobTitl.getText()).toString());
            loGoCas.SpouseMeansInfo().EmployedInfo().setEmployeeStatus(spnEmpStat.getSelectedItem().toString());
            loGoCas.SpouseMeansInfo().EmployedInfo().setLengthOfService(new FormatUIText().getParseBusinessLength(Objects.requireNonNull(tieLngtSrv.getText()).toString(), spnSvcLnth.getSelectedItemPosition()));
            loGoCas.SpouseMeansInfo().EmployedInfo().setSalary(getMonthlyIncome());
            loGoCas.SpouseMeansInfo().EmployedInfo().setCompanyNo(Objects.requireNonNull(tieCompTel.getText()).toString());
        }
        new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, TransNox);
        Log.e(TAG, "Spouse job information result :" + loGoCas.SpouseMeansInfo().EmployedInfo().toJSONString());
    }

    private void setupSector(){
        switch (EmpSector){
            case "0":
                setupGovernmentSectorInfo();
                break;
            case "1":
                setupPrivateSectorInfo();
                break;
            case "2":
                setupOFWInfo();
                break;
        }
    }

    private String getBusinessNature(){
        if(spnEmpBuss.getSelectedItem().toString().equalsIgnoreCase("Others")){
            return Objects.requireNonNull(tieBussNtr.getText()).toString();
        } else {
            return spnEmpBuss.getSelectedItem().toString();
        }
    }

    private Long getMonthlyIncome(){
        try{
            return(Long.parseLong(Objects.requireNonNull(tieMnthInc.getText()).toString()));
        } catch (Exception e){
            e.printStackTrace();
            return Long.valueOf("0");
        }
    }

    private void setupGovernmentSectorInfo(){
        loGoCas.SpouseMeansInfo().EmployedInfo().setEmploymentSector("0");
        loGoCas.SpouseMeansInfo().EmployedInfo().setGovernmentLevel(spnGovLevl.getSelectedItem().toString());
        loGoCas.SpouseMeansInfo().EmployedInfo().IsUniformedPersonel(MlPersonl);
        loGoCas.SpouseMeansInfo().EmployedInfo().IsMilitaryPersonel(UnPersonl);
    }

    private void setupPrivateSectorInfo(){
        loGoCas.SpouseMeansInfo().EmployedInfo().setEmploymentSector("1");
        loGoCas.SpouseMeansInfo().EmployedInfo().setNatureofBusiness(spnEmpBuss.getSelectedItem().toString());
        loGoCas.SpouseMeansInfo().EmployedInfo().setCompanyLevel(spnCompLvl.getSelectedItem().toString());
        loGoCas.SpouseMeansInfo().EmployedInfo().setEmployeeLevel(String.valueOf(spnEmpLevl.getSelectedItemPosition()));
        loGoCas.SpouseMeansInfo().EmployedInfo().setCompanyAddress(Objects.requireNonNull(tieCompAdd.getText()).toString());
        loGoCas.SpouseMeansInfo().EmployedInfo().setCompanyNo(Objects.requireNonNull(tieCompTel.getText()).toString());
        loGoCas.SpouseMeansInfo().EmployedInfo().setCompanyTown(TownID);
    }

    private void setupOFWInfo(){
        loGoCas.SpouseMeansInfo().EmployedInfo().setEmploymentSector("2");
        loGoCas.SpouseMeansInfo().EmployedInfo().setOFWCategory(String.valueOf(spnOfwWork.getSelectedItemPosition()));
        loGoCas.SpouseMeansInfo().EmployedInfo().setOFWRegion(String.valueOf(spnOfwRegn.getSelectedItemPosition()));
        loGoCas.SpouseMeansInfo().EmployedInfo().setOFWNation(CntryID);
        loGoCas.SpouseMeansInfo().EmployedInfo().setLengthOfService(new FormatUIText().getParseBusinessLength(Objects.requireNonNull(tieOFWStay.getText()).toString(), spnLnthSty.getSelectedItemPosition()));
    }

    private ArrayAdapter<String> getJobTitles(){
        arr_data = new Occupations(getActivity()).getJobTitles();
        String[] data = new String[arr_data.get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = arr_data.get(1).get(x);
    }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getProvinceList(){
        String[] data = new String[address.getProvinceList().get(1).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getProvinceList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getTownList(){
        String[] data = new String[address.getTownList(ProvID).get(1).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getTownList(ProvID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getCountry(){
        arr_countries = new Occupations(getActivity()).getOFWCountry();
        String[] data = new String[arr_countries.get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = arr_countries.get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    class OnAddressSelectionListener implements AdapterView.OnItemClickListener{

        View autoView;

        OnAddressSelectionListener(View view){
            this.autoView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(autoView.getId() == R.id.tie_cap_spsCompAddProv){
                for(int x = 0; x < address.getProvinceList().get(1).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                        ProvID = address.getProvinceList().get(0).get(x);
                        tieCompTwn.setAdapter(getTownList());
                        break;
                    }
                }
            } else if(autoView.getId() == R.id.tie_cap_spsCompAddTown){
                for(int x = 0; x < address.getTownList(ProvID).get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                        TownID = address.getTownList(ProvID).get(0).get(x);
                        break;
                    }
                }
            } else if(autoView.getId() == R.id.tie_cap_spsOfwNation){
                for(int x = 0; x < arr_countries.get(1).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(arr_countries.get(1).get(x))){
                        CntryID = arr_countries.get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnBusinessNatureSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Others")){
                tilEmpBuss.setVisibility(View.VISIBLE);
            } else {
                tilEmpBuss.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnGovPersonelSelectionListener implements RadioGroup.OnCheckedChangeListener{

        View radioView;

        OnGovPersonelSelectionListener(View view){
            this.radioView = view;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(radioView.getId() == R.id.rg_cap_spsUniformedPersonnel){
                if(checkedId == R.id.rb_cap_spsUniPersonnelYes){
                    UnPersonl = "Y";
                } else if(checkedId == R.id.rb_cap_spsUniPersonnelNo){
                    UnPersonl = "N";
                }
            } else if(checkedId == R.id.rg_cap_spsMilitaryPersonnel){
                if(checkedId == R.id.rb_cap_spsMiliPersonnelYes){
                    MlPersonl = "Y";
                } else if(checkedId == R.id.rb_cap_spsMiliPersonnelNo){
                    MlPersonl = "N";
                }
            }
        }
    }

    class OnSectorSelectedListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.rb_cap_spsEmpGovSector){
                EmpSector = "0";
                linearEmpInfoxx.setVisibility(View.VISIBLE);
                linearGovSector.setVisibility(View.VISIBLE);
                linearPrvSector.setVisibility(View.GONE);
                linearOfwSector.setVisibility(View.GONE);
            } else if(checkedId == R.id.rb_cap_spsEmpPrivSector){
                EmpSector = "1";
                linearEmpInfoxx.setVisibility(View.VISIBLE);
                linearGovSector.setVisibility(View.GONE);
                linearPrvSector.setVisibility(View.VISIBLE);
                linearOfwSector.setVisibility(View.GONE);
            } else if(checkedId == R.id.rb_cap_spsOfw){
                EmpSector = "2";
                linearGovSector.setVisibility(View.GONE);
                linearPrvSector.setVisibility(View.GONE);
                linearOfwSector.setVisibility(View.VISIBLE);
                linearEmpInfoxx.setVisibility(View.GONE);
            }
        }
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_fragment_spsJobInfo_prevs){
                    new Fragment_SpouseMeans().getInstance().goPreviousFragment();
            } else if(v.getId() == R.id.btn_fragment_spsJobInfo_next){
                if(isDataValid()) {
                    setupSpouseIncomeInfo();
                    new Fragment_SpouseMeans().getInstance().goNextFragment();
                }
            }
        }
    }

    class OnOccupationSelectedListener implements AdapterView.OnItemClickListener{

        @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int x = 0; x < arr_data.get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(arr_data.get(1).get(x))){
                        JobIDx = arr_data.get(0).get(x);
                        break;
                    }
                }
            }
    }

    class OnAddressSetListener implements TextWatcher{

        AutoCompleteTextView input;

        OnAddressSetListener(AutoCompleteTextView input){
            this.input = input;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int id = input.getId();
            if(id == R.id.tie_cap_spsCompAddProv){
                isProvinceValid();
            } else if(id == R.id.tie_cap_spsCompAddTown){
                isTownValid();
            }
        }
    }

    class OnJobStatusSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Tap here to select.")) {
                EmpStats = "";
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Regular")) {
                EmpStats = "R";
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Probitionary")) {
                EmpStats = "P";
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Contractual")) {
                EmpStats = "C";
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seasonal")) {
                EmpStats = "S";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isDataValid(){
        if(EmpSector.equalsIgnoreCase("1")) {
            if (!Objects.requireNonNull(tieCompNme.getText()).toString().isEmpty()) {
                if (!isCompAddressValid()) {
                    new CustomToast(getActivity(), "Company address is invalid. Please check", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if (JobIDx.isEmpty()) {
                    new CustomToast(getActivity(), "Job title is invalid. Please check", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if (Objects.requireNonNull(tieJobTitl.getText()).toString().isEmpty()) {
                    new CustomToast(getActivity(), "Please enter specific job", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if(EmpStats.isEmpty()){
                    new CustomToast(getActivity(), "Please select employment status", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if (Objects.requireNonNull(tieLngtSrv.getText()).toString().isEmpty()) {
                    new CustomToast(getActivity(), "Please enter the length of service in company", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if (Objects.requireNonNull(tieMnthInc.getText()).toString().isEmpty()) {
                    new CustomToast(getActivity(), "Please provide atleast partial or estimated monthly income.", CustomToast.TYPE.WARNING).show();
                    return false;
                }
            } else {
                new CustomToast(getActivity(), "Please enter company name", CustomToast.TYPE.WARNING).show();
                return false;
            }
        } else if(EmpSector.equalsIgnoreCase("0")){
            if (MlPersonl.isEmpty()){
                new CustomToast(getActivity(), "Please check if military personnel", CustomToast.TYPE.WARNING).show();
                return false;
            } else if(UnPersonl.isEmpty()){
                new CustomToast(getActivity(), "Please check if uniformed personnel", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (Objects.requireNonNull(tieCompNme.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Please enter government agency/institution", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (!isCompAddressValid()) {
                new CustomToast(getActivity(), "Address is invalid. Please check.", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (Objects.requireNonNull(tieJobTitl.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Please enter specific job.", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (Objects.requireNonNull(tieLngtSrv.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Please enter the length of service in company", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (Objects.requireNonNull(tieMnthInc.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Please provide atleast partial or estimated monthly income.", CustomToast.TYPE.WARNING).show();
                return false;
            }
        } else if(EmpSector.equalsIgnoreCase("2")){
            if(CntryID.isEmpty()){
                new CustomToast(getActivity(), "Selected country is empty or invalid.", CustomToast.TYPE.WARNING).show();
                return false;
            }
        }
        return true;
    }

    private boolean isProvinceValid(){
        if(tieCompPrv.getText().toString().isEmpty()){
            tilProv.setError("Please provide province.");
            requestFocus(tieCompPrv);
            return false;
        }

        tilProv.setErrorEnabled(false);
        return true;
    }

    private boolean isTownValid(){
        if(isProvinceValid()){
            if(tieCompTwn.getText().toString().isEmpty()){
                tilTown.setError("Please provide town.");
                requestFocus(tieCompTwn);
                return false;
            }
            tilTown.setErrorEnabled(false);
            return true;
        }

        requestFocus(tieCompPrv);
        return false;
    }

    private boolean isAddressValid(){
        if(!Objects.requireNonNull(tieCompNme.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(tieLngtSrv.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(tieMnthInc.getText()).toString().isEmpty() &&
                ProvID.isEmpty() &&
                TownID.isEmpty()){
            return false;
        }
        return true;
    }

    private boolean isCompAddressValid(){
        if(ProvID.isEmpty()){
            requestFocus(tieCompPrv);
            tieCompPrv.setError("Invalid province entered.");
            return false;
        }
        if(TownID.isEmpty()){
            requestFocus(tieCompTwn);
            tieCompTwn.setError("Invalid town entered.");
            return false;
        }
        return true;
    }
}
