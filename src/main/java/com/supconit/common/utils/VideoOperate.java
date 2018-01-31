package com.supconit.common.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

public class VideoOperate {

    private String ip ;
    private int port;

    public VideoOperate(String ip,int port){
        this.ip = ip;
        this.port = port;
    }

    public void control(Command command){
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();

            //channel.connect(new InetSocketAddress("192.168.81.200",9494));
            channel.connect(new InetSocketAddress(ip,port));

            List<ByteBuffer> bufferList = command.findCommands();

            for(ByteBuffer buffer : bufferList){
                channel.write(buffer);
                System.out.println("======="+buffer);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != channel)
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public interface Command{
        List<ByteBuffer> findCommands();
    }

}