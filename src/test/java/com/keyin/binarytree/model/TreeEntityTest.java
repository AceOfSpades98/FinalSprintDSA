package com.keyin.binarytree.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class TreeEntityTest {
    @Test
    void constructor_setsFields() {
        TreeEntity e = new TreeEntity("1,2,3", "{\"root\":1}");
        assertThat(e.getInput()).isEqualTo("1,2,3");
        assertThat(e.getTreeJson()).isEqualTo("{\"root\":1}");
        assertThat(e.getCreatedAt()).isNull();
    }

    @Test
    void setters_and_getters_work() {
        TreeEntity e = new TreeEntity();
        e.setId(42L);
        e.setInput("A B C");
        e.setTreeJson("{\"root\":\"A\"}");
        LocalDate date = LocalDate.of(2024, 12, 31);
        e.setCreatedAt(date);

        assertThat(e.getId()).isEqualTo(42L);
        assertThat(e.getInput()).isEqualTo("A B C");
        assertThat(e.getTreeJson()).isEqualTo("{\"root\":\"A\"}");
        assertThat(e.getCreatedAt()).isEqualTo(date);
    }

    @Test
    void prePersist_setsCreatedAt_ifNull() {
        TreeEntity e = new TreeEntity("x", "{}");
        e.onCreate(); // simulate JPA lifecycle
        assertThat(e.getCreatedAt()).isEqualTo(LocalDate.now());
    }

    @Test
    void prePersist_doesNotOverride_createdAt_ifAlreadySet() {
        TreeEntity e = new TreeEntity("x", "{}");
        LocalDate preset = LocalDate.of(2020, 1, 2);
        e.setCreatedAt(preset);

        e.onCreate();
        assertThat(e.getCreatedAt()).isEqualTo(preset);
    }
}
