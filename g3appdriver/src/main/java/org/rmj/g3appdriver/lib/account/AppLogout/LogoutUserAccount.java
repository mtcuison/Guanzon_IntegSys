package org.rmj.g3appdriver.lib.account.AppLogout;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class LogoutUserAccount {
    private static final String TAG = LogoutUserAccount.class.getSimpleName();
    private String LOGOUT_URL = "https://restgk.guanzongroup.com.ph/security/logout.php";
    private onLogoutListener mListener;
    private SharedPref sharedPref;
    private ConnectionUtil connectionUtil;
    private RequestHeaders header;

    public void logout(final Context context, onLogoutListener listener) {
        this.mListener = listener;
        setupJavaClasses(context);
        new HttpRequestUtil().sendRequest(LOGOUT_URL, new HttpRequestUtil.onServerResponseListener() {
            @Override
            public HashMap setHeaders() {
                return (HashMap) header.getHeaders();
            }

            @Override
            public JSONObject setData() {
                return mListener.setData();
            }

            @Override
            public void onResponse(JSONObject jsonResponse) {
                Log.e(TAG, "User account has been logout successfully");
                mListener.onSuccessResponse();
            }

            @Override
            public void onErrorResponse(String message) {
                mListener.onErrorResponse(message);
            }
        });
    }

    private void setupJavaClasses(Context context){
        this.sharedPref = new SharedPref(context);
        header = new RequestHeaders(context);
    }

    public interface onLogoutListener {
        JSONObject setData();
        void onSuccessResponse();
        void onErrorResponse(String Message);
    }

}
