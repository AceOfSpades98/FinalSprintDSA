package com.keyin.binarytree.controller;

import com.keyin.binarytree.repository.TreeRepository;
import com.keyin.binarytree.model.TreeEntity;
import com.keyin.binarytree.service.BinarySearchTree;
import com.keyin.binarytree.service.TreeBuildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UIController {

    private final TreeBuildService buildService;
    private final TreeRepository repository;

    public UIController(TreeBuildService buildService, TreeRepository repository) {
        this.buildService = buildService;
        this.repository = repository;
    }

    @GetMapping("/enter-numbers")
    public String enterNumbers() {
        return "enter-numbers";
    }

    @GetMapping("/previous-trees")
    public String previousTrees(Model model) {

    }

    @PostMapping(value = "/process-numbers", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> processNumbers(@RequestParam("numbers") String numbers) {
        var parsed = buildService.parseNumbers(numbers);
        if (parsed.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Please enter at least one valid integer.\"}");
        }

        BinarySearchTree bst = buildService.buildTree(parsed);
        String json = buildService.toJson(bst.root);

        repository.save(new TreeEntity(numbers, json));
        return ResponseEntity.ok(json);
    }


}
