package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.ApplicantInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

public class CI_Disbursement extends Fragment {

    public CI_Disbursement(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ci_disbursement, container, false);
    }
}
