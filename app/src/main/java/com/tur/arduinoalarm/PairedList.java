package com.tur.arduinoalarm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PairedList extends AppCompatActivity {

    Button refresh;
    ListView lv;

    List<String> btNameList = new ArrayList<>();
    List<String> btAddress = new ArrayList<>();

    private BluetoothAdapter myBluetooth = null;
    //private Set pairedDevices;

    public static String mainBTName;
    public static String mainBTAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired);

        refresh = (Button)findViewById(R.id.refresh);
        lv = (ListView)findViewById(R.id.lv);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        getPairedDeviceList();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPairedDeviceList();
            }
        });


    }

    public void getPairedDeviceList(){

        btNameList.clear();
        btAddress.clear();
        // Get paired devices.
        Set<BluetoothDevice> pairedDevices = myBluetooth.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                //String deviceName = device.getName();
                //String deviceHardwareAddress = device.getAddress(); // MAC address
                btNameList.add(device.getName());
                btAddress.add(device.getAddress());

            }
        }

        //--
        String[] btNameList1 = btNameList.toArray(new String[btNameList.size()]);
        String[] btAddress1 = btAddress.toArray(new String[btAddress.size()]);
        //Log.d("#########",btAddress.toString());
        lv.setAdapter(new BlueList(this,btNameList1, btAddress1,btNameList1.length));
        //lv.setAdapter(new com.tur.arduinoalarm.BluetoothAdapter(this,btNameList1, btNameList1,btNameList1.length));
       // lv.notify();


        //-----------
    }

    public void connectWithBluetooth(String name, String address){

        mainBTName = name;
        mainBTAdress = address;

        Intent openMain = new Intent(PairedList.this,MainActivity.class);
        startActivity(openMain);
        //finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPairedDeviceList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}

