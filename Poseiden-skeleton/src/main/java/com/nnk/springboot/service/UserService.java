package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.UserDTO;
import com.nnk.springboot.exception.ExceptionHandler;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.mappers.UserMapper;
import com.nnk.springboot.models.UserModel;
import com.nnk.springboot.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class UserService implements UserDetailsService {

    UserMapper userMapper;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    /**
     * Get all User objects from database
     * @return List<UserModel> list of all entities found in database
     * @throws Exception Bad Request on error
     */
    public List<UserModel> findAllUser() {
        try {
            log.info("findAllUser");
            List<UserModel> userList = new ArrayList<UserModel>();
            userRepository.findAll().forEach(ct -> userList.add(userMapper.entityToModel(ct)));
            return  userList;
        } catch (Exception e) {
            log.error("We could not find all user: " + e.getMessage());
            throw new ExceptionHandler("We could not find your users");
        }
    }

    /**
     * Get a User object from database by ID
     * @param id the Id of the object to find
     * @return UserModel the user with correct Id, if any.
     * @throws  Exception Bad Request on error
     */
    public UserModel findUserById(Integer id) {
        try {
            log.info("findUserById - id: " + id.toString());
            UserModel user = userMapper.entityToModel(userRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We didn't find your user")));
            return user;
        } catch (Exception e) {
            log.error("We could not find all user: " + e.getMessage());
            throw new ExceptionHandler("We could not find your user");
        }
    }

    /**
     * Save a User object into database.
     * @param dto the User to save or update into database
     * @return UserModel the saved User
     * @throws  Exception Bad Request on error
     */
    public UserModel createUser(UserDTO dto) {
        try {
            log.info("createUser");
            UserModel user = userMapper.dtoToModel(dto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User entity = userRepository.save(userMapper.modelToEntity(user));
            return userMapper.entityToModel(entity);
        } catch (Exception e) {
            log.error("Couldn't user user: " + e.getMessage());
            throw new ExceptionHandler("We could not create your user");
        }
    }

    /**
     * Update a User object into database.
     * @param dto the User to save or update into database
     * @return UserModel the saved User
     * @throws  Exception Bad Request on error
     */
    public UserModel updateUser(UserDTO dto) {
        try {
            log.info("updateUserModel - id: " + dto.getId().toString());
            UserModel user = userMapper.entityToModel(userRepository.findById(dto.getId()).orElseThrow(()
                    -> new ExceptionHandler("We could not find your user")));
            userMapper.updateUserModelFromDto(dto, user, new CycleAvoidingMappingContext());
            User entity = userRepository.save(userMapper.modelToEntity(user));
            return userMapper.entityToModel(entity);
        } catch (Exception e) {
            log.error("Couldn't update user: " + e.getMessage());
            throw new ExceptionHandler("We could not update your user");
        }
    }

    /**
     * Delete a User object from database by ID
     * @param id the Id of the object to delete
     * @return String deleted messsage.
     * @throws Exception Bad Request on error
     */
    public String deleteUser(Integer id) {
        try {
            log.info("deleteUserModel - id: " + id.toString());
            UserModel user = userMapper.entityToModel(userRepository.findById(id).orElseThrow(()
                    -> new ExceptionHandler("We could not find your user")));
            userRepository.delete(userMapper.modelToEntity(user));
            return "UserModel deleted";
        } catch (Exception e) {
            log.error("Couldn't delete user: " + e.getMessage());
            throw new ExceptionHandler("We could not delete your user");
        }
    }

    /**
     * Loads a User object from database by username
     * @param username the username of the User to load
     * @return UserDetails loaded user.
     * @throws Exception Bad Request on error
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("loadUserByUsername");
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user with that username"));
            List<SimpleGrantedAuthority> authi = new ArrayList<>();
            authi.add(new SimpleGrantedAuthority(user.getRole()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authi);
        } catch (Exception e) {
            log.error("Couldn't load all user: " + username + " - error: " + e.getMessage());
            throw new ExceptionHandler("We could not load your profile");

        }
    }
}
