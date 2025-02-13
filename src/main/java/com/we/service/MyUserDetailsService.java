package com.we.service;

import com.we.dto.RegisterRequest;
import com.we.dto.UserDto;
import com.we.model.User;

public interface MyUserDetailsService {
    User registerRequestToUser(RegisterRequest registerRequest);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    UserDto saveLearnerUser(RegisterRequest registerRequest);

    void deleteUser(UserDto userDto);

    UserDto updateLearnerUser(UserDto userDto);
}
