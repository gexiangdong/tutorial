import java.io.*;
import java.net.*;

/**
 * 一个TCP Server的例子，收到字符串后，把字符串变成大写并返回给客户端
 */
public class TcpServer {
    
    public static void main(String argv[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("Server started, waiting for connections on 6789.");
        while (true) {
            System.out.println("waiting...");
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("One client connected.");
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            String clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);
            String capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
            outToClient.close();
            System.out.println("close client connection.");
            connectionSocket.close();
        }
    }
    
}