package com.example.application2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "MY_TEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonInit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.help_item:
                Toast.makeText(this, "hlep",Toast.LENGTH_LONG).show();
                break;
            case R.id.quit_item:
                finish();
                default:
                    break;
        }
        return true;
    }

    void mButtonInit() {
        Button AnrTest = (Button) findViewById(R.id.anr_test);
        AnrTest.setOnClickListener(this);
        Button ReadBrand = (Button) findViewById(R.id.read_brand);
        ReadBrand.setOnClickListener(this);
        Button DataTraffic = (Button) findViewById(R.id.data_traffic);
        DataTraffic.setOnClickListener(this);
        Button LayoutStudy = (Button) findViewById(R.id.layout_study);
        LayoutStudy.setOnClickListener(this);
        Button PingTest = (Button) findViewById(R.id.ping_test);
        PingTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anr_test:
                Toast.makeText(this, "anr test", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,AnrTest.class));
                break;
            case R.id.read_brand:
                String brand = Build.BRAND;
                Log.i(TAG, "brand: " + brand);
                Toast.makeText(this, "build brand: " + brand, Toast.LENGTH_SHORT).show();
                break;
            case R.id.data_traffic:
                Toast.makeText(this, "read data traffic", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,DataTraffic.class));
                break;
            case R.id.layout_study:
                Toast.makeText(this, "layout study", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,LayoutStudy.class));
                break;
            case R.id.ping_test:
                Toast.makeText(this, "ping test", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,PingTest.class));
                break;
                default:
                    break;
        }
    }
}
