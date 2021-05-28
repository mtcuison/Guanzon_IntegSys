package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.CreditAppAPI;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class CreditImportApplication {
    private static final String TAG = CreditOnlineApplication.class.getSimpleName();
    private Context mContext;
    private RequestHeaders headers;
    private AppData db;
    private OnImportCreditApplication poListener;

    public CreditImportApplication(Context context){
        this.mContext = context;
        this.db = AppData.getInstance(mContext);
    }

    public interface OnImportCreditApplication{
        void onSuccessImportResult();
        void onFailedImportResult(String errorMessage);
    }

    public void importApplications(OnImportCreditApplication listener){
        this.headers = new RequestHeaders(mContext);
        this.poListener = listener;
        if(new ConnectionUtil(mContext).isDeviceConnected()){
            new HttpRequestUtil().sendRequest(CreditAppAPI.URL_IMPORT_ONLINE_APPLICATIONS, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    try{
                        JSONObject params = new JSONObject();
                        params.put("sUserIDxx", db.getUserID());
                        return params;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return new JSONObject();
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    saveToLocal(jsonResponse);
                }

                @Override
                public void onErrorResponse(String message) {
                    poListener.onFailedImportResult(message);
                }
            });
        }
    }

    private void saveToLocal(JSONObject jsonData){
        try{
            JSONArray laJson = jsonData.getJSONArray("detail");
            if(laJson.length() > 0) {
                db.getWritableDb().beginTransaction();
                db.getWritableDb().execSQL("DELETE FROM Credit_Online_Application WHERE cSendStat = '1'");
                String lsSql = "INSERT OR REPLACE INTO Credit_Online_Application(sTransNox, " +
                        " sBranchCd, " +
                        " dTransact, " +
                        " sClientNm, " +
                        " sGOCASNox, " +
                        " cUnitAppl, " +
                        " sSourceCD, " +
                        " sDetlInfo, " +
                        " sQMatchNo, " +
                        " cWithCIxx, " +
                        " nDownPaym, " +
                        " sRemarksx, " +
                        " sCreatedx, " +
                        " dCreatedx, " +
                        " cSendStat, " +
                        " sVerified, " +
                        " dVerified, " +
                        " cTranStat, " +
                        " cDivision, " +
                        " dReceived, " +
                        " cApplStat) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                SQLiteStatement loStatement = db.getWritableDb().compileStatement(lsSql);
                for(int x = 0; x < laJson.length(); x++){
                    JSONObject loJson = new JSONObject(laJson.getString(x));
                    loStatement.clearBindings();
                    loStatement.bindString(1, loJson.getString("sTransNox"));
                    loStatement.bindString(2, loJson.getString("sBranchCd"));
                    loStatement.bindString(3, loJson.getString("dTransact"));
                    loStatement.bindString(4, loJson.getString("sClientNm"));
                    loStatement.bindString(5, loJson.getString("sGOCASNox"));
                    loStatement.bindString(6, loJson.getString("cUnitAppl"));
                    loStatement.bindString(7, loJson.getString("sSourceCD"));
                    loStatement.bindString(8, loJson.getString("sDetlInfo"));
                    loStatement.bindString(9, loJson.getString("sQMatchNo"));
                    loStatement.bindString(10, loJson.getString("cWithCIxx"));
                    loStatement.bindString(11, loJson.getString("nDownPaym"));
                    loStatement.bindString(12, loJson.getString("sRemarksx"));
                    loStatement.bindString(13, loJson.getString("sCreatedx"));
                    loStatement.bindString(14, loJson.getString("dCreatedx"));
                    loStatement.bindString(15, "1");
                    loStatement.bindString(16, loJson.getString("sVerified"));
                    loStatement.bindString(17, loJson.getString("dVerified"));
                    loStatement.bindString(18, loJson.getString("cTranStat"));
                    loStatement.bindString(19, loJson.getString("cDivision"));
                    loStatement.bindString(20, loJson.getString("dReceived"));
                    loStatement.bindString(21, "1");
                    loStatement.execute();
                }
                Log.e(TAG, "Applications has been imported successfully.");
                db.getWritableDb().setTransactionSuccessful();
            }
            poListener.onSuccessImportResult();
        } catch (JSONException e){
            e.printStackTrace();
            poListener.onFailedImportResult(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            poListener.onFailedImportResult(e.getMessage());
        } finally {
            db.getWritableDb().endTransaction();
        }
    }
}
