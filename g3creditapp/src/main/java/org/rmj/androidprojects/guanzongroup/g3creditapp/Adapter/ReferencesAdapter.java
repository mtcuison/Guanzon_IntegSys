package org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.PersonalReferences;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

import java.util.List;

public class ReferencesAdapter extends RecyclerView.Adapter<ReferencesAdapter.ReferencesViewHolder> {

    private List<PersonalReferences> personalReferencesList;
    private PersonalReferences personalReferences;
    private OnReferenceDeleteListener onReferenceDeleteListener;

    public ReferencesAdapter(List<PersonalReferences> personalReferencesList){
        this.personalReferencesList = personalReferencesList;
    }

    public void setOnReferenceDeleteListener(OnReferenceDeleteListener listener){
        this.onReferenceDeleteListener = listener;
    }

    @NonNull
    @Override
    public ReferencesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dependent, parent, false);
        return new ReferencesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferencesViewHolder holder, int position) {
        personalReferences = personalReferencesList.get(position);

        holder.fullname.setText(personalReferences.getFullname());
        holder.btnRemove.setOnClickListener(v -> onReferenceDeleteListener.onDelete(position));
    }

    @Override
    public int getItemCount() {
        return personalReferencesList.size();
    }

    class ReferencesViewHolder extends RecyclerView.ViewHolder{

        TextView fullname;
        MaterialButton btnRemove;

        ReferencesViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.lbl_list_cap_dependent);
            btnRemove = itemView.findViewById(R.id.btn_list_dpdRemove);
        }
    }

    public interface OnReferenceDeleteListener{
        void onDelete(int position);
    }
}
