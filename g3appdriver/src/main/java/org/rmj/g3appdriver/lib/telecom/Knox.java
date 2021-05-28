package org.rmj.g3appdriver.lib.telecom;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class Knox {
    private static final String TAG = Knox.class.getSimpleName();
    private static final String KNOX_URL = "https://restgk.guanzongroup.com.ph/samsung/knox.php";
    private ConnectionUtil connectionUtil;
    private RequestHeaders headers;
    private Context mContext;
    private SharedPref sharedPref;
    private onKnoxRequestListener onKnoxRequestListener;

    public void sendKnoxRequest(Context context, final String DeviceImei, final String Remarks, onKnoxRequestListener listener){
        this.mContext = context;
        this.onKnoxRequestListener = listener;
        connectionUtil = new ConnectionUtil(mContext);
        headers = new RequestHeaders(mContext);
        sharedPref = new SharedPref(mContext);
        if(connectionUtil.isDeviceConnected()) {
            new HttpRequestUtil().sendRequest(KNOX_URL, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("deviceUid", DeviceImei);
                        params.put("approveComment", Remarks);
                        Log.e(TAG, "Knox parameters has been created." + params.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    Log.e(TAG, "Server response has been receive. Result : SUCCESS");
                    onKnoxRequestListener.onSuccessResult("Knox registration has finish successfully.");
                }

                @Override
                public void onErrorResponse(String message) {
                    Log.e(TAG, "Server response has been receive with errors. Result : ERROR ");
                    Log.e(TAG, "Knox response error message : " + message);
                    onKnoxRequestListener.onErrorResult(message);
                }
            });
        } else {
            onKnoxRequestListener.onErrorResult("Unable to reach our server. Please make sure you are connected to the internet.");
        }
    }

    public interface onKnoxRequestListener{
        void onSuccessResult(String Result);
        void onErrorResult(String errorMessage);
    }
}
