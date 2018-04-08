package thrift;

import org.apache.thrift.TException;

public class NameServiceImpl implements NameService.Iface {
    @Override
    public String echoName(String name) throws TException {
        return name;
    }
}
