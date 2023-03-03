package system;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DeviceManager {
    private int currentDevice = 0;
    private List<Device> devices;
    public DeviceManager(int devicesCount)
    {
        devices = new LinkedList<Device>();
        for (int i = 0; i < devicesCount; i++)
        {
            devices.add(new Device(i));
        }
    }
    public Device getDevice(int deviceNum)
    {
        return devices.get(deviceNum);
    }
    public int getDeviceForRequest(double time)
    {
        if (currentDevice == devices.size() || currentDevice == -1)
        {
            currentDevice = 0;
        }
        int deviceNum = currentDevice;
        if (Double.compare(time, devices.get(currentDevice).getEndTime()) > 0 || Double.compare(time, devices.get(currentDevice).getEndTime()) == 0)
        {
            return currentDevice;
        }
        if (deviceNum == devices.size() - 1)
        {
            deviceNum = 0;
        }
        else
        {
            deviceNum++;
        }
        while(deviceNum != currentDevice)
        {
            if (Double.compare(time, devices.get(deviceNum).getEndTime()) > 0)
            {
                currentDevice = deviceNum;
                return currentDevice;
            }
            if (deviceNum == devices.size() - 1)
            {
                deviceNum = 0;
            }
            else
            {
                deviceNum++;
            }
        }
        currentDevice = -1;
        return currentDevice;
    }
    public int getDeviceForBufferedRequest()
    {
        currentDevice = Collections.min(devices, Comparator.comparing(Device::getEndTime)).getDeviceNum();
        return currentDevice;
    }
    public void proccessRequest(Request req)
    {
        devices.get(currentDevice).proccessReq(req);
    }

    public LinkedList<Double> countWorkloadTime()
    {
        LinkedList<Double> devicesWorkloadTime = new LinkedList<>();
        for (Device d: devices)
        {
            devicesWorkloadTime.add(d.getWorkloadTime());
        }
        return devicesWorkloadTime;
    }
}
