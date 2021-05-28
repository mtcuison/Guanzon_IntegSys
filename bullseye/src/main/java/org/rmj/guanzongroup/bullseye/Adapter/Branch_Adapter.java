package org.rmj.guanzongroup.bullseye.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.bullseye.LocalData.Area_Model;
import org.rmj.guanzongroup.bullseye.LocalData.Branch_Model;
import org.rmj.guanzongroup.bullseye.R;

import java.util.List;

public class Branch_Adapter extends RecyclerView.Adapter<Branch_Adapter.BarListViewHolder>{
    private static final String TAG = Branch_Adapter.class.getSimpleName();

    private List<Branch_Model> branch_modelList;
    private OnBarGraphClickListener onBarGraphClickListener;
    private int DynamicSize;

    public Branch_Adapter(List<Branch_Model> branchList, int DynamicSize){
        this.branch_modelList = branchList;
        this.DynamicSize = DynamicSize;
    }

    public void setOnBarGraphClickListener(OnBarGraphClickListener onBarGraphClickListener) {
        this.onBarGraphClickListener = onBarGraphClickListener;
    }

    @NonNull
    @Override
    public BarListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_brach_performance, parent, false);
        return new BarListViewHolder(view, onBarGraphClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BarListViewHolder holder, final int position) {
        final Branch_Model branchModel = branch_modelList.get(position);
         holder.branchModel = branchModel;
        holder.lblAreaName.setText(branchModel.getBranchName());
        holder.lblArePerformance.setText(branchModel.getSalesPercentage());
        holder.progress.setScaleY(55f);
        holder.progress.setMax(DynamicSize);
        holder.progress.setProgress(getParseValue(branchModel.getSalesPercentage()));
    }

    @Override
    public int getItemCount() {
        return branch_modelList.size();
    }

    static class BarListViewHolder extends RecyclerView.ViewHolder {

        Branch_Model branchModel;

        TextView lblAreaName;
        TextView lblArePerformance;
        ProgressBar progress;

        BarListViewHolder(View itemView, final OnBarGraphClickListener listener) {
            super(itemView);

            lblAreaName = itemView.findViewById(R.id.lbl_list_branch_name);
            lblArePerformance = itemView.findViewById(R.id.lbl_list_branch_performance);
            progress = itemView.findViewById(R.id.progress_branchPerformance);
            /*progress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(branchModel.getBranchCode(), branchModel.getBranchName());
                }
            });*/
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
