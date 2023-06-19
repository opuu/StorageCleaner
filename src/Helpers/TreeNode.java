package Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Type of the node in the file system tree.
 */
enum NodeType {
    FILE, FOLDER
}

/**
 * Represents a node in the file system tree.
 */
public class TreeNode {
    private String name;
    private String hash;
    private String type;
    private List<TreeNode> children;

    /**
     * Creates a new tree node.
     * 
     * @param name the name of the node
     * @param hash the hash of the node
     * @param type the type of the node (file or folder)
     */
    public TreeNode(String name, String hash, String type) {
        this.name = name;
        this.hash = hash;
        this.type = type;
        this.children = new ArrayList<>();
    }

    /**
     * Returns the name of the node.
     * 
     * @return the name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the hash of the node.
     * 
     * @return the hash of the node
     */
    public String getHash() {
        return hash;
    }

    /**
     * Returns the type of the node.
     * 
     * @return the type of the node
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the children of the node.
     * 
     * @return the children of the node
     */
    public List<TreeNode> getChildren() {
        return children;
    }

    /**
     * Adds a child to the node.
     * 
     * @param child the child to add
     */
    public void addChild(TreeNode child) {
        children.add(child);
    }
}
