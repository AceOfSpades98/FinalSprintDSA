package com.keyin.binarytree.repository;

import com.keyin.binarytree.model.TreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<TreeEntity, Long> {
    
}
