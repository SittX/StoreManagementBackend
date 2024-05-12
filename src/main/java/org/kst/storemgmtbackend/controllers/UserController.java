package org.kst.storemgmtbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.models.User;
import org.kst.storemgmtbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:read')")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "false") boolean paging, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:read')")
    public ResponseEntity<List<User>> searchUser(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<User> getUserDetails(@PathVariable String id) {
        return ResponseEntity.ok().body(new User());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:update')")
    public ResponseEntity<User> updateUserDetails(@RequestBody User user) {
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:write')")
    public ResponseEntity<User> createNewAdminUser(@RequestBody User user, @RequestParam(name = "store_id") String storeId) {
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:write')")
    public ResponseEntity<Void> removeUser(@RequestParam String id, @RequestParam(name = "store_id") String storeId) {
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/validateUsernameExist")
    public ResponseEntity<Boolean> checkUsernameExist(@RequestBody String username) {
        return ResponseEntity.ok(false);
    }
}
