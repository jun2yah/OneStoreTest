package com.jun2yah.codingtest.onestoretest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jun2yah.codingtest.onestoretest.R;

public class ServiceListActivity extends AppCompatActivity {

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
    }
}
