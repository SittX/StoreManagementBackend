package org.kst.storemgmtbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.dtos.GenericResponse;
import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.kst.storemgmtbackend.models.Store;
import org.kst.storemgmtbackend.services.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    @GetMapping()
    public ResponseEntity<List<Store>> listAllStores(@RequestParam(defaultValue = "false") boolean paging, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "10") int offset) {
        List<Store> stores = this.storeService.findAll(paging,limit,offset);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/search")
    public ResponseEntity<Store> searchStoreById(@RequestParam(defaultValue = "") ObjectId id) {
        try {
            Store store = this.storeService.findById(id);
            return ResponseEntity.ok().body(store);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('USER') and hasAuthority('user:read')")
    public ResponseEntity<List<Store>> listAllStoresForSpecificUser(@PathVariable String userId,
                                                                    @RequestParam(defaultValue = "false") boolean paging,
                                                                    @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Store> getStoreDetails(@PathVariable ObjectId id) {
        try {
            Store store = this.storeService.findById(id);
            return ResponseEntity.ok().body(store);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:update')")
    public ResponseEntity<GenericResponse> updateStore(@RequestBody Store store) {
        try {
            this.storeService.update(store);
            GenericResponse response = GenericResponse.builder().status("Updated").message("Store Details have been updated.").build();
            return ResponseEntity.ok().body(response);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:write')")
    public ResponseEntity<Store> createNewStore(@RequestBody Store store) {
        Store newStore = this.storeService.save(store);
        return ResponseEntity.ok(newStore);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:delete')")
    public ResponseEntity<GenericResponse> deleteStore(@RequestParam ObjectId id) {
        try {
            this.storeService.deleteById(id);
            GenericResponse response = GenericResponse.builder().message("Store has been successfully deleted.").status("Deletion Success").build();
            return ResponseEntity.ok().body(response);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
