package org.rmj.g3appdriver.etc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import org.rmj.g3appdriver.dev.AppData;

public class SessionManager {
    //LOG CAT TAG
    private static String TAG = SessionManager.class.getSimpleName();

    //SHARED PREFERENCES
    private SharedPreferences pref;

    private Editor editor;

    private Context _context;

    //shared preference  file name
    private static final String PREF_NAME = "GuanzonApps";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    private static final String KEY_IS_FIRST_LAUNCHED = "isFirstLaunch";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        this._context = context;
        //Shared pref mode
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     *
     * @param isLoggedIn set user login status
     */
    public void setLogin(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        if(isLoggedIn){
            Log.d(TAG, "User Login.");
        } else {
            AppData db = AppData.getInstance(_context);
            db.LogoutUser();
            Log.d(TAG, "User Logout");
        }
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setIsFirstLaunched(boolean isFirstLaunched){
        editor.putBoolean(KEY_IS_FIRST_LAUNCHED, isFirstLaunched);

        editor.commit();

        Log.e(TAG, "Is app first launched.");
    }

    public boolean isFirstLaunch(){
        return pref.getBoolean(KEY_IS_FIRST_LAUNCHED, true);
    }
}

