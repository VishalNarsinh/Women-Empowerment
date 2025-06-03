package com.lms.service;

import com.lms.dto.RegisterRequest;
import com.lms.dto.UserDto;
import com.lms.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface MyUserDetailsService {
    User registerRequestToUser(RegisterRequest registerRequest);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    UserDto saveUser(RegisterRequest registerRequest);

    void deleteUser(long userId);

    UserDto updateLearnerUser(UserDto userDto, MultipartFile file);
}
