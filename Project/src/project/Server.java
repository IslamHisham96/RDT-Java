/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Islam
 */
public class Server {

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(4445);
            DatagramPacket packet;
            InetAddress address;
            int port;
            byte [] send;
            while (true) {
                packet = new DatagramPacket(new byte[256], 256);
                socket.receive(packet);
                String received = new String(packet.getData());
                if (received.equals("end")) {
                    continue;
                }
                address = packet.getAddress();
                port = packet.getPort();
                send = received.toUpperCase().getBytes();
                packet = new DatagramPacket(send, send.length, address, port);
                socket.send(packet);
            }
            //  socket.close();
        } catch (Exception ex) {
        }
    }
}
