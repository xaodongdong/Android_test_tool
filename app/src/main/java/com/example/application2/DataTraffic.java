package com.example.application2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.net.TrafficStats.getMobileRxBytes;
import static android.net.TrafficStats.getMobileTxBytes;
import static android.net.TrafficStats.getTotalRxBytes;
import static android.net.TrafficStats.getTotalTxBytes;

public class DataTraffic extends AppCompatActivity implements View.OnClickListener  {

    public static final long KB_IN_BYTES = 1024;
    public static final long MB_IN_BYTES = KB_IN_BYTES * 1024;
    public static final long GB_IN_BYTES = MB_IN_BYTES * 1024;
    public static final long TB_IN_BYTES = GB_IN_BYTES * 1024;

    TextView WriteDataTx;
    TextView WriteDataRx;
    ListView Data_histroy;
    public long DataTx = 0;
    public long DataRx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_traffic);

        Button ReadData = (Button) findViewById(R.id.read_data);
        ReadData.setOnClickListener(this);

        WriteDataTx = (TextView) findViewById(R.id.data_tx);
        WriteDataRx = (TextView) findViewById(R.id.data_rx);
        mSetData(DataTx,"Tx");
        mSetData(DataRx,"Rx");

//        Data_histroy = (ListView) findViewById(R.id.data_histroy);
//        ListAdapter listAdapter = ;
//        Data_histroy.setAdapter(listAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_data:
                Toast.makeText(this, "read data traffic", Toast.LENGTH_SHORT).show();
                mReadData();
                mSetData(DataTx,"Tx");
                mSetData(DataRx,"Rx");
                break;
            default:
                break;
        }
    }
    public void mSetData(long data,String TxOrRx) {
        String mByteSize = "B";
        mByteSize = "B";

        if( DataTx/KB_IN_BYTES <= 0 ) { //B
            mByteSize = "B";
        }else if(DataTx/MB_IN_BYTES <= 0){ //KB
            mByteSize = "KB";
            data = data/KB_IN_BYTES;
        }else if(DataTx/GB_IN_BYTES <= 0){ //MB
            mByteSize = "MB";
            data = data/MB_IN_BYTES;
        }else if(DataTx/TB_IN_BYTES <= 0){ //GB
            mByteSize = "GB";
            data = data/GB_IN_BYTES;
        }else{
            mByteSize = "TB";
            data = data/TB_IN_BYTES;
        }
        if( TxOrRx == "Tx" ){
            String outTx = "发送：" + data + mByteSize;
            WriteDataTx.setText(outTx);
        }else{
            String outRx = "接收：" + data + mByteSize;
            WriteDataRx.setText(outRx);
        }
    }

    public void mReadData() {
//        static long  getMobileRxBytes()  //获取通过移动数据网络收到的字节总数
//        static long  getMobileTxBytes()  //通过移动数据网发送的总字节数
//        static long  getTotalRxBytes()  //获取设备总的接收字节数
//        static long  getTotalTxBytes()  //获取设备总的发送字节数
        DataRx = getMobileRxBytes();
        DataTx = getMobileTxBytes();
    }
}
