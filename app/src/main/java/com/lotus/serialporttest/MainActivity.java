package com.lotus.serialporttest;

import android.content.Intent;
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

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册EventBus
        EventBus.getDefault().register(this);

        // 返回安卓
        findViewById(R.id.bt_android).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将应用退到桌面上，保留自身
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

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

        /*findViewById(R.id.fiveThai).setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    /**
     * 用EventBus进行线程间通信，也可以使用Handler
     *
     * @param string
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String string) {
        Log.d("MainActivity", "获取到了从传感器发送到Android主板的串口数据");
        /*if (string.contains(CmdConstance.REGISTERED)) {
            String id = string.substring(0, string.length() - 2);
            serialPortUtil.sendSerialPort(id);
        }
        if (string.contains(CmdConstance.RESET)) {
            String id = string.substring(0, string.length() - 2);
            serialPortUtil.sendSerialPort(id);
        }*/
        if (string.equals("010101017D")) {
            flag = true;
            serialPortUtil.sendSerialPort("01010101");
            return;
        }
        if (string.contains("01010101")) {
            serialPortUtil.sendSerialPort("01010101");
        }
        if (string.equals(CmdConstance.REGISTER_ASK)) {
            if (flag) {
                serialPortUtil.sendSerialPort("01010101");
                flag = false;
            }
        }
        if (string.equals(CmdConstance.RESET)) {
            flag = true;
        }
        Toast.makeText(MainActivity.this, "接收到串口指令：" + string, Toast.LENGTH_SHORT).show();
    }

}
