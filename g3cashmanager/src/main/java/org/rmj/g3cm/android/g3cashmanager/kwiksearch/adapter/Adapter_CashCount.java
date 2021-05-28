package org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3cm.android.g3cashmanager.R;

import java.util.List;

public class Adapter_CashCount extends RecyclerView.Adapter<Adapter_CashCount.CashCountViewHolder>{
    private static final String TAG = Adapter_CashCount.class.getSimpleName();

    private List<CashCountLog> cashCountLogList;
    private CashCountLog cashCountLog;

    public Adapter_CashCount(List<CashCountLog> cashCountLogList){
        this.cashCountLogList = cashCountLogList;
    }

    @NonNull
    @Override
    public CashCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cash_count_log, parent, false);
        return new CashCountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CashCountViewHolder holder, int position) {
        cashCountLog = cashCountLogList.get(position);

        holder.lblTransNo.setText(cashCountLog.getsTransNo());
        holder.lblDcreatd.setText(cashCountLog.getDateCrtd());
        holder.lblReqName.setText(cashCountLog.getReqtName());
        holder.lblStatusx.setText(cashCountLog.getStatusxx());
        holder.lblDateSnt.setText(cashCountLog.getNtryDate());
    }

    @Override
    public int getItemCount() {
        return cashCountLogList.size();
    }

    class CashCountViewHolder extends RecyclerView.ViewHolder{

        TextView lblTransNo;
        TextView lblDcreatd;
        TextView lblReqName;
        TextView lblStatusx;
        TextView lblDateSnt;

        CashCountViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTransNo = itemView.findViewById(R.id.lbl_list_ccTransNox);
            lblDcreatd = itemView.findViewById(R.id.lbl_list_ccDateCreated);
            lblReqName = itemView.findViewById(R.id.lbl_list_ccRequestName);
            lblStatusx = itemView.findViewById(R.id.lbl_list_ccSentStatus);
            lblDateSnt = itemView.findViewById(R.id.lbl_list_ccDateSent);
        }
    }

}
