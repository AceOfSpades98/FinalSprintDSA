package com.keyin.binarytree.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TreeNodeTest {

    @Test
    void constructor_setsValue_and_DefaultHeightIsOne_andChildrenNull() {
        TreeNode n = new TreeNode(10);
        assertThat(n.getValue()).isEqualTo(10);
        assertThat(n.getHeight()).isEqualTo(1);
        assertThat(n.getLeft()).isNull();
        assertThat(n.getRight()).isNull();
    }

    @Test
    void setters_and_getters_forValue_work() {
        TreeNode n = new TreeNode(0);
        n.setValue(42);
        assertThat(n.getValue()).isEqualTo(42);

        n.setValue(-7);
        assertThat(n.getValue()).isEqualTo(-7);
    }

    @Test
    void setters_and_getters_forHeight_work() {
        TreeNode n = new TreeNode(1);
        n.setHeight(3);
        assertThat(n.getHeight()).isEqualTo(3);

        n.setHeight(0);
        assertThat(n.getHeight()).isEqualTo(0);
    }

    @Test
    void can_set_and_clear_left_and_right_children() {
        TreeNode parent = new TreeNode(5);
        TreeNode left = new TreeNode(3);
        TreeNode right = new TreeNode(7);

        parent.setLeft(left);
        parent.setRight(right);

        assertThat(parent.getLeft()).isSameAs(left);
        assertThat(parent.getRight()).isSameAs(right);

        parent.setLeft(null);
        parent.setRight(null);

        assertThat(parent.getLeft()).isNull();
        assertThat(parent.getRight()).isNull();
    }

    @Test
    void builds_small_tree_structure_correctly() {
        //      4
        //     / \
        //    2   6
        //       /
        //      5
        TreeNode root = new TreeNode(4);
        TreeNode n2 = new TreeNode(2);
        TreeNode n6 = new TreeNode(6);
        TreeNode n5 = new TreeNode(5);

        root.setLeft(n2);
        root.setRight(n6);
        n6.setLeft(n5);

        assertThat(root.getValue()).isEqualTo(4);
        assertThat(root.getLeft().getValue()).isEqualTo(2);
        assertThat(root.getRight().getValue()).isEqualTo(6);
        assertThat(root.getRight().getLeft().getValue()).isEqualTo(5);

        assertThat(root.getLeft()).isSameAs(n2);
        assertThat(root.getRight()).isSameAs(n6);
        assertThat(root.getRight().getLeft()).isSameAs(n5);
    }
}
