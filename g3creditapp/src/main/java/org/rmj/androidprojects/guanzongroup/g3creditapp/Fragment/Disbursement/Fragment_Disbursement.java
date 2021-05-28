package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Disbursement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.CreditSourceObjects;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.FormatUIText;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.TextCurrencyFormater;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Disbursement extends Fragment {
    private static final String TAG = Fragment_Disbursement.class.getSimpleName();
    private GOCASApplication poGoCas;
    private String TransNox;
    private View v;
    private FormatUIText formater;
    private TextInputLayout tilElctx;
    private TextInputLayout tilWater;
    private TextInputLayout tilFoodx;
    private TextInputLayout tilLoans;
    private TextInputLayout tilBankN;
    private TextInputLayout tilCCBnk;
    private TextInputLayout tilLimit;
    private TextInputLayout tilYearS;

    private Spinner spnTypex;
    private TextInputEditText tieElctx;
    private TextInputEditText tieWater;
    private TextInputEditText tieFoodx;
    private TextInputEditText tieLoans;
    private TextInputEditText tieBankN;
    private TextInputEditText tieCCBnk;
    private TextInputEditText tieLimit;
    private TextInputEditText tieYearS;

    private MaterialButton btnPrev;
    private MaterialButton btnNext;

    public Fragment_Disbursement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_disbursement, container, false);
        TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        formater = new FormatUIText();
        setupWidgets();
        setupActionListeners();
        return v;
    }

    private void setupWidgets(){
        tilElctx = v.findViewById(R.id.til_cap_dbmElectricity);
        tilWater = v.findViewById(R.id.til_cap_dbmWater);
        tilFoodx = v.findViewById(R.id.til_cap_dbmFood);
        tilLoans = v.findViewById(R.id.til_cap_dbmLoans);
        tilBankN = v.findViewById(R.id.til_cap_dbmBankName);
        tilCCBnk = v.findViewById(R.id.til_cap_dbmBankNameCC);
        tilLimit = v.findViewById(R.id.til_cap_dbmCreditLimit);
        tilYearS = v.findViewById(R.id.til_cap_dbmYearStarted);

        tieElctx = v.findViewById(R.id.tie_cap_dbmElectricity);
        tieWater = v.findViewById(R.id.tie_cap_dbmWater);
        tieFoodx = v.findViewById(R.id.tie_cap_dbmFood);
        tieLoans = v.findViewById(R.id.tie_cap_dbmLoans);
        tieBankN = v.findViewById(R.id.tie_cap_dbmBankName);
        spnTypex = v.findViewById(R.id.spinner_cap_dbmAccountType);
        tieCCBnk = v.findViewById(R.id.tie_cap_dbmBankNameCC);
        tieLimit = v.findViewById(R.id.tie_cap_dbmCreditLimit);
        tieYearS = v.findViewById(R.id.tie_cap_dbmYearStarted);

        btnPrev = v.findViewById(R.id.btn_fragment_dbm_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_dbm_next);
    }

    private void setupActionListeners(){
        tieElctx.addTextChangedListener(new TextCurrencyFormater(tieElctx));
        tieWater.addTextChangedListener(new TextCurrencyFormater(tieWater));
        tieFoodx.addTextChangedListener(new TextCurrencyFormater(tieFoodx));
        tieLoans.addTextChangedListener(new TextCurrencyFormater(tieLoans));
        tieLimit.addTextChangedListener(new TextCurrencyFormater(tieLimit));

        btnPrev.setOnClickListener(new OnButtonClickListener());
        btnNext.setOnClickListener(new OnButtonClickListener());

        spnTypex.setAdapter(getAccountType());
    }

    private void setupDisbursementInfo(){
        GOCASApplication loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(TransNox);
        loGoCas.DisbursementInfo().Expenses().setElectricBill(formater.getParseDouble(Objects.requireNonNull(tieElctx.getText()).toString() ));
        loGoCas.DisbursementInfo().Expenses().setWaterBill(formater.getParseDouble(Objects.requireNonNull(tieWater.getText()).toString()));
        loGoCas.DisbursementInfo().Expenses().setFoodAllowance(formater.getParseDouble(Objects.requireNonNull(tieFoodx.getText()).toString()));
        loGoCas.DisbursementInfo().Expenses().setLoanAmount(formater.getParseDouble(Objects.requireNonNull(tieLoans.getText()).toString()));
        loGoCas.DisbursementInfo().BankAccount().setBankName(Objects.requireNonNull(tieBankN.getText()).toString());
        loGoCas.DisbursementInfo().BankAccount().setAccountType(String.valueOf(spnTypex.getSelectedItemPosition()));
        loGoCas.DisbursementInfo().CreditCard().setBankName(Objects.requireNonNull(tieCCBnk.getText()).toString());
        loGoCas.DisbursementInfo().CreditCard().setCreditLimit(formater.getParseDouble(Objects.requireNonNull(tieLimit.getText()).toString()));
        loGoCas.DisbursementInfo().CreditCard().setMemberSince(formater.getParseInt(Objects.requireNonNull(tieYearS.getText()).toString()));
        new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, TransNox);
        Log.e(TAG, "Disbursement Expenses information result : " + loGoCas.DisbursementInfo().toJSONString());
        Log.e(TAG, "Disbursement Bank Account information result : " + loGoCas.DisbursementInfo().BankAccount().toJSONString());
        Log.e(TAG, "Disbursement Credit Card information result : " + loGoCas.DisbursementInfo().CreditCard().toJSONString());
    }

    private ArrayAdapter<String> getAccountType(){
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, CreditSourceObjects.getAccountType);
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.btn_fragment_dbm_prevs){
                if(hasPartner()) {
                    ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(5, true);
                } else {
                    ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(2, true);
                }
            } else {
                setupDisbursementInfo();
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(7, true);
            }
        }

        boolean hasPartner(){
            poGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(TransNox);
            return poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("1") ||
                    poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("5");
        }
    }
}
