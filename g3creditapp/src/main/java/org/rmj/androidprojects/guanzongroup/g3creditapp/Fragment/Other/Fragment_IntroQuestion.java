package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Other;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.simple.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.ConstantsAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.AutoSuggestBrandModel;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.pricelist.Pricelist;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment_IntroQuestion extends Fragment {
    private static final String TAG = Fragment_IntroQuestion.class.getSimpleName();
    private final DecimalFormat currency_total = new DecimalFormat("###,###,###.###");

    private View view;
    private AutoSuggestBrandModel model;
    private Pricelist oPrice;
    private SQLiteDatabase db;
    private JSONObject params;
    private ConstantsAdapter adapter;

    private Spinner spnApplicantType;
    private Spinner spnCustomerType;
    private TextInputLayout tilBranch;
    private AutoCompleteTextView txtBranchLst;
    private TextInputLayout tilBrand;
    private AutoCompleteTextView txtBrand;
    private TextInputLayout tilModel;
    private AutoCompleteTextView txtModel;
    private TextInputLayout tilDownpayment;
    private TextInputEditText txtDownpayment;
    private Spinner spnTermx;
    private TextInputEditText txtMonthlyPayment;
    private MaterialButton btnNext;
    private ArrayList<ArrayList<String>> brandData;
    private ArrayList<ArrayList<String>> modelData;

    private String BrandID = "";
    private String ModelID = "";
    private String BrnchID = "";

    public Fragment_IntroQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_intro_question, container, false);
        return view;
    }
}