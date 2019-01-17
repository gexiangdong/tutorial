import java.io.*;
import java.net.*;


/**
 * TCP客户端的例子，运行前需要先启动TcpServer。
 * 可以命令行参数传递一个字符串，如果没有命令行参数，则用"hello, tcpserver."代替。
 * 把字符串发送给TcpServer，并显示TcpServer的返回值
 */
public class TcpClient {
    
    public static void main(String argv[]) throws Exception {
        String sentence;
        String responseString;

        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        if(argv.length > 0){
            sentence = argv[0];
        }else{
            sentence = "hello, tcpserver.";
        }
        outToServer.writeBytes(sentence + '\n');
        responseString = inFromServer.readLine();
        System.out.println("Send to SERVER: " + sentence);
        System.out.println("Received: " + responseString);
        clientSocket.close();
    }
    
}