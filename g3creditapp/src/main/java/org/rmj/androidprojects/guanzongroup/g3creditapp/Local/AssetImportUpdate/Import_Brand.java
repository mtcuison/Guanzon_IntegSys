package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.CreditAppLocalData;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.CreditAppAPI;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class Import_Brand implements ImportInstance {
    private static final String TAG = Import_Brand.class.getSimpleName();

    private Context mContext;
    private RequestHeaders headers;

    @Override
    public void SendImportRequest(Context context, final OnImportAssetListener listener) {
        this.mContext = context;
        this.headers = new RequestHeaders(mContext);
        ConnectionUtil connectionUtil = new ConnectionUtil(mContext);

        if(connectionUtil.isDeviceConnected()){
            new HttpRequestUtil().sendRequest(CreditAppAPI.URL_IMPORT_BRAND, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    Log.e(TAG, "Headers has been initialized");
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    try{
                        JSONObject params = new JSONObject();
                        params.put("bsearch", true);
                        params.put("descript", "All");
                        Log.e(TAG, "Parameters has been initialized.");
                        return params;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return new JSONObject();
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    Log.e(TAG, "Server response has been received. Waiting to save...");
                    CreditAppLocalData localData = new CreditAppLocalData(mContext);
                    if(localData.InsertBrand(jsonResponse)) {
                        listener.onImportSuccess();
                    } else {
                        listener.onImportFailed(localData.getMessage());
                    }
                }

                @Override
                public void onErrorResponse(String message) {
                    listener.onImportFailed(message);
                }
            });
        } else {
            listener.onImportFailed("Unable to import asset. No internet connection.");
        }
    }
}
