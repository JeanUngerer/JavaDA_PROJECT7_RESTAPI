package com.nnk.springboot.dtos;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    Integer id;
    String username;
    String password;
    String fullname;
    String role;
}
