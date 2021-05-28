package org.rmj.g3appdriver.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;

import java.util.ArrayList;
import java.util.HashMap;

public class VersionHandler {
    private static final String TAG = VersionHandler.class.getSimpleName();
    private static final String CHECK_UPDATE_URL = "https://restgk.guanzongroup.com.ph/gcard/ms/version_checker.php";
    private Context mContext;
    private HttpRequestUtil requestUtil;
    private RequestHeaders headers;
    private SharedPref sharedPref;
    private String lVersion = "";
    private String sVersion = "";

    public VersionHandler(Context context, String Version){
        this.mContext = context;
        this.requestUtil = new HttpRequestUtil();
        this.headers = new RequestHeaders(mContext);
        this.lVersion = Version;
        this.sharedPref = new SharedPref(mContext);
    }

    public void checkAppVersion(){
        requestUtil.sendRequest(CHECK_UPDATE_URL, new HttpRequestUtil.onServerResponseListener() {
            @Override
            public HashMap setHeaders() {
                return (HashMap) headers.getHeaders();
            }

            @Override
            public JSONObject setData() {
                JSONObject params = new JSONObject();
                return params;
            }

            @Override
            public void onResponse(JSONObject jsonResponse) {
                try {
                    sVersion = jsonResponse.getString("version");
                    if(!isUpdated()){
                        String level = jsonResponse.getString("level");
                        if(level.equalsIgnoreCase("1")){
                            /**View Dialog for update app here...*/
                        } else {

                            /**
                             * return statement with
                             * Not a priority update...*/
                        }
                    } else {

                        /**
                         * return statement with
                         * app is already updated...*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String message) {
                Log.e(TAG, "UNABLE TO CHECK UPDATE" + message);
            }
        });
    }


    private ArrayList serverVersion(){
        ArrayList<Integer> versionArray = new ArrayList<>();
        String[] laVersion = sVersion.split("\\.");
        for(int x = 0; x < laVersion.length; x++){
            versionArray.add(Integer.parseInt(laVersion[x]));
        }
        return versionArray;
    }

    private ArrayList localVersion(){
        ArrayList<Integer> versionArray = new ArrayList<>();
        String[] laVersion = lVersion.split("\\.");
        for(int x = 0; x <laVersion.length; x++){
            versionArray.add(Integer.parseInt(laVersion[x]));
        }

        return versionArray;
    }

    private boolean isUpdated(){
        for(int x = 0; x < localVersion().size(); x++){
            int lnVersion = (int) localVersion().get(x);
            int snVersion = (int) serverVersion().get(x);
            if(lnVersion < snVersion){
                return false;
            }
        }
        return true;
    }
}
