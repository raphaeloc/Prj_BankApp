package com.example.prj_bankapp.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    public static boolean validarEmail(String email){
        Matcher matcher;
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validarCpf(String cpf){
        if(cpf.matches("[0-9]+") && cpf.length() == 11){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        Log.d("Abacaxi", "isValidPassword: " + "/ " + password + " / " + String.valueOf(matcher.matches()));

        return matcher.matches();

    }

}
