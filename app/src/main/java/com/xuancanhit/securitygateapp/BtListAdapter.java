package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BtListAdapter extends BaseAdapter {

    private static final int RESOURCE_LAYOUT = R.layout.list_item;

    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private LayoutInflater inflater;
    private int iconType;
    BtListAdapter mContext = this;
    Context context;

    public BtListAdapter(){}

    public BtListAdapter(Context context, ArrayList<BluetoothDevice> bluetoothDevices, int iconType) {
        this.bluetoothDevices = bluetoothDevices;
        this.iconType = iconType;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return bluetoothDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(RESOURCE_LAYOUT, parent, false);

        BluetoothDevice device = bluetoothDevices.get(position);
        if (device != null) {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ((TextView) view.findViewById(R.id.tv_name)).setText(device.getName());
                ((TextView) view.findViewById(R.id.tv_address)).setText(device.getAddress());
                ((ImageView) view.findViewById(R.id.iv_icon)).setImageResource(iconType);
                return view;
            }
        }
        return view;
    }
}




