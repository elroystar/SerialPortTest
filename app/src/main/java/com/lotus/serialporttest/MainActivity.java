package com.lotus.serialporttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private SerialPortUtil serialPortUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册EventBus
        EventBus.getDefault().register(this);

        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil = new SerialPortUtil();
                serialPortUtil.openSerialPort();
            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.closeSerialPort();
            }
        });

        findViewById(R.id.fiveThai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort(CmdConstance.FIVE_THAI_BAHT);
            }
        });

        findViewById(R.id.tenThai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort(CmdConstance.TEN_THAI_BAHT);
            }
        });

        findViewById(R.id.oneEuro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort(CmdConstance.ONE_EURO);
            }
        });

        findViewById(R.id.oneDollar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort(CmdConstance.ONE_DOLLAR);
            }
        });

        findViewById(R.id.washing_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort("01010101");
            }
        });

        findViewById(R.id.washing_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort("02020202");
            }
        });
        findViewById(R.id.washing_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort("03030303");
            }
        });
        findViewById(R.id.washing_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort("04040404");
            }
        });
        findViewById(R.id.washing_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort("05050505");
            }
        });
        findViewById(R.id.washing_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPortUtil.sendSerialPort("06060606");
            }
        });
    }

    /**
     * 用EventBus进行线程间通信，也可以使用Handler
     *
     * @param string
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String string) {
        Log.d("C09Activity", "获取到了从传感器发送到Android主板的串口数据");
        if (string.contains(CmdConstance.REGISTERED)) {
            String id = string.substring(0, string.length() - 2);
            serialPortUtil.sendSerialPort(id);
        }
        if (string.contains(CmdConstance.RESET)) {
            String id = string.substring(0, string.length() - 2);
            serialPortUtil.sendSerialPort(id);
        }
        Toast.makeText(MainActivity.this, "接收到串口指令：" + string, Toast.LENGTH_SHORT).show();
    }

}
