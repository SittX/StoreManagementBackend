package org.kst.storemgmtbackend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.kst.storemgmtbackend.models.Item;
import org.kst.storemgmtbackend.services.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerToken")
public class ItemController {
    private final ItemService itemService;

    @GetMapping()
    public ResponseEntity<List<Item>> getAllItems(@RequestParam(defaultValue = "false") boolean paging, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<List<Item>> listItemForSpecificStore(@PathVariable String storeId) {
       return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Item> getItemDetails(@PathVariable String id) {
        return ResponseEntity.ok().body(new Item());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:update')")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        return ResponseEntity.ok().body(item);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:create')")
    public ResponseEntity<Item> createNewItem(@RequestBody Item item, @RequestParam(name = "store_id") String storeId) {
        return ResponseEntity.ok().body(item);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteItem(@RequestParam String id, @RequestParam(name = "store_id") String storeId) {
        return ResponseEntity.ok().body(null);
    }

}
