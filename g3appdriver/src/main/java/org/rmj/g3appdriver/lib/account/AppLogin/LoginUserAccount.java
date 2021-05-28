package org.rmj.g3appdriver.lib.account.AppLogin;

import android.content.Context;
import android.util.Log;

import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.HashMap;

public class LoginUserAccount {
    private static final String TAG = LoginUserAccount.class.getSimpleName();


    public void LoginAccount(Context context, onUserLoginListener listener) {

        /*
         * SelectLogin automatically redirects the user
         * to the desinated login API according to the App PRODUCT_ID
         * */
        SelectLogin selectLogin = new SelectLogin();

        /*
         * SharePref in this class returns the PRODUCT_ID
         * which is always set on the start of the App...
         * */
        SharedPref sharedPref = new SharedPref(context);

        /*
         * Setting Headers for secured Account Login...*/
        RequestHeaders requestHeaders = new RequestHeaders(context);

        /*
        * instance refers to the class that's return by the SelectorClass...
         * Telecom/GuanzonApp...
         * Those classes automatically redirect the user to
         * its Login Api Destination...*/
        G3AppsLogin instance = selectLogin.getLoginInstance(sharedPref.ProducID());
        Log.e(TAG, "Login App has been selected");
        instance.loginUser(context, (HashMap) requestHeaders.getHeaders(),
                listener.setParams());

        /*
         * This area of code identifies the value of result...*/
        Log.e(TAG, "Login request has been sent. Waiting for response.");
        if(!instance.getResult().equalsIgnoreCase("success")) {
            listener.onLoginErrorResult(instance.getMessage());
        } else {
            listener.onSuccessLogin();
        }
    }
}
