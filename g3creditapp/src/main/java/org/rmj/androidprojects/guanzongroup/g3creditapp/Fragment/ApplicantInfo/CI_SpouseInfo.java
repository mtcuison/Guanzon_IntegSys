package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.ApplicantInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

public class CI_SpouseInfo extends Fragment {

    public CI_SpouseInfo(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ci_spouse_info, container, false);
    }
}
