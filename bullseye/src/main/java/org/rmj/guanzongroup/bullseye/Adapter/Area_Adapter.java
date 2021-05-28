package org.rmj.guanzongroup.bullseye.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.bullseye.LocalData.Area_Model;
import org.rmj.guanzongroup.bullseye.R;

import java.util.List;

public class Area_Adapter extends RecyclerView.Adapter<Area_Adapter.BarListViewHolder>{
    private static final String TAG = Area_Adapter.class.getSimpleName();

    private List<Area_Model> areaModels;
    private OnBarGraphClickListener onBarGraphClickListener;

    public Area_Adapter(List<Area_Model> area_models){
        this.areaModels = area_models;
    }

    public void setOnBarGraphClickListener(OnBarGraphClickListener onBarGraphClickListener) {
        this.onBarGraphClickListener = onBarGraphClickListener;
    }

    @NonNull
    @Override
    public BarListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_area_performance, parent, false);
        return new BarListViewHolder(view, onBarGraphClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BarListViewHolder holder, final int position) {
        final Area_Model areaModel = areaModels.get(position);
        holder.areaModel = areaModel;
        holder.lblAreaName.setText(areaModel.getAreaName());
        holder.lblArePerformance.setText(areaModel.getSalesPercentage()+"%");
        holder.progress.setScaleY(55f);
        holder.progress.setMax(areaModel.getDynamicSize());
        holder.progress.setProgress(getParseValue(areaModel.getSalesPercentage()));
    }

    @Override
    public int getItemCount() {
        return areaModels.size();
    }

    static class BarListViewHolder extends RecyclerView.ViewHolder {

        Area_Model areaModel;

        TextView lblAreaName;
        TextView lblArePerformance;
        ProgressBar progress;

        BarListViewHolder(View itemView, final OnBarGraphClickListener listener) {
            super(itemView);

            lblAreaName = itemView.findViewById(R.id.lbl_list_area_name);
            lblArePerformance = itemView.findViewById(R.id.lbl_list_area_performance);
            progress = itemView.findViewById(R.id.progress_areaPerformance);
            progress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(areaModel.getAreaCode(), areaModel.getAreaName());
                }
            });
        }
    }

    private int getParseValue(String value){
        try{
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
            return 100;
        }
    }

    public interface OnBarGraphClickListener{
        void OnClick(String AreaCode, String AreaName);
    }
}
