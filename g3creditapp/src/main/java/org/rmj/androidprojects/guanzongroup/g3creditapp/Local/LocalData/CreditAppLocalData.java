package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;

public class CreditAppLocalData {
    private static final String TAG = CreditAppLocalData.class.getSimpleName();
    private AppData db;
    private String message;

    public CreditAppLocalData(Context context){
        this.db = AppData.getInstance(context);
    }

    public String getMessage(){
        return message;
    }

    public void setupCreditAppData() {
        try {
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS CreditApp_Barangay (" +
                    "sBrgyIDxx varchar PRIMARY KEY, " +
                    "sBrgyName varchar, " +
                    "sTownIDxx varchar, " +
                    "cHasRoute char, " +
                    "cBlackLst char, " +
                    "cRecdStat char, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS CreditApp_Town (" +
                    "sTownIDxx varchar PRIMARY KEY, " +
                    "sTownName varchar, " +
                    "sZippCode varchar, " +
                    "sProvIDxx varchar, " +
                    "sProvCode varchar, " +
                    "sMuncplCd varchar, " +
                    "cHasRoute varchar, " +
                    "cBlackLst varchar, " +
                    "cRecdStat varchar, " +
                    "dModified varchar, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS CreditApp_Province (" +
                    "sProvIDxx varchar PRIMARY KEY, " +
                    "sProvName varchar, " +
                    "cRecdStat char, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS CreditApp_Country (" +
                    "sCntryCde varchar PRIMARY KEY, " +
                    "sCntryNme varchar, " +
                    "sNational varchar, " +
                    "cRecdStat char, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS MC_Category (" +
                    "sMcCatIDx varchar, " +
                    "sMcCatNme varchar, " +
                    "nMiscChrg double, " +
                    "nRebatesx double, " +
                    "nEndMrtgg int, " +
                    "cRecdStat char, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS MC_Term_Category (" +
                    "sMCCatIDx varchar, " +
                    "nAcctTerm int, " +
                    "nAcctThru int, " +
                    "nFactorRt int, " +
                    "dPricexxx double, " +
                    "dTimeStmp DATETIME," +
                    "PRIMARY KEY(sMCCatIDx, nAcctTerm))");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS MC_Brand (" +
                    "sBrandIDx varchar PRIMARY KEY, " +
                    "sBrandNme varchar, " +
                    "cRecdStat char, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS Mc_Model (" +
                    "sModelIDx varchar PRIMARY KEY, " +
                    "sModelCde varchar, " +
                    "sModelNme varchar, " +
                    "sBrandIDx varchar, " +
                    "cMotorTyp char, " +
                    "cRegisTyp char, " +
                    "cEndOfLfe char, " +
                    "cEngineTp char, " +
                    "cHotItemx char, " +
                    "cRecdStat char, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS Mc_Model_Price (" +
                    "sModelIDx varchar PRIMARY KEY, " +
                    "nSelPrice double, " +
                    "nLastPrce double, " +
                    "nDealrPrc double, " +
                    "nMinDownx double, " +
                    "sMCCatIDx varchar, " +
                    "dPricexxx double, " +
                    "dInsPrice double, " +
                    "cRecdStat char, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS CreditApp_Religion (" +
                    "sRelgnIDx varchar PRIMARY KEY, " +
                    "sRelgnNme varchar, " +
                    "cRecdStat char, " +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS CreditApp_Branches (" +
                    "sBranchCd varchar PRIMARY KEY," +
                    "sBranchNm varcahr," +
                    "sDescript varchar," +
                    "sAddressx varchar," +
                    "sTownIDxx varchar," +
                    "sAreaCode varchar," +
                    "cPromoDiv char," +
                    "cDivision char," +
                    "cRecdStat char," +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS CreditApp_Occupations (" +
                    "sOccptnID varchar PRIMARY KEY, " +
                    "sOccptnNm varchar," +
                    "cRecdStat char," +
                    "dTimeStmp DATETIME)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS Temp_CreditApplication (" +
                    "sTransNox varchar PRIMARY KEY, " +
                    "sBranchCd varchar, " +
                    "sClientNm varchar, " +
                    "dCreatedx datetime, " +
                    "cUnitAppl varchar, " +
                    "sCreatedx varchar, " +
                    "sDetlInfo varchar, " +
                    "cAppPrgrs char, " +
                    "cApplStat char, " +
                    "dModified datetime)");

            db.getWritableDb().execSQL("CREATE TABLE IF NOT EXISTS Credit_Online_Application (" +
                    "sTransNox varchar PRIMARY KEY, " +
                    "sBranchCd varchar, " +
                    "dTransact date, " +
                    "dTargetDt date, " +
                    "sClientNm varchar, " +
                    "sGOCASNox varchar, " +
                    "sGOCASNoF varchar, " +
                    "cUnitAppl char, " +
                    "sSourceCD varchar, " +
                    "sDetlInfo int, " +
                    "sCatInfox char, " +
                    "sDesInfox varchar, " +
                    "sQMatchNo varchar, " +
                    "sQMAppCde varchar, " +
                    "nCrdtScrx int, " +
                    "cWithCIxx char," +
                    "nDownPaym double, " +
                    "nDownPayF double, " +
                    "sRemarksx varchar, " +
                    "dReceived date, " +
                    "sCreatedx varchar, " +
                    "dCreatedx dateTime, " +
                    "cSendStat char, " +
                    "sVerified varchar, " +
                    "dVerified date, " +
                    "dModified date, " +
                    "cTranStat char, " +
                    "cDivision char, " +
                    "cApplStat char, " +
                    "dTimeStmp DATETIME)");

            Log.e(TAG, "Tables for CreditApp has been created.");
            db.getWritableDb().setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all branch details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertBranches(JSONObject jsonResponse){
        try {
            JSONArray laJson = jsonResponse.getJSONArray("detail");
            if (laJson.length() > 0) {
                String lsSql = "INSERT OR REPLACE INTO CreditApp_Branches(sBranchCd, sBranchNm, sDescript, sAddressx, sTownIDxx, sAreaCode, cPromoDiv, cDivision, cRecdStat, dTimeStmp, sAreaCode) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                SQLiteStatement loSQl = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for (int x = 0; x < laJson.length(); x++) {
                    JSONObject loJson = new JSONObject(laJson.getString(x));
                    loSQl.clearBindings();
                    loSQl.bindString(1, loJson.getString("sBranchCd"));
                    loSQl.bindString(2, loJson.getString("sBranchNm"));
                    loSQl.bindString(3, loJson.getString("sDescript"));
                    loSQl.bindString(4, loJson.getString("sAddressx"));
                    loSQl.bindString(5, loJson.getString("sTownIDxx"));
                    loSQl.bindString(6, loJson.getString("sAreaCode"));
                    loSQl.bindString(7, loJson.getString("cPromoDiv"));
                    loSQl.bindString(8, loJson.getString("cDivision"));
                    loSQl.bindString(9, loJson.getString("cRecdStat"));
                    loSQl.bindString(10, loJson.getString("dTimeStmp"));
                    loSQl.bindString(11, loJson.getString("sAreaCode"));
                    loSQl.execute();
                }
                loSQl.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all barangay details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertBarangay(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO CreditApp_Barangay (sBrgyIDxx, sBrgyName, sTownIDxx, cBlackLst, cHasRoute, cRecdStat, dTimeStmp)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++) {
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1,detail.getString("sBrgyIDxx"));
                    loStatement.bindString(2,detail.getString("sBrgyName"));
                    loStatement.bindString(3,detail.getString("sTownIDxx"));
                    loStatement.bindString(4,detail.getString("cHasRoute"));
                    loStatement.bindString(5,detail.getString("cBlackLst"));
                    loStatement.bindString(6,detail.getString("cRecdStat"));
                    loStatement.bindString(7,detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all brand details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertBrand(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO MC_Brand(sBrandIDx, sBrandNme, cRecdStat, dTimeStmp) " +
                        "VALUES (?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1, detail.getString("sBrandIDx"));
                    loStatement.bindString(2, detail.getString("sBrandNme"));
                    loStatement.bindString(3, detail.getString("cRecdStat"));
                    loStatement.bindString(4, detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all category details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertCategory(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO MC_Category(sMCCatIDx, sMCCatNme, nMiscChrg, nRebatesx, nEndMrtgg, cRecdStat, dTimeStmp) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1,detail.getString("sMCCatIDx"));
                    loStatement.bindString(2,detail.getString("sMCCatNme"));
                    loStatement.bindString(3,detail.getString("nMiscChrg"));
                    loStatement.bindString(4,detail.getString("nRebatesx"));
                    loStatement.bindString(5,detail.getString("nEndMrtgg"));
                    loStatement.bindString(6,detail.getString("cRecdStat"));
                    loStatement.bindString(7,detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return false;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all country details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertCountry(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO CreditApp_Country(sCntryCde, sCntryNme, sNational, cRecdStat, dTimeStmp) " +
                        "VALUES(?, ?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1,detail.getString("sCntryCde"));
                    loStatement.bindString(2,detail.getString("sCntryNme"));
                    loStatement.bindString(3,detail.getString("sNational"));
                    loStatement.bindString(4,detail.getString("cRecdStat"));
                    loStatement.bindString(5,detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all Motorcycle Model details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertMcModel(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO Mc_Model(sModelIDx, sModelCde, sModelNme, sBrandIDx, cMotorTyp, cRegisTyp, cEndOfLfe, cEngineTp, cHotItemx, cRecdStat, dTimeStmp)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1, detail.getString("sModelIDx"));
                    loStatement.bindString(2, detail.getString("sModelCde"));
                    loStatement.bindString(3, detail.getString("sModelNme"));
                    loStatement.bindString(4, detail.getString("sBrandIDx"));
                    loStatement.bindString(5, detail.getString("cMotorTyp"));
                    loStatement.bindString(6, detail.getString("cRegisTyp"));
                    loStatement.bindString(7, detail.getString("cEndOfLfe"));
                    loStatement.bindString(8, detail.getString("cEngineTp"));
                    loStatement.bindString(9, detail.getString("cHotItemx"));
                    loStatement.bindString(10, detail.getString("cRecdStat"));
                    loStatement.bindString(11, detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all Motorcycle Model Price details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertMcModelPrice(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO Mc_Model_Price(sModelIDx, nSelPrice, nLastPrce, nDealrPrc, nMinDownx, sMCCatIDx, dPricexxx, dInsPrice, cRecdStat, dTimeStmp) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1, detail.getString("sModelIDx"));
                    loStatement.bindString(2, detail.getString("nSelPrice"));
                    loStatement.bindString(3, detail.getString("nLastPrce"));
                    loStatement.bindString(4, detail.getString("nDealrPrc"));
                    loStatement.bindString(5, detail.getString("nMinDownx"));
                    loStatement.bindString(6, detail.getString("sMCCatIDx"));
                    loStatement.bindString(7, detail.getString("dPricexxx"));
                    loStatement.bindString(8, detail.getString("dInsPrice"));
                    loStatement.bindString(9, detail.getString("cRecdStat"));
                    loStatement.bindString(10, detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all Occupations/Company Positions details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertOccupation(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO CreditApp_Occupations(sOccptnID, sOccptnNm, cRecdStat, dTimeStmp) " +
                        "VALUES(?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1, detail.getString("sOccptnID"));
                    loStatement.bindString(2, detail.getString("sOccptnNm"));
                    loStatement.bindString(3, detail.getString("cRecdStat"));
                    loStatement.bindString(4, detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all Province details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertProvince(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length() > 0){
                String lsSql = "INSERT OR REPLACE INTO CreditApp_Province(sProvIDxx, sProvName, cRecdStat, dTimeStmp) " +
                        "VALUES (?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1,detail.getString("sProvIDxx"));
                    loStatement.bindString(2,detail.getString("sProvName"));
                    loStatement.bindString(3,detail.getString("cRecdStat"));
                    loStatement.bindString(4,detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all Religion details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertReligion(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO CreditApp_Religion(sRelgnIDx, sRelgnNme, cRecdStat, dTimeStmp) " +
                        "VALUES (?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1, detail.getString("sRelgnIDx"));
                    loStatement.bindString(2, detail.getString("sRelgnNme"));
                    loStatement.bindString(3, detail.getString("cRecdStat"));
                    loStatement.bindString(4, detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all Term Category details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertTermCategory(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO MC_Term_Category(sMCCatIDx, nAcctTerm, nAcctThru, nFactorRt, dPricexxx, dTimeStmp) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1, detail.getString("sMCCatIDx"));
                    loStatement.bindString(2, detail.getString("nAcctTerm"));
                    loStatement.bindString(3, detail.getString("nAcctThru"));
                    loStatement.bindString(4, detail.getString("nFactorRt"));
                    loStatement.bindString(5, detail.getString("dPricexxx"));
                    loStatement.bindString(6, detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }

    /**
     *
     * @param jsonResponse gets the detailed info of branches inside the json array
     *                     and inserts in local database.
     * @return if all Town details are inserted successfully returns true else false
     * to get the error message
     * call the getMessage() method.
     */
    public boolean InsertTown(JSONObject jsonResponse){
        try{
            JSONArray jsonDetail = jsonResponse.getJSONArray("detail");
            if(jsonDetail.length()>0){
                String lsSql = "INSERT OR REPLACE INTO CreditApp_Town(sTownIDxx, sTownName, sZippCode, sProvIDxx, sMuncplCd, cHasRoute, cBlackLst, cRecdStat, dTimeStmp) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                db.getWritableDb().beginTransaction();
                for(int x = 0; x < jsonDetail.length(); x++){
                    JSONObject detail = new JSONObject(jsonDetail.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1,detail.getString("sTownIDxx"));
                    loStatement.bindString(2,detail.getString("sTownName"));
                    loStatement.bindString(3,detail.getString("sZippCode"));
                    loStatement.bindString(4,detail.getString("sProvIDxx"));
                    loStatement.bindString(5,detail.getString("sMuncplCd"));
                    loStatement.bindString(6,detail.getString("cHasRoute"));
                    loStatement.bindString(7,detail.getString("cBlackLst"));
                    loStatement.bindString(8,detail.getString("cRecdStat"));
                    loStatement.bindString(9,detail.getString("dTimeStmp"));
                    loStatement.execute();
                }
                loStatement.close();
                db.getWritableDb().setTransactionSuccessful();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            message = e.getMessage();
            return false;
        } finally {
            db.getWritableDb().endTransaction();
        }
        return true;
    }
}
