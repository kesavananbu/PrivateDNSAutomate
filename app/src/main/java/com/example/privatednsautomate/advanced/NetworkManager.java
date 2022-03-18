package com.example.privatednsautomate.advanced;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class NetworkManager extends BroadcastReceiver {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        int network_type = NetworkUtil.getConnectivityStatus(context);
        String network = NetworkUtil.getConnectivityStatusString(context);
        if(network_type == TYPE_WIFI)
        {
            String ssid_name = getConnectNetworkSSIDString(context,intent);
        }
        else if(network_type == TYPE_MOBILE)
        {

        }
        else if(network_type == TYPE_NOT_CONNECTED)
        {

        }
    }

    public static String getConnectNetworkSSIDString(Context context, Intent intent) {
        String SSID_Name = null;
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            SSID_Name = wifiInfo.getSSID();
            Toast.makeText(context,"Wifi Connected to " + SSID_Name,Toast.LENGTH_LONG);
            Log.i("PrivateDNSLogger","Wifi Connected to " + SSID_Name);
        }
        return SSID_Name;
    }
}
