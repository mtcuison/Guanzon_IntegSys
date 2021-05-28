package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Disbursement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.DependenciesAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.CreditSourceObjects;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.Dependency;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.AutoSuggestAddress;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Dependencies extends Fragment {
    private static final String TAG = Fragment_Dependencies.class.getSimpleName();

    private CreditApplication poCreditApp;
    private String TransNox;
    private GOCASApplication poGoCas;

    private AutoSuggestAddress address;
    private View v;

    private String Employment = "";
    private String IsStudentx = "0";
    private String IsEmployed = "0";
    private String Dependentx = "0";
    private String HouseHoldx = "0";
    private String IsMarriedx = "0";
    private String IsScholarx = "0";
    private String IsPrivatex = "0";
    private String ProvID = "";
    private String TownID = "";
    private ArrayList<String> Dependencies;
    private DependenciesAdapter adapter;
    private List<Dependency> dependentList;

    private TextInputLayout tilFullname;
    private TextInputLayout tilDpdAgexx;
    private TextInputLayout tilSchoolNm;
    private TextInputLayout tilSchlAddx;
    private TextInputLayout tilSchlProv;
    private TextInputLayout tilSchlTown;
    private TextInputLayout tilCompName;
    private TextInputEditText tieFullname;
    private TextInputEditText tieDpdAgexx;
    private TextInputEditText tieSchoolNm;
    private TextInputEditText tieSchlAddx;
    private AutoCompleteTextView tieSchlProv;
    private AutoCompleteTextView tieSchlTown;
    private TextInputEditText tieCompName;
    private RadioGroup rgDpdStudent;
    private RadioGroup rgDpdEmpymnt;
    private RadioGroup rgDpdEmployd;
    private RadioGroup rgSchoolType;
    private Spinner spnSchoolLvl;
    private Spinner spnRelationx;
    private CheckBox cbScholarxx;
    private CheckBox cbDependent;
    private CheckBox cbHouseHold;
    private CheckBox cbIsMarried;
    private LinearLayout linearStudent;
    private LinearLayout linearEmployd;
    private RecyclerView recyclerView;

    private MaterialButton btnAddDependent;
    private MaterialButton btnPrev;
    private MaterialButton btnNext;
    public Fragment_Dependencies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_dependencies, container, false);
        dependentList = new ArrayList<>();
        setupJavaClass();
        setupWidgets();
        setupActionListener();
        setupList();
        return v;
    }

    private void setupJavaClass(){
        dependentList = new ArrayList<>();
        address = new AutoSuggestAddress(getActivity());
        Dependencies = new ArrayList<>();
        poCreditApp = new CreditApplication(getActivity());
        TransNox = new Activity_CreditApplication().getInstance().getTransNox();
    }

    private void setupWidgets(){
        tilFullname = v.findViewById(R.id.til_cap_dpdFullname);
        tilDpdAgexx = v.findViewById(R.id.til_cap_dpdAge);
        tilCompName = v.findViewById(R.id.til_cap_dpdCompanyName);
        tilSchoolNm = v.findViewById(R.id.til_cap_dpdSchoolName);
        tilSchlAddx = v.findViewById(R.id.til_cap_dpdSchoolAddress);
        tilSchlProv = v.findViewById(R.id.til_cap_dpdSchoolProv);
        tilSchlTown = v.findViewById(R.id.til_cap_dpdSchoolTown);

        tieFullname = v.findViewById(R.id.tie_cap_dpdFullname);
        tieDpdAgexx = v.findViewById(R.id.tie_cap_dpdAge);
        tieCompName = v.findViewById(R.id.tie_cap_dpdCompanyName);
        tieSchoolNm = v.findViewById(R.id.tie_cap_dpdSchoolName);
        tieSchlAddx = v.findViewById(R.id.tie_cap_dpdSchoolAddress);
        tieSchlProv = v.findViewById(R.id.tie_cap_dpdSchoolProv);
        tieSchlTown = v.findViewById(R.id.tie_cap_dpdSchoolTown);
        linearStudent = v.findViewById(R.id.linear_dpdStudent);
        linearEmployd = v.findViewById(R.id.linear_dpdEmployed);
        rgDpdStudent = v.findViewById(R.id.rg_cap_dpdStudent);
        rgDpdEmployd = v.findViewById(R.id.rg_cap_dpdEmployed);
        rgDpdEmpymnt = v.findViewById(R.id.rg_cap_dpdEmployedSector);
        rgSchoolType = v.findViewById(R.id.rg_cap_dpdSchoolType);

        spnSchoolLvl = v.findViewById(R.id.spinner_cap_eduElvel);
        spnRelationx = v.findViewById(R.id.spinner_cap_dpdRelation);
        cbDependent = v.findViewById(R.id.cb_cap_Dependent);
        cbHouseHold = v.findViewById(R.id.cb_cap_HouseHold);
        cbIsMarried = v.findViewById(R.id.cb_cap_Married);
        cbScholarxx = v.findViewById(R.id.cb_cap_dpdScholar);
        recyclerView = v.findViewById(R.id.recyclerview_dependencies);

        btnAddDependent = v.findViewById(R.id.btn_fragment_dependency_addDependent);
        btnPrev = v.findViewById(R.id.btn_fragment_dependency_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_dependency_next);
    }

    private void setupActionListener(){
        tieSchlProv.setAdapter(getProvinceList());
        tieSchlProv.setOnItemClickListener(new OnAddressSelectionListener(tieSchlProv));
        tieSchlTown.setOnItemClickListener(new OnAddressSelectionListener(tieSchlTown));
        spnSchoolLvl.setAdapter(getEducationLevel());
        spnRelationx.setAdapter(getDependentRelation());
        rgSchoolType.setOnCheckedChangeListener(new OnDependencyStatusSelectionListener(rgSchoolType));
        rgDpdStudent.setOnCheckedChangeListener(new OnDependencyStatusSelectionListener(rgDpdStudent));
        rgDpdEmployd.setOnCheckedChangeListener(new OnDependencyStatusSelectionListener(rgDpdEmployd));
        rgDpdEmpymnt.setOnCheckedChangeListener(new OnDependencyStatusSelectionListener(rgDpdEmpymnt));
        cbDependent.setOnCheckedChangeListener(new OnDependencySelectionListener(cbDependent));
        cbHouseHold.setOnCheckedChangeListener(new OnDependencySelectionListener(cbHouseHold));
        cbIsMarried.setOnCheckedChangeListener(new OnDependencySelectionListener(cbIsMarried));
        cbScholarxx.setOnCheckedChangeListener(new OnDependencySelectionListener(cbScholarxx));

        btnAddDependent.setOnClickListener(new OnButtonClickListener());
        spnRelationx.setOnItemSelectedListener(new OnDependentSelectedListener());
        btnPrev.setOnClickListener(new OnButtonClickListener());
        btnNext.setOnClickListener(new OnButtonClickListener());
    }

    private void setupList(){
        adapter = new DependenciesAdapter(Dependencies);
        adapter.setOnDependencyRemoveListener(position -> {
            removeDependent(position);
            Dependencies.remove(position);
            adapter.notifyDataSetChanged();
            setupRecyclerviewVisibility();
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void AddDependencies(){
        if(isValidChildInfo()) {
            addDependent();
            Dependencies.add(Objects.requireNonNull(tieFullname.getText()).toString());
            setupRecyclerviewVisibility();
            clearInputField();
        }
    }

    private void addDependent(){
        Dependency loDependency = new Dependency();
        loDependency.setFullNamexx(Objects.requireNonNull(tieFullname.getText()).toString());
        loDependency.setDpRelation(String.valueOf(spnRelationx.getSelectedItemPosition()));
        loDependency.setDpndentAge(Objects.requireNonNull(tieDpdAgexx.getText()).toString());
        loDependency.setIsStudentx(IsStudentx);
        loDependency.setSchoolName(Objects.requireNonNull(tieSchoolNm.getText()).toString());
        loDependency.setSchoolAddx(Objects.requireNonNull(tieSchlAddx.getText()).toString());
        loDependency.setSchoolTown(TownID);
        loDependency.setSchoolLevl(String.valueOf(spnSchoolLvl.getSelectedItemPosition()));
        loDependency.setIsPrivatex(IsPrivatex);
        loDependency.setIsScholarx(IsScholarx);
        loDependency.setIsEmployed(IsEmployed);
        loDependency.setEmployment(Employment);
        loDependency.setIsMarriedx(IsMarriedx);
        loDependency.setHouseHoldx(HouseHoldx);
        loDependency.setIsDpndentx(Dependentx);
        loDependency.setCompanyNmx(Objects.requireNonNull(tieCompName.getText()).toString());
        dependentList.add(loDependency);
    }

    private void setupDependentInfo(){
        poGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        for(int x = 0; x < dependentList.size(); x++) {
            poGoCas.DisbursementInfo().DependentInfo().addDependent();
            poGoCas.DisbursementInfo().DependentInfo().setFullName(x, dependentList.get(x).getFullNamexx());
            poGoCas.DisbursementInfo().DependentInfo().setRelation(x, dependentList.get(x).getDpRelation());
            poGoCas.DisbursementInfo().DependentInfo().setAge(x, Integer.parseInt(dependentList.get(x).getDpndentAge()));
            poGoCas.DisbursementInfo().DependentInfo().IsStudent(x, dependentList.get(x).getIsStudentx());
            poGoCas.DisbursementInfo().DependentInfo().IsWorking(x, dependentList.get(x).getIsEmployed());
            poGoCas.DisbursementInfo().DependentInfo().IsDependent(x, dependentList.get(x).getIsDpndentx());
            poGoCas.DisbursementInfo().DependentInfo().IsHouseHold(x, dependentList.get(x).getHouseHoldx());
            poGoCas.DisbursementInfo().DependentInfo().IsMarried(x, dependentList.get(x).getIsMarriedx());
            poGoCas.DisbursementInfo().DependentInfo().setSchoolName(x, dependentList.get(x).getSchoolName());
            poGoCas.DisbursementInfo().DependentInfo().setSchoolAddress(x, dependentList.get(x).getSchoolAddx());
            poGoCas.DisbursementInfo().DependentInfo().setSchoolTown(x, dependentList.get(x).getSchoolTown());
            poGoCas.DisbursementInfo().DependentInfo().setEducationalLevel(x, dependentList.get(x).getSchoolLevl());
            poGoCas.DisbursementInfo().DependentInfo().IsPrivateSchool(x, dependentList.get(x).getIsPrivatex());
            poGoCas.DisbursementInfo().DependentInfo().IsScholar(x, dependentList.get(x).getIsScholarx());
            poGoCas.DisbursementInfo().DependentInfo().IsWorking(x, dependentList.get(x).getIsEmployed());
            poGoCas.DisbursementInfo().DependentInfo().setWorkType(x, dependentList.get(x).getEmployment());
            poGoCas.DisbursementInfo().DependentInfo().setCompany(x, dependentList.get(x).getCompanyNmx());
        }
        Log.e(TAG, "Dependent info has been set." + poGoCas.DisbursementInfo().DependentInfo().toJSONString());
    }

    private void clearInputField(){
        tieFullname.setText("");
        tieDpdAgexx.setText("");
        tieCompName.setText("");
        tieSchoolNm.setText("");
        tieSchlAddx.setText("");
        tieSchlTown.setText("");
        rgDpdEmployd.clearCheck();
        rgDpdEmpymnt.clearCheck();
        rgDpdStudent.clearCheck();
        rgSchoolType.clearCheck();
        cbDependent.setChecked(false);
        cbHouseHold.setChecked(false);
        cbIsMarried.setChecked(false);
        cbScholarxx.setChecked(false);
        adapter.notifyDataSetChanged();
    }

    private boolean isValidChildInfo(){
        if(!isNameValid()){
            return false;
        }
        if(!isAgeValid()){
            return false;
        }

        if(IsEmployed.equalsIgnoreCase("1")){
            return isCompanyValid();
        }
        return true;
    }

    private void setupRecyclerviewVisibility(){
        if(adapter.getItemCount() != 0){
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void removeDependent(int position){
        dependentList.remove(position);
    }

    private ArrayAdapter<String> getProvinceList(){
        String[] data = new String[address.getProvinceList().get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getProvinceList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getTownlist(){
        String[] data = new String[address.getTownList(ProvID).get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getTownList(ProvID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getEducationLevel(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getSchoolLevel);
    }

    private ArrayAdapter<String> getDependentRelation(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getDependentRelation);
    }

    class OnAddressSelectionListener implements AdapterView.OnItemClickListener{

        View autoView;

        OnAddressSelectionListener(View view){
            this.autoView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(autoView.getId() == R.id.tie_cap_dpdSchoolProv){
                for(int x = 0; x <address.getProvinceList().get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                        ProvID = address.getProvinceList().get(0).get(x);
                        tieSchlTown.setAdapter(getTownlist());
                        break;
                    }
                }
            } else if(autoView.getId() == R.id.tie_cap_dpdSchoolTown){
                for(int x = 0; x <address.getTownList(ProvID).get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                        TownID = address.getTownList(ProvID).get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnDependencyStatusSelectionListener implements RadioGroup.OnCheckedChangeListener{

        View rbView;

        OnDependencyStatusSelectionListener(View view){
            this.rbView = view;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(rbView.getId() == R.id.rg_cap_dpdStudent){
                if(checkedId == R.id.rb_cap_dpdStudentYes) {
                    IsStudentx = "1";
                    linearStudent.setVisibility(View.VISIBLE);
                } else {
                    IsStudentx = "0";
                    linearStudent.setVisibility(View.GONE);
                }
            }

            if(rbView.getId() == R.id.rg_cap_dpdSchoolType){
                if(checkedId == R.id.rb_cap_dpdSchoolPrivate){
                    IsPrivatex = "1";
                } else {
                    IsPrivatex = "0";
                }
            }
            if(rbView.getId() == R.id.rg_cap_dpdEmployed){
                if(checkedId == R.id.rb_cap_dpdEmployedYes) {
                    IsEmployed = "1";
                    linearEmployd.setVisibility(View.VISIBLE);
                } else {
                    IsEmployed = "0";
                    linearEmployd.setVisibility(View.GONE);
                }
            }

            if (rbView.getId() == R.id.rg_cap_dpdEmployedSector){
                if(checkedId == R.id.rb_cap_dpdEmployedPublic){
                    Employment = "0";
                } else if(checkedId == R.id.rb_cap_dpdEmployedPrivate){
                    Employment = "1";
                } else if(checkedId == R.id.rb_cap_dpdEmployedSelf){
                    Employment = "2";
                }
            }
        }
    }

    class OnDependencySelectionListener implements CheckBox.OnCheckedChangeListener{

        View cbView;

        OnDependencySelectionListener(View view){
            this.cbView = view;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(cbView.getId() == R.id.cb_cap_Dependent){
                if(buttonView.isChecked()) {
                    Dependentx = "1";
                } else {
                    Dependentx = "0";
                }
            } else if(cbView.getId() == R.id.cb_cap_HouseHold){
                if(buttonView.isChecked()){
                    HouseHoldx = "1";
                } else {
                    HouseHoldx = "0";
                }
            } else if(cbView.getId() == R.id.cb_cap_Married){
                if(buttonView.isChecked()){
                    IsMarriedx = "1";
                } else {
                    IsMarriedx = "0";
                }
            } else if(cbView.getId() == R.id.cb_cap_dpdScholar){
                if(buttonView.isChecked()){
                    IsScholarx = "1";
                } else {
                    IsScholarx = "2";
                }
            }
        }
    }

    class OnDependentSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getItemAtPosition(position).equals("Parents")){
                cbIsMarried.setChecked(true);
            } else {
                cbIsMarried.setChecked(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.btn_fragment_dependency_addDependent){
                AddDependencies();
            } else if(id == R.id.btn_fragment_dependency_prevs){
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(6, true);
            } else if(id == R.id.btn_fragment_dependency_next){
                setupDependentInfo();
                poCreditApp.UpdateApplicationInfo(poGoCas, TransNox);
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(8, true);
            }
        }
    }
    /**
     * Child info validators
     * ***********************/

    private boolean isNameValid(){
        if(Objects.requireNonNull(tieFullname.getText()).toString().isEmpty()){
            tilFullname.setError("Please enter full name.");
            return false;
        }
        tilFullname.setErrorEnabled(false);
        return true;
    }

    private boolean isAgeValid(){
        if(Objects.requireNonNull(tieDpdAgexx.getText()).toString().isEmpty()){
            tilDpdAgexx.setError("Please enter full name.");
            return false;
        }
        tilDpdAgexx.setErrorEnabled(false);
        return true;
    }

    private boolean isCompanyValid(){
        if(Objects.requireNonNull(tieCompName.getText()).toString().isEmpty()){
            tilCompName.setError("Please enter full name.");
            return false;
        }
        tilCompName.setErrorEnabled(false);
        return true;
    }
}
