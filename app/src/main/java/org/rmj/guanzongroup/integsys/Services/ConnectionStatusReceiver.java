package org.rmj.guanzongroup.integsys.Services;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.CreditAppNotice;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditOnlineApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditSendApplication;
import org.rmj.g3appdriver.dev.AppData;
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

public class ConnectionStatusReceiver extends BroadcastReceiver {
    private static final String TAG = ConnectionStatusReceiver.class.getSimpleName();

    private Context mContext;
    private String lsMessage;
    private CreditSendApplication loApplication;
    private String[] laTransNo;
    private AppData db;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        loApplication = new CreditSendApplication(context);
        laTransNo = loApplication.getTransNox();
        mContext = context;
        Log.e(TAG, "Internet connection status");
        if(new ConnectionUtil(context).isDeviceConnected()){
            lsMessage = "Your device has reconnected. Updating local data.";
            new CreditAppNotice(context, lsMessage, CreditAppNotice.CHANNEL_ID.LocalReceiverStatus).showNotification();
            DoBackgroundProcess loProcess = new DoBackgroundProcess();
            loProcess.execute(laTransNo.length);
            loProcess.setLoListener(message -> loProcess.cancel(true));
        }
    }

    @SuppressLint("StaticFieldLeak")
    class DoBackgroundProcess extends AsyncTask<Integer, Integer, String>{

        OnBackgroundDownloadFinishedListener loListener;

        void setLoListener(OnBackgroundDownloadFinishedListener listener){
            this.loListener = listener;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            try {
                boolean[] sentStat = new boolean[laTransNo.length];
                if (laTransNo.length > 0) {
                    for (int x = 0; x < laTransNo.length; x++) {
                        int finalX = x;
                        new CreditOnlineApplication(mContext).saveApplication(laTransNo[x], new CreditOnlineApplication.OnSaveCreditOnlineApplication() {
                            @Override
                            public void onSaveSuccessResult(String ClientName) {
                                Log.e(TAG, "Application No. : " + laTransNo[finalX] + " has been uploaded successfully.");
                                new CreditAppNotice(mContext, ClientName + "s' credit application has been sent.", CreditAppNotice.CHANNEL_ID.IndividualMessage).showNotification();
                                sentStat[finalX] = true;
                            }

                            @Override
                            public void onSaveFailedResult(String message) {
                                lsMessage = "Application No. : " + laTransNo[finalX] + " has failed to send. " + message;
                                new CreditAppNotice(mContext, lsMessage, CreditAppNotice.CHANNEL_ID.IndividualMessage).showNotification();
                                sentStat[finalX] = false;
                            }
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e(TAG, "All pending applications has been sent.");
                    lsMessage = "All pending applications has been sent.";
                    for (boolean b : sentStat) {
                        if (!b) {
                            lsMessage = "Some applications failed to send.";
                        }
                    }
                    new CreditAppNotice(mContext, lsMessage, CreditAppNotice.CHANNEL_ID.LocalReceiverStatus).showNotification();
                    loListener.OnFinished(lsMessage);
                } else {
                    Log.e(TAG, "No pending applications.");
                    lsMessage = "No pending applications.";
                    updatePromoEntries();
                    new CreditAppNotice(mContext, lsMessage, CreditAppNotice.CHANNEL_ID.LocalReceiverStatus).showNotification();
                    loListener.OnFinished(lsMessage);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return "Process has ended.";
        }
    }

    interface OnBackgroundDownloadFinishedListener{
        void OnFinished(String message);
    }



    private void updatePromoEntries() throws Exception {
        PromoLocalData promoLocalData = new PromoLocalData(mContext);
        List<PromoDetail> entries = getList_PromoUrls();
        if(entries.size() > 0){
            for(int x = 0; x < entries.size(); x++){
                String response = WebClient.httpsPostJSon(entries.get(x).getEntryLink(), "", (HashMap<String, String>) new RequestHeaders(mContext).getHeaders());
                JSONObject loResponse = new JSONObject(response);
                String result = loResponse.getString("result");
                if(result.equalsIgnoreCase("success")){
                    Log.e(TAG, "Promo entry of : " + entries.get(x).getsClientNm() + "has been sent to server.");
                    entries.get(x).setcSendStat('1');
                    entries.get(x).setsTimeStmp(getDateTransact());
                    promoLocalData.updatePromoDetail(entries.get(x));
                    Thread.sleep(1000);
                } else {
                    Thread.sleep(1000);
                }
            }
        }
    }

    private List<PromoDetail> getList_PromoUrls(){
        List<PromoDetail> PromoUrl = new ArrayList<>();
        String[] args = {"0"};
        db = AppData.getInstance(mContext);
        Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM PromoLocal_Detail WHERE cSendStat = ?", args);
        if(cursor.getCount() > 0){
            Log.e(TAG, "Updating promo entries...");
            cursor.moveToFirst();
            for(int x = 0; x < cursor.getCount(); x++) {
                PromoDetail promoDetail = new PromoDetail();
                String BranchCd = cursor.getString(cursor.getColumnIndex("sBranchCd"));
                String Transact = cursor.getString(cursor.getColumnIndex("dTransact"));
                String DocTypex = cursor.getString(cursor.getColumnIndex("sDocTypex"));
                String DocNoxxx = cursor.getString(cursor.getColumnIndex("sDocNoxxx"));
                String MobileNo = cursor.getString(cursor.getColumnIndex("sMobileNo"));
                String ClientNm = cursor.getString(cursor.getColumnIndex("sClientNm"));
                promoDetail.setsBranchCd(BranchCd);
                promoDetail.setdTransact(Transact);
                promoDetail.setsDocTypex(DocTypex);
                promoDetail.setsDocNoxxx(DocNoxxx);
                promoDetail.setsMobileNo(MobileNo);
                promoDetail.setsClientNm(ClientNm);
                PromoUrl.add(promoDetail);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return PromoUrl;
    }

    private String getDateTransact(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }
}
