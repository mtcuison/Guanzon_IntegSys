package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.FormatUIText;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.TextCurrencyFormater;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_IncomStatus;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_PensionInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_SpousePension extends Fragment {
    private static final String TAG = Fragment_SpousePension.class.getSimpleName();

    private String EmpSector = "";
    private TextInputEditText tiePnsnRange;
    private TextInputEditText tieRtrmntYrx;
    private TextInputEditText tieOthrNtrIn;
    private TextInputEditText tieRngeIncmx;

    private LinearLayout linearPensionInfo;
    private MaterialButton btnNext;
    private MaterialButton btnPrvs;

    public Fragment_SpousePension() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_spouse_pension, container, false);

        RadioGroup rbSector = v.findViewById(R.id.rg_cap_spsPensionSector);
        rbSector.setOnCheckedChangeListener(new OnSectorSelectionListener());
        tiePnsnRange = v.findViewById(R.id.tie_cap_spsPensionRange);
        tieRtrmntYrx = v.findViewById(R.id.tie_cap_spsRetirementYr);
        tieOthrNtrIn = v.findViewById(R.id.tie_cap_spsNtrIncm);
        tieRngeIncmx = v.findViewById(R.id.tie_cap_spsOthrRangeIncm);
        linearPensionInfo = v.findViewById(R.id.linear_pensionInfo);
        btnNext = v.findViewById(R.id.btn_fragment_spsPension_next);
        btnPrvs = v.findViewById(R.id.btn_fragment_spsPension_prevs);
        setupActions();
        return v;
    }

    private void setupActions(){
        tiePnsnRange.addTextChangedListener(new TextCurrencyFormater(tiePnsnRange));
        tieRngeIncmx.addTextChangedListener(new TextCurrencyFormater(tieRngeIncmx));
        btnNext.setOnClickListener(v -> {
            if(isDataValid()) {
                setupPensionInfo();
                new Fragment_SpouseMeans().getInstance().goNextFragment();
            }
        });

        btnPrvs.setOnClickListener(v -> new Fragment_SpouseMeans().getInstance().goPreviousFragment());
    }

    private void setupPensionInfo(){
        String TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        GOCASApplication loGoCas = new CreditApplication(getActivity()).getActiveGoCasInfo(TransNox);
        loGoCas.SpouseMeansInfo().PensionerInfo().setYearRetired(new FormatUIText().getParseInt(Objects.requireNonNull(tieRtrmntYrx.getText()).toString()));
        loGoCas.SpouseMeansInfo().PensionerInfo().setAmount(new FormatUIText().getParseInt(Objects.requireNonNull(tiePnsnRange.getText()).toString()));
        loGoCas.SpouseMeansInfo().PensionerInfo().setSource(EmpSector);
        loGoCas.SpouseMeansInfo().setOtherIncomeNature(Objects.requireNonNull(tieOthrNtrIn.getText()).toString());
        loGoCas.SpouseMeansInfo().setOtherIncomeAmount(new FormatUIText().getParseLong(Objects.requireNonNull(tieRngeIncmx.getText()).toString()));
        new CreditApplication(getActivity()).UpdateApplicationInfo(loGoCas, TransNox);
    }

    class OnSectorSelectionListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.rb_cap_spsPensionGovn){
                EmpSector = "0";
            } else if(checkedId == R.id.rb_cap_spsPensionPriv){
                EmpSector = "1";
            }
            linearPensionInfo.setVisibility(View.VISIBLE);
        }
    }

    private boolean isDataValid(){
        if(EmpSector.isEmpty()){
            new CustomToast(getActivity(), "Sector is empty or invalid.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(Objects.requireNonNull(tiePnsnRange.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Please provide at least partial range of income.", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(Objects.requireNonNull(tieRtrmntYrx.getText()).toString().isEmpty()){
            new CustomToast(getActivity(), "Retirement provide retirement year.", CustomToast.TYPE.WARNING).show();
            return false;
        }
        return true;
    }
}
