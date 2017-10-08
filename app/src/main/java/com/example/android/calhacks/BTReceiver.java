package com.example.android.calhacks;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by vincehan on 10/7/17.
 */

public class BTReceiver extends BroadcastReceiver {
    public BTReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {

            Log.d("onReceive called","ok");
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.d("Device detected", deviceName+ " " + deviceHardwareAddress);

        }
    }
}
