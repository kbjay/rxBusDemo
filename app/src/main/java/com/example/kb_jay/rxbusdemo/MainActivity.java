package com.example.kb_jay.rxbusdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * * 实现rxbus来传递信息
 *
 * @author kb_jay
 *         created at 2018/3/27 下午2:25
 */
public class MainActivity extends AppCompatActivity {

    private Button mBt;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBt = this.findViewById(R.id.bt_jump);
        mTv = this.findViewById(R.id.tv_test);
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SendMsgActivity.class);
                startActivity(intent);
            }
        });

        regist();
    }

    private void regist() {
        Subscription subscription = RxBusUtils.getInstance().tObservable(TransInstance.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TransInstance>() {
                    @Override
                    public void call(TransInstance transInstance) {
                        mTv.setText(transInstance.getName() + ":" + transInstance.getAge());
                    }
                });
        RxBusUtils.getInstance().addSubscription(this,subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBusUtils.getInstance().unSubscribe(this);
    }
}
