package org.rmj.androidprojects.guanzongroup.g3logindriver.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3logindriver.Activity_Login;
import org.rmj.androidprojects.guanzongroup.g3logindriver.R;
import org.rmj.g3appdriver.etc.SharedPref;
import org.rmj.g3appdriver.lib.account.AppLogin.LoginUserAccount;
import org.rmj.g3appdriver.lib.account.AppLogin.onUserLoginListener;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.WebClient;

public class Dialog_AccountActivation {

    private Context mContext;
    private View mView;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private SharedPref sharedPref;
    private Toast toast;

    private EditText txtPIN;
    private Button btnActivate;
    private TextView txtDialogMessage;
    private String MobileNo = "";
    private String ActivationURL;
    private JSONObject loginParameters;

    public Dialog_AccountActivation(Context context, String Number){
        this.mContext = context;
        this.MobileNo = Number;
        this.builder = new AlertDialog.Builder(mContext);
        this.sharedPref = new SharedPref(mContext);
    }

    public void showDialog(String ActivationURL){
        this.ActivationURL = ActivationURL;
        createDialog();
        alertDialog.show();
    }

    @SuppressLint("InflateParams")
    private void createDialog(){
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_account_verification_code, null, false);
        builder.setCancelable(false)
                .setView(mView);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        txtDialogMessage = mView.findViewById(R.id.lbl_dialog_activation_msg);
        btnActivate = mView.findViewById(R.id.btn_dialogActivate);
        txtPIN = mView.findViewById(R.id.txt_dialog_confirm_PIN);

        txtDialogMessage.setText(mContext.getResources().getString(R.string.dialog_msg_account_confirm1)+ " " + MobileNo + " " + mContext.getResources().getString(R.string.dialog_msg_account_confirm2));
        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidPIN()){
                    alertDialog.dismiss();
                    try {
                        String response = WebClient.httpsPostJSon(ActivationURL, "", null);
                        if(response.isEmpty()){
                            toast = Toast.makeText(mContext, "Unable to confirm your account. No server response has received.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else if(response.equalsIgnoreCase("Your account was activated successfully. You can now login on Guanzon App.")){
                            toast = Toast.makeText(mContext, "Your Account has been activated successfully.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            new Activity_Login().getInstance().showDialog();
                            alertDialog.dismiss();
                        } else if(response.equalsIgnoreCase("Unable to verify account. Your account cannot be updated.")){
                            toast = Toast.makeText(mContext, "Unable to confirm your account. No server response has received.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            toast = Toast.makeText(mContext, "Unable to confirm your account. Unknown problem occurred. Please try again.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    toast = Toast.makeText(mContext, "You have entered an incorrect PIN. Please try again.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    txtPIN.setText("");
                }
            }
        });
    }

    private boolean isValidPIN(){
        return sharedPref.getPIN().equalsIgnoreCase(txtPIN.getText().toString());
    }
}
