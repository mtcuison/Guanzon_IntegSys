package org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.ApplicationLog;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

import java.util.ArrayList;
import java.util.List;

public class ApplicationLogAdapter extends RecyclerView.Adapter<ApplicationLogAdapter.ApplicationLogVewHolder>{
    private static final String TAG = ApplicationLogAdapter.class.getSimpleName();

    private List<ApplicationLog> applicationLogList;
    private List<ApplicationLog> applicantSearchFilter;
    private OnVoidApplicationListener onVoidApplicationListener;
    private OnExportGOCASListener onExportGOCASListener;
    private OnApplicationClickListener onApplicationClickListener;
    private ApplicantionFilter applicantionFilter;

    public ApplicationLogAdapter(List<ApplicationLog> applicationLogList){
        this.applicationLogList = applicationLogList;
        this.applicantSearchFilter = applicationLogList;
        this.applicantionFilter = new ApplicantionFilter(ApplicationLogAdapter.this);
    }

    public void setOnVoidApplicationListener(OnVoidApplicationListener listener){
        this.onVoidApplicationListener = listener;
    }

    public void setOnApplicationClickListener(OnApplicationClickListener onApplicationClickListener) {
        this.onApplicationClickListener = onApplicationClickListener;
    }

    public void setOnExportGOCASListener(OnExportGOCASListener onExportGOCASListener) {
        this.onExportGOCASListener = onExportGOCASListener;
    }

    @NonNull
    @Override
    public ApplicationLogVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View applicationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_application_log, parent, false);

        return new ApplicationLogVewHolder(applicationView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ApplicationLogVewHolder holder, int position) {
        ApplicationLog applicationLog = applicantSearchFilter.get(position);
        holder.lblGoCasNoxxx.setText("GOCas No. :"+applicationLog.getGoCasNoxx());
        holder.lblTransNoxxx.setText(applicationLog.getsTransNox());
        holder.lblClientName.setText(applicationLog.getsClientNm());
        holder.lblUnitModelx.setText(applicationLog.getModelName());
        holder.lblModelPrice.setText(applicationLog.getPrice());
        holder.lblUnitAppldx.setText(applicationLog.getcUnitAppl());
        holder.lblSlctBranch.setText(applicationLog.getsBranchCd());
        holder.lblAppltnDate.setText(applicationLog.getdTransact());
        holder.lblDownpaymnt.setText(applicationLog.getDown());
        holder.lblApplResult.setText(applicationLog.getIsWithCI());
        holder.lblDownPercnt.setText(applicationLog.getDownPerc());
        holder.lblSendStatus.setText(applicationLog.getcSendStat());
        holder.lblSendStatus.setTextColor(applicationLog.getStatColor());
        holder.lblDateApprov.setText(applicationLog.getDateApproved());
        holder.lblDateSent.setText(applicationLog.getdDateSent());
        holder.btnVoid.setVisibility(applicationLog.getVoidStatus());
        holder.btnExpt.setVisibility(applicationLog.getExportStatus());
    }

    public ApplicantionFilter getApplicantionFilter(){
        return applicantionFilter;
    }

    @Override
    public int getItemCount() {
        return applicantSearchFilter.size();
    }

    class ApplicationLogVewHolder extends RecyclerView.ViewHolder{

        TextView lblTransNoxxx;
        TextView lblClientName;
        TextView lblUnitModelx;
        TextView lblModelPrice;
        TextView lblUnitAppldx;
        TextView lblSlctBranch;
        TextView lblAppltnDate;
        TextView lblDownpaymnt;
        TextView lblApplResult;
        TextView lblDownPercnt;
        TextView lblSendStatus;
        TextView lblGoCasNoxxx;
        TextView lblDateApprov;
        TextView lblDateSent;
        ImageButton btnVoid;
        ImageButton btnExpt;

        ApplicationLogVewHolder(@NonNull View v) {
            super(v);

            lblGoCasNoxxx = v.findViewById(R.id.lbl_listLog_GoCasNo);
            lblTransNoxxx = v.findViewById(R.id.lbl_listLog_applicationTransNo);
            lblClientName = v.findViewById(R.id.lbl_listLog_applicantName);
            lblUnitModelx = v.findViewById(R.id.lbl_listLog_applicationModel);
            lblModelPrice = v.findViewById(R.id.lbl_listLog_applicationPrice);
            lblUnitAppldx = v.findViewById(R.id.lbl_listLog_applicationUnit);
            lblSlctBranch = v.findViewById(R.id.lbl_listLog_applicationBranch);
            lblAppltnDate = v.findViewById(R.id.lbl_listLog_applicationDate);
            lblDownpaymnt = v.findViewById(R.id.lbl_listLog_applicationDown);
            lblApplResult = v.findViewById(R.id.lbl_listLog_applicationWithCI);
            lblDownPercnt = v.findViewById(R.id.lbl_listLog_applicationPrcnt);
            lblSendStatus = v.findViewById(R.id.lbl_listLog_applicationStat);
            lblDateApprov = v.findViewById(R.id.lbl_listLog_approvedDate);
            lblDateSent = v.findViewById(R.id.lbl_listLog_dateSent);
            btnVoid = v.findViewById(R.id.btn_deleteApplication);
            btnExpt = v.findViewById(R.id.btn_ExportApplication);

            btnVoid.setOnClickListener(v1 -> {
                if(onVoidApplicationListener!=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        onVoidApplicationListener.OnVoid(lnPos, applicantSearchFilter.get(lnPos).getsTransNox());
                    }
                }
            });

            btnExpt.setOnClickListener(v12 -> {
                if(onExportGOCASListener!=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        onExportGOCASListener.onExport(applicantSearchFilter.get(lnPos).getDetlInfox(),
                                applicantSearchFilter.get(lnPos).getsTransNox(),
                                applicantSearchFilter.get(lnPos).getGoCasNoxx());
                    }
                }
            });

            v.setOnClickListener(v12 -> {
                if(onApplicationClickListener!=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        onApplicationClickListener.OnClick(lnPos, applicationLogList.get(lnPos).getsTransNox());
                    }
                }
            });
        }
    }

    public interface OnVoidApplicationListener{
        void OnVoid(int position, String TransNox);
    }

    public interface OnApplicationClickListener{
        void OnClick(int position, String TransNox);
    }

    public interface OnExportGOCASListener{
        void onExport(String GOCAS, String ClientName,String DateApplied);
    }

    public class ApplicantionFilter extends Filter{

        private ApplicationLogAdapter adapter;

        ApplicantionFilter(ApplicationLogAdapter adapter){
            super();
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults results = new FilterResults();
            if(constraint.length() == 0){
                applicantSearchFilter.addAll(applicationLogList);
            } else {
                List<ApplicationLog> filterSearch = new ArrayList<>();
                for(ApplicationLog applicationLog : applicationLogList){
                    if(applicationLog.getsClientNm().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterSearch.add(applicationLog);
                    }
                }
                applicantSearchFilter = filterSearch;
            }
            results.values = applicantSearchFilter;
            results.count = applicantSearchFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.applicantSearchFilter = (List<ApplicationLog>) results.values;
            this.adapter.notifyDataSetChanged();
        }
    }
}
