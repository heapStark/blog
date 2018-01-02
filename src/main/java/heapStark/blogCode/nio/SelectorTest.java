package heapStark.blogCode.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/2.
 */
public class SelectorTest {
    /**
     *
     */
    @Test
    public void selectorTest() throws Exception{


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8080), 1024);
        serverSocketChannel.configureBlocking(false);
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        socketChannel.configureBlocking(false);

        Selector selector1 = Selector.open();
        Selector selector = Selector.open();
        socketChannel.keyFor(selector);
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
        long readyCount = selector.select();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.register(selector1, SelectionKey.OP_CONNECT);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel.register(selector1, SelectionKey.OP_ACCEPT);


    }
}
