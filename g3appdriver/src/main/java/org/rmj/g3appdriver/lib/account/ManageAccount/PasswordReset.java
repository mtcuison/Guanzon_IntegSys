package org.rmj.g3appdriver.lib.account.ManageAccount;

import android.content.Context;

import org.json.JSONObject;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class PasswordReset implements G3Accounts {
    private static final String TAG = PasswordReset.class.getSimpleName();
    private static final String PASSWORD_RESET_URL = "https://restgk.guanzongroup.com.ph/security/acctupdate.php";
    private Context mContext;
    private RequestHeaders headers;
    private SharedPref sharedPref;
    private onSendRequestListener mListener;

    @Override
    public void sendRequest(Context context, onSendRequestListener listener) {
        this.mContext = context;
        this.headers = new RequestHeaders(mContext);
        this.sharedPref = new SharedPref(mContext);
        this.mListener = listener;

        new HttpRequestUtil().sendRequest(PASSWORD_RESET_URL, new HttpRequestUtil.onServerResponseListener() {
            @Override
            public HashMap setHeaders() {
                return (HashMap) headers.getHeaders();
            }

            @Override
            public JSONObject setData() {
                return mListener.setData();
            }

            @Override
            public void onResponse(JSONObject jsonResponse) {
                mListener.onSuccessResult(jsonResponse);
            }

            @Override
            public void onErrorResponse(String message) {
                mListener.onErrorResult(message);
            }
        });
    }
}
