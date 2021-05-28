package org.rmj.guanzongroup.promotions.Model;

public class Voucher {
    private String BranchCodexx;
    private String CustomerName;
    private String CustomerAddx;
    private String CustomerTown;
    private String CustomerProv;
    private String DocumentType;
    private String DocumentNoxx;
    private String MobileNumber;
    private String message;

    public Voucher() {

    }

    public String getMessage() {
        return message;
    }

    public void setBranchCodexx(String branchCodexx) {
        BranchCodexx = branchCodexx;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public void setCustomerAddx(String customerAddx) {
        CustomerAddx = customerAddx;
    }

    public void setCustomerTown(String customerTown) {
        CustomerTown = customerTown;
    }

    public void setCustomerProv(String customerProv) {
        CustomerProv = customerProv;
    }

    public void setDocumentNoxx(String documentNoxx) {
        DocumentNoxx = documentNoxx;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getCustomerAddx() {
        return CustomerAddx;
    }

    public String getCustomerTown() {
        return CustomerTown;
    }

    public String getCustomerProv() {
        return CustomerProv;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getBranchCodexx() {
        return BranchCodexx;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public String getDocumentNoxx() {
        return DocumentNoxx;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public boolean isValidInfo(){
        return isNameValid() &&
                isAddressValid() &&
                isDocumentNoValid() &&
                isMobileNoValid() &&
                isDocumentTypeValid();
    }

    private boolean isNameValid(){
        return !CustomerName.trim().isEmpty();
    }

    private boolean isAddressValid(){
        if(CustomerAddx.trim().isEmpty()){
            message = "Please enter customer address";
            return false;
        }
        if(CustomerTown.isEmpty()){
            message = "Please enter customer town info";
            return false;
        }
        return true;
    }

    private boolean isDocumentNoValid(){
        return !DocumentNoxx.trim().isEmpty();
    }

    private boolean isMobileNoValid(){
        if(MobileNumber.trim().isEmpty()){
            message = "Please enter customer contact info";
            return false;
        }
        if(MobileNumber.length()!=11){
            message = "Please enter valid contact info";
            return false;
        }
        if(!MobileNumber.substring(0, 2).equalsIgnoreCase("09")) {
            message = "Please enter valid contact info";
            return false;
        }
        return true;
    }

    private boolean isDocumentTypeValid(){
        if(DocumentType.trim().isEmpty()){
            message = "Please select document type";
            return false;
        }
        return true;
    }
}
