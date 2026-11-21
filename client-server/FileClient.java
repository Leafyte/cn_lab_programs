/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 5 : TCP/IP SOCKET - FILE TRANSFER (CLIENT)
 * ----------------------------------------------------------------------------
 *  Title : Using TCP/IP sockets, write a client-server program to make the
 *          client send the file name and to make the server send back the
 *          contents of the requested file if present.
 *  Aim   : Client program - sends a file name to the server and displays
 *          the contents received from the server.
 *  Run   : javac FileClient.java  ->  java FileClient   (after server starts)
 * ============================================================================
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FileClient {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5000;
        Scanner sc = new Scanner(System.in);

        try {
            // ---------------- Connect to the server ----------------
            Socket socket = new Socket(serverAddress, port);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // ---------------- Send file name ----------------
            System.out.print("Enter the file name: ");
            String fileName = sc.nextLine();
            out.println(fileName);

            // ---------------- Receive and display response ----------------
            String response = in.readLine();
            if ("FILE_FOUND".equals(response)) {
                System.out.println("\nFile contents:");
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.equals("END_OF_FILE")) {
                        break;
                    }
                    System.out.println(line);
                }
            } else {
                System.out.println("\nFile not found on the server.");
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
