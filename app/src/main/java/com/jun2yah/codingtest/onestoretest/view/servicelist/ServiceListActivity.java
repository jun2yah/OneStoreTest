package com.jun2yah.codingtest.onestoretest.view.servicelist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jun2yah.codingtest.onestoretest.R;
import com.jun2yah.codingtest.onestoretest.common.AppUtil;
import com.jun2yah.codingtest.onestoretest.model.ServiceItem;
import com.jun2yah.codingtest.onestoretest.view.serviceinfo.ServiceInfoActivity;
import com.jun2yah.codingtest.onestoretest.view.setting.SettingActivity;

import java.util.ArrayList;

public class ServiceListActivity extends AppCompatActivity {

    // 통신사 TextView
    TextView textViewTelecom;
    // 사용자 정보 TextView
    TextView textViewUserInfo;
    // 설정 Button
    ImageButton imageButtonSetting;

    // 부가서비스 RecyclerView
    RecyclerView recyclerViewService;

    LinearLayoutManager linearLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    String market = "";
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        //  공통 기능 설명
        //      헤더 : 통신사 타이틀 , 선호 마켓 , 가입 정보수신 email정보(화면2에서 사용자가 입력한 값), 설정버튼 노출
        //      설정버튼 : 클릭하면 화면2(선호 마켓/가입정보 수신 email 설정화면)로 이동

        //  화면 3 (부가 서비스 목록 화면)
        //  헤더와 부가서비스 선택 목록 으로 구성됨
        //  헤더 : 공통기능 참조
        //  부가 서비스 목록을 3~5개 노출하고 그중 하나를 클릭하면 부가서비스 가입 화면으로 이동
        //  부가 서비스 목록 세부 규칙
        //      SKT : 세로로 목록 표시 (내용 좌측 정렬)
        //      KT : 가로로 목록 표시
        //      U+ : 세로로 목록 표시 (내용 우측 정렬)
        //      N/A : “이용 가능한 부가서비스가 없습니다.” 노출

        Intent intent = getIntent();
        if (intent.hasExtra("MARKET")) {
            market = intent.getStringExtra("MARKET");
        }
        if (intent.hasExtra("EMAIL")) {
            email = intent.getStringExtra("EMAIL");
        }

        textViewTelecom = (TextView) findViewById(R.id.textViewTelecom);
        textViewUserInfo = (TextView) findViewById(R.id.textViewUserInfo);

        textViewTelecom.setText(AppUtil.getTelecom(this));
        textViewUserInfo.setText(market + ", " + email);

        imageButtonSetting = (ImageButton) findViewById(R.id.imageButtonSetting);
        imageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 설정 화면 이동
                startSettingActivity();
            }
        });

        recyclerViewService = (RecyclerView) findViewById(R.id.recyclerViewService);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        // Item 세팅
        ArrayList<ServiceItem> items = new ArrayList();
        items.add(new ServiceItem("부가서비스1"));
        items.add(new ServiceItem("부가서비스2"));
        items.add(new ServiceItem("부가서비스3"));
        items.add(new ServiceItem("부가서비스4"));
        items.add(new ServiceItem("부가서비스5"));

        // LinearLayout으로 설정
        recyclerViewService.setLayoutManager(linearLayoutManager);
        // Animation Defualt 설정
        recyclerViewService.setItemAnimator(new DefaultItemAnimator());
//        // Decoration 설정
//        recyclerViewService.addItemDecoration(new RecyclerViewDecoration(this, RecyclerViewDecoration.VERTICAL_LIST));

        // Adapter 생성
        recyclerViewAdapter = new RecyclerViewAdapter(items);
        recyclerViewService.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setItemClick(new RecyclerViewAdapter.ItemView() {
            @Override
            public void onItemClick(View view, int position) {
                startServiceInfoActivity();
            }
        });
    }

    /**
     * 설정 화면 이동
     */
    private void startSettingActivity() {
        Intent intent = new Intent(ServiceListActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    /**
     * 부가서비스 정보 화면 이동
     */
    private void startServiceInfoActivity() {
        Intent intent = new Intent(ServiceListActivity.this, ServiceInfoActivity.class);
        intent.putExtra("MARKET", market);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
}
