package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Personal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.gocas.base.GOCASApplication;

import static org.junit.Assert.*;

public class Fragment_PersonalInfoTest {

    @Mock
    public GOCASApplication poGOcas;

    String MobileNo = "3";
    String MobileNo1 = "";
    String MobileNo2 = "";
    String MobileNo3 = "";

    int MobilePlan1 = 0;
    int MobilePlan2 = 0;
    int MobilePlan3 = 0;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        poGOcas = new GOCASApplication();
        MobileNo1 = "09171870011";
        MobileNo2 = "09171870012";
        MobileNo3 = "09270359402";
    }

    @Test
    public void isSuffixValid() {
        if(MobileNo.equalsIgnoreCase("1")){
            poGOcas.ApplicantInfo().setMobileNoQty(1);
            poGOcas.ApplicantInfo().setMobileNo(0, MobileNo1);
            if(MobilePlan1 == 1) {
                poGOcas.ApplicantInfo().IsMobilePostpaid(0, "1");
                poGOcas.ApplicantInfo().setPostPaidYears(0, 1);
            }
        } else if(MobileNo.equalsIgnoreCase("2")) {
            poGOcas.ApplicantInfo().setMobileNoQty(1);
            poGOcas.ApplicantInfo().setMobileNo(0, MobileNo1);
            if (MobilePlan1 == 1) {
                poGOcas.ApplicantInfo().IsMobilePostpaid(0, "1");
                poGOcas.ApplicantInfo().setPostPaidYears(0, 1);
            }
            poGOcas.ApplicantInfo().setMobileNoQty(2);
            poGOcas.ApplicantInfo().setMobileNo(1, MobileNo2);
            if(MobilePlan2 == 1) {
                poGOcas.ApplicantInfo().IsMobilePostpaid(1, "1");
                poGOcas.ApplicantInfo().setPostPaidYears(1, 1);
            }
        } else if(MobileNo.equalsIgnoreCase("3")) {
            poGOcas.ApplicantInfo().setMobileNoQty(1);
            poGOcas.ApplicantInfo().setMobileNo(0, MobileNo1);
            if(MobilePlan1 == 1) {
                poGOcas.ApplicantInfo().IsMobilePostpaid(0, "1");
                poGOcas.ApplicantInfo().setPostPaidYears(0, 1);
            }
            poGOcas.ApplicantInfo().setMobileNoQty(2);
            poGOcas.ApplicantInfo().setMobileNo(1, MobileNo2);
            if(MobilePlan2 == 1) {
                poGOcas.ApplicantInfo().IsMobilePostpaid(1, "1");
                poGOcas.ApplicantInfo().setPostPaidYears(1, 1);
            }
            poGOcas.ApplicantInfo().setMobileNoQty(3);
            poGOcas.ApplicantInfo().setMobileNo(2, MobileNo3);
            if(MobilePlan3 == 1) {
                poGOcas.ApplicantInfo().IsMobilePostpaid(2, "1");
                poGOcas.ApplicantInfo().setPostPaidYears(2, 1);
            }
        }

        String lsMobileNo1 = poGOcas.ApplicantInfo().getMobileNo(0);
        String lsMobileNo2 = poGOcas.ApplicantInfo().getMobileNo(1);
        String lsMobileNo3 = poGOcas.ApplicantInfo().getMobileNo(2);

        assertEquals("09171870011",lsMobileNo1);
        assertEquals("09171870012",lsMobileNo2);
        assertEquals("09270359402",lsMobileNo3);
    }
}