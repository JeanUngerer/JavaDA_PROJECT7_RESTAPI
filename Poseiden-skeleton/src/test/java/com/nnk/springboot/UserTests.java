package com.nnk.springboot;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.UserDTO;
import com.nnk.springboot.mappers.UserMapper;
import com.nnk.springboot.models.UserModel;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTests {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void userCreateService(){
        User entity = new User(0, "testuser", "testpassword", "testFullname", "USER");
        UserDTO dto = userMapper.modelToDto(userMapper.entityToModel(entity));

        UserModel model = userService.createUser(dto);

        assertNotNull(model);
        assertEquals("testuser", model.getUsername());
        assertEquals("USER", model.getRole());
    }

    @Test
    public void userUpdateService(){
        User entity = new User(0, "testuser", "testpassword", "testFullname", "USER");
        UserDTO dto = userMapper.modelToDto(userMapper.entityToModel(entity));

        UserModel model = userService.createUser(dto);
        model.setUsername("testuser2");
        model.setRole("USER2");
        model = userService.updateUser(userMapper.modelToDto(model));

        assertNotNull(model);
        assertEquals("testuser2", model.getUsername());
        assertEquals("USER2", model.getRole());
    }

    @Test
    public void userFindAllService(){
        User entity = new User(0, "testuser", "testpassword", "testFullname", "USER");
        UserDTO dto = userMapper.modelToDto(userMapper.entityToModel(entity));

        UserModel model = userService.createUser(dto);

        List<UserModel> list = userService.findAllUser();

        Integer len = list.size();
        assertNotNull(list);
        assertNotEquals(Optional.of(0), len);
    }

    @Test
    public void userFindByIdService(){
        User entity = new User(0, "testuser", "testpassword", "testFullname", "USER");
        UserDTO dto = userMapper.modelToDto(userMapper.entityToModel(entity));

        UserModel model = userService.createUser(dto);

        UserModel found = userService.findUserById(model.getId());

        assertNotNull(found);
        assertEquals(found.getId(), model.getId());
        assertEquals("testuser", model.getUsername());
        assertEquals("USER", model.getRole());
    }

    @Test
    public void userDeleteService(){
        User entity = new User(0, "testuser", "testpassword", "testFullname", "USER");
        UserDTO dto = userMapper.modelToDto(userMapper.entityToModel(entity));

        UserModel model = userService.createUser(dto);

        String message = userService.deleteUser(model.getId());


        assertNotNull(message);
        assertEquals(message, "UserModel deleted");

    }
}
