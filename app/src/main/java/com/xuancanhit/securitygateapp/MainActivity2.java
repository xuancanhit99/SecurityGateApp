package com.xuancanhit.securitygateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity implements
        AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, LocationListener {

    private Button btnValue, btnUp, btnDown, btnA, btnB, btnC, btnD;
    private TextView tvPtOpen;
    private ImageView ivBack;

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i = 0;

    double value = 0;

    private static final String TAG = MainActivity2.class.getSimpleName();
    public static final int REQUEST_CODE_LOC = 1;

    private static final int REQ_ENABLE_BT = 10;
    public static final int BT_BOUNDED = 21;
    public static final int BT_SEARCH = 22;


    private FrameLayout frameMessage;
    private LinearLayout frameControls;

    private RelativeLayout frameLedControls;
    private Button btnDisconnect;


    private Button btn_Send;
    private Button btn_Update;
    private Button btn_Confirm;
    private EditText et_c;
    private EditText et_d;
    private EditText et_e;
    private EditText et_m0;
    private EditText et_S;
    private EditText et_Cx;
    private EditText et_Cy;
    private EditText et_T;
    private EditText et_teta;
    private EditText et_phi;
    private EditText et_f;
    private EditText et_Lvpp;
    private EditText et_lat_start;
    private EditText et_long_start;
    private EditText et_abr;
    private EditText et_w;
    private EditText et_fpr;
    private EditText et_Cxpr;
    private EditText et_Cypr;


    private Switch switchEnableBt;
    private Button btnEnableSearch;
    private ProgressBar pbProgress;
    private ListView listBtDevices;

    private BluetoothAdapter bluetoothAdapter;
    private BtListAdapter listAdapter;
    private ArrayList<BluetoothDevice> bluetoothDevices;

    private ConnectThread connectThread;
    private ConnectedThread connectedThread;

    private ProgressDialog progressDialog;


    private String lastSensorValues = "";
    private Handler handler;
    private Runnable timer;
    private int xTempLastValue = 0;
    private int xRandLastValue = 0;

    //private PrefModel preference;

    private Location latlong;
    private double lat = 0, lg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();


        frameMessage = findViewById(R.id.frame_message);
        frameControls = findViewById(R.id.frame_control);

        switchEnableBt = findViewById(R.id.switch_enable_bt);
        btnEnableSearch = findViewById(R.id.btn_enable_search);
        pbProgress = findViewById(R.id.pb_progress);
        listBtDevices = findViewById(R.id.lv_bt_device);

        frameLedControls = findViewById(R.id.frameLedControls);
        btnDisconnect = findViewById(R.id.btn_disconnect);

        btn_Send = findViewById(R.id.bt_Send);
        btn_Update = findViewById(R.id.bt_Update);
        btn_Confirm = findViewById(R.id.bt_Confirm);

//        et_c = findViewById(R.id.et_c);
//        et_d = findViewById(R.id.et_d);
//        et_e = findViewById(R.id.et_e);
//        et_m0 = findViewById(R.id.et_m0);
//        et_S = findViewById(R.id.et_S);
//        et_Cx = findViewById(R.id.et_Cx);
//        et_Cy = findViewById(R.id.et_Cy);
//        et_T = findViewById(R.id.et_T);
//        et_teta = findViewById(R.id.et_teta);
//        et_phi = findViewById(R.id.et_phi);
//        et_f = findViewById(R.id.et_f);
//        et_Lvpp = findViewById(R.id.et_Lvpp);
//        et_lat_start = findViewById(R.id.et_lat_start);
//        et_long_start = findViewById(R.id.et_long_start);
//        et_abr = findViewById(R.id.et_abr);
//        et_w = findViewById(R.id.et_w);
//        et_fpr = findViewById(R.id.et_fpr);
//        et_Cxpr = findViewById(R.id.et_Cxpr);
//        et_Cypr = findViewById(R.id.et_Cypr);


        //preference = new PrefModel(this);


        btnEnableSearch.setOnClickListener(this);
        listBtDevices.setOnItemClickListener(this);
        btn_Confirm.setOnClickListener(this);
        btn_Update.setOnClickListener(this);
        btn_Send.setOnClickListener(this);



        switchEnableBt.setOnCheckedChangeListener(this);
        btnDisconnect.setOnClickListener(this);


        bluetoothDevices = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.connecting));
        progressDialog.setMessage(getString(R.string.please_wait));

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, R.string.bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: " + getString(R.string.bluetooth_not_supported));
            finish();
        }

        if (bluetoothAdapter.isEnabled()) {
            showFrameControls();
            switchEnableBt.setChecked(true);
            setListAdapter(BT_BOUNDED);
        }

        //check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {

            //start the program if permission is granted
            doStuff();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_preference) {
            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        cancelTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (connectedThread != null) {
            startTimer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelTimer();

        unregisterReceiver(receiver);

        if (connectThread != null) {
            connectThread.cancel();
        }

        if (connectedThread != null) {
            connectedThread.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnEnableSearch)) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            enableSearch();
        }
        if (v.equals(btnDisconnect)) {
            cancelTimer();

            if (connectedThread != null) {
                connectedThread.cancel();
            }

            if (connectThread != null) {
                connectThread.cancel();
            }
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            showFrameControls();
        }

        if (v.equals(btn_Send)) {
            enableLed();
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
        if (v.equals(btn_Update)) {
            //enableLed();

            et_c.setText("0");
            et_d.setText("-220");
            et_e.setText("103750");
            et_m0.setText("40000");
            et_f.setText("0.03");
            et_S.setText("127");
            et_Cx.setText("0.06");
            et_Cy.setText("1");
            et_T.setText("15");
            et_teta.setText("0");
            et_phi.setText("45");
            et_Lvpp.setText("2000");
            et_abr.setText("1.5");

            et_lat_start.setText(new DecimalFormat("#.#######").format(lat));
            et_long_start.setText(new DecimalFormat("#.#######").format(lg));
//            et_lat_start.setText("55.801664");
//            et_long_start.setText("37.80561514414269");

            et_w.setText("2");
            et_fpr.setText("0.25");
            et_Cxpr.setText("0.1");
            et_Cypr.setText("1.3");


        }
        if (v.equals(btn_Confirm)) {
            enableLed_confirm();
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.equals(switchEnableBt)) {
            enableBt(isChecked);

            if (!isChecked) {
                showFrameMessage();
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(listBtDevices)) {
            BluetoothDevice device = bluetoothDevices.get(position);
            if (device != null) {
                connectThread = new ConnectThread(device);
                connectThread.start();

                startTimer();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ENABLE_BT) {
            if (resultCode == RESULT_OK && bluetoothAdapter.isEnabled()) {
                showFrameControls();
                setListAdapter(BT_BOUNDED);
            } else if (resultCode == RESULT_CANCELED) {
                enableBt(true);
            }
        }


    }

    private void showFrameMessage() {
        frameMessage.setVisibility(View.VISIBLE);
        frameLedControls.setVisibility(View.GONE);
        frameControls.setVisibility(View.GONE);
    }

    private void showFrameControls() {
        frameMessage.setVisibility(View.GONE);
        frameLedControls.setVisibility(View.GONE);
        frameControls.setVisibility(View.VISIBLE);
    }

    private void showFrameLedControls() {
        frameLedControls.setVisibility(View.VISIBLE);
        frameMessage.setVisibility(View.GONE);
        frameControls.setVisibility(View.GONE);
    }

    private void enableBt(boolean flag) {
        if (flag) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQ_ENABLE_BT);
        } else {
            if (ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                bluetoothAdapter.disable();
                //return;
            }
            //bluetoothAdapter.disable();
        }
    }

    private void setListAdapter(int type) {

        bluetoothDevices.clear();
        int iconType = R.drawable.ic_bluetooth_bounded_device;

        switch (type) {
            case BT_BOUNDED:
                bluetoothDevices = getBoundedBtDevices();
                iconType = R.drawable.ic_bluetooth_bounded_device;
                break;
            case BT_SEARCH:
                iconType = R.drawable.ic_bluetooth_search_device;
                break;
        }
        listAdapter = new BtListAdapter(this, bluetoothDevices, iconType);
        listBtDevices.setAdapter(listAdapter);
    }

    private ArrayList<BluetoothDevice> getBoundedBtDevices() {
        ArrayList<BluetoothDevice> tmpArrayList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();

            if (deviceSet.size() > 0) {
                for (BluetoothDevice device : deviceSet) {
                    tmpArrayList.add(device);
                }
            }
            return tmpArrayList;
        }
        Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();

        if (deviceSet.size() > 0) {
            for (BluetoothDevice device : deviceSet) {
                tmpArrayList.add(device);
            }
        }

        return tmpArrayList;
    }


    private void enableSearch() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            } else {
                accessLocationPermission();
                bluetoothAdapter.startDiscovery();
            }
            //return;
        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            switch (action) {
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    btnEnableSearch.setText(R.string.stop_search);
                    pbProgress.setVisibility(View.VISIBLE);
                    setListAdapter(BT_SEARCH);
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    btnEnableSearch.setText(R.string.start_search);
                    pbProgress.setVisibility(View.GONE);
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device != null) {
                        bluetoothDevices.add(device);
                        listAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    /**
     * Запрос на разрешение данных о местоположении (для Marshmallow 6.0 и выше)
     */
    private void accessLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int accessCoarseLocation = this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFineLocation = this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);

            List<String> listRequestPermission = new ArrayList<String>();

            if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
                listRequestPermission.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
                listRequestPermission.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (!listRequestPermission.isEmpty()) {
                String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
                this.requestPermissions(strRequestPermission, REQUEST_CODE_LOC);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_LOC:

                if (grantResults.length > 0) {
                    for (int gr : grantResults) {
                        // Check if request is granted or not
                        if (gr != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    //TODO - Add your code here to start Discovery
                    doStuff();
                }
                break;
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doStuff();
                } else {

                    finish();
                }
            default:
                return;
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        latlong = location;
        lat = latlong.getLatitude();
        lg = latlong.getLongitude();

    }

    private class ConnectThread extends Thread {

        private BluetoothSocket bluetoothSocket = null;
        private boolean success = false;

        public ConnectThread(BluetoothDevice device) {
            try {
                Method method = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                bluetoothSocket = (BluetoothSocket) method.invoke(device, 1);

                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                if (ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    bluetoothSocket.connect();
                    success = true;
                    progressDialog.dismiss();
                    //return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity2.this, "Не могу соединиться!", Toast.LENGTH_SHORT).show();
                    }
                });

                cancel();
            }

            if (success) {
                connectedThread = new ConnectedThread(bluetoothSocket);
                connectedThread.start();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showFrameLedControls();
                    }
                });
            }
        }

        public boolean isConnect() {
            return bluetoothSocket.isConnected();
        }

        public void cancel() {
            try {
                Log.d(TAG, "cancel: " + this.getClass().getSimpleName());
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConnectedThread extends Thread {

        private final InputStream inputStream;
        private final OutputStream outputStream;

        private boolean isConnected = false;

        public ConnectedThread(BluetoothSocket bluetoothSocket) {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = bluetoothSocket.getInputStream();
                outputStream = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.inputStream = inputStream;
            this.outputStream = outputStream;
            isConnected = true;
        }

        @Override
        public void run() {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            StringBuffer buffer = new StringBuffer();
            final StringBuffer sbConsole = new StringBuffer();

            while (isConnected) {
                try {
                    int bytes = bis.read();
                    buffer.append((char) bytes);
                    int eof = buffer.indexOf("\r\n");

                    if (eof > 0) {
                        sbConsole.append(buffer.toString());
                        lastSensorValues = buffer.toString();
                        buffer.delete(0, buffer.length());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                bis.close();
                cancel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void write(String command) {
            byte[] bytes = command.getBytes();
            if (outputStream != null) {
                try {
                    outputStream.write(bytes);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void cancel() {
            try {
                isConnected = false;
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void enableLed() {
        if (connectedThread != null && connectThread.isConnect()) {
            String command = "a";
            Toast.makeText(this, command, Toast.LENGTH_SHORT).show();
            connectedThread.write(command);
        }
    }
    private void enableLed_down() {
        if (connectedThread != null && connectThread.isConnect()) {
            String command = "b";
            Toast.makeText(this, command, Toast.LENGTH_SHORT).show();
            connectedThread.write(command);
        }
    }

    private void enableLed_confirm() {
        if (connectedThread != null && connectThread.isConnect()) {
            String command = "c";
            Toast.makeText(this, command, Toast.LENGTH_SHORT).show();
            connectedThread.write(command);
        }
    }


    private HashMap parseData(String data) {    // temp:37|humidity:80
        if (data.indexOf('|') > 0) {
            HashMap map = new HashMap();
            String[] pairs = data.split("\\|");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                map.put(keyValue[0], keyValue[1]);
            }
            return map;
        }
        return null;
    }

    private void startTimer() {
        cancelTimer();
        handler = new Handler();
        final MovementMethod movementMethod = new ScrollingMovementMethod();
        Object preference = null;
        handler.postDelayed(timer = new Runnable() {
            @Override
            public void run() {

                HashMap dataSensor = parseData(lastSensorValues);
                if (dataSensor != null) {
                    if (dataSensor.containsKey("Temp") && dataSensor.containsKey("rand")) {
                        int temp = Integer.parseInt(dataSensor.get("Temp").toString());
                        int rand = Integer.parseInt(dataSensor.get("rand").toString().trim());
                        //Toast.makeText(MainActivity2.this, "Millis: " + dataSensor.get("millis"), Toast.LENGTH_SHORT).show();
                    }
                    xTempLastValue++;
                    xRandLastValue++;

                }

                //handler.postDelayed(this, preference.getDelayTimer());
            }
        }, 12);
    }

    private void cancelTimer() {
        if (handler != null) {
            handler.removeCallbacks(timer);
        }
    }

    private void doStuff() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (lm != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                //return;
            }
            //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            //commented, this is from the old version
            // this.onLocationChanged(null);
        }
        Toast.makeText(this, "Waiting for GPS connection!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void initView() {
        btnDown = findViewById(R.id.btn_garage_down);
        btnValue = findViewById(R.id.btn_garage_value);
        btnUp = findViewById(R.id.btn_garage_up);
        btnA = findViewById(R.id.btn_a);
        btnB = findViewById(R.id.btn_b);
        btnC = findViewById(R.id.btn_c);
        btnD = findViewById(R.id.btn_d);
        tvPtOpen = findViewById(R.id.tv_pt_open);
        mProgressBar = findViewById(R.id.progressbar);
        ivBack = findViewById(R.id.iv_gr_back);

//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                backToMenu();
//            }
//        });


        btnValue.setText(String.valueOf(value));
        tvPtOpen.setText("0%");

        btnUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "ResourceAsColor"})
            @Override
            public void onClick(View view) {
                if (value == 0) {
                    value = 0.25;
                    btnValue.setText("1/4");
                    btnD.setVisibility(View.GONE);
                    progressBarAuto(); i=0;
                } else if (value == 0.25) {
                    value = 0.5;
                    btnValue.setText("1/2");
                    btnD.setVisibility(View.GONE);
                    btnC.setVisibility(View.GONE);
                    progressBarAuto(); i=0;
                } else if (value == 0.5) {
                    value = 0.75;
                    btnValue.setText("3/4");
                    btnD.setVisibility(View.GONE);
                    btnC.setVisibility(View.GONE);
                    btnB.setVisibility(View.GONE);
                    progressBarAuto(); i=0;
                } else if (value == 0.75) {
                    value = 1;
                    btnValue.setText("Полностью открытый");;
                    btnD.setVisibility(View.GONE);
                    btnC.setVisibility(View.GONE);
                    btnB.setVisibility(View.GONE);
                    btnA.setVisibility(View.GONE);
                    progressBarAuto(); i=0;
                }
                enableLed();
                Toast.makeText(MainActivity2.this, "success", Toast.LENGTH_SHORT).show();
                //Toast.makeText(GarageActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (value == 0.25) {
                    value = 0;
                    btnValue.setText("Полностью закрытый");
                    btnD.setVisibility(View.VISIBLE);
                    btnC.setVisibility(View.VISIBLE);
                    btnB.setVisibility(View.VISIBLE);
                    btnA.setVisibility(View.VISIBLE);
                    progressBarAuto(); i=0;

                } else if (value == 0.5) {
                    value = 0.25;
                    btnValue.setText("1/4");
                    btnC.setVisibility(View.VISIBLE);
                    btnB.setVisibility(View.VISIBLE);
                    btnA.setVisibility(View.VISIBLE);
                    progressBarAuto(); i=0;
                } else if (value == 0.75) {
                    value = 0.5;
                    btnValue.setText("1/2");
                    btnB.setVisibility(View.VISIBLE);
                    btnA.setVisibility(View.VISIBLE);
                    progressBarAuto(); i=0;
                } else if (value == 1) {
                    value = 0.75;
                    btnValue.setText("3/4");
                    btnA.setVisibility(View.VISIBLE);
                    progressBarAuto(); i=0;
                }
                enableLed_down();
                Toast.makeText(MainActivity2.this, "success", Toast.LENGTH_SHORT).show();

                //Toast.makeText(GarageActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void progressBarAuto() {
        mProgressBar.setProgress(i);
        mCountDownTimer = new CountDownTimer(10000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
                i++;
                int val = (int) i * 100 / (5000 / 1000);
                mProgressBar.setProgress(val);
                if(val <= 100)
                    tvPtOpen.setText(val +"%");

            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
                tvPtOpen.setText("100%");
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        backToMenu();
    }

    private void backToMenu() {
        startActivity(new Intent(MainActivity2.this, MainMenuActivity.class));
        finish();
    }

}