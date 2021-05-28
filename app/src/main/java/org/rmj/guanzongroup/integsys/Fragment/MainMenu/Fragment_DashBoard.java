package org.rmj.guanzongroup.integsys.Fragment.MainMenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_ApplicationLog;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_IntroductoryQuestion;
import org.rmj.guanzongroup.integsys.BuildConfig;
import org.rmj.guanzongroup.integsys.R;
import org.rmj.guanzongroup.promotions.Activity_RaffleEntry;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment_DashBoard extends Fragment {
    private View mView;

    public Fragment_DashBoard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setupWidgets();

        return mView;
    }

    @SuppressLint("SetTextI18n")
    private void setupWidgets(){
        CardView btnDCP = mView.findViewById(R.id.mCardView_DCP);
        CardView btnAppList = mView.findViewById(R.id.mCardView_CreditApplications_List);
        CardView btnCrdtApp = mView.findViewById(R.id.mCardView_credit_app);
        CardView cshCnt = mView.findViewById(R.id.mCardView_CashCount);
        CardView draftx = mView.findViewById(R.id.mCardView_Draft);
        CardView btnChart = mView.findViewById(R.id.mCardView_Bullseye);
        TextView lblVersion = mView.findViewById(R.id.mLbl_BuildVersion);
        lblVersion.setText("Build Version. "
                + BuildConfig.VERSION_NAME + " - "
                + BuildConfig.VERSION_CODE + "Vc.");

        btnDCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Activity_RaffleEntry.class));
            }
        });

        /*btnDCP.setOnClickListener(v -> new CustomToast(getActivity(), "This feature is not yet available", CustomToast.TYPE.INFORMATION).show());*/

        btnAppList.setOnClickListener(v -> requireActivity().startActivity(new Intent(getActivity(), Activity_ApplicationLog.class)));

        btnCrdtApp.setOnClickListener(v -> requireActivity().startActivity(new Intent(getActivity(), Activity_IntroductoryQuestion.class)));

        /*cshCnt.setOnClickListener(v -> {
            CashCountSelect select = new CashCountSelect(getActivity(), v);
            select.showDialog();
        });*/

        //cshCnt.setOnClickListener(v -> new CustomToast(getActivity(), "This feature is not yet available", CustomToast.TYPE.INFORMATION).show());

        /*draftx.setOnClickListener(v -> startActivity(new Intent(getActivity(), Activity_CashCountLog.class)));*/

        //draftx.setOnClickListener(v -> new CustomToast(getActivity(), "Sample custom toast.", CustomToast.TYPE.INFORMATION).show());

        //btnChart.setOnClickListener(v -> {getActivity().startActivity(new Intent(getActivity() , Activity_Area.class)); });
    }
}
