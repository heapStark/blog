package heapStark.blogCode.nio.utils;

import java.nio.ByteBuffer;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/2.
 */
public class NIOUtils {
    public static ByteBuffer[] getReadableByteBuffers(int length) {
        return getByteBuffers(length, null);
    }
    public static ByteBuffer[] getByteBuffers(int length) {
        return getByteBuffers(length, null);
    }

    public static ByteBuffer[] getByteBuffers(String... args) {
        return getByteBuffers(0, args);
    }

    public static ByteBuffer[] getByteBuffers(int length, String... args) {
        if (args == null) {
            ByteBuffer[] buffers  =  new ByteBuffer[length];
            for (int i=0;i<length;i++){
                buffers[i] = ByteBuffer.allocate(10);
            }
            return buffers;
        } else if (length == 0) {
            ByteBuffer[] buffers = new ByteBuffer[args.length];
            int index = 0;
            for (String s : args) {
                buffers[index] = ByteBuffer.allocate(s.length());
                buffers[index].put(s.getBytes());
                buffers[index].flip();
                index++;
            }
            return buffers;
        } else {
            ByteBuffer[] buffers = new ByteBuffer[args.length];
            int index = 0;
            for (String s : args) {
                buffers[index] = ByteBuffer.allocate(s.length());
                buffers[index].put(s.getBytes());
                buffers[index].flip();
                index++;
            }
            return buffers;
        }
    }
}
