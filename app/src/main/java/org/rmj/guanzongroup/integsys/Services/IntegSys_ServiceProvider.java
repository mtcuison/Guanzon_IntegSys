package org.rmj.guanzongroup.integsys.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.CreditAppNotice;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditOnlineApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditSendApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.ImportInstance;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_BarangayAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Branches;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Brand;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Category;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_CountryAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_McModel;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_McModel_Price;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Occupation;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_ProvinceAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Religion;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_TermCategoryAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_TownAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.OnImportAssetListener;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.promotions.Local.PromoLocalData;
import org.rmj.guanzongroup.promotions.Model.PromoDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class IntegSys_ServiceProvider extends JobService {
    private static final String TAG = IntegSys_ServiceProvider.class.getSimpleName();

    private AppData db;
    private boolean jobCancelled = false;
    private String lsMessage;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "Job service has been started.");
        db = AppData.getInstance(IntegSys_ServiceProvider.this);
        if(new ConnectionUtil(IntegSys_ServiceProvider.this).isDeviceConnected()) {
            updatePromoEntries();
        }
        doBackgroundWork(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "Job Cancelled.");
        jobCancelled = true;
        return true;
    }

    private void doBackgroundWork(JobParameters params){
        new Thread(() -> {
            CreditSendApplication loApplications = new CreditSendApplication(IntegSys_ServiceProvider.this);
            String[] laTransNox = loApplications.getTransNox();
            int liMaxCount = laTransNox.length;
            if(new ConnectionUtil(IntegSys_ServiceProvider.this).isDeviceConnected()) {
                if(liMaxCount > 0) {
                    lsMessage = "Updating local applications";
                    new CreditAppNotice(IntegSys_ServiceProvider.this, lsMessage, CreditAppNotice.CHANNEL_ID.LocalServiceStatus).showNotification();
                    for (int x = 0; x < liMaxCount; x++) {
                        Log.e(TAG, "current count : " + laTransNox[x]);

                        int finalX = x;
                        new CreditOnlineApplication(IntegSys_ServiceProvider.this)
                                .saveApplication(laTransNox[x], new CreditOnlineApplication.OnSaveCreditOnlineApplication() {
                                    @Override
                                    public void onSaveSuccessResult(String ClientName) {
                                        Log.e(TAG, "Application No. : " + laTransNox[finalX] + " has been uploaded successfully.");
                                        lsMessage = ClientName + "s' credit application has been sent.";
                                        new CreditAppNotice(IntegSys_ServiceProvider.this, lsMessage, CreditAppNotice.CHANNEL_ID.IndividualMessage).showNotification();
                                    }

                                    @Override
                                    public void onSaveFailedResult(String message) {
                                        lsMessage = "Application No. : " + laTransNox[finalX] + " has failed to send. " + message;
                                        new CreditAppNotice(IntegSys_ServiceProvider.this, lsMessage, CreditAppNotice.CHANNEL_ID.IndividualMessage).showNotification();
                                    }
                                });

                        if (jobCancelled) {
                            return;
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lsMessage = "Local applications has been updated.";
                    new CreditAppNotice(IntegSys_ServiceProvider.this, lsMessage, CreditAppNotice.CHANNEL_ID.LocalServiceStatus).showNotification();
                }
                if(!isDataUpdated() && new SessionManager(IntegSys_ServiceProvider.this).isLoggedIn()){
                    updateLocalData(IntegSys_ServiceProvider.this);
                }
                Log.e(TAG, "Job Finished.");
                jobFinished(params, false);
            } else {
                Log.e(TAG, "Job finished. No internet connection");
                jobFinished(params, false);
            }
        }).start();
    }

    private boolean isDataUpdated(){
        String lsDateLog = db.getDateLogin();
        String lsDateUpx = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return lsDateLog.equalsIgnoreCase(lsDateUpx);
    }

    private void updateLocalData(Context context){
        ImportInstance[] importInstances = {
                new Import_BarangayAsset(),
                new Import_TownAsset(),
                new Import_ProvinceAsset(),
                new Import_CountryAsset(),
                new Import_McModel(),
                new Import_McModel_Price(),
                new Import_Brand(),
                new Import_Category(),
                new Import_TermCategoryAsset(),
                new Import_Religion(),
                new Import_Branches(),
                new Import_Occupation()};
        char[] isImported = new char[importInstances.length];
        lsMessage = "Updating MC Branches and Details.";
        new CreditAppNotice(context, lsMessage, CreditAppNotice.CHANNEL_ID.LocalServiceStatus).showNotification();
        for(int x = 0; x < importInstances.length; x++){
            int finalX = x;
            importInstances[x].SendImportRequest(context, new OnImportAssetListener() {
                @Override
                public void onImportSuccess() {
                    isImported[finalX] = '1';
                }

                @Override
                public void onImportFailed(String Message) {
                    isImported[finalX] = '0';
                }
            });

            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (char c : isImported) {
            if (c == '0') {
                lsMessage = "Failed to update some local data.";
                new CreditAppNotice(context, lsMessage, CreditAppNotice.CHANNEL_ID.LocalServiceStatus).showNotification();
                break;
            }
        }
    }

    private void updatePromoEntries() {
        try {
            List<JSONObject> entries = getList_PromoUrls();
            if (entries.size() > 0) {
                new sendRaffleEntryTask().execute(entries);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<JSONObject> getList_PromoUrls(){
        List<JSONObject> PromoUrl = new ArrayList<>();
        try {
            String[] args = {"0"};
            db.getReadableDb().beginTransaction();
            Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM PromoLocal_Detail WHERE cSendStat = ?", args);
            if (cursor.getCount() > 0) {
                Log.e(TAG, "Updating promo entries...");
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++) {
                    JSONObject loJson = new JSONObject();
                    loJson.put("brc", cursor.getString(cursor.getColumnIndex("sBranchCd")));
                    loJson.put("typ", cursor.getString(cursor.getColumnIndex("sDocTypex")));
                    loJson.put("dte", cursor.getString(cursor.getColumnIndex("dTransact")));
                    loJson.put("nox", cursor.getString(cursor.getColumnIndex("sDocNoxxx")));
                    loJson.put("mob", cursor.getString(cursor.getColumnIndex("sMobileNo")));
                    loJson.put("nme", cursor.getString(cursor.getColumnIndex("sClientNm")));
                    loJson.put("add", cursor.getString(cursor.getColumnIndex("sAddressx")));
                    loJson.put("twn", cursor.getString(cursor.getColumnIndex("sTownIDxx")));
                    loJson.put("prv", cursor.getString(cursor.getColumnIndex("sProvIDxx")));
                    loJson.put("cid", "");
                    loJson.put("div", "");
                    loJson.put("ent", db.getUserID());
                    PromoUrl.add(loJson);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
        } catch (Exception e){
            e.printStackTrace();
        }
        return PromoUrl;
    }

    private String getDateTransact(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    private class sendRaffleEntryTask extends AsyncTask<List<JSONObject>, Void, String>{
        private PromoLocalData promoLocalData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            promoLocalData = new PromoLocalData(IntegSys_ServiceProvider.this);
        }

        @Override
        protected String doInBackground(List<JSONObject>... entries) {
            try {
                if (entries[0].size() > 0) {
                    for (int x = 0; x < entries[0].size(); x++) {
                        String lsUrl = "https://restgk.guanzongroup.com.ph/promo/fblike/encodex.php";
                        String response = WebClient.httpsPostJSon(lsUrl, entries[0].get(x).toString(), (HashMap<String, String>) new RequestHeaders(IntegSys_ServiceProvider.this).getHeaders());
                        JSONObject loResponse = new JSONObject(response);
                        PromoDetail detail = new PromoDetail();
                        detail.setsClientNm(entries[0].get(x).getString("nme"));
                        detail.setsDocNoxxx(entries[0].get(x).getString("nox"));
                        detail.setsDocTypex(entries[0].get(x).getString("typ"));
                        detail.setsMobileNo(entries[0].get(x).getString("mov"));
                        detail.setcSendStat('1');
                        detail.setsBranchCd(entries[0].get(x).getString("brc"));
                        detail.setdTransact(getDateTransact());
                        String result = loResponse.getString("result");
                        if (result.equalsIgnoreCase("success")) {
                            Log.e(TAG, "Promo entry of : " + entries[0].get(x).getString("nme") + "has been sent to server.");
                            //new CreditAppNotice(IntegSys_ServiceProvider.this, "Promo entry of : " + entries[0].get(x).getString("nme") + "has been sent to server.", CreditAppNotice.CHANNEL_ID.IndividualMessage).showNotification();
                            detail.setcSendStat('1');
                            detail.setdTransact(getDateTransact());
                            promoLocalData.updatePromoDetail(detail);
                        } else {
                            JSONObject loJson = loResponse.getJSONObject("error");
                            String lsCode = loJson.getString("code");
                            int nCase = Integer.parseInt(lsCode);
                            for (int i = 99; i < 107; i++) {
                                if (nCase == i) {
                                    detail.setcSendStat('3');
                                    detail.setdTransact(getDateTransact());
                                    promoLocalData.updatePromoDetail(detail);
                                    break;
                                }
                            }
                        }
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return "null";
        }
    }
}
