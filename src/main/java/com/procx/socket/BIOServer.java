package com.procx.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    private static ServerSocket server;
    // 线程池,处理每个客户端请求
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException {
        try {
            // 通过构造函数创建ServerSocket
            server = new ServerSocket(7777);
            System.out.println("服务器已启动，端口号：" + 7777);
            while (true) {
                // 真正处理的还是Socket
                Socket socket = server.accept();// 阻塞方法
                // 把客户端请求打包成一个任务，放到线程池执行
                executorService.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                server.close();
            }
        }

    }

}