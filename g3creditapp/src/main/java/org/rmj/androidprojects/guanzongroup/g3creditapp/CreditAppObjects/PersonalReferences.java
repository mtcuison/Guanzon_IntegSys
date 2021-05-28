package org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects;

public class PersonalReferences {
    private String Fullname;
    private String Address1;
    private String TownCity;
    private String ContactN;

    public PersonalReferences(String fullname,
                              String address1,
                              String townCity,
                              String contactN){
        this.Fullname = fullname;
        this.Address1 = address1;
        this.TownCity = townCity;
        this.ContactN = contactN;
    }

    public String getFullname() {
        return Fullname;
    }

    public String getAddress1() {
        return Address1;
    }

    public String getTownCity() {
        return TownCity;
    }

    public String getContactN() {
        return ContactN;
    }
}
