package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.CreditAppNotice;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.CreditAppLocalData;
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

public class CreditSendApplication {
    private static final String TAG = CreditSendApplication.class.getSimpleName();

    private Context mContext;
    private RequestHeaders headers;
    private AppData db;
    private String lsClientNm;

    public CreditSendApplication(Context context){
        this.mContext = context;
        this.headers = new RequestHeaders(mContext);
        this.db = AppData.getInstance(mContext);
        new CreditAppLocalData(mContext);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("Recycle")
    public void sendApplications(){
        Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM Credit_OnLine_Application WHERE cSendStat = 0 AND cApplStat = '1'", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            String[] lsTransaNo = new String[cursor.getCount()];
            for(int x = 0; x < cursor.getCount(); x++){
                lsTransaNo[x] = cursor.getString(cursor.getColumnIndex("sTransNox"));
                cursor.moveToNext();
            }
            int x = 0;
            int count = cursor.getCount();
            while(x < count){

                saveApplication(lsTransaNo[x]);
                x++;

                try{
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveApplication(String TransNox) {
        this.headers = new RequestHeaders(mContext);
        if (new ConnectionUtil(mContext).isDeviceConnected()) {
            new HttpRequestUtil().sendRequest(CreditAppAPI.URL_SUBMIT_ONLINE_APPLICATION, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    return getData(TransNox);
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    UpdateLocalData(jsonResponse, TransNox);
                }

                @Override
                public void onErrorResponse(String message) {
                    Log.e(TAG, "Unable to save transaction online.");
                }
            });
        } else {
            Log.e(TAG, "Unable to send credit application. No Internet.");
        }
    }

    private JSONObject getData(String TransNox){
        try{
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM Credit_Online_Application WHERE sTransNox = '"+TransNox+"'", null);
            if(cursor.getCount() >0) {
                cursor.moveToFirst();
                String data = cursor.getString(cursor.getColumnIndex("sDetlInfo"));
                GOCASApplication loGoCas = new GOCASApplication();
                loGoCas.setData(data);
                JSONObject params = new JSONObject(loGoCas.toJSONString());
                lsClientNm = loGoCas.ApplicantInfo().getClientName();
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
            db.getWritableDb().beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put("sTransNox", data.getString("sTransNox"));
            cv.put("dReceived", getDateReceived());
            cv.put("dCreatedx", getDateReceived());
            cv.put("cSendStat", "1");
            cv.put("dModified", getDateReceived());
            String[] args = new String[]{TransNox};
            db.getWritableDb().update("Credit_Online_Application", cv, "sTransNox = ?", args);
            Log.e(TAG, "Credit application local info has been updated.");
            String lsMessage = "The Credit Application of " + lsClientNm + " has been sent.";
            new CreditAppNotice(mContext, lsMessage, CreditAppNotice.CHANNEL_ID.IndividualMessage).showNotification();
            db.getWritableDb().setTransactionSuccessful();
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

    public String[] getTransNox(){
        try {
            String[] laTransNox;
            String[] args = {"0"};
            Cursor loCursor = db.getReadableDb().rawQuery("SELECT * FROM Credit_Online_Application " +
                    "WHERE cSendStat = ?", args);
            laTransNox = new String[loCursor.getCount()];
            if (loCursor.getCount() > 0) {
                loCursor.moveToFirst();
                for (int x = 0; x < loCursor.getCount(); x++) {
                    laTransNox[x] = loCursor.getString(loCursor.getColumnIndex("sTransNox"));
                    loCursor.moveToNext();
                }
            }
            loCursor.close();
            return laTransNox;
        } catch (Exception e){
            e.printStackTrace();
        }
        return new String[0];
    }
}
