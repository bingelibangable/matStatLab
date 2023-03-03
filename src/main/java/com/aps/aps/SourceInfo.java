package com.aps.aps;

public class SourceInfo {
    private String sourceNum;
    private double rejectionPercentage;
    private double bufferRequestTime;
    private double deviceRequestTime;
    private double systemRequestTime;
    public String getSourceNum() {
        return sourceNum;
    }

    public double getRejectionPercentage() {
        return rejectionPercentage;
    }

    public double getBufferRequestTime() {
        return bufferRequestTime;
    }

    public double getDeviceRequestTime() {
        return deviceRequestTime;
    }

    public double getSystemRequestTime() {
        return systemRequestTime;
    }

    public SourceInfo(String sourceNum, double rejectionPercentage, double bufferRequestTime, double deviceRequestTime, double systemRequestTime) {
        this.sourceNum = sourceNum;
        this.rejectionPercentage = rejectionPercentage;
        this.bufferRequestTime = bufferRequestTime;
        this.deviceRequestTime = deviceRequestTime;
        this.systemRequestTime = systemRequestTime;
    }


}
