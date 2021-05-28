package org.rmj.androidprojects.guanzongroup.g3creditapp.JavaTesting;

import org.json.simple.JSONObject;

public class logic_test {

    public static void main(String[] args){
        /*int Age = new AgeCalculator().getAge("11 26, 1996");
        System.out.println(Age);*/

        //arrayTest();
        ParseJson("Its's my life");
    }

    private static void arrayTest(){
        boolean[] lbValue = new boolean[4];
        lbValue[0] = true;
        lbValue[1] = false;
        lbValue[2] = true;
        lbValue[3] = false;
        int lnCtr = 0;

        int[] liFalsePos = new int[lbValue.length];
        for(int x = 0; x < lbValue.length; x++){
            if(!lbValue[x]){
                lnCtr = lnCtr + 1;
                liFalsePos[x] = x;
                System.out.println(liFalsePos[x]);
            }
        }
    }

    private static void ParseJson(String sample){
        JSONObject loJson = new JSONObject();
        JSONObject json = new JSONObject();
        loJson.put("sample", sample);
        loJson.put("sample1", sample);
        json.put("sample_object", loJson);
        System.out.println(json);
    }

    private String parseToDouleResult(String entry){
        double entry1 = Double.parseDouble(entry);
        double cost = 1000;
        double result = entry1 * 1000;
        return String.valueOf(result);
    }
}
