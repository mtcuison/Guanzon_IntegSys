package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Disbursement;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.ComakerSetting;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment_Properties extends Fragment {
    private static final String TAG = Fragment_Properties.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static Fragment_Properties instance;
    private View v;

    private CreditApplication poCreditApp;
    private String TransNox;

    private TextInputEditText tieLandLot1;
    private TextInputEditText tieLandLot2;
    private TextInputEditText tieLandLot3;
    private CheckBox cb4WheelVhcl;
    private CheckBox cb3WheelVhcl;
    private CheckBox cb2WheelVhcl;
    private CheckBox cbAircondtnx;
    private CheckBox cbRefrigertr;
    private CheckBox cbTelevision;

    private String has4Wheels = "0";
    private String has3Wheels = "0";
    private String has2Wheels = "0";
    private String hasAirconx = "0";
    private String hasFridgex = "0";
    private String hasTelevsn = "0";

    private MaterialButton btnPrvs;
    private MaterialButton btnNext;

    public Fragment_Properties() {
        // Required empty public constructor
    }

    public static Fragment_Properties getInstance(){
        return instance;
    }

    public void setButtonText(String Text){
        btnNext.setText(Text);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        instance = this;
        v = inflater.inflate(R.layout.fragment_properties, container, false);
        poCreditApp = new CreditApplication(getActivity());
        TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        setupWidgets();
        setupActionListener();
        return v;
    }

    private void setupWidgets(){
        tieLandLot1 = v.findViewById(R.id.tie_cap_landLot1);
        tieLandLot2 = v.findViewById(R.id.tie_cap_landLot2);
        tieLandLot3 = v.findViewById(R.id.tie_cap_landLot3);
        cb4WheelVhcl = v.findViewById(R.id.cb_cap_4WheelVehicle);
        cb3WheelVhcl = v.findViewById(R.id.cb_cap_3WheelVehicle);
        cb2WheelVhcl = v.findViewById(R.id.cb_cap_2WheelVehicle);
        cbAircondtnx = v.findViewById(R.id.cb_cap_Aircon);
        cbRefrigertr = v.findViewById(R.id.cb_cap_refrigerator);
        cbTelevision = v.findViewById(R.id.cb_cap_television);

        btnPrvs = v.findViewById(R.id.btn_fragment_properties_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_properties_next);
    }

    private void setupActionListener(){
        cb4WheelVhcl.setOnCheckedChangeListener(new OnPropertiesSelectionListener());
        cb3WheelVhcl.setOnCheckedChangeListener(new OnPropertiesSelectionListener());
        cb2WheelVhcl.setOnCheckedChangeListener(new OnPropertiesSelectionListener());
        cbAircondtnx.setOnCheckedChangeListener(new OnPropertiesSelectionListener());
        cbRefrigertr.setOnCheckedChangeListener(new OnPropertiesSelectionListener());
        cbTelevision.setOnCheckedChangeListener(new OnPropertiesSelectionListener());
        btnPrvs.setOnClickListener(new OnButtonClickListener());
        btnNext.setOnClickListener(new OnButtonClickListener());
        btnNext.setText(getButtonText());
    }

    private void setupPropertiesInfo(){
        GOCASApplication poGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        poGoCas.DisbursementInfo().PropertiesInfo().setLotName1(Objects.requireNonNull(tieLandLot1.getText()).toString());
        poGoCas.DisbursementInfo().PropertiesInfo().setLotName2(Objects.requireNonNull(tieLandLot2.getText()).toString());
        poGoCas.DisbursementInfo().PropertiesInfo().setLotName3(Objects.requireNonNull(tieLandLot3.getText()).toString());
        poGoCas.DisbursementInfo().PropertiesInfo().Has4Wheels(has4Wheels);
        poGoCas.DisbursementInfo().PropertiesInfo().Has3Wheels(has3Wheels);
        poGoCas.DisbursementInfo().PropertiesInfo().Has2Wheels(has2Wheels);
        poGoCas.DisbursementInfo().PropertiesInfo().WithAirCon(hasAirconx);
        poGoCas.DisbursementInfo().PropertiesInfo().WithRefrigerator(hasFridgex);
        poGoCas.DisbursementInfo().PropertiesInfo().WithTelevision(hasTelevsn);
        Log.e(TAG, "Properties information result : " + poGoCas.DisbursementInfo().PropertiesInfo().toJSONString());
        poCreditApp.UpdateApplicationInfo(poGoCas, TransNox);
        ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(10, true);

    }

    class OnPropertiesSelectionListener implements CheckBox.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            if (id == R.id.cb_cap_4WheelVehicle) {
                if (isChecked) {
                    has4Wheels = "1";
                } else {
                    has4Wheels = "0";
                }
            } else if (id == R.id.cb_cap_3WheelVehicle) {
                if (isChecked) {
                    has3Wheels = "1";
                } else {
                    has3Wheels = "0";
                }
            } else if (id == R.id.cb_cap_2WheelVehicle) {
                if (isChecked) {
                    has2Wheels = "1";
                } else {
                    has2Wheels = "0";
                }
            } else if (id == R.id.cb_cap_Aircon) {
                if (isChecked) {
                    hasAirconx = "1";
                } else {
                    hasAirconx = "0";
                }
            } else if (id == R.id.cb_cap_refrigerator) {
                if (isChecked) {
                    hasFridgex = "1";
                } else {
                    hasFridgex = "0";
                }
            } else if (id == R.id.cb_cap_television) {
                if (isChecked) {
                    hasTelevsn = "1";
                } else {
                    hasTelevsn = "0";
                }
            }
        }
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_fragment_properties_prevs){
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(8, true);
            } else {
                setupPropertiesInfo();
            }
        }
    }

    private String getButtonText(){
        GOCASApplication loGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        String lsBirthdate = loGoCas.ApplicantInfo().getBirthdate();
        boolean lbNeedComaker = new ComakerSetting().isComakeNeeded(lsBirthdate);
        if(lbNeedComaker){
            return "Next";
        }
        return "Save";
    }
}
