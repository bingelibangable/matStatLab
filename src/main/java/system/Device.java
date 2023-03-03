package system;

public class Device {

    private int num;
    private double lambda = 0.9;
    private double startTime = 0;
    private double endTime = 0;
    private double workloadTime = 0;

    public double getWorkloadTime()
    {
        return workloadTime;
    }

    public int getDeviceNum() {
        return num;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public Device(int num)
    {
        this.num = num;
    }
    public void proccessReq(Request req)
    {
        startTime = req.getTime();
        endTime = startTime + Math.log(1 - Math.random()) / (-lambda);
        workloadTime += endTime - startTime;
    }
}
