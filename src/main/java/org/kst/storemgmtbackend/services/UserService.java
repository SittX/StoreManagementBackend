package org.kst.storemgmtbackend.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.kst.storemgmtbackend.exceptions.DataNotFoundException;
import org.kst.storemgmtbackend.models.User;
import org.kst.storemgmtbackend.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CrudRestService<User, ObjectId> {
    private final UserRepository userRepository;

    @Override
    public User save(@NotNull User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findById(ObjectId objectId) throws DataNotFoundException {
        return this.userRepository
                .findById(objectId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    public User findByUsername(String username) throws DataNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    public boolean checkUsernameExist(String username){
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public List<User> findAll(boolean paging, int limit, int offset) {
        if(!paging){
            return this.userRepository.findAll();
        }

        PageRequest pageRequest = PageRequest.of(offset, limit);
        return this.userRepository.findAll(pageRequest).getContent();
    }

    @Override
    public User update(@NotNull User user) throws DataNotFoundException {
        if(!userRepository.existsById(user.getId())){
            throw new DataNotFoundException("User not found");
        }

        return this.userRepository.save(user);
    }

    @Override
    public void delete(@NotNull User user) throws DataNotFoundException {
        if(!userRepository.existsById(user.getId())){
            throw new DataNotFoundException("User not found");
        }

        this.userRepository.delete(user);
    }

    @Override
    public void deleteById(ObjectId objectId) throws DataNotFoundException {
        if(!userRepository.existsById(objectId)){
            throw new DataNotFoundException("User not found");
        }

        this.userRepository.deleteById(objectId);
    }



    public boolean usernameExists(String username) {
        if (username == null) return true;
        return this.userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        if (email == null) return true;
        return this.emailExists(email);
    }
}


