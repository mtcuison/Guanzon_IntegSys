package org.rmj.g3appdriver.lib.account.ManageAccount;

import android.util.Log;

import androidx.annotation.NonNull;

import static org.rmj.g3appdriver.lib.account.ManageAccount.ManageAccountEntry.GetAccountEntry.ACCOUNT_REGISTRATION;
import static org.rmj.g3appdriver.lib.account.ManageAccount.ManageAccountEntry.GetAccountEntry.FORGOT_PASSWORD;
import static org.rmj.g3appdriver.lib.account.ManageAccount.ManageAccountEntry.GetAccountEntry.PASSWORD_RESET;

public class ManageAccountEntry {
    private static final String TAG = ManageAccountEntry.class.getSimpleName();
    private static G3Accounts instance;
    public G3Accounts getAccountInstance(@NonNull String accountEntry){
        switch (accountEntry){
            case ACCOUNT_REGISTRATION:
                instance = new AccountRegistration();
                Log.e(TAG, "Account registration has been initialize.");
                return instance;
            case FORGOT_PASSWORD:
                instance = new ForgotPassword();
                Log.e(TAG, "Password retrieval has been initialize.");
                return instance;
            case PASSWORD_RESET:
                instance = new PasswordReset();
                Log.e(TAG, "Password reset has been initialize");
                return instance;
        }
        return null;
    }

    public interface GetAccountEntry{
        String ACCOUNT_REGISTRATION = "rsgstr";
        String FORGOT_PASSWORD = "frgtpswrd";
        String PASSWORD_RESET = "pswrdrst";
    }
}
