package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.CreditAppAPI;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class CreditRequestApplication {
    private static final String TAG = CreditRequestApplication.class.getSimpleName();
    private Context mContext;
    private RequestHeaders headers;
    private String TransNox = "";

    public CreditRequestApplication(Context context){
        this.mContext = context;
    }

    public interface OnRequestCreditOnlineApplication{
        void onSuccessRequestResult();
        void onFailedRequestResult(String message);
    }

    public void requestApplications(String TransNox, OnRequestCreditOnlineApplication listener) {
        this.TransNox = TransNox;
        this.headers = new RequestHeaders(mContext);
        if (new ConnectionUtil(mContext).isDeviceConnected()) {
            new HttpRequestUtil().sendRequest(CreditAppAPI.URL_REQUEST_ONLINE_APPLICATIONS, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    try{
                        JSONObject loJson = new JSONObject();
                        loJson.put("refernox", TransNox);
                        return loJson;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    UpdateLocalData(jsonResponse);
                    listener.onSuccessRequestResult();
                }

                @Override
                public void onErrorResponse(String message) {
                    listener.onFailedRequestResult(message);
                }
            });
        } else {
            Log.e(TAG, "Unable to send credit application. No Internet.");
            listener.onFailedRequestResult("Unable to save online credit application. No internet connection.");
        }
    }

    private void UpdateLocalData(JSONObject jsonData){
        AppData db = AppData.getInstance(mContext);
        try {
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("UPDATE Credit_Online_Application SET sQMatchNo = '"+jsonData.getString("sQMatchNo")+"'," +
                    "sGOCASNox = '"+jsonData.getString("sGOCASNox")+"', " +
                    "cUnitAppl = '"+jsonData.getString("cUnitAppl")+"', " +
                    "sCreatedx = '"+jsonData.getString("sCreatedx")+"', " +
                    "sVerified = '"+jsonData.getString("sVerified")+"', " +
                    "dVerified = '"+jsonData.getString("sVerified")+"', " +
                    "cTranStat = '"+jsonData.getString("cTranStat")+"' " +
                    "WHERE sTransNox = '"+TransNox+"'");
            Log.e(TAG, "Data of transaction no :" + TransNox + " has been updated.");
            db.getWritableDb().setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().setTransactionSuccessful();
        }
    }
}
