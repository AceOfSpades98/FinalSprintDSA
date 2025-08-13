package com.keyin.binarytree.controller;

import com.keyin.binarytree.service.BinarySearchTree;
import com.keyin.binarytree.model.TreeNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tree")
public class TreeController {

    private final BinarySearchTree bst;

    public TreeController(BinarySearchTree bst) {
        this.bst = bst;
    }

    @PostMapping("/nodes/{value}")
    public ResponseEntity<String> insert(@PathVariable int value) {
        bst.insert(new TreeNode(value));
        return ResponseEntity.status(HttpStatus.CREATED).body("Inserted " + value);
    }

    @GetMapping("/nodes/{value}")
    public ResponseEntity<Boolean> search(@PathVariable int value) {
        return ResponseEntity.ok(bst.search(value));
    }

    @DeleteMapping("/nodes/{value}")
    public ResponseEntity<String> remove(@PathVariable int value) {
        boolean exists = bst.search(value);
        bst.remove(value);
        return exists
                ? ResponseEntity.ok("Removed " + value)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Value " + value + " not found");
    }

    @GetMapping("/nodes")
    public ResponseEntity<List<Integer>> traversal(@RequestParam(defaultValue = "inorder") String order) {
        switch (order.toLowerCase()) {
            case "preorder":  return ResponseEntity.ok(bst.preOrder());
            case "postorder": return ResponseEntity.ok(bst.postOrder());
            case "inorder":
            default:          return ResponseEntity.ok(bst.inOrder());
        }
    }
}

