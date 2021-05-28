package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse;

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
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_SpouseBusiness extends Fragment {
    private static final String TAG = Fragment_SpouseBusiness.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static Fragment_SpouseBusiness instance;

    private View v;
    private AutoSuggestAddress address;
    private ConstantsAdapter adapter;
    private String ProvID = "";
    private String TownID = "";
    private String BussType = "";
    private String BussSize = "";

    private TextInputLayout tilNtrBuss;
    private TextInputLayout tilBusProv;
    private TextInputLayout tilBusTown;
    private Spinner spnNtrBuss;
    private Spinner spnBusType;
    private Spinner spnBusSize;
    private Spinner spnBusLnth;
    private TextInputEditText tieNtrBuss;
    private TextInputEditText tieNmeBuss;
    private TextInputEditText tieBusAddx;
    private AutoCompleteTextView tieBusProv;
    private AutoCompleteTextView tieBusTown;
    private TextInputEditText tieBusLnth;
    private TextInputEditText tieBusIncm;
    private TextInputEditText tieBusExps;
    private MaterialButton btnNext;
    private MaterialButton btnPrvs;


    public Fragment_SpouseBusiness() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_spouse_business, container, false);
        instance = this;
        address = new AutoSuggestAddress(getActivity());
        adapter = new ConstantsAdapter(getActivity());
        setupWidgets();
        setupActions();
        return v;
    }

    private void setupWidgets(){
        spnNtrBuss = v.findViewById(R.id.spinner_cap_SpsBussNature);
        tilNtrBuss = v.findViewById(R.id.til_cap_SpsNtrBusiness);
        tieNtrBuss = v.findViewById(R.id.tie_cap_SpsNtrBusiness);
        tieNmeBuss = v.findViewById(R.id.tie_cap_SpsNameOFBuss);
        tieBusAddx = v.findViewById(R.id.tie_cap_SpsAddOFBuss);
        tieBusProv = v.findViewById(R.id.tie_cap_SpsBussProv);
        tieBusTown = v.findViewById(R.id.tie_cap_SpsBussTown);
        spnBusType = v.findViewById(R.id.spinner_cap_SpsBussType);
        spnBusSize = v.findViewById(R.id.spinner_cap_SpsBussSize);
        tieBusLnth = v.findViewById(R.id.tie_cap_SpsBussLenght);
        tieBusIncm = v.findViewById(R.id.tie_cap_SpsBussIncome);
        tieBusExps = v.findViewById(R.id.tie_cap_SpsBussExpense);
        spnBusLnth = v.findViewById(R.id.spinner_cap_businessLength);
        tilBusProv = v.findViewById(R.id.til_cap_SpsBussProv);
        tilBusTown = v.findViewById(R.id.til_cap_SpsBussTown);
        btnNext = v.findViewById(R.id.btn_fragment_business_next);
        btnPrvs = v.findViewById(R.id.btn_fragment_business_prevs);
    }

    private void setupActions(){
        spnNtrBuss.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Nature_Of_Business));
        tieBusProv.setAdapter(getBussinessProvince());
        tieBusProv.setOnItemClickListener(new OnProvinceSelectedListener());
        tieBusTown.setOnItemClickListener(new OnTownSelectedListener());
        tieBusIncm.addTextChangedListener(new TextCurrencyFormater(tieBusIncm));
        spnBusType.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Business_Type));
        spnBusSize.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.Business_Size));
        spnNtrBuss.setOnItemSelectedListener(new OnBussinessNatureSelectionListener(spnNtrBuss));
        spnBusSize.setOnItemSelectedListener(new OnBussinessNatureSelectionListener(spnBusSize));
        spnBusType.setOnItemSelectedListener(new OnBussinessNatureSelectionListener(spnBusType));
        tieBusIncm.addTextChangedListener(new TextCurrencyFormater(tieBusIncm));
        tieBusExps.addTextChangedListener(new TextCurrencyFormater(tieBusExps));
        spnBusLnth.setAdapter(adapter.getAdapter(ConstantsAdapter.AdapterSelections.TimeLength));
        tieBusProv.addTextChangedListener(new OnAddressSetListener(tieBusProv));
        tieBusTown.addTextChangedListener(new OnAddressSetListener(tieBusTown));
        btnNext.setOnClickListener(v -> {
            if(isDataValid()){
                setupBussinessInfo();
                new Fragment_SpouseMeans().getInstance().goNextFragment();
            }
        });

        btnPrvs.setOnClickListener(v -> new Fragment_SpouseMeans().getInstance().goPreviousFragment());
    }

    private ArrayAdapter<String> getBussinessProvince(){
        String[] data = new String[address.getProvinceList().get(0).size()];
        for(int x = 0;  x < data.length; x++){
            data[x] = address.getProvinceList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getBussinessTown(){
        String[] data = new String[address.getTownList(ProvID).get(0).size()];
        for(int x = 0;  x < data.length; x++){
            data[x] = address.getTownList(ProvID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    class OnProvinceSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(int x = 0; x < address.getProvinceList().get(0).size(); x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                    ProvID = address.getProvinceList().get(0).get(x);
                    break;
                }
            }
            tieBusTown.setAdapter(getBussinessTown());
        }
    }

    class OnTownSelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(int x = 0; x < address.getTownList(ProvID).get(0).size(); x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                    TownID = address.getTownList(ProvID).get(0).get(x);
                    break;
                }
            }
        }
    }

    class OnBussinessNatureSelectionListener implements AdapterView.OnItemSelectedListener{

        View spinner;

        OnBussinessNatureSelectionListener(View view){
            this.spinner = view;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(spinner.getId() == R.id.spinner_cap_SpsBussNature){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Others")){
                    tilNtrBuss.setVisibility(View.VISIBLE);
                } else {
                    tilNtrBuss.setVisibility(View.GONE);
                }
            } else if(spinner.getId() == R.id.spinner_cap_SpsBussType){
                if(position == 0){
                    BussType = "";
                } else {
                    BussType = String.valueOf(position - 1);
                }
            } else if(spinner.getId() == R.id.spinner_cap_SpsBussSize){
                if(position == 0){
                    BussSize = "";
                } else {
                    BussSize = String.valueOf(position - 1);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public static Fragment_SpouseBusiness getInstance(){
        return instance;
    }

    private void setupBussinessInfo(){
        String TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        GOCASApplication loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(TransNox);
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setNatureOfBusiness(getBusinessNature());
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setNameOfBusiness(Objects.requireNonNull(tieNmeBuss.getText()).toString());
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setBusinessAddress(Objects.requireNonNull(tieBusAddx.getText()).toString());
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setCompanyTown(TownID);
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setBusinessType(BussType);
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setOwnershipSize(BussType);
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setBusinessLength(new FormatUIText().getParseBusinessLength(Objects.requireNonNull(tieBusLnth.getText()).toString(), spnBusLnth.getSelectedItemPosition()));
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setIncome(new FormatUIText().getParseLong(Objects.requireNonNull(tieBusIncm.getText()).toString()));
        loGoCas.SpouseMeansInfo().SelfEmployedInfo().setMonthlyExpense(new FormatUIText().getParseLong(Objects.requireNonNull(tieBusExps.getText()).toString()));
        new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, TransNox);
        Log.e(TAG, "Business information result : " + loGoCas.SpouseMeansInfo().SelfEmployedInfo().toJSONString());
    }

    private String getBusinessNature(){
        if(spnNtrBuss.getSelectedItem().toString().equalsIgnoreCase("Others")){
            return Objects.requireNonNull(tieNtrBuss.getText()).toString();
        }
        return spnNtrBuss.getSelectedItem().toString();
    }

    class OnAddressSetListener implements TextWatcher {

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

            if(id == R.id.tie_cap_SpsBussProv){
                tieBusProv.removeTextChangedListener(this);
                isValidProvince();
                tieBusProv.addTextChangedListener(this);
            } else if(id == R.id.tie_cap_SpsBussTown){
                tieBusTown.removeTextChangedListener(this);
                isValidTown();
                tieBusTown.addTextChangedListener(this);
            }
        }
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isDataValid(){
        if(Objects.requireNonNull(tieNmeBuss.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Please provide business name.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isBussinessAddValid()){
            new CustomToast(getActivity(), "Business address is empty or invalid.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(BussType.isEmpty()){
            new CustomToast(getActivity(), "Please select type of business.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(BussSize.isEmpty()){
            new CustomToast(getActivity(), "Please select size of business.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(Objects.requireNonNull(tieBusLnth.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Please enter length of business.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(Objects.requireNonNull(tieBusIncm.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Please provide atleast partial or estimated monthly income.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(Objects.requireNonNull(tieBusExps.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Please provide atleast partial or estimated monthly expense.", CustomToast.TYPE.WARNING).show();
            return false;
        }
        return true;
    }

    private boolean isValidProvince(){
        if(tieBusProv.getText().toString().isEmpty()){
            tilBusProv.setError("Please provide province.");
            requestFocus(tieBusProv);
            return false;
        }
        tilBusProv.setErrorEnabled(false);
        return true;
    }

    private boolean isValidTown(){
        if(isValidProvince()){
            if(tieBusTown.getText().toString().isEmpty()){
                tieBusTown.setError("Please provide town.");
                requestFocus(tieBusProv);
                return false;
            }
            tilBusTown.setErrorEnabled(false);
            return true;
        }

        tieBusTown.setText("");
        requestFocus(tieBusProv);
        return false;
    }

    private boolean isBussinessAddValid(){
        if(ProvID.isEmpty()){
            tilBusProv.setError("Invalid province entered.");
            requestFocus(tieBusProv);
            return false;
        } else if(TownID.isEmpty()){
            tilBusTown.setError("Invalid town entered.");
            requestFocus(tieBusTown);
            return false;
        }
        return true;
    }
}
