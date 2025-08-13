package com.keyin.binarytree.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class TreeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false, length = 1000)
    public String input; // Preserves exactly what user types

    @Lob
    @Column(nullable = false)
    public String treeJson; // stores serialized tre so that it can be displayed without rebuilding it

    @Column(nullable = false)
    public LocalDate createdAt;

    public TreeEntity() {

    }

    public TreeEntity(String input, String treeJson) {
        this.input = input;
        this.treeJson = treeJson;
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    public String getTreeJson() {
        return treeJson;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setTreeJson(String treeJson) {
        this.treeJson = treeJson;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}