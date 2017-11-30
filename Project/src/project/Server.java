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
            RDT Server = new RDT(socket);
            while (true) {
                
                String received = Server.receive_rdt();
                if (received.equals("end")) {
                    Server.reset_connection();
                    continue;
                }
                Server.send_rdt(received.toUpperCase());
            }
            //  socket.close();
        } catch (Exception ex) {
            
        }
    }
}
