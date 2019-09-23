package com.procx.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * 处理类
 *
 */

public class ServerHandler implements Runnable {
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            String message;
            String result;
            // 通过输入流读取客户端传输的数据
            while ((message = br.readLine()) != null) {
                System.out.println("server receive data:" + message);
                result = response(message);
                // 将业务结果通过输出流返回给客户端
                pw.println(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }

    // 返回给客户端的应答
    public static String response(String msg) {
        return "Hello," + msg + ",Now is " + new java.util.Date(System.currentTimeMillis()).toString();
    }
}