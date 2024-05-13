package org.kst.storemgmtbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.dtos.GenericResponse;
import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.kst.storemgmtbackend.models.Role;
import org.kst.storemgmtbackend.models.Store;
import org.kst.storemgmtbackend.models.User;
import org.kst.storemgmtbackend.services.StoreService;
import org.kst.storemgmtbackend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.kst.storemgmtbackend.models.Role.ADMIN;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final StoreService storeService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:read')")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "false") boolean paging, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "10") int offset) {
        List<User> users = this.userService.findAll(paging,limit, offset);
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:read')")
    public ResponseEntity<User> searchUserById(@RequestParam(defaultValue = "") ObjectId id) {
        try{
            User user = this.userService.findById(id);
            return ResponseEntity.ok().body(user);
        }catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:read')")
    public ResponseEntity<User> searchByUsername(@RequestParam(defaultValue = "") String username) {
        try{
            User user = this.userService.findByUsername(username);
            return ResponseEntity.ok().body(user);
        }catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<User> getUserDetails(@PathVariable ObjectId id) {
        try{
            User user = this.userService.findById(id);
            return ResponseEntity.ok().body(user);
        }catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:update')")
    public ResponseEntity<GenericResponse> updateUserDetails(@RequestBody User user) {
        try {
            User updatedUser = this.userService.update(user);
            return ResponseEntity.accepted()
                    .body(GenericResponse.builder().message("User has been updated successfully.").build());
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }catch(Exception ex){
            logger.error(ex.getMessage());
            ex.printStackTrace();
           return ResponseEntity.internalServerError()
                   .body(GenericResponse.builder().message(ex.getMessage()).build());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:write')")
    public ResponseEntity<User> createNewAdminUser(@RequestBody User user, @RequestParam(name = "store_id") ObjectId storeId) {
        // Save the new User into their associated Store
        Store targetStore = null;
        try {
            targetStore = this.storeService.findById(storeId);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        Set<User> storeOwners = targetStore.getOwners();
        storeOwners.add(user);
        targetStore.setOwners(storeOwners);
        this.storeService.save(targetStore);

        user.setRoles(Set.of(ADMIN));
        User admin = this.userService.save(user);
        return ResponseEntity.ok().body(admin);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:write')")
    public ResponseEntity<GenericResponse> removeUser(@RequestParam ObjectId id, @RequestParam(name = "store_id") ObjectId storeId) {
        try {
            // Remove the User from the owners list if the user has STORE_OWNER role
            // TODO: Check if the user has STORE_OWNER role and remove the user from the owner list.
            Store store = this.storeService.findById(storeId);
            Set<User> owners = store
                    .getOwners()
                    .stream()
                    .filter(owner -> owner.getId() != id)
                    .collect(Collectors.toSet());
            store.setOwners(owners);

            this.userService.deleteById(id);
            return ResponseEntity.ok().body(GenericResponse.builder().message("User has been deleted.").status("Success").build());
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkUsernameExist(@RequestBody String username) {
        return ResponseEntity.ok(this.userService.checkUsernameExist(username));
    }
}
