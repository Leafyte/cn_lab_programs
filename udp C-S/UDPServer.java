/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 6 : DATAGRAM (UDP) SOCKET - SERVER
 * ----------------------------------------------------------------------------
 *  Title : Develop a program using Datagram Socket for client/server
 *          communication to display the messages on the client side, typed
 *          at the server side.
 *  Aim   : Server program - reads messages from the keyboard and sends them
 *          to the client as DatagramPackets. Type "exit" to stop.
 *  Run   : javac UDPServer.java  ->  java UDPServer   (start client first)
 * ============================================================================
 */

import java.net.*;
import java.util.Scanner;

public class UDPServer {

    public static void main(String[] args) {
        int port = 5000;

        try {
            // ---------------- Create socket and set client details ----------------
            DatagramSocket socket = new DatagramSocket(port);
            Scanner sc = new Scanner(System.in);
            InetAddress clientAddress = InetAddress.getByName("localhost");
            int clientPort = 5001;

            System.out.println("UDP Server started.");

            // ---------------- Read and send messages ----------------
            while (true) {
                System.out.print("Enter message: ");
                String message = sc.nextLine();
                byte[] data = message.getBytes();

                DatagramPacket packet = new DatagramPacket(
                        data,
                        data.length,
                        clientAddress,
                        clientPort
                );
                socket.send(packet);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            socket.close();
            sc.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
