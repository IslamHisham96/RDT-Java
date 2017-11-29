/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.*;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Islam
 */
public class RDT {

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

    public static void send_rdt(DatagramSocket socket, String data, byte seq) {
        try {
            boolean acknowledged = false;
            while (!acknowledged) {
                DatagramPacket sendpacket = make_pkt(data, seq);
                socket.send(sendpacket);
                ExecutorService es = Executors.newFixedThreadPool(1);
                FutureTask<String> ft = new FutureTask<String>(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        
                        while(!acknowledged) {
                            
                        } 
                        
                        return null;
                    }
                });
                es.execute(ft);
                try {
                    ft.get(100, TimeUnit.MILLISECONDS);
                } catch (Exception ex) {
                }
                es.shutdown();
                //futuretask

            }
        } catch (Exception ex) {
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
