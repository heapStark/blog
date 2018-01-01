package heapStark.blogCode.nio;

import heapStark.blogCode.utils.MultiThreadTestUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.CountDownLatch;

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
        assert (size==100);
        //buffer.put("hello".getBytes());
        buffer.flip();
        try {
            fileInputStream.getChannel().write(buffer);
        }catch (Exception e){
            assert (e.getClass().getName()=="java.nio.channels.NonWritableChannelException");
        }


        //

    }

    @Test
    public void pipeTest() throws Exception {

        Pipe pipe = Pipe.open();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put("hello".getBytes());
        buffer.flip();
        pipe.sink().write(buffer);

        ByteBuffer readbuffer = ByteBuffer.allocate(100);
        pipe.source().read(readbuffer);


        System.out.println("end-----------------------");
    }
}
