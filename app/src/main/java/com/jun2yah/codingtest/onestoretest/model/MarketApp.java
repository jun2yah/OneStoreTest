package com.jun2yah.codingtest.onestoretest.model;

public class MarketApp {
    private String marketName;  // 앱 마켓 이름
    private int numberApp;      // 단말에 설치된 앱 수
    private long totalSize;     // 설치된 앱들의 apk size 합

    public MarketApp(String marketName, int numberApp, long totalSize) {
        this.marketName = marketName;
        this.numberApp = numberApp;
        this.totalSize = totalSize;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public int getNumberApp() {
        return numberApp;
    }

    public void setNumberApp(int numberApp) {
        this.numberApp = numberApp;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}
