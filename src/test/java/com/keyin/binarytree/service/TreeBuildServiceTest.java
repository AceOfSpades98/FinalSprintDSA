package com.keyin.binarytree.service;

import com.keyin.binarytree.model.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TreeBuildServiceTest {

    private final TreeBuildService svc = new TreeBuildService();

    @Test
    void parseNumbers_handlesSpacesCommasSemicolonsAndGarbage() {
        List<Integer> nums = svc.parseNumbers("7,  3; 9  x  1 4");
        assertEquals(List.of(7, 3, 9, 1, 4), nums);
    }

    @Test
    void parseNumbers_nullOrEmptyGivesEmptyList() {
        assertTrue(svc.parseNumbers(null).isEmpty());
        assertTrue(svc.parseNumbers("").isEmpty());
        assertTrue(svc.parseNumbers("   , ; ").isEmpty());
    }

    @Test
    void buildTree_buildsFreshTreeWithExpectedTraversal() {
        var nums = List.of(7, 3, 9, 1, 5);
        BinarySearchTree bst = svc.buildTree(nums);

        // In-order should be sorted for a BST with unique ints
        assertEquals(List.of(1, 3, 5, 7, 9), bst.inOrder());
        // Sanity on search
        assertTrue(bst.search(5));
        assertFalse(bst.search(42));
    }

    @Test
    void toJson_serializesNestedStructure_andHandlesNull() {
        // Null root -> JSON "null"
        assertEquals("null", svc.toJson((TreeNode) null));

        // Build a tiny tree and check basic shape
        BinarySearchTree bst = svc.buildTree(List.of(7, 3, 9));
        String json = svc.toJson(bst.root);

        assertNotNull(json);
        // Basic structure checks (no strict ordering assertions needed)
        assertTrue(json.contains("\"value\":7"));
        assertTrue(json.contains("\"value\":3"));
        assertTrue(json.contains("\"value\":9"));
        assertTrue(json.contains("\"left\""));
        assertTrue(json.contains("\"right\""));
    }
}
