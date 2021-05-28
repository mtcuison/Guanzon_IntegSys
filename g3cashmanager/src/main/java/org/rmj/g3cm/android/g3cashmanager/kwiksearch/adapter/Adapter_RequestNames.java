package org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3cm.android.g3cashmanager.R;
import org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter.RequestNames;

import java.util.List;

public class Adapter_RequestNames extends RecyclerView.Adapter<Adapter_RequestNames.ViewHolder>{

    private List<RequestNames> requestNamesList;
    private RequestNames requestNames;
    private Context mContext;
    private onItemClickListener mListener;

    public Adapter_RequestNames(Context context,
                                List<RequestNames> requestNames_List){
        this.mContext = context;
        this.requestNamesList = requestNames_List;
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View nView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_requestnames, parent, false);
        return new ViewHolder(nView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.requestNames = requestNamesList.get(position);
        requestNames = requestNamesList.get(position);

        holder.textView.setText(requestNames.getRequestName());
    }

    @Override
    public int getItemCount() {
        return requestNamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RequestNames requestNames;
        TextView textView;
        public ViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.lbl_employeeName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
}
