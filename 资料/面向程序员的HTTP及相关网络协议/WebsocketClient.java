import java.net.*;
import java.io.*;

public class WebsocketClient {

    public static void main(String[] args) throws Exception {

        String hostname = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println("GET /chat HTTP/1.1");
            writer.println("Host: " + hostname + ":" + port);
            // 下面两行是告诉服务器端，此次连接的使用的http协议期望升级到websocket协议，如果服务端同意升级会返回
            // Connection: upgrade  upgrade: websocket表示已经升级了，而且状态吗是101 switching protocols
            writer.println("Connection: upgrade");
            writer.println("Upgrade: WebSocket");
            // 下面这2行是必须的，sec-websocket-key是base64格式的一个随机数，由客户端生成
            // sec-websocket-version是指明客户端的websocket版本
            writer.println("Sec-WebSocket-Key: AQIDBAUGBwgJCgsMDQ4PEC==");
            writer.println("Sec-WebSocket-Version: 13");
            writer.println("User-Agent: MSIE");
            writer.println("Origin: http://localhost:8080/");
            writer.println();

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            System.out.println("request sent...");
            int i=0;
            while (i < 10) {
                if (reader.ready()){
                    // 在读websocket发送过来的消息时，这里没有解码消息帧，都当字符串了，消息的数据报文格式前面有些事数据包控制的bit，所以打印时有乱码
                    char[] bin = new char[2048];
                    int len = reader.read(bin, 0, bin.length);
                    char[] buf = new char[len];
                    System.arraycopy(bin, 0, buf, 0, len);
                    System.out.println(new String(buf));
                    i++;
                }
            }
            // 连接好了后可以发送数据，发送的数据报格式是 Base Framing Protocol， ABNF RFC5234
            writer.close();
            output.close();
            socket.close();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}