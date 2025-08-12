package com.keyin.binarytree;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.binarytree.repository.TreeRepository;

public class TreeService {
    
    private TreeRepository treeRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public TreeService(TreeRepository treeRepository, ObjectMapper mapper) {
        this.treeRepository = treeRepository;
        this.mapper = mapper;
    }

    // parse numbers

    // insert

    // inOrder

    // save record

    // get all records
}
