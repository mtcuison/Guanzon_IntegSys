package org.rmj.androidprojects.guanzongroup.g3creditapp.Etc;

import android.util.Log;

public class ComakerSetting {
    private static final String TAG = ComakerSetting.class.getSimpleName();

    private int liAgexx;

    public boolean isComakeNeeded(String BirthDate){
        liAgexx = new AgeCalculator().getAge(BirthDate);
        return isChildDependent() || isAdultDependent();
    }

    private boolean isChildDependent(){
        if(liAgexx <= 17){
            Log.e(TAG, "Applicant is child dependent.");
            return true;
        }
        return false;
    }

    private boolean isAdultDependent(){
        if(liAgexx >=59){
            Log.e(TAG, "Applicant is adult dependent.");
            return true;
        }
        return false;
    }
}
