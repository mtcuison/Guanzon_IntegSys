package org.rmj.androidprojects.guanzongroup.g3logindriver.Etc;

import org.rmj.androidprojects.guanzongroup.g3logindriver.R;

public class LoginAssets {

    public int getLoginBackground(String ProductID){
        switch (ProductID){
            case "GuanzonApp":
                return R.drawable.background_login_guanzonapp;
            case "Telecom":
                return R.drawable.background_login_telecom;
            case "IntegSys":
                return R.drawable.background_login_integsys;
        }
        return 0;
    }
}
