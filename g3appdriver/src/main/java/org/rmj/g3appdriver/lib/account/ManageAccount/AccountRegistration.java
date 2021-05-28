package org.rmj.g3appdriver.lib.account.ManageAccount;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class AccountRegistration implements G3Accounts {
    private static final String TAG = AccountRegistration.class.getSimpleName();

    private static final String REGISTRATION_URL = "https://restgk.guanzongroup.com.ph/security/signup.php";
    private Context mContext;
    private RequestHeaders headers;
    private SharedPref sharedPref;
    private onSendRequestListener mListener;
    private ConnectionUtil connectionUtil;

    /**
     * sendRequest function sets the data to the HttpRequestUtil*/
    @Override
    public void sendRequest(Context context, onSendRequestListener listener) {
        this.mContext = context;
        this.headers = new RequestHeaders(mContext);
        this.sharedPref = new SharedPref(mContext);
        this.connectionUtil = new ConnectionUtil(mContext);
        this.mListener = listener;

        if(connectionUtil.isDeviceConnected()) {
            new HttpRequestUtil().sendRequest(REGISTRATION_URL, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    Log.e(TAG, "Account Registration headers has been set.");

                    /**
                     * All Api and HttpRequest Class always requires
                     * API HEADERS for server Authorization...*/
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    Log.e(TAG, "Account Registration parameters has been set.");

                    /**
                     * JSONObject data comes from the Activity that is being pass through
                     * onSendRequestListener.setData(), in this phase function can now be
                     * easily manipulation on Main Classes of account registration Activity*/

                    /**
                     * Account registration parameters are compose of the
                     * following KEY VALUES :
                     * KEY = "name" //this must containg full name of the user. (Last Name, First Name Middle Name)
                     * KEY = "mail" //an active email of the user is a must to perform sending confirmation email.
                     * KEY = "pswd" //Account password
                     * KEY = "mobile", //an active mobile number is required for API Authorization.
                     * */
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
             * Account Registration Main class Activity*/
            mListener.onErrorResult("Account registration failed. Unable to connect.");
        }
    }
}
