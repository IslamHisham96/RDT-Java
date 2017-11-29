/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

//import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Islam
 */
public class Client {

    public static void main(String[] args) {
        try {
            DatagramSocket socket;
            InetAddress address;
            byte[] buf;
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            Scanner sc = new Scanner(System.in);
            String msg;
            while (true) {
                msg = sc.next();
                buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
                socket.send(packet);
                packet = new DatagramPacket(new byte[265], 265);
                socket.receive(packet);
                String received = new String(packet.getData());
                if (received.equals("END")) {
                    break;
                }
                System.out.println(received);
            }

        } catch (Exception ex) {
            System.out.println("closed");
        }
    }
}
