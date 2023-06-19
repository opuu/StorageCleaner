import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DumpFiles {
    private static final int MAX_DEPTH = 6; // Maximum depth of folder tree
    private static final int MAX_FILES = 12; // Maximum number of files in each folder
    private static final int MAX_DUPLICATES = 8; // Maximum number of duplicate files
    public static final int MAX_FILE_SIZE = 100000; // Maximum file size in bytes

    public static void generateFolderTree(File rootFolder, int depth) throws IOException {
        if (depth <= 0) {
            return;
        }

        Random random = new Random();
        int numFiles = random.nextInt(MAX_FILES) + 1;

        for (int i = 0; i < numFiles; i++) {
            File file = new File(rootFolder, "file" + i + ".txt");
            file.createNewFile();

            // Generate random content for the file
            String content = generateRandomString();
            writeToFile(file, content);

            // Generate duplicate files
            int numDuplicates = random.nextInt(MAX_DUPLICATES);
            for (int j = 0; j < numDuplicates; j++) {
                File duplicateFile = new File(rootFolder, "file" + i + "_duplicate" + j + ".txt");
                duplicateFile.createNewFile();
                writeToFile(duplicateFile, content);
            }
        }

        int numSubfolders = random.nextInt(MAX_DEPTH - depth + 1) + 1;
        for (int i = 0; i < numSubfolders; i++) {
            File subfolder = new File(rootFolder, "folder" + i);
            subfolder.mkdir();

            generateFolderTree(subfolder, depth - 1);
        }
    }

    private static String generateRandomString() {
        int length = new Random().nextInt(MAX_FILE_SIZE) + 1;
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private static void writeToFile(File file, String content) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }

    public static void main(String[] args) {
        try {
            File rootFolder = new File("data/"); // Replace with desired root folder path

            // Create an array of Runnable tasks
            Runnable[] tasks = new Runnable[Runtime.getRuntime().availableProcessors()];
            for (int i = 0; i < tasks.length; i++) {
                tasks[i] = () -> {
                    try {
                        generateFolderTree(rootFolder, MAX_DEPTH);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
            }

            // Create and start threads for the tasks
            Thread[] threads = new Thread[tasks.length];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(tasks[i]);
                threads[i].start();
            }

            // Wait for all threads to finish
            for (Thread thread : threads) {
                thread.join();
            }

            System.out.println("Folder tree generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
