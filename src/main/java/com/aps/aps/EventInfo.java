package com.aps.aps;

public class EventInfo {
    private String status;
    private String placement;
    private double time;
    public EventInfo(String status, String placement, double time) {
        this.status = status;
        this.placement = placement;
        this.time = time;
    }
    public double getTime()
    {
        return time;
    }
    public String getStatus()
    {
        return status;
    }
    public String getPlacement()
    {
        return placement;
    }
}
