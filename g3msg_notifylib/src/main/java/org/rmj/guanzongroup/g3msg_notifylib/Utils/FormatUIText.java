package org.rmj.guanzongroup.g3msg_notifylib.Utils;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUIText {

    @SuppressLint("SimpleDateFormat")
    public String getDateUIFormat(String date){
        try {
            Date parseDate = new SimpleDateFormat("dd/mm/yyyy").parse(date);
            return new SimpleDateFormat("mmmm dd, yyyy").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param date
     * @return
     *
     * this method parses date
     * from yyyy-MM-dd HH:mm:ss Format to MMMM dd, yyyy
     */
    public String getParseDate(String date){
        try {
            Date parseDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(date);
            return new SimpleDateFormat("mmmm dd, yyyy").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param date
     * @return
     *
     * this method parses datetime from local database
     * a user friendly intervention...
     */
    public String getParseDateTime(String date){
        try {
            Date parseDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(date);
            return new SimpleDateFormat("mmmm dd, yyyy HH:mm aa").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getCurrencyUIFormat(String price){
        try {
            DecimalFormat currency_total = new DecimalFormat("₱ ###,###,###.###");
            BigDecimal loBigDecimal = new BigDecimal(price);
            return currency_total.format(loBigDecimal);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public double getParseDouble(String value) {
        try {
            String downPrice = value.replaceAll(",", "");
            return Double.parseDouble(downPrice);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public long getParseLong(String value){
        try{
        double ldValue = Double.parseDouble(value.replace(",", ""));
        return (Double.valueOf(ldValue)).longValue();
        } catch (Exception e){
            e.printStackTrace();
            return Long.valueOf("0");
        }
    }

    public int getParseInt(String value){
        try {
            return Integer.parseInt(value.replace(",", ""));
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param value
     * @param position
     * @return value requires the input value from
     * the edittext, while position requires the
     * selected value from the spinner
     */
    public double getParseBusinessLength(String value, int position){
        try{
            if(position == 0) {
                double ldValue = Double.parseDouble(value);
                return ldValue / 12;
            } else {
                return Double.parseDouble(value);
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
