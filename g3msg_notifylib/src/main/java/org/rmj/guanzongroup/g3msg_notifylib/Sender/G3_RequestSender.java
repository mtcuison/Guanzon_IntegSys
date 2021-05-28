package org.rmj.guanzongroup.g3msg_notifylib.Sender;

import android.content.Context;

import org.json.JSONObject;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;
import org.rmj.guanzongroup.g3msg_notifylib.Api.ApiConfig;
import org.rmj.guanzongroup.g3msg_notifylib.DbHelper.NotificationDataHelper;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.SharedPref;

import java.util.HashMap;
import java.util.Map;

public class G3_RequestSender {
    private static final String TAG = G3_RequestSender.class.getSimpleName();
    private Context mContext;
    private NotificationDataHelper helper;
    private RequestHeaders requestHeaders;

    public G3_RequestSender(Context context){
        this.mContext = context;
        this.helper = new NotificationDataHelper(mContext);
        this.requestHeaders = new RequestHeaders(mContext);
    }

    public void sendNewMessage(final HashMap headers, final JSONObject DataMessage){
        new HttpRequestUtil().sendRequest(ApiConfig.SEND_REQUEST, new HttpRequestUtil.onServerResponseListener() {
            @Override
            public HashMap setHeaders() {
                return (HashMap) requestHeaders.getHeaders();
            }

            @Override
            public JSONObject setData() {
                return DataMessage;
            }

            @Override
            public void onResponse(JSONObject jsonResponse) {
                /**
                 * Set onMessageBroadcastReceiver to update the local Database
                 * set message status base on the server response and recepient
                 * response*/
            }

            @Override
            public void onErrorResponse(String message) {
                /**
                 * Show Toast message or message dialog here...
                 * to notify the user that the message being send has a problem*/
            }
        });
    }
}
