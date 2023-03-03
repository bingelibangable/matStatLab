package system;

public class Event {
    private String status;
    private String placement;
    private double time;
    private int sourceNum;
    private int deviceNum;
    private int bufferStatus;

    public int getSourceNum() {
        return sourceNum;
    }

    public int getDeviceNum() {
        return deviceNum;
    }

    public int getBufferStatus() {
        return bufferStatus;
    }

    public Event(String status, String placement, double time, int sourceNum, int deviceNum, int bufferStatus) {
        this.status = status;
        this.placement = placement;
        this.time = time;
        this.sourceNum = sourceNum;
        this.deviceNum = deviceNum;
        this.bufferStatus = bufferStatus;
    }
    public double getEventTime()
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
