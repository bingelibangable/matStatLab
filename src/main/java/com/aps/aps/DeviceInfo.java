package com.aps.aps;

public class DeviceInfo {
    private String deviceNum;
    private double workloadPercentage;
    public String getDeviceNum() {
        return deviceNum;
    }
    public DeviceInfo(String deviceNum, double workloadPercentage)
    {
        this.deviceNum = deviceNum;
        this.workloadPercentage = workloadPercentage;
    }

    public double getWorkloadPercentage() {
        return workloadPercentage;
    }
}
