package com.example.privatednsautomate.advanced;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;
import android.widget.Toast;

public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(NetworkCapabilities networkcapabilities)
    {
        if (networkcapabilities != null) {
            if(networkcapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return TYPE_WIFI;
            else if(networkcapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkcapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkcapabilities != null) {
            if(networkcapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return TYPE_WIFI;
            else if(networkcapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        Toast.makeText(context,status,Toast.LENGTH_LONG);
        Log.i("PrivateDNSLogger",status);
        return status;
    }
}