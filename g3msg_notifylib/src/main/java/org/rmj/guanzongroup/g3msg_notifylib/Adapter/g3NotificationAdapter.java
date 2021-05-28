package org.rmj.guanzongroup.g3msg_notifylib.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.g3msg_notifylib.Model.NotificationMessageModel;
import org.rmj.guanzongroup.g3msg_notifylib.R;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.FormatUIText;

import java.util.List;

public class g3NotificationAdapter extends RecyclerView.Adapter<g3NotificationAdapter.NotificationViewHolder> {

    private List<NotificationMessageModel> listNotifications;
    private NotificationMessageModel poNotifications;
    private OnNotificationClickListener poListener;

    public g3NotificationAdapter(List<NotificationMessageModel> listNotifications){
        this.listNotifications = listNotifications;
    }

    public void setOnNotificationClickListener(OnNotificationClickListener listener){
        this.poListener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notifications, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {
        this.poNotifications = listNotifications.get(position);

        holder.lblTitle.setText(poNotifications.getMessageTitle());
        holder.lblDatex.setText(new FormatUIText().getParseDateTime(poNotifications.getMessageDate()));
        holder.lblMesgx.setText(poNotifications.getMessageBody());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(poListener!=null){
                   poListener.OnClick(position, poNotifications.isRead());
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNotifications.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{

        TextView lblTitle;
        TextView lblDatex;
        TextView lblMesgx;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            lblTitle = itemView.findViewById(R.id.lbl_ntfTitle);
            lblDatex = itemView.findViewById(R.id.lbl_ntfDateTime);
            lblMesgx = itemView.findViewById(R.id.lbl_ntfMessage);
        }
    }

    public interface OnNotificationClickListener{
        void OnClick(int position, boolean isRead);
    }
}
