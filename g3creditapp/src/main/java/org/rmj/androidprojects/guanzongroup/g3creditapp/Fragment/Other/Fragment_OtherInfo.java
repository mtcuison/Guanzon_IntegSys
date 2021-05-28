package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Other;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.ConstantsAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.ReferencesAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.PersonalReferences;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.ComakerSetting;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Disbursement.Fragment_Properties;
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
public class Fragment_OtherInfo extends Fragment {
    private static final String TAG = Fragment_OtherInfo.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static Fragment_OtherInfo instance;
    private AutoSuggestAddress address;

    private CreditApplication poCreditApp;
    private String TransNox;
    private GOCASApplication poGoCas;

    private View v;
    private String ProvID = "";
    private String TownID = "";
    private List<PersonalReferences> personalReferencesList;
    private ReferencesAdapter adapter;

    private Spinner spnUnitUser;
    private Spinner spnUnitPrps;
    private Spinner spnUnitPayr;
    private Spinner spnSourcexx;
    private Spinner spnUserBuyr;
    private Spinner spnPayrBuyr;
    private TextInputLayout tilSpcfSrc;
    private TextInputLayout tilRefname;
    private TextInputLayout tilRefCntc;
    private TextInputLayout tilAddProv;
    private TextInputLayout tilAddTown;
    private TextInputEditText tieSpcfSrc;
    private TextInputEditText tieRefAdd1;
    private TextInputEditText tieRefName;
    private TextInputEditText tieRefCntc;
    private AutoCompleteTextView tieAddProv;
    private AutoCompleteTextView tieAddTown;
    private RecyclerView recyclerView;
    private MaterialButton btnAddReferencex;
    private MaterialButton btnPrevs;
    private MaterialButton btnNext;

    public Fragment_OtherInfo() {
        // Required empty public constructor
    }

