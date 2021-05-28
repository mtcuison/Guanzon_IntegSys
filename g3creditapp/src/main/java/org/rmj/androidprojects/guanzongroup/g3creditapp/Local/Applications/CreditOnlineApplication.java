package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.CreditAppAPI;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;
import org.rmj.gocas.base.GOCASApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CreditOnlineApplication {
    private static final String TAG = CreditOnlineApplication.class.getSimpleName();
    private Context mContext;
    private RequestHeaders headers;
    private AppData db;
    private String sClientNm;

    public CreditOnlineApplication(Context context){
        this.mContext = context;
        this.db = AppData.getInstance(mContext);
    }

    public interface OnSaveCreditOnlineApplication{
        void onSaveSuccessResult(String ClientName);
        void onSaveFailedResult(String message);
    }

    /**
     *
     * @param transNox
     * @param listener
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveApplication(String transNox, OnSaveCreditOnlineApplication listener) {
        this.headers = new RequestHeaders(mContext);
        if (new ConnectionUtil(mContext).isDeviceConnected()) {
            new HttpRequestUtil().sendRequest(CreditAppAPI.URL_SUBMIT_ONLINE_APPLICATION, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    return getData(transNox);
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    UpdateLocalData(jsonResponse, transNox);
                    listener.onSaveSuccessResult(sClientNm);
                }

                @Override
                public void onErrorResponse(String message) {
                    if(isJsonValid(message)){
                        deleteGoCasInfo(transNox);
                        listener.onSaveFailedResult(getJsonMessage(message));
                    } else {
                        listener.onSaveFailedResult(message);
                    }
                }
            });
        } else {
            Log.e(TAG, "Unable to send credit application. No Internet.");
            listener.onSaveFailedResult("Application has been temporarily save to local. To send application online please try to connect your mobile/tablet to internet. \n\nNote: Applications stored in local will be send automatically if device is online. Open 'Credit Application List' to check application status");
        }
    }

    private boolean isJsonValid(String Json){
        try{
            JSONObject loJson = new JSONObject(Json);
            return true;
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception error : " + e.getMessage());
            return false;
        }
    }

    private String getJsonMessage(String Json){
        try{
            JSONObject loJson = new JSONObject(Json);
            return loJson.getString("message");
        } catch (JSONException e){
            Log.e(TAG, "Json Exception error : " + e.getMessage());
            return "";
        }
    }

    private JSONObject getData(String TransNox){
        try{
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM Credit_Online_Application WHERE sTransNox = '"+TransNox+"'", null);
            if(cursor.getCount() >0) {
                cursor.moveToFirst();
                sClientNm = cursor.getString(cursor.getColumnIndex("sClientNm"));
                String data = cursor.getString(cursor.getColumnIndex("sDetlInfo"));
                GOCASApplication loGoCas = new GOCASApplication();
                loGoCas.setData(data);
                loGoCas.PurchaseInfo().setPreferedBranch(cursor.getString(cursor.getColumnIndex("sBranchCd")));
                JSONObject params = new JSONObject(loGoCas.toJSONString());
                params.put("dCreatedx", cursor.getString(cursor.getColumnIndex("dCreatedx")));
                cursor.close();
                return params;
            } else {
                cursor.close();
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void UpdateLocalData(JSONObject data, String TransNox){
        try{
            String[] args = new String[]{data.getString("sTransNox"),
                    getDateReceived(),
                    getDateReceived(),
                    "1",
                    getDateReceived(),
                    TransNox};
            db.getWritableDb().beginTransaction();
            db.getWritableDb().execSQL("UPDATE Credit_Online_Application SET sTransNox = ?, dReceived = ?, dCreatedx = ?, cSendStat = ?, dModified = ? " +
                    "WHERE sTransNox = ?", args);
            db.getWritableDb().setTransactionSuccessful();
            Log.e(TAG, "Credit application local info has been updated.");
        } catch (SQLiteException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.getWritableDb().endTransaction();
        }
    }

    private String getDateReceived(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    private void deleteGoCasInfo(String TransNox){
        String[] bindArgs = {TransNox};
        db.getWritableDb().beginTransaction();
        db.getWritableDb().execSQL("UPDATE Credit_Online_Application SET cApplStat = '2' WHERE sTransNox = ?", bindArgs);
        db.getWritableDb().setTransactionSuccessful();
        db.getWritableDb().endTransaction();
    }
}
