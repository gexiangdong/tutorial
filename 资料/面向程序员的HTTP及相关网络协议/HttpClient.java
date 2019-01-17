import java.net.*;
import java.io.*;

public class HttpClient {

    public static void main(String[] args) throws Exception {

        String hostname = "www.baidu.com";
        int port = 80;

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.print("GET / HTTP/1.1\r\n");
            writer.print("Host: " + hostname + ":" + port + "\r\n");
            writer.print("User-Agent: MSIE\r\n");
            writer.print("Accept: text/html\r\n");
            writer.print("Accept-Language: en-US\r\n");
            writer.print("Connection: close\r\n");
            writer.print("\r\n");

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
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