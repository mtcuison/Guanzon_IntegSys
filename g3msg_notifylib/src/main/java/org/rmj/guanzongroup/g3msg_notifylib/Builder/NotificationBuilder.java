package org.rmj.guanzongroup.g3msg_notifylib.Builder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.DataParser;
import org.rmj.guanzongroup.g3msg_notifylib.Sender.G3_ResponseSender;

public class NotificationBuilder{
    private static final String TAG = NotificationBuilder.class.getCanonicalName();
    private Context mContext;

    private RemoteMessage remoteMessage;
    private RequestHeaders requestHeaders;
    private NotificationReceiver notificationReceiver;
    private DataParser dataParser;
    private G3_ResponseSender.RESPONSE response;

    private Intent intent;
    private PendingIntent pIntent;

    public NotificationBuilder(Context context, RemoteMessage remoteMessage){
        this.mContext = context;
        this.remoteMessage = remoteMessage;
        this.requestHeaders = new RequestHeaders(mContext);
        this.notificationReceiver = new NotificationReceiver(mContext);
        this.dataParser = new DataParser(remoteMessage);
        this.response = new G3_ResponseSender.RESPONSE();
    }

    public void setIntent(Intent intent) {
        /**
         * Set the Intent class from the MyFirebaseMessagingService class*/
        this.intent = intent;
    }

    /**
     * CreateNotification Method automatically calls the
     * create NotificationChannel and ShowNotification from
     * the NotificationReceiver class...*/
    public void CreateNotification(){
        /**
         * This method calls another method from NotificationReceiver class
         * NotificationReceiver class handles notification dataMessage through
         * onNotificationReceiveListener...*/
        notificationReceiver.saveNotification(remoteMessage, new onNotificationReceiveListener() {
            @Override
            public void onRecievedNotificationListener(String MessageID) {
                new G3_ResponseSender(mContext).sendResponse(new G3_ResponseSender.onSendResponseListener() {
                    @Override
                    public JSONObject setResponseType() {
                        /**
                         * RECEIVED_RESPONSE is a JSONObject parameter that needs to
                         * to be pass on the server upon receive of the notification.
                         * this method requires the parameter transno from the
                         * notification message receive by the device...*/
                        return response.RECEIVED_RESPONSE(dataParser.getValueOf("transno"));
                    }

                    @Override
                    public void onSendSuccessResult() {
                        /**
                         * Any Function that can notify the developer
                         * that the message is successfully sent...*/
                        Log.e(TAG, "Notification received response has been sent...");
                    }

                    @Override
                    public void onSendErrorResult(String error) {
                        /**
                         * Any Function that can notify the developer
                         * that the message is not sent...*/
                        Log.e(TAG, "Notification received response cannot be send. Error : " + error);
                    }
                });
            }

            @Override
            public JSONObject notificationData() {
                JSONObject data = new JSONObject();
                try{
                    /**
                     * this method sets all the important data of the
                     * notification that's going to be display on screen
                     * or on notification tray...
                     *
                     * 'msgmon' indicates what type of notification must be displayed...
                     * 'srcenm' indicates the name of the user's who sends the notification
                     * message. This is only being displayed when the notification is just
                     * regular message. NotificationAssets handles the title of the notification...
                     * 'appsrce' indicates which app is the notification came from...
                     * Integsys/Telecom/GuanzonApp this are the only apps that can send a
                     * notification message...
                     * */
                    data.put("title", NotificationAssets.getAppTitle(dataParser.getValueOf("msgmon"),
                            dataParser.getDataValueOf("title"), dataParser.getValueOf("srcenm")));
                    data.put("message", dataParser.getDataValueOf("message"));
                    data.put("appsrce", dataParser.getValueOf("appsrce"));
                    data.put("msgtype", dataParser.getValueOf("msgmon"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return data;
            }

            @Override
            public PendingIntent onCreateNotification() {
                /**
                 * this method passess the intent which has been set by
                 * the builder.setIntent method on onReceivedMessage of
                 * MyFirebaseMessagingService class
                 * Intent objects must be pass in other to
                 * automatically open a designated activity when the user
                 * taps or opens the Notification on Notification tray...*/
                pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
                return pIntent;
            }
        });
    }
}
