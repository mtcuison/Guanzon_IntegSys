package org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter;

public class CashCountLog {
    private String sTransNo;
    private String DateCrtd;
    private String ReqtName;
    private String NtryDate;

    public CashCountLog(String sTransNo,
                        String DateCrtd,
                        String ReqtName,
                        String NtryDate){
        this.sTransNo = sTransNo;
        this.DateCrtd = DateCrtd;
        this.ReqtName = ReqtName;
        this.NtryDate = NtryDate;
    }

    public String getsTransNo() {
        return sTransNo;
    }

    public String getDateCrtd() {
        return DateCrtd;
    }

    public String getReqtName() {
        return ReqtName;
    }

    public String getStatusxx() {
        return getStatus();
    }

    public String getNtryDate() {
        return NtryDate;
    }

    private String getStatus(){
        if(NtryDate.isEmpty()){
            return "Sent";
        }
        return "";
    }
}
