package com.example.privatednsautomate;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class StorageHelper {

    public static String DNS_PRIMARY = "primary";
    public static String DNS_SECONDARY = "secondary";
    public static String DNS_Entry = "dns_entry";

    private StorageHelper() {
    }

    public static void SavePrimaryDNS(String dns, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(DNS_PRIMARY, dns);
        edit.commit();//storing
    }

    public static String GetPrimaryDNS(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(DNS_PRIMARY,"");
    }

    public static void SaveSecondaryDNS(String dns, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(DNS_SECONDARY, dns);
        edit.commit();//storing
    }

    public static String GetSecondaryDNS(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(DNS_SECONDARY,"");
    }
}
