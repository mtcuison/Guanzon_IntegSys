package org.rmj.g3appdriver.lib.account.AppLogin;

import android.util.Log;

class SelectLogin {
    private static final String TAG = SelectLogin.class.getSimpleName();

    /**
     * This class automatically selects a designated app login to the
     * user.*/

    G3AppsLogin getLoginInstance(String ProductID){
        switch (ProductID){
            case "GuanzonApp":
                G3AppsLogin instance = new GuanzonApps();
                Log.e(TAG, "Guanzon App login has been initialize");
                return instance;
            case "Telecom":
            case "IntegSys":
                instance = new Telecom();
                Log.e(TAG, "Telecom App login has been initialize");
                return instance;
        }
        return null;
    }
}
