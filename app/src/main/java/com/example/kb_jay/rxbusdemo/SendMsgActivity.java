package com.example.kb_jay.rxbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SendMsgActivity extends AppCompatActivity {

    private Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        mBt = (Button) this.findViewById(R.id.bt_send_msg);
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBusUtils.getInstance().send(new TransInstance("zpy",25));
            }
        });

    }
}
