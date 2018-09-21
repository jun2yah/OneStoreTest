package com.jun2yah.codingtest.onestoretest.model;

public class AppInfo {
    // 패키지명
    private String packageName;
    // 설치마켓
    private String installationMarket;
    // 버전정보
    private String versionName;
    // 최근 update time
    private String updateTime;
    // 최초 설치 시간
    private String installTime;
    // apk 파일 경로
    private String publicSourceDir;
    // apk파일 size
    private String fileSize;

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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public String getPublicSourceDir() {
        return publicSourceDir;
    }

    public void setPublicSourceDir(String publicSourceDir) {
        this.publicSourceDir = publicSourceDir;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
