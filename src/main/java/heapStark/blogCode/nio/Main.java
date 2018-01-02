package heapStark.blogCode.nio;

import heapStark.blogCode.nio.utils.NIOUtils;
import heapStark.blogCode.utils.MultiThreadTestUtil;
import org.hamcrest.core.Is;
import org.junit.Test;

import javax.xml.stream.FactoryConfigurationError;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * blogcode
 * Created by wangzhilei3 on 2017/12/31.
 */
public class Main {
    public static void main(String... args) {
        //console input 123
        WritableByteChannel writableByteChannel = Channels.newChannel(System.out);
        ReadableByteChannel readableByteChannel = Channels.newChannel(System.in);

        ByteBuffer readBuffer = ByteBuffer.allocate(100);
        while (true) {
            try {
                readableByteChannel.read(readBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert (readBuffer.position() == 2);

            readBuffer.flip();
            assert (readBuffer.position() == 2);
            try {
                writableByteChannel.write(readBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            readBuffer.clear();
            readBuffer.rewind();
            readBuffer.compact();
        }

    }

    @Test
    public void bufferTest() {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put("1234".getBytes());
        assert (buffer.capacity() == 100);
        assert (buffer.position() == 4);
        assert (buffer.limit() == 100);

        buffer.mark();
        buffer.put("234".getBytes());
        assert (buffer.position() == 7);
        buffer.reset();
        assert (buffer.position() == 4);
        buffer.put("6".getBytes());
        assert (buffer.get(4) == 54);
    }

    /**
     * ThreadUnsafe
     */
    @Test
    public void bufferThreadUnsafeTest() {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        CountDownLatch countDownLatch = new CountDownLatch(100);
        try {
            MultiThreadTestUtil.multiThreadRun(() -> {
                buffer.put("qwer".getBytes());
                countDownLatch.countDown();
            }, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(buffer.position());
        assert (buffer.position() != 400);

    }

    /**
     * get channel
     */
    @Test
    public void channelTest() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));

        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));

        DatagramChannel dc = DatagramChannel.open();

        RandomAccessFile raf = new RandomAccessFile("E:\\JcloudMyProject\\blogcode\\src\\main\\java\\heapStark\\blogCode\\nio\\Main.java", "r");
        FileChannel fc = raf.getChannel();
        //Socket 无法产生Channel
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8080));
        assert (socket.getChannel() == null);
        //
    }

    @Test
    public void fileInputChannelTest() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("E:\\JcloudMyProject\\blogcode\\src\\main\\java\\heapStark\\blogCode\\nio\\Main.java");
        ByteBuffer buffer = ByteBuffer.allocate(100);
        int size = fileInputStream.getChannel().read(buffer);
        assert (size == 100);
        //buffer.put("hello".getBytes());
        buffer.flip();
        try {
            fileInputStream.getChannel().write(buffer);
        } catch (Exception e) {
            assert (e.getClass().getName() == "java.nio.channels.NonWritableChannelException");
        }
        //
    }

    /**
     * 矢量操作
     *
     * @throws Exception
     */
    @Test
    public void scatteringChannelTest() throws Exception {
        Pipe pipe = Pipe.open();
        ByteBuffer[] buffers = NIOUtils.getByteBuffers("hello", "world");
        pipe.sink().write(buffers);

        ByteBuffer readbuffer = ByteBuffer.allocate(100);
        pipe.source().read(readbuffer);

        ByteBuffer[] buffers2 = NIOUtils.getByteBuffers("hello", "world", "hello");
        pipe.sink().write(buffers2);

        ByteBuffer[] readbuffers = NIOUtils.getReadableByteBuffers(2);
        pipe.source().read(readbuffers);
        System.out.println("end-----------------------");

    }

    @Test
    public void pipeTest() throws Exception {

        Pipe pipe = Pipe.open();
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put("hello".getBytes());
        buffer.flip();
        pipe.sink().write(buffer);

        ByteBuffer readbuffer = ByteBuffer.allocate(100);
        pipe.source().read(readbuffer);
        System.out.println("end-----------------------");
    }

    /**
     * configureBlocking Test
     * @throws Exception
     */
    @Test
    public void socketChannelTest() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        assert (ssc.isBlocking());
        ssc.configureBlocking(false);
        assert (!ssc.isBlocking());

        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));

    }
    /**
     * configureBlocking Test
     * @throws Exception
     */
    @Test
    public void socketChannelConfigureBlockingTest() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        assert (ssc.isBlocking());
        ssc.configureBlocking(false);
        assert (!ssc.isBlocking());
        Object lock = ssc.blockingLock();
        Thread thread = new Thread(()->{
            Date date = new Date();
            try {
                TimeUnit.SECONDS.sleep(3);
                date = new Date();
                //阻塞操作
                ssc.configureBlocking(true);
            } catch (Exception e) {
                e.printStackTrace();
                assert (false);
            }
            assert (ssc.isBlocking());
            assert (new Date().getTime()-date.getTime()>=6000);
        });
        thread.start();
        synchronized (lock){
            TimeUnit.SECONDS.sleep(10);
        }
        thread.join();
    }
    /**
     * configureBlocking Test
     * @throws Exception
     */
    @Test
    public void serverSocketChannelTest() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        assert (ssc.validOps()==16);//return SelectionKey.OP_ACCEPT;
        SocketChannel socketChannel = ssc.accept();
        assert (socketChannel==null);
        //ServerSocket socket  =ssc.socket();
        //socket.accept();//抛出异常，IllegalBlockingModeException,ssc.configureBlocking(false)之后方可执行
        Thread socketThread = new Thread(()->{
            while (true){
                try {
                    SocketChannel socketChannel1 = ssc.accept();
                    if (socketChannel1!=null){
                        assert (socketChannel1.isBlocking());
                        ByteBuffer [] buffers = NIOUtils.getByteBuffers("hello","world");
                        long size = socketChannel1.write(buffers);
                        System.out.println(size);
                        //socketChannel1.close();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        socketThread.start();
        SocketChannel socket = SocketChannel.open();
        socket.configureBlocking(false);
        socket.connect(new InetSocketAddress("localhost",8080));
        assert (socket.validOps()==13);
            /*public final int validOps() {
                return (SelectionKey.OP_READ
                    | SelectionKey.OP_WRITE
                    | SelectionKey.OP_CONNECT);
            }*/
        assert (!socket.isBlocking());

        ByteBuffer[] readableBuffers = NIOUtils.getReadableByteBuffers(3);
        assert (!socket.isConnected());
        assert (socket.isConnectionPending());
        while (true){
            //TimeUnit.SECONDS.sleep(1);
            assert (socket.finishConnect());//本地情况直接返回true
            if (socket.isConnected()){
                long size = socket.read(readableBuffers);
                if (size!=0){
                    break;
                }
            }
        }
        socketThread.join();
        System.out.println(readableBuffers[0]);
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put("hello".getBytes());
        assert (buffer.equals(readableBuffers[0]));
    }
    @Test
    public void serverSocketChannelTest2() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        assert (ssc.validOps()==16);//return SelectionKey.OP_ACCEPT;
        SocketChannel socketChannel = ssc.accept();
        assert (socketChannel==null);
        //ServerSocket socket  =ssc.socket();
        //socket.accept();//抛出异常，IllegalBlockingModeException,ssc.configureBlocking(false)之后方可执行
        Thread socketThread = new Thread(()->{
            while (true){
                try {
                    SocketChannel socketChannel1 = ssc.accept();
                    if (socketChannel1!=null){
                        assert (socketChannel1.isBlocking());
                        //assert (!socketChannel1.isBlocking());
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        socketThread.start();
        SocketChannel socket = SocketChannel.open();
        socket.configureBlocking(false);
        socket.connect(new InetSocketAddress("localhost",8080));
        socketThread.join();
    }
    /**
     * 原生socket
     */
    @Test
    public void socketTest() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerSocketChannel channel = serverSocket.getChannel();
        assert (channel == null);
    }

}
