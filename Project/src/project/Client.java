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
            RDT Client = new RDT(socket);
            Scanner sc = new Scanner(System.in);
            String msg;
            while (true) {
                msg = sc.next();              
                Client.send_rdt(msg);
                if (msg.equals("end")) {
                    socket.close();
                    throw new Exception();
                }
                String received = Client.receive_rdt();
                System.out.println(received);
            }

        } 
        catch (Exception ex) {
            System.out.println("closed");
            
        }
    }
}
