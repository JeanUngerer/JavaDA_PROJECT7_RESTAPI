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

    public UserModel createUser(UserDTO dto) {
        try {
            log.info("createUser");
            UserModel user = userMapper.dtoToModel(dto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(userMapper.modelToEntity(user));
            return user;
        } catch (Exception e) {
            log.error("Couldn't user user: " + e.getMessage());
            throw new ExceptionHandler("We could not create your user");
        }
    }
    public UserModel updateUser(UserDTO dto) {
        try {
            log.info("updateUserModel - id: " + dto.getId().toString());
            UserModel user = userMapper.entityToModel(userRepository.findById(dto.getId()).orElseThrow(()
                    -> new ExceptionHandler("We could not find your user")));
            userMapper.updateUserModelFromDto(dto, user, new CycleAvoidingMappingContext());
            userRepository.save(userMapper.modelToEntity(user));
            return user;
        } catch (Exception e) {
            log.error("Couldn't update user: " + e.getMessage());
            throw new ExceptionHandler("We could not update your user");
        }
    }

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
