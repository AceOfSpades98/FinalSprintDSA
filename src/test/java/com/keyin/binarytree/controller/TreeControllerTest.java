// src/test/java/com/keyin/binarytree/controller/TreeControllerTest.java
package com.keyin.binarytree.controller;

import com.keyin.binarytree.model.TreeNode;
import com.keyin.binarytree.service.BinarySearchTree;
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

@WebMvcTest(TreeController.class)
class TreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BinarySearchTree bst;

    @Test
    void insert_returns201_and_callsServiceWithTreeNodeValue() throws Exception {
        mockMvc.perform(post("/tree/nodes/{value}", 5))
                .andExpect(status().isCreated())
                .andExpect(content().string("Inserted 5"));

        ArgumentCaptor<TreeNode> captor = ArgumentCaptor.forClass(TreeNode.class);
        verify(bst).insert(captor.capture());
        assertThat(captor.getValue().getValue()).isEqualTo(5);
    }

    @Test
    void search_returnsBooleanBody_true() throws Exception {
        when(bst.search(7)).thenReturn(true);

        mockMvc.perform(get("/tree/nodes/{value}", 7))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(bst).search(7);
    }

    @Test
    void search_returnsBooleanBody_false() throws Exception {
        when(bst.search(8)).thenReturn(false);

        mockMvc.perform(get("/tree/nodes/{value}", 8))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(bst).search(8);
    }

    @Test
    void remove_whenExists_returns200_removedMessage_callsRemove() throws Exception {
        when(bst.search(9)).thenReturn(true);

        mockMvc.perform(delete("/tree/nodes/{value}", 9))
                .andExpect(status().isOk())
                .andExpect(content().string("Removed 9"));

        verify(bst).search(9);
        verify(bst).remove(9);
    }

    @Test
    void remove_whenNotExists_returns404_notFoundMessage_stillCallsRemove() throws Exception {
        when(bst.search(10)).thenReturn(false);

        mockMvc.perform(delete("/tree/nodes/{value}", 10))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Value 10 not found"));

        verify(bst).search(10);
        verify(bst).remove(10);
    }

    @Test
    void traversal_default_returnsInorder() throws Exception {
        when(bst.inOrder()).thenReturn(List.of(1, 2, 3));

        mockMvc.perform(get("/tree/nodes")) // no 'order' param
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[1,2,3]"));

        verify(bst).inOrder();
        verifyNoMoreInteractions(bst);
    }

    @Test
    void traversal_preorder_returnsPreorder() throws Exception {
        when(bst.preOrder()).thenReturn(List.of(4, 2, 3));

        mockMvc.perform(get("/tree/nodes").param("order", "preorder"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[4,2,3]"));

        verify(bst).preOrder();
        verifyNoMoreInteractions(bst);
    }

    @Test
    void traversal_postorder_returnsPostorder() throws Exception {
        when(bst.postOrder()).thenReturn(List.of(2, 3, 4));

        mockMvc.perform(get("/tree/nodes").param("order", "postorder"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[2,3,4]"));

        verify(bst).postOrder();
        verifyNoMoreInteractions(bst);
    }

    @Test
    void traversal_unknownOrder_fallsBackToInorder() throws Exception {
        when(bst.inOrder()).thenReturn(List.of(11, 12));

        mockMvc.perform(get("/tree/nodes").param("order", "weird"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[11,12]"));

        verify(bst).inOrder();
        verifyNoMoreInteractions(bst);
    }
}
