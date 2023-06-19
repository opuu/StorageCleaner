import java.util.List;
import java.util.Scanner;

import Helpers.CreateTree;
import Helpers.DuplicateFinder;
import Helpers.TreeNode;

public class StorageCleaner {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        long saving = 0;
        System.out.println("====================================");
        System.out.println("=                                  =");
        System.out.println("=  Welcome to the Storage Cleaner  =");
        System.out.println("=                                  =");
        System.out.println("====================================");

        System.out.println("This program will help you clean your storage by removing duplicate files.");
        System.out.println("Please enter a path for the program to start cleaning from.");
        System.out.println("Example: C:\\Users\\Opu\\ or /home/opu/");
        System.out.print("Path: ");

        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        scanner.close();

        // count time
        long startTime = System.currentTimeMillis();

        System.out.println(ANSI_YELLOW + "Scanning: " + path + " and its subdirectories..." + ANSI_RESET);

        System.out.println(ANSI_YELLOW + "Creating tree..." + ANSI_RESET);
        TreeNode root = CreateTree.createTree(path);
        CreateTree.printTree(root);

        System.out.println(ANSI_CYAN + "Searching unique hashes..." + ANSI_RESET);
        List<String> hashes = DuplicateFinder.getUniqueHashes(root, 10000);
        System.out.println("Total unique files: " + hashes.size());
        System.out.println();
        System.out.println("Searching duplicates:");
        for (String hash : hashes) {
            List<String> paths = CreateTree.findPaths(root, hash);
            if (paths.size() > 1) {

                System.out.println("File: " + paths.get(0));
                System.out.println("Hash: " + hash);
                System.out.println(ANSI_YELLOW + "Duplicate found: " + (paths.size() - 1) + " times" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "+Keeping: " + paths.get(0) + ANSI_RESET);
                // remove all except the first path
                for (int i = 1; i < paths.size(); i++) {
                    System.out.println(ANSI_RED + "-Removing: " + paths.get(i) + ANSI_RESET);
                    saving += CreateTree.removeFile(paths.get(i));
                }
                System.out.println();
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println(ANSI_CYAN + "Done!" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Total saving: " + saving + " bytes" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Total saving (KB): " + (saving / 1024) + " KB" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Total saving (MB): " + (saving / 1024 / 1024) + " MB" + ANSI_RESET);

        System.out.println(ANSI_PURPLE + "Total time: " + (endTime - startTime) + " ms" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Total time (s): " + (endTime - startTime) / 1000 + " s" + ANSI_RESET);
    }
}
