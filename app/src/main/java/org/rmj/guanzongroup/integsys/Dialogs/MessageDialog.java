package org.rmj.guanzongroup.integsys.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.rmj.guanzongroup.integsys.R;

import java.util.Objects;

public class MessageDialog {
    private Context mContext;

    private String Title;
    private String btnPositive;
    private String btnNegative;
    private String Message;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private int btnPositiveVisibility = View.GONE;
    private int btnNegativeVisibility = View.GONE;
    private MessageDialogInterfaceClickListener positiveButtonClickListener;
    private MessageDialogInterfaceClickListener negativeButtonClickListener;

    public MessageDialog(Context context, String Title, String Message) {
        this.mContext = context;
        this.Title = Title;
        this.Message = Message;
        this.builder = new AlertDialog.Builder(mContext);
    }

    public void showDialog(){
        createDialog();
        dialog.show();
    }

    @SuppressLint("ResourceAsColor")
    private void createDialog(){
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_message_box, null, false);
        builder.setView(view)
                .setCancelable(false);
        dialog = builder.create();
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        TextView lblTitle = view.findViewById(R.id.lbl_dialogTitle);
        TextView lblMessage = view.findViewById(R.id.lbl_dialog_message);
        Button btnPositiveButton = view.findViewById(R.id.btn_dialog_positiveButton);
        Button btnNegativeButton = view.findViewById(R.id.btn_dialog_negativeButton);

        lblTitle.setText(Title);
        lblMessage.setText(Message);
        btnPositiveButton.setText(btnPositive);
        btnNegativeButton.setText(btnNegative);
        btnPositiveButton.setVisibility(btnPositiveVisibility);
        btnNegativeButton.setVisibility(btnNegativeVisibility);
        btnPositiveButton.setOnClickListener(v -> positiveButtonClickListener.onClick(v, dialog));

        btnNegativeButton.setOnClickListener(v -> negativeButtonClickListener.onClick(v, dialog));
    }

    public void setPositiveButton(String Text, MessageDialogInterfaceClickListener listener){
        this.btnPositive = Text;
        this.positiveButtonClickListener = listener;
        this.btnPositiveVisibility = View.VISIBLE;
    }

    public void setNegativeButton(String Text, MessageDialogInterfaceClickListener listener){
        this.btnNegative = Text;
        this.negativeButtonClickListener = listener;
        this.btnNegativeVisibility = View.VISIBLE;
    }

    public interface MessageDialogInterfaceClickListener{
        void onClick(View view, AlertDialog dialog);
    }
}
