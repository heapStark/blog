package heapStark.blogCode.thrift;
import heapStark.blogCode.thriftDemo.service.NameService;
import org.apache.thrift.TException;

public class NameEchoImpl implements NameService.Iface{
    @Override
    public String echoName(String name) throws TException {
        return name;
    }
}
