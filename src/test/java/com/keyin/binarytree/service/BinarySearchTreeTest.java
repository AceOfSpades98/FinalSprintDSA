package com.keyin.binarytree.service;

import com.keyin.binarytree.model.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {

    @Test
    void insertSearchRemoveWorks() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(new TreeNode(5));
        bst.insert(new TreeNode(3));
        bst.insert(new TreeNode(7));

        assertTrue(bst.search(3));
        assertTrue(bst.search(5));
        assertFalse(bst.search(42));

        bst.remove(3);
        assertFalse(bst.search(3));
        assertTrue(bst.search(5));
        assertTrue(bst.search(7));
    }

    @Test
    void traversalsReturnExpectedOrders() {
        BinarySearchTree bst = new BinarySearchTree();
        for (int n : new int[]{7, 3, 9, 1, 5}) bst.insert(new TreeNode(n));

        assertEquals(List.of(1,3,5,7,9), bst.inOrder());
        assertEquals(List.of(7,3,1,5,9), bst.preOrder());
        assertEquals(List.of(1,5,3,9,7), bst.postOrder());
    }
}
