package com.procx.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class ServiceDatagramChannelDemo {

    public static class UDPEchoService implements Runnable{

        private int port;

        public UDPEchoService(int port){
            this.port = port;
        }

        @Override
        public void run(){

            ByteBuffer readBuffer = ByteBuffer.allocate(256);
            ByteBuffer writeBuffer = ByteBuffer.allocate(256);

            DatagramChannel dc = null;

            try{

                /*服务器端使用默认的阻塞IO的方式*/
                dc = DatagramChannel.open();
                dc.bind(new InetSocketAddress(port));

                System.out.println("service start");
                while(!Thread.currentThread().isInterrupted()){

                    try{

                        /*先读取客户端发送的消息，直到读取到消息才会返回*/
                        /*只能调用receive方法，因为不知道哪个地址给服务器发信息，没法实现调用connect方法*/
                        /*dc是阻塞的，所以receive方法要等到接收到数据才返回*/
                        SocketAddress clientAddress = dc.receive(readBuffer);
                        readBuffer.flip();
                        CharBuffer charBuffer = Charset.defaultCharset().decode(readBuffer);
                        System.out.println(charBuffer.array());

                        /*调用send方法向客户端发送的消息，
                         *dc是阻塞的,所以直到send方法把数据全部写入到socket缓冲区才返回*/
                        writeBuffer.put("echo : ".getBytes());
                        readBuffer.rewind();
                        writeBuffer.put(readBuffer);
                        writeBuffer.flip();
                        dc.send(writeBuffer, clientAddress);

                        readBuffer.clear();
                        writeBuffer.clear();

                    }catch(IOException e){
                        System.out.println("receive from or send to client failed");
                    }
                }
            }catch(IOException e){
                System.out.println("server error");
            }finally{
                try {
                    if(dc != null){
                        dc.close();
                    }
                } catch (IOException e) {

                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        new Thread(new UDPEchoService(8080)).start();
    }
}