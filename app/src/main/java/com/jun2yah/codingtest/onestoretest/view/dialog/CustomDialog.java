package com.jun2yah.codingtest.onestoretest.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jun2yah.codingtest.onestoretest.R;

public class CustomDialog extends Dialog implements View.OnClickListener{
    private Context context;

    TextView textViewTime;

    TextView textViewClose;
    TextView textViewCancel;

    private static final int COUNT_DOWN_SECOND = 15;    // Count Down 초

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        textViewTime = (TextView) findViewById(R.id.textViewTime);

        textViewClose = (TextView) findViewById(R.id.textViewClose);
        textViewCancel = (TextView) findViewById(R.id.textViewCancel);

        textViewClose.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        startTimer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewClose:
//                finish();
                break;
            case R.id.textViewCancel:
//                cancel();
                break;
        }
    }

    public void startTimer() {
        new CountDownTimer(COUNT_DOWN_SECOND * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int remainTime = (int) millisUntilFinished / 1000 + 1;
                textViewTime.setText("남은 시간 : " + remainTime + "초");
            }

            @Override
            public void onFinish() {
                textViewTime.setText("남은 시간 : 0초");
            }
        }.start();
    }
}
