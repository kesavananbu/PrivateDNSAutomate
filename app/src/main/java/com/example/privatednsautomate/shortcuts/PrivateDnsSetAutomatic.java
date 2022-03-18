package com.example.privatednsautomate.shortcuts;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.example.privatednsautomate.R;

public class PrivateDnsSetAutomatic extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(hasPermission())
        {
            Settings.Global.putString(getContentResolver(), "private_dns_mode", DNS_MODE_AUTO);
        }
        else if (!(hasPermission())) {
            Toast.makeText(this, getString(R.string.toast_no_permission), Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    public static String DNS_MODE_AUTO = "opportunistic";

    public boolean hasPermission() {
        return checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != PackageManager.PERMISSION_DENIED;
    }
}