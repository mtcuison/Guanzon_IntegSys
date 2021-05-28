package org.rmj.guanzongroup.bullseye.LocalData;

import java.text.DecimalFormat;

public class Branch_Model {
    private String BranchCode;
    private String BranchName;
    private float Goal;
    private float Actual;

    private final DecimalFormat format = new DecimalFormat("##.##");

    public Branch_Model(String branchCode,
                      String branchName,
                      String goal,
                      String actual){
        this.BranchCode = branchCode;
        this.BranchName = branchName;
        this.Goal = Float.parseFloat(goal);
        this.Actual = Float.parseFloat(actual);
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public String getBranchName() {
        return BranchName;
    }

    public String getSalesPercentage() {
        return new DecimalFormat("###").format(Actual);
        /*float percentage = Goal/Actual;
        float result = percentage * 100;
        return format.format(result);*/
    }
}
