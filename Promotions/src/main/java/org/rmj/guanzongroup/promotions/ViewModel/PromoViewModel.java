package org.rmj.guanzongroup.promotions.ViewModel;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.promotions.Etc.RaffleEntryCallback;
import org.rmj.guanzongroup.promotions.Local.PromoLocalData;
import org.rmj.guanzongroup.promotions.Model.DocumentDetail;
import org.rmj.guanzongroup.promotions.Model.PromoDetail;
import org.rmj.guanzongroup.promotions.Model.TownInfo;
import org.rmj.guanzongroup.promotions.Model.Voucher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PromoViewModel extends AndroidViewModel {
    public static final String TAG = PromoViewModel.class.getSimpleName();
    private final RequestHeaders headers;
    private final AppData db;
    private final PromoLocalData data;
    private final ConnectionUtil conn;
    private static LiveData<List<DocumentDetail>> docuDetails;
    private int documentPosition = 0;

    public PromoViewModel(@NonNull Application application) {
        super(application);
        headers = new RequestHeaders(application);
        db = AppData.getInstance(application);
        conn = new ConnectionUtil(application);
        data = new PromoLocalData(application);
    }

    public LiveData<String[]> getDocuments(){
        docuDetails = data.getDocumentTypes(getDivision());
        return data.documentTypes(getDivision());
    }

    public LiveData<String[]> getTownInfos(){
        return data.getTownInfoList();
    }

    public LiveData<List<TownInfo>> getTownInfoList(){
        return data.getTownInfo();
    }


    public void setDocumentPosition(int position){
        documentPosition = position;
    }

    public void submit(Voucher voucher, RaffleEntryCallback callBack){
        voucher.setDocumentType(docuDetails.getValue().get(documentPosition).getsReferCde());
        if (voucher.isValidInfo()) {
            String lsUserID = db.getUserID();
            new SubmitRaffleEntryTask(conn, headers, data, callBack, lsUserID).execute(voucher);
        } else {
            callBack.OnFailedEntry(voucher.getMessage());
        }
    }

    String getDivision(){
        String[] lsBranchCode = {db.getBranchCode()};
        db.getReadableDb().beginTransaction();
        Cursor cursor = db.getReadableDb().rawQuery("SELECT cPromoDiv FROM CreditApp_Branches WHERE sBranchCd = ?", lsBranchCode);
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            String lsDivision = cursor.getString(cursor.getColumnIndex("cPromoDiv"));
            db.getReadableDb().setTransactionSuccessful();
            db.getReadableDb().endTransaction();
            return lsDivision;
        }
        db.getReadableDb().setTransactionSuccessful();
        db.getReadableDb().endTransaction();
        return "";
    }

    private static class SubmitRaffleEntryTask extends AsyncTask<Voucher, Void, String>{
        private final RequestHeaders headers;
        private final RaffleEntryCallback callBack;
        private final PromoLocalData db;
        private final ConnectionUtil conn;
        private final String UserID;

        public SubmitRaffleEntryTask(ConnectionUtil conn, RequestHeaders headers, PromoLocalData db, RaffleEntryCallback callBack, String UserId){
            this.conn = conn;
            this.headers = headers;
            this.db = db;
            this.callBack = callBack;
            this.UserID = UserId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBack.OnSendingEntry();
        }

        @Override
        protected String doInBackground(Voucher... vouchers) {
            String response = "";

            PromoDetail detail = new PromoDetail();
            detail.setsBranchCd(vouchers[0].getBranchCodexx());
            detail.setdTransact(getDateTransact());
            detail.setsClientNm(vouchers[0].getCustomerName());
            detail.setsAddresxx(vouchers[0].getCustomerAddx());
            detail.setsTownIDxx(vouchers[0].getCustomerTown());
            detail.setsProvIDxx(vouchers[0].getCustomerProv());
            detail.setsDocTypex(vouchers[0].getDocumentType());
            detail.setsDocNoxxx(vouchers[0].getDocumentNoxx());
            detail.setsMobileNo(vouchers[0].getMobileNumber());
            detail.setcSendStat('0');
            detail.setsTimeStmp(getDateTransact());
            db.insertPromoDetail(detail);

            if(conn.isDeviceConnected()) {
                try {
                    JSONObject loJson = new JSONObject();
                    loJson.put("brc", vouchers[0].getBranchCodexx());
                    loJson.put("typ", vouchers[0].getDocumentType());
                    loJson.put("dte", getDateTransact());
                    loJson.put("nox", vouchers[0].getDocumentNoxx());
                    loJson.put("mob", vouchers[0].getMobileNumber());
                    loJson.put("nme", vouchers[0].getCustomerName());
                    loJson.put("add", vouchers[0].getCustomerAddx());
                    loJson.put("twn", vouchers[0].getCustomerTown());
                    loJson.put("prv", vouchers[0].getCustomerProv());
                    loJson.put("cid", "");
                    loJson.put("div", "");
                    loJson.put("ent", UserID);

                    String lsUrl = "https://restgk.guanzongroup.com.ph/promo/fblike/encodex.php";
                    Log.e(TAG, lsUrl);
                    response = WebClient.httpsPostJSon(lsUrl, loJson.toString(), (HashMap) headers.getHeaders());
                    Log.e(TAG, response);
                    JSONObject loResponse = new JSONObject(response);
                    String lsResult = loResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        detail.setcSendStat('1');
                        detail.setsTimeStmp(getDateTransact());
                        db.updatePromoDetail(detail);
                    } else {
                        JSONObject loErr = loResponse.getJSONObject("error");
                        String lsCode = loErr.getString("code");
                        int nCase = Integer.parseInt(lsCode);
                        for(int i = 99; i < 107; i++){
                            if(nCase == i){
                                detail.setcSendStat('3');
                                detail.setdTransact(getDateTransact());
                                db.updatePromoDetail(detail);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return "{\"result\":\"error\",\"error\":{\"message\":\"Promo entry has been save to local. Please reconnect to send entry.\"}}";
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if (lsResult.equalsIgnoreCase("success")) {
                    callBack.OnSuccessEntry();
                } else {
                    Log.e(TAG, loJson.toString());
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callBack.OnFailedEntry(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        String getDateTransact(){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        }
    }
}
