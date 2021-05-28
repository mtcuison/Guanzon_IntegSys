package org.rmj.guanzongroup.promotions.Model;

public class TownInfo {
    private String sTownID;
    private String sTownNm;
    private String sProvID;
    private String sProvNm;
    private String sZippCd;

    public TownInfo(String sTownID, String sTownNm, String sProvID, String sProvNm, String sZippCd) {
        this.sTownID = sTownID;
        this.sTownNm = sTownNm;
        this.sProvID = sProvID;
        this.sProvNm = sProvNm;
        this.sZippCd = sZippCd;
    }

    public String getsTownID() {
        return sTownID;
    }

    public String getsTownNm() {
        return sTownNm;
    }

    public String getsProvID() {
        return sProvID;
    }

    public String getsProvNm() {
        return sProvNm;
    }

    public String getsZippCd() {
        return sZippCd;
    }
}
