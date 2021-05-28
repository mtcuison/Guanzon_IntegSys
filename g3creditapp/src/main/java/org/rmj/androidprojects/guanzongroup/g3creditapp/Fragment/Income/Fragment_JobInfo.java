package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income;

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
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.TextCurrencyFormater;
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
public class Fragment_JobInfo extends Fragment {
    private static final String TAG = Fragment_JobInfo.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static Fragment_JobInfo instance;
    private GOCASApplication loGoCas;
    private View v;
    private AutoSuggestAddress address;
    private ConstantsAdapter adapter;
    private String ProvID = "";
    private String TownID = "";
    private String EmpSector = "1";
    private String MlPersonl = "0";
    private String UnPersonl = "0";
    private String JobTitlex = "";
    private String EmpStatus = "";
    private String lsCountry = "";
    private ArrayList<ArrayList<String>> arr_data;

    private RadioGroup rgEmpSector;
    private RadioGroup rgMilitaryx;
    private RadioGroup rgUniformed;
    private TextInputLayout tilBussType;
    private AutoCompleteTextView tieBussType;
    private TextInputLayout tilCompname;
    private TextInputLayout tilCompProv;
    private TextInputLayout tilCompTown;
    private LinearLayout linearEmpInfo;
    private TextInputEditText tieCompname;
    private TextInputEditText tieCompAddx;
    private AutoCompleteTextView tieCompProv;
    private AutoCompleteTextView tieCompTown;
    private TextInputLayout tilJobTitle;
    private AutoCompleteTextView tieJobTitle;
    private TextInputEditText tieSpfcJobx;
    private Spinner spnEmpStats;
    private Spinner spnEmpBsnss;
    private TextInputEditText tieLngthSrv;
    private TextInputEditText tieMnthlyIn;
    private TextInputEditText tieCompCntc;

    private LinearLayout linearGovSector;
    private LinearLayout linearPrvSector;
    private LinearLayout linearOfwSector;
    private Spinner spnGovlevel;
    private Spinner spnEmpGovL;
    private Spinner spnCmpLevel;
    private Spinner spnEmpLevel;
    private Spinner spnOfwCtgyx;
    private Spinner spnOvsRgion;
    private Spinner spnSvcLngth;
    private MaterialButton btnNext;
    private MaterialButton btnPrvs;
    private AutoCompleteTextView tieNationsx;

    private ArrayList<ArrayList<String>> arr_countries;

