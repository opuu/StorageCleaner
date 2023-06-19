package Helpers;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class CreateTree {
    /**
     * Creates a hierarchical file system tree structure from the given root folder
     * path (Uses DFS)
     * 
     * @param path the root folder path
     * @return the file system tree structure
     */
    public static TreeNode createTree(String path) {
        File rootFolder = new File(path);
        TreeNode rootNode = new TreeNode(rootFolder.getName(), Hashing.hash(rootFolder.getName()), "folder");

        File[] files = rootFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    TreeNode fileNode = new TreeNode(file.getName(), Hashing.hashFile(file.getAbsolutePath()),
                            "file");
                    rootNode.addChild(fileNode);
                } else if (file.isDirectory()) {
                    TreeNode subfolderNode = createTree(file.getAbsolutePath());
                    rootNode.addChild(subfolderNode);
                }
            }
        }

        return rootNode;
    }

    /**
     * Finds the full path of the node with the given hash.
     * 
     * @param root the root node of the tree
     * @param hash the hash of the node to search for
     * @return list of the full path of the node with the given hash
     */
    public static List<String> findPaths(TreeNode root, String hash) {
        List<String> paths = new ArrayList<>();
        // store the full path of the node with the given hash
        if (root.getHash().equals(hash)) {
            paths.add(root.getName());
        }
        // recursively search for the node with the given hash (using DFS)
        for (TreeNode child : root.getChildren()) {
            List<String> childPaths = findPaths(child, hash);
            // if the node with the given hash is found in the current child,
            // add the current node to the path
            if (!childPaths.isEmpty()) {
                for (String childPath : childPaths) {
                    paths.add(root.getName() + "/" + childPath);
                }
            }
        }
        return paths;
    }

    /**
     * Prints the file system hierarchical tree.
     * 
     * @param root the root node of the tree
     */
    public static void printTree(TreeNode root) {
        printTree(root, 0);
    }

    /**
     * Prints the file system hierarchical tree.
     * 
     * @param node  the current node
     * @param depth the depth of the current node
     */
    private static void printTree(TreeNode node, int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("|  ");
        }
        sb.append("|--");
        sb.append(node.getName() + " (" + node.getHash() + ")");
        System.out.println(sb.toString());

        for (TreeNode child : node.getChildren()) {
            printTree(child, depth + 1);
        }
    }

    /**
     * Remove file from the file system.
     * 
     * @param path the path of the file to remove
     */
    public static long removeFile(String path) {
        File file = new File(path);
        // get the file size
        long fileSize = file.length();

        // remove the file
        file.delete();

        // remove empty folders
        File parent = file.getParentFile();
        long folderSize = 0;
        while (parent != null && parent.list().length == 0) {
            // get the folder size
            folderSize = parent.length();
            parent.delete();
            parent = parent.getParentFile();
        }

        long totalSize = fileSize + folderSize;

        // System.out.println("File removed successfully!");
        // System.out.println("Saved " + totalSize + " bytes.");
        return totalSize;
    }
}
