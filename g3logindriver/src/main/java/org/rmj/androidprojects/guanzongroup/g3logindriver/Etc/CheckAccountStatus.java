package org.rmj.androidprojects.guanzongroup.g3logindriver.Etc;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3logindriver.Dialog.Dialog_AccountActivation;
import org.rmj.g3appdriver.etc.SharedPref;

public class CheckAccountStatus {
    private Context mContext;
    private JSONObject mResponse;
    private JSONObject loginParams;

    public CheckAccountStatus(Context context, String Response) throws JSONException {
        this.mContext = context;
        this.mResponse = new JSONObject(Response);
        this.loginParams = loginParams;
    }

    public void checkAccountResult() throws JSONException {
        JSONObject error = mResponse.getJSONObject("error");
        if(error.getString("code").equalsIgnoreCase("40003")){
            new SharedPref(mContext).setTemp_PIN(mResponse.getString("otp"));
            new Dialog_AccountActivation(mContext, mResponse.getString("mobile")).showDialog(mResponse.getString("verify"));
        } else {
            Toast toast = Toast.makeText(mContext, error.getString("message"), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
