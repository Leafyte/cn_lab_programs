/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 5 : TCP/IP SOCKET - FILE TRANSFER (SERVER)
 * ----------------------------------------------------------------------------
 *  Title : Using TCP/IP sockets, write a client-server program to make the
 *          client send the file name and to make the server send back the
 *          contents of the requested file if present.
 *  Aim   : Server program - waits for a client, receives a file name and
 *          sends back the file contents (if present).
 *  Run   : javac FileServer.java  ->  java FileServer   (start this first)
 * ============================================================================
 */

import java.io.*;
import java.net.*;

public class FileServer {

    public static void main(String[] args) {
        int port = 5000;

        try {
            // ---------------- Start server and wait for client ----------------
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started.");
            System.out.println("Waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // ---------------- Receive file name from client ----------------
            String fileName = in.readLine();
            File file = new File(fileName);

            // ---------------- Send file contents or not-found message ----------------
            if (file.exists() && file.isFile()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String line;
                out.println("FILE_FOUND");
                while ((line = fileReader.readLine()) != null) {
                    out.println(line);
                }
                out.println("END_OF_FILE");
                fileReader.close();
            } else {
                out.println("FILE_NOT_FOUND");
            }

            // ---------------- Close connection ----------------
            socket.close();
            serverSocket.close();
            System.out.println("Connection closed.");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
