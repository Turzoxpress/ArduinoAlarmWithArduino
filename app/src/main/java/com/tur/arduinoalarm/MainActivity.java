package com.tur.arduinoalarm;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;

    ImageView onOffButton;
    ImageView leftArrow,rightArrow;
    TextView detectionTxt;
    TextView currentTxt;

    boolean deviceOnOffFlag = true;

    InputStream serialInputStream;
    private SimpleBluetoothDeviceInterface deviceInterface;
    BluetoothManager bluetoothManager;
    MediaPlayer mp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onOffButton = (ImageView)findViewById(R.id.onoffbutton);
        leftArrow = (ImageView)findViewById(R.id.leftarrow);
        rightArrow = (ImageView)findViewById(R.id.rightarrow);
        detectionTxt = (TextView)findViewById(R.id.detectiondistance);
        currentTxt = (TextView)findViewById(R.id.currentdistance);

        onOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                turnOnOffDevice();
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendCommandToBluetooth("d");
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendCommandToBluetooth("i");
            }
        });



            //-----------


       bluetoothManager = BluetoothManager.getInstance();

        connectDevice(PairedList.mainBTAdress);

        mp = MediaPlayer.create(this, R.raw.bell);





    }

    private void turnOnOffDevice() {

        if(deviceOnOffFlag){
            deviceOnOffFlag = false;
        }else {
            deviceOnOffFlag = true;
        }

        if(deviceOnOffFlag){

            sendCommandToBluetooth("1");
            onOffButton.setImageResource(R.drawable.on);
        }else if(!deviceOnOffFlag) {

            sendCommandToBluetooth("0");
            onOffButton.setImageResource(R.drawable.off);
        }

    }



    private void sendCommandToBluetooth(String st)
    {

        deviceInterface.sendMessage(st);
    }

    //--

    private void connectDevice(String mac) {
        bluetoothManager.openSerialDevice(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }

    private void onConnected(BluetoothSerialDevice connectedDevice) {
        // You are now connected to this device!
        // Here you may want to retain an instance to your device:
        deviceInterface = connectedDevice.toSimpleDeviceInterface();

        // Listen to bluetooth events
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);

        // Let's send a message:
       //deviceInterface.sendMessage("Hello world!");
    }

    private void onMessageSent(String message) {
        // We sent a message! Handle it here.
        //Toast.makeText(MainActivity.this, "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
        Log.d("#####",message);
    }

    private void onMessageReceived(String message) {
        // We received a message! Handle it here.
        //Toast.makeText(MainActivity.this, "Received a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
        Log.d("#####",message);
        handleMsg(message);

    }

    private void onError(Throwable error) {
        // Handle the error
    }

    private void handleMsg(String msg){

        String[] separated = msg.split(",");

        detectionTxt.setText("Detection Distance : "+ separated[0]);
        currentTxt.setText("Current Distant : "+separated[1]);



        if(separated[2].equalsIgnoreCase("1")){

            mp.start();
        }

    }



    //-------------------



    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();



        finish();
    }
}
