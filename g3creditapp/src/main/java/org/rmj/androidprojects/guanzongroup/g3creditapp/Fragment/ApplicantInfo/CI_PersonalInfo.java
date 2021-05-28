package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.ApplicantInfo;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_InformationCI;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.FormatUIText;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.gocas.base.GOCASApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class CI_PersonalInfo extends Fragment {

    private AppData db;

    public CI_PersonalInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.ci_personal_info, container, false);

        GOCASApplication gocas = new Activity_InformationCI().getInstance().getGOCasInfo();
        TextView lblindex0 = v.findViewById(R.id.lbl_review_applName);
        TextView lblindex1 = v.findViewById(R.id.lbl_review_applGender);
        TextView lblindex2 = v.findViewById(R.id.lbl_review_cvlStatus);
        TextView lblindex3 = v.findViewById(R.id.lbl_review_applBrthDate);
        TextView lblindex4 = v.findViewById(R.id.lbl_review_applBrthPlace);
        TextView lblindex5 = v.findViewById(R.id.lbl_review_applCitizen);
        TextView lblindex6 = v.findViewById(R.id.lbl_review_applMtherName);
        TextView lblindex7 = v.findViewById(R.id.lbl_review_applContact);
        TextView lblindex8 = v.findViewById(R.id.lbl_review_appllandline);
        TextView lblindex9 = v.findViewById(R.id.lbl_review_applEmail);
        TextView lblindex10 = v.findViewById(R.id.lbl_review_applFacebook);
        TextView lblindex11 = v.findViewById(R.id.lbl_review_applViberAccount);
        TextView lblindex12 = v.findViewById(R.id.lbl_review_applLandmark);
        TextView lblindex13 = v.findViewById(R.id.lbl_review_applPresentAdd);
        TextView lblindex14 = v.findViewById(R.id.lbl_review_applPermanentAdd);
        TextView lblindex15 = v.findViewById(R.id.lbl_review_applHouseOwnership);
        TextView lblindex16 = v.findViewById(R.id.lbl_review_applHousehold);
        TextView lblindex17 = v.findViewById(R.id.lbl_review_applHouseType);
        TextView lblindex18 = v.findViewById(R.id.lbl_review_applLngthStay);
        TextView lblindex19 = v.findViewById(R.id.lbl_review_applGarage);

        lblindex0.setText(gocas.ApplicantInfo().getClientName());
        lblindex1.setText(getGender(gocas.ApplicantInfo().getGender()));
        lblindex2.setText(getCivilStatus(gocas.ApplicantInfo().getCivilStatus()));
        lblindex3.setText(new FormatUIText().formatGOCasBirthdate(gocas.ApplicantInfo().getBirthdate()));
        lblindex4.setText(getBirthPlace(gocas.ApplicantInfo().getBirthPlace()));
        lblindex5.setText(getCitizenship(gocas.ApplicantInfo().getCitizenship()));
        lblindex6.setText(gocas.ApplicantInfo().getMaidenName());
        lblindex7.setText(getContact(gocas));
        lblindex8.setText(getLandLine(gocas.ApplicantInfo().getPhoneNo(0)));
        lblindex9.setText(gocas.ApplicantInfo().getEmailAddress(0));
        lblindex10.setText(gocas.ApplicantInfo().getFBAccount());
        lblindex11.setText(gocas.ApplicantInfo().getViberAccount());
        lblindex12.setText(gocas.ResidenceInfo().PresentAddress().getLandMark());
        lblindex13.setText(getPresentAddress(gocas));
        lblindex14.setText(getPermanentAddress(gocas));
        lblindex15.setText(getOwnership(gocas.ResidenceInfo().getOwnership()));
        lblindex16.setText(getHousehold(gocas));
        lblindex17.setText(getHouseType(gocas.ResidenceInfo().getHouseType()));
        lblindex18.setText(getLenghtStay(gocas.ResidenceInfo().getRentNoYears()));
        lblindex19.setText(hasGarage(gocas.ResidenceInfo().hasGarage()));
        return v;
    }

    private String getGender(String value){
        if(value.equalsIgnoreCase("0")){
            return "Male";
        } else if(value.equalsIgnoreCase("1")){
            return "Female";
        } else {
            return "LGBT";
        }
    }

    private String getCivilStatus(String value){
        switch (value){
            case "0":
                return "Single";

            case "1":
                return "Married";

            case "2":
                return "Separated";

            case "3":
                return "Widowed";

            case "4":
                return "Single-Parent";

            case "5":
                return "Single-Parent W/ Live-in Partner";
        }
        return "";
    }

    private String getBirthPlace(String value){
        String[] laBplace = getTownProvince(value);
        return laBplace[1] + ", " + laBplace[0];
    }

    private String getCitizenship(String value){
        Cursor loCursor = db.getReadableDb().rawQuery("SELECT * FROM CreditApp_Country WHERE sCntryCde = '"+value+"'", null);
        loCursor.moveToFirst();
        String lsReturn = loCursor.getString(loCursor.getColumnIndex("sNational"));
        loCursor.close();
        return lsReturn;
    }

    private String getContact(GOCASApplication gocas){
        int count = gocas.ApplicantInfo().getMobileNoQty();
        switch (count){
            case 1:
                return gocas.ApplicantInfo().getMobileNo(0);
            case 2:
                return gocas.ApplicantInfo().getMobileNo(0) +"\n" +
                        gocas.ApplicantInfo().getMobileNo(1);
            case 3:
                return gocas.ApplicantInfo().getMobileNo(0) + "\n" +
                        gocas.ApplicantInfo().getMobileNo(1) + "\n" +
                        gocas.ApplicantInfo().getMobileNo(2);
        }
        return "";
    }

    private String getLandLine(String value){
        try {
            String lsFirstCut = value.substring(0, 3);
            String ls2ndCut = value.substring(3, 7);
            return lsFirstCut + "-" + ls2ndCut;
        }catch (Exception e){
            e.printStackTrace();
            return "NA";
        }
    }

    private String getPresentAddress(GOCASApplication gocas){
        try {
            String lsBrgyID = gocas.ResidenceInfo().PresentAddress().getBarangay();
            return getHouseNo(gocas) + getAddress1(gocas) + getAddress2(gocas) + " Brgy. " +
                    getBarangayDetail(lsBrgyID)[0] +", "+
                    getBarangayDetail(lsBrgyID)[1] +", "+
                    getBarangayDetail(lsBrgyID)[2];
        } catch (Exception e){
            e.printStackTrace();
            return "NA";
        }
    }

    private String getHouseNo(GOCASApplication gocas){
        if(!gocas.ResidenceInfo().PresentAddress().getHouseNo().isEmpty()){
            return "#"+gocas.ResidenceInfo().PresentAddress().getHouseNo() + " ";
        }
        return "";
    }

    private String getAddress1(GOCASApplication gocas){
        if(!gocas.ResidenceInfo().PresentAddress().getAddress1().isEmpty()){
            return gocas.ResidenceInfo().PresentAddress().getAddress1() + ", ";
        }
        return "";
    }

    private String getAddress2(GOCASApplication gocas){
        if(!gocas.ResidenceInfo().PresentAddress().getAddress2().isEmpty()){
            return gocas.ResidenceInfo().PresentAddress().getAddress2() + ", ";
        }
        return "";
    }

    private String getPermanentAddress(GOCASApplication gocas){
        try {
            String lsBrgyID = gocas.ResidenceInfo().PermanentAddress().getBarangay();
            return getPermanentHouseNo(gocas) + getPermanentAddress1(gocas) + getPermanentAddress2(gocas) + " Brgy. " +
                    getBarangayDetail(lsBrgyID)[0] +", "+
                    getBarangayDetail(lsBrgyID)[1] +", "+
                    getBarangayDetail(lsBrgyID)[2];
        } catch (Exception e){
            e.printStackTrace();
            return "NA";
        }
    }

    private String getPermanentHouseNo(GOCASApplication gocas){
        if(!gocas.ResidenceInfo().PermanentAddress().getHouseNo().isEmpty()){
            return "#"+gocas.ResidenceInfo().PermanentAddress().getHouseNo() + " ";
        }
        return "";
    }

    private String getPermanentAddress1(GOCASApplication gocas){
        if(!gocas.ResidenceInfo().PermanentAddress().getAddress1().isEmpty()){
            return gocas.ResidenceInfo().PermanentAddress().getAddress1() + ", ";
        }
        return "";
    }

    private String getPermanentAddress2(GOCASApplication gocas){
        if(!gocas.ResidenceInfo().PermanentAddress().getAddress2().isEmpty()){
            return gocas.ResidenceInfo().PermanentAddress().getAddress2() + ", ";
        }
        return "";
    }

    private String getOwnership(String value){
        if(value.equalsIgnoreCase("0")){
            return "Owned";
        } else if(value.equalsIgnoreCase("1")){
            return "Rent";
        } else {
            return "Caretaker";
        }
    }

    private String getHousehold(GOCASApplication gocas){
        String value;
        if(!gocas.ResidenceInfo().getOwnedResidenceInfo().isEmpty()) {
            value = gocas.ResidenceInfo().getOwnedResidenceInfo();
        } else {
            value = gocas.ResidenceInfo().getRentedResidenceInfo();
        }
        if (value.equalsIgnoreCase("0")) {
            return "Living with spouse";
        } else if (value.equalsIgnoreCase("1")) {
            return "Living with parents & siblings";
        } else {
            return "Living with relatives";
        }
    }

    private String getHouseType(String value){
        if(value.equalsIgnoreCase("0")){
            return "Concrete";
        } else if(value.equalsIgnoreCase("1")){
            return "Concrete & Wood";
        } else {
            return "Wood";
        }
    }

    private String getLenghtStay(double value){
        if(value == 0.0){
            return "NA";
        } else if(value % 1 == 0){
            return value + "Year/s";
        }
        double ldMonth = value * 12;
        return ldMonth + "Month/s";
    }

    private String hasGarage(String value){
        if(value.equalsIgnoreCase("1")){
            return "YES";
        }
        return "NO";
    }


    private String[] getTownProvince(String ID){
        String[] data = new String[3];
        String[] bindArgs = {ID};
        Cursor cursor = db.getReadableDb().rawQuery("SELECT a.sProvName, b.sTownName FROM CreditApp_Province a " +
                "LEFT JOIN CreditApp_Town b " +
                "ON a.sProvIDxx = b.sProvIDxx " +
                "WHERE b.sTownIDxx = ?", bindArgs);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            data[0] = cursor.getString(0);
            data[1] = cursor.getString(1);
            cursor.close();
            return data;
        }
        cursor.close();
        return data;
    }

    private String[] getBarangayDetail(String ID){
        String[] data = new String[3];
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
            cursor.close();
            return data;
        }
        cursor.close();
        return data;
    }
}
