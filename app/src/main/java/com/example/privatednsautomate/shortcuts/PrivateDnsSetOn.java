package com.example.privatednsautomate.shortcuts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.example.privatednsautomate.R;
import com.example.privatednsautomate.StorageHelper;

public class PrivateDnsSetOn extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(hasPermission())
        {
            Settings.Global.putString(getContentResolver(), "private_dns_mode", DNS_MODE_ON);
            Settings.Global.putString(getContentResolver(), "private_dns_specifier", StorageHelper.GetPrimaryDNS(this));
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