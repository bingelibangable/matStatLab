package system;

import java.util.Collections;
import java.util.LinkedList;

public class BufferManager {
    private LinkedList<Request> buffer;
    private int size;

    public BufferManager(int bufferSize)
    {
        buffer = new LinkedList<>();
        size = bufferSize;
    }

    public int putRequest(Request req)
    {
        if(buffer.isEmpty())
        {
            buffer.add(req);
            return 0;
        }
        if (buffer.size() >= size)
        {
            for (int i = 0; i < buffer.size(); i++)
            {
                if(req.getSourceNum() < buffer.get(i).getSourceNum())
                {
                    buffer.add(i, req);
                    if (buffer.get(buffer.size() - 1).getSourceNum() == buffer.get(buffer.size() - 2).getSourceNum()
                            && (Double.compare(buffer.get(buffer.size() - 1).getTime(),buffer.get(buffer.size() - 2).getTime()) < 0))
                    {
                        Collections.swap(buffer, buffer.size() - 1, buffer.size() - 2);
                    }
                    return -1;
                }
            }
            buffer.add(req);
            if (buffer.get(buffer.size() - 1).getSourceNum() == buffer.get(buffer.size() - 2).getSourceNum()
                    && (Double.compare(buffer.get(buffer.size() - 1).getTime(),buffer.get(buffer.size() - 2).getTime()) < 0))
            {
                Collections.swap(buffer, buffer.size() - 1, buffer.size() - 2);
            }
            return -1;
        }
        for (int i = 0; i < buffer.size(); i++)
        {
            if(req.getSourceNum() < buffer.get(i).getSourceNum())
            {
                buffer.add(i, req);
                return i;
            }
        }
        buffer.add(req);
        return buffer.size() - 1;
    }
    public boolean isEmpty()
    {
        return buffer.isEmpty();
    }

    public Request deleteRejectedRequest()
    {
        return buffer.removeLast();
    }

    public Request getRequest()
    {
        if(buffer.isEmpty())
        {
            return null;
        }
        /*for(Request r: buffer)
        {
            System.out.print("[" + r.getSourceNum() + " " + r.getReqNum() + "],");
        }
        System.out.println();*/
        Request req = buffer.peekFirst();
        return req;
    }

    public double deleteRequest()
    {
        if(!buffer.isEmpty()) buffer.pop();
        return 0;
    }
}
