/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 1 : HAMMING CODE
 * ----------------------------------------------------------------------------
 *  Title : Develop a program to implement error detection and error
 *          correction using Hamming Code.
 *  Aim   : To develop and execute a Java program to implement error
 *          detection and error correction using Hamming Code (even parity).
 * ============================================================================
 */

import java.util.Scanner;

public class HammingCode {

    // Checks whether a position is a power of 2 (parity bit position)
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ---------------- Read the data bits ----------------
        System.out.print("Enter the number of data bits: ");
        int m = sc.nextInt();
        int[] data = new int[m];
        System.out.println("Enter the data bits:");
        for (int i = 0; i < m; i++) {
            data[i] = sc.nextInt();
        }

        // ---------------- Calculate number of parity bits (2^r >= m + r + 1) ----------------
        int r = 0;
        while (Math.pow(2, r) < m + r + 1) {
            r++;
        }
        System.out.println("Number of parity bits required: " + r);

        int totalBits = m + r;
        int[] hamming = new int[totalBits + 1];

        // ---------------- Place data bits in non-parity positions ----------------
        int j = 0;
        for (int i = 1; i <= totalBits; i++) {
            if (!isPowerOfTwo(i)) {
                hamming[i] = data[j];
                j++;
            }
        }

        // ---------------- Calculate parity bits (even parity) ----------------
        for (int p = 0; p < r; p++) {
            int parityPosition = (int) Math.pow(2, p);
            int parity = 0;
            for (int i = 1; i <= totalBits; i++) {
                if ((i & parityPosition) != 0) {
                    parity = parity ^ hamming[i];
                }
            }
            hamming[parityPosition] = parity;
        }

        // ---------------- Display generated Hamming Code ----------------
        System.out.println("\nGenerated Hamming Code:");
        for (int i = totalBits; i >= 1; i--) {
            System.out.print(hamming[i]);
        }

        // ---------------- Receiver side: read received code ----------------
        System.out.println("\n\nEnter the received Hamming Code:");
        int[] received = new int[totalBits + 1];
        System.out.println("Enter " + totalBits + " bits:");
        for (int i = totalBits; i >= 1; i--) {
            received[i] = sc.nextInt();
        }

        // ---------------- Check parity and compute error position ----------------
        int errorPosition = 0;
        for (int p = 0; p < r; p++) {
            int parityPosition = (int) Math.pow(2, p);
            int parity = 0;
            for (int i = 1; i <= totalBits; i++) {
                if ((i & parityPosition) != 0) {
                    parity = parity ^ received[i];
                }
            }
            if (parity != 0) {
                errorPosition += parityPosition;
            }
        }

        // ---------------- Detect and correct the error ----------------
        if (errorPosition == 0) {
            System.out.println("\nNo error detected.");
        } else {
            System.out.println("\nError detected at position: " + errorPosition);
            received[errorPosition] = received[errorPosition] == 0 ? 1 : 0;
            System.out.println("Corrected Hamming Code:");
            for (int i = totalBits; i >= 1; i--) {
                System.out.print(received[i]);
            }
            System.out.println();
        }

        sc.close();
    }
}
