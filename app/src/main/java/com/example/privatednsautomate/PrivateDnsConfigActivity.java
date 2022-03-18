package com.example.privatednsautomate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
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

import com.example.privatednsautomate.advanced.NetworkCallback;

public class PrivateDnsConfigActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_dns_config);

        final SharedPreferences togglestates = getSharedPreferences("togglestates", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = togglestates.edit();

        final CheckBox checkoff = findViewById(R.id.check_off);
        final CheckBox checkauto = findViewById(R.id.check_auto);
        final CheckBox checkon = findViewById(R.id.check_on);
        final CheckBox checkprimary = findViewById(R.id.check_primary);
        final CheckBox checksecondary = findViewById(R.id.check_secondary);

        final EditText texthostname = findViewById(R.id.text_hostname);
        final EditText textsecondaryhostname = findViewById(R.id.text_secondary_hostname);

        final Button okbutton = findViewById(R.id.button_ok);

        if ((!hasPermission()) || togglestates.getBoolean("first_run", true) ){
            HelpMenu();
            editor.putBoolean("first_run", false).commit();
        }

        if (togglestates.getBoolean("toggle_off", true)) {
            checkoff.setChecked(true);
        }

        if (togglestates.getBoolean("toggle_auto", true)) {
            checkauto.setChecked(true);
        }

        if (togglestates.getBoolean("toggle_on", true)) {
            checkon.setChecked(true);
            //texthostname.setEnabled(true);

            checkprimary.setEnabled(false);
            checkprimary.setChecked(true);
        } else {
            //texthostname.setEnabled(false);
            checkprimary.setEnabled(false);
            checkprimary.setChecked(false);
        }



        if(togglestates.getBoolean("toggle_secondary",true))
        {
            checksecondary.setChecked(true);
            textsecondaryhostname.setEnabled(true);
        }
        else
        {
            textsecondaryhostname.setEnabled(false);
        }

        //String dnsprovider = Settings.Global.getString(getContentResolver(), "private_dns_specifier");
        //if (dnsprovider != null) {
        //    texthostname.setText(dnsprovider);
        //    StorageHelper.SavePrimaryDNS(dnsprovider,this);
        //}

        String primaryDNS = StorageHelper.GetPrimaryDNS(this);
        if(!primaryDNS.isEmpty())
        {
            texthostname.setText(primaryDNS);
        }

        String secondaryDNS = StorageHelper.GetSecondaryDNS(this);
        if(!secondaryDNS.isEmpty())
        {
            textsecondaryhostname.setText(secondaryDNS);
        }

        checkoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkoff.isChecked()) {
                    editor.putBoolean("toggle_off", true);
                } else {
                    editor.putBoolean("toggle_off", false);
                }
            }
        });

        checkauto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkauto.isChecked()) {
                    editor.putBoolean("toggle_auto", true);
                } else {
                    editor.putBoolean("toggle_auto", false);
                }
            }
        });

        checkon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkon.isChecked()) {
                    editor.putBoolean("toggle_on", true);
                    //texthostname.setEnabled(true);
                    checkprimary.setChecked(true);
                    checksecondary.setEnabled(true);

                } else {
                    editor.putBoolean("toggle_on", false);
                    //texthostname.setEnabled(false);
                    checkprimary.setChecked(false);
                    checksecondary.setChecked(false);
                    checksecondary.setEnabled(false);
                }
            }
        });


        checkprimary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkprimary.isChecked()) {
                    editor.putBoolean("toggle_primary", true);
                    texthostname.setEnabled(true);
                } else {
                    editor.putBoolean("toggle_primary", false);
                    texthostname.setEnabled(false);
                }
            }
        });

        checksecondary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checksecondary.isChecked()) {
                    editor.putBoolean("toggle_secondary", true);
                    textsecondaryhostname.setEnabled(true);
                } else {
                    editor.putBoolean("toggle_secondary", false);
                    textsecondaryhostname.setEnabled(false);
                }
            }
        });

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission()) {
                    if (checkon.isChecked()) {
                        if (texthostname.getText().toString().isEmpty()) {
                            Toast.makeText(PrivateDnsConfigActivity.this, R.string.toast_no_dns, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(checksecondary.isChecked() && textsecondaryhostname.getText().toString().isEmpty())
                        {
                            Toast.makeText(PrivateDnsConfigActivity.this, R.string.toast_no_secondary_dns, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        StorageHelper.SavePrimaryDNS(texthostname.getText().toString(),getApplicationContext());
                        StorageHelper.SaveSecondaryDNS(textsecondaryhostname.getText().toString(),getApplicationContext());
                        Settings.Global.putString(getContentResolver(), "private_dns_specifier", texthostname.getText().toString());

                    }
                    editor.commit();
                    Toast.makeText(PrivateDnsConfigActivity.this, R.string.toast_changes_saved, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PrivateDnsConfigActivity.this, getString(R.string.toast_no_permission), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(new NetworkCallback(this.getBaseContext()));
    }

    public boolean hasPermission() {
        return checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != PackageManager.PERMISSION_DENIED;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_appinfo) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } else if (id == R.id.action_fdroid) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(getString(R.string.url_fdroid)));
            startActivity(intent);
        } else if (id == R.id.action_help) {
            HelpMenu();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void HelpMenu() {
        LayoutInflater layoutInflater = LayoutInflater.from(PrivateDnsConfigActivity.this);
        View helpView = layoutInflater.inflate(R.layout.dialog_help, null);

        VideoView videoView = helpView.findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.terminal));
        videoView.start();

        AlertDialog helpDialog = new AlertDialog
                .Builder(PrivateDnsConfigActivity.this)
                .setMessage(R.string.message_help)
                .setPositiveButton(android.R.string.ok, null)
                .setView(helpView)
                .create();
        helpDialog.show();

    }
}
