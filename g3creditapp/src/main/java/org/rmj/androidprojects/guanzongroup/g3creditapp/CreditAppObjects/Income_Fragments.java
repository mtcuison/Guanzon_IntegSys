package org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Income_Fragments {
    private static final String TAG = Income_Fragments.class.getSimpleName();

    private Fragment Employed;
    private Fragment Seld_Employed;
    private Fragment Finance;
    private Fragment Pension;
    private Fragment SpouseEmployment;
    private Fragment SpouseBusiness;
    private Fragment SpousePension;

    private ArrayList<Fragment> pArFragments;

    public Income_Fragments(){
        pArFragments = new ArrayList<>();
    }

    public void setEmployed(Fragment employed) {
        Employed = employed;
    }

    public void setSeld_Employed(Fragment seld_Employed) {
        Seld_Employed = seld_Employed;
    }

    public void setFinance(Fragment finance) {
        Finance = finance;
    }

    public void setPension(Fragment pension){Pension = pension;}

    public void setSpouseEmployment(Fragment employment){
        SpouseEmployment = employment;
    }

    public void setSpouseBusiness(Fragment spouseBusiness) {
        SpouseBusiness = spouseBusiness;
    }

    public void setSpousePension(Fragment spousePension){
        SpousePension = spousePension;
    }

    public ArrayList<Fragment> getIncomeFragments(){
        pArFragments.clear();
        if(Employed != null){
            pArFragments.add(Employed);
        }
        if(Seld_Employed != null){
            pArFragments.add(Seld_Employed);
        }
        if(Finance != null){
            pArFragments.add(Finance);
        }
        if(Pension != null){
            pArFragments.add(Pension);
        }
        if(SpouseEmployment != null){
            pArFragments.add(SpouseEmployment);
        }
        if(SpouseBusiness != null){
            pArFragments.add(SpouseBusiness);
        }
        if(SpousePension != null){
            pArFragments.add(SpousePension);
        }
        Log.e(TAG, "Fragment size : " + pArFragments.size());
        return pArFragments;
    }
}
