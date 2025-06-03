package com.lms.dto;

import com.lms.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
