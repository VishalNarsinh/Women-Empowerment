package com.we.service;

import com.we.dto.RegisterRequest;
import com.we.dto.UserDto;
import com.we.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface MyUserDetailsService {
    User registerRequestToUser(RegisterRequest registerRequest);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    UserDto saveUser(RegisterRequest registerRequest);

    void deleteUser(long userId);

    UserDto updateLearnerUser(UserDto userDto, MultipartFile file);
}
