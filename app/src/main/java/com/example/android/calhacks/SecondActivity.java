package com.example.android.calhacks;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class SecondActivity extends AppCompatActivity{
    BluetoothAdapter mBluetoothAdapter;
    String SID;
    String MAC;
    String timeT;
    String urlGet;
    String secNum;
    String course;

    boolean chamisul = false;
    pl.droidsonroids.gif.GifTextView checkgif;
//    BTReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkgif = (pl.droidsonroids.gif.GifTextView)findViewById(R.id.checkgif);


        Log.d("onCreate","ok");
        Intent intent = getIntent();
        SID = intent.getStringExtra("ID");
        secNum = intent.getStringExtra("classNo");
        course = intent.getStringExtra("theclass");

        TextView text = (TextView)findViewById(R.id.course);
        text.setText(course);

        TextView text1 = (TextView)findViewById(R.id.studentno);
        text1.setText("Welcome, Student No."+SID);


//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(getApplicationContext(),"Bluetooth Unavailable", Toast.LENGTH_SHORT).show();
//        }else if (!mBluetoothAdapter.isEnabled()) {

//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_BLUETOOTH);
        }

//
//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.d("MbluetoothAdapter", "mReceiver Received");
//        }
//    };

//    public void searchMAC (View v){
//        try{
//            if (device.getName().length() != 0) {
//                TextView textView = (TextView) findViewById(R.id.stuff);
//                //textView.setText("Connection Successful!");
//                sendCode();
//            }
//        }catch (Exception e){
//            //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//            Log.d("Vince1",e.getMessage());
//        }
//    }

    public void sendCode(View v){
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder time = new StringBuilder(sdf.format(now));
        timeT = time.toString();
        Toast.makeText(getApplicationContext(),"Time at: " + time.toString(),Toast.LENGTH_LONG).show();
        Log.d("Vince2",time.toString());
        request();
    }

    public void request(){

        urlGet = "Hi"; //localhost:3000/checkins/post/:student_id/:section_id/:time

        Log.d(urlGet, "");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Request attendance?");
        builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BackgroundTask bgt = new BackgroundTask();
                bgt.execute();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class BackgroundTask extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            //StringBuffer buffer = new StringBuffer();
            try {
                //READY
                String pageStr = "https://tickin.herokuapp.com/post/"+SID+"/"+secNum;//3/1";//3: sId, 1:section id//api.thingspeak.com/update?api_key=RDQW0LWYPZO10LH1&field1=" + urlGet;
                URL url = new URL(pageStr);
                Log.d("url",pageStr);
                //CONNECT
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                //SETTING
                connection.setConnectTimeout(10000);//wait for 10 seconds
                connection.setRequestMethod("GET");//request form(Get/post)
                connection.setDoInput(false); //simply connect? or read it?
                //connection.setDoOutput(true);//write?
                //connection.setUseCaches(true);//use Cache? Cache: saved memory of history. Used for frequently visiting sites/non changing site. Basic 1 hr

                Log.d("Connected","ok");
                //receive response code(200[yes], 304[redirect-wrong value], 404[wrong request], 500[problem in server])
                int responseCode = connection.getResponseCode();
                Log.d("response code",responseCode+" ok");
                if(responseCode!=406){//200
                    Log.d("response fail","ok");
                    throw new Exception("Bad Response" + responseCode);
                }else{
                    Log.d("response succeeded", "ok");
                    chamisul = true;
                }

            } catch (Exception e) {
                Log.e("BackgroundTask Error", e.getMessage());
//                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("onPost","ok");
            if(chamisul) {
                Log.d("chamisul","ok");
                checkgif.setBackgroundResource(R.drawable.checkgif);
                TextView textView = (TextView) findViewById(R.id.stuff);
                textView.setText("You are Signed in!");
                Button bt = (Button)findViewById(R.id.attBt);
                bt.setText("Attend another class");
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.

    }

}
