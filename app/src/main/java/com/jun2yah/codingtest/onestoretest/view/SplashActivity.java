package com.jun2yah.codingtest.onestoretest.view;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jun2yah.codingtest.onestoretest.R;
import com.jun2yah.codingtest.onestoretest.database.DBHelper;
import com.jun2yah.codingtest.onestoretest.model.AppInfo;

import java.io.File;
import java.util.Date;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //  화면 1 (splash 화면)
        //  단말 내에 설치된 앱들에 대해 설치 마켓 정보를 조회하여 local DB 를 구축하고 갱신한다.
        //  DB 에 저장할 정보 : 설치된 앱의 pkgName, 설치마켓, 버전정보, 최근 update time, 최초 설치 시간, apk 파일 경로, apk파일 size

        getAppInfo();
        createDatabase();
//        insertData();

        startMarketActivity();
    }

    private void createDatabase() {
        dbHelper = new DBHelper(getApplicationContext(), "AppInfo.db", null, 1);
    }

    private void insertData() {
        dbHelper.insert(new AppInfo());
    }

    private void getAppInfo() {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> list = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        try {

            for (ApplicationInfo applicationInfo: list) {
                String packageName = applicationInfo.packageName;
                Log.d(TAG, "Package Name: " + packageName);

                //null - developer
                //com.android.vending - google play
                //com.amazon.venezia - amazon app
                //com.sec.android.app.samsungapps - samsung app store
                //com.samsung.android.themestore
                //com.lguplus.appstore - lguplus
                //com.sec.android.preloadinstaller
                //com.samsung.android.app.spage
                //com.samsung.android.samsungpass

                String installationMarket = pm.getInstallerPackageName(packageName);
                Log.d(TAG, "Package Name: " + packageName + ", Installation Market: " + installationMarket);
//                Log.d(TAG, "" + installationMarket);

                PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);

                String versionName = packageInfo.versionName;
                Log.d(TAG, "Version Name: " + versionName);

                Date updateTime = new Date( packageInfo.lastUpdateTime );
                Log.d(TAG, "Updated: " + updateTime.toString());

                Date installTime = new Date( packageInfo.firstInstallTime );
                Log.d(TAG, "Installed: " + installTime.toString());

                String publicSourceDir = applicationInfo.publicSourceDir;
                Log.d(TAG, "publicSourceDir: " + publicSourceDir);

                long fileSize = new File(applicationInfo.publicSourceDir).length();
                Log.d(TAG, "fileSize: " + fileSize);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startMarketActivity() {
        Intent intent = new Intent(SplashActivity.this, MarketActivity.class);
        startActivity(intent);
        finish();
    }
}
