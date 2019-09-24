package com.procx.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;

public class ClientDatagramChannelDemo {

    public static class UDPEchoClient implements Runnable{

        private String name;
        private InetSocketAddress serviceAddress;

        public UDPEchoClient(String name, InetSocketAddress serviceAddress){
            this.name = name;
            this.serviceAddress = serviceAddress;
        }

        @Override
        public void run(){
            DatagramChannel dc = null;
            try{

                /*每个实际上可以创建多个通道连接同一个服务器地址，
                我们这里为了演示方便，只创建了一个通道*/
                dc = DatagramChannel.open();

                /*客户端采用非阻塞模式*/
                dc.configureBlocking(false);

                /*这里的连接不是指TCP的握手连接，因为UDP协议本身不需要连接，
                 *这里连接的意思大概是提前向操作系统申请好本地端口号，以及高速操作系统要发送的目的
                 *连接后的UDP通道可以提高发送的效率，还可以调用read和write方法接收和发送数据
                 *未连接的UDP通道只能调用receive和send方法接收和发送数据*/
                dc.connect(serviceAddress);

                Selector selector = Selector.open();
                int interest = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
                dc.register(selector, interest, new Buffers(256, 256));

                int i = 0;
                while(!Thread.currentThread().isInterrupted()){

                    selector.select();

                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while(it.hasNext()){

                        SelectionKey key = it.next();
                        it.remove();

                        Buffers buffers = (Buffers)key.attachment();

                        ByteBuffer readBuffer = buffers.getReadBuffer();
                        ByteBuffer writeBuffer = buffers.gerWriteBuffer();

                        try{

                            if(key.isReadable()){
                                dc.read(readBuffer);
                                readBuffer.flip();
                                CharBuffer charBuffer = Charset.defaultCharset().decode(readBuffer);
                                System.out.println(charBuffer.array());
                                readBuffer.clear();
                            }

                            if(key.isWritable()){
                                writeBuffer.put((name + (i++)).getBytes());
                                writeBuffer.flip();
                                dc.write(writeBuffer);
                                writeBuffer.clear();

                                Thread.sleep(500);
                            }

                        }catch(IOException e){
                            key.cancel();
                            key.channel().close();
                        }
                    }
                }
            }catch(InterruptedException e){
                System.out.println(name + "interrupted");
            } catch (IOException e) {
                System.out.println(name + "encounter connect error");
            } finally{
                try {
                    dc.close();
                } catch (IOException e) {
                    System.out.println(name + "encounter close error");
                }finally{
                    System.out.println(name + "closed");
                }
            }
        }
    }

    public static void main(String[] args){

        InetSocketAddress serviceAddress = new InetSocketAddress("192.168.1.100", 8080);

        UDPEchoClient clientA = new UDPEchoClient("thread a ", serviceAddress);
        UDPEchoClient clientB = new UDPEchoClient("thread b ", serviceAddress);
        UDPEchoClient clientC = new UDPEchoClient("thread c ", serviceAddress);

        new Thread(clientA).start();
        new Thread(clientB).start();
        new Thread(clientC).start();

    }
}