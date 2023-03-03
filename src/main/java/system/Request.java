package system;

public class Request {
    private int reqNum;

    public int getReqNum()
    {
        return reqNum;
    }

    public int getSourceNum() {
        return sourceNum;
    }

    public void setTime(double time)
    {
        this.time = time;
    }
    private int sourceNum;
    private double time;

    public Request(int reqNum, int sourceNum, double time)
    {
        this.reqNum = reqNum;
        this.sourceNum = sourceNum;
        this.time = time;
    }

    public double getTime()
    {
        return time;
    }
}