    public Fragment_JobInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_job_info, container, false);
        instance = this;
        address = new AutoSuggestAddress(getActivity());
        adapter = new ConstantsAdapter(getActivity());
        arr_countries = new Occupations(getActivity()).getOFWCountry();
        setupWidgets();
        setupWidgetActions();
        return v;
    }

    private void setupWidgets(){
        rgEmpSector = v.findViewById(R.id.rg_cap_ApplEmpSector);
        rgMilitaryx = v.findViewById(R.id.rg_cap_militaryPersonnel);
        rgUniformed = v.findViewById(R.id.rg_cap_uniformedPersonnel);
        linearGovSector = v.findViewById(R.id.linear_governmentSector);
        linearPrvSector = v.findViewById(R.id.linear_privateSector);
        linearOfwSector = v.findViewById(R.id.linear_ofwSector);
        tilBussType = v.findViewById(R.id.til_cap_empBusiness);
        tieBussType = v.findViewById(R.id.tie_cap_empBusiness);
        linearEmpInfo = v.findViewById(R.id.linear_employmentInfo);
        tilCompname = v.findViewById(R.id.til_cap_applCompName);
        tieCompname = v.findViewById(R.id.tie_cap_applCompName);
        tieCompAddx = v.findViewById(R.id.tie_cap_applCompAddress);
        tieCompProv = v.findViewById(R.id.tie_cap_applCompAddProv);
        tilCompProv = v.findViewById(R.id.til_cap_applCompAddProv);
        tieCompTown = v.findViewById(R.id.tie_cap_applCompAddTown);
        tilCompTown = v.findViewById(R.id.til_cap_applCompAddTown);
        tilJobTitle = v.findViewById(R.id.til_cap_ApplJobTitle);
        tieJobTitle = v.findViewById(R.id.tie_cap_ApplJobTitle);
        tieSpfcJobx = v.findViewById(R.id.tie_cap_applSpcfcJob);
        spnEmpStats = v.findViewById(R.id.spinner_cap_applEmpStatus);
        spnEmpBsnss = v.findViewById(R.id.spinner_cap_applbussinessType);
        tieLngthSrv = v.findViewById(R.id.tie_cap_applLenghtSrvc);
        tieMnthlyIn = v.findViewById(R.id.tie_cap_applMnthlyIncm);
        tieCompCntc = v.findViewById(R.id.tie_cap_applCompContact);
        spnGovlevel = v.findViewById(R.id.spinner_cap_govtLevel);
        spnEmpGovL = v.findViewById(R.id.spinner_cap_applPublicEmpLvl);
        spnEmpLevel = v.findViewById(R.id.spinner_cap_applPrivateEmpLvl);
        spnCmpLevel = v.findViewById(R.id.spinner_cap_compLevel);
        spnOfwCtgyx = v.findViewById(R.id.spinner_cap_ofwWorkCategory);
        spnOvsRgion = v.findViewById(R.id.spinner_cap_ofwRegion);
        tieNationsx = v.findViewById(R.id.tie_cap_ofwNation);
        spnSvcLngth = v.findViewById(R.id.spinner_cap_serviceLength);
        btnNext = v.findViewById(R.id.btn_fragment_employed_next);
        btnPrvs = v.findViewById(R.id.btn_fragment_employed_prevs);
    }

    private void setupWidgetActions(){
        rgEmpSector.setOnCheckedChangeListener(new OnEmploymentSectorSelectionListener(rgEmpSector));
        rgMilitaryx.setOnCheckedChangeListener(new OnEmploymentSectorSelectionListener(rgMilitaryx));
        rgUniformed.setOnCheckedChangeListener(new OnEmploymentSectorSelectionListener(rgUniformed));
        spnEmpBsnss.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Nature_Of_Business));
        tieCompProv.setAdapter(getProvinceList());
        tieCompProv.setOnItemClickListener(new OnAddressSelectionListener(tieCompProv));
        tieCompTown.setOnItemClickListener(new OnAddressSelectionListener(tieCompTown));
        tieJobTitle.setAdapter(getJobTitle());
        tieJobTitle.setOnItemClickListener(new OnJobTitleSelectedListener());
        tieMnthlyIn.addTextChangedListener(new TextCurrencyFormater(tieMnthlyIn));
        spnEmpStats.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Employment_Status));
        spnEmpStats.setOnItemSelectedListener(new OnJobStatusSelectedListener());
        spnEmpBsnss.setOnItemSelectedListener(new OnEmploymentStatusSelectionListener(spnEmpBsnss));
        spnGovlevel.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Government_Level));
        spnEmpGovL.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Employment_Level));
        spnCmpLevel.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Company_Level));
        spnEmpLevel.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Employment_Level));
        spnOfwCtgyx.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.OFW_Work_Type));
        spnOvsRgion.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Country_Region));
        tieNationsx.setAdapter(getCountries());
        tieNationsx.setOnItemClickListener(new OnAddressSelectionListener(tieNationsx));
        spnSvcLngth.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.TimeLength));
        tieCompProv.addTextChangedListener(new OnSetCompanyAddres(tieCompProv));
        tieCompTown.addTextChangedListener(new OnSetCompanyAddres(tieCompTown));
        btnNext.setOnClickListener(new OnButtonClickListener());
        btnPrvs.setOnClickListener(new OnButtonClickListener());
    }

    public static Fragment_JobInfo getInstance(){
        return instance;
    }

    private void setupJobInfo(){
        String transNox = new Activity_CreditApplication().getInstance().getTransNox();
        loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(transNox);
        loGoCas.MeansInfo().EmployedInfo().setEmploymentSector(EmpSector);
        setupSector();
        if (!spnEmpBsnss.getSelectedItem().toString().equalsIgnoreCase("Others")) {
            loGoCas.MeansInfo().EmployedInfo().setNatureofBusiness(spnEmpBsnss.getSelectedItem().toString());
        } else {
            loGoCas.MeansInfo().EmployedInfo().setNatureofBusiness(tieBussType.getText().toString());
        }
        if (!EmpSector.equalsIgnoreCase("2")) {
            loGoCas.MeansInfo().EmployedInfo().setCompanyName(Objects.requireNonNull(tieCompname.getText()).toString());
            loGoCas.MeansInfo().EmployedInfo().setCompanyAddress(Objects.requireNonNull(tieCompAddx.getText()).toString());
            loGoCas.MeansInfo().EmployedInfo().setEmployeeLevel(String.valueOf(spnEmpLevel.getSelectedItemPosition()));
            loGoCas.MeansInfo().EmployedInfo().setPosition(JobTitlex);
            loGoCas.MeansInfo().EmployedInfo().setEmployeeStatus(EmpStatus);
            loGoCas.MeansInfo().EmployedInfo().setJobDescription(Objects.requireNonNull(tieSpfcJobx.getText()).toString());
            loGoCas.MeansInfo().EmployedInfo().setLengthOfService(new FormatUIText().getParseBusinessLength(Objects.requireNonNull(tieLngthSrv.getText()).toString(), spnSvcLngth.getSelectedItemPosition()));
            loGoCas.MeansInfo().EmployedInfo().setSalary(new FormatUIText().getParseLong(Objects.requireNonNull(tieMnthlyIn.getText()).toString()));
            loGoCas.MeansInfo().EmployedInfo().setCompanyNo(Objects.requireNonNull(tieCompCntc.getText()).toString());
            loGoCas.MeansInfo().EmployedInfo().setCompanyTown(TownID);
        }
        new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, transNox);
        Log.e(TAG, "Job information result : " + loGoCas.MeansInfo().EmployedInfo().toJSONString());
    }

    private void setupSector(){
        switch(EmpSector){
            case "0":
                setUpGovermentSectorInfo();
                break;
            case "1":
                setupPrivateSectorInfo();
                break;
            case "2":
                setupOFWSectorInfo();
                break;
        }
    }

    private void setUpGovermentSectorInfo(){
        loGoCas.MeansInfo().EmployedInfo().setGovernmentLevel(String.valueOf(spnGovlevel.getSelectedItemPosition()));
        loGoCas.MeansInfo().EmployedInfo().IsMilitaryPersonel(MlPersonl);
        loGoCas.MeansInfo().EmployedInfo().IsUniformedPersonel(UnPersonl);
        loGoCas.MeansInfo().EmployedInfo().setEmployeeLevel(String.valueOf(spnEmpGovL.getSelectedItemPosition()));
    }

    private void setupPrivateSectorInfo(){
        loGoCas.MeansInfo().EmployedInfo().setCompanyLevel(String.valueOf(spnCmpLevel.getSelectedItemPosition()));
        loGoCas.MeansInfo().EmployedInfo().setEmployeeLevel(String.valueOf(spnEmpLevel.getSelectedItemPosition()));
    }

    private void setupOFWSectorInfo(){
        loGoCas.MeansInfo().EmployedInfo().setOFWCategory(String.valueOf(spnOfwCtgyx.getSelectedItemPosition()));
        loGoCas.MeansInfo().EmployedInfo().setOFWRegion(String.valueOf(spnOvsRgion.getSelectedItemPosition()));
        loGoCas.MeansInfo().EmployedInfo().setOFWNation(lsCountry);
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

    private ArrayAdapter<String> getJobTitle(){
        arr_data = new Occupations(getActivity()).getJobTitles();
        String[] data = new String[arr_data.get(1).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = arr_data.get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getCountries(){
        String[] countries = new String[arr_countries.get(0).size()];
        for(int x = 0; x < countries.length; x++){
            countries[x] = arr_countries.get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, countries);
    }

    class OnEmploymentStatusSelectionListener implements AdapterView.OnItemSelectedListener{

        View spinner;

        OnEmploymentStatusSelectionListener(View view){
            this.spinner = view;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(spinner.getId() == R.id.spinner_cap_applbussinessType){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Others")){
                    tilBussType.setVisibility(View.VISIBLE);
                } else {
                    tilBussType.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnEmploymentSectorSelectionListener implements RadioGroup.OnCheckedChangeListener{

        View radioView;

        OnEmploymentSectorSelectionListener(View view){
            this.radioView = view;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int id = radioView.getId();
            if(id == R.id.rg_cap_ApplEmpSector){
                setSectorView(checkedId);
            } else if (id == R.id.rg_cap_uniformedPersonnel) {
                setupGovnSectorUniform(checkedId);
            } else if (id == R.id.rg_cap_militaryPersonnel) {
                setupGovnSectorMilitary(checkedId);
            }
        }

        void setSectorView(int checkedId){
            if (checkedId == R.id.rb_cap_empGovSector) {
                EmpSector = "0";
                spnEmpBsnss.setVisibility(View.GONE);
                tieJobTitle.setVisibility(View.GONE);
                linearEmpInfo.setVisibility(View.VISIBLE);
                tilCompname.setHint("Government Agency/Institution");
                linearGovSector.setVisibility(View.VISIBLE);
                linearPrvSector.setVisibility(View.GONE);
                linearOfwSector.setVisibility(View.GONE);
            } else if(checkedId == R.id.rb_cap_empPrivSector){
                EmpSector = "1";
                spnEmpBsnss.setVisibility(View.VISIBLE);
                tieJobTitle.setVisibility(View.VISIBLE);
                linearEmpInfo.setVisibility(View.VISIBLE);
                tilCompname.setHint("Company Name");
                linearGovSector.setVisibility(View.GONE);
                linearPrvSector.setVisibility(View.VISIBLE);
                linearOfwSector.setVisibility(View.GONE);
            } else if(checkedId == R.id.rb_cap_empOFW){
                EmpSector = "2";
                linearEmpInfo.setVisibility(View.GONE);
                linearGovSector.setVisibility(View.GONE);
                linearPrvSector.setVisibility(View.GONE);
                linearOfwSector.setVisibility(View.VISIBLE);
            }
        }

        void setupGovnSectorUniform(int checkID){
            if(checkID == R.id.rb_cap_uniPersonnelYes){
                UnPersonl = "1";
            } else if(checkID == R.id.rb_cap_uniPersonnelNo){
                UnPersonl = "0";
            }
        }

        void setupGovnSectorMilitary(int checkID){
            if(checkID == R.id.rb_cap_miliPersonnelYes){
                MlPersonl = "1";
            } else if(checkID == R.id.rb_cap_miliPersonnelNo){
                MlPersonl = "0";
            }
        }
    }

    class OnAddressSelectionListener implements AdapterView.OnItemClickListener{

        View autoView;

        OnAddressSelectionListener(View view){
            this.autoView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (autoView.getId() == R.id.tie_cap_applCompAddProv) {
                for(int x = 0; x < address.getProvinceList().get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                        ProvID = address.getProvinceList().get(0).get(x);
                        tieCompTown.setAdapter(getTownList());
                        break;
                    }
                }
            } else if(autoView.getId() == R.id.tie_cap_applCompAddTown){
                for(int x = 0; x < address.getTownList(ProvID).get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                        TownID = address.getTownList(ProvID).get(0).get(x);
                        break;
                    }
                }
            } else if(autoView.getId() == R.id.tie_cap_ofwNation){
                for(int x = 0; x < arr_countries.get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(arr_countries.get(1).get(x))){
                        lsCountry = arr_countries.get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnJobStatusSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Tap here to select.")) {
                EmpStatus = "";
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Regular")) {
                EmpStatus = "R";
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Probitionary")) {
                EmpStatus = "P";
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Contractual")) {
                EmpStatus = "C";
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seasonal")) {
                EmpStatus = "S";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnJobTitleSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(int x =0; x < arr_data.get(0).size(); x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(arr_data.get(1).get(x))){
                    JobTitlex = arr_data.get(0).get(x);
                    break;
                }
            }
        }
    }

    class OnSetCompanyAddres implements TextWatcher{

        AutoCompleteTextView input;

        OnSetCompanyAddres(AutoCompleteTextView input){
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
            if(id == R.id.tie_cap_applCompAddProv){
                tieCompProv.removeTextChangedListener(this);
                isProvinceValid();
                tieCompProv.addTextChangedListener(this);
            } else if(id == R.id.tie_cap_applCompAddTown){
                tieCompTown.removeTextChangedListener(this);
                isTownValid();
                tieCompTown.addTextChangedListener(this);
            }
        }
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_fragment_employed_next){
                if(isDataValid()) {
                    setupJobInfo();
                    Fragment_IncomStatus.getInstance().goNextFragment();
                }
            } else {
                Fragment_IncomStatus.getInstance().goPreviousFragment();
            }
        }
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isDataValid(){
        if(EmpSector.equalsIgnoreCase("1")) {
            if (!Objects.requireNonNull(tieCompname.getText()).toString().isEmpty()) {
                if (!isCompAddressValid()) {
                    new CustomToast(getActivity(), "Company address is invalid. Please check", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if (JobTitlex.isEmpty()) {
                    new CustomToast(getActivity(), "Job title is invalid. Please check", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if (Objects.requireNonNull(tieSpfcJobx.getText()).toString().isEmpty()) {
                    new CustomToast(getActivity(), "Please enter specific job", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if(EmpStatus.isEmpty()){
                    new CustomToast(getActivity(), "Please select employment status", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if (Objects.requireNonNull(tieLngthSrv.getText()).toString().isEmpty()) {
                    new CustomToast(getActivity(), "Please enter the length of service in company", CustomToast.TYPE.WARNING).show();
                    return false;
                } else if (Objects.requireNonNull(tieMnthlyIn.getText()).toString().isEmpty()) {
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
            } else if (Objects.requireNonNull(tieCompname.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Please enter government agency/institution", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (!isCompAddressValid()) {
                new CustomToast(getActivity(), "Address is invalid. Please check.", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (Objects.requireNonNull(tieSpfcJobx.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Please enter specific job.", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (Objects.requireNonNull(tieLngthSrv.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Please enter the length of service in company", CustomToast.TYPE.WARNING).show();
                return false;
            } else if (Objects.requireNonNull(tieMnthlyIn.getText()).toString().isEmpty()) {
                new CustomToast(getActivity(), "Please provide atleast partial or estimated monthly income.", CustomToast.TYPE.WARNING).show();
                return false;
            }
        } else if(EmpSector.equalsIgnoreCase("2")){
            if(lsCountry.isEmpty()){
                new CustomToast(getActivity(), "Selected country is empty or invalid.", CustomToast.TYPE.WARNING).show();
                return false;
            }
        }
        return true;
    }

    private boolean isProvinceValid(){
        if(tieCompProv.getText().toString().isEmpty()){
            tilCompProv.setError("Please provide province.");
            requestFocus(tieCompProv);
            return false;
        }
        tilCompProv.setErrorEnabled(false);
        return true;
    }

    private boolean isTownValid(){
        if(isProvinceValid()) {
            if (tieCompTown.getText().toString().isEmpty()) {
                tilCompTown.setError("Please provide town.");
                requestFocus(tieCompTown);
                return false;
            }
            tilCompTown.setErrorEnabled(false);
            return true;
        }
        tieCompTown.setText("");
        requestFocus(tieCompProv);
        return false;
    }

    private boolean isCompAddressValid(){
        if(ProvID.isEmpty()){
            requestFocus(tieCompProv);
            tilCompProv.setError("Invalid province entered.");
            return false;
        }
        if(TownID.isEmpty()){
            requestFocus(tieCompTown);
            tilCompTown.setError("Invalid town entered.");
            return false;
        }
        return true;
    }
}