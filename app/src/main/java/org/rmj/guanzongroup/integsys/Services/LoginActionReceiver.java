package org.rmj.guanzongroup.integsys.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.guanzongroup.integsys.Activity.Activity_LoadAsset;
import org.rmj.guanzongroup.integsys.Activity.Activity_Splash;
import org.rmj.guanzongroup.integsys.Activity.MainActivity;

public class LoginActionReceiver extends BroadcastReceiver {
    private static final String TAG = LoginActionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        closeSplashScreen();
        intent = new Intent(context, getActivity(context));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Log.e(TAG, "Broadcast has been receive.");
    }

    private Class<? extends AppCompatActivity> getActivity(Context context){
        if(new SessionManager(context).isFirstLaunch()){
            return Activity_LoadAsset.class;
        } else {
            return MainActivity.class;
        }
    }

    private void closeSplashScreen(){
        try {
            Activity_Splash.getInstance().finish();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
