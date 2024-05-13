package org.kst.storemgmtbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.dtos.GenericResponse;
import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.kst.storemgmtbackend.models.Item;
import org.kst.storemgmtbackend.models.Rating;
import org.kst.storemgmtbackend.models.Store;
import org.kst.storemgmtbackend.models.User;
import org.kst.storemgmtbackend.services.ItemService;
import org.kst.storemgmtbackend.services.RatingService;
import org.kst.storemgmtbackend.services.StoreService;
import org.kst.storemgmtbackend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {
    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);
    private final RatingService ratingService;
    private final UserService userService;
    private final ItemService itemService;


    @GetMapping
    public ResponseEntity<List<Rating>> listAllRatings(@RequestParam boolean paging, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "10") int offset) {
        List<Rating> ratings = this.ratingService.findAll(paging,limit,offset);
        return ResponseEntity.ok(ratings);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and hasAnyAuthority('admin:write', 'user:write')")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating, @RequestParam(name = "item_id") ObjectId itemId, @RequestParam(name = "user_id") ObjectId userId) {
        try {
            Item item = this.itemService.findById(itemId);
            User user = this.userService.findById(itemId);
            rating.setItem(item);
            rating.setUser(user);
            Rating newRating = this.ratingService.save(rating);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRating);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and hasAnyAuthority('admin:delete', 'user:delete')")
    public ResponseEntity<GenericResponse> deleteRating(@RequestParam ObjectId id) {
        try {
            this.ratingService.deleteById(id);
            GenericResponse response  = GenericResponse.builder().status("Deletion Success").message("Rating has been successfully deleted.").build();
            return ResponseEntity.ok().body(response);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Rating>> searchRatingBySpecificUser(@RequestParam(name = "user_id") ObjectId userId){
        try {
            List<Rating> ratings = this.ratingService.findRatingByUserId(userId);
            return ResponseEntity.ok().body(ratings);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
           return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<Rating>> searchRatingForSpecificItem(@RequestParam(name = "item_id") ObjectId itemId){
        try {
            List<Rating> ratings = this.ratingService.findRatingByItemId(itemId);
            return ResponseEntity.ok().body(ratings);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
