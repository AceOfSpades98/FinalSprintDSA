package com.keyin.binarytree.controller;

import com.keyin.binarytree.model.TreeEntity;
import com.keyin.binarytree.repository.TreeRepository;
import com.keyin.binarytree.service.BinarySearchTree;
import com.keyin.binarytree.service.TreeBuildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final TreeBuildService buildService;
    private final TreeRepository repository;

    public ApiController(TreeBuildService buildService, TreeRepository repository) {
        this.buildService = buildService;
        this.repository = repository;
    }

    @GetMapping("/previous-trees")
    public List<TreeEntity> previousTrees() {
        return repository.findAll();
    }

    @PostMapping(value = "/process-numbers", produces = "application/json")
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
