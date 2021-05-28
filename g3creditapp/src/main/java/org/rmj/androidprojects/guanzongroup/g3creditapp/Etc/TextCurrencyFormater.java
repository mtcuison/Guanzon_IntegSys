package org.rmj.androidprojects.guanzongroup.g3creditapp.Etc;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.StringTokenizer;

public class TextCurrencyFormater implements TextWatcher {

    private TextInputEditText textInputEditText;

    public TextCurrencyFormater(TextInputEditText textInputEditText){
        this.textInputEditText = textInputEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        FormatCurrency(textInputEditText);
    }

    private void FormatCurrency(TextInputEditText txt){
        try
        {
            txt.removeTextChangedListener(this);
            String value = Objects.requireNonNull(txt.getText()).toString();


            if (!value.equals(""))
            {

                if(value.startsWith(".")){
                    txt.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){
                    txt.setText("");

                }


                String str = txt.getText().toString().replaceAll(",", "");
                txt.setText(getDecimalFormattedString(str));
                txt.setSelection(txt.getText().toString().length());
            }
            txt.addTextChangedListener(this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            txt.addTextChangedListener(this);
        }

    }

    private static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        StringBuilder str3 = new StringBuilder();
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = new StringBuilder(".");
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3.append(".").append(str2);
                return str3.toString();
            }
            if (i == 3)
            {
                str3.insert(0, ",");
                i = 0;
            }
            str3.insert(0, str1.charAt(k));
            i++;
        }

    }
}
