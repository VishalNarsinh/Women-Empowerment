package com.we.service;

import com.we.dto.RegisterRequest;
import com.we.dto.UserDto;
import com.we.exception.ResourceNotFound;
import com.we.model.Role;
import com.we.model.User;
import com.we.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService,MyUserDetailsService {

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
    public UserDto saveLearnerUser(RegisterRequest registerRequest){
        User user = registerRequestToUser(registerRequest);
        user.setRole(Role.ROLE_LEARNER);
        user.setEnabled(true);
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        return userToUserDto(save);
    }


}
