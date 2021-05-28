package org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

import java.util.ArrayList;

public class DependenciesAdapter extends RecyclerView.Adapter<DependenciesAdapter.DependenciesViewHolder> {

    private ArrayList<String> arr_dpd;

    private onDependencyRemoveLisneter lisneter;

    public DependenciesAdapter(ArrayList arr_dpd){
        this.arr_dpd = arr_dpd;
    }

    public void setOnDependencyRemoveListener(onDependencyRemoveLisneter listener){
        this.lisneter = listener;
    }

    @NonNull
    @Override
    public DependenciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dependent, parent, false);
        return new DependenciesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DependenciesViewHolder holder, int position) {
        holder.lblDdpName.setText(arr_dpd.get(position));
        holder.btnRemove.setOnClickListener(v -> lisneter.onDependencyRemove(position));
    }

    @Override
    public int getItemCount() {
        return arr_dpd.size();
    }

    class DependenciesViewHolder extends RecyclerView.ViewHolder{

        TextView lblDdpName;
        MaterialButton btnRemove;

        DependenciesViewHolder(@NonNull View itemView) {
            super(itemView);

            lblDdpName = itemView.findViewById(R.id.lbl_list_cap_dependent);
            btnRemove = itemView.findViewById(R.id.btn_list_dpdRemove);
        }
    }

    public interface onDependencyRemoveLisneter{
        void onDependencyRemove(int position);
    }
}
