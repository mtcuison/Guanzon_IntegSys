package org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.CreditSourceObjects;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;

public class ConstantsAdapter {
    private String lcSelected;
    private String lsMarried;

    private Context mContext;
    private GOCASApplication loGoCas;

    public ConstantsAdapter(Context context){
        this.mContext = context;
    }

    public ConstantsAdapter(GOCASApplication GoCas, Context context){
        this.loGoCas = GoCas;
        this.mContext = context;
        this.lsMarried = loGoCas.ApplicantInfo().getCivilStatus();
    }
    /**
     *
     * @param Selection
     *
     * selection sets which array must be return...
     * @return
     */
    public ArrayAdapter<String> getAdapter(String Selection){
        this.lcSelected = Selection;
        return new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, Objects.requireNonNull(getArrayData()));
    }

    private String[] getArrayData(){
        switch (lcSelected){
            case AdapterSelections.Civil_Status:
                return CreditSourceObjects.getCivilStatus;
            case AdapterSelections.Suffix:
                return CreditSourceObjects.getSuffix;
            case AdapterSelections.Loan_Application:
                return CreditSourceObjects.getApplications;
            case AdapterSelections.Customer_Type:
                return CreditSourceObjects.getCustomerTypes;
            case AdapterSelections.Living_Status:
                return CreditSourceObjects.getLivingStatus;
            case AdapterSelections.House_Type:
                return CreditSourceObjects.getHouseType;
            case AdapterSelections.Nature_Of_Business:
                return CreditSourceObjects.getNatureOfBusiness;
            case AdapterSelections.Employment_Status:
                return CreditSourceObjects.getEmploymentStatus;
            case AdapterSelections.Business_Type:
                return CreditSourceObjects.getBusinessType;
            case AdapterSelections.Business_Size:
                return CreditSourceObjects.getBusinessSize;
            case AdapterSelections.Finance_Source:
                return CreditSourceObjects.getFinanceSource;
            case AdapterSelections.Installment_Term:
                return CreditSourceObjects.getInstallmenTerms;
            case AdapterSelections.Unit_User:
                return CreditSourceObjects.getUnitUser;
            case AdapterSelections.Other_Unit_User:
                return UnitUserOther();
            case AdapterSelections.Unit_Purpose:
                return CreditSourceObjects.getUnitPurpose;
            case AdapterSelections.Unit_Payer:
                return UnitPayerOther();
            case AdapterSelections.Company_Info_Source:
                return CompanyInfoSource();
            case AdapterSelections.Mobile_Network_Type:
                return CreditSourceObjects.getNetworkStat;
            case AdapterSelections.Government_Level:
                return CreditSourceObjects.getGovernmentLevel;
            case AdapterSelections.Company_Level:
                return CreditSourceObjects.getCompanyLevel;
            case AdapterSelections.Employment_Level:
                return CreditSourceObjects.getEmploymentLevel;
            case AdapterSelections.Country_Region:
                return CreditSourceObjects.getOverseasRegion;
            case AdapterSelections.Years_Of_Experience:
                return CreditSourceObjects.getYearsOfExp;
            case AdapterSelections.Education_Level:
                return CreditSourceObjects.getSchoolLevel;
            case AdapterSelections.Comaker_Income_Source:
                return CreditSourceObjects.getComakerIncomeSource;
            case AdapterSelections.OFW_Work_Type:
                return CreditSourceObjects.getOfwCategory;
            case AdapterSelections.Relations:
                return Relation();
            case AdapterSelections.TimeLength:
                return CreditSourceObjects.getTimeLength;
        }
        return null;
    }

    public interface AdapterSelections{
        String Civil_Status = "Cap1000";
        String Suffix = "Cap1001";
        String Loan_Application = "Cap1002";
        String Customer_Type = "Cap1003";
        String Living_Status = "Cap1004";
        String House_Type = "Cap1005";
        String Nature_Of_Business = "Cap1006";
        String Employment_Status = "Cap1007";
        String Business_Type = "Cap1008";
        String Business_Size = "Cap1009";
        String Finance_Source = "Cap1010";
        String Installment_Term = "Cap1011";
        String Unit_User = "Cap1012";
        String Other_Unit_User = "Cap1013";
        String Unit_Purpose = "Cap1014";
        String Unit_Payer = "Cap1015";
        String Company_Info_Source = "Cap1016";
        String Mobile_Network_Type = "Cap1017";
        String Government_Level = "Cap1018";
        String Company_Level = "Cap1019";
        String Employment_Level = "Cap1020";
        String OFW_Work_Type = "Cap1021";
        String Country_Region = "Cap1022";
        String Years_Of_Experience = "Cap1023";
        String Education_Level = "Cap1024";
        String Comaker_Income_Source = "Cap1025";
        String Relations = "Cap1026";
        String TimeLength = "Cap1027";
    }

    private boolean isMarried(){
        return lsMarried.equalsIgnoreCase("1");
    }

    private String[] UnitUserOther(){
        if(isMarried())
            return CreditSourceObjects.getUnitUserOthers;
        else
            return CreditSourceObjects.getUnitUserOthersNoSpouse;
    }

    private String[] UnitPayerOther(){
        if(isMarried())
            return CreditSourceObjects.getUnitPayer;
        else
            return CreditSourceObjects.getUnitPayerNoSpouse;
    }

    private String[] CompanyInfoSource(){
        if(isMarried())
            return CreditSourceObjects.getIntoUs;
        else
            return CreditSourceObjects.getIntoUsNoSpouse;
    }

    private String[] Relation(){
        if(isMarried())
            return CreditSourceObjects.getComakerRelation;
        else
            return CreditSourceObjects.getComakerRelationNoSpouse;
    }
}
