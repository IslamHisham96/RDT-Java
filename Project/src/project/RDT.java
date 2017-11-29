/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Islam
 */
public class RDT {

    private static byte senderSeqNumber = 0;
    private static byte receiverSeqNumber = 0;
    private static boolean acknowledged = false;

    public static InetAddress address;
    public static short port;

    static {
        try {
            address = InetAddress.getByName("localhost");
            port = 4445;
        } catch (Exception ex) {
        }
    }

    public static DatagramPacket make_pkt(String data, byte seq) {
        byte[] buf;
        buf = data.getBytes();
        buf = Arrays.copyOf(buf, buf.length + 1);
        buf[buf.length - 1] = seq;
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        return packet;
    }
    
    public static String receive_rdt(DatagramSocket socket){
        boolean isOldAcknowledged = false;
        while(!isOldAcknowledged){
            DatagramPacket packet = new DatagramPacket(new byte[256], 256);
            try {
                socket.receive(packet);
                
            } catch (Exception ex) {}
        } 
        return null;
    }

    public static void send_rdt(DatagramSocket socket, String data) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        try {
            while (!acknowledged) {
                DatagramPacket sendpacket = make_pkt(data, senderSeqNumber);
                socket.send(sendpacket);
                try {
                    
                    FutureTask<String> ft = new FutureTask<String>(new Callable<String>() {
                        @Override
                        public String call() throws Exception {

                            while (!acknowledged) {
                                byte[] buf = new byte[1];
                                DatagramPacket packet = new DatagramPacket(buf, 1);
                                socket.receive(packet);
                                byte ack = (packet.getData())[0];
                                if (ack == senderSeqNumber) {
                                    acknowledged = true;
                                }
                            }

                            return null;
                        }
                    });
                    es.execute(ft);
                    ft.get(100, TimeUnit.MILLISECONDS);
                } catch (Exception ex) {
                }

                //futuretask
            }
            senderSeqNumber = (byte) ((senderSeqNumber + 1) % 2);
            acknowledged = false;
            
        } catch (Exception ex) {}
        finally{
            es.shutdown();
        }
    }

    public static void main(String[] args) {
        String data = "ss";
        //byte [] bytes = data.getBytes();
        byte seq = 1;
        byte[] buf = data.getBytes();
        buf = Arrays.copyOf(buf, buf.length + 1);
        buf[buf.length - 1] = seq;
        //System.out.println(Arrays.toString(buf));
        //buf[buf.length-1]=seq;
    }
}
