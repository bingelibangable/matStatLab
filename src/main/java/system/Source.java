package system;

public class Source {
    private int num;
    private double a = 1.0;
    private double b = 1.3;
    private int reqNum = 0;
    private double prevReqTime = 0.0;
    private int rejectionTimes = 0;
    private double bufferRequestTime = 0.0;
    private double deviceRequestTime = 0.0;
    public double countRejectionPercentage(int requestCount)
    {
        return rejectionTimes == 0 ? 0 : ((double) rejectionTimes) / requestCount;
    }
    public void captureRejection()
    {
        rejectionTimes++;
    }

    public double getBufferRequestTime() {
        return bufferRequestTime;
    }

    public void addBufferRequestTime(double time)
    {
        bufferRequestTime += time;
    }

    public double getDeviceRequestTime() {
        return deviceRequestTime;
    }
    public void addDeviceRequestTime(double time)
    {
        deviceRequestTime += time;
    }

    public Source(int num)
    {
        this.num = num;
    }

    public Request generateRequest()
    {
        double generationTime = prevReqTime + (b - a) * Math.random() + a;
        Request req = new Request(reqNum, num, generationTime);
        prevReqTime = generationTime;
        reqNum++;
        return req;
    }

    public int getSourceNum()
    {
        return num;
    }

    public double getGenerationTime()
    {
        return prevReqTime;
    }
}
