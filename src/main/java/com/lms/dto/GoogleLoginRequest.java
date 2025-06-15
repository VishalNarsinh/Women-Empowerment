package com.lms.dto;

import com.lms.model.Role;
import lombok.Data;

@Data
public class GoogleLoginRequest {
    private String idToken;
    private Role role;
}
