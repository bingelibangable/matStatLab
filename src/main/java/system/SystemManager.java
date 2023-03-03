package system;

import com.aps.aps.SystemInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SystemManager
{
    private int sourcesNum;
    private int requestsNum;
    private int devicesNum;
    private int bufferSize;
    public SystemManager(int sourcesNum, int requestsNum, int devicesNum, int bufferSize)
    {
        this.sourcesNum = sourcesNum;
        this.requestsNum = requestsNum;
        this.devicesNum = devicesNum;
        this.bufferSize = bufferSize;
    }
    public List<Event> simulateSystem()
    {
        Request temp;
        List<Source> sources = new LinkedList<>();
        List<Event> Events = new LinkedList<>();
        List<Request> requests = new LinkedList<>();
        BufferManager bufMan = new BufferManager(bufferSize);
        for (int i = 0; i < sourcesNum; i++) {
            sources.add(new Source(i));
        }
        DeviceManager manager = new DeviceManager(devicesNum);
        for (int i = 0; i < requestsNum; i++) {
            for (Source s : sources) {
                requests.add(s.generateRequest());
                Events.add(new Event("Создана", "Заявка № " + i + " в источнике " + s.getSourceNum(), s.getGenerationTime(), s.getSourceNum(), -1, -1));
            }
        }
        requests.sort(Comparator.comparing(Request::getTime));
        int deviceNum;
        for (Request req : requests)
        {
            if(!bufMan.isEmpty())
            {
                deviceNum = manager.getDeviceForBufferedRequest();
                temp = bufMan.getRequest();
                double tempTime = temp.getTime();
                temp.setTime(manager.getDevice(deviceNum).getEndTime());
                if (Double.compare(req.getTime(), temp.getTime()) > 0)
                {
                    bufMan.deleteRequest();
                    if (bufMan.isEmpty())
                    {
                        Events.add(new Event("Повторно отправлена", "Заявка № " + temp.getReqNum() + " из источника " + temp.getSourceNum() + " покинула Буффер. Буфер пустой", manager.getDevice(deviceNum).getEndTime(), -1, -1, 3));
                    } else
                    {
                        Events.add(new Event("Повторно отправлена", "Заявка № " + temp.getReqNum() + " из источника " + temp.getSourceNum() + " покинула Буффер", manager.getDevice(deviceNum).getEndTime(), -1, -1, 1));
                    }
                    manager.proccessRequest(temp);
                    sources.get(temp.getSourceNum()).addBufferRequestTime(temp.getTime() - tempTime);
                    Events.add(new Event("Получена", "Заявка № " + temp.getReqNum() + " из источника " + temp.getSourceNum() + " в приборе " + manager.getDevice(deviceNum).getDeviceNum(), manager.getDevice(deviceNum).getStartTime() + 0.0001, -1, deviceNum, -1));
                    Events.add(new Event("Обработана", "Заявка № " + temp.getReqNum() + " из источника " + temp.getSourceNum() + " в приборе " + manager.getDevice(deviceNum).getDeviceNum(), manager.getDevice(deviceNum).getEndTime(), -1, deviceNum, -1));
                    sources.get(temp.getSourceNum()).addDeviceRequestTime(manager.getDevice(deviceNum).getEndTime() - manager.getDevice(deviceNum).getStartTime());
                }
            }
            deviceNum = manager.getDeviceForRequest(req.getTime());
            if (deviceNum == -1)
            {
                int bufReqNum = bufMan.putRequest(req);
                if (bufReqNum == -1)
                {
                    Request tempReq = bufMan.deleteRejectedRequest();
                    sources.get(tempReq.getSourceNum()).captureRejection();
                    if (tempReq == req)
                    {
                        Events.add(new Event("Исключена", "Заявка № " + req.getReqNum() + " из источника " + req.getSourceNum()  + " исключена из системы", req.getTime(), -1, -1, 2));
                    }
                    else
                    {
                        Events.add(new Event("Исключена", "Заявка № " + tempReq.getReqNum() + " из источника " + tempReq.getSourceNum()  + " исключена из системы", req.getTime(), -1, -1, 2));
                        Events.add(new Event("Отклонена", "Заявка № " + req.getReqNum() + " из источника " +  req.getSourceNum()  + " помещена в буфер", req.getTime(), -1, -1, 0)); //  на позицию заявки № " + tempReq.getReqNum() + " из источника " + tempReq.getSourceNum()
                    }
                }
                else
                {
                    Events.add(new Event("Отклонена", "Заявка № " + req.getReqNum() + " из источника " +  req.getSourceNum()  + " помещена в буфер на позицию " + bufReqNum, req.getTime(), -1, -1, 0));
                }
            }
            else
            {
                manager.proccessRequest(req);
                Events.add(new Event("Получена", "Заявка № " + req.getReqNum() + " из источника " +  req.getSourceNum()  + " в приборе " + manager.getDevice(deviceNum).getDeviceNum(), manager.getDevice(deviceNum).getStartTime() + 0.0001, -1, deviceNum, -1));
                Events.add(new Event("Обработана", "Заявка № " + req.getReqNum() + " из источника " +  req.getSourceNum() + " в приборе " + manager.getDevice(deviceNum).getDeviceNum(), manager.getDevice(deviceNum).getEndTime(), -1, deviceNum, -1));
                sources.get(req.getSourceNum()).addDeviceRequestTime(manager.getDevice(deviceNum).getEndTime() - manager.getDevice(deviceNum).getStartTime());
            }
        }
        while (!bufMan.isEmpty()) {
            deviceNum = manager.getDeviceForBufferedRequest();
            temp = bufMan.getRequest();
            double tempTime = temp.getTime();
            temp.setTime(manager.getDevice(deviceNum).getEndTime());
            bufMan.deleteRequest();
            if (bufMan.isEmpty())
            {
                Events.add(new Event("Повторно отправлена", "Заявка № " + temp.getReqNum() + " из источника " + temp.getSourceNum() + " покинула Буффер", manager.getDevice(deviceNum).getEndTime(), -1, -1, 3));
            }
            else
            {
                Events.add(new Event("Повторно отправлена", "Заявка № " + temp.getReqNum() + " из источника " + temp.getSourceNum() + " покинула Буффер", manager.getDevice(deviceNum).getEndTime(), -1, -1, 1));
            }
            manager.proccessRequest(temp);
            sources.get(temp.getSourceNum()).addBufferRequestTime(temp.getTime() - tempTime);
            Events.add(new Event("Получена", "Заявка № " + temp.getReqNum() + " из источника " + temp.getSourceNum() + " в приборе " + manager.getDevice(deviceNum).getDeviceNum(), manager.getDevice(deviceNum).getStartTime() + 0.0001, -1, deviceNum, -1));
            Events.add(new Event("Обработана", "Заявка № " + temp.getReqNum() + " из источника " + temp.getSourceNum() + " в приборе " + manager.getDevice(deviceNum).getDeviceNum(), manager.getDevice(deviceNum).getEndTime(), -1, deviceNum, -1));
            sources.get(temp.getSourceNum()).addDeviceRequestTime(manager.getDevice(deviceNum).getEndTime() - manager.getDevice(deviceNum).getStartTime());
        }
        Events.sort(Comparator.comparing(Event::getEventTime));
        Events.add(new Event("Конец сессии", "Система завершила работу", Events.get(Events.size()-1).getEventTime() + 0.01, -1,-1,-1));
        /*for (Event e : Events) {
            System.out.println(e.getPlacement() + " " + e.getStatus() + " " + e.getEventTime());
        }*/
        //System.out.println(Events.size());
        //System.out.println(bufMan.getSize());
        for (Source s: sources)
        {
            SystemInfo.rejectionPercentage.add(s.countRejectionPercentage(requestsNum));
            SystemInfo.deviceRequestTime.add(s.getDeviceRequestTime());
            SystemInfo.bufferRequestTime.add(s.getBufferRequestTime());
            SystemInfo.systemRequestTime.add(s.getDeviceRequestTime()+s.getBufferRequestTime());
            //System.out.println("Источник " + s.getSourceNum());
            //System.out.println(s.countRejectionPercentage(requestsNum));
            //System.out.println(s.getDeviceRequestTime());
            //System.out.println(s.getBufferRequestTime());
        }
        int i = 0;
        for (Double d: manager.countWorkloadTime())
        {
            //System.out.println("device " + i + " workload is " + d);
            SystemInfo.workloadPercentage.add(d / (manager.getDevice(i++).getEndTime() - (Events.get(0).getEventTime()-0.1)));
            //System.out.println("device " + i + " workload percentage is " + d / (manager.getDevice(i).getEndTime() - Events.get(0).getEventTime()));
            //++i;

        }
        return Events;
    }
}
