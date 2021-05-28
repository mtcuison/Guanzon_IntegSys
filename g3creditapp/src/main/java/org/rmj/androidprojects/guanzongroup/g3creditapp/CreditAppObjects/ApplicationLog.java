package org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.FormatUIText;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.gocas.pricelist.PriceFactory;
import org.rmj.gocas.pricelist.Pricelist;

import java.math.BigDecimal;

public class ApplicationLog {

    private AppData db;

    private String GoCasNoxx;
    private String sTransNox;
    private String sBranchCd;
    private String dTransact;
    private String DetlInfox;
    private String sClientNm;
    private String Model;
    private String Down;
    private String DownPerc;
    private String IsWithCI;
    private String cUnitAppl;
    private String cSendStat;
    private String cTranStat;
    private String dDateSent;
    private String dApproved;

    public ApplicationLog(Context context,
                          String GoCasNoxx,
                          String sTransNox,
                          String sBranchCd,
                          String dTransact,
                          String DetlInfox,
                          String sClientNm,
                          String Model,
                          String Down,
                          String DownPerc,
                          String IsWithCI,
                          String cUnitAppl,
                          String cSendStat,
                          String cTranStat,
                          String dDateSent,
                          String dApproved) {
        this.db = AppData.getInstance(context);
        this.GoCasNoxx = GoCasNoxx;
        this.sTransNox = sTransNox;
        this.sBranchCd = sBranchCd;
        this.dTransact = dTransact;
        this.sClientNm = sClientNm;
        this.DetlInfox = DetlInfox;
        this.Model = Model;
        this.Down = Down;
        this.DownPerc = DownPerc;
        this.IsWithCI = IsWithCI;
        this.cUnitAppl = cUnitAppl;
        this.cSendStat = cSendStat;
        this.cTranStat = cTranStat;
        this.dDateSent = dDateSent;
        this.dApproved = dApproved;
    }

    public String getDetlInfox() {
        return DetlInfox;
    }

    public String getGoCasNoxx() {
        if(GoCasNoxx.equalsIgnoreCase("null")){
            return "Pending...";
        }
        return GoCasNoxx;
    }

    public String getdDateSent() {
        return new FormatUIText().getParseDateTime(dDateSent);
    }

    public String getsTransNox() {
        return sTransNox;
    }

    public String getsBranchCd() {
        return sBranchCd;
    }

    public String getdTransact() {
        return new FormatUIText().getParseDateTime(dTransact);
    }

    public String getsClientNm() {
        return sClientNm;
    }

    public String getModelName() {
        return getModelName(Model);
    }

    public String getPrice() {
        return getModelPrice(Model);
    }

    public String getDown() {
        return "Downpayment : "+ new FormatUIText().getCurrencyUIFormat(Down);
    }

    public String getDownPerc() {
        return "DP Percentage : " + getDownpaymentPercentage() + "%";
    }

    public int getVoidStatus(){
        if(cSendStat.equalsIgnoreCase("0")){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public int getExportStatus(){
        if(GoCasNoxx.equalsIgnoreCase("null")){
            return View.GONE;
        }
        return View.VISIBLE;
    }

    public String getIsWithCI() {
        if(cTranStat.equalsIgnoreCase("0")){
            return "Waiting for approval.";
        } else if(cTranStat.isEmpty()){
            return "Waiting for approval.";
        }
        return "Approve " + getCIStat();
    }

    public String getcUnitAppl() {
        return getUnitApplied(cUnitAppl);
    }

    public String getcSendStat() {
        return getStatus();
    }

    public String getDateApproved(){
        if(dApproved.equalsIgnoreCase("null")){
            return "pending...";
        }
        return new FormatUIText().getParseDateTime(dApproved);
    }

    private String getUnitApplied(String UnitApplied){
        switch (UnitApplied){
            case "0":
                return "Motorcycle";
            case "1":
                return "Sidecar";
            case "2":
                return "Others";
            case "3":
                return "Mobile Phone";
            case "4":
                return "Cars";
            case "5":
                return "Services";

        }
        return "Unknown";
    }

    private String getModelPrice(String ModelID){
        try{
            Pricelist loPrice = PriceFactory.make(PriceFactory.ProductType.MOTORCYCLE);
            String lsSql = loPrice.getSQ_Model(ModelID, true, true);
            Cursor cursor = db.getReadableDb().rawQuery(lsSql, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                String lsPrice = cursor.getString(cursor.getColumnIndex("nSelPrice"));
                Pricelist pricelist = PriceFactory.make(PriceFactory.ProductType.MOTORCYCLE);
                cursor.close();
                return "Price : "+ new FormatUIText().getCurrencyUIFormat(String.valueOf(pricelist.getSellingPrice(lsPrice)));
            }
            cursor.close();
            return "No model price found";
        }catch (Exception e){
            e.printStackTrace();
            return "No model price found";
        }
    }

    private String getModelName(String ModelID) {
        Cursor cursor = db.getReadableDb().rawQuery("SELECT sModelNme FROM MC_Model WHERE sModelIDx = '" + ModelID + "'", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String lsName = cursor.getString(cursor.getColumnIndex("sModelNme"));
            cursor.close();
            return lsName;
        }
        cursor.close();
        return "No model found";
    }

    private String getDownpaymentPercentage(){
        Pricelist loPrice = PriceFactory.make(PriceFactory.ProductType.MOTORCYCLE);
        String lsSql = loPrice.getSQ_Model(Model, true, true);
        Cursor cursor = db.getReadableDb().rawQuery(lsSql, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            String lsPrice = cursor.getString(cursor.getColumnIndex("nSelPrice"));
            Pricelist pricelist = PriceFactory.make(PriceFactory.ProductType.MOTORCYCLE);
            cursor.close();
            BigDecimal decimal = new BigDecimal(pricelist.getSellingPrice(lsPrice));
            double percent = Double.parseDouble(Down)/Double.parseDouble(String.valueOf(decimal))*100;
            return String.valueOf(BigDecimal.valueOf(percent).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        cursor.close();
        return "";
    }

    private String getStatus(){
        if(cSendStat.equalsIgnoreCase("0")){
            return "Waiting to send...";
        }
        return "Sent";
    }

    private String getCIStat(){
        if(IsWithCI.equalsIgnoreCase("1")){
            return "for C.I";
        }
        return "";
    }

    public int getStatColor(){
        if(cSendStat.equalsIgnoreCase("1")){
            return Color.GREEN;
        }
        return Color.RED;
    }
}
