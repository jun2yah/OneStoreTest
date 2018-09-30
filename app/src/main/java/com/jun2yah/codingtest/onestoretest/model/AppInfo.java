package com.jun2yah.codingtest.onestoretest.model;

public class AppInfo {
    // 패키지명
    private String packageName;
    // 설치마켓
    private String installationMarket;
    // 버전정보
    private String versionName;
    // 최근 update time
    private long updateTime;
    // 최초 설치 시간
    private long installTime;
    // apk 파일 경로
    private String sourceDir;
    // apk파일 size
    private long fileSize;

    public AppInfo() {

    }

    public AppInfo(String packageName, String installationMarket, String versionName, long updateTime, long installTime, String sourceDir, long fileSize) {
        this.packageName = packageName;
        this.installationMarket = installationMarket;
        this.versionName = versionName;
        this.updateTime = updateTime;
        this.installTime = installTime;
        this.sourceDir = sourceDir;
        this.fileSize = fileSize;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getInstallationMarket() {
        return installationMarket;
    }

    public void setInstallationMarket(String installationMarket) {
        this.installationMarket = installationMarket;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getInstallTime() {
        return installTime;
    }

    public void setInstallTime(long installTime) {
        this.installTime = installTime;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
