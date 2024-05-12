package org.kst.storemgmtbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.kst.storemgmtbackend.models.Store;
import org.kst.storemgmtbackend.services.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping()
    public ResponseEntity<List<Store>> listAllStores(@RequestParam(defaultValue = "false") boolean paging, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Store>> searchStore(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('USER') and hasAuthority('user:read')")
    public ResponseEntity<List<Store>> listAllStoresForSpecificUser(@PathVariable String userId,
                                                                    @RequestParam(defaultValue = "false") boolean paging,
                                                                    @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Store> getStoreDetails(@PathVariable String id) {
        return ResponseEntity.ok().body(new Store());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:update')")
    public ResponseEntity<Store> updateStore(@RequestBody Store store) {
        return ResponseEntity.ok().body(store);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:write')")
    public ResponseEntity<Store> createNewStore(@RequestBody Store store, @RequestParam(name = "store_id") String storeId) {
        return ResponseEntity.ok().body(store);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteStore(@RequestParam String id, @RequestParam(name = "store_id") String storeId) {
        return ResponseEntity.ok().body(null);
    }
}
