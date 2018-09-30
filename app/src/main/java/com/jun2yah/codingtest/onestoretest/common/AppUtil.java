package com.jun2yah.codingtest.onestoretest.common;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.DecimalFormat;

public class AppUtil {
    private static final String TAG = "AppUtil";

    /**
     * 구글 계정 가져오기 (1건)
     *
     * @param context
     * @return
     */
    public static String getGoogleAccount(Context context) {
        String googleAccount = "";

        Account[] accounts = AccountManager.get(context).getAccounts();

        for (Account account : accounts) {
            if (account.name.contains("gmail.com")) {
                googleAccount = account.name;
                Log.d(TAG, "Account Type :" + account.type + ", Account Name : " + account.name);
                break;
            }
        }
        return googleAccount;
    }

    /**
     * 숫자 콤마 붙이기
     *
     * @param number
     * @return
     */
    public static String getCommaString(long number) {
        return new DecimalFormat("#,###").format(number);
    }

    /**
     * 통신사 가져오기
     * MCC + MNC
     *
     * @param context
     * @return
     */
    public static String getTelecom(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = telephonyManager.getSimOperator();
        String telecom = "N/A";

        if (networkOperator != null) {
            if (networkOperator.equals("45005") || networkOperator.equals("45011") || networkOperator.equals("45003")) {
                telecom = "SKT";
            } else if (networkOperator.equals("45008") || networkOperator.equals("45004") || networkOperator.equals("45002")) {
                telecom = "KT";
            } else if (networkOperator.equals("45006")) {
                telecom = "LGU+";
            }
        }

        return telecom;
    }
}
