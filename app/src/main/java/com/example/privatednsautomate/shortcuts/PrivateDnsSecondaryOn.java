package com.example.privatednsautomate.shortcuts;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.privatednsautomate.R;
import com.example.privatednsautomate.StorageHelper;

public class PrivateDnsSecondaryOn extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(hasPermission())
        {
            Settings.Global.putString(getContentResolver(), "private_dns_mode", DNS_MODE_ON);
            Settings.Global.putString(getContentResolver(), "private_dns_specifier", StorageHelper.GetSecondaryDNS(this));
            Log.i("DNS_ON","Triggered the Secondary DNS");
            Log.i("DNS_ON","StorageHelper.GetSecondaryDNS(this) : "+StorageHelper.GetSecondaryDNS(this));
        }
        else if (!(hasPermission())) {
            Toast.makeText(this, getString(R.string.toast_no_permission), Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    public static String DNS_MODE_ON = "hostname";

    public boolean hasPermission() {
        return checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != PackageManager.PERMISSION_DENIED;
    }
}