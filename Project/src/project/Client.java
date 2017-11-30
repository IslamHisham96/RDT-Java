/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author Islam
 */
public class Client {

    public static void main(String[] args) {
        try {          
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet;           
            InetAddress address = InetAddress.getByName("localhost");
            Scanner sc = new Scanner(System.in);
            byte[] buf;
            String msg;
            while (true) {
                msg = sc.next();
                if (msg.equals("end")) {
                    throw new Exception();
                }
                buf = msg.getBytes();
                packet = new DatagramPacket(buf, buf.length, address, 4445);
                socket.send(packet);
                packet = new DatagramPacket(new byte[256], 256);
                socket.receive(packet);
                String received = new String(packet.getData());
                System.out.println(received);
            }

        } catch (Exception ex) {
            System.out.println("closed");
        }
    }
}
