package com.jun2yah.codingtest.onestoretest.view;

import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jun2yah.codingtest.onestoretest.R;

public class MarketActivity extends AppCompatActivity {

    Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        //  화면 2 (선호 마켓/가입정보 수신 email 설정 화면)
        //  가입정보 수신 email 입력 폼, 앱 마켓 선택 목록, 확인버튼으로 구성
        //  가입정보 수신 email 입력 폼 : 단말의 구글 계정을 기본 값으로 로딩한다.(기본 로딩 되는 구글 계정 값은 앱B에서 변경 가능)
        //  앱 마켓 선택 목록 : 앱 마켓 목록을 노출하고 그 중 선호 마켓을 선택할 수 있는 UI
        //      각 앱 마켓 목록에 표시할 정보 : 앱 마켓 이름, 단말에 설치된 앱 수, 설치된 앱들의 apk size 합
        //      목록 상단에 "선호하는 앱 마켓을 선택해 주세요" 메시지 상단에 표시
        //  기본 선택 값은 '원스토어'로 설정
        //  선호 앱 마켓 정보는 앱을 종료하면 초기화 됨
        //  확인 버튼 : 확인 버튼을 클릭하면 선호 앱 마켓 및 가입정보 수신 email 값이 설정되고, 화면 3으로 이동

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startServiceListActivity();
            }
        });
    }

    /**
     * 부가서비스 목록 화면 이동
     */
    private void startServiceListActivity() {
        Intent intent = new Intent(MarketActivity.this, ServiceListActivity.class);
        startActivity(intent);
        finish();
    }
}
