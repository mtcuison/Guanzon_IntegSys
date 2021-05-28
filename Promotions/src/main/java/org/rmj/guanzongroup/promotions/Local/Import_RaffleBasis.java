package org.rmj.guanzongroup.promotions.Local;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.ImportInstance;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.OnImportAssetListener;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;
import org.rmj.guanzongroup.promotions.Model.DocumentDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import_RaffleBasis implements ImportInstance {
    public static final String TAG = Import_RaffleBasis.class.getSimpleName();
    private String lsUrl = "https://restgk.guanzongroup.com.ph/promo/param/download_raffle_entry_basis_all.php";

    private Context mContext;
    private RequestHeaders headers;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void SendImportRequest(Context context, final OnImportAssetListener listener) {
        this.mContext = context;
        this.headers = new RequestHeaders(mContext);
        ConnectionUtil connectionUtil = new ConnectionUtil(mContext);

        if(connectionUtil.isDeviceConnected()){
            new HttpRequestUtil().sendRequest(lsUrl, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    Log.e(TAG, "Headers has been initialized.");
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    return new JSONObject();
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    String lsResult = null;
                    try {
                            lsResult = jsonResponse.getString("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            JSONArray laJson = jsonResponse.getJSONArray("detail");
                            List<DocumentDetail> documentDetails = new ArrayList<>();
                            for(int x = 0; x < laJson.length(); x++) {
                                DocumentDetail documentDetail = new DocumentDetail();
                                JSONObject json = laJson.getJSONObject(x);
                                documentDetail.setsDivision(json.getString("sDivision"));
                                documentDetail.setsTableNme(json.getString("sTableNme"));
                                documentDetail.setsReferCde(json.getString("sReferCde"));
                                documentDetail.setsReferNme(json.getString("sReferNme"));
                                documentDetail.setcRecdStat(json.getString("cRecdStat"));
                                documentDetail.setdModified(json.getString("dModified"));
                                documentDetails.add(documentDetail);
                            }
                            PromoLocalData localData = new PromoLocalData(mContext);
                            if(localData.insertDocumentTypeDetail(documentDetails)) {
                                listener.onImportSuccess();
                            } else {
                                listener.onImportFailed("Something went wrong...");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String message) {
                    Log.e(TAG, "unable to import Assets. Message : " + message);
                    listener.onImportFailed(message);
                }
            });
        } else {
            listener.onImportFailed("Unable to import town asset. No internet connection.");
        }
    }
}
