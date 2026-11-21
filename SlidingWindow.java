/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 3 : SLIDING WINDOW PROTOCOL
 * ----------------------------------------------------------------------------
 *  Title : Develop a program to implement a Sliding Window Protocol in the
 *          Data Link Layer.
 *  Aim   : To develop and execute a Java program to implement the Sliding
 *          Window Protocol (Go-Back-N style) used for reliable data
 *          transmission in the data link layer.
 * ============================================================================
 */

import java.util.Scanner;

public class SlidingWindow {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ---------------- Read inputs ----------------
        System.out.print("Enter the total number of frames: ");
        int totalFrames = sc.nextInt();
        System.out.print("Enter the window size: ");
        int windowSize = sc.nextInt();
        System.out.print("Enter the frame number to simulate loss (-1 for no loss): ");
        int lostFrame = sc.nextInt();

        int i = 0; // first frame of the current window

        while (i < totalFrames) {
            int end = Math.min(i + windowSize, totalFrames);

            // ---------------- Send all frames in the current window ----------------
            System.out.println("\nSending frames:");
            for (int j = i; j < end; j++) {
                System.out.println("Frame " + j + " sent.");
            }

            // ---------------- Receive acknowledgments ----------------
            boolean error = false;
            for (int j = i; j < end; j++) {
                if (j == lostFrame) {
                    System.out.println("Frame " + j + " lost.");
                    error = true;
                    break;
                } else {
                    System.out.println("ACK received for Frame " + j);
                }
            }

            // ---------------- Retransmit or slide the window ----------------
            if (error) {
                System.out.println("Retransmitting from Frame " + lostFrame);
                i = lostFrame;
                lostFrame = -1; // loss is simulated only once
            } else {
                i = end;
                System.out.println("Window slides forward.");
            }
        }

        System.out.println("\nAll frames transmitted successfully.");
        sc.close();
    }
}
