package com.project.xetnghiem.utilities;

import android.text.TextUtils;

import com.google.android.gms.flags.impl.DataUtils;

import java.util.EmptyStackException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isUsernameValid(String username) {
        if (isPhoneValid(username)) {
            return true;
        }
//        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//        Matcher matcher = pattern.matcher(username);
//        if (matcher.matches()) {
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }

    public static boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.matches("^\\d{6,}$");
    }

    public static boolean isNullOrEmpty(String source) {
        return source == null || source.isEmpty();
    }

    public static boolean isPasswordValid(String password) {
        if (isNullOrEmpty(password)) {
            return false;
        }
        return password.length() > 5;
    }

    public static boolean isNameValid(String name) {
        if (isNullOrEmpty(name)) {
            return false;
        }
        return name.length() > 2;
    }

    public static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isDegreeValid(String degree) {
        if (isNullOrEmpty(degree)) {
            return false;
        }
        return degree.length() > 2;
    }

    public static boolean isAddressValid(String address) {
        if (isNullOrEmpty(address)) {
            return false;
        }
        return address.length() > 0;
    }
}
