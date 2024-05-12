package org.kst.storemgmtbackend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kst.storemgmtbackend.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll_should_return_first_page_items() {
        List<Item> items = itemService.findAll(true,0,0);
        Assertions.assertEquals(1, items.size());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}