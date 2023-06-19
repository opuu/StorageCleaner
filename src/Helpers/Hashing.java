package Helpers;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hashing {
    // get number of available processors dynamically
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * Generates a hash value for the given content string.
     *
     * @param content the content string to be hashed
     * @param salt    the salt string to be hashed
     * @return hexadecimal hash value
     */
    public static String hash(String content, String salt) {
        int[] coefficients = { 7, 12, 17, 22 }; // 4 coefficients
        int[] powers = { 1, 2, 4, 8 }; // 4 powers

        int hash = 0;

        // Calculate hash using content characters
        for (int i = 0; i < content.length(); i++) {
            hash = (hash * 31 + content.charAt(i)) % 1000000007;
        }

        // Calculate hash using salt characters
        for (int i = 0; i < salt.length(); i++) {
            hash = (hash * 31 + salt.charAt(i)) % 1000000007;
        }

        // Incorporate coefficients and powers into the hash
        for (int i = 0; i < coefficients.length; i++) {
            hash = (hash * powers[i] + coefficients[i]) % 1000000007;
        }

        // convert hash to base Character.MAX_RADIX
        return Integer.toString(hash, Character.MAX_RADIX);
    }

    /**
     * Generates a hash value for the given content string.
     * Method overloading is used to provide a default salt value. (213902018)
     *
     * @param content the content string to be hashed
     * @return hexadecimal hash value
     */
    public static String hash(String content) {
        return hash(content, "213902018");
    }

    /**
     * Generates a hash value for the given file.
     *
     * @param path the path of the file to be hashed
     * @return hexadecimal hash value
     */
    public static String hashFile(String path) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            Path filePath = Paths.get(path);

            if (Files.exists(filePath) && Files.size(filePath) > 0) {
                BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
                char[] buffer = new char[4096]; // Buffer size for reading chunks of the file
                int bytesRead;
                while ((bytesRead = reader.read(buffer)) != -1) {
                    contentBuilder.append(buffer, 0, bytesRead);
                }
                reader.close();
            }
        } catch (Exception e) {
        }
        return hash(contentBuilder.toString());
    }

    /**
     * Test the performance of the hash function using multiple threads.
     */
    public static void benchmark() {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        long startTime = System.nanoTime();
        int iteration_num = 1000000; // Number of iterations to run

        for (int i = 0; i < iteration_num; i++) {
            final int index = i;
            executor.execute(() -> {
                System.out.println(hash(
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                                "when an unknown printer took a galley of type and scrambled it to make a type " +
                                "specimen book. It has survived not only five centuries, but also the leap into " +
                                "electronic typesetting, remaining essentially unchanged. It was popularised in " +
                                "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, " +
                                "and more recently with desktop publishing software like Aldus PageMaker including " +
                                "versions of Lorem Ipsum.",
                        "Obaydur Rahman" + index)); // remove the println to get accurate time
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) / iteration_num + "ms");
        System.out.println("Total iterations: " + iteration_num);
    }
}
