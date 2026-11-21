/*
 * ============================================================================
 *  COMPUTER NETWORKS LABORATORY [BCSCL505] - PART B
 *  EXPERIMENT 7 : LEAKY BUCKET ALGORITHM
 * ----------------------------------------------------------------------------
 *  Title : Develop a program for congestion control using the Leaky Bucket
 *          Algorithm.
 *  Aim   : To develop and execute a Java program to implement congestion
 *          control using the Leaky Bucket Algorithm.
 * ============================================================================
 */

import java.util.Scanner;

public class LeakyBucket {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ---------------- Read inputs ----------------
        System.out.print("Enter the bucket capacity: ");
        int capacity = sc.nextInt();
        System.out.print("Enter the output rate: ");
        int outputRate = sc.nextInt();
        System.out.print("Enter the number of packets: ");
        int n = sc.nextInt();

        int bucket = 0; // current amount of data in the bucket

        // ---------------- Process each arriving packet ----------------
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter size of packet " + i + ": ");
            int packet = sc.nextInt();
            System.out.println("\nPacket " + i + " arrived: " + packet);

            // Add packet to bucket or discard overflow
            if (packet <= capacity - bucket) {
                bucket += packet;
                System.out.println("Packet added to bucket.");
            } else {
                int overflow = packet - (capacity - bucket);
                bucket = capacity;
                System.out.println("Bucket overflow: " + overflow + " packet(s) discarded.");
            }

            System.out.println("Packets in bucket: " + bucket);

            // Transmit at the constant output rate
            if (bucket > 0) {
                int transmitted = Math.min(bucket, outputRate);
                bucket -= transmitted;
                System.out.println("Packets transmitted: " + transmitted);
                System.out.println("Packets remaining: " + bucket);
            }
        }

        // ---------------- Drain remaining packets from the bucket ----------------
        while (bucket > 0) {
            int transmitted = Math.min(bucket, outputRate);
            bucket -= transmitted;
            System.out.println("\nPackets transmitted: " + transmitted);
            System.out.println("Packets remaining: " + bucket);
        }

        System.out.println("\nAll packets processed successfully.");
        sc.close();
    }
}
