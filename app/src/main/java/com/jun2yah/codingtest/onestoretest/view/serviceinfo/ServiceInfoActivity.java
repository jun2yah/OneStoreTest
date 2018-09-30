package com.jun2yah.codingtest.onestoretest.view.serviceinfo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jun2yah.codingtest.onestoretest.R;
import com.jun2yah.codingtest.onestoretest.common.AppUtil;
import com.jun2yah.codingtest.onestoretest.view.dialog.CustomDialog;
import com.jun2yah.codingtest.onestoretest.view.servicelist.ServiceListActivity;
import com.jun2yah.codingtest.onestoretest.view.setting.SettingActivity;


public class ServiceInfoActivity extends AppCompatActivity {

    // 통신사 TextView
    TextView textViewTelecom;
    // 사용자 정보 TextView
    TextView textViewUserInfo;
    // 설정 Button
    ImageButton imageButtonSetting;

    Button buttonRegister;

    private static final int COUNT_DOWN_SECOND = 15;    // Count Down 초

    String telecom = "";
    String market = "";
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        //  공통 기능 설명
        //      헤더 : 통신사 타이틀 , 선호 마켓 , 가입 정보수신 email정보(화면2에서 사용자가 입력한 값), 설정버튼 노출
        //      설정버튼 : 클릭하면 화면2(선호 마켓/가입정보 수신 email 설정화면)로 이동

        //  화면 4 (부가서비스 확인 화면) 
        //  헤더와 통신사 타이틀, 부가서비스 정보, 등록버튼,  가입처리중 레이어 로 구성됨
        //  헤더 : 공통기능 참조
        //  통신사 타이틀 : SKT, KT, U+, N/A 중 하나
        //  부가서비스 정보 : 가상으로 아무 내용이나 채우면 됨. 단, N/A일 경우 “통신사를 확인 할 수 없습니다.” 메시지 노출
        //  등록버튼(N/A일 경우 비 노출, 화면 4로 이동 후 앱 B에서 통신사를 N/A로 설정할 경우에 대한 예외 처리 구현)
        //      모양 및 레이아웃 : 통신사 별로 , 모양은 동일하지만 레이아웃 위치가 다름
        //          SKT : (화면 기준으로 ) 우 하단
        //          KT : 좌 하단
        //          U+ : 우 상단
        //      동작 : 클릭하면 가입 처리중 레이어 노출
        //  가입 처리중 레이어 : 화면의 약 50% 정도를 차지하는 일종의 팝업(단, N/A일 경우 비 노출)
        //      화면 구성은 상태 메시지 ,  '닫기 버튼', '취소 버튼'으로 구성
        //      상태 메시지
        //          15초 중 : "부가서비스 가입중입니다. 남은 시간 : ##초 " 
        //          15초 경과 후 : "입력한 email(#가입정보 수신 email )로 주문내역서가 발송되었습니다"
        //      닫기 버튼
        //          15초 중 : 클릭하면 앱을 종료하고 노티(Notification)로 남은 시간을 노출
        //          15초 경과 후 : 클릭하면 앱을 종료
        //      노티 : 아래 메시지 표시
        //          15초 중 : "부가서비스 가입중입니다. 남은 시간 : ##초 " 
        //          15초 경과 후 : "입력한 email(#가입정보 수신 email )로 주문내역서가 발송되었습니다"
        //      취소 버튼 : 클릭 시 등록 작업을 취소하고 레이어만 닫는다. 

        Intent intent = getIntent();
        if (intent.hasExtra("MARKET")) {
            market = intent.getStringExtra("MARKET");
        }

        if (intent.hasExtra("EMAIL")) {
            email = intent.getStringExtra("EMAIL");
        }

        telecom = AppUtil.getTelecom(this);

        textViewTelecom = (TextView) findViewById(R.id.textViewTelecom);
        textViewUserInfo = (TextView) findViewById(R.id.textViewUserInfo);

        textViewTelecom.setText(telecom);
        textViewUserInfo.setText(market + ", " + email);

        imageButtonSetting = (ImageButton) findViewById(R.id.imageButtonSetting);
        imageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 설정 화면 이동
                startSettingActivity();
            }
        });

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
                startTimer();
            }
        });
    }

    private void showCustomDialog() {
        CustomDialog dialog = new CustomDialog(this);
        dialog.show();
    }

    public void startTimer() {
        new CountDownTimer(COUNT_DOWN_SECOND * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int remainTime = (int) millisUntilFinished / 1000 + 1;
                createNotificationProgress("부가서비스1", remainTime);
            }

            @Override
            public void onFinish() {
                createNotificationComplete();
            }
        }.start();
    }

    /**
     * 부가서비스 가입중 Notification
     */
    public void createNotificationProgress(String title, int remainTime) {
        String channelId = "channel";
        String channelName = "Channel Name";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        Intent notificationIntent = new Intent(getApplicationContext(), ServiceInfoActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int requestId = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle(title)
                .setContentText("부가서비스 가입중입니다. 남은 시간 : " + remainTime + "초")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOngoing(true)   // noti고정
                .setAutoCancel(false) // 알림 터치시 반응 후 삭제
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_setting))
//                .setBadgeIconType(R.drawable.ic_setting)
                .setContentIntent(pendingIntent);

        notificationManager.notify(0, builder.build());
    }

    /**
     * 부가서비스 가입완료 Notification
     */
    public void createNotificationComplete() {
        String channelId = "channel";
        String channelName = "Channel Name";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        Intent notificationIntent = new Intent(getApplicationContext(), ServiceInfoActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int requestId = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle("부가서비스1")
                .setContentText("입력한 email(" + email + ")로 주문내역서가 발송되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOngoing(false)   // noti고정
                .setAutoCancel(true) // 알림 터치시 반응 후 삭제
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_setting))
//                .setBadgeIconType(R.drawable.ic_setting)
                .setContentIntent(pendingIntent);

        notificationManager.notify(0, builder.build());
    }

    /**
     * 설정 화면 이동
     */
    private void startSettingActivity() {
        Intent intent = new Intent(ServiceInfoActivity.this, SettingActivity.class);
        startActivity(intent);
    }
}
