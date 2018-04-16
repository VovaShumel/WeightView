package com.livejournal.lofe.weightview;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.GregorianCalendar;

import static android.util.Log.d;
import static com.livejournal.lofe.weightview.MyUtil.log;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DB db;
    EditText newWeight_edt;
    Button addNewWeight_btn;
    TextView tv;
    HTTPD httpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newWeight_edt = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String ipAddr = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        tv.setText("http://" + ipAddr);

        addNewWeight_btn = (Button) findViewById(R.id.button);
        addNewWeight_btn.setOnClickListener(this);

        db = new DB(this);
        db.open();

        httpd = new HTTPD();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                double weightDbl;
                try {
                    Number formatted = NumberFormat.getInstance().parse(newWeight_edt.getText().toString());
                    weightDbl = formatted.doubleValue();
                } catch (ParseException e) {
                    //System.out.println("Error parsing cost string " + editText.getText().toString());
                    weightDbl = 0.0;
                }
                int weight = (int)(weightDbl * 1000);
                long ms = GregorianCalendar.getInstance().getTimeInMillis();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        httpd.destroy();
        db.close();
    }
}
