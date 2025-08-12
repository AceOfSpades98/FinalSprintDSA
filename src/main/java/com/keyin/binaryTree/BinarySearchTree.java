package com.keyin.binarytree;

// Need to later integrate persistence to make this the service layer

public class BinarySearchTree {

    TreeNode root;

    public void insert(TreeNode node) {
        root = insertHelper(root, node);
    }

    private TreeNode insertHelper(TreeNode root, TreeNode node) {
        int value = node.value;
        
        if (root == null) {
            root = node;
            return root;
        } else if (value < root.value) {
            root.left = insertHelper(root.left, node);
        } else {
            root.right = insertHelper(root.right, node);
        }

        return root;
    }

    public void display() {
        displayHelper(root);
    }

    private void displayHelper(TreeNode root) {
        if (root  != null) {
            displayHelper(root.left);
            System.out.println(root.value);
            displayHelper(root.right);
        }
    }

    public boolean search(int value) {
        return searchHelper(root, value);
    }

    private boolean searchHelper(TreeNode root, int value) {
        if(root == null) {
            return false;
        } else if (root.value == value) {
            return true;
        } else if (root.value > value) {
            return searchHelper(root.left, value);
        } else {
            return searchHelper(root.right, value);
        }
    }

    public void remove(int value) {
        if (search(value)) {
            removeHelper(root, value);
        } else {
            System.out.println("Data could not be found.");
        }
    }

    public TreeNode removeHelper(TreeNode root, int value) {
        if (root == null) {
            return root;
        } else if (value < root.value) {
            root.left = removeHelper(root.left, value);
        } else if (value > root.value) {
            root.right = removeHelper(root.right, value);
        } else {
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.right != null) {
                root.value = successor(root);
                root.right = removeHelper(root.right, root.value);
            } else {
                root.value = predecessor(root);
                root.left = removeHelper(root.left, root.value);
            }
        }

        return root;
    }

    private int successor(TreeNode root) {
        root = root.right;
        while (root.left != null) {
            root = root.left;
        }
        return root.value;
    }

    private int predecessor(TreeNode root) {
        root = root.left;
        while (root.right != null) {
            root = root.right;
        }
        return root.value;
    }
}