    public static Fragment_OtherInfo getInstance(){
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_other_info, container, false);
        instance = this;
        address = new AutoSuggestAddress(getActivity());
        personalReferencesList = new ArrayList<>();
        poCreditApp = new CreditApplication(getActivity());
        TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        setupWidgets();
        setupActions();
        setupReferenceList();
        return v;
    }

    private void setupWidgets(){
        spnUnitUser = v.findViewById(R.id.spinner_cap_unitUser);
        spnUnitPrps = v.findViewById(R.id.spinner_cap_purposeOfBuying);
        spnUnitPayr = v.findViewById(R.id.spinner_cap_monthlyPayer);
        spnUserBuyr = v.findViewById(R.id.spinner_cap_sUsr2buyr);
        spnPayrBuyr = v.findViewById(R.id.spinner_cap_sPyr2Buyr);
        spnSourcexx = v.findViewById(R.id.spinner_cap_source);
        tilSpcfSrc = v.findViewById(R.id.til_cap_CompanyInfoSource);
        tilRefname = v.findViewById(R.id.til_cap_referenceName);
        tilRefCntc = v.findViewById(R.id.til_cap_referenceContact);
        tilAddProv = v.findViewById(R.id.til_cap_referenceAddProv);
        tilAddTown = v.findViewById(R.id.til_cap_referenceAddTown);
        tieSpcfSrc = v.findViewById(R.id.tie_cap_CompanyInfoSource);
        tieRefName = v.findViewById(R.id.tie_cap_referenceName);
        tieRefCntc = v.findViewById(R.id.tie_cap_refereceContact);
        tieRefAdd1 = v.findViewById(R.id.tie_cap_refereceAddress);
        tieAddProv = v.findViewById(R.id.tie_cap_referenceAddProv);
        tieAddTown = v.findViewById(R.id.tie_cap_referenceAddTown);
        recyclerView = v.findViewById(R.id.recyclerview_references);
        btnAddReferencex = v.findViewById(R.id.btn_fragment_others_addReference);
        btnPrevs = v.findViewById(R.id.btn_fragment_others_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_others_next);
    }

    private void setupActions(){
        GOCASApplication loGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        ConstantsAdapter adapter = new ConstantsAdapter(loGoCas, getActivity());
        spnUnitUser.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Unit_User));
        spnUnitUser.setOnItemSelectedListener(new OnSpinnerSelectionListener(spnUnitUser));
        spnUserBuyr.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Other_Unit_User));
        spnUnitPrps.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Unit_Purpose));
        spnUnitPayr.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Unit_User));
        spnUnitPayr.setOnItemSelectedListener(new OnSpinnerSelectionListener(spnUnitPayr));
        spnPayrBuyr.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Unit_Payer));
        spnSourcexx.setOnItemSelectedListener(new OnSpinnerSelectionListener(spnSourcexx));
        spnSourcexx.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Company_Info_Source));
        tieAddProv.setAdapter(getProvinceList());
        tieAddProv.setOnItemClickListener(new OnAddressSelectedListener(tieAddProv));
        tieAddTown.setOnItemClickListener(new OnAddressSelectedListener(tieAddTown));

        btnAddReferencex.setOnClickListener(new OnButtonClickListener());
        btnPrevs.setOnClickListener(new OnButtonClickListener());
        btnNext.setOnClickListener(new OnButtonClickListener());
    }

    private void setupOtherInfo(){
        poGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        poGoCas.OtherInfo().setUnitUser(getUniUser());
        poGoCas.OtherInfo().setPurpose(String.valueOf(spnUnitPrps.getSelectedItemPosition()));
        if(spnUnitPayr.getSelectedItemPosition() != 1) {
            poGoCas.OtherInfo().setUnitPayor(String.valueOf(spnUnitPayr.getSelectedItemPosition()));
        } else {
            poGoCas.OtherInfo().setPayorRelation(String.valueOf(spnPayrBuyr.getSelectedItemPosition()));
        }
        poGoCas.OtherInfo().setSourceInfo(getSourceInfo());
        for(int x = 0; x < personalReferencesList.size(); x++){
            poGoCas.OtherInfo().addReference();
            poGoCas.OtherInfo().setPRName(x, personalReferencesList.get(x).getFullname());
            poGoCas.OtherInfo().setPRTownCity(x, personalReferencesList.get(x).getTownCity());
            poGoCas.OtherInfo().setPRMobileNo(x, personalReferencesList.get(x).getContactN());
            poGoCas.OtherInfo().setPRAddress(x, personalReferencesList.get(x).getAddress1());
        }
        poCreditApp.UpdateApplicationInfo(poGoCas, TransNox);
        Log.e(TAG, "Other information result : " + poGoCas.OtherInfo().toJSONString());
    }

    private String getUniUser(){
        if(spnUnitUser.getSelectedItemPosition()!=1){
            return String.valueOf(spnUnitUser.getSelectedItemPosition());
        }
        return String.valueOf(spnUserBuyr.getSelectedItemPosition());
    }

    private void addPersonalReference(){
        if(isValidReferenceInfo()) {
            PersonalReferences references = new PersonalReferences(
                    Objects.requireNonNull(tieRefName.getText()).toString(),
                    Objects.requireNonNull(tieRefAdd1.getText()).toString(),
                    TownID,
                    Objects.requireNonNull(tieRefCntc.getText()).toString());
            personalReferencesList.add(references);
            adapter.notifyDataSetChanged();
            Log.e(TAG, "Reference has been added");
            clearReferenceField();
        } else {
            Toast.makeText(getActivity(), "Please make sure to fill all important fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSourceInfo(){
        if(spnSourcexx.getSelectedItem().toString().equalsIgnoreCase("Other")){
            return Objects.requireNonNull(tieSpcfSrc.getText()).toString();
        }
        return spnSourcexx.getSelectedItem().toString();
    }

    private void setupReferenceList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adapter = new ReferencesAdapter(personalReferencesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnReferenceDeleteListener(position -> {
            personalReferencesList.remove(position);
            setupReferenceList();
            adapter.notifyDataSetChanged();
        });
    }

    private void clearReferenceField(){
        tieRefName.setText("");
        tieRefAdd1.setText("");
        tieRefCntc.setText("");
        tieAddProv.setText("");
        tieAddTown.setText("");
        ProvID = "";
        TownID = "";
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

    class OnSpinnerSelectionListener implements AdapterView.OnItemSelectedListener{

        View spnView;

        OnSpinnerSelectionListener(View view){
            this.spnView = view;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(spnView.getId() == R.id.spinner_cap_unitUser){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Others")){
                    spnUserBuyr.setVisibility(View.VISIBLE);
                } else {
                    spnUserBuyr.setVisibility(View.GONE);
                }
            } else if(spnView.getId() == R.id.spinner_cap_monthlyPayer){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Others")){
                    spnPayrBuyr.setVisibility(View.VISIBLE);
                } else {
                    spnPayrBuyr.setVisibility(View.GONE);
                }
            } else if(spnView.getId() == R.id.spinner_cap_source){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Other")){
                    tilSpcfSrc.setVisibility(View.VISIBLE);
                } else {
                    tilSpcfSrc.setVisibility(View.GONE);
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
            if(addView.getId() == R.id.tie_cap_referenceAddProv){
                for(int x = 0; x < address.getProvinceList().get(1).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                        ProvID = address.getProvinceList().get(0).get(x);
                        tieAddTown.setAdapter(getTownList());
                        break;
                    }
                }
            } else if(addView.getId() == R.id.tie_cap_referenceAddTown){
                for(int x = 0; x < address.getTownList(ProvID).get(1).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                        TownID = address.getTownList(ProvID).get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_fragment_others_prevs){
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(7, true);
            } else if(v.getId() == R.id.btn_fragment_others_next){
                if(isReferenceValid()) {
                    setupOtherInfo();
                    String lsBirthdate = poGoCas.ApplicantInfo().getBirthdate();
                    if(new ComakerSetting().isComakeNeeded(lsBirthdate)) {
                       new Fragment_Properties().getInstance().setButtonText("Save");
                    }
                    ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(9, true);
                } else {
                    new CustomToast(getActivity(), "Please provide atleast 3 personal reference.", CustomToast.TYPE.WARNING).show();
                }
            } else if(v.getId() == R.id.btn_fragment_others_addReference){
                addPersonalReference();
            }
        }
    }

    /*
     * Validations of Reference Info
     * ****************************************/

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isReferenceValid(){
        return personalReferencesList.size() >= 3;
    }

    /*If the value of personal reference list is == 0
     * set the GoCas object to local private variable
     * setting the GoCas Object might cause null pointer
     * exception or Empty String input.*/
    private boolean isValidReferenceInfo(){
        if(personalReferencesList.size() == 0){
            poGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        }
        return isNameValid() && isContactValid() && isProvinceValid() && isTownValid();
    }

    private boolean isNameValid(){
        if(Objects.requireNonNull(tieRefName.getText()).toString().isEmpty()){
            tilRefname.setError("Please enter reference name.");
            requestFocus(tieRefName);
            return false;
        }
        tilRefname.setErrorEnabled(false);
        return true;
    }

    private boolean isContactValid(){
        if(Objects.requireNonNull(tieRefCntc.getText()).toString().isEmpty()){
            tilRefCntc.setError("Please enter contact no.");
            requestFocus(tieRefCntc);
            return false;
        } else if (tieRefCntc.getText().toString().substring(0,1).equalsIgnoreCase("09")){
            tilRefCntc.setError("Please provide valid contact no.");
            requestFocus(tieRefCntc);
            return false;
        } else if(tieRefCntc.getText().length()!= 11){
            tilRefCntc.setError("please provide valid contact no.");
            requestFocus(tieRefCntc);
            return false;
        }
        tilRefCntc.setErrorEnabled(false);
        return true;
    }

    private boolean isProvinceValid(){
        if(tieAddProv.getText().toString().isEmpty() || ProvID.isEmpty()){
            tilAddProv.setError("Please enter province.");
            requestFocus(tieAddProv);
            return false;
        }
        tilAddProv.setErrorEnabled(false);
        return true;
    }

    private boolean isTownValid(){
        if(isProvinceValid()) {
            if (tieAddTown.getText().toString().isEmpty() || TownID.isEmpty()) {
                tilAddTown.setError("Please enter province.");
                requestFocus(tieAddTown);
                return false;
            }
            tilAddTown.setErrorEnabled(false);
            return true;
        } else {
            requestFocus(tieAddProv);
            return false;
        }
    }
}
