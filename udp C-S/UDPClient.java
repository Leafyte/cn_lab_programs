/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 6 : DATAGRAM (UDP) SOCKET - CLIENT
 * ----------------------------------------------------------------------------
 *  Title : Develop a program using Datagram Socket for client/server
 *          communication to display the messages on the client side, typed
 *          at the server side.
 *  Aim   : Client program - receives DatagramPackets from the server and
 *          displays the messages. Stops when "exit" is received.
 *  Run   : javac UDPClient.java  ->  java UDPClient
 * ============================================================================
 */

import java.net.*;

public class UDPClient {

    public static void main(String[] args) {
        int port = 5001;

        try {
            // ---------------- Create socket ----------------
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("UDP Client started.");
            System.out.println("Waiting for messages...");

            // ---------------- Receive and display messages ----------------
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Message from Server: " + message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            socket.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
