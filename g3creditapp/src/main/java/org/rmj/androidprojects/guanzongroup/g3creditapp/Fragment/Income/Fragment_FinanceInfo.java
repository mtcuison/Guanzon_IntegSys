package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
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
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.CreditSourceObjects;
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
public class Fragment_FinanceInfo extends Fragment {
    private static final String TAG = Fragment_FinanceInfo.class.getSimpleName();

    private View v;
    private AutoSuggestAddress address;
    private String CountryCode = "";
    private String Relation = "";

    private Spinner spnSource;
    private TextInputEditText tieNmeFncr;
    private TextInputEditText tieRngIncm;
    private AutoCompleteTextView tieCountry;
    private TextInputEditText tieMobleNo;
    private TextInputEditText tieFbAccnt;
    private TextInputEditText tieEmailAd;
    private TextInputLayout tilEmailAd;
    private MaterialButton btnNext;
    private MaterialButton btnPrvs;

    public Fragment_FinanceInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_finance_info, container, false);
        address = new AutoSuggestAddress(getActivity());
        setupWidgets();
        setupActions();
        return v;
    }

    private void setupWidgets(){
        spnSource = v.findViewById(R.id.spinner_cap_applFinancerRelation);
        tieNmeFncr = v.findViewById(R.id.tie_cap_applFinancer);
        tieRngIncm = v.findViewById(R.id.tie_cap_applRangeIncm);
        tieCountry = v.findViewById(R.id.tie_cap_applFncrCountry);
        tieMobleNo = v.findViewById(R.id.tie_cap_applMobileNo);
        tieFbAccnt = v.findViewById(R.id.tie_cap_applFbAccnt);
        tieEmailAd = v.findViewById(R.id.tie_cap_applEmailAdd);
        tilEmailAd = v.findViewById(R.id.til_cap_applEmailAdd);
        btnNext = v.findViewById(R.id.btn_fragment_finance_next);
        btnPrvs = v.findViewById(R.id.btn_fragment_finance_prevs);
    }

    private void setupActions(){
        spnSource.setAdapter(getSource());
        spnSource.setOnItemSelectedListener(new OnItemSelectedListener());
        tieCountry.setAdapter(getCountry());
        tieCountry.setOnItemClickListener(new OnCountrySelectedListener());
        tieRngIncm.addTextChangedListener(new TextCurrencyFormater(tieRngIncm));
        btnNext.setOnClickListener(v -> {
            if(isDataValid()){
                setupFinanceInfo();
                Fragment_IncomStatus.getInstance().goNextFragment();
            }
        });

        btnPrvs.setOnClickListener(v -> Fragment_IncomStatus.getInstance().goPreviousFragment());
    }

    private ArrayAdapter<String> getSource(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getFinanceSource);
    }

    private ArrayAdapter<String> getCountry(){
        String[] data = new String[address.getNationalityList().get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getNationalityList().get(2).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private void setupFinanceInfo() {
        String TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        GOCASApplication loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(TransNox);
        loGoCas.MeansInfo().FinancerInfo().setSource(Relation);
        loGoCas.MeansInfo().FinancerInfo().setFinancerName(Objects.requireNonNull(tieNmeFncr.getText()).toString());
        loGoCas.MeansInfo().FinancerInfo().setAmount(new FormatUIText().getParseLong(Objects.requireNonNull(tieRngIncm.getText()).toString()));
        loGoCas.MeansInfo().FinancerInfo().setCountry(CountryCode);
        loGoCas.MeansInfo().FinancerInfo().setMobileNo(Objects.requireNonNull(tieMobleNo.getText()).toString());
        loGoCas.MeansInfo().FinancerInfo().setFBAccount(Objects.requireNonNull(tieFbAccnt.getText()).toString());
        loGoCas.MeansInfo().FinancerInfo().setEmailAddress(Objects.requireNonNull(tieEmailAd.getText()).toString());
        new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, TransNox);
        Log.e(TAG,"Financier ifo result : " + loGoCas.MeansInfo().FinancerInfo().toJSONString());
    }

    class OnCountrySelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int count = address.getNationalityList().get(0).size();
            for(int x = 0; x < count; x++){
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getNationalityList().get(2).get(x))){
                    CountryCode = address.getNationalityList().get(0).get(x);
                    break;
                }
            }
        }
    }

    class OnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0){
                Relation = "";
            } else {
                Relation = String.valueOf(position - 1);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private boolean isDataValid(){
        if(Relation.isEmpty()){
            new CustomToast(getActivity(), "Please select financier relationship.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(Objects.requireNonNull(tieNmeFncr.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Please enter financier name.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(Objects.requireNonNull(tieRngIncm.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Please provide at least partial range of income.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(CountryCode.isEmpty()){
            new CustomToast(getActivity(), "Country info is empty or invalid.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(Objects.requireNonNull(tieMobleNo.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Please enter financier mobile number.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isEmailValid()){
            new CustomToast(getActivity(), "Invalid email input.", CustomToast.TYPE.WARNING).show();
            return false;
        }
        return true;
    }

    private boolean isEmailValid(){
        if(!Objects.requireNonNull(tieEmailAd.getText()).toString().trim().isEmpty()){
            String email = tieEmailAd.getText().toString();
            if((!Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                tilEmailAd.setError("Please enter valid email.");
                requestFocus(tieEmailAd);
                return false;
            }
        }
        tilEmailAd.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
