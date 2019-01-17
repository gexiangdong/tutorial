import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class DNSClient {
    private final static int TIME_OUT = 10000;
    private final static int PORT = 53;
    private final static int BUF_SIZE = 8192;
    private String dnsServer;

    public DNSClient(){
        this("8.8.8.8");
    }
    public DNSClient(String dnsServer){
        this.dnsServer = dnsServer;
    }

    private List<String> query(String domainName)
            throws SocketTimeoutException, IOException {
        DatagramSocket socket = new DatagramSocket(0);
        socket.setSoTimeout(TIME_OUT);

        ByteArrayOutputStream outBuf = new ByteArrayOutputStream(BUF_SIZE);
        DataOutputStream output = new DataOutputStream(outBuf);
        encodeDNSMessage(output, domainName);

        InetAddress host = InetAddress.getByName(dnsServer);
        DatagramPacket request = new DatagramPacket(outBuf.toByteArray(), outBuf.size(), host, PORT);

        socket.send(request);

        byte[] inBuf = new byte[BUF_SIZE];
        ByteArrayInputStream inBufArray = new ByteArrayInputStream(inBuf);
        DataInputStream input = new DataInputStream(inBufArray);
        DatagramPacket response = new DatagramPacket(inBuf, inBuf.length);

        socket.receive(response);

        List<String> ipList = decodeDNSMessage(input);

        socket.close();

        return ipList;
    }

    private void encodeDNSMessage(DataOutputStream output, String domainName)
            throws IOException {
        // transaction id
        output.writeShort(1);
        // flags
        output.writeShort(0x100);
        // number of queries
        output.writeShort(1);
        // answer, auth, other
        output.writeShort(0);
        output.writeShort(0);
        output.writeShort(0);

        encodeDomainName(output, domainName);

        // query type
        output.writeShort(1);
        // query class
        output.writeShort(1);

        output.flush();
    }

    private void encodeDomainName(DataOutputStream output, String domainName)
            throws IOException {
        //StringUtils.split(domainName, '.')
        String[] ary = domainName.split("[.]");
        for(String label : ary) {
            output.writeByte((byte)label.length());
            output.write(label.getBytes());
        }
        output.writeByte(0);
    }

    private List<String> decodeDNSMessage(DataInputStream input)
            throws IOException {
        List<String> ipList = new ArrayList<>();
        // header
        // transaction id
        input.skip(2);
        // flags
        input.skip(2);
        // number of queries
        input.skip(2);
        // answer, auth, other
        short numberOfAnswer = input.readShort();
        input.skip(2);
        input.skip(2);

        // question record
        skipDomainName(input);
        // query type
        input.skip(2);
        // query class
        input.skip(2);

        // answer records
        for (int i = 0; i < numberOfAnswer; i++) {
            input.mark(1);
            byte ahead = input.readByte();
            input.reset();
            if ((ahead & 0xc0) == 0xc0) {
                // compressed name
                input.skip(2);
            } else {
                skipDomainName(input);
            }

            // query type
            short type = input.readShort();
            // query class
            input.skip(2);
            // ttl
            input.skip(4);
            short addrLen = input.readShort();
            if (type == 1 && addrLen == 4) {
                int addr = input.readInt();
                ipList.add(longToIp(addr));
            } else {
                input.skip(addrLen);
            }
        }
        return ipList;
    }

    private  void skipDomainName(DataInputStream input) throws IOException {
        byte labelLength = 0;
        do {
            labelLength = input.readByte();
            input.skip(labelLength);
        } while (labelLength != 0);
    }

    private  String longToIp(long ip) {
        return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "." + (ip & 0xFF);
    }


    public static void main(String[] args) {
        DNSClient dnsCLient;
        String domainName = "devmgr.cn";
        if(args.length == 2){
            dnsCLient = new DNSClient(args[0]);
            domainName = args[1];
        }else if(args.length == 1){
            domainName = args[0];
            dnsCLient = new DNSClient();
        }else{
            dnsCLient = new DNSClient();
        }
        try {
            List<String> ipList = dnsCLient.query(domainName);
            System.out.println(domainName + ":");
            for (String ip : ipList) {
                System.out.println("\t" + ip);
            }
        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout");
        } catch (IOException ex) {
            System.out.println("Unexpected IOException: " + ex);
            ex.printStackTrace(System.out);
        }
        
    }

}