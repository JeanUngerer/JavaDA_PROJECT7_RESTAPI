package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password is too short min 8 characters")
    @Size(max = 16, message = "Password is too long max 16 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Column(name = "role", nullable = false)
    private String role;


}
