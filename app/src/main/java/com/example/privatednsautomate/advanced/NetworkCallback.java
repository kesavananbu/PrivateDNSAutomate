package com.example.privatednsautomate.advanced;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;

public class NetworkCallback extends ConnectivityManager.NetworkCallback {

    public final Context context;

    public NetworkCallback(Context context)
    {
        this.context = context;
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
    }
}
