package org.rmj.guanzongroup.bullseye.LocalData;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.BullsEyeAPI;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class SaveMonitorData {
    private static final String TAG = SaveMonitorData.class.getSimpleName();

    private AppData db;
    private ConnectionUtil conn;
    private RequestHeaders headers;

    private String[] arr_areaCd;
    private boolean[] isDownloaded;

    public SaveMonitorData(Context context){
        this.db = AppData.getInstance(context);
        this.conn = new ConnectionUtil(context);
        this.headers = new RequestHeaders(context);
    }

    public void DownloadData(){
        DownloadAreaPerformance(new OnDownloadListener() {
            @Override
            public void OnSuccess() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new DownloadDataTask().execute();
            }

            @Override
            public void OnError() {

            }
        });
    }

    class DownloadDataTask extends AsyncTask<Integer, Integer, String>{
        String result;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for(int x = 0;  x < arr_areaCd.length; x++){
                isDownloaded = new boolean[arr_areaCd.length];
                isDownloaded[x] = false;
                final int finalX = x;
                DownloadBranchPerformance(arr_areaCd[x], new OnDownloadListener() {
                    @Override
                    public void OnSuccess() {
                        isDownloaded[finalX] = true;
                        Log.e(TAG, "Performance data of Area " + arr_areaCd[finalX] + " has been downloaded successfully.");
                    }

                    @Override
                    public void OnError() {
                        isDownloaded[finalX] = false;
                        Log.e(TAG, "Performance data of Area " + arr_areaCd[finalX] + " has failed to download.");
                    }
                });
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for(int i = 0;  i < isDownloaded.length; i++){
                if(!isDownloaded[i]){
                    result = "error";
                    break;
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.cancel(true);
        }
    }

    public void DownloadAreaPerformance(final OnDownloadListener listener){
        final OnDownloadListener loListener = listener;
        if(conn.isDeviceConnected()){
            new HttpRequestUtil().sendRequest(BullsEyeAPI.IMPORT_AREA_PERFORMANCE, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    JSONObject loJson = new JSONObject();
                    try{
                        loJson.put("period", "201911");
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    return loJson;
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    saveAreaMonitorData(jsonResponse, loListener);
                }

                @Override
                public void onErrorResponse(String message) {
                    loListener.OnError();
                }
            });
        }
    }

    private void DownloadBranchPerformance(final String AreaCode, final OnDownloadListener listener){
        if(conn.isDeviceConnected()){
            new HttpRequestUtil().sendRequest(BullsEyeAPI.IMPORT_BRANCH_PERFORMANCE, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    JSONObject loJson = new JSONObject();
                    try{
                        loJson.put("period", "201911");
                        loJson.put("areacd", AreaCode);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    return loJson;
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    saveBranchMonitorData(jsonResponse);
                    listener.OnSuccess();
                }

                @Override
                public void onErrorResponse(String message) {
                    listener.OnError();
                }
            });
        }
    }

    private void saveAreaMonitorData(JSONObject jsonResponse, OnDownloadListener listener){
        try{
            JSONArray laJson = jsonResponse.getJSONArray("detail");
            if(laJson.length() > 0){
                arr_areaCd = new String[laJson.length()];
                String sql = "INSERT OR REPLACE INTO MC_Area_Performance(" +
                        "sAreaCode, " +
                        "sAreaDesc, " +
                        "nMCGoalxx, " +
                        "nSPGoalxx, " +
                        "nJOGoalxx, " +
                        "nLRGoalxx, " +
                        "nMCActual, " +
                        "nSPActual, " +
                        "nLRActual)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                db.getWritableDb().beginTransaction();
                SQLiteStatement loSql = db.getWritableDb().compileStatement(sql);
                for(int x = 0; x < laJson.length(); x++){
                    JSONObject loJson = new JSONObject(laJson.getString(x));
                    arr_areaCd[x] = loJson.getString("sAreaCode");
                    loSql.clearBindings();
                    loSql.bindString(1, loJson.getString("sAreaCode"));
                    loSql.bindString(2, loJson.getString("sAreaDesc"));
                    loSql.bindString(3, loJson.getString("nMCGoalxx"));
                    loSql.bindString(4, loJson.getString("nSPGoalxx"));
                    loSql.bindString(5, loJson.getString("nJOGoalxx"));
                    loSql.bindString(6, loJson.getString("nLRGoalxx"));
                    loSql.bindString(7, loJson.getString("nMCActual"));
                    loSql.bindString(8, loJson.getString("nSPActual"));
                    loSql.bindString(9, loJson.getString("nLRActual"));
                    loSql.execute();
                }
                db.getWritableDb().setTransactionSuccessful();
                db.getWritableDb().endTransaction();
                listener.OnSuccess();
            }
        } catch (Exception e){
            e.printStackTrace();
            listener.OnError();
        }
    }

    private void saveBranchMonitorData(JSONObject jsonResponse){
        try{
            JSONArray laJson = jsonResponse.getJSONArray("detail");
            if(laJson.length() > 0){
                String sql = "INSERT OR REPLACE INTO MC_Branch_Performance(" +
                        "sBranchCd, " +
                        "sBranchNm, " +
                        "nMCGoalxx, " +
                        "nSPGoalxx, " +
                        "nJOGoalxx, " +
                        "nLRGoalxx, " +
                        "nMCActual, " +
                        "nSPActual, " +
                        "nLRActual)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                db.getWritableDb().beginTransaction();
                SQLiteStatement loSql = db.getWritableDb().compileStatement(sql);
                for(int x = 0; x < laJson.length(); x++){
                    JSONObject loJson = new JSONObject(laJson.getString(x));
                    loSql.clearBindings();
                    loSql.bindString(1, loJson.getString("sBranchCd"));
                    loSql.bindString(2, loJson.getString("sBranchNm"));
                    loSql.bindString(3, loJson.getString("nMCGoalxx"));
                    loSql.bindString(4, loJson.getString("nSPGoalxx"));
                    loSql.bindString(5, loJson.getString("nJOGoalxx"));
                    loSql.bindString(6, loJson.getString("nLRGoalxx"));
                    loSql.bindString(7, loJson.getString("nMCActual"));
                    loSql.bindString(8, loJson.getString("nSPActual"));
                    loSql.bindString(9, loJson.getString("nLRActual"));
                    loSql.execute();
                }
                db.getWritableDb().setTransactionSuccessful();
                db.getWritableDb().endTransaction();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface OnDownloadListener{
        void OnSuccess();
        void OnError();
    }
}
