/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

/**
 *
 * @author Islam
 */
class EchoHandler extends Thread {

    private Socket s;

    public EchoHandler(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            OutputStreamWriter osr = new OutputStreamWriter(s.getOutputStream());
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pr = new PrintWriter(osr);
            while (true) {
                String str = br.readLine();
                System.out.println(str);
                pr.println(str.toUpperCase());
                pr.flush();
                if (str.equalsIgnoreCase("salam")) {
                    break;
                }
            }
            s.close();
        } catch (Exception ex) {
        }

    }
}

public class Server {

    public static void main(String[] args) {
        try {
            DatagramSocket socket;
            boolean running;
            byte[] buf = new byte[256];
            socket = new DatagramSocket(4445);
            running = true;

            while (true) {
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte [] sent = new String(buf,"UTF-8").toUpperCase().getBytes(Charset.forName("UTF-8"));
                packet = new DatagramPacket(sent, sent.length, address, port);
                String received
                        = new String(packet.getData(), 0, packet.getLength());

                if (received.equals("end")) {
                   continue;
                }
                socket.send(packet);
            }
          //  socket.close();
        } catch (Exception ex) {
        }
    }
}
