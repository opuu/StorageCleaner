package Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class DuplicateFinder {
    /**
     * Returns a list of unique hashes in the file system tree (Uses BFS)
     * 
     * @param root the root node of the tree
     * @return list of unique hashes in the file system tree
     */
    public static List<String> getUniqueHashes(TreeNode root, int queueSize) {
        List<String> uniqueHashes = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayBlockingQueue<>(queueSize);
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.getType().equals("file")) {
                if (!uniqueHashes.contains(node.getHash())) {
                    uniqueHashes.add(node.getHash());
                }
            } else {
                for (TreeNode child : node.getChildren()) {
                    queue.add(child);
                }
            }
        }

        return uniqueHashes;
    }

    public static void main(String[] args) {
        TreeNode root = CreateTree.createTree("data/");
        List<String> hashes = getUniqueHashes(root, 1000);

        for (String hash : hashes) {
            List<String> paths = CreateTree.findPaths(root, hash);
            if (paths.size() > 1) {
                System.out.println("Hash: " + hash);
                for (String path : paths) {
                    System.out.println(path);
                }
                System.out.println();
            }
        }
    }
}
