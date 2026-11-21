/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 2 : CRC-CCITT
 * ----------------------------------------------------------------------------
 *  Title : Develop a program for error detecting code using CRC-CCITT.
 *  Aim   : To develop and execute a Java program to implement error
 *          detection using CRC-CCITT (Cyclic Redundancy Check).
 *          Generator polynomial: x^16 + x^12 + x^5 + 1 (0x1021)
 * ============================================================================
 */

import java.util.Scanner;

public class CRC_CCITT {

    // Performs modulo-2 (XOR) division and returns the remainder
    public static String divide(String data, String generator) {
        char[] bits = data.toCharArray();
        int dataLength = bits.length;
        int generatorLength = generator.length();

        for (int i = 0; i <= dataLength - generatorLength; i++) {
            if (bits[i] == '1') {
                for (int j = 0; j < generatorLength; j++) {
                    if (bits[i + j] == generator.charAt(j)) {
                        bits[i + j] = '0';
                    } else {
                        bits[i + j] = '1';
                    }
                }
            }
        }

        return new String(
                bits,
                dataLength - generatorLength + 1,
                generatorLength - 1
        );
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // CRC-CCITT generator polynomial
        String generator = "10001000000100001";

        // ---------------- Read data bits ----------------
        System.out.print("Enter the data bits: ");
        String data = sc.nextLine();
        if (!data.matches("[01]+")) {
            System.out.println("Invalid input. Enter only binary bits (0 or 1).");
            sc.close();
            return;
        }

        // ---------------- Sender side: append 16 zeros and compute CRC ----------------
        String appendedData = data + "0000000000000000";
        String crc = divide(appendedData, generator);
        String codeword = data + crc;

        System.out.println("\n--- Sender Side ---");
        System.out.println("Original Data       : " + data);
        System.out.println("Generator Polynomial: " + generator);
        System.out.println("CRC Remainder       : " + crc);
        System.out.println("Transmitted Codeword: " + codeword);

        // ---------------- Receiver side: read received codeword ----------------
        System.out.print("\nEnter the received codeword: ");
        String received = sc.nextLine();
        if (!received.matches("[01]+") || received.length() < generator.length()) {
            System.out.println("Invalid input. Enter a binary codeword of at least "
                    + generator.length() + " bits.");
            sc.close();
            return;
        }

        String remainder = divide(received, generator);
        System.out.println("\n--- Receiver Side ---");
        System.out.println("Received Codeword: " + received);
        System.out.println("Remainder        : " + remainder);

        // ---------------- Check remainder for errors ----------------
        boolean error = false;
        for (int i = 0; i < remainder.length(); i++) {
            if (remainder.charAt(i) == '1') {
                error = true;
                break;
            }
        }

        if (error) {
            System.out.println("Error detected in received data.");
        } else {
            System.out.println("No error detected.");
        }

        sc.close();
    }
}
