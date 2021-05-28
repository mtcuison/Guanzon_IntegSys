package org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter;

public class RequestNames {
    private String RequestName;
    private String RequestIDxx;
    private String RequestDept;

    public RequestNames(String requestName,
                        String requestIDxx,
                        String requestDept){
        this.RequestName = requestName;
        this.RequestIDxx = requestIDxx;
        this.RequestDept = requestDept;
    }

    public String getRequestName() {
        return RequestName;
    }

    public String getRequestIDxx() {
        return RequestIDxx;
    }

    public String getRequestDept() {
        return RequestDept;
    }
}
