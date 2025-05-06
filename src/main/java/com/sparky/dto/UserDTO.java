package com.sparky.dto;


import lombok.Data;
import com.sparky.Domain.User.Role;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private Role role;
    private Long companyId;
}
