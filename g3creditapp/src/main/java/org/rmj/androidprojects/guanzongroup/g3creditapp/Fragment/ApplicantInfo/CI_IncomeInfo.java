package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.ApplicantInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

public class CI_IncomeInfo extends Fragment {

    public CI_IncomeInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ci_personal_info, container, false);
    }
}
