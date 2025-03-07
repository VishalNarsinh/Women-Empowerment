package com.we.service.impl;

import com.we.dto.RegisterRequest;
import com.we.dto.UserDto;
import com.we.exception.ResourceNotFound;
import com.we.model.User;
import com.we.repository.UserRepository;
import com.we.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, MyUserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder bcryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFound("User", "email", username));
    }
    @Override
    public User registerRequestToUser(RegisterRequest registerRequest){
        return modelMapper.map(registerRequest, User.class);
    }

    @Override
    public UserDto userToUserDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User userDtoToUser(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }


    @Override
    public UserDto saveUser(RegisterRequest registerRequest){
        User user = registerRequestToUser(registerRequest);
        user.setRole(registerRequest.getRole());
        user.setEnabled(true);
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        return userToUserDto(save);
    }

    @Override
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));
        userRepository.delete(user);
    }

    @Override
    public UserDto updateLearnerUser(UserDto userDto, MultipartFile file) {
        return null;
    }


}
