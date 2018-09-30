package com.jun2yah.codingtest.onestoretest.view.splash;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jun2yah.codingtest.onestoretest.R;
import com.jun2yah.codingtest.onestoretest.common.AppConf;
import com.jun2yah.codingtest.onestoretest.database.DBHelper;
import com.jun2yah.codingtest.onestoretest.model.AppInfo;
import com.jun2yah.codingtest.onestoretest.view.setting.SettingActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";

    DBHelper dbHelper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //  화면 1 (splash 화면)
        //  단말 내에 설치된 앱들에 대해 설치 마켓 정보를 조회하여 local DB 를 구축하고 갱신한다.
        //  DB 에 저장할 정보 : 설치된 앱의 pkgName, 설치마켓, 버전정보, 최근 update time, 최초 설치 시간, apk 파일 경로, apk파일 size

        createDatabase();
        insertData();
        getData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startSettingActivity();
            }
        }, 0);
    }

    private void createDatabase() {
        dbHelper = new DBHelper(getApplicationContext(), "AppInfo.db", null, 1);
    }

    private void insertData() {
        List<AppInfo> appInfoList = getAppInfo();

        AppConf.appInfoList = appInfoList;

        for (AppInfo appInfo : appInfoList) {
            dbHelper.insert(appInfo);
        }
    }

    private void getData() {
        List<AppInfo> appInfoList = dbHelper.getResult();
        long index = 1;

        for (AppInfo appInfo : appInfoList) {
            Log.d(TAG, "==> " + index + " | "
                    + appInfo.getPackageName() + " | "
                    + appInfo.getInstallationMarket() + " | "
                    + appInfo.getVersionName() + " | "
                    + appInfo.getUpdateTime() + " | "
                    + appInfo.getInstallTime() + " | "
                    + appInfo.getSourceDir() + " | "
                    + appInfo.getFileSize() + "\n");

            index++;
        }
    }

    private List<AppInfo> getAppInfo() {
        List<AppInfo> appInfoList = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> list = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        try {
            for (ApplicationInfo applicationInfo : list) {
                String packageName = applicationInfo.packageName;
                PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);

                // --------------------------------------------------------------------
                // 마켓               | Install Package Name            | 비고
                // --------------------------------------------------------------------
                // T store            | com.skt.skaf.A000Z00040         | -
                // olleh마켓          | com.kt.olleh.storefront         | 프리로드 버전
                // olleh마켓          | com.kt.olleh.istore             | 인스톨 버전
                // U+스토어           | com.lguplus.appstore            | LTE 단말
                // U+스토어           | android.lgt.appstore            | 3G 단말
                // Google Play 스토어 | com.android.vending             | -
                // Galaxy Apps        | com.sec.android.app.samsungapps | -
                // developer (etc)    | null                            | -
                // --------------------------------------------------------------------
                String installationMarket = pm.getInstallerPackageName(packageName);
                String versionName = packageInfo.versionName;
                long updateTime = packageInfo.lastUpdateTime;
                long installTime = packageInfo.firstInstallTime;
                String sourceDir = applicationInfo.publicSourceDir;
                long fileSize = new File(applicationInfo.publicSourceDir).length();

                Log.d(TAG, "Package Name: " + packageName);
                Log.d(TAG, "installationMarket:" + installationMarket);
                Log.d(TAG, "Version Name: " + versionName);
                Log.d(TAG, "Updated: " + sdf.format(new Date(updateTime)));
                Log.d(TAG, "Installed: " + sdf.format(new Date(installTime)));
                Log.d(TAG, "sourceDir: " + sourceDir);
                Log.d(TAG, "fileSize: " + fileSize);

                AppInfo appInfo = new AppInfo();
                appInfo.setPackageName(packageName);
                appInfo.setInstallationMarket(installationMarket);
                appInfo.setVersionName(versionName);
                appInfo.setUpdateTime(updateTime);
                appInfo.setInstallTime(installTime);
                appInfo.setSourceDir(sourceDir);
                appInfo.setFileSize(fileSize);

                appInfoList.add(appInfo);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appInfoList;
    }

    private void startSettingActivity() {
        Intent intent = new Intent(SplashActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
