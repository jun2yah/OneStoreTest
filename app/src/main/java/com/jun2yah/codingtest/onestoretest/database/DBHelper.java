package com.jun2yah.codingtest.onestoretest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.jun2yah.codingtest.onestoretest.model.AppInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    private final String tableName = "App";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     * DB를 새로 생성할 때 호출되는 함수
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE APP_INFO (_id INTEGER PRIMARY KEY AUTOINCREMENT, package_name TEXT, installation_market TEXT, version_name TEXT, update_time INTEGER, install_time INTEGER, source_dir TEXT, file_size INTEGER);");
        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();
    }

    /**
     * DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }

    public void insert(AppInfo appInfo) {
//        // 읽고 쓰기가 가능하게 DB 열기
//        SQLiteDatabase db = getWritableDatabase();
//        // DB에 입력한 값으로 행 추가
//        db.execSQL("INSERT INTO APP_INFO VALUES(null, '" + appInfo.getPackageName() + "', " + appInfo.getInstallationMarket() + ", '" + appInfo.getVersionName() + "');");
//        db.close();

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("package_name", appInfo.getPackageName());
        values.put("installation_market", appInfo.getInstallationMarket());
        values.put("version_name", appInfo.getVersionName());
        values.put("update_time", appInfo.getUpdateTime());
        values.put("install_time", appInfo.getInstallTime());
        values.put("source_dir", appInfo.getSourceDir());
        values.put("file_size", appInfo.getFileSize());

        db.insert("APP_INFO", null, values);
        db.close();
    }

    public void update(AppInfo appInfo) {
//        SQLiteDatabase db = getWritableDatabase();
//        // 입력한 항목과 일치하는 행의 가격 정보 수정
//        db.execSQL("UPDATE APP_INFO SET price=" + price + " WHERE item='" + item + "';");
//        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM APP_INFO WHERE item='" + item + "';");
        db.close();
    }

    public List<AppInfo> getResult() {
        List<AppInfo> appInfoList = new ArrayList<>();

        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM APP_INFO", null);
        while (cursor.moveToNext()) {
            AppInfo appInfo = new AppInfo();
            appInfo.setPackageName(cursor.getString(1));
            appInfo.setInstallationMarket(cursor.getString(2));
            appInfo.setVersionName(cursor.getString(3));
            appInfo.setUpdateTime(cursor.getLong(4));
            appInfo.setInstallTime(cursor.getLong(5));
            appInfo.setSourceDir(cursor.getString(6));
            appInfo.setFileSize(cursor.getLong(7));

            appInfoList.add(appInfo);
        }

        return appInfoList;
    }
}
