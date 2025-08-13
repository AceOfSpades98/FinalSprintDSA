package com.keyin.binarytree.controller;

import com.keyin.binarytree.model.TreeEntity;
import com.keyin.binarytree.model.TreeNode;
import com.keyin.binarytree.repository.TreeRepository;
import com.keyin.binarytree.service.BinarySearchTree;
import com.keyin.binarytree.service.TreeBuildService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean TreeBuildService buildService;
    @MockBean TreeRepository repository;

    @Test
    void previousTrees_returnsJsonArray() throws Exception {
        when(repository.findAll()).thenReturn(List.of(
                new TreeEntity("1,2,3","{\"root\":1}"),
                new TreeEntity("7 4 9","{\"root\":7}")
        ));

        mockMvc.perform(get("/api/previous-trees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void processNumbers_empty_returns400() throws Exception {
        when(buildService.parseNumbers("; ,")).thenReturn(List.of());

        mockMvc.perform(post("/api/process-numbers")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("numbers", "; ,"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"error\":\"Please enter at least one valid integer.\"}"));

        verifyNoInteractions(repository);
    }

    @Test
    void processNumbers_happyPath_savesAndReturnsJson() throws Exception {
        var input = "5,3,7"; var parsed = List.of(5,3,7);
        when(buildService.parseNumbers(input)).thenReturn(parsed);

        var bst = new BinarySearchTree();
        bst.root = new TreeNode(5);
        when(buildService.buildTree(parsed)).thenReturn(bst);
        when(buildService.toJson(bst.root)).thenReturn("{\"root\":5}");

        mockMvc.perform(post("/api/process-numbers")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("numbers", input))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"root\":5}"));

        var captor = ArgumentCaptor.forClass(TreeEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getInput()).isEqualTo(input);
        assertThat(captor.getValue().getTreeJson()).isEqualTo("{\"root\":5}");
    }
}
