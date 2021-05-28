package org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.button.MaterialButton;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.Income_Fragments;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_BussinessInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_FinanceInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_IncomStatus;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_JobInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Income.Fragment_PensionInfo;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse.Fragment_SpouseBusiness;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse.Fragment_SpouseEmployment;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse.Fragment_SpouseMeans;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse.Fragment_SpousePension;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;

public class Dialog_IncomeSource {
    private static final String TAG = Dialog_IncomeSource.class.getSimpleName();

    private Context mContext;
    private AlertDialog poDialog;

    private String TransNox;
    private CreditApplication poCreditApp;
    private GOCASApplication poGoCas;
    private TYPE type;

    private Income_Fragments loIncome;

    public Dialog_IncomeSource(Context context, String TransNox, TYPE type){
        this.mContext = context;
        this.TransNox = TransNox;
        this.type = type;
        this.poCreditApp = new CreditApplication(mContext);
        this.poGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        this.loIncome = new Income_Fragments();
    }

    public void showDialog(){
        createDialog();
        poDialog.show();
    }

    @SuppressLint({"ResourceAsColor", "InflateParams"})
    private void createDialog(){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_income_sources, null, false);
        loBuilder.setView(view)
                .setCancelable(false);

        CheckBox cbEmployed = view.findViewById(R.id.cb_cap_employed);
        CheckBox cbSelfEmpx = view.findViewById(R.id.cb_cap_self_employed);
        CheckBox cbFinancex = view.findViewById(R.id.cb_cap_finance);
        CheckBox cbPensionx = view.findViewById(R.id.cb_cap_pension);
        MaterialButton btnSkip = view.findViewById(R.id.btn_dialog_incomeSourceSkip);
        MaterialButton btnOkay = view.findViewById(R.id.btn_dialog_incomeSourceOkay);

        if(type == TYPE.SPOUSE){
            cbFinancex.setVisibility(View.GONE);
            btnSkip.setVisibility(View.VISIBLE);
        } else {
            btnSkip.setVisibility(View.GONE);
        }

        cbEmployed.setOnCheckedChangeListener(new OnIncomeSourceSelectedListener());
        cbSelfEmpx.setOnCheckedChangeListener(new OnIncomeSourceSelectedListener());
        cbFinancex.setOnCheckedChangeListener(new OnIncomeSourceSelectedListener());
        cbPensionx.setOnCheckedChangeListener(new OnIncomeSourceSelectedListener());

        btnSkip.setOnClickListener(new OnDialogButtonClickListener());
        btnOkay.setOnClickListener(new OnDialogButtonClickListener());

        poDialog = loBuilder.create();
        Objects.requireNonNull(poDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
    }

    class OnIncomeSourceSelectedListener implements CheckBox.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            if(type == TYPE.APPLICANT) {
                if (id == R.id.cb_cap_employed) {
                    if (buttonView.isChecked()) {
                        loIncome.setEmployed(new Fragment_JobInfo());
                        setupPrimaryIncome("0");
                    } else {
                        loIncome.setEmployed(null);
                    }
                } else if (id == R.id.cb_cap_self_employed) {
                    if (buttonView.isChecked()) {
                        loIncome.setSeld_Employed(new Fragment_BussinessInfo());
                        setupPrimaryIncome("1");
                    } else {
                        loIncome.setSeld_Employed(null);
                    }
                } else if (id == R.id.cb_cap_finance) {
                    if (buttonView.isChecked()) {
                        loIncome.setFinance(new Fragment_FinanceInfo());
                        setupPrimaryIncome("2");
                    } else {
                        loIncome.setFinance(null);
                    }
                } else if (id == R.id.cb_cap_pension) {
                    if (buttonView.isChecked()) {
                        loIncome.setPension(new Fragment_PensionInfo());
                        setupPrimaryIncome("3");
                    } else {
                        loIncome.setPension(null);
                    }
                }
            } else {
                if (id == R.id.cb_cap_employed) {
                    if (buttonView.isChecked()) {
                        loIncome.setSpouseEmployment(new Fragment_SpouseEmployment());
                        setupPrimaryIncome("0");
                    } else {
                        loIncome.setSpouseEmployment(null);
                    }
                }else if(id == R.id.cb_cap_self_employed){
                    if (buttonView.isChecked()) {
                        loIncome.setSpouseBusiness(new Fragment_SpouseBusiness());
                        setupPrimaryIncome("1");
                    } else {
                        loIncome.setSpouseBusiness(null);
                    }
                } else if (id == R.id.cb_cap_pension) {
                    if (buttonView.isChecked()) {
                        loIncome.setSpousePension(new Fragment_SpousePension());
                        setupPrimaryIncome("3");
                    } else {
                        loIncome.setSpousePension(null);
                    }
                }
            }
        }
    }

    class OnDialogButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_dialog_incomeSourceOkay) {
                if (type == TYPE.APPLICANT) {
                    if (loIncome.getIncomeFragments().size() > 0) {
                        poCreditApp.UpdateApplicationInfo(poGoCas, TransNox);
                        new Activity_CreditApplication().getInstance().setCurrentItem(2, true);
                        new Fragment_IncomStatus().getInstance().setFragments(loIncome.getIncomeFragments());
                        Log.e(TAG, "Income info has been selected. Proceeding to Income Form.");
                        poDialog.dismiss();
                    } else {
                        new CustomToast(mContext, "Please select at least one source of income", CustomToast.TYPE.INFORMATION).show();
                    }
                } else {
                    if (loIncome.getIncomeFragments().size() > 0) {
                        poDialog.dismiss();
                        poCreditApp.UpdateApplicationInfo(poGoCas, TransNox);
                        new Activity_CreditApplication().getInstance().setCurrentItem(5, true);
                        new Fragment_SpouseMeans().getInstance().setFragments(loIncome.getIncomeFragments());
                        Log.e(TAG, "Income info has been selected. Proceeding to Income Form.");
                        poDialog.dismiss();
                    } else {
                        new CustomToast(mContext, "Please select at least one source of income or press 'Tap here to skip' if means info is not applicable", CustomToast.TYPE.INFORMATION).show();
                    }
                }
            } else {
                new Activity_CreditApplication().getInstance().setCurrentItem(6, true);
                poDialog.dismiss();
            }
        }
    }

    private void setupPrimaryIncome(String value){
        if(type == TYPE.APPLICANT) {
            if (poGoCas.MeansInfo().getIncomeSource().isEmpty()) {
                poGoCas.MeansInfo().setIncomeSource(value);
                Log.e(TAG, "Primary source of income has been set to : " + value);
            }
        } else {
            if (poGoCas.SpouseMeansInfo().getIncomeSource().isEmpty()) {
                poGoCas.SpouseMeansInfo().setIncomeSource(value);
                Log.e(TAG, "Spouse primary source of income has been set to : " + value);
            }
        }
    }

    public enum TYPE{
        APPLICANT,
        SPOUSE
    }
}
