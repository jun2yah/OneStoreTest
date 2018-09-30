package com.jun2yah.codingtest.onestoretest.view.setting;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jun2yah.codingtest.onestoretest.R;
import com.jun2yah.codingtest.onestoretest.common.AppConf;
import com.jun2yah.codingtest.onestoretest.common.AppUtil;
import com.jun2yah.codingtest.onestoretest.model.AppInfo;
import com.jun2yah.codingtest.onestoretest.model.MarketApp;
import com.jun2yah.codingtest.onestoretest.view.servicelist.ServiceListActivity;

import java.text.DecimalFormat;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";

    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    EditText editTextEmail;
    Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //  화면 2 (선호 마켓/가입정보 수신 email 설정 화면)
        //  가입정보 수신 email 입력 폼, 앱 마켓 선택 목록, 확인버튼으로 구성
        //  가입정보 수신 email 입력 폼 : 단말의 구글 계정을 기본 값으로 로딩한다.(기본 로딩 되는 구글 계정 값은 앱B에서 변경 가능)
        //  앱 마켓 선택 목록 : 앱 마켓 목록을 노출하고 그 중 선호 마켓을 선택할 수 있는 UI
        //      각 앱 마켓 목록에 표시할 정보 : 앱 마켓 이름, 단말에 설치된 앱 수, 설치된 앱들의 apk size 합
        //      목록 상단에 "선호하는 앱 마켓을 선택해 주세요" 메시지 상단에 표시
        //  기본 선택 값은 '원스토어'로 설정
        //  선호 앱 마켓 정보는 앱을 종료하면 초기화 됨
        //  확인 버튼 : 확인 버튼을 클릭하면 선호 앱 마켓 및 가입정보 수신 email 값이 설정되고, 화면 3으로 이동

        // 앱마켓 RadioButton
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);

        // 이메일 EditText
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        // 확인 Button
        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startServiceListActivity();
            }
        });

        // View 초기화
        initializeView();
    }

    /**
     * View 초기화
     */
    private void initializeView() {
        // 기본 선택 : 원스토어
        radioButton1.setChecked(true);

        editTextEmail.setText(AppUtil.getGoogleAccount(this));

        setView();
    }

    private void setView() {
        int[] count = new int[]{0,0,0};
        long[] fileSize = new long[]{0,0,0};

        List<AppInfo> appInfoList = AppConf.appInfoList;

        // T store            | com.skt.skaf.A000Z00040         | -
        // olleh마켓          | com.kt.olleh.storefront         | 프리로드 버전
        // olleh마켓          | com.kt.olleh.istore             | 인스톨 버전
        // U+스토어           | com.lguplus.appstore            | LTE 단말
        // U+스토어           | android.lgt.appstore            | 3G 단말
        // Google Play 스토어 | com.android.vending             | -
        // Galaxy Apps        | com.sec.android.app.samsungapps | -

        for (AppInfo appInfo : appInfoList) {
            // One Store
            if ("com.skt.skaf.A000Z00040".equals(appInfo.getInstallationMarket())
                    || "com.kt.olleh.storefront".equals(appInfo.getInstallationMarket())
                    || "com.kt.olleh.istore".equals(appInfo.getInstallationMarket())
                    || "com.lguplus.appstore".equals(appInfo.getInstallationMarket())
                    || "com.lguplus.appstore".equals(appInfo.getInstallationMarket())
                    || "android.lgt.appstore".equals(appInfo.getInstallationMarket())
                    ) {
                count[0]++;
                fileSize[0] += appInfo.getFileSize();
            }
            // Google Play 스토어
            else if ("com.android.vending".equals(appInfo.getInstallationMarket())) {
                count[1]++;
                fileSize[1] += appInfo.getFileSize();
            }
            // Galaxy Apps
            else if ("com.sec.android.app.samsungapps".equals(appInfo.getInstallationMarket())) {
                count[2]++;
                fileSize[2] += appInfo.getFileSize();
            }
        }

        radioButton1.setText("One store, " + count[0] + "개, " + AppUtil.getCommaString(fileSize[0]/1000) + "KB");
        radioButton2.setText("Google Play 스토어, " + count[1] + "개, " + AppUtil.getCommaString(fileSize[1]/1000) + "KB");
        radioButton3.setText("Galaxy Apps, " + count[2] + "개, " + AppUtil.getCommaString(fileSize[2]/1000) + "KB");
    }

    /**
     * 부가서비스 목록 화면 이동
     */
    private void startServiceListActivity() {
        Intent intent = new Intent(SettingActivity.this, ServiceListActivity.class);

        int index = radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()));
        String market = "";
        if (index == 0) {
            market = "원스토어";
        } else if (index == 1) {
            market = "Google Play 스토어";
        } else if (index == 2) {
            market = "Galaxy Apps";
        }

        intent.putExtra("MARKET", market);
        intent.putExtra("EMAIL", editTextEmail.getText().toString());
        startActivity(intent);
        finish();
    }
}
