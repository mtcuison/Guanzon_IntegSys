package org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

import java.util.Objects;

public class MessageDialog {
    private Context mContext;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private String message;
    private String btnPositiveText;
    private String btnNegativeText;
    private int btnPosVisibility = View.GONE;
    private int btnNegVisibility = View.GONE;

    private onMessageDialogButtonClickListener btnPositiveListener;
    private onMessageDialogButtonClickListener btnNegativeListener;

    public MessageDialog(Context context){
        this.mContext = context;
        this.builder = new AlertDialog.Builder(mContext);
    }

    public interface onMessageDialogButtonClickListener{
        void onButtonClick(AlertDialog dialog);
    }

    public void setPositiveButton(String ButtonText, onMessageDialogButtonClickListener listener){
        this.btnPositiveText = ButtonText;
        this.btnPositiveListener = listener;
        this.btnPosVisibility = View.VISIBLE;
    }

    public void setNegativeButton(String ButtonText, onMessageDialogButtonClickListener listener){
        this.btnNegativeText = ButtonText;
        this.btnNegativeListener = listener;
        this.btnNegVisibility = View.VISIBLE;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void showDialog(){
        createDialog();
        dialog.show();
    }

    @SuppressLint("ResourceAsColor")
    private void createDialog(){
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_credit_app_message_box, null, false);
        builder.setCancelable(false)
                .setView(view);
        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        TextView lblMessage = view.findViewById(R.id.lbl_creditApp_dialogMessage);
        MaterialButton btnPositive = view.findViewById(R.id.btn_dialog_positiveButton);
        MaterialButton btnNegative = view.findViewById(R.id.btn_dialog_negativeButton);
        btnPositive.setText(btnPositiveText);
        btnNegative.setText(btnNegativeText);
        btnPositive.setVisibility(btnPosVisibility);
        btnNegative.setVisibility(btnNegVisibility);

        lblMessage.setText(message);

        btnPositive.setOnClickListener(v -> {
            if(btnPositiveListener!=null){
                btnPositiveListener.onButtonClick(dialog);
            }
        });

        btnNegative.setOnClickListener(v -> {
            if(btnNegativeListener!=null){
                btnNegativeListener.onButtonClick(dialog);
            }
        });
    }
}
