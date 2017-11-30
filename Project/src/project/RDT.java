/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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

    private byte senderSeqNumber = 0;
    private byte receiverSeqNumber = 0;
    private boolean acknowledged = false;

    private DatagramSocket socket;
    private InetAddress address;
    private short port;

    public RDT(DatagramSocket socket, InetAddress address, short port) {
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

   

    public RDT(DatagramSocket socket) {
        try {
            this.socket = socket;
            address = InetAddress.getByName("localhost");
            port = 4445;
        } catch (Exception ex) {
        }
    }

    public DatagramPacket make_pkt(String data, byte seq) {
        byte[] buf;
        buf = data.getBytes();
        buf = Arrays.copyOf(buf, buf.length + 1);
        buf[buf.length - 1] = seq;
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        return packet;
    }

    public String receive_rdt() {
        boolean isOldAcknowledged = false;
        while (!isOldAcknowledged) {
            DatagramPacket packet = new DatagramPacket(new byte[256], 256);
            try {
                socket.receive(packet);
                if (packet.getLength() == 1) //ack
                {

                } else {

                }
            } catch (Exception ex) {
            }
        }
        return null;
    }

    public void send_rdt(String data) {
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
                                if (packet.getLength() == 1) { //ack 
                                    byte ack = (packet.getData())[0];
                                    if (ack == senderSeqNumber) {
                                        acknowledged = true;
                                    }
                                } else { // send the last ack if available

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

        } catch (Exception ex) {
        } finally {
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
