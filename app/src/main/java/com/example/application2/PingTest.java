package com.example.application2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class PingTest extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "Ping Test";
    private EditText mFileNameText;
    private EditText mPingIpText;
    private EditText mPingCountText;
    private Switch mStartSwitch;
    private String mFileName;
    private String mPingIp;
    private String mPingCount; //24h
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean mEnd = false;
    private String mDirName = "/data/media/0/PingLog";
    private String mFileTimeName;
    private static Process sProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_test);

        mFileName = getResources().getString(R.string.file_name);
        mPingIp = getResources().getString(R.string.ping_ip);
        mPingCount = getResources().getString(R.string.ping_count); //24h
        mButtonInit();
        mEditTextInit();
        mSwtichInit();
        RootCommand("mkdir " + mDirName );
        Log.i(TAG, "run:----" + Thread.currentThread().getName());
    }

    void mTimerInit() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "run:----" + Thread.currentThread().getName() + "---"+mFileTimeName);
                RootCommand("ping" + " -c " + mPingCount + " " + mPingIp + " > " + mDirName + mFileTimeName);
                //RootCommand("ping 127.0.0.1 > " + mDirName + mFileTimeName);
            }
        };
    }

    void mStopTimer() {
        mTimer.cancel();
        mTimerTask.cancel();
    }

    void mSwtichInit() {
        mStartSwitch = (Switch) findViewById(R.id.switch1);
        mStartSwitch.setOnCheckedChangeListener(this);
    }

    void mEditTextInit() {
        mFileNameText = (EditText) findViewById(R.id.editText1);
        mPingIpText = (EditText) findViewById(R.id.editText2);
        mPingCountText = (EditText) findViewById(R.id.editText3);
    }

    void mButtonInit() {
        Button FileNameButton = (Button) findViewById(R.id.button1);
        FileNameButton.setOnClickListener(this);
        Button PingIpButton = (Button) findViewById(R.id.button2);
        PingIpButton.setOnClickListener(this);
        Button PingCountButton = (Button) findViewById(R.id.button3);
        PingCountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                mFileName = mFileNameText.getText().toString();
                Toast.makeText(this, "FileName is " + mFileName, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                mPingIp = mPingIpText.getText().toString();
                Toast.makeText(this, "PingIp is " + mPingIp, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                mPingCount = mPingCountText.getText().toString();
                Toast.makeText(this, "PingCount is " + mPingCount, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SimpleDateFormat logDate = new SimpleDateFormat("yyyyMMddHHmmss");

        if (isChecked) {
            Toast.makeText(this, "Start ping test", Toast.LENGTH_SHORT).show();
            if (!mEnd) {
                Date date = new Date(System.currentTimeMillis());
                mFileTimeName = mFileName + logDate.format(date) + ".log";
                RootCommand("touch " + mDirName + "/" +mFileTimeName);
                mTimerInit();
                mTimer.schedule(mTimerTask, 0);
            }
            mEnd = true;
        } else {
            Toast.makeText(this, "Stop ping test", Toast.LENGTH_SHORT).show();
            if (mEnd) {
                //mStopTimer();
                sProcess.destroy();
            }
            mEnd = false;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStopTimer();
    }

    public static boolean RootCommand(String command) {
        sProcess = null;
        DataOutputStream os = null;
      /*  BufferedReader buf=null;
        InputStream is = null;*/
        try {
            sProcess = Runtime.getRuntime().exec("su");//执行这一句，superuser.apk就会弹出授权对话框
            os = new DataOutputStream(sProcess.getOutputStream());
            os.writeBytes(command + "\n");
/*
            buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
*/
         /*   String result=buf.readLine();
            Log.d(TAG,"result is "+result);*/
            os.writeBytes("exit\n");
            os.flush();
            sProcess.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "获取root权限失败： " + e.getMessage());
            return false;
        }
        Log.d(TAG, "获取root权限成功");
        return true;
    }
}
