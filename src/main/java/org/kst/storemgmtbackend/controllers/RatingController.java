package org.kst.storemgmtbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.kst.storemgmtbackend.models.Rating;
import org.kst.storemgmtbackend.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping()
    public ResponseEntity<List<Rating>> listAllRatings(@RequestParam boolean paging, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and hasAnyAuthority('admin:write', 'user:write')")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        return ResponseEntity.ok().body(rating);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and hasAnyAuthority('admin:delete', 'user:delete')")
    public ResponseEntity<Void> deleteRating(@RequestParam String id, @RequestParam(name = "store_id") String storeId) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/users")
    public ResponseEntity<Void> searchRatingForSpecificUser(@RequestParam(name = "user_id") String userId){
       return ResponseEntity.ok().body(null);
    }

    @GetMapping("/items")
    public ResponseEntity<Void> searchRatingForSpecificItem(@RequestParam(name = "item_id") String itemId){
        return ResponseEntity.ok().body(null);
    }
}
