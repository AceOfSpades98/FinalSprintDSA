package com.keyin.binarytree.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.binarytree.model.TreeNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeBuildService {
    private final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public List<Integer> parseNumbers(String input) {
        List<Integer> out = new ArrayList<>();
        if (input == null) return out;
        String normalized = input.replaceAll("[;,]", " ").trim();
        for (String token : normalized.split("\\s+")) {
            if (!token.isBlank()) {
                try { out.add(Integer.parseInt(token)); } catch (NumberFormatException ignored) {}
            }
        }
        return out;
    }

    public BinarySearchTree buildTree(List<Integer> nums) {
        BinarySearchTree bst = new BinarySearchTree();
        for (int n : nums) bst.insert(new TreeNode(n));
        return bst;
    }

    public String toJson(TreeNode root) {
        try { return mapper.writeValueAsString(toDto(root)); }
        catch (Exception e) { throw new RuntimeException("Failed to serialize tree", e); }
    }

    private NodeDto toDto(TreeNode n) {
        if (n == null) return null;
        NodeDto d = new NodeDto();
        d.value = n.value;
        d.left = toDto(n.left);
        d.right = toDto(n.right);
        return d;
    }
    static class NodeDto { public int value; public NodeDto left, right; }
}
