package com.example.application2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class AnrTest extends AppCompatActivity implements View.OnClickListener {

    String TAG = "ANR_TEST";
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean mEnd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_test);

        Button AnrStart = (Button) findViewById(R.id.start);
        AnrStart.setOnClickListener(this);
        Button AnrEnd = (Button) findViewById(R.id.end);
        AnrEnd.setOnClickListener(this);
        Button BtnReturn = (Button) findViewById(R.id.btn_return);
        BtnReturn.setOnClickListener(this);

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
                public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        for(;;){
                            Log.i(TAG, "run: -----timer-----");
                        }
                    }
                });
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                Toast.makeText(this, "anr test start", Toast.LENGTH_SHORT).show();
                if(!mEnd) {
                    mTimer.schedule(mTimerTask, 0);
                }
                mEnd = true;
                break;
            case R.id.end:
                Toast.makeText(this, "anr test end", Toast.LENGTH_SHORT).show();
                if(mEnd){
                    mTimer.cancel();
                }
                mEnd = false;
                break;
            case R.id.btn_return:
                Toast.makeText(this, "return home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AnrTest.this, MainActivity.class));
                break;
            default:
                break;
        }
    }

}
