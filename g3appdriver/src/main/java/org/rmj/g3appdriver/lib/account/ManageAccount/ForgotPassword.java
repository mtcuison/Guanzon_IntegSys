package org.rmj.g3appdriver.lib.account.ManageAccount;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class ForgotPassword implements G3Accounts {
    private static final String TAG = ForgotPassword.class.getSimpleName();
    private static final String FORGOT_PASSWORD_URL = "https://restgk.guanzongroup.com.ph/security/forgotpswd.php";
    private Context mContext;
    private ConnectionUtil connectionUtil;
    private RequestHeaders headers;
    private SharedPref sharedPref;
    private onSendRequestListener mListener;

    @Override
    public void sendRequest(Context context, onSendRequestListener listener) {
        this.mContext = context;
        this.connectionUtil = new ConnectionUtil(mContext);
        this.headers = new RequestHeaders(mContext);
        this.sharedPref = new SharedPref(mContext);
        this.mListener = listener;

        /**
         * ask/check connection first before proceeding...*/
        if(connectionUtil.isDeviceConnected()) {
            new HttpRequestUtil().sendRequest(FORGOT_PASSWORD_URL, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    Log.e(TAG, "Forgot Password headers has been set.");

                    /**
                     * All Api and HttpRequest Class always requires
                     * API HEADERS for server Authorization...*/
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    Log.e(TAG, "Forgot Password parameters has been set");

                    /**
                     * JSONObject data comes from the Activity that is being pass through
                     * onSendRequestListener.setData(), in this phase function can now be
                     * easily manipulation on Main Classes of account registration Activity*/
                    return mListener.setData();
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {

                    /**
                     * IF HttpRequestUtil receives a response from the server.
                     * a JSONObject response is being set to the onServerResponseListener.onResponse
                     * to retrieve the Data easily...*/

                    /**
                     * NOTE:
                     * IF THE SERVER DOES NOT RETURN A SUCCESSFUL RESULT JSONOBJECT RESPONSE IS NULL
                     * IT MIGHT CAUSE ERRORS TO SOME FUNCTIONS IF NOT CATCH...*/
                    mListener.onSuccessResult(jsonResponse);
                }

                @Override
                public void onErrorResponse(String message) {
                    mListener.onErrorResult(message);
                }
            });
        } else {
            /**
             * Internet Connection might not be available at the current device
             * This message is return to the onErrorResult statement on the
             * Forgot Password Main class Activity*/
            mListener.onErrorResult("Unable to connect. ");
        }
    }
}
