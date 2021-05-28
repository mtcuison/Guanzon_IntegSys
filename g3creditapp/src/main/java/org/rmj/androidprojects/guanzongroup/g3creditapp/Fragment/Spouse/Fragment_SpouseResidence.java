package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.Dialog_IncomeSource;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.AutoSuggestAddress;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_SpouseResidence extends Fragment {
    private static final String TAG = Fragment_SpouseResidence.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static Fragment_SpouseResidence instance;

    private CreditApplication poCreditApp;
    private String TransNox;

    private View v;
    private AutoSuggestAddress address;
    private String ProvID = "";
    private String TownID = "";
    private String BrgyID = "";

    private TextInputEditText tieLndMark;
    private TextInputEditText tieHouseNo;
    private TextInputEditText tieLotNoSt;
    private TextInputEditText tieStreetx;
    private TextInputLayout tilProvince;
    private TextInputLayout tilTownCity;
    private TextInputLayout tilBarangay;
    private AutoCompleteTextView tieProvince;
    private AutoCompleteTextView tieTownCity;
    private AutoCompleteTextView tieBarangay;
    private CheckBox cbLivingWithSpouse;
    private MaterialButton btnPrvs;
    private MaterialButton btnNext;

    public Fragment_SpouseResidence() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_spouse_residence, container, false);
        instance = this;
        address = new AutoSuggestAddress(getActivity());
        poCreditApp = new CreditApplication(getActivity());
        TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        setupWidgets();
        setupAction();
        return v;
    }

    private void setupWidgets(){
        tieLndMark = v.findViewById(R.id.tie_cap_spsResidenceLandM);
        tieHouseNo = v.findViewById(R.id.tie_cap_spsHouseNo);
        tieLotNoSt = v.findViewById(R.id.tie_cap_spsRsdnSittio);
        tilProvince = v.findViewById(R.id.til_cap_spsRsdnProvince);
        tilTownCity = v.findViewById(R.id.til_cap_spsRsdnTown);
        tilBarangay = v.findViewById(R.id.til_cap_spsRdsnBrgy);
        tieStreetx = v.findViewById(R.id.tie_cap_spsRsdnStreet);
        tieProvince = v.findViewById(R.id.tie_cap_spsRsdnProvince);
        tieTownCity = v.findViewById(R.id.tie_cap_spsRsdnTown);
        tieBarangay = v.findViewById(R.id.tie_cap_spsRsdnBrgy);
        cbLivingWithSpouse = v.findViewById(R.id.cb_cap_livingWithSpouse);
        btnPrvs = v.findViewById(R.id.btn_fragment_spsResidence_prevs);
        btnNext = v.findViewById(R.id.btn_fragment_spsResidence_next);
    }

    private void setupAction(){
        tieProvince.setAdapter(getProvinceList());
        tieProvince.setOnItemClickListener(new OnAddressSelectedListener(tieProvince));
        tieTownCity.setAdapter(getTownList());
        tieTownCity.setOnItemClickListener(new OnAddressSelectedListener(tieTownCity));
        tieBarangay.setAdapter(getBarangayList());
        tieBarangay.setOnItemClickListener(new OnAddressSelectedListener(tieBarangay));
        cbLivingWithSpouse.setOnCheckedChangeListener(new OnCheckboxSetListener());
        btnPrvs.setOnClickListener(new OnButtonClickListener());
        btnNext.setOnClickListener(new OnButtonClickListener());
    }

    public static Fragment_SpouseResidence getInstance(){
        return instance;
    }

    private void setupSpouseResidenceInfo(){
        GOCASApplication poGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
        poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setLandMark(Objects.requireNonNull(tieLndMark.getText()).toString());
        poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setHouseNo(Objects.requireNonNull(tieHouseNo.getText()).toString());
        poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setAddress1(Objects.requireNonNull(tieLotNoSt.getText()).toString());
        poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setAddress2(Objects.requireNonNull(tieStreetx.getText()).toString());
        poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setTownCity(TownID);
        poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setBarangay(BrgyID);
        Log.e(TAG, "Spouse residence information result : " + poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().toJSONString());
        poCreditApp.UpdateApplicationInfo(poGoCas, TransNox);
    }

    private ArrayAdapter<String> getProvinceList(){
        String[] data = new String[address.getProvinceList().get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getProvinceList().get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getTownList(){
        String[] data = new String[address.getTownList(ProvID).get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getTownList(ProvID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private ArrayAdapter<String> getBarangayList(){
        String[] data = new String[address.getBarangayList(TownID).get(0).size()];
        for(int x = 0; x < data.length; x++){
            data[x] = address.getBarangayList(TownID).get(1).get(x);
        }
        return new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_dropdown_item, data);
    }

    class OnAddressSelectedListener implements AdapterView.OnItemClickListener{

        View autoView;

        OnAddressSelectedListener(View view){
            this.autoView = view;
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(autoView.getId() == R.id.tie_cap_spsRsdnProvince){
                for(int x = 0; x < address.getProvinceList().get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getProvinceList().get(1).get(x))){
                        ProvID = address.getProvinceList().get(0).get(x);
                        tieTownCity.setAdapter(getTownList());
                        break;
                    }
                }
            } else if(autoView.getId() == R.id.tie_cap_spsRsdnTown){
                for(int x = 0; x < address.getTownList(ProvID).get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getTownList(ProvID).get(1).get(x))){
                        TownID = address.getTownList(ProvID).get(0).get(x);
                        tieBarangay.setAdapter(getBarangayList());
                        break;
                    }
                }
            } else if(autoView.getId() == R.id.tie_cap_spsRsdnBrgy){
                for(int x = 0; x < address.getBarangayList(TownID).get(0).size(); x++){
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase(address.getBarangayList(TownID).get(1).get(x))){
                        BrgyID = address.getBarangayList(TownID).get(0).get(x);
                        break;
                    }
                }
            }
        }
    }

    class OnCheckboxSetListener implements CheckBox.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.isChecked()){
                GOCASApplication loGoCas = poCreditApp.getActiveGoCasInfo(TransNox);
                String ID = loGoCas.ResidenceInfo().PresentAddress().getBarangay();
                tieLndMark.setText(loGoCas.ResidenceInfo().PresentAddress().getLandMark());
                tieHouseNo.setText(loGoCas.ResidenceInfo().PresentAddress().getHouseNo());
                tieLotNoSt.setText(loGoCas.ResidenceInfo().PresentAddress().getAddress1());
                tieStreetx.setText(loGoCas.ResidenceInfo().PresentAddress().getAddress2());
                tieProvince.setText(getAddressName(ID)[2]);
                tieTownCity.setText(getAddressName(ID)[1]);
                tieBarangay.setText(getAddressName(ID)[0]);
                TownID = loGoCas.ResidenceInfo().PresentAddress().getTownCity();
                BrgyID = loGoCas.ResidenceInfo().PresentAddress().getBarangay();
            }
        }
    }

    private String[] getAddressName(String ID){
        String[] data = new String[3];
        AppData db = AppData.getInstance(getActivity());
        Cursor cursor = db.getReadableDb().rawQuery("SELECT a.sBrgyName, b.sTownName, c.sProvName, c.sProvIDxx " +
                "FROM CreditApp_Barangay a " +
                "LEFT JOIN CreditApp_Town b " +
                "ON a.sTownIDxx = b.sTownIDxx " +
                "LEFT JOIN CreditApp_Province c " +
                "ON b.sProvIDxx = c.sProvIDxx WHERE sBrgyIDxx = '"+ID+"'", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            data[0] = cursor.getString(0);
            data[1] = cursor.getString(1);
            data[2] = cursor.getString(2);
            ProvID = cursor.getString(3);
            cursor.close();
            return data;
        }
        cursor.close();
        return data;
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_fragment_spsResidence_prevs){
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(3, true);
            } else if(v.getId() == R.id.btn_fragment_spsResidence_next){
                if(isDataValid()) {
                    setupSpouseResidenceInfo();
                    if(new Fragment_SpouseMeans().getInstance().getFragmentSize() == 0) {
                        new Dialog_IncomeSource(getActivity(), TransNox, Dialog_IncomeSource.TYPE.SPOUSE).showDialog();
                    }
                    ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(5, true);
                }
            }
        }
    }

    /**
     * Validations for spouse residence information
     * *************************************************
     * *************************************************/

    private void requestFocus(View view){
        if(view.requestFocus()){
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isDataValid(){
        if(!isProvinceValid()){
            new CustomToast(getActivity(), "Province info is empty or invalid", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isTownCityValid()){
            new CustomToast(getActivity(), "Town info is empty or invalid", CustomToast.TYPE.WARNING).show();
            return false;
        } else if(!isBarangayValid()){
            new CustomToast(getActivity(), "Barangay info is empty or invalid", CustomToast.TYPE.WARNING).show();
            return false;
        }
        return true;
    }

    private boolean isProvinceValid(){
        if(ProvID.isEmpty()){
            requestFocus(tieProvince);
            tilProvince.setError("Province info is empty or invalid.");
            return false;
        }
        tilProvince.setErrorEnabled(false);
        return true;
    }

    private boolean isTownCityValid(){
        if(TownID.isEmpty()){
            requestFocus(tieTownCity);
            tilTownCity.setError("Town info is empty or invalid");
            return false;
        }
        tilTownCity.setErrorEnabled(false);
        return true;
    }

    private boolean isBarangayValid(){
        if(BrgyID.isEmpty()){
            requestFocus(tilBarangay);
            tilBarangay.setError("Barangay info is empty or invalid");
            return false;
        }
        tilBarangay.setErrorEnabled(false);
        return true;
    }
}
