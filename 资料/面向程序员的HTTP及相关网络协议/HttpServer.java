import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HttpServer {

    public static void main(String[] argv) throws Exception {
        int port = 8090;
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            System.err.println("等待客户端接入... ...");
            Socket client = serverSocket.accept();
            System.err.println("有客户端接入 " + client.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

            String action = in.readLine();
            if(action == null) {
                break;
            }
            System.out.println("客户端action: " + action);
            StringBuffer clientHeaders = new StringBuffer();
            for (String s = in.readLine(); s != null && s.length() > 0; s = in.readLine()) {
                clientHeaders.append(s);
                clientHeaders.append("\r\n");
            }
            System.out.println("客户端发送的Http Headers:\r\n" + clientHeaders);
            if (action.startsWith("GET ")) {
                // ok
                String html = "<!DOCTYPE html>\r\n" + "<html><head><title>hello,world</title>"
                        + "<body><h1>Hello, World!</h1><form method='POST'><input type='submit'></form></body></html>";
                out.print("HTTP/1.1 200 OK\r\n");
                // out.print("Date:" + new Date().toGMTString() + "\r\n");
                out.print("Context-Type: text/html\r\n");
                out.print("Content-Length:" + html.length() + "\r\n");
                out.print("Connection:close\r\n");
                out.print("\r\n");
                out.print(html);
            } else {
                out.print("HTTP/1.1 405 METHOD NOT ALLOWED\r\n");
                out.print("Connection:close\r\n");
                out.print("\r\n");
                out.print("方法 (" + action + ") 不允许。\r\n");
            }
            out.close();
            in.close();
            client.close();
        }
    }
}